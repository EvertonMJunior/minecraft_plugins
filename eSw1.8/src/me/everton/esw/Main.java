package me.everton.esw;

import me.everton.esw.commands.Admin;
import me.everton.esw.commands.Tag;
import me.everton.esw.listeners.Gerais;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;

public class Main extends JavaPlugin{
	public static ConsoleCommandSender ccs;
	public static boolean autorizado = false;
	
	public static Plugin plugin;
	public static BukkitScheduler sh;
	public static Scoreboard sb;
	
	public void onEnable() {
		ccs = Bukkit.getConsoleSender();
		plugin = this;
		sh = Bukkit.getScheduler();
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		checkIP();
		registerEvents();
		registerCmds();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			
			@Override
			public void run() {
				ccs.sendMessage("			§7[§c§leSkyWars§7]");
				ccs.sendMessage("§5  ");
				ccs.sendMessage("		§3§lO plugin foi habilitado com sucesso!");
				ccs.sendMessage("		§3§lDesenvolvido por §cEvertonPvP§3§l!");
				if(autorizado) {
					ccs.sendMessage("		§3§lStatus do servidor: §aAutorizado!");
				} else {
					ccs.sendMessage("		§3§lStatus do servidor: §4Sem autorizacao!");
					ccs.sendMessage("§e§lPossiveis causas: ");
					ccs.sendMessage("§f- §9O servidor esta hospedado numa maquina sem acesso a internet");
					ccs.sendMessage("§f- §9Voce esta usando uma copia nao paga do Plugin");
					ccs.sendMessage("§f- §9O plugin esta com algum problema na autenticacao");
					ccs.sendMessage("§e§lCaso voce tenha comprado o plugin e nao esteja conseguindo habilita-lo, contate-me pelo skype: live:marcelinojr.everton");
					Bukkit.shutdown();
				}
				ccs.sendMessage("§5  ");
				ccs.sendMessage("			§7[§c§leSkyWars§7]");
			}
		}, 1 * 20L);
	}
	
	public void onDisable() {
		ccs.sendMessage("			§7[§c§leSkyWars§7]");
		ccs.sendMessage("§5  ");
		ccs.sendMessage("		§3§lO plugin foi desabilitado com sucesso!");
		ccs.sendMessage("		§3§lDesenvolvido por §cEvertonPvP§3§l!");
		ccs.sendMessage("§5  ");
		ccs.sendMessage("			§7[§c§leSkyWars§7]");
	}
	
	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new Gerais(), this);
		getServer().getPluginManager().registerEvents(new Admin(), this);
	}
	
	public void registerCmds() {
		getCommand("tag").setExecutor(new Tag());
		getCommand("adm").setExecutor(new Admin());
		getCommand("admin").setExecutor(new Admin());
		getCommand("vis").setExecutor(new Admin());
		getCommand("invis").setExecutor(new Admin());
	}
	
	public void checkIP() {
		if(String.valueOf(Bukkit.getIp() + ":" + Bukkit.getPort()).equalsIgnoreCase("127.0.0.1:3000")) {
			autorizado = true;
		}
	}

	public static void abrirGuiSpectador(Player p, Player[] players, int rows,
			Boolean showadmin) {
		ItemStack i = new ItemStack(Material.SKULL_ITEM, 1,
				(short) SkullType.PLAYER.ordinal());
		SkullMeta m = (SkullMeta) i.getItemMeta();
		Inventory spectate = Bukkit.createInventory(p, rows * 9,
				"§4§lJogadores Onlines");
		int adminc = 0;
		for (int z = 0; z < players.length; z++) {
			if (Admin.admin.contains(players[z].getName())) {
				adminc++;
			}
			if (!(players.length > 53)) {
				m.setDisplayName(players[z].getName());
				m.setOwner(players[z].getName());
				i.setItemMeta(m);
				if (!showadmin) {
					if (!Admin.admin.contains(players[z].getName()))  {
						spectate.setItem(z - adminc, i);
					}
				} else {
					spectate.setItem(z, i);
				}
				p.openInventory(spectate);
			} else if ((players.length > 53)) {
				p.sendMessage("§aSuportando por enquanto apenas 53 jogadores onlines!");
			}
		}
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
}
