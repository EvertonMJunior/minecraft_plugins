package com.goodcraft.api;

import com.goodcraft.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLUtils {

    private static Connection c = Main.getSQL().getConnection();

    public static void register(String name, String ip, UUID uuid) {
        try {
            PreparedStatement ps = c.prepareStatement("SELECT uuid FROM jogadores WHERE uuid = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement update = c.prepareStatement("UPDATE jogadores SET nick = ?, ip = ?");
                update.setString(1, name);
                update.setString(2, ip);
                update.executeUpdate();
                return;
            }
            PreparedStatement ps1 = c.prepareStatement("INSERT INTO jogadores (nick, uuid, firstjoin, ip)"
                    + " values (?, ?, ?, ?)");
            ps1.setString(1, name);
            ps1.setString(2, uuid.toString());
            ps1.setLong(3, System.currentTimeMillis());
            ps1.setString(4, ip);
            ps1.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SQLUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isRegistered(UUID uuid) {
        try {
            PreparedStatement ps = c.prepareStatement("SELECT uuid FROM jogadores WHERE uuid = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(SQLUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static String getFirstJoin(UUID uuid) {
        try {
            PreparedStatement ps1 = c.prepareStatement("SELECT firstjoin FROM jogadores WHERE uuid = ?");
            ps1.setString(1, uuid.toString());
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                long l = rs1.getLong("firstjoin");
                Date d = new Date(l);
                DateFormat data = DateFormat.getDateInstance(DateFormat.FULL);
                DateFormat horario = DateFormat.getTimeInstance(DateFormat.MEDIUM);
                return data.format(d) + " às " + horario.format(d);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static long getFirstJoinLong(UUID uuid) {
        try {
            PreparedStatement ps1 = c.prepareStatement("SELECT firstjoin FROM jogadores WHERE uuid = ?");
            ps1.setString(1, uuid.toString());
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                long l = rs1.getLong("firstjoin");
                return l;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }
}
