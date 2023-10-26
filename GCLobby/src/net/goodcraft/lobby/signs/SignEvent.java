package net.goodcraft.lobby.signs;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignEvent implements Listener {
    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();

        if (!e.getAction().name().contains("RIGHT")) return;
        if (b == null) return;
        if (!b.getType().name().contains("SIGN")) return;

        GameSign gameSign = GameSign.getByLocation(b.getLocation());

        if (gameSign == null) return;

        gameSign.sendToMostFullServer(p);
    }
}
