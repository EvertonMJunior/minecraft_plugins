package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Flash implements Listener {
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void flash(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction().name().contains("RIGHT")) {
			if (KitManager.getKit(p) == KitType.FLASH) {
				if (KitManager.isWithKitItemInHand(p)) {
					e.setCancelled(true);
					e.setUseInteractedBlock(Result.DENY);
					if(Grappler.nerf.contains(p.getName())) {
						p.sendMessage("§7[§c!§7] Você está em §a§lcombate§7!");
						return;
					}
					
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
					if(b.getType() == Material.AIR) {
						p.sendMessage("§cMire para um bloco!");
						return;
					}
					Location loc = b.getLocation();
					p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (int) (loc.distance(p.getLocation()) * 20), 0));
					loc.setPitch(p.getLocation().getPitch());
					loc.setYaw(p.getLocation().getYaw());
					loc.setY(loc.getWorld().getHighestBlockYAt(loc));
					loc.getWorld().strikeLightning(loc.clone().add(0, 10, 0));
					p.setNoDamageTicks(20);
					p.teleport(loc);
					p.sendMessage("§aTeleportado!");
					API.startCd(p, 15, 0, cd, "§7[§c!§7] O cooldown acabou!");
				}
			}
		}
	}
}
