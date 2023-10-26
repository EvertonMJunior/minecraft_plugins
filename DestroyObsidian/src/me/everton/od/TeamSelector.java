package me.everton.od;

import me.everton.od.API.GameStage;
import me.everton.od.cmds.Time;
import me.everton.od.cmds.Time.Team;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class TeamSelector implements Listener{
	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, "§4Seletor de Times");
		inv.setItem(0, API.item(Material.STAINED_GLASS_PANE, 1, " ", 15));
		inv.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " ", 15));
		inv.setItem(11, API.item(Material.STAINED_GLASS_PANE, 1, " ", 15));
		inv.setItem(15, API.item(Material.STAINED_GLASS_PANE, 1, " ", 15));
		inv.setItem(18, API.item(Material.STAINED_GLASS_PANE, 1, " ", 15));
		inv.setItem(26, API.item(Material.STAINED_GLASS_PANE, 1, " ", 15));
		
		inv.setItem(1, API.item(Material.IRON_FENCE, 1, " "));
		inv.setItem(3, API.item(Material.IRON_FENCE, 1, " "));
		inv.setItem(5, API.item(Material.IRON_FENCE, 1, " "));
		inv.setItem(7, API.item(Material.IRON_FENCE, 1, " "));
		inv.setItem(9, API.item(Material.IRON_FENCE, 1, " "));
		inv.setItem(13, API.item(Material.IRON_FENCE, 1, " "));
		inv.setItem(17, API.item(Material.IRON_FENCE, 1, " "));
		inv.setItem(19, API.item(Material.IRON_FENCE, 1, " "));
		inv.setItem(21, API.item(Material.IRON_FENCE, 1, " "));
		inv.setItem(23, API.item(Material.IRON_FENCE, 1, " "));
		inv.setItem(25, API.item(Material.IRON_FENCE, 1, " "));
		
		
		inv.setItem(12, API.item(Material.WOOL, 1, "§7>> §cTime Vermelho", new String[] {"§7Clique para entrar", "§7no time §cVermelho§7!"}, 14));
		inv.setItem(14, API.item(Material.WOOL, 1, "§7>> §9Time Azul", new String[] {"§7Clique para entrar", "§7no time §cAzul§7!"}, 11));
		p.openInventory(inv);
	}
	
	@EventHandler
	public void selectorClicks(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getTitle().equalsIgnoreCase("§4Seletor de Times")) {
			e.setCancelled(true);
			e.setResult(Result.DENY);
			if(e.getCurrentItem().getType() != Material.WOOL) {
				return;
			}
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Vermelho")) {
				Time.setTeam(p, Team.RED);
			} else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Azul")) {
				Time.setTeam(p, Team.BLUE);
			}
			p.closeInventory();
		}
	}
	
	@EventHandler
	public void openSelector(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!e.getAction().name().contains("RIGHT")) {
			return;
		}
		
		if(API.getGameStage() != GameStage.PRE_JOGO) {
			return;
		}
		
		if(p.getInventory().getHeldItemSlot() != 4) {
			return;
		}
		
		open(p);
	}
}
