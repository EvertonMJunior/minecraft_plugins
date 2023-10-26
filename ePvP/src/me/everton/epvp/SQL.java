package me.everton.epvp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQL {
	public static Connection connection;
	
	public synchronized static void openConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/epvp", "root", "vertrigo");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static void closeConnection() {
		try {
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static boolean tableContainsPlayer(String p) {
		openConnection();
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM player_data WHERE player=?;");
			sql.setString(1, p);
			ResultSet resultSet = sql.executeQuery();
			boolean cp = resultSet.next();
			
			sql.close();
			resultSet.close();
			
			return cp;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}