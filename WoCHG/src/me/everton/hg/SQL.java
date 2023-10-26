package me.everton.hg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQL {
	public static Connection connection;

	public synchronized static void openConnection() {
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/epvp", "root", "vertrigo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void closeConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void putReport(String reportado, String motivo, String por) {
		openConnection();
		try {
			PreparedStatement newReport = SQL.connection
					.prepareStatement("INSERT INTO reports (reportado, motivo, por) values(?,?,?);");
			newReport.setString(1, reportado);
			newReport.setString(2, motivo);
			newReport.setString(3, por);
			newReport.execute();
			newReport.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public synchronized static boolean tableContainsPlayer(String p) {
		openConnection();
		try {
			PreparedStatement sql = connection
					.prepareStatement("SELECT * FROM player_data WHERE player=?;");
			sql.setString(1, p);
			ResultSet resultSet = sql.executeQuery();
			boolean cp = resultSet.next();

			sql.close();
			resultSet.close();

			return cp;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}