package me.jakebooy.jframework.commands;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.permissions.ToonUser;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jake Steen on 17/05/2017.
 */
public class Ban extends AbstractCommand {

    private JFramework jFramework;
    public Ban(JFramework jFramework){
        super("ban");
        this.jFramework = jFramework;
    }

    @Override
    public void execute(Guild server, Message message, ToonUser user, String[] args) {
        if(!user.hasPermission("toon.moderation.ban")){
            message.getTextChannel().sendMessage("It looks like you're not authorized to do that!").queue();
            return;
        }
        if(args.length == 0){
            message.getTextChannel().sendMessage("Usage: ban <**@user**> <**reason...**>").queue();
            return;
        }

        User target = message.getMentionedUsers().get(0);
        String a = "";
        for(int i = 0; i < args.length; i++){
            a+= args[i] + " ";
        }
        if(server.getMember(message.getMentionedUsers().get(0)).getNickname() == null || server.getMember(message.getMentionedUsers().get(0)).getNickname().isEmpty()){
            a = a.replace("@"+ server.getMember(message.getMentionedUsers().get(0)).getEffectiveName() + " ", "");
        }else a = a.replace("@"+ server.getMember(message.getMentionedUsers().get(0)).getNickname() + " ", "");
        String[] arg = a.split(" ");

        if(arg.length != 0){
            String reason = arg[0];
            if(arg.length > 1) {
                for (int i = 1; i < arg.length; i++) {
                    reason += " " + arg[i];
                }
            }

            Date dt = new java.util.Date();
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(dt);

            if(jFramework.getMySQL().add("INSERT INTO bans (banner, banner_id, banned, banned_id, ban_date, reason, image) VALUES (?,?,?,?,?,?,?)", user.getUser().getName(), user.getUser().getId(), target.getName(), target.getId(), date, reason, target.getAvatarUrl())) {
                server.getController().ban(target.getId(), 7).queue();
                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor("Ban", "https://emojipedia-us.s3.amazonaws.com/cache/08/cc/08cc3db71086f360326028bb5cc889e5.png", "https://emojipedia-us.s3.amazonaws.com/cache/08/cc/08cc3db71086f360326028bb5cc889e5.png");
                builder.setThumbnail(target.getAvatarUrl());
                builder.setDescription(user.getUser().getName() + " has kicked " + target.getName());
                builder.setColor(jFramework.getUtil().getColor(255, 65, 54));
                builder.addField("Reason", reason, true);
                builder.setFooter("Toon Bot by Jakebooy", "https://www.gravatar.com/avatar/eac450191b27888572b65081cffc5b43");
                server.getTextChannelById(jFramework.getConfig().getString("logs")).sendMessage(builder.build()).queue();
                server.getTextChannelById(jFramework.getConfig().getString("default_channel")).sendMessage(user.getUser().getName() + " has banned " + target.getName() + "\nReason: " + reason).queue();
            }else{
                message.getTextChannel().sendMessage("There was an error inserting the kick into the database, so the user wasn't banned.").queue();
            }
            return;
        }
        message.getTextChannel().sendMessage("Usage: warn <**@user**> <**reason...**>").queue();
        return;
    }
}
