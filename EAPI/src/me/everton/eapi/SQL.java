package me.everton.eapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL {

    private static Connection connection;

    private String username = null;
    private String password = null;
    private String url = null;

    public SQL(String username, String password, String databaseName, String ip, int port) {
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + ip + ":" + port + "/" + databaseName;

        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
            }
            try {
                setConnection(DriverManager.getConnection(url, username, password));
            } catch (SQLException e) {
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection aConnection) {
        connection = aConnection;
    }

    public void close() {
        try {
            if (connection != null && !getConnection().isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
