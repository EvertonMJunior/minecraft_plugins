package net.goodcraft.eventos;

import net.goodcraft.GameMode;
import net.goodcraft.Main;
import net.goodcraft.api.AdminAPI;
import net.goodcraft.api.Rank;
import net.goodcraft.sql.MySQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        new BukkitRunnable() {
            @Override
            public void run() {
                MySQL.addPlayerToTable(id, "good_lobby", new String[]{
                        "pets"
                }, new String[]{
                        "[]"
                }, false);

                for (GameMode gm : GameMode.values()) {
                    if (gm == GameMode.LOBBY) {
                        continue;
                    }

                    if (!gm.isActive()) {
                        continue;
                    }
                    HashMap<String, String> status = new HashMap<>();
                    for (int i = 0; i < gm.getStatus().length; i++) {
                        status.put(gm.getStatus()[i].getName().toLowerCase(), "0");
                    }

                    if (gm == GameMode.HARDCOREGAMES) {
                        status.put("g1", "0");
                        status.put("g2", "0");
                        status.put("g3", "0");
                    }
                    if (gm != GameMode.LOBBY) {
                        status.put("kits", "[]");
                    }
                    Set<String> names = status.keySet();

                    MySQL.addPlayerToTable(id, "good_" + gm.name().toLowerCase(), names.toArray(new String[names.size()]),
                            status.values().toArray((new String[]{})), false);
                }

            }
        }.runTaskAsynchronously(Main.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        p.spigot().setCollidesWithEntities(true);

        e.setJoinMessage(null);
        for (AdminAPI a : AdminAPI.admins.values()) {
            Player adm = a.getPlayer();
            if (!adm.isOnline()) continue;
            if (Rank.has(Rank.getPlayerRank(p.getUniqueId()), Rank.MOD)) continue;
            p.hidePlayer(adm);
        }
    }
}
