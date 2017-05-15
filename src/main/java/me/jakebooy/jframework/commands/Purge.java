package me.jakebooy.jframework.commands;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.permissions.PermissionUser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.requests.RestAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakebooy on 15/05/17.
 */
public class Purge extends AbstractCommand {

    private JFramework jFramework;
    public Purge(JFramework jFramework){
        super("purge");
        this.jFramework = jFramework;
    }

    @Override
    public void execute(Guild server, Message message, PermissionUser user, String[] args) {
        if(!user.hasPermission("toon.admin.purge")){
            message.getTextChannel().sendMessage("It looks like you're not authorized to do that!").queue();
            return;
        }
        if(args.length == 0){
            message.getTextChannel().sendMessage("Usage: purge <#>").queue();
            return;
        }
        int toPurge = Integer.parseInt(args[0]);
        List<Message> messages;
        try {
            messages = message.getTextChannel().getHistory().retrievePast(toPurge).block();
            message.getTextChannel().deleteMessages(messages).queue();
            message.getTextChannel().sendMessage("Messages purged.").queue();
        }catch(Exception e){
            message.getTextChannel().sendMessage("Error: " + e.getMessage());
        }
    }
}
