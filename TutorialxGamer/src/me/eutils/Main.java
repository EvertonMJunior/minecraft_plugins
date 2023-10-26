package me.eutils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
  public static Plugin plugin;
  public static ConsoleCommandSender ccs;
  
  public void onEnable(){
	  plugin = this;
	  ccs = Bukkit.getConsoleSender();
	  ccs.sendMessage("§4eUtils: Plugin Ativado");
  }
  
  public void onDisable() {
	  ccs.sendMessage("§4eUtils: Plugin Desativado");
  }
  
  public void Registrar(){
	   
  }
  
  public void Comandos(){
   
  }
  
  public static Plugin getPlugin(){
	  return plugin;
  }
}