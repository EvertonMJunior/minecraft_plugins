package net.goodcraft.eventos;

import net.goodcraft.api.AdminAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class QuitEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);

        Player p = e.getPlayer();
        AdminAPI.admins.remove(p.getName());
    }
}
