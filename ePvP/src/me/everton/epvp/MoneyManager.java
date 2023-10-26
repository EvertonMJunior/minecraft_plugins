package me.everton.epvp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

public class MoneyManager {
	static FileConfiguration moneysettings = Main.settings.getMoney();
	
	public static HashMap<String, Integer> money = new HashMap<>(); 

	public static void addMoney(String nome, int quantidade) throws Exception {
		if (money.containsKey(nome)) {
			money.put(nome, money.get(nome) + quantidade);	
		} else {
			money.put(nome, quantidade);
		}

		if (Bukkit.getPlayerExact(nome) != null) {
			Player p = Bukkit.getPlayerExact(nome);
			p.sendMessage("§a+ §7" + quantidade + " moedas!");

			Score m = (Score) ServerScoreboard.money.get(p.getName());
			m.setScore(checkMoney(nome));
			ServerScoreboard.money.put(p.getName(), m);
		}
	}

	
	public static void setMoney(String nome, int quantidade) throws Exception {
		if (money.containsKey(nome)) {
			money.put(nome, quantidade);
		} else {
			money.put(nome, quantidade);
		}

		if (Bukkit.getPlayerExact(nome) != null) {
			Player p = Bukkit.getPlayerExact(nome);
			p.sendMessage("§aAgora Você tem " + quantidade + " moedas!");

			Score m = (Score) ServerScoreboard.money.get(p.getName());
			m.setScore(checkMoney(nome));
			ServerScoreboard.money.put(p.getName(), m);
		}
	}

	public static void removeMoney(String nome, int quantidade) throws Exception {
		if (money.containsKey(nome)) {
			money.put(nome, money.get(nome) - quantidade);
		} else {
			money.put(nome, 0);
		}
		
		if (Bukkit.getPlayerExact(nome) != null) {
			Player p = Bukkit.getPlayerExact(nome);
			p.sendMessage("§c- §7" + quantidade + " moedas!");

			Score m = (Score) ServerScoreboard.money.get(p.getName());
			m.setScore(checkMoney(nome));
			ServerScoreboard.money.put(p.getName(), m);
		}
	}

	public static int checkMoney(String nome) throws Exception {
		
		SQL.openConnection();
		try{
			if(SQL.tableContainsPlayer(nome)) {				
				PreparedStatement sql = SQL.connection.prepareStatement("SELECT money FROM player_data WHERE player=?;");
				sql.setString(1, nome);
				ResultSet result = sql.executeQuery();
				result.next();
				
				if(money.containsKey(nome)) {
					return result.getInt("money") + money.get(nome);
				} else {
					return result.getInt("money");
				}
				
			} else {
				return 0;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			SQL.closeConnection();
		}
		return 0;
	}
}
