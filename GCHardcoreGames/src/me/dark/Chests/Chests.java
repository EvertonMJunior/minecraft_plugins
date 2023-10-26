package me.dark.Chests;

import java.util.Arrays;
import java.util.HashMap;

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

public class Chests implements Listener{
	public static ItemStack chest = DarkUtils.create_item(Material.ENDER_CHEST, "§bChests", 1, 0, null);
	public static HashMap<Player, ItemStack[]> item = new HashMap<Player, ItemStack[]>();
	public static void inv(Player p) {
		Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER, "§7Chests");
		inv.setItem(0, DarkUtils.create_item(Material.CHEST, "§aSeus itens ganhos", 1, 0, null));
		inv.setItem(2,DarkUtils.create_item(Material.ENDER_CHEST,
				"§aG1 §7[§c"+SQLStatus.getChests(p.getUniqueId(), ChestsTypes.G1)+"§7]", 1, 0, Arrays.asList("§7Tenha a sorte de ganhar itens de armaduras aleatórias §c(Capacete, Peitoral..)",
						                                                                               "§7Você poderá usar o item eternamente!",
						                                                                               " ",
						                                                                               "§7Compre por §b50000 GoodCoins§7 ou pelo Site do Servidor (§3good-craft.net§7)")));
		inv.setItem(3,DarkUtils.create_item(Material.ENDER_CHEST,
				"§cG2 §7[§c"+SQLStatus.getChests(p.getUniqueId(), ChestsTypes.G2)+"§7]", 1, 0, Arrays.asList("§7Tenha a sorte de ganhar tanto itens de armaduras §c(Capacete, Peitoral..)§7 quanto §cespadas§7.","§7Armaduras e espadas de Diamantes também!",
						"§7Você poderá usar o item eternamente!",
						" ",
						 "§7Compre por §b65000 GoodCoins§7 ou pelo Site do Servidor (§3good-craft.net§7)")));
		inv.setItem(4,DarkUtils.create_item(Material.ENDER_CHEST,
				"§4G3 §7[§c"+SQLStatus.getChests(p.getUniqueId(), ChestsTypes.G3)+"§7]", 1, 0, Arrays.asList("§7Tenha a sorte de ganhar 1 §ckit§7 aleatoriamente.","§7Você poderá usar o kit sorteado eternamente!",
						" ",
						"§7Compre por §b75000 GoodCoins§7 ou pelo Site do Servidor (§3good-craft.net§7)")));
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getClickedInventory() == null) return;
		if (e.getCurrentItem() == null) return;
		
		if (e.getInventory().getName().equalsIgnoreCase("§7Chests")) {
			e.setCancelled(true);
			p.closeInventory();
			p.sendMessage("§cEm breve.");
		}
		
	}
	
	

}
