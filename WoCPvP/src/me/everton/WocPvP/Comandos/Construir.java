package me.everton.WocPvP.Comandos;

import java.util.ArrayList;

import me.everton.WocPvP.Main;

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

public class Construir implements CommandExecutor, Listener {
	public static ArrayList<Player> construir = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("construir")) {
			Player p = (Player) sender;
			if (p.hasPermission("wocpvp.build")) {
				if (args.length == 0) {
					if (construir.contains(p)) {
						construir.remove(p);
						p.sendMessage(ChatColor.RED
								+ "Modo construir desativado!");
						Main.log(p.getName() + " saiu do modo Construir.");
						p.setGameMode(GameMode.SURVIVAL);
					} else if (!construir.contains(p)) {
						construir.add(p);
						p.sendMessage(ChatColor.GREEN
								+ "Modo construir ativado!");
						Main.log(p.getName() + " entrou no modo Construir.");
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
		if (p.hasPermission("wocpvp.build")) {
			if (!construir.contains(p)) {
				e.setCancelled(true);
			}
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void EventoBotar(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("wocpvp.build")) {
			if (!construir.contains(p)) {
				e.setCancelled(true);
			}
		} else {
			e.setCancelled(true);
		}
	}
}
