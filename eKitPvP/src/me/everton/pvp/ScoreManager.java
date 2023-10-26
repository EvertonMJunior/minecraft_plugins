package me.everton.pvp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreManager {
	public static HashMap<String, Integer> kills = new HashMap<>();
	public static HashMap<String, Integer> deaths = new HashMap<>();
	public static HashMap<String, Integer> money = new HashMap<>();
	public static HashMap<String, Integer> villagercoins = new HashMap<>();
	
	public static enum DataType{
		KILLS, DEATHS, MONEY, VILLAGERCOINS;
	}
	
	public static enum ModifyDataType{
		ADD, REMOVE, SET;
	}
	
	public static void registerData(Player p) {
		if(!kills.containsKey(p.getName())) {
			kills.put(p.getName(), getData(p.getName(), DataType.KILLS));
		}
		
		if(!deaths.containsKey(p.getName())) {
			deaths.put(p.getName(), getData(p.getName(), DataType.DEATHS));
		}
		
		if(!money.containsKey(p.getName())) {
			money.put(p.getName(), getData(p.getName(), DataType.MONEY));
		}
		
		if(!villagercoins.containsKey(p.getName())) {
			villagercoins.put(p.getName(), getData(p.getName(), DataType.VILLAGERCOINS));
		}
	}
	
	public static void registerData(String p) {
		if(!kills.containsKey(p)) {
			kills.put(p, getData(p, DataType.KILLS));
		}
		
		if(!deaths.containsKey(p)) {
			deaths.put(p, getData(p, DataType.DEATHS));
		}
		
		if(!money.containsKey(p)) {
			money.put(p, getData(p, DataType.MONEY));
		}
		
		if(!villagercoins.containsKey(p)) {
			villagercoins.put(p, getData(p, DataType.VILLAGERCOINS));
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void storageData() {
		for(Map.Entry e : kills.entrySet()) {
			String p = (String) e.getKey();
			int kills = (int) e.getValue();
			if(getData(p, DataType.KILLS) == kills) {
				modifyData(p, DataType.KILLS, ModifyDataType.SET, kills);
			}
		}
		
		for(Map.Entry e : deaths.entrySet()) {
			String p = (String) e.getKey();
			int deaths = (int) e.getValue();
			if(getData(p, DataType.DEATHS) == deaths) {
				modifyData(p, DataType.DEATHS, ModifyDataType.SET, deaths);
			}
		}
		
		for(Map.Entry e : money.entrySet()) {
			String p = (String) e.getKey();
			int money = (int) e.getValue();
			if(getData(p, DataType.MONEY) == money) {
				API.broadcastMessage("Nome: " + p);
				API.broadcastMessage("Money: " + money);
				modifyData(p, DataType.MONEY, ModifyDataType.SET, money);
			}
		}
		
		for(Map.Entry e : villagercoins.entrySet()) {
			String p = (String) e.getKey();
			int villagercoins = (int) e.getValue();
			if(getData(p, DataType.VILLAGERCOINS) == villagercoins) {
				modifyData(p, DataType.VILLAGERCOINS, ModifyDataType.SET, villagercoins);
			}
		}
	}
	
	public static int getData(String p, DataType d) {
		try {
			Statement statement = Main.c.createStatement();
			ResultSet res = statement.executeQuery("SELECT " + d.name().toLowerCase() + " FROM player_data WHERE player = '" + p + "';");
			res.next();
			return res.getInt(d.name().toLowerCase());
		} catch(SQLException e) {
			return 0;
		}
	}
	
	public static void modifyData(String p, DataType d, ModifyDataType mdt, int value) {
		try {
			if(mdt == ModifyDataType.ADD) {
				Statement statement = Main.c.createStatement();
				int vl = (getData(p, d) + value);
				if(vl < 0) {
					vl = 0;
				}
				if(isRegistered(p)) {
					statement.executeUpdate("UPDATE player_data SET " + d.name().toLowerCase() + " = '" + vl + "' WHERE player = '" + p + "';");
				} else {
					if(d == DataType.DEATHS) {
						statement.executeUpdate("INSERT INTO player_data (player,kills,deaths,money,villagercoins) VALUES ('" + p + "',0," + vl + ",0,0);");
					} else if(d == DataType.KILLS) {
						statement.executeUpdate("INSERT INTO player_data (player,kills,deaths,money,villagercoins) VALUES ('" + p + "'," + vl + ",0,0,0);");
					} else if(d == DataType.MONEY){
						statement.executeUpdate("INSERT INTO player_data (player,kills,deaths,money,villagercoins) VALUES ('" + p + "',0,0," + vl + ",0);");
					} else if(d == DataType.VILLAGERCOINS) {
						statement.executeUpdate("INSERT INTO player_data (player,kills,deaths,money,villagercoins) VALUES ('" + p + "',0,0,0," + vl + ");");
					}
				}
			} else if(mdt == ModifyDataType.REMOVE){
				int vl = (getData(p, d) - value);
				if(vl < 0) {
					vl = 0;
				}
				Statement statement = Main.c.createStatement();
				statement.executeUpdate("UPDATE player_data SET " + d.name().toLowerCase() + " = '" + vl + "' WHERE player = '" + p + "';");
			} else if(mdt == ModifyDataType.SET) {
				int vl = value;
				if(vl < 0) {
					vl = 0;
				}
				Statement statement = Main.c.createStatement();
				if(isRegistered(p)) {
					statement.executeUpdate("UPDATE player_data SET " + d.name().toLowerCase() + " = '" + vl + "' WHERE player = '" + p + "';");
				} else {
					if(d == DataType.DEATHS) {
						statement.executeUpdate("INSERT INTO player_data (player,kills,deaths,money,villagercoins) VALUES ('" + p + "',0," + vl + ",0,0);");
					} else if(d == DataType.KILLS) {
						statement.executeUpdate("INSERT INTO player_data (player,kills,deaths,money,villagercoins) VALUES ('" + p + "'," + vl + ",0,0,0);");
					} else if(d == DataType.MONEY){
						statement.executeUpdate("INSERT INTO player_data (player,kills,deaths,money,villagercoins) VALUES ('" + p + "',0,0," + vl + ",0);");
					} else if(d == DataType.VILLAGERCOINS) {
						statement.executeUpdate("INSERT INTO player_data (player,kills,deaths,money,villagercoins) VALUES ('" + p + "',0,0,0," + vl + ");");
					}
				}
			}
		} catch(SQLException e) {
		}
	}
	
	public static boolean isRegistered(String p) {
		try {
			Statement statement = Main.c.createStatement();
			ResultSet res = statement.executeQuery("SELECT player FROM player_data WHERE player = '" + p + "';");
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
	
	public static void modifyData(HashMap<String, Integer> h, String n, int q, ModifyDataType mdt) {
		if(mdt == ModifyDataType.ADD) {
			if(h.containsKey(n)) {
				int vl = h.get(n) + q;
				if(vl < 0) {
					vl = 0;
				}
				h.put(n, vl);
			} else {
				h.put(n, q);
			}
		} else if(mdt == ModifyDataType.REMOVE) {
			if(h.containsKey(n)) {
				int vl = h.get(n) - q;
				if(vl < 0) {
					vl = 0;
				}
				h.put(n, vl);
			} else {
				h.put(n, 0);
			}
		} else if(mdt == ModifyDataType.SET) {
			int vl = q;
			if(vl < 0) {
				vl = 0;
			}
			h.put(n, vl);
		}
	}
	
	public static int getData(String n, HashMap<String, Integer> h) {
		if(h.containsKey(n)) {
			return h.get(n);
		} else {
			return 0;
		}
	}
	
	public static class Money{
		public static void addMoney(String n, int q) {
			modifyData(money, n, q, ModifyDataType.ADD);
			Player t = Bukkit.getPlayerExact(n);
			if(t != null) {
				t.sendMessage("§a+ §d" + q + " §lTC");
			}
		}
		
		public static void removeMoney(String n, int q) {
			modifyData(money, n, q, ModifyDataType.REMOVE);
			Player t = Bukkit.getPlayerExact(n);
			if(t != null) {
				t.sendMessage("§c- §d" + q + " §lTC");
			}
		}
		
		public static void setMoney(String n, int q) {
			modifyData(money, n, q, ModifyDataType.SET);
			Player t = Bukkit.getPlayerExact(n);
			if(t != null) {
				t.sendMessage("§7Agora você tem §d" + q + " §lTigerCoins§7!");
			}
		}
		
		public static int getMoney(String n) {
			return getData(n, money);
		}
	}
	
	public static class VillagerCoins{
		public static void addVillagerCoins(String n, int q) {
			modifyData(villagercoins, n, q, ModifyDataType.ADD);
			Player t = Bukkit.getPlayerExact(n);
			if(t != null) {
				t.sendMessage("§a+ §e" + q + " §lVC");
			}
		}
		
		public static void removeVillagerCoins(String n, int q) {
			modifyData(villagercoins, n, q, ModifyDataType.REMOVE);
			Player t = Bukkit.getPlayerExact(n);
			if(t != null) {
				t.sendMessage("§c- §e" + q + " §lVC");
			}
		}
		
		public static void setVillagerCoins(String n, int q) {
			modifyData(villagercoins, n, q, ModifyDataType.SET);
			Player t = Bukkit.getPlayerExact(n);
			if(t != null) {
				t.sendMessage("§7Agora você tem §e" + q + " §lVillagerCoins§7!");
			}
		}
		
		public static int getVillagerCoins(String n) {
			return getData(n, villagercoins);
		}
	}
	
	public static class Kills{
		public static void addKill(String n, int q) {
			modifyData(kills, n, q, ModifyDataType.ADD);
		}
		
		public static void removeKill(String n, int q) {
			modifyData(kills, n, q, ModifyDataType.REMOVE);
		}
		
		public static void setKills(String n, int q) {
			modifyData(kills, n, q, ModifyDataType.SET);
		}
		
		public static int getKills(String n) {
			return getData(n, kills);
		}
	}
	
	public static class Deaths{
		public static void addDeath(String n, int q) {
			modifyData(deaths, n, q, ModifyDataType.ADD);
		}
		
		public static void removeDeath(String n, int q) {
			modifyData(deaths, n, q, ModifyDataType.REMOVE);
		}
		
		public static void setDeaths(String n, int q) {
			modifyData(deaths, n, q, ModifyDataType.SET);
		}
		
		public static int getDeaths(String n) {
			return getData(n, deaths);
		}
	}
	
	public static class Levels{
		public static String getLevel(String n) {
			String level = "";
			
			if(Kills.getKills(n) > 5000) {
				level = "Lendário";
			} else if(Kills.getKills(n) > 1000) {
				level = "Pro";
			} else if(Kills.getKills(n) > 50) {
				level = "Médio";
			} else if(Kills.getKills(n) > 0) {
				level = "Fácil";
			} else {
				level = "Fácil";
			}
			
			return level;
		}
	}
}
