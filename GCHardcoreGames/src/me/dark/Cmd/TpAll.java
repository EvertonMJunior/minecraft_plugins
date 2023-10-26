package me.dark.Cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dark.Main;

public class TpAll implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String tag, String[] args) {
			if(!(sender instanceof Player)) {
				return true;
			}
			Player p = (Player) sender;
			if(tag.equalsIgnoreCase("tpall")) {
				if(!p.hasPermission(Main.perm_modplus)) {
					return true;
				}
				if(args.length == 0) {
					p.sendMessage("§bVocê teleportou todos os jogadores!");
					for(Player on : Bukkit.getOnlinePlayers()) {
						if (on!=p) {
							on.teleport(p.getLocation());
							on.sendMessage("§cTodos os jogadores foram teleportados por " + p.getName());	
						}
					}
				}
			}
			return false;
	}

}
