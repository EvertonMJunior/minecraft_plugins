package net.goodcraft.minigames.eventos;

import net.goodcraft.Main;
import net.goodcraft.api.Message;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.game.GameState;
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

public class RelogEvent implements Listener {

    public static HashMap<UUID, ItemStack[]> relogingInv = new HashMap<>();
    public static HashMap<UUID, Location> relogingLoc = new HashMap<>();
    public static ArrayList<UUID> reloging = new ArrayList<>();

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (Main.minigame.getGameState() == GameState.PREGAME) return;
        event(p);
    }

    @EventHandler
    public void kick(PlayerKickEvent e) {
        final Player p = e.getPlayer();
        if (Main.minigame.getGameState() == GameState.PREGAME) return;
        event(p);
    }


    private void event(Player p) {
        Minigame mg = Main.minigame;

        if (mg.isPlayer(p.getUniqueId())) {
            if (DamageEvent.combatLog.containsKey(p.getUniqueId()) && !mg.hasOption(Option.CAN_DISCONNECT_IN_COMBAT)) {
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
                Main.minigame.getPlayers().remove(p.getUniqueId());
                Message.INFO.broadcast("§6[" + mg.getName() + "]§e " + p.getName() + " deslogou em combate!" +
                        " (" + mg.getCurrentPlayers() + "/" + mg.getMaximumPlayers() + ")");
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
                        Message.INFO.broadcast("§6[" + mg.getName() + "]§e " + p.getName() + " demorou muito para entrar de novo!" +
                                " (" + mg.getCurrentPlayers() + "/" + mg.getMaximumPlayers() + ")");
                        Main.minigame.getPlayers().remove(p.getUniqueId());
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
            }.runTaskTimer(Main.minigame.getPlugin(), 20, 20);
        }
    }
}
