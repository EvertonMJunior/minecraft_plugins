package me.everton.od;

import java.util.HashMap;

import me.everton.od.cmds.CmdsGerais;
import me.everton.od.cmds.Time;
import me.everton.od.listeners.Feast;
import me.everton.od.listeners.Obsidians;
import me.everton.od.listeners.Principais;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin{
	public static Plugin plugin;
	public static Scoreboard sb = null;
	public static BukkitScheduler sh = null;
	public static Team red = null;
	public static Team blue = null;
	public static boolean PRE_JOGO = true;
	public static boolean INVENCIBILIDADE = false;
	public static boolean EM_JOGO = false;
	
	public static HashMap<String, Integer> kills = new HashMap<>();
	public static HashMap<String, Integer> deaths = new HashMap<>();
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§7[§c!§7] Plugin habilitado! Criado por EvertonPvP!");
		plugin = this;
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		sh = Bukkit.getScheduler();
		red = sb.registerNewTeam("RED");
		blue = sb.registerNewTeam("BLUE");
		red.setCanSeeFriendlyInvisibles(true);
		blue.setCanSeeFriendlyInvisibles(true);
		red.setPrefix("§c");
		blue.setPrefix("§9");
		red.setAllowFriendlyFire(false);
		blue.setAllowFriendlyFire(false);
		red.setDisplayName("§cVermelho");
		red.setDisplayName("§9Azul");
		registerCmds();
		registerEvents();
		Board.start();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer("§7[§b§lObsidian §6§lDestroyer§7] \n§aServidor reiniciando. Reentre! \nPlugin criado por §lEvertonPvP");
		}
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§7[§c!§7] Plugin desabilitado! Criado por EvertonPvP!");
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer("§7[§b§lObsidian §6§lDestroyer§7] \n§aServidor reiniciando. Reentre! \nPlugin criado por §lEvertonPvP");
		}
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	private void registerCmds() {
		getCommand("time").setExecutor(new Time());
		getCommand("start").setExecutor(new CmdsGerais());
	}
	
	private void registerEvents() {
		getServer().getPluginManager().registerEvents(new Principais(), this);
		getServer().getPluginManager().registerEvents(new Obsidians(), this);
		getServer().getPluginManager().registerEvents(new Feast(), this);
		getServer().getPluginManager().registerEvents(new TeamSelector(), this);
	}
}
