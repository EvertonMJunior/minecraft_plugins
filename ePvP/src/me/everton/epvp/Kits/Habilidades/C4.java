package me.everton.epvp.Kits.Habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.epvp.API;
import me.everton.epvp.Main;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class C4 implements Listener{
	public static HashMap<String, Item> bombas = new HashMap<>();
	public static ArrayList<String> cd = new ArrayList<>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(Main.c4.contains(p.getName())) {
			if(e.getAction().name().contains("LEFT")) {
				if(p.getItemInHand().getType() == Material.SLIME_BALL) {
					if(cd.contains(p.getName())) {
						p.sendMessage("§7[§c!§7] Kit em cooldown!");
						return;
					}
					p.setItemInHand(API.item(Material.STONE_BUTTON, 1, "§2C4 §7(Clique)"));
					Item bomb = p.getWorld().dropItem(p.getEyeLocation(), API.item(Material.TNT, 1, "C4"));
					bomb.setVelocity(p.getEyeLocation().getDirection().multiply(1.2D));
					bomb.setPickupDelay(999999999);
					bombas.put(p.getName(), bomb);
				} else if(p.getItemInHand().getType() == Material.STONE_BUTTON) {
					p.setItemInHand(API.item(Material.SLIME_BALL, 1, "§2C4 §7(Clique)"));
					if(bombas.containsKey(p.getName())) {
						bombas.get(p.getName()).remove();
					}
				}
			} else if(e.getAction().name().contains("RIGHT")) {
				if(p.getItemInHand().getType() == Material.STONE_BUTTON) {
					if(bombas.containsKey(p.getName())) {
						if(cd.contains(p.getName())) {
							p.sendMessage("§7[§c!§7] Kit em cooldown!");
							return;
						}
						p.setItemInHand(API.item(Material.SLIME_BALL, 1, "§2C4 §7(Clique)"));
						Item bomb = bombas.get(p.getName());
						cd.add(p.getName());
						Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
							
							@Override
							public void run() {
								if(cd.contains(p.getName())) {
									p.sendMessage("§7[§a!§7] O cooldown acabou!");
					            	p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
					            	cd.remove(p.getName());
								}
							}
						}, 10 * 20L);
						bomb.getWorld().createExplosion(bomb.getLocation(), 5.0F, true);
						bomb.remove();
						bombas.remove(p.getName());
						p.setItemInHand(API.item(Material.SLIME_BALL, 1, "§2C4 §7(Clique)"));
					} else {
						p.sendMessage("§7[§c!§7] Sua bomba ainda nao foi plantada!");
					}
				}
			}
		}
	}
}
