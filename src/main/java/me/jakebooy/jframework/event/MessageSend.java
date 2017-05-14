package me.jakebooy.jframework.event;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.CommandHandler;
import me.jakebooy.jframework.permissions.PermissionUser;
import me.jakebooy.jframework.util.Util;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by jakebooy on 14/05/17.
 */
public class MessageSend extends CommandHandler {

    private JFramework jFramework;
    public MessageSend(JFramework jFramework){
        this.jFramework = jFramework;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        String msg = e.getMessage().getContent();
        if(msg.startsWith(jFramework.getUtil().getPrefix())) {
            handle(e.getGuild(), e.getMessage(), jFramework.getPermissionManager().getPermissionUser(e.getAuthor().getId()), e.getMessage().getContent());
        }

    }

}
