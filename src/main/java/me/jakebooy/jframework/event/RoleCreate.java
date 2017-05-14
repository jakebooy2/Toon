package me.jakebooy.jframework.event;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.roles.JRole;
import net.dv8tion.jda.core.events.role.RoleCreateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by jakebooy on 14/05/17.
 */
public class RoleCreate extends ListenerAdapter {

    private JFramework jFramework;
    public RoleCreate(JFramework jFramework){
        this.jFramework = jFramework;
    }

    @Override
    public void onRoleCreate(RoleCreateEvent e){
        JRole role = new JRole(e.getRole().getName());
        role.setId(e.getRole().getId());
        role.setRole(e.getRole());
        jFramework.getRoleManager().addRole(role);
        System.out.println("Role \"" + e.getRole().getName() + "\" created.");
    }


}
