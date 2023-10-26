package me.everton.eapi;

import java.util.UUID;

public class PlayerData {

    private final String nick;
    private final UUID uuid;
    private final long firstJoin;
    private final String ip;

    public PlayerData(String nick, UUID uuid, long firstJoin, String ip) {
        this.nick = nick;
        this.uuid = uuid;
        this.firstJoin = firstJoin;
        this.ip = ip;
    }
}
