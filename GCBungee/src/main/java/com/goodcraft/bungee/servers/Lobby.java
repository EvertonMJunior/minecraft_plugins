package com.goodcraft.bungee.servers;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Message;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Lobby {
    public static boolean isThereDisponibleLobbys(ProxiedPlayer p) {
        Map<ServerInfo, Integer> lobbys = new HashMap<>();
        for (ServerInfo s : Main.getPlugin().getProxy().getServers().values()) {
            if (!s.getName().startsWith("lobby")) continue;
            if (p != null && !s.canAccess(p)) continue;
            lobbys.put(s, s.getPlayers().size());
        }
        return !lobbys.isEmpty();
    }

    public static void connect(ProxiedPlayer p) {
        if (!isThereDisponibleLobbys(p)) {
            Message.ERROR.send(p, "No momento, não há nenhum Lobby disponível.");
            return;
        }
        setReconnect(p);
        p.connect(p.getReconnectServer());
    }

    public static ServerInfo getLobby(ProxiedPlayer p) {
        Map<ServerInfo, Integer> lobbys = new HashMap<>();
        for (ServerInfo s : Main.getPlugin().getProxy().getServers().values()) {
            if (!s.getName().startsWith("lobby")) continue;
            if (!s.canAccess(p)) continue;
            lobbys.put(s, s.getPlayers().size());
        }

        if (lobbys.isEmpty()) {
            return null;
        }

        Map.Entry<ServerInfo, Integer> min = Collections.min(lobbys.entrySet(), new Comparator<Map.Entry<ServerInfo, Integer>>() {
            @Override
            public int compare(Map.Entry<ServerInfo, Integer> o1, Map.Entry<ServerInfo, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        return min.getKey();
    }

    public static String setReconnect(ProxiedPlayer p) {
        ServerInfo lobby = getLobby(p);

        if (lobby == null) {
            p.disconnect(new TextComponent("§c§lGOOD CRAFT \n\n§cNão há nenhum Lobby disponível no momento. \n§cPor favor, tente mais tarde."));
            return "§c§lGOOD CRAFT \n\n§cNão há nenhum Lobby disponível no momento. \n§cPor favor, tente mais tarde.";
        }

        p.setReconnectServer(lobby);
        return "Conectado";
    }
}
