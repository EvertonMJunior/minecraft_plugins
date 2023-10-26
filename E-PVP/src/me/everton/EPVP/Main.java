package me.everton.EPVP;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new TagLogin(), this);
		getServer().getPluginManager().registerEvents(new Admin(), this);
		getCommand("tag").setExecutor(new Tag());
		getCommand("admin").setExecutor(new Admin());
		getCommand("v").setExecutor(new Admin());
		getLogger().info("E-PVP Habilitado!");
	}
	public void onDisable(){
		getLogger().info("E-PVP Desabilitado!");
	}
	public static Main getMain(){
		return (Main) Bukkit.getServer().getPluginManager().getPlugin("Main");
	}
}