package me.everton.pvp.mlg;

import java.util.ArrayList;

import me.everton.pvp.API;
import me.everton.pvp.LocationsManager;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MLGListener implements Listener{
	private static ArrayList<String> players = new ArrayList<>();
	
	public static void enterExit(Player p) {
		if(!players.contains(p.getName())) {
			players.add(p.getName());
			p.sendMessage("§7§lMLG §f» §aVocê entrou.");
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
			KitManager.resetKit(p);
			p.getInventory().clear();
			p.getInventory().setItem(0, API.item(Material.WATER_BUCKET, 1, "§f» §lBalde para MLG", new String[] {"§7Com este balde, faça MLGs", "§7e ganhe §d§lTiger §7e §e§lVillager §7Coins!!"}));
		} else {
			players.remove(p.getName());
			p.sendMessage("§7§lMLG §f» §aVocê saiu.");
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
		}
	}
	
	@EventHandler
	public void onFall(final EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		final Player p = (Player) e.getEntity();
		
		if(!players.contains(p.getName())) {
			return;
		}
		
		if(e.getCause() != DamageCause.FALL) {
			return;
		}
		e.setDamage(0.0D);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				p.setNoDamageTicks(5);
				p.teleport(LocationsManager.getLocation("mlg"));
				p.sendMessage("§7§lMLG §f» §aVocê errou o MLG!");
				p.getInventory().setItem(0, API.item(Material.WATER_BUCKET, 1, "§f» §lBalde para MLG", new String[] {"§7Com este balde, faça MLGs", "§7e ganhe §d§lTiger §7e §e§lVillager §7Coins!!"}));
				p.updateInventory();
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
			}
		}.runTaskLater(Main.getPlugin(), 1L);
	}
	
	@EventHandler
	public void onMakeMLG(final PlayerBucketEmptyEvent e) {
		final Player p = e.getPlayer();
		if(p.getItemInHand().getType() != Material.WATER_BUCKET) {
			return;
		}
		if(e.getBlockClicked().getLocation().getY() >= LocationsManager.getLocation("mlg").getY() - 3) {
			e.setCancelled(true);
			return;
		}
		p.setNoDamageTicks(5);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				final Block b = e.getBlockClicked();
				if(!players.contains(p.getName())) {
					return;
				}
				
				for(final BlockFace bf : BlockFace.values()) {
					if(b.getRelative(bf).getType() == Material.WATER) {
						b.getRelative(bf).setType(b.getType());
						new BukkitRunnable() {
							
							@Override
							public void run() {
								b.getRelative(bf).setType(Material.AIR);
							}
						}.runTaskLater(Main.getPlugin(), 1L);
					}
				}
				p.getInventory().setItem(0, API.item(Material.WATER_BUCKET, 1, "§f» §lBalde para MLG", new String[] {"§7Com este balde, faça MLGs", "§7e ganhe §d§lTiger §7e §e§lVillager §7Coins!!"}));
				p.updateInventory();
				
				Location loc = b.getLocation();
				loc.setY(0);
				Location loc2 = p.getLocation().getBlock().getLocation();
				loc2.setY(0);
				p.teleport(LocationsManager.getLocation("mlg"));
				
				if(!loc.equals(loc2)) {
					return;
				}
				
				p.setNoDamageTicks(5);
				p.sendMessage("§7§lMLG §f» §aVocê acertou o MLG!");
				//Money.addMoney(p.getName(), 150);
				//VillagerCoins.addVillagerCoins(p.getName(), 15);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
			}
		}.runTaskLater(Main.getPlugin(), 2L);
	}
}