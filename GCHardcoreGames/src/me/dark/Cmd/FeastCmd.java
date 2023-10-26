package me.dark.Cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dark.Main;
import me.dark.Game.Feast;
import me.dark.Game.GameState;

public class FeastCmd implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (Main.estado==GameState.PREGAME) return true;
			if (Feast.feastLoc != null) {
				p.setCompassTarget(Feast.feastLoc);
				p.sendMessage(Main.prefix_bussola+"ß3o ß7feast");
			} else {
				p.sendMessage(Main.prefix+"O feast aina n√o saiu!");
			}
		}
		return false;
	}

}
