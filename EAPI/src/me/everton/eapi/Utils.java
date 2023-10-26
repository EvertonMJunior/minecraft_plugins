package me.everton.eapi;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Utils {

    public static String getXYZ(Location fromToGet) {
        String xyz = "X" + fromToGet.getX() + ", Y" + fromToGet.getY() + ", Z" + fromToGet.getZ();
        return xyz;
    }

    public static String getSentence(String[] args) {
        String sentence = "";
        for (int i = 0; i < args.length; i++) {
            sentence += args[i] + (i == (args.length - 1) ? "" : " ");
        }
        return sentence;
    }
    
    public static String getSentence(String[] args, int inicial) {
        String sentence = "";
        for (int i = inicial; i < args.length; i++) {
            sentence += args[i] + (i == (args.length - 1) ? "" : " ");
        }
        return sentence;
    }

    public static void broadcast(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }
    
    public static void broadcast(String message, String permission) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(!p.hasPermission(permission)){
                continue;
            }
            p.sendMessage(message);
        }
    }
    
    public static Player getPlayerByUUID(UUID uuid){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getUniqueId().equals(uuid)){
                return p;
            }
        }
        
        return null;
    }
}
