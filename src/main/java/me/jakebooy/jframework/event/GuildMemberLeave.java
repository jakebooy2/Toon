package me.jakebooy.jframework.event;


import me.jakebooy.jframework.JFramework;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by jakebooy on 14/05/17.
 */
public class GuildMemberLeave extends ListenerAdapter {

    private JFramework jFramework;
    public GuildMemberLeave(JFramework jFramework){
        this.jFramework = jFramework;
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e){
        User user = e.getMember().getUser();
        jFramework.getMySQL().remove("DELETE FROM user_permissions WHERE user_id = ?", user.getId());
        System.out.println(user.getName() + " left the server.");
    }

}
