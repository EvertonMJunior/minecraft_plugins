package me.everton.EPVP;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tag implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
//Lista de Tags	(/tag)	
		if(label.equalsIgnoreCase("tag")){
			if(args.length == 0){
				sender.sendMessage(ChatColor.WHITE + "********************");
				if(sender.hasPermission("*")){
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_RED + "Dono");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.RED + "Admin");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_PURPLE + "Mod");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_GREEN + "Trial");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.AQUA + "Youtuber");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.BLUE + "Vip+");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Vip");
					
				} else if(sender.hasPermission("tag.dono")){
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_RED + "Dono");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.RED + "Admin");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_PURPLE + "Mod");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_GREEN + "Trial");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.AQUA + "Youtuber");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.BLUE + "Vip+");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Vip");
					
				} else if(sender.hasPermission("tag.admin")){
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.RED + "Admin");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_PURPLE + "Mod");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_GREEN + "Trial");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.AQUA + "Youtuber");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.BLUE + "Vip+");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Vip");
					
				} else if(sender.hasPermission("tag.mod")){
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_PURPLE + "Mod");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_GREEN + "Trial");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.AQUA + "Youtuber");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.BLUE + "Vip+");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Vip");
					
				} else if(sender.hasPermission("tag.trial")){
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_GREEN + "Trial");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.AQUA + "Youtuber");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.BLUE + "Vip+");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Vip");
					
				} else if(sender.hasPermission("tag.youtuber")){
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.AQUA + "Youtuber");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.BLUE + "Vip+");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Vip");
					
				} else if(sender.hasPermission("tag.vip+")){
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.BLUE + "Vip+");
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Vip");
					
				} else if(sender.hasPermission("tag.vip")){
					sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + "Vip");
					
				}
				sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.GRAY + ChatColor.ITALIC + "Normal");
				sender.sendMessage(ChatColor.WHITE + "********************");
				return true;
			}
			String tag = args[0];
			Player p = (Player) sender;
//Definir Tag (/tag [tag])
			if(args.length == 1){
				if(tag.equalsIgnoreCase("dono")){
					if(sender.hasPermission("tag.dono")){
						p.setDisplayName(ChatColor.DARK_RED + p.getName() + ChatColor.WHITE);
						p.setPlayerListName(ChatColor.DARK_RED + p.getName() + ChatColor.WHITE);
						sender.sendMessage(ChatColor.GRAY + "Agora você está usando a tag " + ChatColor.DARK_RED + "DONO");
						return true;
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Você não tem permissão!");
					return true;
				}
			}
				if(tag.equalsIgnoreCase("admin")){
					if((sender.hasPermission("tag.admin") || sender.hasPermission("tag.dono"))){
						p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.WHITE);
						p.setPlayerListName(ChatColor.RED + p.getName() + ChatColor.WHITE);
						sender.sendMessage(ChatColor.GRAY + "Agora você está usando a tag " + ChatColor.RED + "ADMIN");
						return true;
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Você não tem permissão!");
					return true;
				}
			}
				if(tag.equalsIgnoreCase("mod")){
					if((sender.hasPermission("tag.mod")) || sender.hasPermission("tag.admin") || sender.hasPermission("tag.dono")){
						p.setDisplayName(ChatColor.DARK_PURPLE + p.getName() + ChatColor.WHITE);
						p.setPlayerListName(ChatColor.DARK_PURPLE + p.getName() + ChatColor.WHITE);
						sender.sendMessage(ChatColor.GRAY + "Agora você está usando a tag " + ChatColor.DARK_PURPLE + "MOD");
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Voce não tem permissão!");
				}
			}
				if(tag.equalsIgnoreCase("trial")){
					if((sender.hasPermission("tag.trial") || sender.hasPermission("tag.mod") || sender.hasPermission("tag.admin") || sender.hasPermission("tag.dono"))){
						p.setDisplayName(ChatColor.DARK_GREEN + p.getName() + ChatColor.WHITE);
						p.setPlayerListName(ChatColor.DARK_GREEN + p.getName() + ChatColor.WHITE);
						sender.sendMessage(ChatColor.GRAY + "Agora você está usando a tag " + ChatColor.DARK_GREEN + "TRIAL");
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Voce não tem permissão!");
				}
			}
				if(tag.equalsIgnoreCase("youtuber")){
					if((sender.hasPermission("tag.youtuber") || sender.hasPermission("tag.trial") || sender.hasPermission("tag.mod") || sender.hasPermission("tag.admin") || sender.hasPermission("tag.dono"))){
						p.setDisplayName(ChatColor.AQUA + p.getName() + ChatColor.WHITE);
						p.setPlayerListName(ChatColor.AQUA + p.getName() + ChatColor.WHITE);
						sender.sendMessage(ChatColor.GRAY + "Agora você está usando a tag " + ChatColor.AQUA + "YOUTUBER");
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Voce não tem permissão!");
				}
			}
				if(tag.equalsIgnoreCase("vip+")){
					if((sender.hasPermission("tag.vip+")) || sender.hasPermission("tag.youtuber") || sender.hasPermission("tag.trial") || sender.hasPermission("tag.mod") || sender.hasPermission("tag.admin") || sender.hasPermission("tag.dono")){
						p.setDisplayName(ChatColor.BLUE + p.getName() + ChatColor.WHITE);
						p.setPlayerListName(ChatColor.BLUE + p.getName() + ChatColor.WHITE);
						sender.sendMessage(ChatColor.GRAY + "Agora você está usando a tag " + ChatColor.BLUE + "VIP+");
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Voce não tem permissão!");
				}
			}
				if(tag.equalsIgnoreCase("vip")){
					if((sender.hasPermission("tag.vip") || sender.hasPermission("tag.vip+") || sender.hasPermission("tag.youtuber") || sender.hasPermission("tag.trial") || sender.hasPermission("tag.mod") || sender.hasPermission("tag.admin") || sender.hasPermission("tag.dono"))){
						p.setDisplayName(ChatColor.GREEN + p.getName() + ChatColor.WHITE);
						p.setPlayerListName(ChatColor.GREEN + p.getName() + ChatColor.WHITE);
						sender.sendMessage(ChatColor.GRAY + "Agora você está usando a tag " + ChatColor.GREEN + "VIP");
					} else {
					sender.sendMessage(ChatColor.DARK_RED + "Voce não tem permissão!");
				}
				}
				if(tag.equalsIgnoreCase("normal")){
						p.setDisplayName(ChatColor.GRAY + p.getName() + ChatColor.GRAY);
						p.setPlayerListName(ChatColor.GRAY + p.getName() + ChatColor.GRAY);
						sender.sendMessage(ChatColor.GRAY + "Agora você está usando a tag " + ChatColor.GRAY + ChatColor.ITALIC + "NORMAL");
				}
			}
		}
		return false;
	}

}