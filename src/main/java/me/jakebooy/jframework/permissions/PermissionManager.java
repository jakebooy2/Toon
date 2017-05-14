package me.jakebooy.jframework.permissions;

import me.jakebooy.jframework.JFramework;
import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jakebooy on 19/04/17.
 */
public class PermissionManager {

    private JFramework framework;
    public PermissionManager(JFramework framework){
        this.framework = framework;
    }

    public PermissionUser getPermissionUser(String id){
        PermissionUser user = new PermissionUser(framework.getApi().getUserById(id));
        List<HashMap<String, Object>> result = framework.getMySQL().find("SELECT * FROM user_permissions WHERE user_id = ?", id);
        for(HashMap<String, Object> row : result){
            user.addPerm(row.get("permission").toString());
        }
        return user;
    }


}
