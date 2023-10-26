package com.goodcraft.eventos;

import com.goodcraft.GameMode;
import com.goodcraft.Main;
import com.goodcraft.api.Header;
import com.goodcraft.sql.MySQL;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        new BukkitRunnable() {
            @Override
            public void run() {
                MySQL.addPlayerToTable(id, "good_global", new String[]{
                    "100",
                    String.valueOf(System.currentTimeMillis()),
                    "",
                    ""
                }, true);
                MySQL.addPlayerToTable(id, "good_lobby", new String[]{
                    "[]"
                }, false);
                
                for (GameMode gm : GameMode.values()) {
                    if (gm == GameMode.LOBBY) {
                        continue;
                    }

                    if (!gm.isActive()) {
                        continue;
                    }
                    ArrayList<String> status = new ArrayList<>();
                    for (int i = 0; i < gm.getStatus().length; i++) {
                        status.add("0");
                    }
                    
                    if (gm == GameMode.HARDCOREGAMES) {
                        status.add("0");
                        status.add("0");
                        status.add("0");
                    }
                    MySQL.addPlayerToTable(id, "good_" + gm.name().toLowerCase(), status.toArray(new String[]{}), false);
                }

            }
        }.runTaskAsynchronously(Main.getPlugin());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(null);
        Header.set(p, "\n"
                + "§3§lGoodCraft\n"
                + "§fwww.good-craft.net\n",
                "\n    "
                + "§fAdquira VIP acessando:§7 loja.good-craft.net    "
                + "\n");
    }
}
