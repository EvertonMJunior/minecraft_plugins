package me.everton.epvp.Comandos;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Duvida implements CommandExecutor {
	public static HashMap<String, String> duvidas = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("duvida")) {
			if (args.length > 0) {
				p.sendMessage("§2§lSua duvida foi enviada!");

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String duvida = sb.toString().trim();

				duvidas.put(p.getName(), duvida);

				for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
					if (staff.hasPermission("pvp.admin")) {
						staff.sendMessage("                §f§l******§c§l DUVIDA DE "
								+ p.getName().toUpperCase() + " §f§l******");
						staff.sendMessage(" ");
						staff.sendMessage("§6" + duvidas.get(p.getName()));
						staff.sendMessage(" ");
						staff.sendMessage("§cUse /rd " + p.getName()
								+ " (resposta)");
						staff.sendMessage(" ");
						staff.sendMessage("                §f§l******§c§l DUVIDA DE "
								+ p.getName().toUpperCase() + " §f§l******");
					}
				}
			} else {
				p.sendMessage("§cUse: /duvida (duvida)");
			}
		}

		if (label.equalsIgnoreCase("rd")) {
			if (p.hasPermission("pvp.admin")) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("list")) {
						p.sendMessage("§6" + duvidas.toString());
					}
				} else if (args.length >= 2) {

					StringBuilder sb = new StringBuilder();
					for (int i = 1; i < args.length; i++) {
						sb.append(args[i] + " ");
					}
					String duvidar = sb.toString().trim();

					Player target = Bukkit.getServer().getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					if (duvidas.containsKey(target.getName())) {
						p.sendMessage("§2§lDuvida Respondida!");
						target.sendMessage("                §f§l******§2§l DUVIDA RESPONDIDA §f§l******");
						target.sendMessage(" ");
						target.sendMessage("§7Respondido por "
								+ p.getDisplayName());
						target.sendMessage(" ");
						target.sendMessage("§7Resposta: §6" + duvidar);
						target.sendMessage(" ");
						target.sendMessage("                §f§l******§2§l DUVIDA RESPONDIDA §f§l******");
						duvidas.remove(target.getName());
					} else {
						p.sendMessage("§cEste player nao enviou nenhuma duvida ou ela ja foi respondida!");
					}
				} else {
					p.sendMessage("§cUse: /rd (jogador) (resposta)");
				}
			} else {
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}
}
