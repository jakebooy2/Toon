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
    }

}
