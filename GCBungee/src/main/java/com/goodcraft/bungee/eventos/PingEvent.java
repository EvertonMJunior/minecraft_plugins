package com.goodcraft.bungee.eventos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.MySQL;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;


public class PingEvent implements Listener {
    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing sp = e.getResponse();
        String linha1 = "      §3§lGoodCraft Network §7(1.8.*)";
        String linha2 = " §f§lCONFIRA NOSSOS §c§lMODOS DE JOGO§f§l!";

        sp.setDescriptionComponent(new TextComponent(linha1 + " \n" + linha2));

        ServerPing.PlayerInfo[] pInfo = new ServerPing.PlayerInfo[]{
                new ServerPing.PlayerInfo("§3§lGoodCraft Network", UUID.randomUUID()),
                new ServerPing.PlayerInfo(" ", UUID.randomUUID()),
                new ServerPing.PlayerInfo("§fVersão: §71.8.*", UUID.randomUUID()),
                new ServerPing.PlayerInfo("§fSite: §7www.good-craft.net", UUID.randomUUID()),
                new ServerPing.PlayerInfo("§fLoja: §7loja.good-craft.net", UUID.randomUUID()),
        };
        sp.setPlayers(new ServerPing.Players(1500, Main.getPlugin().getProxy().getOnlineCount(), pInfo));
        sp.setVersion(new ServerPing.Protocol("Requer MC 1.8.*", 47));
        if (e.getConnection().getVersion() == 47 && MySQL.getData("manutencao").equalsIgnoreCase("true")) {
            sp.setVersion(new ServerPing.Protocol("Manutenção", 1));
        }
        e.setResponse(sp);
    }
}
