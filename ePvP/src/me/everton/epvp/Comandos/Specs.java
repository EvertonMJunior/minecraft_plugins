package me.everton.epvp.Comandos;

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
			if (p.hasPermission("pvp.spec")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "Use /specs (on/off)");
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("on")) {
						if (!vendospecs.contains(p.getName())) {
							p.sendMessage(ChatColor.RED
									+ "Agora você pode ver espectadores!");
							vendospecs.add(p.getName());
							for (String admins : Admin.vis) {
								if(Bukkit.getPlayerExact(admins) != null) {
									Player admin = Bukkit.getPlayerExact(admins);
									p.showPlayer(admin);
								}
							}
							for (String specss : Spec.specs) {
								if(Bukkit.getPlayerExact(specss) != null) {
									Player specs = Bukkit.getPlayerExact(specss);
									p.showPlayer(specs);
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
							for (String admins : Admin.admins) {
								if(Bukkit.getPlayerExact(admins) != null) {
									Player admin = Bukkit.getPlayerExact(admins);
									p.hidePlayer(admin);
								}
							}
							for (String specss : Spec.specs) {
								if(Bukkit.getPlayerExact(specss) != null) {
									Player specs = Bukkit.getPlayerExact(specss);
									p.hidePlayer(specs);
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
