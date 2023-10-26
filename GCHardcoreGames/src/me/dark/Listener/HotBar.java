package me.dark.Listener;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.dark.MySQL.SQLStatus;
import me.dark.Utils.DarkUtils;

public class HotBar implements Listener{
	public static ItemStack soup = DarkUtils.create_item(Material.MUSHROOM_SOUP, "§aSopas", 1, 0, null);
	public static ItemStack status = DarkUtils.create_item(Material.SKULL_ITEM, "§eStatus", 1, 3, null);
	public static void invSoup(Player p) {
		Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER, "§aSopas");
		inv.setItem(0, DarkUtils.create_item(Material.MUSHROOM_SOUP, "§aSopa de Cogumelo", 1, 0, Arrays.asList("§7Pote",
				"§7Cogumelo Vermelho","§7Cogumelo Marrom")));
		inv.setItem(1, DarkUtils.create_item(Material.CACTUS, "§aSopa de Cactus", 1, 0, Arrays.asList("§7Pote",
				"§7Cactus")));
		inv.setItem(2, DarkUtils.create_item(Material.INK_SACK, "§aSopa de Cocoa", 1, 3, Arrays.asList("§7Pote",
				"§7Cocoa Beans")));
		inv.setItem(3, DarkUtils.create_item(Material.RED_ROSE, "§aSopa de Flor", 1, 3, Arrays.asList("§7Pote",
				"§7Flor Amarela","§7Flor Vermelha")));
		
		
		p.openInventory(inv);
	}
	
	public static void invStatus(Player p) {
		Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER, "§eSeus Status");
		inv.setItem(2, DarkUtils.create_item(Material.CAKE, "§3Wins", 1, 0, Arrays.asList("§7"+SQLStatus.getWins(p.getUniqueId()))));
		inv.setItem(0, DarkUtils.create_item(Material.STONE_SWORD, "§3Kills", 1, 0, Arrays.asList("§7"+SQLStatus.getKills(p.getUniqueId()))));
		inv.setItem(4, DarkUtils.create_item(Material.SKULL_ITEM, "§3Deaths", 1, 0, Arrays.asList("§7"+SQLStatus.getDeaths(p.getUniqueId()))));
		
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getClickedInventory() == null) return;
		if (e.getCurrentItem() == null) return;
		
		if (e.getInventory().getName().equalsIgnoreCase("§aSopas")) {
			if (e.getCurrentItem().getType() == Material.MUSHROOM_SOUP) {
				e.setCancelled(true);
				p.closeInventory();
				Inventory inv = Bukkit.createInventory(p, InventoryType.DISPENSER, "§aSopa de Cogumelo");
				inv.setItem(3, DarkUtils.create_item(Material.BOWL, "§7Pote", 1, 0, null));
				inv.setItem(4, DarkUtils.create_item(Material.RED_MUSHROOM, "§7Cogumelo Vermelho", 1, 0, null));
				inv.setItem(5, DarkUtils.create_item(Material.BROWN_MUSHROOM, "§7Cogumelo Marrom", 1, 0, null));
				p.openInventory(inv);
				
			}else if (e.getCurrentItem().getType() == Material.CACTUS) {
				e.setCancelled(true);
				p.closeInventory();
				Inventory inv = Bukkit.createInventory(p, InventoryType.DISPENSER, "§aSopa de Cactus");
				inv.setItem(3, DarkUtils.create_item(Material.BOWL, "§7Pote", 1, 0, null));
				inv.setItem(4, DarkUtils.create_item(Material.CACTUS, "§7Cactus", 1, 0, null));
				p.openInventory(inv);
			}else if (e.getCurrentItem().getType() == Material.INK_SACK) {
				e.setCancelled(true);
				p.closeInventory();
				Inventory inv = Bukkit.createInventory(p, InventoryType.DISPENSER, "§aSopa de Cocoa");
				inv.setItem(3, DarkUtils.create_item(Material.BOWL, "§7Pote", 1, 0, null));
				inv.setItem(4, DarkUtils.create_item(Material.INK_SACK, "§7Cocoa Beans", 1, 3, null));
				p.openInventory(inv);
			}else if (e.getCurrentItem().getType() == Material.RED_ROSE) {
				e.setCancelled(true);
				p.closeInventory();
				Inventory inv = Bukkit.createInventory(p, InventoryType.DISPENSER, "§aSopa de Flor");
				inv.setItem(3, DarkUtils.create_item(Material.BOWL, "§7Pote", 1, 0, null));
				inv.setItem(4, DarkUtils.create_item(Material.YELLOW_FLOWER, "§7Flor Amarela", 1, 0, null));
				inv.setItem(5, DarkUtils.create_item(Material.RED_ROSE, "§7Flor Vermelha", 1, 0, null));
				p.openInventory(inv);
			}
		} else if (e.getInventory().getName().contains("§aSopa de")) {
			e.setCancelled(true);
			p.closeInventory();
		}
		
	}
}
