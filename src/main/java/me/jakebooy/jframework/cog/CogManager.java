package me.jakebooy.jframework.cog;

import me.jakebooy.jframework.JFramework;
import net.dv8tion.jda.core.EmbedBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Jake Steen on 20/05/2017.
 */
public class CogManager {

    private JFramework jFramework;
    public CogManager(JFramework jFramework){
        this.jFramework = jFramework;
    }

    List<Cog> cogs = new ArrayList<>();

    public void getCogs(){
        List<HashMap<String, Object>> cogList = jFramework.getMySQL().get("cogs");
        List<HashMap<String, Object>> levelsList = jFramework.getMySQL().get("cogs_levels");
        List<HashMap<String, Object>> streetList = jFramework.getMySQL().get("cogs_streets");
        for(HashMap<String, Object> cogMap : cogList){
            Cog cog = new Cog(cogMap.get("name").toString(), cogMap.get("department").toString());
            for(HashMap<String, Object> level : levelsList){
                if(level.get("cog_id").toString().equalsIgnoreCase(cogMap.get("cog_id").toString())){
                    cog.setMinLevel(Integer.parseInt(level.get("min").toString()));
                    cog.setMaxLevel(Integer.parseInt(level.get("max").toString()));
                }
            }
            cog.setWeakness(cogMap.get("weakness").toString());
            for(HashMap<String, Object> street : streetList){
                if(street.get("cog_id").toString().equalsIgnoreCase(cogMap.get("cog_id").toString())){
                    cog.addStreet(street.get("street").toString());
                }
            }
            cogs.add(cog);
        }
    }

    public Cog getCog(String name){
        for(Cog cog : cogs){
            if(cog.getName().equalsIgnoreCase(name)) return cog;
        }
        return null;
    }

    public EmbedBuilder getCogMessage(String name){
        if(getCog(name) != null){
            Cog cog = getCog(name);
            EmbedBuilder builder = new EmbedBuilder();
            String url = "https://toon.jakebooy.me/img/" + cog.getDepartment() + ".jpg";
            builder.setColor(jFramework.getUtil().getColor(33, 150, 243));
            builder.setAuthor(cog.getName(), url, url);
            builder.setThumbnail("https://toon.jakebooy.me/img/cogs/" + cog.getDepartment() + "/" + cog.getName().replace(" ", "%20") + ".png");
            StringJoiner join = new StringJoiner("\n");
            for(String street : cog.getStreets()){
                join.add(street);
            }
            builder.addField("Min Level", cog.getMinLevel() + "", true);
            builder.addField("Max Level", cog.getMaxLevel() + "", true);
            builder.addField("Streets", join.toString(), true);
            builder.addField("Weakness", cog.getWeakness(), true);
            return builder;
        }else return null;
    }

}
