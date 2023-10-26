package net.goodcraft.sql;

import net.goodcraft.GameMode;
import net.goodcraft.Main;
import net.goodcraft.api.Rank;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class SQLStatus {

    public SQLStatus() {
        if (MySQL.ativo) {
            MySQL sql = Main.getSQL();
        }
    }

    public static void exists(UUID id, Callback<Boolean> c) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    c.onFailure(new Exception());
                    return;
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
                c.onSucess(exists);
            }
        }.runTaskAsynchronously(Main.getPlugin());
    }

    public static void getFirstJoin(UUID id, Callback<String> c) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    c.onFailure(new Exception());
                    return;
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

                        c.onSucess(data.format(d) + " " + horario.format(d));
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
                    c.onFailure(new Exception());
                }
            }
        }.runTaskAsynchronously(Main.getPlugin());
    }

    public static void addCoins(UUID id, int value) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    return;
                }
                try {
                    PreparedStatement ps = Main.getSQL().getConnection()
                            .prepareStatement("SELECT coins FROM good_global WHERE uuid='"
                                    + id + "'");
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        int valor = rs.getInt("coins") + value;

                        Rank r = Rank.getPlayerRank(id);
                        if(Rank.has(r, Rank.BUILDER)){
                            valor *= 5;
                        } else if(Rank.has(r, Rank.OURO)){
                            valor *= 4;
                        } else if(Rank.has(r, Rank.PRATA)){
                            valor *= 3;
                        } else if(Rank.has(r, Rank.BRONZE)){
                            valor *= 2;
                        }

                        PreparedStatement ps1 = Main.getSQL().getConnection()
                                .prepareStatement("UPDATE good_global SET coins='"
                                        + Integer.toString(valor)
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
        }.runTaskAsynchronously(Main.getPlugin());
    }

    public static void removeCoins(UUID id, int value) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    return;
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
        }.runTaskAsynchronously(Main.getPlugin());
    }

    public static void addStatus(UUID id, GameMode gm, String status, int value) {
        new BukkitRunnable() {
            @Override
            public void run() {
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
                        if(valor < 0){
                            valor = 0;
                        }

                        PreparedStatement ps1 = Main.getSQL().getConnection()
                                .prepareStatement("UPDATE good_" + gm.name().toLowerCase() + " SET " + status + "='"
                                        + Integer.toString(valor)
                                        + "' WHERE uuid='" + id + "'");
                        ps1.executeUpdate();
                        ps1.close();

                        rs.close();
                        ps.close();
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
        }.runTaskAsynchronously(Main.getPlugin());
    }

    public static void getStat(UUID id, String table, String stat, Callback<Object> c) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    c.onFailure(new Exception());
                    return;
                }
                try {
                    PreparedStatement ps = Main.getSQL().getConnection()
                            .prepareStatement("SELECT " + stat + " FROM " + table + " WHERE uuid='"
                                    + id + "'");
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        Object value = rs.getObject(stat);
                        if (!rs.isClosed()) {
                            rs.close();
                        }
                        if (!ps.isClosed()) {
                            ps.close();
                        }
                        c.onSucess(value);

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
                    c.onFailure(new Exception());
                }
            }
        }.runTaskAsynchronously(Main.getPlugin());
    }

    public interface Callback<T> {
        void onSucess(T done);

        void onFailure(Throwable cause);
    }
}
