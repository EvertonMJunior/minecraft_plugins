package me.everton.epvp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.bukkit.Bukkit;

public class ScoreManager {
	public static HashMap<String, Integer> kills = new HashMap<>();
	public static HashMap<String, Integer> deaths = new HashMap<>();
	
	public static void addKill(String nome) {
		if (kills.containsKey(nome)) {
			kills.put(nome, kills.get(nome) + 1);	
		} else {
			kills.put(nome, 1);
		}
	}
	
	public static void addDeath(String nome) {
		if (deaths.containsKey(nome)) {
			deaths.put(nome, deaths.get(nome) + 1);	
		} else {
			deaths.put(nome, 1);
		}
	}
	
	public static int checkKills(String nome) {
		SQL.openConnection();
		try{
			if(SQL.tableContainsPlayer(nome)) {				
				PreparedStatement sql = SQL.connection.prepareStatement("SELECT * FROM player_data WHERE player=?;");
				sql.setString(1, nome);
				ResultSet result = sql.executeQuery();
				result.next();
				
				if(kills.containsKey(nome)) {
					return result.getInt("kills") + kills.get(nome);
				} else {
					return result.getInt("kills");
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
	
	public static int checkDeaths(String nome) {
		SQL.openConnection();
		try{
			if(SQL.tableContainsPlayer(nome)) {				
				PreparedStatement sql = SQL.connection.prepareStatement("SELECT * FROM player_data WHERE player=?;");
				sql.setString(1, nome);
				ResultSet result = sql.executeQuery();
				result.next();
				if(deaths.containsKey(nome)) {
					return result.getInt("deaths") + deaths.get(nome);
				} else {
					return result.getInt("deaths");
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
	
	public static void resetKdr(String nome) {
		SQL.openConnection();
		try{
			if(!SQL.tableContainsPlayer(nome)){
				return;
			}
			
			PreparedStatement sql2 = SQL.connection.prepareStatement("UPDATE player_data SET kills=0, deaths=0 WHERE player=?;");
			sql2.setString(1, nome);
			sql2.executeUpdate();
				
			sql2.close();
			
			if(Bukkit.getPlayerExact(nome) != null){
				ServerScoreboard.scoreboards.remove(nome);
				ServerScoreboard.resetScoreboard(Bukkit.getPlayerExact(nome));
			}			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			SQL.closeConnection();
		}
		
		kills.remove(nome);
		deaths.remove(nome);
	}
}
