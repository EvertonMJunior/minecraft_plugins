package me.dark.Cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Utils.DarkUtils;

public class Tempo implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission(Main.perm_modplus)) {
			if (args.length == 0) {
				sender.sendMessage("§cUse: /tempo <tempo>");
			} else {
				if (Main.estado == GameState.PREGAME) {
					if (DarkUtils.isInteger(args[0])) {
						Main.toStart = Integer.valueOf(args[0]);
						sender.sendMessage(Main.prefix+"Tempo mudado!");	
					} else {
						sender.sendMessage("§cUse: /tempo <tempo>");
					}
				} else {
					if (DarkUtils.isInteger(args[0])) {
						Main.gameTime = Integer.valueOf(args[0]);
						sender.sendMessage(Main.prefix+"Tempo mudado!");	
					} else {
						sender.sendMessage("§cUse: /tempo <tempo>");
					}
				}
			}
		}
		return false;
	}

}
