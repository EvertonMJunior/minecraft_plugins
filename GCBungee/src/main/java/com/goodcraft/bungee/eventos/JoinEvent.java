package com.goodcraft.bungee.eventos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Message;
import com.goodcraft.bungee.api.MySQL;
import com.goodcraft.bungee.api.Rank;
import com.goodcraft.bungee.api.Title;
import com.goodcraft.bungee.punishments.PunishmentAPI;
import com.goodcraft.bungee.punishments.PunishmentType;
import com.goodcraft.bungee.servers.Lobby;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class JoinEvent implements Listener {

    public static ArrayList<UUID> joined = new ArrayList<>();

    @EventHandler
    public void onHandShake(PlayerHandshakeEvent e) {
        int version = e.getHandshake().getProtocolVersion();
        if (version != 47) {
            e.getConnection().disconnect(new TextComponent[]{
                    new TextComponent("§c§lGOOD CRAFT"),
                    new TextComponent("\n"),
                    new TextComponent("\n"),
                    new TextComponent("§cO servidor está na versão 1.8 do Minecraft."),
                    new TextComponent("\n"),
                    new TextComponent("§cPara jogar, você precisa usá-la!")
            });
            return;
        }

        if (!Lobby.isThereDisponibleLobbys(null)) {
            e.getConnection().disconnect(new TextComponent("§c§lGOOD CRAFT \n\n" +
                    "§cNão há nenhum Lobby disponível no momento. \n" +
                    "§cPor favor, tente mais tarde."));
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(LoginEvent e) {

        if (MySQL.getData("manutencao").equalsIgnoreCase("true")) {
            if (Rank.has(e.getConnection().getUniqueId(), Rank.MOD)) {
                return;
            }
            e.setCancelled(true);
            e.setCancelReason("§c§lGOOD CRAFT \n\n" +
                    "§cO servidor está em manutenção, voltaremos já!");
            return;
        }
        List<PunishmentAPI.Punimento> punimentos = PunishmentAPI.getPunishments(e.getConnection().getUniqueId());

        if (!punimentos.isEmpty()) {
            for (PunishmentAPI.Punimento p : punimentos) {
                if (p.getType().getType() == PunishmentType.Type.BAN) {
                    if (p.isExpired()) continue;
                    e.setCancelled(true);
                    e.setCancelReason("§c§lGOOD CRAFT \n\n" +
                            "§cVocê " + (p.isPermanent() ? "foi permanentemente" : "está") + " banido por " +
                            p.getType().toString() + "!\n" +
                            "§cData do banimento: " + p.getFormatedDate() + (!p.isPermanent() ? "\n" +
                            "§cProva: " + p.getProof() + "\n" +
                            "§cExpira em: " + p.getFormatedExpireDate() : ""));
                    break;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPostJoin(PostLoginEvent e) {
        final ProxiedPlayer p = e.getPlayer();
        final String ip = p.getAddress().getHostName();
        final Rank r = Rank.getPlayerRank(p.getUniqueId());

        Lobby.setReconnect(p);

        p.setDisplayName(r.getColor() + (r != Rank.NORMAL ? "[" + r.getAliases().get(0) + "] " : "") + p.getName() +
                (r != Rank.NORMAL ? "§f" : "§7"));

        Main.getPlugin().getProxy().getScheduler().runAsync(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if (!MySQL.addPlayerToTable(p.getUniqueId(), "good_global", new String[]{
                        "100",
                        ip,
                        String.valueOf(System.currentTimeMillis()),
                        String.valueOf(Rank.values().length - 1),
                        ""
                }, true, true)) {
                    MySQL.updateIP(p.getUniqueId(), ip);
                }

            }
        });
    }

    @EventHandler
    public void onJoinLobby(ServerConnectedEvent e) {
        String name = e.getServer().getInfo().getName();
        if (!name.startsWith("lobby")) return;

        final ProxiedPlayer p = e.getPlayer();

        if (!joined.contains(p.getUniqueId())) {
            joined.add(p.getUniqueId());

            Message.GOOD.send(p, "Bem-vindo, <p>!");
            Message.GOOD.send(p, "Use a bússola para navegar entre os modos de jogo!");

            Title.GOOD.send(p, "Bem-vindo!", "Confira nossos §lMODOS DE JOGO§r!");
            Main.getPlugin().getProxy().getScheduler().schedule(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    p.chat("/gotospawn");
                }
            }, 50, TimeUnit.MILLISECONDS);
        }

        p.setTabHeader(new TextComponent("\n"
                        + "§3§lGood Craft\n"
                        + "§fSite: www.good-craft.net\n"),
                new TextComponent("\n    "
                        + "§fAdquira VIP acessando: loja.good-craft.net    \n"));
    }

    @EventHandler
    public void onKickServer(ServerKickEvent e) {
        Lobby.setReconnect(e.getPlayer());
    }

    @EventHandler
    public void onDisconnect(ServerDisconnectEvent e) {
        Lobby.setReconnect(e.getPlayer());
    }

    @EventHandler
    public void onDisconnect2(PlayerDisconnectEvent e) {
        Lobby.setReconnect(e.getPlayer());
        Lobby.connect(e.getPlayer());
        if (joined.contains(e.getPlayer().getUniqueId())) {
            joined.remove(e.getPlayer().getUniqueId());
        }
    }

}
