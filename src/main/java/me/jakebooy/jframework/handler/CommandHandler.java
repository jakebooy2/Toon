package me.jakebooy.jframework.handler;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.command.Command;
import me.jakebooy.jframework.permissions.ToonUser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandHandler extends ListenerAdapter {


    public void handle(Guild server, Message message, ToonUser user, String sMessage){
        if(!sMessage.startsWith(JFramework.getUtil().getPrefix())) return;
        sMessage = sMessage.substring(1);
        String[] args = sMessage.split(" ");
        String command = args[0];
        AbstractCommand cmd = null;
        for(Command c : JFramework.getCommandManager().getCommands()){
            if(c.getExecutor().getName().equalsIgnoreCase(command)){
                cmd = c.getExecutor();
                break;
            }
        }
        Command c = JFramework.getCommandManager().getCommand(command);
        if(cmd == null && c == null){
           // message.getTextChannel().sendMessage("Command not found!").queue();
            // This will just return in the future
            return;
        }
        if(!c.getStatus()) return;

        String[] newArgs = new String[args.length - 1];
        for(int i = 1; i < args.length; i++){
            newArgs[i - 1] = args[i];
        }
        cmd.execute(server, message, user, newArgs);
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        System.out.println("[" + timeStamp + "] " + user.getMember().getEffectiveName() + " ran \"" + sMessage + "\"");
    }

    /*private static List<AbstractCommand> commands = new ArrayList<>();

    public List<AbstractCommand> getCommands(){
        return commands;
    }

    public void handle(Guild server, Message message, Member user, String sMessage){
        if(!sMessage.startsWith(Util.prefix)) return;
        sMessage = sMessage.substring(1);
        String[] args = sMessage.split(" ");
        String command = args[0];
        AbstractCommand cmd = null;
        for(AbstractCommand c : commands){
            if(c.getName().equalsIgnoreCase(command)){
                cmd = c;
                break;
            }
        }
        Command c = CommandManager.getCommand(command);
        if(cmd == null){
            if(c != null){
                message.getTextChannel().sendMessage("Command is registered, but has no handler!").queue();
                System.out.println("Be sure to register(new CommandClass(this)) in CogCommand for the command to work!");
            }
            return;
        }
        if(!c.getStatus())return;

        boolean hasPermission = false;
        for(String permission : c.getPermissions()){
            if(Util.userHasRole(user, permission)) hasPermission = true;
        }
        if(!hasPermission){
            message.getTextChannel().sendMessage("You don't have permission to do that!").queue();
            return;
        }

        String[] newArgs = new String[args.length - 1];
        for(int i = 1; i < args.length; i++){
            newArgs[i - 1] = args[i];
        }
        cmd.execute(server, message, user, newArgs);
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        System.out.println("[" + timeStamp + "] " + user.getEffectiveName() + " ran \"" + sMessage + "\"");
    }

    public AbstractCommand getCommand(String name){
        for(AbstractCommand c : getCommands()){
            if(c.getName().equalsIgnoreCase(name))
                return c;
        }
        return null;
    }

    public void register(AbstractCommand command){
        if(getCommand(command.getName()) == null){
            getCommands().add(command);
        }
    }

    public void unregister(String name){
        if(getCommand(name) != null){
            commands.remove(getCommand(name));
        }
    }
*/
}