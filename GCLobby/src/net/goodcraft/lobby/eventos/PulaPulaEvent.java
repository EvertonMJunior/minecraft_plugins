package net.goodcraft.lobby.eventos;

import net.goodcraft.lobby.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PulaPulaEvent implements Listener {

    public static HashMap<String, Double> jumps = new HashMap<>();

    @EventHandler
    public void onJump(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block b = e.getFrom().subtract(0, 1, 0).getBlock();

        if (!(e.getTo().getY() > e.getFrom().getY())) {
            return;
        }

        if (b.getType() != Material.SLIME_BLOCK) {
            if (b.getType() != Material.AIR) {
                if (jumps.containsKey(p.getName())) {
                    jumps.remove(p.getName());
                }
            }
            return;
        }
        double multiplier;

        if (jumps.containsKey(p.getName())) {
            double playerJumps = jumps.get(p.getName());
            jumps.remove(p.getName());
            jumps.put(p.getName(), playerJumps + 0.1);
            multiplier = playerJumps + 0.1;
        } else {
            jumps.put(p.getName(), 1.5);
            multiplier = 2;
        }

        p.setVelocity(new Vector(0, multiplier * 0.1, 0));
        p.setSneaking(true);
        new BukkitRunnable(){
            @Override
            public void run() {
                p.setSneaking(false);
            }
        }.runTaskLater(Main.getPlugin(), 10L);
    }
}
