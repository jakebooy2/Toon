package me.jakebooy.jframework.roles;

import me.jakebooy.jframework.JFramework;
import net.dv8tion.jda.core.entities.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakebooy on 14/05/17.
 */
public class RoleManager {

    private JFramework jFramework;
    public RoleManager(JFramework jFramework){
        this.jFramework = jFramework;
    }

    private List<JRole> roles = new ArrayList<>();

    public void getRoles(){
       List<Role> roles = jFramework.getApi().getGuildById(jFramework.getProperties().getString("guild")).getRoles();
       for(Role r : roles){
           JRole role = new JRole(r.getName());
           role.setId(r.getId());
           role.setRole(r);
           role.registerPermissions();
           addRole(role);
       }
    }

    public JRole getRole(String name){
        for(JRole role : roles){
            if(role.getName().equalsIgnoreCase(name)) return role;
        }
        return null;
    }

    public void addRole(JRole role){
        if(!roles.contains(role)) {
            roles.add(role);
        }
    }
}
