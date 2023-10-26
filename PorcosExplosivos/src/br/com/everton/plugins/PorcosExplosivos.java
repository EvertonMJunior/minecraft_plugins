package br.com.everton.plugins;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PorcosExplosivos extends JavaPlugin {
	
	public void onEnable(){
		System.out.printIn("[dima] Plugin inicializado");
	}
	
	public void onDisable(){
		System.out.println("[dima] Plugin desabilitado");
	}
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(command.getName().equalsIgnoreCase("dima")){
			Player p = (Player)sender;
			p.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
			p.updateInventory();
			p.sendMessage("§c§lVocê ganhou 1 diamante.");
		}
		return true;
	}
}
