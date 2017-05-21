package me.jakebooy.jframework.commands;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.permissions.ToonUser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Jake Steen on 20/05/2017.
 */
public class Cog extends AbstractCommand {

    private JFramework jFramework;
    public Cog(JFramework jFramework){
        super("cog");
        this.jFramework = jFramework;
    }

    @Override
    public void execute(Guild server, Message message, ToonUser user, String[] args) {
        if(args.length == 0){
            message.getTextChannel().sendMessage("Usage: cog <cog name>").queue();
            return;
        }
        String name = args[0];
        if(args.length > 1){
            for(int i = 1; i < args.length; i++){
                name+= " " + args[i];
            }
        }
        if(name.contains("hollywood")) name = "Mr. Hollywood";
        me.jakebooy.jframework.cog.Cog cog = jFramework.getCogManager().getCog(name);
        if(cog != null){
            message.getTextChannel().sendMessage(jFramework.getCogManager().getCogMessage(cog.getName()).build()).queue();
            return;
        }
        message.getTextChannel().sendMessage("Cog not found!").queue();
    }
}
