package me.jakebooy.jframework.commands;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.permissions.ToonUser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Jake Steen on 19/05/2017.
 */
public class Economy extends AbstractCommand {

    private JFramework jFramework;
    public Economy(JFramework jFramework){
        super("economy");
        this.jFramework = jFramework;
    }

    @Override
    public void execute(Guild server, Message message, ToonUser user, String[] args) {

    }
}
