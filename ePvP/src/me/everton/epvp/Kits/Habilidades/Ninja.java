package me.everton.epvp.Kits.Habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.epvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Ninja implements Listener{
	public static HashMap<String, String> targets = new HashMap<>();
	public static ArrayList<String> cd = new ArrayList<>();
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player d = (Player) e.getDamager();
			if(Main.ninja.contains(d.getName())) {
				if(d.isSneaking()) {
					if(targets.containsKey(d.getName())) {
						if(cd.contains(d.getName())) {
							d.sendMessage("§7[§c!§7] Kit em cooldown!");
							return;
						}
						if(Bukkit.getPlayerExact(targets.get(d.getName())) != null) {
							Player t = Bukkit.getPlayerExact(targets.get(d.getName()));
							if(d.getLocation().distance(t.getLocation()) <= 30) {
								d.teleport(t.getLocation());
								d.playSound(d.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
								t.playSound(t.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
								d.sendMessage("§aTeleportado!");
								cd.add(d.getName());
								Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
									
									@Override
									public void run() {
										if(cd.contains(d.getName())) {
											cd.remove(d.getName());
											d.sendMessage("§7[§a!§7] O cooldown acabou!");
											d.playSound(d.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
										}
									}
								}, 10 * 20L);
							} else {
								d.sendMessage("§c" + targets.get(d.getName()) + " está muito longe de você!");
							}
						} else {
							d.sendMessage("§c" + targets.get(d.getName()) + " está offline!");
						}
					}
				}
			}
		}
		if((e.getDamager() instanceof Player) && (e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			
			if(Main.ninja.contains(d.getName())) {
				if(!targets.containsKey(d.getName())) {
					targets.put(d.getName(), p.getName());
				} else {
					if(targets.get(d.getName()) != p.getName()) {
						targets.remove(d.getName());
						targets.put(d.getName(), p.getName());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if(e.getAction().name().contains("LEFT")) {
			Player d = e.getPlayer();
			if(Main.ninja.contains(d.getName())) {
				if(d.isSneaking()) {
					if(targets.containsKey(d.getName())) {
						if(cd.contains(d.getName())) {
							d.sendMessage("§7[§c!§7] Kit em cooldown!");
							return;
						}
						if(Bukkit.getPlayerExact(targets.get(d.getName())) != null) {
							Player t = Bukkit.getPlayerExact(targets.get(d.getName()));
							if(d.getLocation().distance(t.getLocation()) <= 30) {
								d.teleport(t.getLocation());
								d.playSound(d.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
								t.playSound(t.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
								d.sendMessage("§aTeleportado!");
								cd.add(d.getName());
								Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
									
									@Override
									public void run() {
										if(cd.contains(d.getName())) {
											cd.remove(d.getName());
											d.sendMessage("§7[§a!§7] O cooldown acabou!");
											d.playSound(d.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
										}
									}
								}, 10 * 20L);
							} else {
								d.sendMessage("§c" + targets.get(d.getName()) + " está muito longe de você!");
							}
						} else {
							d.sendMessage("§c" + targets.get(d.getName()) + " está offline!");
						}
					}
				}
			}
		}
	}
}
