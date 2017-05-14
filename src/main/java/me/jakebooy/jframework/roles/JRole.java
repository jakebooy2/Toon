package me.jakebooy.jframework.roles;

import me.jakebooy.jframework.JFramework;
import net.dv8tion.jda.core.entities.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jakebooy on 12/05/17.
 */
public class JRole {

    String name;
    String id;
    Role role;
    List<String> permissions = new ArrayList<>();

    public JRole(String role){
        name = role;
    }

    public void registerPermissions(){
        List<HashMap<String, Object>> result = JFramework.getStaticSQL().find("SELECT * FROM role_permissions WHERE role_id = ?", id);
        for(HashMap<String, Object> row : result){
            permissions.add(row.get("permission").toString());
        }
    }

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean addPermission(String permission){
        if(!permissions.contains(permission)){
            permissions.add(permission);
            JFramework.getStaticSQL().add("INSERT INTO role_permissions (role_id, role, permission) VALUES (?, ?, ?)", id, name, permission);
            return true;
         }
         return false;
    }

    public boolean removePermission(String permission){
        if(permissions.contains(permission)) {
            permissions.remove(permission);
            JFramework.getStaticSQL().remove("DELETE FROM permissions WHERE role_id = ? AND id = ?", role.getId(), permission);
            return true;
        }
        return false;
    }

    public List<String> getPermissions(){
        return permissions;
    }


}
