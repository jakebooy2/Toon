package me.jakebooy.jframework.event;

import me.jakebooy.jframework.JFramework;
import net.dv8tion.jda.core.events.role.RoleDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by jakebooy on 19/04/17.
 */
public class RoleDelete extends ListenerAdapter{


    private JFramework jFramework;
    public RoleDelete(JFramework jFramework){
        this.jFramework = jFramework;
    }

    @Override
    public void onRoleDelete(RoleDeleteEvent e){
        jFramework.getMySQL().remove("DELETE FROM role_permissions WHERE role_id = ?", e.getRole().getId());
        System.out.println("Role \"" + e.getRole().getName() + "\" deleted.");
    }

}
