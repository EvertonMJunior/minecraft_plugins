package com.goodcraft.bungee.eventos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.servers.Lobby;
import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class DisconnectHandlerEvent implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onServerKickEvent(ServerKickEvent ev) {
        Plugin plugin = Main.getPlugin();

        ServerInfo kickedFrom;

        if (ev.getPlayer().getServer() != null) {
            kickedFrom = ev.getPlayer().getServer().getInfo();
        } else if (plugin.getProxy().getReconnectHandler() != null) {
            kickedFrom = plugin.getProxy().getReconnectHandler().getServer(ev.getPlayer());
        } else {
            kickedFrom = AbstractReconnectHandler.getForcedHost(ev.getPlayer().getPendingConnection());
            if (kickedFrom == null) {
                kickedFrom = ProxyServer.getInstance().
                        getServerInfo(ev.getPlayer().getPendingConnection().getListener().getDefaultServer());
            }
        }

        ServerInfo kickTo = Lobby.getLobby(ev.getPlayer());

        if (kickedFrom != null && kickedFrom.equals(kickTo)) {
            return;
        }

        ev.setCancelled(true);
        ev.setCancelServer(kickTo);
        ev.setKickReasonComponent(null);
    }
}
