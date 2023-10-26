package com.goodcraft.bungee.punishments;

import java.util.Arrays;

public enum PunishmentType {
    FLOOD_SPAM("Flood/Spam", Type.MUTE, 6, "flood", "spam"),
    INICIATIVA_FLOOD("Iniciativa de Flood", Type.MUTE, 4, "iniciativa_flood", "iniciativa-flood", "iniciativaflood",
            "iniciativadeflood"),
    DIVULGACAO("Divulgação", Type.BAN, 0, "divulgacao", "divulgaçao", "divulgação"),
    OFENSA_JOGADOR("Ofensa a Jogador", Type.MUTE, 8, "ofensa-jogador", "ofensa_jogador",
            "ofensajogador", "ofensaajogador"),
    OFESA_SERVIDOR_STAFF("Ofensa ao Servidor/Staff", Type.MUTE, 12, "ofensa-staff", "ofensa-servidor", "ofensa_staff",
            "ofensa_servidor", "ofensaservidor", "ofensastaff", "ofensaaoservidor", "ofensaastaff"),
    PALAVRAS_INADEQUADAS("Palavras Inadequadas", Type.MUTE, 6, "palavroes", "palavras_inadequadas", "palavras-inadequadas",
            "palavrasinadequadas"),
    INICIATIVA_BRIGA("Iniciativa de Briga", Type.MUTE, 5, "briga", "iniciativa_briga", "iniciativa-briga", "iniciativadebriga",
            "iniciativabriga"),
    USO_HACK("Uso de Hack", Type.BAN, 0, "hack", "uso_hack", "uso-hack", "usodehack", "usohack"),
    ABUSO_BUGS("Abuso de Bugs", Type.BAN, 0, "bugs", "abuso", "abuso-bugs", "abuso_bugs", "abusobugs",
            "abusodebugs"),
    ANTI_JOGO("Anti Jogo", Type.MUTE, 4, "antijogo", "anti-jogo", "anti_jogo"),
    CHAT_FAKE("Chat Fake", Type.MUTE, 2, "fakechat", "chatfake", "chat-fake", "chat_fake", "fake_chat", "fake-chat");

    private final String name;
    private final Type type;
    private final int time;
    private String[] aliases;

    PunishmentType(String name, Type type, int time, String... aliases) {
        this.name = name;
        this.aliases = aliases;
        this.type = type;
        this.time = time;
    }

    public static PunishmentType getByName(String name) {
        for (PunishmentType p : PunishmentType.values()) {
            if (p.name().equalsIgnoreCase(name)) return p;
            if (p.toString().equalsIgnoreCase(name)) return p;
            if (Arrays.asList(p.getAliases()).contains(name.toLowerCase())) return p;
        }
        return null;
    }

    public String toString() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getTime() {
        return time;
    }

    public String[] getAliases() {
        if (aliases != null) {
            return aliases;
        }
        return new String[]{};
    }

    public enum Type {
        BAN, MUTE;
    }
}
