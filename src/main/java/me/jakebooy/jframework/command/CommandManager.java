package me.jakebooy.jframework.command;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import oracle.jrockit.jfr.JFR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jakebooy on 12/04/17.
 */
public class CommandManager {

    List<Command> commands = new ArrayList<>();

    private final JFramework framework;
    public CommandManager(JFramework framework){
        this.framework = framework;
    }

    public Command getCommand(String name){
        for(Command command : getCommands()){
            if(command.getName().equalsIgnoreCase(name))
                return command;
        }
        return null;
    }

    public List<Command> getCommands(){
        return commands;
    }

    public void registerCommand(String name, AbstractCommand executor){

        if(getCommand(name) != null) return;

        List<HashMap<String, Object>> commands = framework.getMySQL().find("SELECT * FROM commands WHERE command = ?", name);

        if(commands.isEmpty()){
            framework.getMySQL().add("INSERT INTO commands (command, help, status) VALUES (?, ?, ?)", name, "Some help here", "true");
            String help = "Some help here";
            String status = "true";

            Command command = new Command(name, executor);

            command.setHelp(help);
            command.setStatus(Boolean.valueOf(status));

            this.commands.add(command);

            System.out.println("Command \"" + name + "\" added to the database!");

        }else{
            String help = commands.get(0).get("help").toString();
            String status = commands.get(0).get("status").toString();
            String id = commands.get(0).get("id").toString();

            Command command = new Command(name, executor);

            command.setHelp(help);
            command.setStatus(Boolean.valueOf(status));
            command.setId(id);

            this.commands.add(command);

            System.out.println("Command \"" + name + "\" registered!");
        }

    }

    public boolean deleteCommand(String name){
        List<HashMap<String, Object>> commands = framework.getMySQL().find("SELECT * FROM `commands`");
        int id = -1;
        for(HashMap<String, Object> command : commands){
            if(command.get("command").toString().equalsIgnoreCase(name)){
                id = (int)command.get("id");
            }
        }
        if(id!=-1){
            framework.getMySQL().remove("DELETE FROM `commands` WHERE id = ?", id);
            commands.remove(getCommand(name));
            return true;
        }
        return false;
    }

}
