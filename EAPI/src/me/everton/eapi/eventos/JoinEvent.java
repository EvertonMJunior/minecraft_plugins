package me.everton.eapi.eventos;

import me.everton.eapi.Main;
import me.everton.eapi.SQLUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                SQLUtils.register(e.getName(), e.getAddress().getHostName(), e.getUniqueId());
            }
        }.runTaskAsynchronously(Main.getPlugin());
    }
}
