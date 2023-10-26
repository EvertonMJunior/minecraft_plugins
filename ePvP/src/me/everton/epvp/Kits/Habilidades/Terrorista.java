package me.everton.epvp.Kits.Habilidades;

import java.util.ArrayList;

import me.everton.epvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class Terrorista implements Listener{
	public static ArrayList<String> jumps = new ArrayList<>();
	public static ArrayList<String> cd = new ArrayList<>();
	
	@EventHandler
	public void lancar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT")) {
			if(Main.terrorista.contains(p.getName())) {
				if(p.getItemInHand().getType() == Material.MAGMA_CREAM) {
					if(!jumps.contains(p.getName())) {
						if(cd.contains(p.getName())) {
							p.sendMessage("§7[§c!§7] Kit em cooldown!");
							return;
						}
						p.setVelocity(new Vector(p.getVelocity().getX(), 3.0D, p.getVelocity().getZ()));
						jumps.add(p.getName());
						cd.add(p.getName());
						Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
							
							@Override
							public void run() {
								if(cd.contains(p.getName())) {
									cd.remove(p.getName());
									p.sendMessage("§7[§a!§7] O cooldown acabou!");
									p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
								}
							}
						}, 30 * 20L);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void explosions(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Main.terrorista.contains(p.getName())) {
				if((e.getCause() == DamageCause.BLOCK_EXPLOSION) || (e.getCause() == DamageCause.ENTITY_EXPLOSION)) {
					e.setCancelled(true);
				} else if(e.getCause() == DamageCause.FALL) {
					e.setDamage(2.0D);
						if(jumps.contains(p.getName())) {
							jumps.remove(p.getName());
							e.setCancelled(true);
						}
						p.getLocation().getWorld().createExplosion(p.getLocation(), 5F, true);
						Bukkit.getServer().getScheduler()
						.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
							public void run() {
								p.setVelocity(new Vector());
							}
						}, 1L);
						for(Entity en : p.getNearbyEntities(2D, 2D, 2D)) {
							if(en instanceof Player) {
								Player pn = (Player) en;
								pn.sendMessage("§7[§c!§7] Você foi atingido por um atentado terrorista de " + ChatColor.stripColor(p.getDisplayName()) + "!");
								pn.damage(10.0D);
							}
					}
				}
			}
		}
	}
}
