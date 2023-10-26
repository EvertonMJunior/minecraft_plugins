package com.goodcraft.bungee.api;

import com.goodcraft.bungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public enum Rank {
    DONO("§4", "DONO", "dono", "owner", "master"), //0
    ADMIN("§c", "ADMIN", "admin", "administrador", "adm"), //1
    DEVELOPER("§9", "DEV", "coder", "dev", "developer", "programador"), //2
    MODPLUS("§2", "MOD+", "modplus", "mod+", "moderador+", "moderadorplus"), //3
    MOD("§a", "MOD", "moderador", "mod"), //4
    BUILDER("§3", "BUILDER", "builder", "construtor", "cnt"), //5
    YOUTUBER("§b", "YT", "youtuber"), //6
    OURO("§6", "OURO", "gold"), //7
    PRATA("§8", "PRATA", "silver"), //8
    BRONZE("§e", "BRONZE"), //9
    NORMAL("§7", "", "default", "undefined", "normal"); //10

    private String color;
    private String[] aliases;

    Rank(String color, String... aliases) {
        this.color = color;
        this.aliases = aliases;
    }

    public static boolean check(ProxiedPlayer p, Rank per) {
        if (!has(p.getUniqueId(), per)) {
            Message.ERROR.send(p, "Você precisa ao menos do rank [" + per.name() + "] para usar isto!");
            return false;
        }
        return true;
    }

    public static boolean has(Rank rank, Rank per) {
        if (per == NORMAL) {
            return true;
        }

        if (rank == per) {
            return true;
        }

        for (Rank perm : Rank.values()) {
            if (perm.ordinal() <= per.ordinal()) {
                if (rank == perm) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean has(UUID id, Rank per) {
        if (per == NORMAL) {
            return true;
        }

        Rank rank = getPlayerRank(id);

        if (rank == per) {
            return true;
        }

        for (Rank perm : Rank.values()) {
            if (perm.ordinal() <= per.ordinal()) {
                if (rank == perm) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Rank getPlayerRank(UUID id) {
        if (!MySQL.ativo) {
            return NORMAL;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT rank FROM good_global WHERE uuid='" + id + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int rank = rs.getInt("rank");
                if (!rs.isClosed()) {
                    rs.close();
                }
                if (!ps.isClosed()) {
                    ps.close();
                }
                return Rank.values()[rank];
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
        return NORMAL;
    }

    public static Rank getByName(String name) {
        for (Rank rank : Rank.values()) {
            if (rank.getAliases().contains(name.toLowerCase()) || rank.name().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public List<String> getAliases() {
        return Arrays.asList(aliases);
    }

    public String getColor() {
        return color;
    }

    public ArrayList<Rank> getUpper() {
        Rank pRank = this;
        ArrayList<Rank> upperRanks = new ArrayList<>();
        for (Rank r : Rank.values()) {
            if (has(pRank, r)) continue;
            upperRanks.add(r);
        }
        return upperRanks;
    }

    public ArrayList<Rank> getLower() {
        Rank pRank = this;
        ArrayList<Rank> lowerRanks = new ArrayList<>();
        for (Rank r : Rank.values()) {
            if (!has(pRank, r)) continue;
            lowerRanks.add(r);
        }
        return lowerRanks;
    }
}
