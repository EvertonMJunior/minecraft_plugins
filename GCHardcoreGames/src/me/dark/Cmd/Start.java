package me.dark.Cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Game.Timer.GameTimer;

public class Start implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission(Main.perm_modplus)) {
			if (Main.estado == GameState.PREGAME) {
				GameTimer.start();
			} else {
				sender.sendMessage(Main.prefix+"O jogo já iniciou!");
			}
		}
		return false;
	}

}
