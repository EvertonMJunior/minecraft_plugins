package me.everton.esc;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public static Plugin plugin;
	public static FileConfiguration config;

	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(
				"§f*************************************");
		Bukkit.getConsoleSender().sendMessage(
				"§4eStaffChat - Plugin Habilitado");
		Bukkit.getConsoleSender().sendMessage("§cDesenvolvido por EvertonPvP");
		Bukkit.getConsoleSender().sendMessage(
				"§cContato: marcelinojr.everton (skype)");
		Bukkit.getConsoleSender().sendMessage(
				"§f*************************************");

		Bukkit.getPluginManager().registerEvents(new Listeners(), this);
		getCommand("sc").setExecutor(new Cmd());
		getConfig().options().copyDefaults(true);
		saveConfig();
		saveConfig();
		config = getConfig();
		plugin = this;
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(
				"§f*************************************");
		Bukkit.getConsoleSender().sendMessage(
				"§4eStaffChat - Plugin Desabilitado");
		Bukkit.getConsoleSender().sendMessage("§cDesenvolvido por EvertonPvP");
		Bukkit.getConsoleSender().sendMessage(
				"§cContato: marcelinojr.everton (skype)");
		Bukkit.getConsoleSender().sendMessage(
				"§f*************************************");
	}

	public static Main getMain() {
		return (Main) Bukkit.getServer().getPluginManager().getPlugin("Main");
	}

	public static Plugin getPlugin() {
		return plugin;
	}
}
