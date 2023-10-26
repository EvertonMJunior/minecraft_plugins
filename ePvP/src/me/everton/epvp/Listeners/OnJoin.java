package me.everton.epvp.Listeners;

import me.confuser.barapi.BarAPI;
import me.everton.epvp.Main;
import me.everton.epvp.ServerScoreboard;
import me.everton.epvp.Title;
import me.everton.epvp.Comandos.Admin;
import me.everton.epvp.Comandos.Specs;
import me.everton.epvp.Comandos.Tag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;


public class OnJoin implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void Entrar(final PlayerJoinEvent e) throws IllegalStateException, Exception {
		Player p = e.getPlayer();	
		p.setScoreboard(Bukkit.getScoreboardManager()
				.getMainScoreboard());
		ServerScoreboard.criarScoreboard(p);
		
		p.setGameMode(GameMode.SURVIVAL);
		p.setWalkSpeed(0.2F);
		p.setFlySpeed(0.2F);
		
		Main.spawnItens(p);
		p.teleport(Main.loc("spawn", p));
		if(Title.getProtocolVersion(p) < 47) {
			BarAPI.setMessage(p, "§3Seja bem-vindo ao §6§lWoC§r§3!", 3);
		}
		Title t = new Title(Main.nomeservidor, "Bem-Vindo!", 1, 3, 1);
		t.setTitleColor(ChatColor.GOLD);
		t.setSubtitleColor(ChatColor.BLUE);
		t.setTimingsToSeconds();
		t.send(p);
		Tag.setTag(p);

				for (Player on : Bukkit.getOnlinePlayers()) {
					if(p.hasPermission("pvp.admin")){
						p.showPlayer(on);
						Specs.vendospecs.add(p.getName());
					} else {
						on.sendMessage("§7[§a+§7] " + p.getDisplayName());
						if(Admin.vis.contains(on.getName())){
							p.hidePlayer(on);
						}
					}
				}
		e.setJoinMessage(null);
	}
	
	@EventHandler
	public void aoMotd(ServerListPingEvent e){
		e.setMaxPlayers(0);
		e.setMotd(Main.settings.getConfig().getString("Motd1").replace("&", "§").replace("%SERVIDOR", Main.nomeservidor).replace("%SITE%", Main.site).replace("%LOJA%", Main.loja) + " \n" + Main.settings.getConfig().getString("Motd2").replace("&", "§").replace("%SERVIDOR", Main.nomeservidor).replace("%SITE%", Main.site).replace("%LOJA%", Main.loja));
	}
	
	@EventHandler
	public void preLogin(PlayerLoginEvent e) {
		if(Main.settings.getConfig().getBoolean("Manutencao")) {
			e.disallow(Result.KICK_OTHER, Main.settings.getConfig().getString("Motivo-Manutencao"));
		}
	}
}
