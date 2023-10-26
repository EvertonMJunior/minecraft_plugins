package com.goodcraft.bungee.punishments;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PunishmentAPI {
    public static List<Punimento> getPunishments(UUID id) {
        if (!MySQL.ativo) {
            return new ArrayList<>();
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT * FROM good_punishments WHERE uuid='" +
                            id
                            + "';");
            ResultSet rs = ps.executeQuery();
            List<Punimento> punimentos = new ArrayList<>();
            while (rs.next()) {
                int pTO = rs.getInt("type");
                PunishmentType pT = null;
                for (PunishmentType pt : PunishmentType.values()) {
                    if (pTO == pt.ordinal()) {
                        pT = pt;
                        break;
                    }
                }
                punimentos.add(new Punimento(pT, UUID.fromString(rs.getString("uuid")),
                        UUID.fromString(rs.getString("author")), rs.getString("prova"), rs.getLong("date")));
            }
            rs.close();
            ps.close();
            return punimentos;
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    public static boolean removePunishment(UUID id, PunishmentType type) {
        if (!MySQL.ativo) {
            return false;
        }
        try {
            List<Punimento> punimentos = getPunishments(id);
            if (punimentos.isEmpty()) {
                return false;
            }
            boolean has = false;
            for (Punimento p : punimentos) {
                if (p.getType() == type) {
                    has = true;
                    break;
                }
            }
            if (!has) return has;

            PreparedStatement ps = Main.getSQL().getConnection().prepareStatement("DELETE FROM good_punishments WHERE " +
                    "uuid='" + id + "' AND type='" + type.ordinal() + "';");
            ps.executeUpdate();
            ps.close();
            return true;
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

    public static class Punimento {
        private PunishmentType type;
        private UUID punished, author;
        private String prova;
        private long date;

        public Punimento(PunishmentType type, UUID punished, UUID author, String prova, long date) {
            this.type = type;
            this.punished = punished;
            this.author = author;
            this.prova = prova;
            this.date = date;
        }

        public PunishmentType getType() {
            return type;
        }

        public UUID getPunished() {
            return punished;
        }

        public UUID getAuthor() {
            return author;
        }

        public String getProof(){
            return prova;
        }

        public long getDate() {
            return date;
        }

        public String getFormatedDate() {
            Date d = new Date(this.date);
            Locale ptBr = new Locale("pt", "BR");

            DateFormat data = DateFormat.getDateInstance(DateFormat.SHORT, ptBr);
            DateFormat horario = DateFormat.getTimeInstance(DateFormat.MEDIUM, ptBr);

            return data.format(d) + " " + horario.format(d);
        }

        public boolean isExpired() {
            long current = System.currentTimeMillis();
            if (type.getTime() == 0) {
                return false;
            }
            return current > (date + TimeUnit.HOURS.toMillis(type.getTime()));
        }

        public boolean isPermanent() {
            return this.type.getTime() == 0;
        }

        public String getFormatedExpireDate() {
            Date d = new Date(date + TimeUnit.HOURS.toMillis(type.getTime()));
            Locale ptBr = new Locale("pt", "BR");

            DateFormat data = DateFormat.getDateInstance(DateFormat.SHORT, ptBr);
            DateFormat horario = DateFormat.getTimeInstance(DateFormat.MEDIUM, ptBr);

            return data.format(d) + " " + horario.format(d);
        }
    }
}
