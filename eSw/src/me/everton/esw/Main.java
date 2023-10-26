package me.everton.esw;

import me.everton.esw.Kits.Cmds.EnderMan;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
	public static Plugin plugin;

	public static Plugin getPlugin() {
		return plugin;
	}

	public void onEnable() {
		plugin = this;
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		reloadConfig();
		Bukkit.getConsoleSender()
				.sendMessage(
						"§3§l[eEnderMan] §cPlugin ativado! Feito por EvertonDev (EvertonPvP)");
		Bukkit.getServer().getPluginManager().registerEvents(new me.everton.esw.Kits.Listeners.EnderMan(), this);
		getCommand("enderman").setExecutor(new EnderMan());
	}

	public void onDisable() {
		saveDefaultConfig();
		Bukkit.getConsoleSender()
				.sendMessage(
						"§3§l[eEnderMan] §cPlugin desativado! Feito por EvertonDev (EvertonPvP)");
		HandlerList.unregisterAll();
	}

	
}
