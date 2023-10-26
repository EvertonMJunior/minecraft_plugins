package me.everton.esc;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {
	@EventHandler
	public void aoFalar(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();

		if (Cmd.chat.contains(p.getName())) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if (on.hasPermission(Main.config.getString("Permissao"))) {
					on.sendMessage(Main.config.getString("Formato")
							.replace("%NICK%", p.getDisplayName())
							.replace("%MSG%", msg).replace("&", "§"));
				}
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (Cmd.chat.contains(p.getName())) {
			Cmd.chat.remove(p.getName());
		}
	}

	@EventHandler
	public void reloadConfig(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (e.getMessage().equalsIgnoreCase("/esc reload")) {
			if(p.hasPermission("esc.reload")){
			Main.config = YamlConfiguration.loadConfiguration(new File(
					Main.plugin.getDataFolder(), "config.yml"));
			p.sendMessage("§7[§ceSc§7] Configuracao Recarregada!");
			} else {
				p.sendMessage(Main.config.getString("Sem_Permissao").replace("&", "§"));
			}
			e.setCancelled(true);
		}
	}
}
