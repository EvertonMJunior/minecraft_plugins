package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Terrorista implements Listener{
	public static ArrayList<String> jumps = new ArrayList<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();
	
	@EventHandler
	public void lancar(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT")) {
			if(KitManager.getKit(p) == KitType.TERRORISTA) {
				if(KitManager.isWithKitItemInHand(p)) {
					if(!jumps.contains(p.getName())) {
						
						if(!API.getGamers().contains(p)) {
							p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
							return;
						}
						
						if (cd.containsKey(p.getName())) {
							p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
									+ cd.get(p.getName()) + " segundos§7!");
							return;
						}
						p.setVelocity(new Vector(p.getVelocity().getX(), 3.0D, p.getVelocity().getZ()));
						jumps.add(p.getName());
						API.startCd(p, 30, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
						for(int i = 1; i < 6; i++) {
							Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								
								@SuppressWarnings("deprecation")
								@Override
								public void run() {
									if(p.isOnGround()) {
										if(jumps.contains(p.getName())) {
											jumps.remove(p.getName());
										}
									}
								}
							}, i * 20L);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void explosions(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			final Player p = (Player) e.getEntity();
			if(KitManager.getKit(p) == KitType.TERRORISTA) {
				if((e.getCause() == DamageCause.BLOCK_EXPLOSION) || (e.getCause() == DamageCause.ENTITY_EXPLOSION)) {
					e.setCancelled(true);
				} else if(e.getCause() == DamageCause.FALL) {
					e.setDamage(2.0D);
						if(jumps.contains(p.getName())) {
							jumps.remove(p.getName());
							e.setCancelled(true);
						}
						p.getLocation().getWorld().createExplosion(p.getLocation(), 3.5F, true);
						Bukkit.getServer().getScheduler()
						.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
							public void run() {
								p.setVelocity(new Vector());
							}
						}, 1L);
						for(Entity en : p.getNearbyEntities(2D, 2D, 2D)) {
							if(en instanceof Player) {
								Player pn = (Player) en;
								if(API.getGamers().contains(pn)) {
									pn.sendMessage("§7[§c!§7] Você foi atingido por um atentado terrorista de " + ChatColor.stripColor(p.getPlayerListName()) + "!");
									pn.damage(10.0D, p);
								}
							}
					}
				}
			}
		}
	}
}
