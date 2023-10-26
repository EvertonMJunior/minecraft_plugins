package me.everton.hg.kits.habilidades;

import java.util.HashMap;

import me.everton.hg.API;
import me.everton.hg.Main;
import me.everton.hg.kits.KitManager;
import me.everton.hg.kits.KitType;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Deshfire implements Listener {
	public static HashMap<String, ItemStack[]> armor = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();

	@EventHandler
	public void deshFire(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.DESHFIRE) {
			return;
		}

		if (!e.getAction().name().contains("RIGHT")) {
			return;
		}

		if (!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		
		e.setCancelled(true);
		e.setUseInteractedBlock(Result.DENY);
		p.updateInventory();

		if (!Main.STATE_KITS) {
			p.sendMessage("§7[§c!§7] Os kits estao desativados!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + Main.secToMinSec(cd.get(p.getName())) + " §7!");
			return;
		}		
		
		API.startCd(p, 10, 0, task, cd, true, "§7[§c!§7] O §c§lcooldown §7acabou!");

		p.setVelocity(p.getLocation().getDirection().multiply(3.0D));
		armor.put(p.getName(), p.getInventory().getArmorContents());
		
		p.getInventory().setHelmet(
				API.leatherArmor(Material.LEATHER_HELMET, " ",
						Color.RED));
		p.getInventory().setChestplate(
				API.leatherArmor(Material.LEATHER_CHESTPLATE, " ",
						Color.RED));
		p.getInventory().setLeggings(
				API.leatherArmor(Material.LEATHER_LEGGINGS, " ",
						Color.RED));
		p.getInventory().setBoots(
				API.leatherArmor(Material.LEATHER_BOOTS, " ",
						Color.RED));
		
		p.updateInventory();

		for (int i = 0; i < 50; i++) {
			Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

				@Override
				public void run() {
					for (Entity ent : p.getNearbyEntities(1.5F, 2F, 1.5F)) {
						ent.setFireTicks(90);
					}
					p.getWorld().playEffect(p.getLocation(),
							Effect.MOBSPAWNER_FLAMES, 1);
				}
			}, i);
		}
		
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				p.getInventory().setArmorContents(armor.get(p.getName()));
				armor.remove(p.getName());
			}
		}, 50L);
	}
}
