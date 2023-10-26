package me.everton.epvp.Kits.Habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.epvp.Main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poseidon implements Listener{
	public static ArrayList<String> cd = new ArrayList<>();
	public static HashMap<String, Material> oldblocks = new HashMap<>();
	public static HashMap<String, Location> oldblocksloc = new HashMap<>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(Main.poseidon.contains(p.getName())) {
			if(e.getAction().name().contains("RIGHT")) {
				if(p.getItemInHand().getType() == Material.WATER_BUCKET) {
					e.setCancelled(true);
					if(oldblocks.containsKey(p.getName())) {
						p.sendMessage("§7[§c!§7] Aguarde!");
						return;
					}
					if(cd.contains(p.getName())) {
						p.sendMessage("§7[§c!§7] Kit em cooldown!");
						return;
					}
					
					Block b = e.getClickedBlock().getRelative(BlockFace.UP);
					oldblocks.put(p.getName(), b.getType());
					oldblocksloc.put(p.getName(), new Location(b.getWorld(), b.getX(), b.getY(), b.getZ()));
					
					b.setType(Material.STATIONARY_WATER);
					p.sendMessage("§7[§c!§7] A água foi colocada! Ela sumirá em 10 segundos!");
					
					cd.add(p.getName());
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						
						@Override
						public void run() {
							if(cd.contains(p.getName())) {
								cd.remove(p.getName());
								p.sendMessage("§7[§c!§7] O cooldown acabou!");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
							}
						}
					}, 30 * 20L);
					
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						
						@Override
						public void run() {
							if(oldblocks.containsKey(p.getName())) {
								oldblocksloc.get(p.getName()).getBlock().setType(oldblocks.get(p.getName()));
								oldblocks.remove(p.getName());
								p.sendMessage("§7[§c!§7] A água sumiu!");
								p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
							}
						}
					}, 10 * 20L);
				}
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(Main.poseidon.contains(p.getName())) {
			if((p.getLocation().getBlock().getType() == Material.WATER) || (p.getLocation().getBlock().getType() == Material.STATIONARY_WATER)) {
				for (PotionEffect pot : p.getActivePotionEffects()) {
					p.removePotionEffect(pot.getType());
				}
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10 * 20, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 * 20, 0));
			}
		}
	}
}
