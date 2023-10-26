package me.dark.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import me.dark.Chests.ChestsTypes;
import me.dark.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SQLStatus {

	public SQLStatus(Main plugin) {
		if (MySQL.ativo) {
			MySQL sql = Main.sql;
			sql.queryUpdate("CREATE TABLE IF NOT EXISTS good_players(uuid varchar(255), wins varchar(255), kills varchar(255), deaths varchar(255), PRIMARY KEY (uuid))");	
			sql.queryUpdate("CREATE TABLE IF NOT EXISTS good_coins(uuid varchar(255), coins varchar(255), PRIMARY KEY (uuid))");
			sql.queryUpdate("CREATE TABLE IF NOT EXISTS good_chests(uuid varchar(255), g1 varchar(255), g2 varchar(255), g3 varchar(255), PRIMARY KEY (uuid))");	
		}
	}
	
	public static HashMap<Player, Integer> killstreak = new HashMap<Player, Integer>();
	public static void addKillstreak(Player p) {
		if (!killstreak.containsKey(p)) {
			killstreak.put(p, 1);
		}else {
			killstreak.put(p, getKillstreak(p) + 1);
		}
	}
	
	public static int getKillstreak(Player p) {
		if (!killstreak.containsKey(p)) {
			killstreak.put(p, 0);
		}
		return killstreak.get(p);
	}
	  
	public static boolean exists(UUID id) {
		if (!MySQL.ativo) return false;
		boolean exits = false;
		try {
			PreparedStatement ps = Main.sql.getConnection()
					.prepareStatement("SELECT * FROM good_players WHERE uuid='"
							+ id + "'");
			ResultSet rs = ps.executeQuery();
			exits = rs.next();
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return exits;
	}

	public static int getKills(UUID id) {
		if (!MySQL.ativo) return 0;
		try {
			PreparedStatement ps = Main.sql.getConnection()
					.prepareStatement("SELECT * FROM good_players WHERE uuid='"
							+ id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int kills = rs.getInt("kills");
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!ps.isClosed()) {
					ps.close();
				}
				return kills;
			}
			if (!rs.isClosed()) {
				rs.close();
			}
			if (!ps.isClosed()) {
				ps.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}
	
	public static int getCoins(UUID id) {
		if (!MySQL.ativo) return 0;
		try {
			PreparedStatement ps = Main.sql.getConnection()
					.prepareStatement("SELECT * FROM good_coins WHERE uuid='"
							+ id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int coins = rs.getInt("coins");
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!ps.isClosed()) {
					ps.close();
				}
				return coins;
			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}

	public static int getDeaths(UUID id) {
		if (!MySQL.ativo) return 0;
		try {
			PreparedStatement ps = Main.sql.getConnection()
					.prepareStatement("SELECT * FROM good_players WHERE uuid='"
							+ id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int deaths = rs.getInt("deaths");
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!ps.isClosed()) {
					ps.close();
				}
				return deaths;
			}
			if (!rs.isClosed()) {
				rs.close();
			}
			if (!ps.isClosed()) {
				ps.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}
	
	public static int getWins(UUID id) {
		if (!MySQL.ativo) return 0;
		try {
			PreparedStatement ps = Main.sql.getConnection()
					.prepareStatement("SELECT * FROM good_players WHERE uuid='"
							+ id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int wins = rs.getInt("wins");
				rs.close();
				ps.close();
				return wins;
			}
			if (!rs.isClosed()) {
				rs.close();
			}
			if (!ps.isClosed()) {
				ps.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}

	public static int getRank(UUID id) {
		if (!MySQL.ativo) return 101;
		try {
			PreparedStatement ps = Main.sql.getConnection()
					.prepareStatement("SELECT uuid FROM good_players" +
			                   " ORDER BY cast(wins as unsigned) DESC");
			ResultSet rs = ps.executeQuery();
			int rank = 1;
			while (rs.next()) {
				if (!rs.getString("uuid").equalsIgnoreCase(id.toString())) {
					rank++;
					if (rank == 1001) {
						rs.close();
						ps.close();
						return rank;
					}
				} else {
					rs.close();
					ps.close();
					return rank;
				}
			}
			if (!rs.isClosed()) {
				rs.close();
			}
			if (!ps.isClosed()) {
				ps.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 101;
	}
	public static int getChests (UUID id, ChestsTypes tipo) {
		if (!MySQL.ativo) return 0;
		try {
			PreparedStatement ps = Main.sql.getConnection()
					.prepareStatement("SELECT * FROM good_chests WHERE uuid='"
							+ id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int chests = rs.getInt(tipo.getType());
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!ps.isClosed()) {
					ps.close();
				}
				return chests;
			}
			if (!rs.isClosed()) {
				rs.close();
			}
			if (!ps.isClosed()) {
				ps.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}
	
	public static void addChestG1(UUID id) {
		if (MySQL.ativo) {
			try {
				PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT * FROM good_chests WHERE uuid='"
								+ id + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps1 = Main.sql.getConnection()
							.prepareStatement("UPDATE good_chests SET g1='"
									+ Integer.toString(rs.getInt("g1") + 1)
									+ "' WHERE uuid='" + id + "'");
					ps1.executeUpdate();
					ps1.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void addChestG2(UUID id) {
		if (MySQL.ativo) {
			try {
				PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT * FROM good_chests WHERE uuid='"
								+ id + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps1 = Main.sql.getConnection()
							.prepareStatement("UPDATE good_chests SET g2='"
									+ Integer.toString(rs.getInt("g2") + 1)
									+ "' WHERE uuid='" + id + "'");
					ps1.executeUpdate();
					ps1.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void addChestG3(UUID id) {
		if (MySQL.ativo) {
			try {
				PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT * FROM good_chests WHERE uuid='"
								+ id + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps1 = Main.sql.getConnection()
							.prepareStatement("UPDATE good_chests SET g3='"
									+ Integer.toString(rs.getInt("g3") + 1)
									+ "' WHERE uuid='" + id + "'");
					ps1.executeUpdate();
					ps1.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addKill(UUID id) {
		if (MySQL.ativo) {
			try {
				PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT * FROM good_players WHERE uuid='"
								+ id + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps1 = Main.sql.getConnection()
							.prepareStatement("UPDATE good_players SET kills='"
									+ Integer.toString(rs.getInt("kills") + 1)
									+ "' WHERE uuid='" + id + "'");
					ps1.executeUpdate();
					ps1.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addDeath(UUID id) {
		if (MySQL.ativo) {
			try {
				PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT * FROM good_players WHERE uuid='"
								+ id + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps1 = Main.sql.getConnection()
							.prepareStatement("UPDATE good_players SET deaths='"
									+ Integer.toString(rs.getInt("deaths") + 1)
									+ "' WHERE uuid='" + id + "'");
					ps1.executeUpdate();
					ps1.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void addWin(UUID id) {
		if (MySQL.ativo) {
			try {
				PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT * FROM good_players WHERE uuid='"
								+ id + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps1 = Main.sql.getConnection()
							.prepareStatement("UPDATE good_players SET wins='"
									+ Integer.toString(rs.getInt("wins") + 1)
									+ "' WHERE uuid='" + id + "'");
					ps1.executeUpdate();
					ps1.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void addCoins(UUID id, int quant) {
		if (MySQL.ativo) {
			try {
				if (Bukkit.getPlayerExact(UUIDFetcher.getName(id)) != null) {
					Player p = Bukkit.getPlayerExact(UUIDFetcher.getName(id));
					if (p.hasPermission("good.coins.4")) {
						quant *= 4;
					}else if (p.hasPermission("good.coins.2")) {
						quant *= 2;
					}
					p.sendMessage("�b"+Main.seta2+" �7Voc� ganhou �3"+quant+" �bGoodCoins!");
				}
				PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT * FROM good_coins WHERE uuid='"
								+ id + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps1 = Main.sql.getConnection()
							.prepareStatement("UPDATE good_coins SET coins='"
									+ Integer.toString(rs.getInt("coins") + quant)
									+ "' WHERE uuid='" + id + "'");
					ps1.executeUpdate();
					ps1.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void removeCoins(UUID id, int quant) {
		if (MySQL.ativo) {
			try {
				PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT * FROM good_coins WHERE uuid='"
								+ id + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps1 = Main.sql.getConnection()
							.prepareStatement("UPDATE good_coins SET coins='"
									+ Integer.toString(rs.getInt("coins") - quant)
									+ "' WHERE uuid='" + id + "'");
					ps1.executeUpdate();
					ps1.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}