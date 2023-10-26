package me.everton.WocPvP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MySql {

	public static Connection connection;

	public MySql() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + Main.settings.getConfig().getString("mysql.ip") + "/"
					+ Main.settings.getConfig().getString("mysql.db") + "?user=" + Main.settings.getConfig().getString("mysql.user") + "&password=" + Main.settings.getConfig().getString("mysql.senha"));
			connection = DriverManager.getConnection("jdbc:mysql://"
					+ Main.settings.getConfig().getString("mysql.ip") + "/"
					+ Main.settings.getConfig().getString("mysql.db")
					+ "?user="
					+ Main.settings.getConfig().getString("mysql.user")
					+ "&password="
					+ Main.settings.getConfig().getString("mysql.senha"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reportar(String p, String motivo, String autor) {
	        try {
	                PreparedStatement statement = connection.prepareStatement("insert into reports (dequem, motivo, autor)\nvalues ('" + p + "', '" + motivo + "', '" + autor +"');");
	                statement.executeUpdate();
	                statement.close();
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	    }
}