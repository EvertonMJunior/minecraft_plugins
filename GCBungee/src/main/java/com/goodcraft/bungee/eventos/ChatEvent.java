package com.goodcraft.bungee.eventos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Message;
import com.goodcraft.bungee.api.Rank;
import com.goodcraft.bungee.comandos.StaffChatCmd;
import com.goodcraft.bungee.punishments.PunishmentAPI;
import com.goodcraft.bungee.punishments.PunishmentType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class ChatEvent implements Listener {
    @EventHandler
    public void onChat(net.md_5.bungee.api.event.ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();

        if (e.getMessage().startsWith("/updaterank") || e.getMessage().startsWith("/updatetag")) {
            Rank r = Rank.getPlayerRank(p.getUniqueId());
            p.setDisplayName(r.getColor() + (r != Rank.NORMAL ? "[" + r.getAliases().get(0) + "] " : "") + p.getName() +
                    (r != Rank.NORMAL ? "§f" : "§7"));
            return;
        }

        if (e.getMessage().startsWith("/")) return;

        if (StaffChatCmd.inChat.contains(p.getUniqueId())) {
            StaffChatCmd.broadcast(p, e.getMessage());
            e.setCancelled(true);
            return;
        }

        List<PunishmentAPI.Punimento> punimentos = PunishmentAPI.getPunishments(p.getUniqueId());

        if (!punimentos.isEmpty()) {
            boolean punished = false;
            for (PunishmentAPI.Punimento pu : punimentos) {
                if (pu.getType().getType() == PunishmentType.Type.MUTE) {
                    if (pu.isExpired()) continue;
                    e.setCancelled(true);
                    Message.ERROR.send(p, "Você " + (pu.isPermanent() ? "foi" : "está") + " mutado " +
                            (pu.isPermanent() ? "permanentemente " : "") + "por " + pu.getType().toString() + "!" //MOTIVO
                            + " Prova: " + pu.getProof() + "." //PROVA
                            + (!pu.isPermanent() ? " Expira em: " + pu.getFormatedExpireDate() : "")); //EXPIRA
                    punished = true;
                    break;
                }
            }
            if (punished) return;
        }

        String message = p.getDisplayName() + ": §f" + e.getMessage();
        e.setMessage(message);

        if (p.getServer().getInfo().getName().startsWith("lobby")) {
            e.setCancelled(true);
            for (ProxiedPlayer pl : Main.getPlugin().getProxy().getPlayers()) {
                if (!pl.getServer().getInfo().getName().startsWith("lobby")) {
                    continue;
                }
                pl.sendMessage(new TextComponent(e.getMessage()));
            }
            return;
        }

        e.setCancelled(true);

        for (ProxiedPlayer pl : Main.getPlugin().getProxy().getPlayers()) {
            if (!pl.getServer().getInfo().getName().equals(p.getServer().getInfo().getName())) continue;
            pl.sendMessage(new TextComponent(e.getMessage()));
        }
    }
}
