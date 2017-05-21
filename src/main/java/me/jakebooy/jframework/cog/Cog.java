package me.jakebooy.jframework.cog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake Steen on 20/05/2017.
 */
public class Cog {

    private String name, department, weakness;
    private List<String> streets = new ArrayList<>();
    private int minLevel, maxLevel;

    public Cog(String name, String department){
        this.name = name;
        this.department = department;
    }

    public void addStreet(String name){
        streets.add(name);
    }

    public List<String> getStreets(){
        return streets;
    }

    public String getName(){
        return name;
    }

    public String getDepartment(){
        return department;
    }

    public void setMinLevel(int level){
        this.minLevel = level;
    }

    public void setMaxLevel(int level){
        this.maxLevel = level;
    }

    public int getMinLevel(){
        return minLevel;
    }

    public int getMaxLevel(){
        return maxLevel;
    }

    public void setWeakness(String weakness){
        this.weakness = weakness;
    }

    public String getWeakness(){
        return weakness;
    }



}
