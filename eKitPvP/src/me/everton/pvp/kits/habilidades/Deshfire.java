package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

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
		
		if(!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + cd.get(p.getName()) + " segundos§7!");
			return;
		}		
		
		API.startCd(p, 15, 0, cd, "§7[§c!§7] O cooldown acabou!");

		p.setVelocity(p.getEyeLocation().getDirection().multiply(3.0D).setY(0.4D));
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
					p.getWorld().playEffect(p.getLocation(),
							Effect.MOBSPAWNER_FLAMES, 1);
					for (Entity ent : p.getNearbyEntities(1.5F, 2F, 1.5F)) {
						if(ent instanceof Player) {
							Player en = (Player) ent;
							if(!API.getGamers().contains(en)) {
								return;
							}
						}
						ent.setFireTicks(90);
					}
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
