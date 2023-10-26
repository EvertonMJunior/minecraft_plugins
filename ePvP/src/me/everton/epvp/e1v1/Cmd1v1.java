package me.everton.epvp.e1v1;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd1v1 implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		
		if(label.equalsIgnoreCase("1v1")) {
			Main1v1.entrarOuSair1v1((Player) sender);
		}
		return false;
	}

}
