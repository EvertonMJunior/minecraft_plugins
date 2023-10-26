package com.goodcraft.api;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Utils {

    public static String getXYZ(Location fromToGet) {
        String xyz = "X" + fromToGet.getX() + ", Y" + fromToGet.getY() + ", Z" + fromToGet.getZ();
        return xyz;
    }

    public static String getSentence(String[] args) {
        String sentence = "";
        for (int i = 0; i < args.length; i++) {
            sentence += args[i] + (i == (args.length - 1) ? "" : " ");
        }
        return sentence;
    }

    public static String getSentence(String[] args, int inicial) {
        String sentence = "";
        for (int i = inicial; i < args.length; i++) {
            sentence += args[i] + (i == (args.length - 1) ? "" : " ");
        }
        return sentence;
    }

    public static void broadcast(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

    public static void broadcast(String message, String permission) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission(permission)) {
                continue;
            }
            p.sendMessage(message);
        }
    }

    public static Player getPlayerByUUID(UUID uuid) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getUniqueId().equals(uuid)) {
                return p;
            }
        }

        return null;
    }

    public static void clearChat(Player p) {
        for (int i = 0; i < 90; i++) {
            p.sendMessage(" ");
        }
    }

    public static void onlyVip(Player p) {
        Message.INFO.send(p, "Este recurso é destinado apenas para VIPs! Compre um em good-craft.net");
    }

    public static void hideForAll(Player p) {
        Bukkit.getOnlinePlayers().stream().forEach((oP) -> {
            oP.hidePlayer(p);
        });
    }

    public static void hideForAll(Player p, String permission) {
        Bukkit.getOnlinePlayers().stream().filter((oP) -> !(oP.hasPermission(permission))).forEach((oP) -> {
            oP.hidePlayer(p);
        });
    }

    public static void showForAll(Player p) {
        Bukkit.getOnlinePlayers().stream().forEach((oP) -> {
            oP.showPlayer(p);
        });
    }

    public static void showForAll(Player p, String permission) {
        Bukkit.getOnlinePlayers().stream().filter((oP) -> !(oP.hasPermission(permission))).forEach((oP) -> {
            oP.showPlayer(p);
        });
    }

    public static void hideAllFor(Player p) {
        Bukkit.getOnlinePlayers().stream().forEach((_item) -> {
            p.hidePlayer(p);
        });
    }

    public static void hideAllFor(Player p, String permission) {
        Bukkit.getOnlinePlayers().stream().filter((oP) -> !(oP.hasPermission(permission))).forEach((oP) -> {
            p.hidePlayer(oP);
        });
    }

    public static void showAllFor(Player p) {
        Bukkit.getOnlinePlayers().stream().forEach((_item) -> {
            p.showPlayer(p);
        });
    }

    public static void showAllFor(Player p, String permission) {
        Bukkit.getOnlinePlayers().stream().filter((oP) -> !(oP.hasPermission(permission))).forEach((oP) -> {
            p.showPlayer(oP);
        });
    }

    public static String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }
}
