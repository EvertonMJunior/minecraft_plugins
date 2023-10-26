package me.everton.jpvp.listeners;

import me.everton.jpvp.kits.KitManager;
import me.everton.jpvp.utils.API;
import me.everton.jpvp.utils.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoin implements Listener{
	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.sendMessage(UUID.get("Notch"));
		KitManager.chooseKit(p, KitManager.getKit("Stomper"));
	}
}
