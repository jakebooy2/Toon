package me.jakebooy.jframework.commands;

import javafx.embed.swing.JFXPanel;
import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.command.Command;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.permissions.PermissionUser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by jakebooy on 14/05/17.
 */
public class Help extends AbstractCommand {

    private JFramework jFramework;
    public Help(JFramework jFramework){
        super("help");
        this.jFramework = jFramework;
    }

    @Override
    public void execute(Guild server, Message message, PermissionUser user, String[] args) {
        List<Command> commands = jFramework.getCommandManager().getCommands();
        if(args.length == 0){
            StringJoiner join = new StringJoiner(", ");
            int count = 0;
            for(Command command : commands){
                if(command.getStatus()) {
                    count++;
                    join.add(command.getName());
                }
            }
            message.getTextChannel().sendMessage("**Commands (" + count + "): **" + join.toString()).queue();
        }else if(args[0].equalsIgnoreCase("custom")){
            // support for Custom Commands in here
        }else if(args[0].equalsIgnoreCase("disabled")){
            StringJoiner join = new StringJoiner(", ");
            int count = 0;
            for(Command command : commands){
                if(!command.getStatus()) {
                    count++;
                    join.add(command.getName());
                }
            }
            message.getTextChannel().sendMessage("**Disabled Commands (" + count + "): **" + join.toString()).queue();
        }else{
            String command = args[0];
            if(jFramework.getCommandManager().getCommand(command) != null){
                Command cmd = jFramework.getCommandManager().getCommand(command);
                String help = cmd.getHelp();
                message.getTextChannel().sendMessage("**" + cmd.getName() + ": **" + help).queue();
            }else{
                message.getTextChannel().sendMessage("**ERROR** Unknown command!").queue();
            }
        }
        return;
    }
}
