package me.jakebooy.jframework.event;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.CommandHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

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

        if(e.getMessage().getAuthor().isBot()){
            return;
        }

        if(e.getChannelType() == ChannelType.PRIVATE){
            if(e.getMessage().getContent().toLowerCase().contains("mature")){
                Guild guild = jFramework.getApi().getGuildById("166138524779675652");
                boolean c = true;
                boolean isToon = false;
                Member member = guild.getMember(e.getMessage().getAuthor());
                List<Role> roles = member.getRoles();
                if(!roles.isEmpty()){
                    for(Role r: roles){
                        if(r.getId().equalsIgnoreCase("314769011290144769")) c = false;
                        if(r.getId().equalsIgnoreCase("166169934081163265")) isToon = true;
                    }
                }

                if(!isToon){
                    e.getPrivateChannel().getUser().openPrivateChannel().queue(chan -> chan.sendMessage("Whoa there, not so fast! You must accept the server rules first.").queue());
                    return;
                }
                if(c){
                    guild.getController().addRolesToMember(member, guild.getRoleById("314769011290144769")).queue();
                    e.getPrivateChannel().getUser().openPrivateChannel().queue(chan -> chan.sendMessage("You now have access to the mature channel.").queue());
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(jFramework.getUtil().getColor(138, 43, 226));
                    builder.setTitle("Mature Access Gained");
                    builder.setThumbnail(member.getUser().getAvatarUrl());
                    builder.setDescription(member.getUser().getName() + " now has access to Mature");
                    builder.setFooter("Toon Bot by Jakebooy", "https://www.gravatar.com/avatar/eac450191b27888572b65081cffc5b43");
                    guild.getTextChannelById("314823919540371456").sendMessage(builder.build()).queue();
                    return;
                }else{
                    e.getPrivateChannel().getUser().openPrivateChannel().queue(chan -> chan.sendMessage("You already have access to the mature channel.").queue());
                    return;
                }
            }else if(e.getMessage().getContent().toLowerCase().contains("accept")){
                Guild guild = jFramework.getApi().getGuildById("166138524779675652");
                boolean c = true;
                Member member = guild.getMember(e.getMessage().getAuthor());
                List<Role> roles = member.getRoles();
                if(!roles.isEmpty()){
                    for(Role r: roles){
                        if(r.getId().equalsIgnoreCase("166169934081163265")) c = false;
                    }
                }
                if(c){
                    guild.getController().addRolesToMember(member, guild.getRoleById("166169934081163265")).queue();
                    e.getPrivateChannel().getUser().openPrivateChannel().queue(chan -> chan.sendMessage("Welcome to the TTR Community Server. You now have access to the server.").queue());
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(jFramework.getUtil().getColor(240, 18, 190));
                    builder.setTitle("Rules Accepted");
                    builder.setThumbnail(member.getUser().getAvatarUrl());
                    builder.setDescription(member.getUser().getName() + " has accepted the rules.");
                    builder.setFooter("Toon Bot by Jakebooy", "https://www.gravatar.com/avatar/eac450191b27888572b65081cffc5b43");
                    guild.getTextChannelById("314823919540371456").sendMessage(builder.build()).queue();
                    return;
                }else{
                    e.getPrivateChannel().getUser().openPrivateChannel().queue(chan -> chan.sendMessage("You've already accepted.").queue());
                    return;
                }
            }
            return;
        }

        if (msg.startsWith(jFramework.getUtil().getPrefix())) {
            handle(e.getGuild(), e.getMessage(), jFramework.getPermissionManager().getPermissionUser(e.getAuthor().getId()), e.getMessage().getContent());
        }


    }

}
