package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Thor implements Listener{
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void thor(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction().name().contains("RIGHT")) {
			if (KitManager.getKit(p) == KitType.THOR) {
				if (KitManager.isWithKitItemInHand(p)) {
					e.setCancelled(true);
					
					if(!API.getGamers().contains(p)) {
						p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
						return;
					}
					
					if (cd.containsKey(p.getName())) {
						p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
								+ cd.get(p.getName()) + " segundos§7!");
						return;
					}
					Block b = p.getTargetBlock(null, 100);
					
					Location loc = b.getLocation();
					loc.setY(loc.getWorld().getHighestBlockYAt(loc));
					loc.getWorld().strikeLightning(loc.clone());
					API.startCd(p, 5, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
				}
			}
		}
	}
}
