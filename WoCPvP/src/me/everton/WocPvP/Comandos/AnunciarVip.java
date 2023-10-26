package me.everton.WocPvP.Comandos;

import me.everton.WocPvP.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnunciarVip implements CommandExecutor {
	public static String vipativo;

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (sender instanceof Player) {
			sender.sendMessage("Comando apenas para console!");
			return true;
		}
		if (label.equalsIgnoreCase("anunciovip")) {
			String vipativo1 = args[1];
			if (vipativo1.equalsIgnoreCase("vip")) {
				vipativo = "§aVip";
			} else if (vipativo1.equalsIgnoreCase("pro")) {
				vipativo = "§6Pro";
			} else if (vipativo1.equalsIgnoreCase("tvip")) {
				vipativo = "§9§oTVip";
			}
			for (Player on : Bukkit.getServer().getOnlinePlayers()) {
				on.sendMessage("§6[WoCPvP] §b" + args[0] + " ativou um "
						+ vipativo);
				Main.log(args[0] + " ativou um " + vipativo);
			}
		}
		if (label.equalsIgnoreCase("anunciounban")) {
			for (Player on : Bukkit.getServer().getOnlinePlayers()) {
				on.sendMessage("§6[WoCPvP] §b" + args[0] + " comprou Unban");
				Main.log(args[0] + " comprou unban.");
			}
		}
		if (label.equalsIgnoreCase("anunciokit")) {
			for (Player on : Bukkit.getServer().getOnlinePlayers()) {
				on.sendMessage("§6[WoCPvP] §b" + args[0] + " ativou o kit "
						+ args[1].toUpperCase());
				Main.log(args[0] + " ativou um kit " + args[1].toUpperCase());
			}
		}
		if (label.equalsIgnoreCase("anunciodoacao")) {
			for (Player on : Bukkit.getServer().getOnlinePlayers()) {
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String msg = sb.toString().trim();
				on.sendMessage("§6[WoCPvP] §b" + args[0]
						+ " fez uma doação de " + msg + " reais ao servidor!");
				Main.log(args[0] + " doou " + msg + ".");
			}
		}
		return false;
	}
}
