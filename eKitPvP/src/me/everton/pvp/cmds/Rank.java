package me.everton.pvp.cmds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.everton.pvp.Main;
import me.everton.pvp.tags.TagCmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Rank implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando In-Game!");
			return true;
		}
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("rank")) {
			if(args.length == 1) {
				TagCmd.setPrefix(p, "§4§lDONO §4");
				if(args[0].equalsIgnoreCase("kills")) {
					try {
						Statement statement = Main.c.createStatement();
						ResultSet res = statement.executeQuery("SELECT player,kills FROM player_data ORDER BY kills DESC LIMIT 10;");
						int i = 1;
						p.sendMessage(" ");
						p.sendMessage("§e§m----(------(§6§lTop 10 Kills§e§m)------)----");
						while(res.next()) {
							p.sendMessage("§7" + i + "° §b" + res.getString("player") + " §7(§4§lKills: §c" + res.getInt("kills") + "§7)");
							i++;
						}
						p.sendMessage("§e§m----(------(§6§lTop 10 Kills§e§m)------)----");
						p.sendMessage(" ");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if(args[0].equalsIgnoreCase("deaths")) {
					try {
						Statement statement = Main.c.createStatement();
						ResultSet res = statement.executeQuery("SELECT player,deaths FROM player_data ORDER BY deaths DESC LIMIT 10;");
						int i = 1;
						p.sendMessage(" ");
						p.sendMessage("§e§m----(------(§6§lTop 10 Deaths§e§m)------)----");
						while(res.next()) {
							p.sendMessage("§7" + i + "° §b" + res.getString("player") + " §7(§4§lDeaths: §c" + res.getInt("deaths") + "§7)");
							i++;
						}
						p.sendMessage("§e§m----(------(§6§lTop 10 Deaths§e§m)------)----");
						p.sendMessage(" ");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if(args[0].equalsIgnoreCase("money")) {
					try {
						Statement statement = Main.c.createStatement();
						ResultSet res = statement.executeQuery("SELECT player,money FROM player_data ORDER BY money DESC LIMIT 10;");
						
						int i = 1;
						p.sendMessage(" ");
						p.sendMessage("§e§m----(------(§6§lTop 10 Money§e§m)------)----");
						while(res.next()) {
							p.sendMessage("§7" + i + "° §b" + res.getString("player") + " §7(§4§lMoney: §c" + res.getInt("money") + "§7)");
							i++;
						}
						p.sendMessage("§e§m----(------(§6§lTop 10 Money§e§m)------)----");
						p.sendMessage(" ");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					p.sendMessage("§cUse /rank <kills/deaths/money>");
				}
			} else {
				p.sendMessage("§cUse /rank <kills/deaths/money>");
			}
		}
		return false;
	}
	
}
