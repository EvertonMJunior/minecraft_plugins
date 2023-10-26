package me.everton.EPVP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TagLogin implements Listener {
	//Coloca a tag automaticamente ao logar
		@EventHandler
		public void TagAoLogar(PlayerJoinEvent e){

		Player p = e.getPlayer();

		if(p.hasPermission("*")){
			p.setDisplayName(ChatColor.DARK_RED + p.getName() + ChatColor.WHITE);
			p.setPlayerListName(ChatColor.DARK_RED + p.getName() + ChatColor.WHITE);
		} else if(p.hasPermission("tag.dono")){
			p.setDisplayName(ChatColor.DARK_RED + p.getName() + ChatColor.WHITE);
			p.setPlayerListName(ChatColor.DARK_RED + p.getName() + ChatColor.WHITE);
		} else if(p.hasPermission("tag.admin")){
			p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.WHITE);
			p.setPlayerListName(ChatColor.RED + p.getName() + ChatColor.WHITE);
		} else if(p.hasPermission("tag.mod")){
			p.setDisplayName(ChatColor.DARK_PURPLE + p.getName() + ChatColor.WHITE);
			p.setPlayerListName(ChatColor.DARK_PURPLE + p.getName() + ChatColor.WHITE);
		} else if(p.hasPermission("tag.trial")){
			p.setDisplayName(ChatColor.DARK_GREEN + p.getName() + ChatColor.WHITE);
			p.setPlayerListName(ChatColor.DARK_GREEN + p.getName() + ChatColor.WHITE);
		} else if(p.hasPermission("tag.youtuber")){
			p.setDisplayName(ChatColor.AQUA + p.getName() + ChatColor.WHITE);
			p.setPlayerListName(ChatColor.AQUA + p.getName() + ChatColor.WHITE);
		} else if(p.hasPermission("tag.vip+")){
			p.setDisplayName(ChatColor.BLUE + p.getName() + ChatColor.WHITE);
			p.setPlayerListName(ChatColor.BLUE + p.getName() + ChatColor.WHITE);
		} else if(p.hasPermission("tag.vip")){
			p.setDisplayName(ChatColor.GREEN + p.getName() + ChatColor.WHITE);
			p.setPlayerListName(ChatColor.GREEN + p.getName() + ChatColor.WHITE);
		} else if(p.hasPermission("tag.normal")){
			p.setDisplayName(ChatColor.GRAY + p.getName() + ChatColor.GRAY);
			p.setPlayerListName(ChatColor.GRAY + p.getName() + ChatColor.GRAY);
		}

		}
}
