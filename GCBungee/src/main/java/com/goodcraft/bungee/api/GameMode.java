package com.goodcraft.bungee.api;

public enum GameMode {
    LOBBY("Lobby"),
    KITPVP("KitPvP"),
    SKYWARS("SkyWars"),
    PARKOUR("Parkour"),
    MEGASW("Mega SkyWars"),
    SPLEEF("Spleef"),
    APOCALIPSE_ZUMBI("Apocalipse Zumbi"),
    TIRO_AO_ALVO("Tiro ao Alvo"),
    CRAZY_ISLAND("Crazy Island"),
    HARDCOREGAMES("Hardcore Games"),
    BUILDBATTLE("Build Battle"),
    OITC("One in The Chamber"),
    CREEPER_RUN("Creeper Run");

    private final String name;

    private GameMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
