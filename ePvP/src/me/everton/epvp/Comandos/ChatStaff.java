package me.everton.epvp.Comandos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatStaff implements CommandExecutor, Listener {
	public static ArrayList<String> sc = new ArrayList<>();
	public static ArrayList<String> scoff = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		
		if ((cmd.getName().equalsIgnoreCase("sc")) || (cmd.getName().equalsIgnoreCase("ac"))) {
			if (p.hasPermission("pvp.sc")) {
				if (args.length == 0) {
					if (sc.contains(p.getName())) {
						p.sendMessage("§7[§c-§7] §6Você saiu do §lCHAT STAFF");
						sc.remove(p.getName());
					} else if (!sc.contains(p.getName())) {
						p.sendMessage("§7[§2+§7] §6Você entrou no §lCHAT STAFF");
						sc.add(p.getName());
					}
				} else if (args.length >= 1) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						sb.append(args[i] + " ");
					}
					String allArgs = sb.toString().trim();
					for (Player on : Bukkit.getOnlinePlayers()) {
						if (on.hasPermission("pvp.sc")) {
							if(!scoff.contains(on.getName())) {
							on.sendMessage("§7<§b§lSTAFF§r§7> "
									+ p.getDisplayName() + "§6§l >> §r§f"
									+ allArgs);
							}
						}
					}
				}

			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		if(label.equalsIgnoreCase("togglesc")) {
			if(!p.hasPermission("pvp.sc")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			if(scoff.contains(p.getName())) {
				scoff.remove(p.getName());
				p.sendMessage("§7[§a!§7] Seu StaffChat foi habilitado!");
			} else {
				scoff.add(p.getName());
				p.sendMessage("§7[§c!§7] Seu StaffChat foi desabilitado!");
			}
		}
		return false;
	}

	@EventHandler
	public void EventoChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (sc.contains(p.getName())) {
			e.setCancelled(true);
			for(Player on : Bukkit.getOnlinePlayers()) {
				if(on.hasPermission("pvp.sc")) {
					if(!scoff.contains(on.getName())) {
						on.sendMessage("§7<§b§lSTAFF§r§7> " + p.getDisplayName()
						+ "§6§l >> §r§f" + e.getMessage());
					}
				}
			}
		}
	}
}
