package me.everton.pvp.e1v1;

import me.everton.pvp.API;
import me.everton.pvp.OptionsManager;
import me.everton.pvp.OptionsManager.DataType;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kits1v1 {
	public static void kitNormal(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);

		for (int i = 0; i < 9; i++) {
			p.getInventory().setItem(i, API.getSoup());
		}

		p.getInventory().setItem(0, API.getSword(p));
	}

	public static void kitRefil(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);

		for (int i = 0; i < 36; i++) {
			p.getInventory().setItem(i, API.getSoup());
		}

		p.getInventory().setItem(0, API.getSword(p));
	}

	public static void kitFulliron(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(API.item(Material.IRON_HELMET, 1, " "));
		p.getInventory().setChestplate(
				API.item(Material.IRON_CHESTPLATE, 1, " "));
		p.getInventory().setLeggings(API.item(Material.IRON_LEGGINGS, 1, " "));
		p.getInventory().setBoots(API.item(Material.IRON_BOOTS, 1, " "));

		for (int i = 0; i < 36; i++) {
			p.getInventory().setItem(i, API.getSoup());
		}

		p.getInventory().setItem(0, getSword(p));
	}

	public static ItemStack getSword(Player p) {
		if (OptionsManager.getData(p.getName(), DataType.SWORD)
				.equalsIgnoreCase("stone")) {
			return API.item(Material.DIAMOND_SWORD, 1, "§cEspada");
		} else {
			return API.item(Material.IRON_SWORD, 1, "§cEspada",
					Enchantment.DAMAGE_ALL, 1);
		}
	}
}
