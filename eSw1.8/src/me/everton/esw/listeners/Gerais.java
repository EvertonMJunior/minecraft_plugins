package me.everton.esw.listeners;

import me.everton.esw.API;
import me.everton.esw.commands.Tag;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Gerais implements Listener{
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		API.sendTitle(p, "§3§l[Skywars]", "§6Você entrou na partida!");
		API.setHeaderFooter(p, "§6WoC§bNetwork", "§9Você está no Skywar!");
		e.setJoinMessage("§3[SkyWars] §6" + ChatColor.stripColor(p.getDisplayName()) + " se juntou à partida!");
		Tag.setTag(p);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("sw.admin")) {
			e.setFormat(p.getDisplayName() + " §6§l>> §f" + e.getMessage().replace("&", "§"));
		} else if(p.hasPermission("sw.chatcolor")){
			e.setFormat(p.getDisplayName() + " §6§l>> §7" + e.getMessage().replace("&", "§").replace("§f", "§7"));
		} else {
			e.setFormat(p.getDisplayName() + " §6§l>> §f" + e.getMessage());
		}
	}
}
