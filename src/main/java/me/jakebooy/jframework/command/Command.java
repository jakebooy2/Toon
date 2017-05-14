package me.jakebooy.jframework.command;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakebooy on 12/04/17.
 */
public class Command {

    String name;
    String id;
    String help;
    boolean status;
    AbstractCommand executor;


    public Command(String name, AbstractCommand executor){
        this.name = name;
        this.executor = executor;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setHelp(String help){
        this.help = help;
        JFramework.getStaticSQL().update("UPDATE commands SET help = ? WHERE command = ?", help, name);
    }

    public String getHelp(){
        return help;
    }

    public void setStatus(boolean status){
        this.status = status;
        String stat = status ? "true" : "false";
        JFramework.getStaticSQL().update("UPDATE commands SET status = ? WHERE command = ?", stat, name);
    }

    public boolean getStatus(){
        return status;
    }

    public void setExecutor(AbstractCommand executor){
        this.executor = executor;
    }

    public AbstractCommand getExecutor(){
        return executor;
    }

}
