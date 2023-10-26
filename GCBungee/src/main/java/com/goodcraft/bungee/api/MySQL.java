package com.goodcraft.bungee.api;

import com.goodcraft.bungee.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.UUID;

public class MySQL {

    public static boolean ativo = false;
    private static Configuration dbConfigFile;
    private String ip;
    private int porta;
    private String usuario;
    private String senha;
    private String banco;
    private Connection connection;

    public MySQL() throws Exception {
        if (!Main.getPlugin().getDataFolder().exists()) Main.getPlugin().getDataFolder().mkdir();
        File file = new File(Main.getPlugin().getDataFolder(), "banco.yml");
        if (!file.exists()) {
            try (InputStream in = Main.getPlugin().getResourceAsStream("banco.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        dbConfigFile = cfg;
        String db = "bancodedados.";

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

    public static boolean isAtivo() {
        return !dbConfigFile.getString("bancodedados.ativo").equalsIgnoreCase("false");
    }

    public static void sqlConnect() {
        try {
            Main.setSQL(new MySQL());
            if (MySQL.ativo) {
                Message.INFO.send(Main.getPlugin().getProxy().getConsole(), "Conectado ao Banco de Dados com sucesso!");
            } else {
                Message.INFO.send(Main.getPlugin().getProxy().getConsole(), "Uso do Banco de Dados desligado, desativando MySQL!");
            }
        } catch (Exception ex) {
            Message.ERROR.send(Main.getPlugin().getProxy().getConsole(), "Erro ao conectar ao Banco de Dados");
            MySQL.ativo = false;
            ex.printStackTrace();
        }
    }


    public static boolean addPlayerToTable(UUID id, String table, String[] valuesToInsert, boolean useAI, boolean checkIfNotExists) {
        if (!ativo) {
            return false;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT uuid FROM " + table + " WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            if (!checkIfNotExists || !rs.next()) {
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
                rs.close();
                ps.close();
                return true;
            }
            rs.close();
            ps.close();
            return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getData(String data) {
        if (!ativo) {
            return " ";
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT " + data + " FROM good_data;");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(data);
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return " ";
    }

    public static void changeData(UUID id, String table, String column, String value) {
        if (!MySQL.ativo) {
            return;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT " + column + " FROM " + table + " WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement ps1 = Main.getSQL().getConnection()
                        .prepareStatement("UPDATE " + table + " SET " + column + "='"
                                + value
                                + "' WHERE uuid='" + id + "'");
                ps1.executeUpdate();
                ps1.close();
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateRank(UUID id, Rank r) {
        if (!MySQL.ativo) {
            return;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT rank FROM good_global WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt("rank") == r.ordinal()) return;
                PreparedStatement ps1 = Main.getSQL().getConnection()
                        .prepareStatement("UPDATE good_global SET rank='"
                                + r.ordinal()
                                + "' WHERE uuid='" + id + "'");
                ps1.executeUpdate();
                ps1.close();
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateIP(UUID id, String ip) {
        if (!MySQL.ativo) {
            return;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT ip FROM good_global WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("ip").equalsIgnoreCase(ip)) return;
                PreparedStatement ps1 = Main.getSQL().getConnection()
                        .prepareStatement("UPDATE good_global SET ip='"
                                + ip
                                + "' WHERE uuid='" + id + "'");
                ps1.executeUpdate();
                ps1.close();
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                if (Main.getSQL().hasConnection()) Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
