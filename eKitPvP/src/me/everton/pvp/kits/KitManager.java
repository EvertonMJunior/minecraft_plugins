package me.everton.pvp.kits;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import me.everton.pvp.API;
import me.everton.pvp.OptionsManager;
import me.everton.pvp.OptionsManager.DataType;
import me.everton.pvp.ScoreboardManager;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitManager {
	public static HashMap<String, KitType> kit = new HashMap<>();
	
	public static void giveKit(Player p, KitType kit) {
		if(KitManager.kit.containsKey(p.getName()) && getKit(p) != KitType.NONE) {
			p.sendMessage("§c§lVocê já está usando um Kit! §7(" + getKit(p).getName()+ ")");
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 1F);
			return;
		}
		if(kit == KitType.SURPRISE) {
			kit = KitType.values()[new Random().nextInt(KitType.values().length)];
			p.sendMessage("§aKit Surprise escolhido: §2" + kit.getName() + "§a!");
		}
		KitManager.kit.put(p.getName(), kit);
		
		for(int i = 0; i < p.getInventory().getSize(); i++) {
			p.getInventory().setItem(i, API.getSoup());
		}
		
		p.getInventory().setItem(0, API.getSword(p));
		
		if(kit.getKitItem() != null) {
			p.getInventory().setItem(8, kit.getKitItem());
		}
		if(kit == KitType.ARCHER) {
			ItemStack item = new ItemStack(Material.BOW);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName("§b§lArqueiro");
			im.setLore(Arrays.asList(new String[] {"§7" + kit.getDesc()}));
			item.setItemMeta(im);
			item.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
			item.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			p.getInventory().setItem(8, item);
			p.getInventory().setItem(9, API.item(Material.ARROW));
		}
		if(kit == KitType.GRANDPA) {
			p.getInventory().getItem(8).addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		}
		if(kit == KitType.JELLYFISH) {
			p.getInventory().setItem(8, API.item(Material.AIR));
		}
		if(kit == KitType.SWITCHER) {
			p.getInventory().getItem(8).setAmount(16);
		}
		
		p.getInventory().setItem(13, API.getBowl());
		if(OptionsManager.getData(p.getName(), DataType.RECRAFT).equalsIgnoreCase("mushrooms")) {
			p.getInventory().setItem(14, API.getRedMushroom());
			p.getInventory().setItem(15, API.getBrownMushroom());
		} else {
			p.getInventory().setItem(14, API.getCocoa());
		}
		
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
		p.sendMessage(" ");
		p.sendMessage("§7[§c!§7] Kit §c§l" + kit.getName() + " §7escolhido!");
		p.sendMessage("§7[§c!§7] Descriçao do Kit:§c§l " + kit.getDesc() + "§7!");
		p.sendMessage(" ");
		ScoreboardManager.refreshSidebar(p);
	}
	
	public static KitType getKit(Player p) {
		if(kit.containsKey(p.getName())) {
			return kit.get(p.getName());
		} else {
			return KitType.NONE;
		}
	}
	
	public static boolean isWithKitItemInHand(Player p) {
		if(p.getItemInHand() == null) {
			return false;
		}
		
		if(getKit(p).getKitItem() == null) {
			return false;
		}
		try {
			if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(getKit(p).getKitItem().getItemMeta().getDisplayName())) {
				return true;
			} else {
				return false;
			}
		} catch(Exception e) {
			return false;
		}
	}
	
	public static void resetKit(Player p) {
		kit.remove(p.getName());
		ScoreboardManager.refreshSidebar(p);
	}
}
