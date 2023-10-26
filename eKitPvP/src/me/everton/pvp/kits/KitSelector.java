package me.everton.pvp.kits;

import java.util.ArrayList;

import me.everton.pvp.API;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitSelector implements Listener{
	public static ArrayList<KitType> KitsSeusPage1 = new ArrayList<>();
	public static ArrayList<KitType> KitsOutrosPage1 = new ArrayList<>();
	
	public static enum SelectorPage{
		SEUS_KITS_1, SEUS_KITS_2, OUTROS_KITS_1, OUTROS_KITS_2;   
	}
	
	public static void open(Player p, SelectorPage page, Inventory openInv) {
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
		if(page == SelectorPage.SEUS_KITS_1) {
			Inventory inv;
			if(openInv == null) {
				inv = Bukkit.createInventory(p, 54, "§4Seus Kits §r[" + API.getKits(p, true) + "]");
			} else {
				inv = openInv;
			}
			for(int i = 0; i < 9; i++) {
				inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
			}
			inv.setItem(3, API.item(Material.WOOL, 1, "§f» §aSeus Kits §7[" + API.getKits(p, true) + "]", 5));
			inv.setItem(5, API.item(Material.WOOL, 1, "§f» §cOutros Kits §7[" + API.getKits(p, false) + "]", 14));
			
			if (API.getKits(p, true) > 45) {
				inv.setItem(8, API.item(Material.CARPET, 1,
						"§a§lPróxima Página §f»", 5));
			}
			
			int kitsOnMenu = 9;
			for(KitType kit : KitType.values()) {
				if(kitsOnMenu == 54) {
					break;
				}
				
				if(p.hasPermission("kit." + kit.name().toLowerCase())) {
					if(kit.getKitSelectorItem() != null) {
						if(!KitsSeusPage1.contains(kit)) {
							KitsSeusPage1.add(kit);
						}
						inv.setItem(kitsOnMenu, kit.getKitSelectorItem());
						kitsOnMenu++;
					}
				}
			}
			
			for(int z = 0; z < 54; z++) {
				if(inv.getContents()[z] == null || inv.getContents()[z].getType() == Material.AIR) {
					inv.setItem(z, API.item(Material.STAINED_GLASS_PANE, 1, " ", 7));
				}
			}
			
			if(openInv == null) {
				p.openInventory(inv);
			}
		} else if(page == SelectorPage.SEUS_KITS_2) {
			Inventory inv = openInv;
			inv.setItem(0, API.item(Material.CARPET, 1, "§f« §c§lPágina Anterior", 14));
			inv.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
			for(int i = 9; i < 54; i++) {
				inv.setItem(i, API.item(Material.AIR));
			}
			int i = 9;
			for (KitType kit : KitType.values()) {
				if (kit.name() == kit.name()) {
					if (i == 54) {
						break;
					}
					if (p.hasPermission("kit." + kit.name().toLowerCase())) {
						if (!KitsSeusPage1.contains(kit)) {
							inv.setItem(i, kit.getKitSelectorItem());
							i++;
						}
					}
				}
			}
			
			for(int z = 0; z < 54; z++) {
				if(inv.getContents()[z] == null || inv.getContents()[z].getType() == Material.AIR) {
					inv.setItem(z, API.item(Material.STAINED_GLASS_PANE, 1, " ", 7));
				}
			}
			
		} else if(page == SelectorPage.OUTROS_KITS_1) {
			Inventory inv;
			if(openInv == null) {
				inv = Bukkit.createInventory(p, 54, "§4Outros Kits §r[" + API.getKits(p, false) + "]");
			} else {
				inv = openInv;
			}
			for(int i = 0; i < 9; i++) {
				inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
			}
			
			if (API.getKits(p, false) > 45) {
				inv.setItem(8, API.item(Material.CARPET, 1,
						"§a§lPróxima Página §f»", 5));
			}
			
			inv.setItem(3, API.item(Material.WOOL, 1, "§f» §aSeus Kits §7[" + API.getKits(p, true) + "]", 5));
			inv.setItem(5, API.item(Material.WOOL, 1, "§f» §cOutros Kits §7[" + API.getKits(p, false) + "]", 14));
			
			int kitsOnMenu = 9;
			for(KitType kit : KitType.values()) {
				if(kitsOnMenu == 54) {
					break;
				}
				
				if(!p.hasPermission("kit." + kit.name().toLowerCase())) {
					if(kit.getKitSelectorItem() != null) {
						if(!KitsOutrosPage1.contains(kit)) {
							KitsOutrosPage1.add(kit);
						}
						inv.setItem(kitsOnMenu, kit.getKitSelectorItem());
						kitsOnMenu++;
					}
				}
			}
			
			for(int z = 0; z < 54; z++) {
				if(inv.getContents()[z] == null || inv.getContents()[z].getType() == Material.AIR) {
					inv.setItem(z, API.item(Material.STAINED_GLASS_PANE, 1, " ", 7));
				}
			}
			
			if(openInv == null) {
				p.openInventory(inv);
			}
		} else if(page == SelectorPage.OUTROS_KITS_2) {
			Inventory inv = openInv;
			inv.setItem(0, API.item(Material.CARPET, 1, "§f« §c§lPágina Anterior", 14));
			inv.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
			for(int i = 9; i < 54; i++) {
				inv.setItem(i, API.item(Material.AIR));
			}
			int i = 9;
			for (KitType kit : KitType.values()) {
				if (kit.name() == kit.name()) {
					if (i == 54) {
						break;
					}
					if (!p.hasPermission("kit." + kit.name().toLowerCase())) {
						if (!KitsOutrosPage1.contains(kit)) {
							inv.setItem(i, kit.getKitSelectorItem());
							i++;
						}
					}
				}
			}
			
			for(int z = 0; z < 54; z++) {
				if(inv.getContents()[z] == null || inv.getContents()[z].getType() == Material.AIR) {
					inv.setItem(z, API.item(Material.STAINED_GLASS_PANE, 1, " ", 7));
				}
			}
		}
	}
	
	public static void confirmKit(Player p, KitType kit) {
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
		Inventory inv = Bukkit.createInventory(p, 54, "§4" + kit.getName());
		for(int i = 0; i < 54; i++) {
			inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, " ", 0));
		}
		
		inv.setItem(0, API.item(Material.CARPET, 1, "§f« §c§lVoltar", 14));
		inv.setItem(22, kit.getKitSelectorItem());
		inv.setItem(39, API.item(Material.STAINED_GLASS, 1, "§f» §a§lEscolher Kit", 13));
		inv.setItem(41, API.item(Material.STAINED_GLASS, 1, "§f» §c§lDescrição", new String[] {"§7" + kit.getDesc()}, 15));
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void openSelector(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!e.getAction().name().contains("RIGHT")) {
			return;
		}
		
		if(p.getItemInHand().getType() != Material.ENDER_CHEST) {
			return;
		}
		
		if(p.getItemInHand().getItemMeta().getDisplayName() != null && !p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lKits §7(Clique Direito)")) {
			return;
		}
		e.setCancelled(true);
		e.setUseInteractedBlock(Result.DENY);
		p.updateInventory();
		
		open(p, SelectorPage.SEUS_KITS_1, null);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void invInteract(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		ItemStack item = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		if(inv == null) {
			return;
		}
		if(item == null) {
			return;
		}
		if(item.getType() == Material.AIR) {
			return;
		}
		for(KitType kit : KitType.values()) {
			if(inv.getTitle().contains(kit.getName())) {
				e.setCancelled(true);
				e.setResult(Result.DENY);
				e.setCursor(null);
				
				if(item.getItemMeta().getDisplayName().toLowerCase().contains("voltar")) {
					open(p, SelectorPage.SEUS_KITS_1, null);
					return;
				}
				
				if(item.getItemMeta().getDisplayName().toLowerCase().contains("escolher kit")) {
					KitManager.giveKit(p, kit);
					p.closeInventory();
					return;
				}
				break;
			}
		}
		if(inv.getTitle().contains("Seus Kits")) {
			e.setCancelled(true);
			e.setResult(Result.DENY);
			e.setCursor(null);
			
			if(item != null && item.getType() != Material.STAINED_GLASS_PANE) {
				if(item.getItemMeta().getDisplayName() == null) {
					return;
				}
				if(item.getItemMeta().getDisplayName().toLowerCase().contains("outros kits")) {
					open(p, SelectorPage.OUTROS_KITS_1, null);
					return;
				}
				
				if(item.getItemMeta().getDisplayName().toLowerCase().contains("próxima página")) {
					open(p, SelectorPage.SEUS_KITS_2, inv);
					return;
				}
				
				if(item.getItemMeta().getDisplayName().toLowerCase().contains("página anterior")) {
					open(p, SelectorPage.SEUS_KITS_1, inv);
					return;
				}
				
				for(KitType kit : KitType.values()) {
					if(kit.getKitSelectorItem().equals(item)) {
						confirmKit(p, kit);
						break;
					}
				}
			}
		}
		
		if(inv.getTitle().contains("Outros Kits")) {
			e.setCancelled(true);
			e.setResult(Result.DENY);
			e.setCursor(null);
			
			if(item != null && item.getType() != Material.STAINED_GLASS_PANE) {
				if(item.getItemMeta().getDisplayName() == null) {
					return;
				}
				if(item.getItemMeta().getDisplayName().toLowerCase().contains("seus kits")) {
					open(p, SelectorPage.SEUS_KITS_1, null);
					return;
				}
				
				if(item.getItemMeta().getDisplayName().toLowerCase().contains("próxima página")) {
					open(p, SelectorPage.OUTROS_KITS_2, inv);
					return;
				}
				
				if(item.getItemMeta().getDisplayName().toLowerCase().contains("página anterior")) {
					open(p, SelectorPage.OUTROS_KITS_1, inv);
					return;
				}
			}
		}
	}
}
