package me.dark.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.dark.Main;


public class GamerChat implements Listener {

	
	@EventHandler
	public void digitar(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		if (event.getMessage().startsWith("/me ")
				|| event.getMessage().startsWith("/bukkit:me ")) {
			event.setCancelled(true);
		}
		if (event.getMessage().startsWith("/help") || event.getMessage().startsWith("/? ")) {
			event.setCancelled(true);
			p.chat("/jogo");
		}
	}
	@EventHandler
	public void as(final AsyncPlayerChatEvent e) {
		if (e.getPlayer().hasPermission(Main.perm_mod)) {
			e.setFormat(e.getPlayer().getDisplayName() + " " + Main.seta + " "+e.getMessage());
			return;
		} else if (e.getPlayer().hasPermission(Main.perm_prata)) {
			e.setFormat(e.getPlayer().getDisplayName() + " " + Main.seta + " "+e.getMessage());
			return;
		} else {
			e.setFormat(e.getPlayer().getDisplayName() + " " + Main.seta + " §7"+e.getMessage());
		}
	}
	

}
