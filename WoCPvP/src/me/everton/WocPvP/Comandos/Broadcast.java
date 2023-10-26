package me.everton.WocPvP.Comandos;

import me.everton.WocPvP.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Broadcast implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if ((label.equalsIgnoreCase("bc"))
				|| (label.equalsIgnoreCase("broadcast"))) {

			if (!(sender instanceof Player)) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String argumentos = sb.toString().trim();
				for (Player on : Bukkit.getOnlinePlayers()) {
					on.sendMessage("§6[WoCPvP] §3§l"
							+ argumentos.replace("&", "§"));
				}
				sender.sendMessage("§aBroadcast Enviada!");
				return true;
			}

			if (sender.hasPermission("wocpvp.bc")) {

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String argumentos = sb.toString().trim();
				for (Player on : Bukkit.getOnlinePlayers()) {
					on.sendMessage("§6[WoCPvP] §3§l"
							+ argumentos.replace("&", "§"));
				}
				Main.log(sender.getName() + " enviou um broadcast: "
						+ argumentos);
			} else {
				sender.sendMessage(ChatColor.DARK_RED
						+ "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}
}
