package me.everton.pvp.listeners;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class Sopa implements Listener{
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		Damageable hp = p;
		int sopa = 7;
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (p.getItemInHand().getType() == Material.MUSHROOM_SOUP)) {
			e.setCancelled(true);
			if (hp.getHealth() != 20) {
				p.setHealth(hp.getHealth() + sopa > hp.getMaxHealth() ? hp
						.getMaxHealth() : hp.getHealth() + sopa);
				if (KitManager.getKit(p) == KitType.QUICKDROPPER) {
					p.getItemInHand().setType(Material.BOWL);
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {
								@Override
								public void run() {
									p.setItemInHand(new ItemStack(Material.AIR));
									p.getWorld().dropItem(p.getEyeLocation(), API.item(Material.BOWL, 1, "§7Tigela")).setVelocity(p.getEyeLocation().getDirection().multiply(0.4D));
								}
							}, 1L);
				} else {
					p.setItemInHand(API.item(Material.BOWL, 1, "§7Tigela"));
				}
			} else if (p.getFoodLevel() != 20) {
				p.setFoodLevel(p.getFoodLevel() + 6 > 20 ? 20 : p
						.getFoodLevel() + 6);
				if (KitManager.getKit(p) == KitType.QUICKDROPPER) {
					p.getItemInHand().setType(Material.BOWL);
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {
								@Override
								public void run() {
									p.setItemInHand(new ItemStack(Material.AIR));
									p.getWorld().dropItem(p.getEyeLocation(), API.item(Material.BOWL, 1, "§7Tigela")).setVelocity(p.getEyeLocation().getDirection().multiply(0.4D));
								}
							}, 1L);
				} else {
					p.setItemInHand(API.item(Material.BOWL, 1, "§7Tigela"));
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void craftItem(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		for(ItemStack i : p.getInventory().getContents()) {
			if(i != null) {
				if(i.getType() != Material.AIR) {
					if(i.getType() == Material.MUSHROOM_SOUP) {
						if(i.getItemMeta().getDisplayName() == null) {
							i.setItemMeta(API.getSoup().getItemMeta());
						} else if(!i.getItemMeta().getDisplayName().equalsIgnoreCase(API.getSoup().getItemMeta().getDisplayName())) {
							i.setItemMeta(API.getSoup().getItemMeta());
						}
					}
				}
			}
		}
	}
}
