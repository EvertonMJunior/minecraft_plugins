package net.goodcraft.hardcoregames.eventos;

import net.goodcraft.hardcoregames.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GamerRelog implements Listener {

    public static HashMap<UUID, ItemStack[]> relogingInv = new HashMap<>();
    public static HashMap<UUID, Location> relogingLoc = new HashMap<>();
    public static ArrayList<UUID> reloging = new ArrayList<>();

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (Main.estado == GameState.PREGAME) return;
        event(p);
    }

    @EventHandler
    public void kick(PlayerKickEvent e) {
        final Player p = e.getPlayer();
        if (Main.estado == GameState.PREGAME) return;
        event(p);
    }


    private void event(Player p) {
        if (Main.players.contains(p.getUniqueId())) {
            if (GamerDamage.combatLog.containsKey(p.getUniqueId())) {
                if (p.isOnline()) {
                    p.damage(300D, p);
                    for (ItemStack items : p.getInventory().getContents()) {
                        if (items != null) {
                            if (p.getLocation() != null) {
                                p.getLocation().getWorld().dropItemNaturally(p.getLocation(), items);
                            }
                        }
                    }
                }
                Main.players.remove(p.getUniqueId());
                Bukkit.broadcastMessage("§e" + p.getName() + " deslogou em combate!");
                return;
            }
            ItemStack[] items = p.getInventory().getContents();
            relogingInv.put(p.getUniqueId(), items);
            relogingLoc.put(p.getUniqueId(), p.getLocation());
            reloging.add(p.getUniqueId());
            new BukkitRunnable() {
                int tempo = 0;

                public void run() {
                    if (p.isOnline()) {
                        cancel();
                        relogingInv.remove(p.getUniqueId());
                        relogingLoc.remove(p.getUniqueId());
                        reloging.remove(p.getUniqueId());
                        return;
                    } else if (tempo == 30) {
                        GamerDeath.broadCastDeath(p, "demorou muito para entrar novamente");
                        Main.players.remove(p.getUniqueId());
                        Location loc = null;
                        if (relogingLoc.containsKey(p.getUniqueId())) {
                            loc = relogingLoc.get(p.getUniqueId());
                        }
                        if (relogingInv.containsKey(p.getUniqueId())) {
                            for (ItemStack items : relogingInv.get(p.getUniqueId())) {
                                if (items != null) {
                                    if (loc != null) {
                                        loc.getWorld().dropItemNaturally(loc, items);
                                    }
                                }
                            }
                        }
                        reloging.remove(p.getUniqueId());
                        relogingInv.remove(p.getUniqueId());
                        relogingLoc.remove(p.getUniqueId());
                        cancel();
                        return;
                    }
                    tempo++;
                }
            }.runTaskTimer(Main.getMain(), 20, 20);
        }
    }
}
