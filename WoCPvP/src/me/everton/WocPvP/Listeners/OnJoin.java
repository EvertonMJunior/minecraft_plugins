package me.everton.WocPvP.Listeners;

import me.everton.WocPvP.Main;
import me.everton.WocPvP.ServerScoreboard;
import me.everton.WocPvP.Comandos.Admin;
import me.everton.WocPvP.Comandos.Fake;
import me.everton.WocPvP.Comandos.Specs;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class OnJoin implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void Entrar(final PlayerJoinEvent e) {
		final Player p = e.getPlayer();

		p.setGameMode(GameMode.SURVIVAL);

		Main.sh.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				Main.spawnItens(p);

				if(!Main.settings.getConfig().getStringList("staff").contains(p.getName().toLowerCase())){
					p.kickPlayer("§7[§c§lWoCPvP§7] \n§3§lO WoC mudou de ip! \n§3§lPara acessar o WoC, use §ojogar.wocpvp.com §3§l! \nAgora nao precisa mais de portas! \nIsso é graças a vocês que ajudaram o servidor doando valores em dinheiro!");
				}
				
				
				
				for (final Player on : Bukkit.getOnlinePlayers()) {

					if (p.hasPermission("wocpvp.admin")) {
						e.setJoinMessage(null);
						if (on.hasPermission("wocpvp.admin")) {
							on.sendMessage("§7[§a+§7] " + p.getDisplayName());
						}
						p.showPlayer(on);
						Specs.vendospecs.add(p);
						e.setJoinMessage(null);
						return;
					} else {
						
						on.sendMessage("§7[§a+§7] " + p.getDisplayName());
					}
					if (on.hasPermission("wocpvp.admin")) {
						if (Admin.vis.contains(on)) {
							p.hidePlayer(on);
							return;
						}
						p.showPlayer(on);
					}
				}
			}
		}, 10L);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin,
				new Runnable() {
					public void run() {
						p.setScoreboard(Bukkit.getScoreboardManager()
								.getMainScoreboard());
						ServerScoreboard.criarScoreboard(p);
					}
				}, 5L);
		e.setJoinMessage(null);

		Fake.fakes.remove(p);
		Fake.fakesplay.remove(p);
		Fake.fakestag.remove(p);
	}
	
	@EventHandler
	public void aoMotd(ServerListPingEvent e){
		e.setMaxPlayers(0);
		e.setMotd("                §7[§c§lWoCPvP§7]\n§3§lTemos noticias! Entre e veja!");
	}

}
