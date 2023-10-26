package com.goodcraft.sql;

import com.goodcraft.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.UUID;

public class SQLStatus {

    public static boolean exists(UUID id) {
        if (!MySQL.ativo) {
            return false;
        }
        boolean exists = false;
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT coins FROM good_global WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            exists = rs.next();
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return exists;
    }

    public static int getCoins(UUID id) {
        if (!MySQL.ativo) {
            return 0;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT coins FROM good_global WHERE uuid='"
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

    public static String getFormattedCoins(UUID id) {
        int value = getCoins(id);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        String pattern = "#,###,###";
        DecimalFormat myFormatter = new DecimalFormat(pattern, symbols);
        String coins = myFormatter.format(value);
        return coins;
    }

    public static String getFirstJoin(UUID id) {
        if (!MySQL.ativo) {
            return "ERRO";
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT firstjoin FROM good_global WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long firstJoin = rs.getLong("firstjoin");
                if (!rs.isClosed()) {
                    rs.close();
                }
                if (!ps.isClosed()) {
                    ps.close();
                }

                Date d = new Date(firstJoin);
                DateFormat data = DateFormat.getDateInstance(DateFormat.SHORT);
                DateFormat horario = DateFormat.getTimeInstance(DateFormat.MEDIUM);

                return data.format(d) + " " + horario.format(d);
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return "ERRO";
    }

    public static int addCoins(UUID id, int value) {
        if (!MySQL.ativo) {
            return 0;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT coins FROM good_global WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int valor = rs.getInt("coins") + value;
                PreparedStatement ps1 = Main.getSQL().getConnection()
                        .prepareStatement("UPDATE good_global SET coins='"
                                + Integer.toString(valor)
                                + "' WHERE uuid='" + id + "'");
                ps1.executeUpdate();
                ps1.close();
                return valor;
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    public static int removeCoins(UUID id, int value) {
        if (!MySQL.ativo) {
            return 0;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT coins FROM good_global WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int valor = rs.getInt("coins") - value;
                if (valor < 0) {
                    valor = 0;
                }

                PreparedStatement ps1 = Main.getSQL().getConnection()
                        .prepareStatement("UPDATE good_global SET coins='"
                                + Integer.toString(valor)
                                + "' WHERE uuid='" + id + "'");
                ps1.executeUpdate();
                ps1.close();

                return valor;
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return 0;
    }
    
    

    public SQLStatus(Main plugin) {
        if (MySQL.ativo) {
            MySQL sql = Main.getSQL();
        }
    }
}
