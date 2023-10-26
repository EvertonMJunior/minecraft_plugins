package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Jellyfish implements Listener {
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	
	public static ArrayList<Block> waters = new ArrayList<>();

	@EventHandler
	public void onIE(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.JELLYFISH) {
			return;
		}

		if (p.getItemInHand().getType() != Material.AIR) {
			return;
		}
		
		if(!e.getAction().name().contains("RIGHT")) {
			return;
		}
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
		
		final Block b = e.getClickedBlock().getRelative(BlockFace.UP);
		
		if(b.getType() != Material.AIR) {
			p.sendMessage("§7[§c!§7] Você não pode pôr sua água aqui!");
			return;
		}
		
		API.startCd(p, 30, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
		b.setType(Material.STATIONARY_WATER);
		waters.add(b);
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				b.setType(Material.AIR);
			}
		}, 5 * 20L);
	}
	
	@EventHandler
	public void naoEscorrer(BlockPhysicsEvent e) {
		Block b = e.getBlock();
		if(b.getType() == Material.STATIONARY_WATER && waters.contains(b)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void envenenar(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Block b = p.getLocation().getBlock();
		if(b.getType() == Material.STATIONARY_WATER && waters.contains(b) && KitManager.getKit(p) != KitType.JELLYFISH) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 5, 0));
		}
	}
}
