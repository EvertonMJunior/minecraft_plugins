package me.dark.MySQL;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import me.dark.Main;
public class MySQL {
	    public static boolean ativo = false;
		private String ip;
		private int porta;
		private String usuario;
		private String senha;
		private String banco;
		
		private Connection conn;
		
		public MySQL() throws Exception {
			File file = new File("plugins/GoodCraft/", "banco.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			fil = cfg;
			String db = "bancodedados.";
			cfg.addDefault(db + "ativo", "false");
			cfg.addDefault(db + "ip", "127.0.0.1");
			cfg.addDefault(db + "porta", 3306);
			cfg.addDefault(db + "usuario", "root");
			cfg.addDefault(db + "senha", "");
			cfg.addDefault(db + "banco", "banco");
			cfg.options().copyDefaults(true);
			try {
				cfg.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (isAtivo()) {
				ativo = true;
			} 
			if (ativo) {
				this.ip = cfg.getString(db + "ip");
				this.porta = cfg.getInt(db + "porta");
				this.usuario = cfg.getString(db + "usuario");
				this.senha = cfg.getString(db + "senha");
				this.banco = cfg.getString(db + "banco");
				
				this.openConnection();
			}
		}
		private static FileConfiguration fil;
		
		public static boolean isAtivo(){
			if (fil.getString("bancodedados.ativo").equalsIgnoreCase("false")) {
				return false;
			}
			return true;
		}
	
	public Connection openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.ip + ":" + this.porta + "/" + this.banco, this.usuario, this.senha);
		this.conn = conn;
		return conn;
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public boolean hasConnection() {
		try {
			return this.conn != null || this.conn.isValid(1);
		} catch (SQLException e) {
			return false;
		}
	}
	
	public void queryUpdate(String query) {
		Connection conn = this.conn;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(query);
			st.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to send update '" + query + "'.");
		} finally {
			this.closeRessources(null, st);
		}
	}
	
	public void closeRessources(ResultSet rs, PreparedStatement st) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
	}
	
	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conn = null;
		}
	}
	
	public static void addPlayerToTable(UUID p, String s) {
		if (MySQL.ativo) {
			try {
				PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT * FROM "+s+" WHERE uuid='"
								+ p + "'");
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					PreparedStatement ps1 = Main.sql.getConnection()
							.prepareStatement("INSERT INTO "+s+" VALUES('"
									+ p + "', '0', '0', '0')");
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
	
	public static void addPlayerToTable1(UUID p, String s) {
		if (MySQL.ativo) {
			try {
				PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT * FROM "+s+" WHERE uuid='"
								+ p + "'");
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					PreparedStatement ps1 = Main.sql.getConnection()
							.prepareStatement("INSERT INTO "+s+" VALUES('"
									+ p + "', '0')");
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