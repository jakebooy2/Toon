package me.jakebooy.jframework;

import me.jakebooy.jframework.command.CommandManager;
import me.jakebooy.jframework.commands.*;
import me.jakebooy.jframework.configuration.Config;
import me.jakebooy.jframework.configuration.ConfigProperties;
import me.jakebooy.jframework.database.MySQL;
import me.jakebooy.jframework.event.GuildMemberJoin;
import me.jakebooy.jframework.event.GuildMemberLeave;
import me.jakebooy.jframework.event.MessageSend;
import me.jakebooy.jframework.event.RoleDelete;
import me.jakebooy.jframework.permissions.PermissionManager;
import me.jakebooy.jframework.roles.RoleManager;
import me.jakebooy.jframework.util.Util;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class JFramework {

    private static JDA api;
    private static ConfigProperties properties = new ConfigProperties();
    private MySQL mySQL;
    private static MySQL staticSQL;
    private PermissionManager permissionManager;
    private static CommandManager commandManager;
    private static RoleManager roleManager;
    private static Util util;
    private Config config;

    public static void main(String args[]){
        new JFramework(properties.getString("token"));
    }

    public JFramework(String token){
        try{
            api = new JDABuilder(AccountType.BOT).setToken(token).setAutoReconnect(true).buildBlocking();
            api.addEventListener(new MessageSend(this));
            api.addEventListener(new GuildMemberLeave(this));
            api.addEventListener(new RoleDelete(this));
            api.addEventListener(new GuildMemberJoin(this));
        }catch(Exception e){
            new JFramework(token);
        }

        mySQL = new MySQL(this);
        mySQL.connect();
        staticSQL = mySQL;
        commandManager = new CommandManager(this);
        roleManager = new RoleManager(this);

        // Create Tables
        mySQL.createTable("CREATE TABLE IF NOT EXISTS `commands` (\n\t`id` INT NOT NULL AUTO_INCREMENT,\n\t`command` VARCHAR(50) NOT NULL,\n\t`help` MEDIUMTEXT NOT NULL,\n\t`status` VARCHAR(50) NOT NULL,\n\tPRIMARY KEY (`id`),\n\tUNIQUE INDEX `command` (`command`)\n)\nCOLLATE='latin1_swedish_ci'\nENGINE=InnoDB\n;");
        mySQL.createTable("CREATE TABLE IF NOT EXISTS `role_permissions` (\n\t`id` INT NOT NULL AUTO_INCREMENT,\n\t`role_id` VARCHAR(50) NOT NULL,\n\t`role` VARCHAR(50) NOT NULL,\n\t`permission` VARCHAR(50) NOT NULL,\n\tPRIMARY KEY (`id`)\n)\nCOLLATE='latin1_swedish_ci'\nENGINE=InnoDB\n");
        mySQL.createTable("CREATE TABLE IF NOT EXISTS `user_permissions` (\n`id` INT NOT NULL AUTO_INCREMENT,\n`user_id` VARCHAR(50) NULL,\n`permission` VARCHAR(50) NULL,\nPRIMARY KEY (`id`)\n)\nCOLLATE='latin1_swedish_ci'\nENGINE=InnoDB\n;");
        mySQL.createTable("CREATE TABLE IF NOT EXISTS `bot_config` (\n\t`path` VARCHAR(50) NOT NULL,\n\t`value` VARCHAR(50) NULL,\n\tPRIMARY KEY (`path`)\n)\nCOLLATE='latin1_swedish_ci'\nENGINE=InnoDB\n;");

        mySQL.execute("INSERT INTO bot_config (`path`, `value`) VALUES ('prefix', '`')");

        config = new Config(this);

        permissionManager = new PermissionManager(this);
        roleManager.getRoles();

        util = new Util(this);

        // Register commands
        commandManager.registerCommand("help", new Help(this));
        commandManager.registerCommand("perm", new Perm(this));
        commandManager.registerCommand("command", new Command(this));
        commandManager.registerCommand("purge", new Purge(this));
        commandManager.registerCommand("toon", new Toon(this));
        commandManager.registerCommand("invasions", new Invasions(this));

    }

    public static CommandManager getCommandManager(){
        return commandManager;
    }

    public static JDA getApi(){
        return api;
    }

    public static ConfigProperties getProperties(){
        return properties;
    }

    public MySQL getMySQL(){
        return mySQL;
    }

    public static MySQL getStaticSQL(){ return staticSQL; }

    public PermissionManager getPermissionManager(){ return permissionManager; }

    public static RoleManager getRoleManager(){
        return roleManager;
    }

    public static Util getUtil(){
        return util;
    }

    public Config getConfig(){
        return config;
    }

}
