package me.dark.Cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;
import me.dark.kit.KitManager;

public class SetKit implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission(Main.perm_modplus)) {
			if (args.length==0) {
				sender.sendMessage("§cUse /setkit <player> <kit>");
			} else if (args.length==1) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					Kit k = KitManager.getKitByString(args[0]);
		    		if (k!=null) {
		    			for (Kit kk : Kit.kits) {
		    				kk.removePlayer(p);
		    			}
		    			k.addPlayer(p);
		    			if (Main.estado != GameState.PREGAME) {
		                	if (k.getItems() != null) {
		                		for (ItemStack items : k.getItems()) {
		                			if (items != null) {
		                				p.getInventory().addItem(items);
		                			}
		                		}
		                	}
		    			}
		    			sender.sendMessage("§bVocê setou o seu kit para §3"+k.toString()+"§b!");
		    		} else {
		    			sender.sendMessage(Main.prefix+"Kit não encontrado");
		    		}
				} else {
					sender.sendMessage("§cUse /setkit <player> <kit>");
				}
		    } else {
		    	Player p = Bukkit.getPlayerExact(args[0]);
		    	if (p!=null) {
		    		Kit k = KitManager.getKitByString(args[1]);
		    		if (k!=null) {
				    	if (p==sender) {
				    		p.chat("/setkit "+args[1]);
				    		return true;
				    	}
		    			for (Kit kk : Kit.kits) {
		    				kk.removePlayer(p);
		    			}
		    			k.addPlayer(p);
		    			if (Main.estado != GameState.PREGAME) {
		                	if (k.getItems() != null) {
		                		for (ItemStack items : k.getItems()) {
		                			if (items != null) {
		                				p.getInventory().addItem(items);
		                			}
		                		}
		                	}
		    			}
		    			sender.sendMessage("§bVocê setou o kit de "+p.getName()+" para §3"+k.toString()+"§b!");
		    		} else {
		    			sender.sendMessage(Main.prefix+"Kit não encontrado");
		    		}
		    	} else {
		    		sender.sendMessage(Main.not_found);
		    	}
		    }
			
		}
		return false;
	}

}
