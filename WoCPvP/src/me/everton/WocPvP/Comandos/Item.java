package me.everton.WocPvP.Comandos;

import me.everton.WocPvP.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Item implements CommandExecutor {
	public static FileConfiguration itens = Main.settings.getItens();

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("item")) {
			for (int i = 0; i < Integer.valueOf(itens.getString(p.getName())
					+ ".forca"); i++) {
				p.sendMessage("Forca");
			}
		}
		return false;
	}

}
