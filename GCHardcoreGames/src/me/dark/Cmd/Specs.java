package me.dark.Cmd;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dark.Main;
import me.dark.Utils.Vanish;

public class Specs implements CommandExecutor{
	public static ArrayList<Player> specsOn = new ArrayList<Player>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission(Main.perm_mod)) return true;
			if (args.length == 0) {
				p.sendMessage(Main.prefix+"Use /specs <on | off>");
			} else {
				if (args[0].equalsIgnoreCase("on")) {
					specsOn.add(p);
					Vanish.update(p);
					p.sendMessage("§bEspectadores ativados!");
				} else if (args[0].equalsIgnoreCase("off")) {
					specsOn.remove(p);
					Vanish.update(p);
					p.sendMessage("§bEspectadores desativados!");
				} else {
					p.sendMessage(Main.prefix+"Use /specs <on | off>");
				}
			}
		}
		return false;
	}

}
