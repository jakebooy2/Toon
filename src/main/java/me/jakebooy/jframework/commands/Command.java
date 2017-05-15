package me.jakebooy.jframework.commands;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.handler.CommandHandler;
import me.jakebooy.jframework.permissions.PermissionUser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by jakebooy on 14/05/17.
 */
public class Command extends AbstractCommand {

    private JFramework jFramework;
    public Command(JFramework jFramework){
        super("command");
        this.jFramework = jFramework;
    }

    @Override
    public void execute(Guild server, Message message, PermissionUser user, String[] args) {
        if(!user.hasPermission("toon.admin.command")){
            message.getTextChannel().sendMessage("It looks like you're not authorized to do that!").queue();
            return;
        }
        if(args.length == 0){
            message.getTextChannel().sendMessage("**>** Command Configuration\n" +
                    " - command **command** set status **enabled/disabled**\n" +
                    " - command **command** set help **help message**").queue();
            return;
        }

        me.jakebooy.jframework.command.Command command = jFramework.getCommandManager().getCommand(args[0]);

        if(args[1].equalsIgnoreCase("set")){

            if(args[2].equalsIgnoreCase("status") && args.length == 4){
                if(command.getName().equalsIgnoreCase("help") || command.getName().equalsIgnoreCase("command") || command.getName().equalsIgnoreCase("perm")) return;
                boolean status = args[3].equalsIgnoreCase("enabled") ? true : false;
                command.setStatus(status);
                message.getTextChannel().sendMessage("Command **" + command.getName() + "** is now " + args[3] + ".");
                return;
            }

            if(args[2].equalsIgnoreCase("help") && args.length > 3){
                String help = "";
                for(int i = 3; i < args.length; i++){
                    help += args[i] + " ";
                }
                command.setHelp(help);
                message.getTextChannel().sendMessage("Help for **" + command.getName() + "** updated!").queue();
                return;
            }

        }

        message.getTextChannel().sendMessage("**>** Command Configuration\n" +
                " - command **command** set status **enabled/disabled**\n" +
                " - command **command** set help **help message**").queue();
        return;
    }
}
