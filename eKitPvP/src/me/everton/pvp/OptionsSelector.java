package me.everton.pvp;

import me.everton.pvp.OptionsManager.DataType;

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

public class OptionsSelector implements Listener{
	public static void open(Player p) {
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
		Inventory inv = Bukkit.createInventory(p, 27, "§4Preferências");
		API.fillVillagerInv(inv);
		
		if(OptionsManager.getData(p.getName(), DataType.SWORD).equalsIgnoreCase("stone")) {
			inv.setItem(12, API.item(Material.STONE_SWORD, 1, "§7> §c§lTipo de Espada", new String[] {"§7Escolha entre espada de §a§lPedra", "§7e §a§lMadeira com Sharpness§7 para", "§7poder ver melhor as partículas!", "§c§lAviso: O dano é igual nos dois tipos!", "§7Tipo atual: §a§lEspada de Pedra"}));
		} else {
			inv.setItem(12, API.item(Material.WOOD_SWORD, 1, "§7> §c§lTipo de Espada", new String[] {"§7Escolha entre espada de §a§lPedra", "§7e §a§lMadeira com Sharpness§7 para", "§7poder ver melhor as partículas!", "§c§lAviso: O dano é igual nos dois tipos!", "§7Tipo atual: §a§lEspada de Madeira com Sharpness"}));
		}
		
		if(OptionsManager.getData(p.getName(), DataType.RECRAFT).equalsIgnoreCase("mushrooms")) {
			inv.setItem(14, API.item(Material.RED_MUSHROOM, 1, "§7> §c§lTipo de Recraft", new String[] {"§7Escolha entre recraft na", "§7forma de §a§lCogumelos §7ou", "§a§lCocoa Bean§7, da forma que você achar melhor!", "§7Tipo atual: §a§lRecraft de Cogumelos"}));
		} else {
			inv.setItem(14, API.item(Material.INK_SACK, 1, "§7> §c§lTipo de Recraft", new String[] {"§7Escolha entre recraft na", "§7forma de §a§lCogumelos §7ou", "§a§lCocoa Bean§7, da forma que você achar melhor!", "§7Tipo atual: §a§lRecraft de Cocoa Bean"}, 3));
		}
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(p.getItemInHand().getType() != Material.NETHER_STAR) {
			return;
		}
		
		if(!p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§5§lPreferências §7(Clique Direito)")) {
			return;
		}
		
		if(!e.getAction().name().contains("RIGHT")) {
			return;
		}
		
		open(p);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInvInteract(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		Player p = (Player) e.getWhoClicked();
		if(inv == null) {
			return;
		}
		ItemStack i = e.getCurrentItem();
		if(inv.getTitle().equalsIgnoreCase("§4Preferências")) {
			e.setCancelled(true);
			e.setResult(Result.DENY);
			e.setCursor(null);
			
			if(e.getSlot() == 12) {
				if(i.getType() == Material.STONE_SWORD) {
					inv.setItem(12, API.item(Material.WOOD_SWORD, 1, "§7> §c§lTipo de Espada", new String[] {"§7Escolha entre espada de §a§lPedra", "§7e §a§lMadeira com Sharpness§7 para", "§7poder ver melhor as partículas!", "§c§lAviso: O dano é igual nos dois tipos!", "§7Tipo atual: §a§lEspada de Madeira com Sharpness"}));
					OptionsManager.modifyData(p.getName(), "wood", DataType.SWORD);
				} else if(i.getType() == Material.WOOD_SWORD) {
					inv.setItem(12, API.item(Material.STONE_SWORD, 1, "§7> §c§lTipo de Espada", new String[] {"§7Escolha entre espada de §a§lPedra", "§7e §a§lMadeira com Sharpness§7 para", "§7poder ver melhor as partículas!", "§c§lAviso: O dano é igual nos dois tipos!", "§7Tipo atual: §a§lEspada de Pedra"}));
					OptionsManager.modifyData(p.getName(), "stone", DataType.SWORD);
				}
			}
			
			if(e.getSlot() == 14) {
				if(i.getType() == Material.RED_MUSHROOM) {
					inv.setItem(14, API.item(Material.INK_SACK, 1, "§7> §c§lTipo de Recraft", new String[] {"§7Escolha entre recraft na", "§7forma de §a§lCogumelos §7ou", "§a§lCocoa Bean§7, da forma que você achar melhor!", "§7Tipo atual: §a§lRecraft de Cocoa Bean"}, 3));
					OptionsManager.modifyData(p.getName(), "cocoa", DataType.RECRAFT);
				} else if(i.getType() == Material.INK_SACK) {
					inv.setItem(14, API.item(Material.RED_MUSHROOM, 1, "§7> §c§lTipo de Recraft", new String[] {"§7Escolha entre recraft na", "§7forma de §a§lCogumelos §7ou", "§a§lCocoa Bean§7, da forma que você achar melhor!", "§7Tipo atual: §a§lRecraft de Cogumelos"}));
					OptionsManager.modifyData(p.getName(), "mushrooms", DataType.RECRAFT);
				}
			}
		}
	}
}
