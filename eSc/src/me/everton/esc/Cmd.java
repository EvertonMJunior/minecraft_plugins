package me.everton.esc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor {
	public static ArrayList<String> chat = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cComando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("sc")) {
			if (!p.hasPermission(Main.config.getString("Permissao"))) {
				p.sendMessage(Main.config.getString("Sem_Permissao").replace(
						"&", "§"));
				return true;
			}
			if (args.length == 0) {
				if (chat.contains(p.getName())) {
					chat.remove(p.getName());
					p.sendMessage(Main.config.getString("Msg_Saiu_Sc").replace(
							"&", "§"));
				} else {
					chat.add(p.getName());
					p.sendMessage(Main.config.getString("Msg_Entrou_Sc")
							.replace("&", "§"));
				}
			} else {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String allArgs = sb.toString().trim();

				for (Player on : Bukkit.getOnlinePlayers()) {
					if (on.hasPermission(Main.config.getString("Permissao"))) {
						on.sendMessage(Main.config.getString("Formato")
								.replace("%NICK%", p.getDisplayName())
								.replace("%MSG%", allArgs).replace("&", "§"));
					}
				}
			}
		}
		return false;
	}

}
