package me.everton.pvp.cmds;

import java.util.ArrayList;

import me.everton.pvp.API;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChat implements CommandExecutor{
	public static ArrayList<String> onChat = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game!");
			return true;
		}
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("sc") || label.equalsIgnoreCase("s") || label.equalsIgnoreCase("ac")) {
			if (!p.hasPermission("pvp.staffchat")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			if(args.length == 0) {
				if(onChat.contains(p.getName())) {
					API.broadcastMessage("§7[" + ChatColor.stripColor(p.getPlayerListName()) + " saiu do Staff Chat]", "pvp.staffchat");
					onChat.remove(p.getName());
				} else {
					API.broadcastMessage("§7[" + ChatColor.stripColor(p.getPlayerListName()) + " entrou no Staff Chat]", "pvp.staffchat");
					onChat.add(p.getName());
				}
			} else {
				String msg = "";
				for(int i = 0; i < args.length; i++) {
					msg += args[i] + " ";
				}
				
				sendMessage(p, msg);
			}
		}
		return false;
	}
	
	public static void sendMessage(Player sender, String msg) {
		API.broadcastMessage("§7[§e§lStaff§7] §r" + sender.getDisplayName() + "§7: §r" + msg, "pvp.staffchat");
	}
}
