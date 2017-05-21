package me.jakebooy.jframework.handler;

import me.jakebooy.jframework.permissions.ToonUser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

public abstract class AbstractCommand extends CommandHandler {

    private String name;

    public AbstractCommand(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public abstract void execute(Guild server, Message message, ToonUser user, String[] args);

}