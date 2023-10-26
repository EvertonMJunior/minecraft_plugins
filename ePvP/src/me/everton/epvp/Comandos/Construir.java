package me.everton.epvp.Comandos;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class Construir implements CommandExecutor, Listener {
	public static ArrayList<String> construir = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("build")) {
			Player p = (Player) sender;
			if (p.hasPermission("pvp.build")) {
				if (args.length == 0) {
					if (construir.contains(p.getName())) {
						construir.remove(p.getName());
						p.sendMessage(ChatColor.RED
								+ "Modo construir desativado!");
						p.setGameMode(GameMode.SURVIVAL);
					} else if (!construir.contains(p.getName())) {
						construir.add(p.getName());
						p.sendMessage(ChatColor.GREEN
								+ "Modo construir ativado!");						
						p.setGameMode(GameMode.CREATIVE);
					}
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}

	@EventHandler
	public void EventoQuebrar(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("pvp.build")) {
			if (!construir.contains(p.getName())) {
				e.setCancelled(true);
			}
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void EventoBotar(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("pvp.build")) {
			if (!construir.contains(p.getName())) {
				e.setCancelled(true);
			}
		} else {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void bucketEmpty(PlayerBucketEmptyEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("pvp.build")) {
			if (!construir.contains(p.getName())) {
				e.setCancelled(true);
			}
		} else {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void bucketFill(PlayerBucketFillEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("pvp.build")) {
			if (!construir.contains(p.getName())) {
				e.setCancelled(true);
			}
		} else {
			e.setCancelled(true);
		}
	}
}
