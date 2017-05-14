package me.jakebooy.jframework.configuration;

import me.jakebooy.jframework.JFramework;

public class Config {

    private final JFramework jFramework;
    public Config(JFramework jFramework){
        this.jFramework = jFramework;
    }

    public String getString(String path){
        try {
            return jFramework.getMySQL().find("SELECT * FROM bot_config WHERE path = ?", path).get(0).get("value").toString();
        }catch(Exception e){
            return null;
        }
    }

    public int getInt(String path){
        try {
            return Integer.valueOf(jFramework.getMySQL().find("SELECT * FROM bot_config WHERE path = ?", path).get(0).get("value").toString());
        }catch(Exception e){
            return -1;
        }
    }

    public void setInt(String path, int value){
        if(getInt(path) != -1){
            jFramework.getMySQL().update("UPDATE bot_config SET value = ? WHERE path = ?", value, path);
            return;
        }
        jFramework.getMySQL().add("INSERT INTO bot_config (path, value) VALUES(?, ?)", path, value);
        return;
    }

    public void setString(String path, String value){
        if(getString(path) != null){
            jFramework.getMySQL().update("UPDATE bot_config SET value = ? WHERE path = ?", value, path);
            return;
        }
        jFramework.getMySQL().add("INSERT INTO bot_config (path, value) VALUES(?, ?)", path, value);
        return;
    }



}