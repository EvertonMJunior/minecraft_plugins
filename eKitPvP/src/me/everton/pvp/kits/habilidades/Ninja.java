package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Ninja implements Listener {
	public static HashMap<String, String> targets = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();

	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if ((e.getDamager() instanceof Player)
				&& (e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();

			if (KitManager.getKit(d) == KitType.NINJA) {
				
				if(!API.getGamers().contains(d)) {
					return;
				}
				
				if (!targets.containsKey(d.getName())) {
					targets.put(d.getName(), p.getName());
				} else {
					if (targets.get(d.getName()) != p.getName()) {
						targets.remove(d.getName());
						targets.put(d.getName(), p.getName());
					}
				}
			}
		}
	}

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if (e.getAction().name().contains("LEFT")) {
			Player d = e.getPlayer();
			if (KitManager.getKit(d) == KitType.NINJA) {
				if (d.isSneaking()) {
					if (Grappler.nerf.contains(d.getName())) {
						d.sendMessage("§7[§c!§7] Você está em §a§lcombate§7!");
						return;
					}
					
					if(!API.getGamers().contains(d)) {
						d.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
						return;
					}
					
					if (targets.containsKey(d.getName())) {
						if (cd.containsKey(d.getName())) {
							d.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
									+ cd.get(d.getName()) + " segundos§7!");
							return;
						}
						if (Bukkit.getPlayerExact(targets.get(d.getName())) != null) {
							Player t = Bukkit.getPlayerExact(targets.get(d
									.getName()));
							if (d.getLocation().distance(t.getLocation()) <= 30) {
								d.teleport(t.getLocation());
								d.playSound(d.getLocation(),
										Sound.ENDERMAN_TELEPORT, 1F, 1F);
								t.playSound(t.getLocation(),
										Sound.ENDERMAN_TELEPORT, 1F, 1F);
								d.sendMessage("§aTeleportado!");
								API.startCd(d, 10, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
							} else {
								d.sendMessage("§c" + targets.get(d.getName())
										+ " está muito longe de você!");
							}
						} else {
							d.sendMessage("§c" + targets.get(d.getName())
									+ " está offline!");
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(e.getEntity().getKiller() instanceof Player) {
			Player p = e.getEntity().getKiller();
			if(targets.containsKey(p.getName())) {
				targets.remove(p.getName());
			}
		}
	}
}
