package me.everton.jpvp.comandos;

import me.everton.jpvp.kits.KitManager;
import me.everton.jpvp.utils.Hologram;
import me.everton.jpvp.utils.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCmd extends KitManager implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cEste comando está habilitado somente para §4JOGADORES§c!");
			return true;
		}

		Player p = (Player) sender;

		if (args.length == 0) {
			Hologram.showLine(p.getLocation().clone().add(0, 2, 0), "§aOi");
		} else {
			p.sendMessage("§7Detalhes do jogador §a"
					+ args[0]
					+ "§7: \n§7UUID: §a"
					+ UUID.get(args[0])
					+ " \n§7Comprou Minecraft: §a"
					+ (new Boolean(UUID
							.readUrl("https://minecraft.net/haspaid.jsp?user="
									+ args[0])) ? "Nao" : "Sim"));
		}

		return false;
	}
}
