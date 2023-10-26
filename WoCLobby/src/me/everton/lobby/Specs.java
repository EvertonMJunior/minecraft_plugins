package me.everton.lobby;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Specs implements CommandExecutor, Listener {
	public static ArrayList<String> vendospecs = new ArrayList<String>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("specs")) {
			if (p.hasPermission("woc.spec")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "Use /specs (on/off)");
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("on")) {
						if (!vendospecs.contains(p.getName())) {
							p.sendMessage(ChatColor.RED
									+ "Agora você pode ver espectadores!");
							vendospecs.add(p.getName());
							for (String admins : AdminMode.admins) {
								if(Bukkit.getPlayerExact(admins) != null) {
									Player admin = Bukkit.getPlayerExact(admins);
									p.showPlayer(admin);
								}
							}
						} else {
							p.sendMessage(ChatColor.RED
									+ "Você já está vendo espectadores! Use /specs off para não vê-los!");
						}
					} else if (args[0].equalsIgnoreCase("off")) {
						if (vendospecs.contains(p.getName())) {
							p.sendMessage(ChatColor.RED
									+ "Agora você não pode ver espectadores!");
							vendospecs.remove(p.getName());
							for (String admins : AdminMode.admins) {
								if(Bukkit.getPlayerExact(admins) != null) {
									Player admin = Bukkit.getPlayerExact(admins);
									p.hidePlayer(admin);
								}
							}
						} else {
							p.sendMessage(ChatColor.RED
									+ "Você já não está vendo espectadores! Use /specs on para vê-los!");
						}
					}
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}
}
