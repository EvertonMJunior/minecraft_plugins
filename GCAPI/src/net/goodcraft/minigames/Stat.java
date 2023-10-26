package net.goodcraft.minigames;

import net.goodcraft.GameMode;
import net.goodcraft.sql.SQLStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Stat {
    public static ArrayList<Stat> stats = new ArrayList<>();

    public static Stat getByName(String name){
        for(Stat s : stats){
            if(s.getName().equalsIgnoreCase(name)){
                return s;
            }
        }
        return null;
    }

    public static Stat getByName(String name, Stat[] st){
        for(Stat s : st){
            if(s.getName().equalsIgnoreCase(name)){
                return s;
            }
        }
        return null;
    }

    private String name;
    private final HashMap<UUID, Integer> STATS = new HashMap<>();
    private GameMode GAMEMODE;
    private final Type TYPE;

    public Stat(String name, String gm, Type type) {
        this.name = name;
        this.TYPE = type;
        stats.add(this);
    }

    public Stat(String name, GameMode gamemode, Type type) {
        this.name = name;
        this.GAMEMODE = gamemode;
        this.TYPE = type;
        stats.add(this);
    }

    public String getName() {
        return name;
    }

    public void setGameMode(GameMode GAMEMODE) {
        this.GAMEMODE = GAMEMODE;
    }

    public void setStat(UUID id, Integer newValue){
        if(STATS.containsKey(id)){
            STATS.replace(id, newValue);
            return;
        }
        STATS.put(id, newValue);
    }

    public void addStat(UUID id){
        if(STATS.containsKey(id)){
            STATS.replace(id, STATS.get(id) + 1);
            SQLStatus.addStatus(id, GAMEMODE, name, 1);
            return;
        }
        STATS.put(id, 1);
        SQLStatus.addStatus(id, GAMEMODE, name, 1);
    }

    public void removeStat(UUID id){
        if(STATS.containsKey(id)){
            STATS.replace(id, STATS.get(id) - 1);
            SQLStatus.addStatus(id, GAMEMODE, name, -1);
            return;
        }
        STATS.put(id, 0);
        SQLStatus.addStatus(id, GAMEMODE, name, -1);
    }

    public Integer getStat(UUID id) {
        return STATS.getOrDefault(id, 0);
    }

    public GameMode getGameMode() {
        return GAMEMODE;
    }

    public Type getType() {
        return TYPE;
    }

    public enum Type{
        MAIOR, MENOR;
    }
}
