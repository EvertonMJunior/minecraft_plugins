package me.everton.mcgames;

import me.everton.mcgames.listeners.OnJoin;
import me.tigerhix.lib.bossbar.BossbarLib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	private static Plugin plugin;
	
	public void onEnable() {
		plugin = this;
		registerListeners();
		BossbarLib.setPluginInstance(this);
	}
	
	public void onDisable() {
		
	}
	
	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new OnJoin(), this);
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
}
