package me.jakebooy.jframework.commands;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.permissions.PermissionUser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jakebooy on 15/05/17.
 */
public class Invasions extends AbstractCommand {

    public static Message message;

    private JFramework jFramework;
    public Invasions(JFramework jFramework){
        super("invasions");
        this.jFramework = jFramework;
    }

    @Override
    public void execute(Guild server, Message message, PermissionUser user, String[] args) {
        this.message = message;
        JSONArray array = new JSONArray(getSource("http://api.ttr-invasions.com/json/invasionlist/"));
        if (array.length() == 1) {
            message.getTextChannel().sendMessage("**HQ >** There is 1 active invasion:").queue();
            JSONObject object = array.getJSONObject(0);
            String cog = object.getString("invasion_cog");
            String district = object.getString("invasion_district");
            String progress = object.getString("invasion_progress");
            String remaining = object.getString("invasion_remaining");
            message.getTextChannel().sendMessage("```" + district + " - " + cog + " with " + remaining + " remaining (" + progress + ")```").queue();
            return;
        }

        if (array.length() == 0) {
            message.getTextChannel().sendMessage("**HQ >** There are no active invasions.").queue();
            return;
        }

        if (array.length() > 1) {
            message.getTextChannel().sendMessage("**HQ >** There are " + array.length() + " invasions active:").queue();
            T.s(array);
            return;
        }
        message.getTextChannel().sendMessage("**HQ >** An error occurred.").queue();
    }

    public static String getSource(String url) {
        try {
            URL web = new URL(url);
            URLConnection wc = web.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(wc.getInputStream(), "UTF-8"));
            String input;
            StringBuilder sB = new StringBuilder();
            while ((input = in.readLine()) != null) sB.append(input);
            in.close();
            return sB.toString();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
class T extends Thread{

    private static JSONArray array;

    public void run(){
        String message = "";
        for (int i = 0; i < array.length(); i++) {
            try {
                this.sleep(100);
            }catch(Exception e){

            }
            JSONObject object = array.getJSONObject(i);
            String cog = object.getString("invasion_cog");
            String district = object.getString("invasion_district");
            String progress = object.getString("invasion_progress");
            String remaining = object.getString("invasion_remaining");

            message = message + String.format("%-15s - %-20s%-30s%-11s", district, cog, "with " + remaining + " remaining", " (" + progress + ")") + "\n";
        }
        Invasions.message.getTextChannel().sendMessage("```" + message + "```").queue();
        this.stop();
    }

    public static void s(JSONArray jsonArray){
        array = jsonArray;
        (new T()).start();
    }
}