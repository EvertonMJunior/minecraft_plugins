package me.everton.WocPvP.Comandos;

import java.util.ArrayList;

import me.everton.WocPvP.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChat implements CommandExecutor, Listener {
	public static ArrayList<Player> sc = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("sc")) {
			Player p = (Player) sender;
			if (p.hasPermission("wocpvp.chatadm")) {
				if (args.length == 0) {
					if (sc.contains(p)) {
						p.sendMessage("§7[§c-§7] §6Você saiu do §lCHAT STAFF");
						sc.remove(p);
					} else if (!sc.contains(p)) {
						p.sendMessage("§7[§2+§7] §6Você entrou no §lCHAT STAFF");
						sc.add(p);
					}
				} else if (args.length >= 1) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						sb.append(args[i] + " ");
					}
					String allArgs = sb.toString().trim();
					Main.log(p.getName()
							+ " enviou uma mensagem no chat Staff: " + allArgs);
					for (Player on : Bukkit.getOnlinePlayers()) {
						if (on.hasPermission("wocpvp.sc")) {
							on.sendMessage("§7<§b§lSTAFF§r§7> "
									+ p.getDisplayName() + "§6§l >> §r§f"
									+ allArgs);
						}
					}
				}

			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}

	@EventHandler
	public void EventoChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (sc.contains(p)) {
			e.setCancelled(true);
			Bukkit.broadcast("§7<§b§lSTAFF§r§7> " + p.getDisplayName()
					+ "§6§l >> §r§f" + e.getMessage(), "wocpvp.sc");
			Main.log(p.getName() + " enviou uma mensagem no chat Staff: "
					+ e.getMessage());
		}
	}
}
