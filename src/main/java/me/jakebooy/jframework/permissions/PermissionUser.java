package me.jakebooy.jframework.permissions;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.roles.JRole;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import oracle.jrockit.jfr.JFR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakebooy on 19/04/17.
 */
public class PermissionUser {

    private User user;
    private Member member;
    private List<String> roles = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

    public PermissionUser(User user){
        this.user = user;
        List<Role> roles = JFramework.getApi().getGuildById(JFramework.getProperties().getString("guild")).getRoles();
        for(Role role : roles){
            member = JFramework.getApi().getGuildById(JFramework.getProperties().getString("guild")).getMember(user);
            if(JFramework.getApi().getGuildById(JFramework.getProperties().getString("guild")).getMembersWithRoles(role).contains(member)){
                addRole(role.getName());
            }
        }

    }

    public List<String> getPermissions() {
        List<String> perms = new ArrayList<>(permissions);
        for (String role : roles) {
            JRole r = JFramework.getRoleManager().getRole(role);
            for (String perm : r.getPermissions()) {
                perms.add(perm);
            }
        }
        return perms;
    }

    public boolean hasPermission(String permission){
        boolean hasPermission = false;
        if(permissions.contains(permission)){
            hasPermission = true;
        }
        for(String role : roles){
            JRole r = JFramework.getRoleManager().getRole(role);
            for(String perm : r.getPermissions()){
                if(perm.equalsIgnoreCase(permission)) hasPermission = true;
            }
        }
        return hasPermission;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void addRole(String role){
        roles.add(role);
    }

    public void removeRole(String role){
        roles.remove(role);
    }

    public List<String> getRoles(){
        return roles;
    }

    public void addPerm(String permission){
        if(!permissions.contains(permission))
            permissions.add(permission);
    }

    public boolean addPermission(String permission){
        if(!permissions.contains(permission)) {
            permissions.add(permission);
            JFramework.getStaticSQL().add("INSERT INTO user_permissions (user_id, permission) VALUES (?, ?)", user.getId(), permission);
            return true;
        }
        return false;
    }

    public boolean removePermission(String permission){
        if(permissions.contains(permission)) {
            permissions.remove(permission);
            JFramework.getStaticSQL().add("DELETE FROM user_permissions WHERE user_id = ? AND permission = ?", user.getId(), permission);
            return true;
        }
        return false;
    }

    public Member getMember(){
        return member;
    }

}
