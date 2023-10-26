package net.goodcraft.lobby.eventos;

import net.goodcraft.api.AdminAPI;
import net.goodcraft.api.Rank;
import net.goodcraft.lobby.Main;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class DoubleJumpEvent implements Listener {

    public static ArrayList<String> flyOff = new ArrayList<>();

    @EventHandler
    public void moveDoubleJump(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (p.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (AdminAPI.admins.containsKey(p.getName())) {
            return;
        }

        if (p.isFlying()) {
            for (int i = 0; i < 3; i++) {
                p.getWorld().playEffect(p.getLocation().clone().add(0, 1, 0), Effect.WITCH_MAGIC, 1);
            }
            return;
        }

        if (p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
            return;
        }

        p.setAllowFlight(true);
    }

    @EventHandler
    public void toggleFlightDoubleJump(PlayerToggleFlightEvent e) {
        final Player p = e.getPlayer();

        if (p.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (ArenaKnockEvent.onArenaKnock.contains(p.getName())) {
            e.setCancelled(true);
            p.setFlying(false);
            p.setAllowFlight(false);
            return;
        }

        if (Rank.has(p.getUniqueId(), Rank.BRONZE)) {
            if (e.isFlying()) {
                p.getWorld().playEffect(p.getLocation().clone().subtract(0, 1, 0), Effect.ENDER_SIGNAL, 1);
            }

            if (!flyOff.contains(p.getName())) {
                e.setCancelled(false);
                p.setAllowFlight(true);
                return;
            }
        }
        e.setCancelled(true);

        p.setFlying(false);
        p.setVelocity(p.getLocation().getDirection().multiply(1.5D).setY(0.5D));

        for (int i = 0; i < 20; i++) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.getWorld().playEffect(p.getLocation(), Effect.MAGIC_CRIT, 1);
                }
            }.runTaskLater(Main.getPlugin(), i);
        }
        p.playSound(p.getLocation(), Sound.CREEPER_HISS, 1F, 1F);
    }
}
