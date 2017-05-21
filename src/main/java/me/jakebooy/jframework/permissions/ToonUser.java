package me.jakebooy.jframework.permissions;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.roles.JRole;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import oracle.jrockit.jfr.JFR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jakebooy on 19/04/17.
 */
public class ToonUser {

    private User user;
    private Member member;
    private List<String> roles = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();
    private int balance = 0;
    private int request_tokens = 0;
    private int skip_tokens = 0;

    public ToonUser(User user){
        this.user = user;
        List<Role> roles = JFramework.getApi().getGuildById(JFramework.getProperties().getString("guild")).getRoles();
        for(Role role : roles){
            member = JFramework.getApi().getGuildById(JFramework.getProperties().getString("guild")).getMember(user);
            if(JFramework.getApi().getGuildById(JFramework.getProperties().getString("guild")).getMembersWithRoles(role).contains(member)){
                addRole(role.getName());
            }
        }
        getFirstBalance();
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

    public void getFirstBalance(){
        try {
            HashMap<String, Object> u = JFramework.getStaticSQL().find("SELECT * FROM economy_balances WHERE user_id = ?", user.getId()).get(0);
            this.balance = Integer.parseInt(u.get("balance").toString());
            this.request_tokens = Integer.parseInt(u.get("request_tokens").toString());
            this.skip_tokens = Integer.parseInt(u.get("skip_tokens").toString());
        }catch(Exception e) {
            JFramework.getStaticSQL().add("INSERT INTO economy_balances (user_id, balance, request_tokens, skip_tokens) VALUES (?, ?, ?, ?)", user.getId(), 5, 0, 0);
            this.balance = 5;
            this.request_tokens = 0;
            this.skip_tokens = 0;
        }
    }

    public int getBalance(){
        return balance;
    }

    public int getRequestTokens(){
        return request_tokens;
    }

    public int getSkipTokens(){
        return skip_tokens;
    }

    public void addBalance(int balance){
        this.balance+= balance;
        JFramework.getStaticSQL().update("UPDATE economy_balance SET balance = ? WHERE user_id = ?", this.balance, user.getId());
    }

    public void removeBalance(int balance){
        this.balance-= balance;
        JFramework.getStaticSQL().update("UPDATE economy_balance SET balance = ? WHERE user_id = ?", this.balance, user.getId());
    }

    public void addRequestTokens(int tokens){
        this.request_tokens+= tokens;
        JFramework.getStaticSQL().update("UPDATE economy_balance SET request_tokens = ? WHERE user_id = ?", this.request_tokens, user.getId());
    }

    public void removeRequestTokens(int tokens){
        this.request_tokens-= tokens;
        JFramework.getStaticSQL().update("UPDATE economy_balance SET request_tokens = ? WHERE user_id = ?", this.request_tokens, user.getId());
    }

    public void addSkipTokens(int tokens){
        this.skip_tokens+= tokens;
        JFramework.getStaticSQL().update("UPDATE economy_balance SET skip_tokens = ? WHERE user_id = ?", this.skip_tokens, user.getId());
    }

    public void addRemoveTokens(int tokens){
        this.skip_tokens-= tokens;
        JFramework.getStaticSQL().update("UPDATE economy_balance SET skip_tokens = ? WHERE user_id = ?", this.skip_tokens, user.getId());
    }

    public EmbedBuilder getBalanceMessage(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(JFramework.getUtil().getColor(1, 255, 112));
        builder.setAuthor(user.getName(), user.getAvatarUrl(), user.getAvatarUrl());
        builder.addField("Balance", "$" + getBalance(), true);
        builder.addField("Request Tokens", getSkipTokens() + "", true);
        builder.addField("Skip Tokens", getRequestTokens() + "", true);
        return builder;
    }

}
