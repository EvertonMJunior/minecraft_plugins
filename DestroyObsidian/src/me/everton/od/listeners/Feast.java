package me.everton.od.listeners;

import me.everton.od.API;
import me.everton.od.API.GameStage;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Feast implements Listener {
	private static Location trovao = new Location(Bukkit.getWorld("world"),
			-326.5, 121.5, -109.5);
	private static boolean feastAtingido = false;

	@EventHandler
	public void checkOnFeast(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Bukkit.getWorld("world").setTime(5000);
		if (p.getGameMode() == GameMode.SURVIVAL) {
			if(API.getGameStage() == GameStage.PRE_JOGO) {
				return;
			}
			if (!feastAtingido) {
				if (API.isFeast(p)) {
					trovao.getWorld().strikeLightning(
							trovao.clone().add(0, 5, 0));
					Bukkit.broadcastMessage(p.getDisplayName()
							+ " §bfoi o primeiro à chegar no §lFeast§l!");
					feastAtingido = true;
				}
			}
		}
	}
}
