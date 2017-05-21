package me.jakebooy.jframework.commands;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.permissions.ToonUser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by jakebooy on 15/05/17.
 */
public class Toon extends AbstractCommand {

    private JFramework jFramework;
    public Toon(JFramework jFramework){
        super("toon");
        this.jFramework = jFramework;
    }

    @Override
    public void execute(Guild server, Message message, ToonUser user, String[] args) {
        if(!user.hasPermission("toon.user.toon")){
            message.getTextChannel().sendMessage("It looks like you're not authorized to do that!").queue();
            return;
        }
        if(args.length < 1){
            message.getTextChannel().sendMessage("Usage: toon <name>").queue();
            return;
        }
        String toon = args[0];
        if(args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                toon += " " + args[i];
            }
        }
        if((user.getUser().getName() + "(" + toon + ")").length() > 32){
            message.getTextChannel().sendMessage("Nicknames have a character limit of 32!").queue();
            return;
        }
        try {
            server.getController().setNickname(user.getMember(), user.getUser().getName() + " (" + toon + ")").queue();
            message.getTextChannel().sendMessage("**" + user.getMember().getEffectiveName() + " > **Username updated!").queue();
        }catch(Exception e){
            e.printStackTrace();
            message.getTextChannel().sendMessage("**Error** Perhaps your role is higher than mine?").queue();
        }
        return;
    }
}
