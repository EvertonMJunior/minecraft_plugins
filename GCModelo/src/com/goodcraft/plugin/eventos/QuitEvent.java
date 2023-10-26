package com.goodcraft.plugin.eventos;

import com.goodcraft.plugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class QuitEvent implements Listener{
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        Main.getScoreboard().removeBoard(p);
    }
}
