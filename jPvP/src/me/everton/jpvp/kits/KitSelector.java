package me.everton.jpvp.kits;

import me.everton.jpvp.utils.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class KitSelector{
	
	public static void openSelector(Player p, int page) {
		Inventory inv = Bukkit.createInventory(p, 54, "§f» §c§lSeus Kits");
		new API().fillInvOfGlasses(inv);
		p.openInventory(inv);
	}
}