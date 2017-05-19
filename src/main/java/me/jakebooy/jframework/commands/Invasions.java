package me.jakebooy.jframework.commands;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.permissions.PermissionUser;
import me.jakebooy.jframework.util.Util;
import net.dv8tion.jda.core.EmbedBuilder;
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
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(JFramework.getUtil().getColor(57, 204, 204));
            builder.setDescription("There is 1 active invasion:");
            JSONObject object = array.getJSONObject(0);
            String cog = object.getString("invasion_cog");
            String district = object.getString("invasion_district");
            String remaining = object.getString("invasion_remaining");
            builder.addField("Cog", cog, true);
            builder.addField("District", district, true);
            builder.addField("Time Remaining", remaining, true);
            builder.setFooter("Toon Bot by Jakebooy", "https://www.gravatar.com/avatar/eac450191b27888572b65081cffc5b43");
            Invasions.message.getTextChannel().sendMessage(builder.build()).queue();
            return;
        }

        if (array.length() == 0) {
            message.getTextChannel().sendMessage("**HQ >** There are no active invasions.").queue();
            return;
        }

        if (array.length() > 1) {
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
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(JFramework.getUtil().getColor(57, 204, 204));
        builder.setDescription("There are " + array.length() + " active invasions:");

        String cogs = "";
        String districts = "";
        String remainings = "";

        for (int i = 0; i < array.length(); i++) {
            try {
                this.sleep(100);
            }catch(Exception e){

            }
            JSONObject object = array.getJSONObject(i);
            String cog = object.getString("invasion_cog");
            String district = object.getString("invasion_district");
            String remaining = object.getString("invasion_remaining");

            cogs+= cog + "\n";
            districts+= district + "\n";
            remainings += remaining + "\n";

        }
        builder.addField("Cog", cogs, true);
        builder.addField("District", districts, true);
        builder.addField("Time Remaining", remainings, true);
        builder.setFooter("Toon Bot by Jakebooy", "https://www.gravatar.com/avatar/eac450191b27888572b65081cffc5b43");
        Invasions.message.getTextChannel().sendMessage(builder.build()).queue();
        this.stop();
    }

    public static void s(JSONArray jsonArray){
        array = jsonArray;
        (new T()).start();
    }
}