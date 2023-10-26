package me.everton.epvp;

import java.util.ArrayList;

import me.everton.epvp.Comandos.Admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CombatLog implements Listener {
	public static ArrayList<String> cl = new ArrayList<>();

	@EventHandler
	public void entrarCombate(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player))
				&& ((e.getDamager() instanceof Player))) {
			final Player p = (Player) e.getEntity();
			final Player damager = (Player) e.getDamager();
			if ((!Admin.vis.contains(p)) && (!Admin.vis.contains(damager))
					&& (Main.areaPvP(p)) && (Main.areaPvP(damager))) {
				if (!cl.contains(p.getName())) {
					cl.add(p.getName());
					p.sendMessage("§bVocê entrou em combate com: "
							+ damager.getDisplayName() + "§b!");
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin,
							new Runnable() {
								public void run() {
									if (CombatLog.cl.contains(p.getName())) {
										CombatLog.cl.remove(p.getName());
										if (p != null) {
											p.sendMessage("§bVocê nao esta mais em combate!");
										}
									}
								}
							}, 200L);
				}
				if (!cl.contains(damager.getName())) {
					cl.add(damager.getName());
					damager.sendMessage("§bVocê entrou em combate com: "
							+ p.getDisplayName() + "§b!");
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin,
							new Runnable() {
								public void run() {
									if (CombatLog.cl.contains(damager.getName())) {
										CombatLog.cl.remove(damager.getName());
										if (damager != null) {
											damager.sendMessage("§bVocê nao esta mais em combate!");
										}
									}
								}
							}, 200L);
				}
			}
		}
	}

	@EventHandler
	public void reset(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (cl.contains(p.getName())) {
			cl.remove(p.getName());
		}
	}

	@EventHandler
	public void noTp(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		if (cl.contains(p.getName())) {
			e.setCancelled(true);
			p.sendMessage("§7[§c!§7] Você nao pode teleportar-se em combate!");
		}
	}
	
	@EventHandler
	public void noLeave(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(cl.contains(p.getName())){
			for(Player on : Bukkit.getOnlinePlayers()){
				on.sendMessage("§7[§c!§7] " + ChatColor.stripColor(p.getDisplayName()) + " deslogou em combate!");
			}
		}
	}
}
