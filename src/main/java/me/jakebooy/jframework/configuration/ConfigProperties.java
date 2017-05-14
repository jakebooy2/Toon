package me.jakebooy.jframework.configuration;

import me.jakebooy.jframework.JFramework;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    public ConfigProperties(){}

    public String getString(String path){
        return getConfig().getProperty(path);
    }

    public int getInt(String path){
        int reply;
        try{
            reply = Integer.parseInt(getConfig().getProperty(path));
        }catch(Exception e){
            reply = 0;
        }
        return reply;
    }

    private Properties getConfig(){
        Properties prop = new Properties();
        InputStream input = null;
        try{
            input = new FileInputStream("config.properties");
            prop.load(input);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }




}
