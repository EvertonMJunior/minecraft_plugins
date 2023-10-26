package me.dark.Listener;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;
import me.dark.Game.GameState;

public class GamerRelog implements Listener{
	
	public static HashMap<Player, ItemStack[]> relogingInv = new HashMap<Player, ItemStack[]>();
	public static HashMap<Player, Location> relogingLoc = new HashMap<Player, Location>();
	public static ArrayList<Player> reloging = new ArrayList<Player>();
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		if (Main.estado == GameState.PREGAME) return;
		if (Main.players.contains(p)) {
			if (GamerDamage.combatLog.containsKey(p)) {
				if (p.isOnline()) {
					p.damage(300D, GamerDamage.combatLog.get(p));
					for (ItemStack items : p.getInventory().getContents()) {
						if (items != null) {
							if (p.getLocation() != null) {
								p.getLocation().getWorld().dropItemNaturally(p.getLocation(), items);
							}
						}
					}
				}
				Main.players.remove(p);
				Bukkit.broadcastMessage("§b"+p.getName()+" deslogou em combate!");
				
				
				
				return;
			}
			ItemStack[] items = p.getInventory().getContents();
			relogingInv.put(p, items);
			relogingLoc.put(p, p.getLocation());
			reloging.add(p);
			new BukkitRunnable() {
				int tempo = 0;
				public void run() {
					if (p.isOnline()) {
						cancel();
						relogingInv.remove(p);
						relogingLoc.remove(p);
						reloging.remove(p);
						return;
					} else if (tempo == 30) {
						GamerDeath.broadCastDeath(p, "demorou muito para entrar novamente");
						Main.players.remove(p);
						Location loc = null;
						if (relogingLoc.containsKey(p)) {
							loc = relogingLoc.get(p);
						}
						if (relogingInv.containsKey(p)) {
							for (ItemStack items : relogingInv.get(p)) {
								if (items != null) {
									if (loc != null) {
										loc.getWorld().dropItemNaturally(loc, items);
									}
								}
							}
						}
						reloging.remove(p);
						relogingInv.remove(p);
						relogingLoc.remove(p);
						cancel();
						return;
					}
					tempo++;
				}
			}.runTaskTimer(Main.getMain(), 20, 20);
		}
	}
	
	@EventHandler
	public void kick(PlayerKickEvent e) {
		final Player p = e.getPlayer();
		if (Main.estado == GameState.PREGAME) return;
		if (Main.players.contains(p)) {
			if (GamerDamage.combatLog.containsKey(p)) {
				if (p.isOnline()) {
					p.damage(300D, GamerDamage.combatLog.get(p));
					for (ItemStack items : p.getInventory().getContents()) {
						if (items != null) {
							if (p.getLocation() != null) {
								p.getLocation().getWorld().dropItemNaturally(p.getLocation(), items);
							}
						}
					}
				}
				Main.players.remove(p);
				Bukkit.broadcastMessage("§b"+p.getName()+" deslogou em combate!");
				
				
				
				return;
			}
			ItemStack[] items = p.getInventory().getContents();
			relogingInv.put(p, items);
			relogingLoc.put(p, p.getLocation());
			reloging.add(p);
			new BukkitRunnable() {
				int tempo = 0;
				public void run() {
					if (p.isOnline()) {
						cancel();
						relogingInv.remove(p);
						relogingLoc.remove(p);
						reloging.remove(p);
						return;
					} else if (tempo == 30) {
						GamerDeath.broadCastDeath(p, "demorou muito para entrar novamente");
						Main.players.remove(p);
						Location loc = null;
						if (relogingLoc.containsKey(p)) {
							loc = relogingLoc.get(p);
						}
						if (relogingInv.containsKey(p)) {
							for (ItemStack items : relogingInv.get(p)) {
								if (items != null) {
									if (loc != null) {
										loc.getWorld().dropItemNaturally(loc, items);
									}
								}
							}
						}
						reloging.remove(p);
						relogingInv.remove(p);
						relogingLoc.remove(p);
						cancel();
						return;
					}
					tempo++;
				}
			}.runTaskTimer(Main.getMain(), 20, 20);
		}
	}

}
