package me.everton.pvp.listeners;

import java.util.ArrayList;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class ListenersPrincipais implements Listener {
	@EventHandler
	public void noBlockExplode(EntityExplodeEvent e) {
		e.blockList().clear();
	}
	
	@EventHandler
	public void noDamageVillages(EntityDamageEvent e) {
		if(e.getEntity() instanceof Villager) {
			if(((Villager)e.getEntity()).isCustomNameVisible()) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void noBreak(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			for (ItemStack is : p.getEquipment().getArmorContents()) {
				try {
					is.setDurability((short) 0);
				} catch (Exception ex) {
					
				}
			}
		}
	}

	@EventHandler
	public void noBreak(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			for (ItemStack is : p.getInventory().getContents()) {
				try {
					if(is.getType().name().contains("SWORD") || is.getType().name().contains("AXE")) {
						is.setDurability((short) 0);
					}
				} catch (Exception ex) {
					
				}
			}
		}
	}

	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if(e.getSpawnReason() == SpawnReason.CUSTOM) {
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void noBurn(BlockBurnEvent e) {
		e.setCancelled(true);
		if (e.getBlock().getRelative(BlockFace.UP).getType() == Material.FIRE) {
			e.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
		}
	}

	@EventHandler
	public void noBurn(BlockSpreadEvent e) {
		e.setCancelled(true);
		if (e.getBlock().getRelative(BlockFace.UP).getType() == Material.FIRE) {
			e.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
		}
	}

	@EventHandler
	public void noDropKit(final PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName() == null) {
			return;
		}
		if (e.getItemDrop().getItemStack() == null) {
			return;
		}
		if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName()
				.equalsIgnoreCase("§cEspada")
				|| e.getItemDrop().getItemStack().getItemMeta()
						.getDisplayName()
						.equalsIgnoreCase("§cMachado de Viking")) {
			e.setCancelled(true);
			p.updateInventory();
		}
		final ArrayList<Material> permitidos = new ArrayList<>();
		permitidos.add(Material.MUSHROOM_SOUP);
		permitidos.add(Material.INK_SACK);
		permitidos.add(Material.RED_MUSHROOM);
		permitidos.add(Material.BROWN_MUSHROOM);
		permitidos.add(Material.BOWL);
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (!e.getItemDrop().getItemStack().getItemMeta()
						.getDisplayName().equalsIgnoreCase("noclear")
						|| e.getItemDrop().getItemStack().getItemMeta()
								.getDisplayName() == null) {
					e.getItemDrop().remove();
					e.getItemDrop()
							.getWorld()
							.playEffect(e.getItemDrop().getLocation(),
									Effect.SMALL_SMOKE, 1);
				}
			}
		}, 3 * 20L);

		if (KitManager.getKit(p).getKitItem() == null) {
			return;
		}
		if (e.getItemDrop()
				.getItemStack()
				.getItemMeta()
				.getDisplayName()
				.equalsIgnoreCase(
						KitManager.getKit(p).getKitItem().getItemMeta()
								.getDisplayName())) {
			e.setCancelled(true);
			p.updateInventory();
		}
	}

	@EventHandler
	public void noHunger(FoodLevelChangeEvent e) {
		e.setCancelled(true);
		((Player) e.getEntity()).setFoodLevel(20);
	}

	@EventHandler
	public void noRain(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onPickDrop(PlayerPickupItemEvent e) {
		ArrayList<Material> permitidos = new ArrayList<>();
		permitidos.add(Material.MUSHROOM_SOUP);
		permitidos.add(Material.INK_SACK);
		permitidos.add(Material.RED_MUSHROOM);
		permitidos.add(Material.BROWN_MUSHROOM);
		permitidos.add(Material.BOWL);

		if (!permitidos.contains(e.getItem().getItemStack().getType())) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void showKit(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		
		if(!(e.getDamager() instanceof Player)) {
			return;
		}
		
		if(e.getDamager().hasMetadata("NPC")) {
			return;
		}
		
		if(e.isCancelled()) {
			return;
		}
		
		if(e.getEntity().hasMetadata("NPC")) {
			return;
		}
		
		Player p = (Player) e.getDamager();
		Player ld = (Player) e.getEntity();
		API.sendActionBar(p, ld.getPlayerListName() + "§f - §b§l" + KitManager.getKit(ld).getName());
	}
}
