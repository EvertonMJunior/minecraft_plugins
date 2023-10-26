package com.goodcraft.sql;

import com.goodcraft.Main;
import com.goodcraft.api.Message;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MySQL {

    public static boolean ativo = false;
    private static FileConfiguration dbConfigFile;

    public static boolean isAtivo() {
        return !dbConfigFile.getString("bancodedados.ativo").equalsIgnoreCase("false");
    }

    public static void sqlConnect() {
        try {
            Main.setSQL(new MySQL());
            if (MySQL.ativo) {
                Message.INFO.send(Bukkit.getConsoleSender(), "Conectado ao Banco de Dados com sucesso!");
            } else {
                Message.INFO.send(Bukkit.getConsoleSender(), "Uso do Banco de Dados desligado, desativando MySQL!");
            }
        } catch (Exception ex) {
            Message.ERROR.send(Bukkit.getConsoleSender(), "Erro ao conectar ao Banco de Dados\n" + ex.getMessage());
            MySQL.ativo = false;
        }
    }

    public static void addPlayerToTable(UUID id, String table, String[] valuesToInsert, boolean useAI) {
        if (!ativo) {
            return;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT uuid FROM " + table + " WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                String values = (useAI ? "'0', " : "") + "'" + id + "', ";
                for (int i = 0; i < valuesToInsert.length; i++) {
                    String virgula = "";
                    if (i < (valuesToInsert.length - 1)) {
                        virgula = ", ";
                    }
                    values += "'" + valuesToInsert[i] + "'" + virgula;
                }
                PreparedStatement ps1 = Main.getSQL().getConnection()
                        .prepareStatement("INSERT INTO " + table + " VALUES(" + values + ")");
                ps1.executeUpdate();
                ps1.close();
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private String ip;
    private int porta;
    private String usuario;
    private String senha;
    private String banco;

    private Connection connection;

    public MySQL() throws Exception {
        File file = new File("plugins/GCAPI/", "banco.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        dbConfigFile = cfg;
        String db = "bancodedados.";
        cfg.addDefault(db + "ativo", "false");
        cfg.addDefault(db + "ip", "127.0.0.1");
        cfg.addDefault(db + "porta", 3306);
        cfg.addDefault(db + "usuario", "root");
        cfg.addDefault(db + "senha", "vertrigo");
        cfg.addDefault(db + "banco", "goodcraft");
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

    public Connection openConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + this.ip + ":" + this.porta + "/"
                + this.banco, this.usuario, this.senha);
        this.connection = connection;
        return connection;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public boolean hasConnection() {
        try {
            return this.connection != null || this.connection.isValid(1);
        } catch (SQLException e) {
            return false;
        }
    }

    public void queryUpdate(String query) {
        Connection conn = this.connection;
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(query);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
        } finally {
            this.closeResources(null, st);
        }
    }

    public void closeResources(ResultSet rs, PreparedStatement st) {
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
            if (!ativo) {
                return;
            }
            if (this.connection.isClosed()) {
                return;
            }
            this.connection.close();
        } catch (SQLException e) {
        } finally {
            this.connection = null;
        }
    }

}
