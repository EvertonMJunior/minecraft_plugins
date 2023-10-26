package me.everton.pvp.listeners;

import me.everton.pvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class Motd implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPing(ServerListPingEvent e) {
		e.setMaxPlayers(Bukkit.getOnlinePlayers().length);
		e.setMotd(Main.getPlugin().getConfig().getString("Motd1")
				.replace("&", "§")
				+ "\n"
				+ Main.getPlugin().getConfig().getString("Motd2")
						.replace("&", "§"));
	}
}
