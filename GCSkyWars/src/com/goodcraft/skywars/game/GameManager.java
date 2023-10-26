package com.goodcraft.skywars.game;

import com.goodcraft.api.Utils;
import java.util.ArrayList;
import java.util.UUID;

public class GameManager {
    private int TIME;
    private GameState gs;
    
    private ArrayList<UUID> playing = new ArrayList<>();
    private ArrayList<UUID> spectating = new ArrayList<>();
    
    public void setState(GameState gs){
        this.gs = gs;
    }
    
    public GameState getState(){
        return this.gs;
    }
    
    public void addTime(){
        TIME++;
    }
    
    public int getTime(){
        return TIME;
    }
    
    public String getFormatedTime(){
        return Utils.secondsToString(TIME);
    }
    
    public int getPlayingCount(){
        return playing.size();
    }
}
