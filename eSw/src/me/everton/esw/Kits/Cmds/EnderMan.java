package me.everton.esw.Kits.Cmds;

import me.everton.esw.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class EnderMan implements CommandExecutor{
	public static Configuration pl = Main.getPlugin().getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("§3§l[eSw] §cComando apenas in-game!");
			return true;
		}
		
		if(label.equalsIgnoreCase("enderman")){
			Player p = (Player) sender;
			if(!p.hasPermission("kit.enderman")){
				p.sendMessage(pl.getString("Nao_Possui_Kit").replace("%KIT%", "Enderman"));
				return true;
			}
			me.everton.esw.Kits.Listeners.EnderMan.addKit(p);
			p.sendMessage(pl.getString("Selecionou_Kit").replace("%KIT%", "Enderman"));
		}
		return false;
	}

}
