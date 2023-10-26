package me.everton.pvp.cmds;

import java.util.ArrayList;

import me.everton.pvp.API;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Build implements CommandExecutor, Listener {
	public static ArrayList<String> buildOn = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando In-Game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("build")) {
			if (!p.hasPermission("pvp.cmd.build")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("ferramentas")) {
					p.getInventory().clear();
					p.getInventory().setItem(0, API.item(Material.WOOD_AXE, 1, "§6§lMachado do WE"));
					p.getInventory().setItem(1, API.item(Material.ARROW, 1, "§7§lFlecha do VS"));
					p.getInventory().setItem(2, API.item(Material.SULPHUR, 1, "§8§lPólvora do VS"));
					p.updateInventory();
					p.sendMessage("§7> §eVocê ganhou as ferramentas para Construçao!");
				} else {
					p.sendMessage("§cUse /build ferramentas");
				}
			} else {
				if (buildOn.contains(p.getName())) {
					p.sendMessage("§7[§a!§7] Modo §cBuild §7desativado.");
					buildOn.remove(p.getName());
				} else {
					p.sendMessage("§7[§a!§7] Modo §cBuild §7ativado.");
					buildOn.add(p.getName());
				}
			}
		}
		return false;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(!buildOn.contains(p.getName())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(!buildOn.contains(p.getName())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent e) {
		Player p = e.getPlayer();
		if(!buildOn.contains(p.getName())) {
			e.setCancelled(true);
			e.setInstaBreak(false);
		}
	}
}
