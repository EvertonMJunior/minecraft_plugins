package me.dark.kit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Utils.DarkUtils;

public class KitSelector implements Listener{
	
	@SuppressWarnings("deprecation")
	public static void openInv(Player p) {
		Inventory inv = Bukkit.createInventory(p, 54, Main.prefix+"Kits");
		inv.clear();
		for (int i = 0; i < 9; i++) {
			inv.setItem(i, DarkUtils.create_item(Material.getMaterial(160), " ", 1, 1, null));
		}
		for (Kit k : Kit.kits) {
			if (p.hasPermission("kit."+k.toString())) {
				inv.addItem(DarkUtils.create_item(k.getKitSelectorItem(), "§3"+k.toString(), 1, 0, k.getDesc()));	
			}
		}
		p.openInventory(inv);
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		if (inv != null) {
			if (inv.getName().contains(Main.prefix + "Kits")) {
				ItemStack item = e.getCurrentItem();
				if (item != null) {
					e.setCancelled(true);
					for (Kit k : Kit.kits) {
						if (item.hasItemMeta()) {
							if (item.getItemMeta().hasDisplayName()) {
								if (item.getItemMeta().getDisplayName().contains(k.toString())) {
									p.chat("/kit "+k.toString());
								}
							}
						}
					}
				}
			}
		}
	}

}
