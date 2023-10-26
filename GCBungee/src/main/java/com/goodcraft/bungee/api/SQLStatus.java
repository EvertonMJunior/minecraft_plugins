package com.goodcraft.bungee.api;

import com.goodcraft.bungee.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class SQLStatus {

    public SQLStatus() {
        if (MySQL.ativo) {
            MySQL sql = Main.getSQL();
        }
    }

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
            try {
                if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            try {
                if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                Locale ptBr = new Locale("pt", "BR");

                DateFormat data = DateFormat.getDateInstance(DateFormat.SHORT, ptBr);
                DateFormat horario = DateFormat.getTimeInstance(DateFormat.MEDIUM, ptBr);

                return data.format(d) + " " + horario.format(d);
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
            try {
                if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            try {
                if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static void addStatus(UUID id, GameMode gm, String status, int value) {
        if (!MySQL.ativo) {
            return;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT " + status + " FROM good_" + gm.name().toLowerCase() + " WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int valor = rs.getInt(status) + value;

                PreparedStatement ps1 = Main.getSQL().getConnection()
                        .prepareStatement("UPDATE good_" + gm.name().toLowerCase() + " SET " + status + "='"
                                + Integer.toString(valor)
                                + "' WHERE uuid='" + id + "'");
                ps1.executeUpdate();
                ps1.close();
                return;
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

    public static int getStatus(UUID id, GameMode gm, String status) {
        if (!MySQL.ativo) {
            return 0;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT " + status + " FROM good_" + gm.name().toLowerCase() + " WHERE uuid='"
                            + id + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int stat = rs.getInt(status);
                if (!rs.isClosed()) {
                    rs.close();
                }
                if (!ps.isClosed()) {
                    ps.close();
                }
                return stat;
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                if (Main.getSQL().hasConnection()) Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
