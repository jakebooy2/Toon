package me.jakebooy.jframework.event;

import me.jakebooy.jframework.JFramework;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by jakebooy on 15/05/17.
 */
public class GuildMemberJoin extends ListenerAdapter {

    private JFramework jFramework;
    public GuildMemberJoin(JFramework jFramework){
        this.jFramework = jFramework;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        String join_message = jFramework.getConfig().getString("join_message").replace("%user%", e.getMember().getAsMention());
        e.getGuild().getTextChannelById(jFramework.getConfig().getString("default_channel")).sendMessage(join_message).queue();
        e.getMember().getUser().openPrivateChannel().queue(chan -> chan.sendMessage("Welcome to the TTR Community Discord Server!  \nBefore you can gain access to the rest of the channels, please read through <#314611361617543168>.  We left <#166138524779675652> viewable so that you can get a glimpse into our community before joining. \nOnce you're ready, and have read the rules, reply with \"accept\" to be given access to the rest of the channels.").queue());
    }

}
