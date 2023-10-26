package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.SpawnProtection;
import me.everton.pvp.cmds.Admin;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Forcefield implements Listener {
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();

	@EventHandler
	public void forceField(PlayerInteractEvent e) {
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
		
		if(!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}

		if (cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
					+ cd.get(p.getName()) + " segundos§7!");
			return;
		}

		API.startCd(p, 60, 0, cd, "§7[§c!§7] O cooldown acabou!");
		p.sendMessage("§7[§c!§7] Forcefield ativo!");
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);

		new BukkitRunnable() {
			int no = 21;
			final KitType pKit = KitManager.getKit(p);
			@Override
			public void run() {
				no--;
				
				if(pKit != KitManager.getKit(p)) {
					cancel();
				}
				
				for (Entity ent : p.getNearbyEntities(5D, 5D, 5D)) {
					if (ent instanceof Player) {
						Player pn = (Player) ent;
						if (SpawnProtection.hasProtection(pn)) {
							return;
						}

						if (Admin.admins.contains(pn.getName())) {
							return;
						}
						pn.damage(API.getDamage(p));
						if (no % 5 == 0) {
							pn.sendMessage("§cVocê está sob efeito de um Forcefield! Fuja!");
						}
					}
				}

				if (no == 20) {
					p.sendMessage("§7[§c!§7] Forcefield desativado!");
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F,
							15.5F);
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 5L, 5L);
	}

}
