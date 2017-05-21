package me.jakebooy.jframework.commands;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.permissions.ToonUser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by Jake Steen on 19/05/2017.
 */
public class Balance extends AbstractCommand {

    private JFramework jFramework;
    public Balance(JFramework jFramework){
        super("balance");
        this.jFramework = jFramework;
    }

    @Override
    public void execute(Guild server, Message message, ToonUser user, String[] args) {
        if(!user.hasPermission("toon.economy.balance")){
            message.getTextChannel().sendMessage("It looks like you're not authorized to do that!").queue();
            return;
        }
        if(args.length == 0){
            message.getTextChannel().sendMessage(user.getBalanceMessage().build()).queue();
        }else{
            if(user.hasPermission("toon.economy.balance.others")){
                message.getTextChannel().sendMessage("It looks like you're not authorized to do that!").queue();
                return;
            }
            if(message.getMentionedUsers().size() == 0){
                message.getTextChannel().sendMessage("User not found!").queue();
                return;
            }
            User u = message.getMentionedUsers().get(0);
            ToonUser toonUser = new ToonUser(u);
            message.getTextChannel().sendMessage(toonUser.getBalanceMessage().build()).queue();
        }
    }

}