package com.goodcraft.bungee.eventos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.servers.LobbyGUI;
import dev.wolveringer.BungeeUtil.Player;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class LobbyGUIEvent implements Listener{
    @EventHandler
    public void onPluginMessage(PluginMessageEvent ev) {
        if (!ev.getTag().equals("OpenLobbyGUI")) {
            return;
        }

        if (!(ev.getSender() instanceof Server)) {
            return;
        }

        ByteArrayInputStream stream = new ByteArrayInputStream(ev.getData());
        DataInputStream in = new DataInputStream(stream);
        try {
            Player p = (Player) Main.getPlugin().getProxy().getPlayer(in.readUTF());
            if(p == null) throw new Exception("Player is null");
            LobbyGUI.open(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
