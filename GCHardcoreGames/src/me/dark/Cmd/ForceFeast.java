package me.dark.Cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dark.Main;
import me.dark.Game.Feast;

public class ForceFeast implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission(Main.perm_modplus)) {
				Feast.spawnFeast(p.getLocation());
				Feast.spawnChests(p.getLocation().add(0,1,0));
				p.sendMessage("§bO feast foi spawnado com sucesso!");
			}
		}
		return false;
	}

}
