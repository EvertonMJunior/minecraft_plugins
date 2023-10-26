package net.goodcraft.eventos;

import net.goodcraft.api.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = p.getDisplayName() + "§7:§f " + e.getMessage();

        if (Bukkit.getPluginManager().getPlugin("GCLobby") != null) {
            //lobbys
            return;
        }
        Utils.broadcast(message);
        e.setCancelled(true);
    }
}
