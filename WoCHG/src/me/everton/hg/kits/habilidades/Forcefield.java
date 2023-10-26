package me.everton.hg.kits.habilidades;

import java.util.HashMap;

import me.everton.hg.API;
import me.everton.hg.Main;
import me.everton.hg.kits.KitManager;
import me.everton.hg.kits.KitType;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.player.PlayerInteractEvent;

public class Forcefield implements Listener{
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();

	@EventHandler
	public void deshFire(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.FORCEFIELD) {
			return;
		}

		if (!e.getAction().name().contains("RIGHT")) {
			return;
		}

		if (!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		
		e.setCancelled(true);
		e.setUseInteractedBlock(Result.DENY);
		p.updateInventory();

		if (!Main.STATE_KITS) {
			p.sendMessage("§7[§c!§7] Os kits estao desativados!");
			return;
		}
		
		if(Main.INVENCIBILIDADE) {
			p.sendMessage("§7[§c!§7] Kit desativado durante a invencibilidade!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + Main.secToMinSec(cd.get(p.getName())) + " §7!");
			return;
		}	
		
		API.startCd(p, 60, 0, task, cd, true, "§7[§c!§7] O §c§lcooldown §7acabou!");
		p.sendMessage("§7[§c!§7] Forcefield ativo!");
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
		
		for(int i = 0; i < 20; i++) {
			final int no = i;
			Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					for(Entity ent : p.getNearbyEntities(5D, 5D, 5D)) {
						if(ent instanceof Player) {
							Player pn = (Player) ent;
							pn.damage(API.getDamage(p.getItemInHand()) - (API.getDamage(p.getItemInHand()) / 2));
							if(no % 5 == 0) {
								pn.sendMessage("§cVocê está sob efeito de um Forcefield! Fuja!");
							}
						}
					}
					
					if(no == 19) {
						p.sendMessage("§7[§c!§7] Forcefield desativado!");
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
					}
				}
			}, i * 5L);
		}
	}

}
