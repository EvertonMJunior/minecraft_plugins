package me.everton.pvp.listeners;

import java.util.HashMap;

import me.everton.pvp.API;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Signs implements Listener {
	@EventHandler
	public void criarPlaca(SignChangeEvent e) {
		Player p = e.getPlayer();
		for (int i = 0; i < 4; i++) {
			e.setLine(i, e.getLine(i).replace("&", "§"));
		}
		if (e.getLine(0).equalsIgnoreCase("sopa")) {
			if (p.hasPermission("pvp.placas")) {
				e.setLine(0, "§4§m------------");
				e.setLine(1, "§6§lTiger§fPvP");
				e.setLine(2, "§5§lSopas");
				e.setLine(3, "§4§m------------");
			} else {
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
				e.setCancelled(true);
				e.getBlock().breakNaturally();
			}
		} else if (e.getLine(0).equalsIgnoreCase("recraft")) {
			if (p.hasPermission("pvp.placas")) {
				e.setLine(0, "§4§m------------");
				e.setLine(1, "§6§lTiger§fPvP");
				e.setLine(2, "§5§lRecraft");
				e.setLine(3, "§4§m------------");
			} else {
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
				e.setCancelled(true);
				e.getBlock().breakNaturally();
			}
		} else if (e.getLine(0).equalsIgnoreCase("reparar")) {
			if (p.hasPermission("pvp.placas")) {
				e.setLine(0, "§4§m------------");
				e.setLine(1, "§6§lTiger§fPvP");
				e.setLine(2, "§5§lReparar");
				e.setLine(3, "§4§m------------");
			} else {
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
				e.setCancelled(true);
				e.getBlock().breakNaturally();
			}
		}
	}
	
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();

	@EventHandler
	public void clicarPlaca(PlayerInteractEvent e) throws Exception {
		Player p = e.getPlayer();
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& ((e.getClickedBlock().getType() == Material.SIGN_POST) || (e
						.getClickedBlock().getType() == Material.WALL_SIGN))) {
			Sign s = (Sign) e.getClickedBlock().getState();
			if ((s.getLine(1).equalsIgnoreCase("§6§lTiger§fPvP"))
					&& (s.getLine(2).equalsIgnoreCase("§5§lSopas"))) {
				Inventory sopas = Bukkit.createInventory(p, 54, ChatColor.BLACK
						+ "Sopas");
				for (int i = 0; i < sopas.getSize(); i++) {
					sopas.setItem(i, API.getSoup());
				}
				p.openInventory(sopas);
			} else {
				if ((s.getLine(1).equalsIgnoreCase("§6§lTiger§fPvP"))
						&& (s.getLine(2).equalsIgnoreCase("§5§lRecraft"))) {
					if(task.containsKey(p.getName())) {
						p.sendMessage("§7[§c!§7] Recraft em cooldown de §c§l" + cd.get(p.getName()) + " segundos§7!");
						return;
					}
					if(API.getHowMuchClearSlots(p) < 3) {
						p.sendMessage("§7[§c!§7] Seu inventário está cheio!");
						return;
					}
					p.getInventory().addItem(
							API.item(Material.RED_MUSHROOM, 64, "§7Cogumelo"));
					p.getInventory()
							.addItem(
									API.item(Material.BROWN_MUSHROOM, 64,
											"§7Cogumelo"));
					p.getInventory().addItem(
							API.item(Material.BOWL, 64, "§7Tigela"));
					p.updateInventory();
					p.sendMessage("§7[§a!§7] Recraft Adicionado! Você poderá pegar mais em 15 segundos!");
					API.startCd(p, 15, 0, cd, null);
				} else if ((s.getLine(1).equalsIgnoreCase("§6§lTiger§fPvP"))
						&& (s.getLine(2).equalsIgnoreCase("§5§lReparar"))) {
					for (ItemStack is : p.getInventory().getContents()) {
						try {
							is.setDurability((short) 0);
						} catch (NullPointerException localNullPointerException) {
						}
					}
					for (ItemStack is : p.getEquipment().getArmorContents()) {
						try {
							is.setDurability((short) 0);
						} catch (NullPointerException localNullPointerException1) {
						}
					}
					p.sendMessage("§aItens Reparados!");
				}
			}
		}
	}
}
