package me.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Fly
	implements CommandExecutor, Listener
	{
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel[]args)
	{
	  if(!(sender instanceof Player))
	  {
	     sender.SendMessage("");
	     return true; 
	  }
	  if(cmd.getName().equalsIgnoreCase("fly"))
      {
		  Player player = (Player)sender;
		  if(args.length == 0) {
			  if(sender.hasPermission(eutils.fly))
			  {
				  if(!player.getAllowFlight())
				  {
					  player.sendMessage("&6Fly ativado.");
					  player.setAllowFlight(true);
				  }
	     		  else
				  {
					  player.sendMessage("&6Fly desativado");
					  player.setAllowFlight(false);
				  }
			  }
			  else
			  {
				  player.sendMessage("§cVoce nao tem permissao :(");
				  return true;
						  
			  }
		  }
		  else
		  {
			  player.sendMessage("§cVoce nao tem permissao :(");
			  return true;
		  }
      }
	  return true;
	}
	
		 
	
	
