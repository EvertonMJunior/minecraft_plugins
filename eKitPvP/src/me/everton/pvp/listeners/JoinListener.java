package me.everton.pvp.listeners;

import me.everton.pvp.API;
import me.everton.pvp.LocationsManager;
import me.everton.pvp.OptionsManager;
import me.everton.pvp.ScoreManager;
import me.everton.pvp.ScoreboardManager;
import me.everton.pvp.SpawnProtection;
import me.everton.pvp.cmds.Admin;
import me.everton.pvp.e1v1.bot.Bot;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.tags.TagCmd;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		try {
			for(NPC npc : Bot.hided) {
				try {
					Player np = (Player) npc.getBukkitEntity();
					if(np == null) {
						Bot.hided.remove(npc);
					} else {
						p.hidePlayer(np);
					}
				} catch(Exception ex) {
					
				}
			}
		}catch(Exception ex) {}
		TagCmd.setTag(p);
		ScoreManager.registerData(p);
		OptionsManager.registerData(p);
		API.itensIniciais(p);
		p.updateInventory();
		KitManager.resetKit(p);
		if(p.hasPermission("pvp.cmd.admin")) {
			Admin.entrarOuSair(p);
			for(Player on : Bukkit.getOnlinePlayers()) {
				p.showPlayer(on);
				if(on.hasPermission("pvp.staff")) {
					on.sendMessage("§7[§a+§7] " + p.getDisplayName());
				}
			}
		} else {
			for(Player on : Bukkit.getOnlinePlayers()) {
				on.sendMessage("§7[§a+§7] " + p.getDisplayName());
			}
		}
		
		for(String adm : Admin.admins) {
			if(Bukkit.getPlayerExact(adm) != null) {
				Player ad = Bukkit.getPlayerExact(adm);
				p.hidePlayer(ad);
			}
		}
		for(Player on : Bukkit.getOnlinePlayers()) {
			ScoreboardManager.refreshSidebar(on);
		}
		e.setJoinMessage(null);
		API.sendTitle(p, "§6§lTiger§f§lPvP", "Seja Bem-Vindo", 1, 2, 1);
		p.teleport(LocationsManager.getSpawn());
		SpawnProtection.addProtection(p, true);
		TagCmd.updatePrefixes(p);
	}	
}
