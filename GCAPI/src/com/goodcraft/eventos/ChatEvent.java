package com.goodcraft.eventos;

import com.goodcraft.api.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        
        if(false){
            //lobbys
            return;
        }
        
        Utils.broadcast(p.getDisplayName() + "§7:§f " + e.getMessage());
        
    }
}
