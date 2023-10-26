package me.everton.hg.kits.habilidades;

import me.everton.hg.Main;
import me.everton.hg.kits.KitManager;
import me.everton.hg.kits.KitType;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class Stomper implements Listener {
	@EventHandler
	public void stompar(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(KitManager.getKit(p) != KitType.STOMPER && KitManager.getKit(p) != KitType.TOWER) {
				return;
			}
			
			if (e.getCause() != DamageCause.FALL) {
				return;
			}
			
			if(!Main.EM_JOGO) {
				return;
			}
			
			if(!Main.STATE_KITS) {
				p.sendMessage("§7[§c!§7] Os kits estao desativados!");
				return;
			}
			
			int playerCount = 0;
			
			for (Entity ent : p.getNearbyEntities(4D, 4D, 4D)) {
				if (ent instanceof Player) {
					Player en = (Player) ent;
					playerCount++;
					en.sendMessage("§7[§c!§7] Você foi stompado por §c§l" + ChatColor.stripColor(p.getPlayerListName()) + "§7!");
					if(en.isSneaking()) {
						en.damage(3.0D, p);
					} else {
						en.damage(e.getDamage(), p);
					}
				}
			}
			if(playerCount > 0) {
				p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 15.5F, 15.5F);
			}
			
			if(e.getDamage() >= 4.0D) {
				e.setDamage(4.0D);
			}
		}
	}
}
