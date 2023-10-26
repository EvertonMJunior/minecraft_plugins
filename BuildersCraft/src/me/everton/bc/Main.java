package me.everton.bc;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin{
	public static Plugin plugin;
	public static ConsoleCommandSender ccs;
	public static BukkitScheduler sh;
	
	public void onEnable() {
		plugin = this;
		ccs = Bukkit.getConsoleSender();
		sh = Bukkit.getScheduler();
		
		ccs.sendMessage("        §7[§5§leBuildersCraft§7]");
		ccs.sendMessage("        §cPlugin Habilitado!");
		ccs.sendMessage("        §cBoas construcoes! ;3");
		ccs.sendMessage("        §cDesenvolvido por EvertonPvP");
		ccs.sendMessage("        §7[§5§leBuildersCraft§7]");
		
		BossBarMsgs.msgs = new String[] {"§5§l#CONSTRUINDO", "§7§lhttp://bit.ly/BuildersCraft"};
	}
	
	public void onDisable() {
		ccs.sendMessage("        §7[§5§leBuildersCraft§7]");
		ccs.sendMessage("        §cPlugin Desabilitado!");
		ccs.sendMessage("        §cBoas construcoes na proxima vez! ;3");
		ccs.sendMessage("        §cDesenvolvido por EvertonPvP");
		ccs.sendMessage("        §7[§5§leBuildersCraft§7]");
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
}
