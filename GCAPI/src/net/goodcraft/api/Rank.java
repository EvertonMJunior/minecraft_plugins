package net.goodcraft.api;

import net.goodcraft.Main;
import net.goodcraft.sql.MySQL;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public enum Rank {
    DONO("§4", "DONO", "dono", "owner", "master"), //0
    ADMIN("§c", "ADMIN", "administrador", "adm", "admin"), //1
    DEVELOPER("§9", "DEV", "desenvolvedor", "dev", "developer", "programador", "coder"), //2
    MODPLUS("§2", "MOD+", "moderador+", "mod+", "moderadorplus", "modplus"), //3
    MOD("§a", "MOD", "moderador", "mod"), //4
    BUILDER("§3", "BUILDER", "construtor", "cnt", "builder"), //5
    YOUTUBER("§b", "YT", "youtuber"), //6
    OURO("§6", "OURO", "ouro", "gold"), //7
    PRATA("§8", "PRATA", "prata", "silver"), //8
    BRONZE("§e", "BRONZE", "bronze", "bronze"), //9
    NORMAL("§7", "", "normal", "undefined", "default"); //10

    private String color;
    private String[] aliases;

    Rank(String color, String... aliases) {
        this.color = color;
        this.aliases = aliases;
    }

    public static boolean check(Player p, Rank per) {
        if (!has(Main.rankCache.containsKey(p.getUniqueId()) ? Main.rankCache.get(p.getUniqueId()) : Rank.NORMAL, per)) {
            Message.ERROR.send(p, "Você precisa ao menos do rank [" + per + "] para usar isto!");
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
        return has(Main.rankCache.containsKey(id) ? Main.rankCache.get(id) : Rank.NORMAL, per);
    }

    public static Rank getPlayerRank(UUID id) {
        if (Main.rankCache.containsKey(id)) {
            return Main.rankCache.get(id);
        }
        return NORMAL;
    }

    public static void syncPlayerRank(UUID id, SQLStatus.Callback<Rank> ca) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    if (Main.rankCache.containsKey(id)) {
                        Main.rankCache.remove(id);
                        Main.rankCache.put(id, NORMAL);
                        return;
                    }
                    Main.rankCache.put(id, NORMAL);
                    ca.onSucess(Rank.NORMAL);
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
                        if (Main.rankCache.containsKey(id)) {
                            Main.rankCache.remove(id);
                            Main.rankCache.put(id, Rank.values()[rank]);
                            ca.onSucess(Rank.values()[rank]);
                            return;
                        }
                        Main.rankCache.put(id, Rank.values()[rank]);
                        ca.onSucess(Rank.values()[rank]);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    try {
                        if (Main.getSQL().hasConnection()) Main.getSQL().openConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (Main.rankCache.containsKey(id)) {
                        Main.rankCache.replace(id, NORMAL);
                        return;
                    }
                    Main.rankCache.put(id, NORMAL);
                    ca.onSucess(Rank.NORMAL);
                }
            }
        }.runTaskAsynchronously(Main.getPlugin());
    }

    public static Rank getByName(String name) {
        for (Rank rank : Rank.values()) {
            if (rank.getAliases().contains(name.toLowerCase())) {
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
}
