package me.everton.pvp.kits.habilidades;

import me.everton.pvp.API;
import me.everton.pvp.SpawnProtection;
import me.everton.pvp.cmds.Admin;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Archer implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onArrow(EntityDamageByEntityEvent e) {
		if ((e.getDamager() instanceof Arrow)
				&& (e.getEntity() instanceof Player)) {
			Arrow a = (Arrow) e.getDamager();
			if (!(a.getShooter() instanceof Player)) {
				return;
			}

			Player s = (Player) a.getShooter();
			Player p = (Player) e.getEntity();
			if (s.getName().equalsIgnoreCase(p.getName())) {
				e.setCancelled(true);
				return;
			}
			if(e.isCancelled()) {
				return;
			}
			
			if(!API.getGamers().contains(p)) {
				e.setCancelled(true);
				s.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
				return;
			}
			
			if(!API.getGamers().contains(s)) {
				e.setCancelled(true);
				s.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
				return;
			}

			if (KitManager.getKit(s) == KitType.ARCHER) {
				if (SpawnProtection.hasProtection(p)) {
					return;
				}

				if (Admin.admins.contains(p.getName())) {
					return;
				}
				if (p.getLocation().distance(s.getLocation()) >= 30D) {
					e.setDamage(80.0D);
					p.sendMessage("§7[§c!§7] Você levou um headshot de "
							+ ChatColor.stripColor(s.getName()) + "!");
					s.sendMessage("§7[§c!§7] Você deu um headshot em "
							+ ChatColor.stripColor(p.getName()) + "!");
				} else {
					p.sendMessage("§7[§c!§7] Você foi atingido por uma flecha de "
							+ ChatColor.stripColor(s.getName()) + "!");
					s.sendMessage("§7[§c!§7] Você acertou uma flecha em "
							+ ChatColor.stripColor(p.getName())
							+ "! Fique a 30 blocos ou mais dele e mate-o em um headshot!");
					e.setDamage(4.0D);
				}
			}
		}
	}
}
