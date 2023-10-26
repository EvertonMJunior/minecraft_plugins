package me.everton.WocPvP.Comandos;

import java.util.ArrayList;

import me.everton.WocPvP.Main;
import me.everton.WocPvP.Eventos.EManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Specs implements CommandExecutor, Listener {
	public static ArrayList<Player> vendospecs = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("specs")) {
			if (p.hasPermission("wocpvp.spec")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "Use /specs (on/off)");
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("on")) {
						if (!vendospecs.contains(p)) {
							p.sendMessage(ChatColor.RED
									+ "Agora você pode ver espectadores!");
							Main.log(p.getName() + " ativou os espectadores!.");
							vendospecs.add(p);
							for (Player admin : Admin.vis) {
								p.showPlayer(admin);
							}
							for (Player specs : Spec.specs) {
								p.showPlayer(specs);
							}
							for (Player specse : EManager.pespec) {
								p.showPlayer(specse);
							}
						} else {
							p.sendMessage(ChatColor.RED
									+ "Você já está vendo espectadores! Use /specs off para não vê-los!");
						}
					} else if (args[0].equalsIgnoreCase("off")) {
						if (vendospecs.contains(p)) {
							p.sendMessage(ChatColor.RED
									+ "Agora você não pode ver espectadores!");
							Main.log(p.getName()
									+ " desativou os espectadores.");
							vendospecs.remove(p);
							for (Player admin : Admin.admin) {
								p.hidePlayer(admin);
							}
							for (Player specs : Spec.specs) {
								p.hidePlayer(specs);
							}
							for (Player specse : EManager.pespec) {
								p.hidePlayer(specse);
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
