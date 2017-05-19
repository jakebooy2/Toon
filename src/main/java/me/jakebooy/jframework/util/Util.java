package me.jakebooy.jframework.util;

import javafx.embed.swing.JFXPanel;
import me.jakebooy.jframework.JFramework;
import oracle.jrockit.jfr.JFR;

import java.awt.*;

/**
 * Created by jakebooy on 19/04/17.
 */
public class Util {

    private String prefix;

    private JFramework jFramework;
    public Util(JFramework jFramework){
        this.jFramework = jFramework;
        prefix = jFramework.getConfig().getString("prefix");

    }

    public String getPrefix(){
        return prefix;
    }

    public Color getColor(int red, int green, int blue){
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }
}
