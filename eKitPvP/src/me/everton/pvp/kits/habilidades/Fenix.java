package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.SpawnProtection;
import me.everton.pvp.cmds.Admin;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.CylinderEffect;

public class Fenix implements Listener{
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	
	public static HashMap<String, Integer> taskF = new HashMap<>();
	public static HashMap<String, Integer> cdF = new HashMap<>();

	@EventHandler
	public void fenix(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.FENIX) {
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
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + cd.get(p.getName()) + " segundos§7!");
			return;
		}	
		
		API.startCd(p, 20, 0, cd, "§7[§c!§7] O cooldown acabou!");
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
		p.setAllowFlight(true);
		p.setFlying(true);
		final Effect ef = new CylinderEffect(Main.getEM());
		ef.setLocation(p.getLocation().clone().subtract(0, 2, 0));
		ef.setEntity(p);
		ef.start();
		
		p.getInventory().setHelmet(
				API.leatherArmor(Material.LEATHER_HELMET, " ",
						Color.ORANGE));
		p.getInventory().setChestplate(
				API.leatherArmor(Material.LEATHER_CHESTPLATE, " ",
						Color.ORANGE));
		p.getInventory().setLeggings(
				API.leatherArmor(Material.LEATHER_LEGGINGS, " ",
						Color.ORANGE));
		p.getInventory().setBoots(
				API.leatherArmor(Material.LEATHER_BOOTS, " ",
						Color.ORANGE));
		
		p.updateInventory();
		
		new BukkitRunnable() {
			int time = 5;
			final KitType pKit = KitManager.getKit(p);
			@Override
			public void run() {
				if(time > 0) {
					
					if(pKit != KitManager.getKit(p)) {
						ef.cancel();
						p.setFlying(false);
						p.setAllowFlight(false);
						p.getInventory().setArmorContents(null);
						p.updateInventory();
						cancel();
						return;
					}
					
					String se = "s";
					if(time < 1) {
						se = "";
					}
					p.sendMessage("§eVocê possui mais §6" + time + " §esegundo" + se + " de voo!");
					time--;
				} else {
					ef.cancel();
					p.setFlying(false);
					p.setAllowFlight(false);
					p.getInventory().setArmorContents(null);
					p.updateInventory();
					p.sendMessage("§eVocê já nao pode voar mais!");
					cancel();
				}
				for(Entity en : p.getNearbyEntities(5.0D, 5.0D, 5.0D)) {
					if(en instanceof Player) {
						Player ent = (Player) en;
						if(SpawnProtection.hasProtection(ent)) {
							return;
						}
						
						if(Admin.admins.contains(ent.getName())) {
							return;
						}
					}
					en.setFireTicks(90);
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 20L);
	}
}
