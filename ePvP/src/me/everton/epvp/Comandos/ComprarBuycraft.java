package me.everton.epvp.Comandos;

import me.everton.epvp.API;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ComprarBuycraft implements Listener{
	public static void openMenu(Player p, int menu) {
		if(menu == 1) {
			Inventory inv = Bukkit.createInventory(p, 27, "§4Comprar - Principal");
			for(int i = 0; i < 27; i++) {
				switch (i) {
				case 0:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 4:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 8:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 18:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 22:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 26:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				default:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 7));
					break;
				}
			}
			inv.setItem(11, API.item(Material.DIAMOND_SWORD, 1, "§b§lKitPvP"));
			inv.setItem(15, API.item(Material.MUSHROOM_SOUP, 1, "§b§lHG"));
			p.openInventory(inv);
		} else if(menu == 2) {
			Inventory inv = Bukkit.createInventory(p, 27, "§4Comprar - KitPvP");
			for(int i = 0; i < 27; i++) {
				switch (i) {
				case 0:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 4:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 8:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 18:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 22:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 26:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				default:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 7));
					break;
				}
			}
			inv.setItem(11, API.item(Material.BEACON, 1, "§6§lVips"));
			inv.setItem(15, API.item(Material.TNT, 1, "§6§lKits"));
			p.openInventory(inv);
		} else if(menu == 3) {
			Inventory inv = Bukkit.createInventory(p, 27, "§4Comprar - KitPvP - Vips");
			for(int i = 0; i < 27; i++) {
				switch (i) {
				case 0:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 4:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 8:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 18:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 22:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 26:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				default:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 7));
					break;
				}
			}
			inv.setItem(12, API.item(Material.NETHERRACK, 1, "§aVip"));
			inv.setItem(13, API.item(Material.BEACON, 1, "§6Pro"));
			inv.setItem(14, API.item(Material.BEDROCK, 1, "§9§oTVip"));
			inv.setItem(0, API.item(Material.CARPET, 1, "§c§l<-- Voltar", 14));
			
			p.openInventory(inv);
		} else if(menu == 4) {
			Inventory inv = Bukkit.createInventory(p, 27, "§4Comprar - KitPvP - Vip");
			for(int i = 0; i < 27; i++) {
				switch (i) {
				case 0:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 4:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 8:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 18:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 22:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				case 26:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 0));
					break;
				default:
					inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0 ", 7));
					break;
				}
			}
			inv.setItem(12, API.item(Material.IRON_SWORD, 1, "§c§l1 mês", new String[] {"§dPreço: "}));
			inv.setItem(14, API.item(Material.DIAMOND_SWORD, 1, "§2§lLifetime"));
			inv.setItem(0, API.item(Material.CARPET, 1, "§c§l<-- Voltar", 14));
			
			p.openInventory(inv);
		}
		p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
	}
	
	@EventHandler
	public void invInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		Inventory inv = e.getInventory();
		
		if(ChatColor.stripColor(inv.getName()).equalsIgnoreCase("Comprar - Principal")) {
			e.setCancelled(true);
			if(item.getType() == Material.DIAMOND_SWORD) {
				openMenu(p, 3);
			} else if(item.getType() == Material.MUSHROOM_SOUP) {
				openMenu(p, 5);
			}
		} else if(ChatColor.stripColor(inv.getName()).equalsIgnoreCase("Comprar - KitPvP - Vips")) {
			e.setCancelled(true);
			if(item.getType() == Material.NETHERRACK) {
				openMenu(p, 4);
			} else if(item.getType() == Material.CARPET) {
				openMenu(p, 1);
			}
		} else if(ChatColor.stripColor(inv.getName()).equalsIgnoreCase("Comprar - KitPvP - Vip")) {
			e.setCancelled(true);
			if(item.getType() == Material.IRON_SWORD) {
				p.closeInventory();
				for(int i = 0; i < 92; i++) {
					p.sendMessage(" ");
				}
				p.sendMessage(" §b----------------------------- §l+ §b-----------------------------");
				p.sendMessage(" §0");
				p.sendMessage("   §aPara finalizar a compra, entre em http://loja.wocpvp.com/buy/jUXR");
				p.sendMessage(" §0");
				p.sendMessage(" §b----------------------------- §l+ §b-----------------------------");
				for(int i = 0; i < 3; i++) {
					p.sendMessage(" ");
				}
			} else if(item.getType() == Material.DIAMOND_SWORD) {
				p.closeInventory();
				for(int i = 0; i < 92; i++) {
					p.sendMessage(" ");
				}
				p.sendMessage(" §b----------------------------- §l+ §b-----------------------------");
				p.sendMessage(" §0");
				p.sendMessage("   §aPara finalizar a compra, entre em http://loja.wocpvp.com/buy/gngN");
				p.sendMessage(" §0");
				p.sendMessage(" §b----------------------------- §l+ §b-----------------------------");
				for(int i = 0; i < 3; i++) {
					p.sendMessage(" ");
				}
			} else if(item.getType() == Material.CARPET) {
				openMenu(p, 3);
			}
		}
	}
}
