package me.everton.pvp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class OptionsManager {
	public static HashMap<String, SwordType> sword = new HashMap<>();
	public static HashMap<String, RecraftType> recraft = new HashMap<>();
	
	public static enum DataType{
		SWORD, RECRAFT;
	}
	
	public static enum SwordType{
		WOOD, STONE;
	}
	
	public static enum RecraftType{
		MUSHROOMS, COCOA;
	}
	
	public static void registerData(Player p) {
		if(!sword.containsKey(p.getName())) {
			SwordType sw = null;
			if(getSQLData(p.getName(), DataType.SWORD).equalsIgnoreCase("wood")) {
				sw = SwordType.WOOD;
			} else {
				sw = SwordType.STONE;
			}
			sword.put(p.getName(), sw);
		}
		
		if(!recraft.containsKey(p.getName())) {
			RecraftType rt = RecraftType.MUSHROOMS;
			if(getSQLData(p.getName(), DataType.RECRAFT).equalsIgnoreCase("cocoa")) {
				rt = RecraftType.COCOA;
			}
			recraft.put(p.getName(), rt);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void storageData() {
		for(Map.Entry e : recraft.entrySet()) {
			String p = (String) e.getKey();
			RecraftType vl = (RecraftType) e.getValue();
			String value = vl.name();
			modifyData(p, DataType.RECRAFT, value);
		}
		
		for(Map.Entry e : sword.entrySet()) {
			String p = (String) e.getKey();
			SwordType vl = (SwordType) e.getValue();
			String value = vl.name();
			modifyData(p, DataType.SWORD, value);
		}
	}
	
	public static String getSQLData(String p, DataType d) {
		try {
			String dataT = "";
			if(d == DataType.SWORD) {
				dataT = "espada";
			} else if(d == DataType.RECRAFT) {
				dataT = "recraft";
			}
			Statement statement = Main.c.createStatement();
			ResultSet res = statement.executeQuery("SELECT " + dataT + " FROM player_preferences WHERE player = '" + p + "';");
			res.next();
			return res.getString(dataT);
		} catch(SQLException e) {
			if(d == DataType.SWORD) {
				return "stone";
			} else if(d == DataType.RECRAFT) {
				return "mushrooms";
			} else {
				return null;
			}
		}
	}
	
	public static void modifyData(String p, DataType d, String value) {
		try {
				Statement statement = Main.c.createStatement();
				if(isRegistered(p)) {
					String dataT = "";
					if(d == DataType.SWORD) {
						dataT = "espada";
					} else if(d == DataType.RECRAFT) {
						dataT = "recraft";
					}
					statement.executeUpdate("UPDATE player_preferences SET " + dataT + " = '" + value + "' WHERE player = '" + p + "';");
				} else {
					if(d == DataType.SWORD) {
						statement.executeUpdate("INSERT INTO player_preferences (player,espada,recraft) VALUES ('" + p + "','" + value + "','mushrooms');");
					} else if(d == DataType.RECRAFT) {
						statement.executeUpdate("INSERT INTO player_preferences (player,espada,recraft) VALUES ('" + p + "','stone','" + value + "');");
					}
				}
		} catch(SQLException e) {
		}
	}
	
	public static boolean isRegistered(String p) {
		try {
			Statement statement = Main.c.createStatement();
			ResultSet res = statement.executeQuery("SELECT player FROM player_preferences WHERE player = '" + p + "';");
			res.next();
			if(res.getString("player") == null) {
				return false;
			} else {
				return true;
			}
		} catch(SQLException e) {
			return false;
		}
	}
	
	public static void modifyData(String n, String value, DataType d) {
		if(d == DataType.SWORD) {
			if(value.equalsIgnoreCase("stone")) {
				sword.put(n, SwordType.STONE);
			} else {
				sword.put(n, SwordType.WOOD);
			}
		} else if(d == DataType.RECRAFT) {
			if(value.equalsIgnoreCase("mushrooms")) {
				recraft.put(n, RecraftType.MUSHROOMS);
			} else {
				recraft.put(n, RecraftType.COCOA);
			}
		}
	}
	
	public static String getData(String n, DataType d) {
		if(d == DataType.SWORD) {
			if(sword.containsKey(n)) {
				return sword.get(n).name();
			} else {
				return "STONE";
			}
		} else if(d == DataType.RECRAFT) {
			if(recraft.containsKey(n)) {
				return recraft.get(n).name();
			} else {
				return "MUSHROOMS";
			}
		}
		return null;
	}
}
