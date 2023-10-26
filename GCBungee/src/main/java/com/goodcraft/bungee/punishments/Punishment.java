package com.goodcraft.bungee.punishments;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Message;
import com.goodcraft.bungee.api.MySQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class Punishment {
    private final PunishmentType type;
    private final String toPunishName, prova;
    private final UUID toPunish, author;

    public Punishment(PunishmentType type, String toPunishName, UUID toPunish, UUID author, String prova) {
        this.type = type;
        this.prova = prova;
        this.toPunishName = toPunishName;
        this.toPunish = toPunish;
        this.author = author;
    }

    public boolean punish() {
        String authorName = author.equals(UUID.fromString("f78a4d8d-d51b-4b39-98a3-230f2de0c670")) ? "CONSOLE" :
                Main.getPlugin().getProxy().getPlayer(author).getName();
        boolean punished = MySQL.addPlayerToTable(toPunish, "good_punishments", new String[]{
                String.valueOf(type.ordinal()),
                String.valueOf(author),
                prova != null ? prova : "Nenhuma",
                String.valueOf(System.currentTimeMillis())
        }, false, false);
        if (punished) {
            Message.ERROR.broadcast(" ");

            Message.ERROR.broadcast("[!] " + toPunishName + " foi punido por " + authorName + ".");
            Message.ERROR.broadcast("[!] Motivo: " + type.toString());

            BaseComponent provaClick = new TextComponent(prova);
            provaClick.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new BaseComponent[]{new TextComponent("§cClique para ver a prova")
                    }));
            provaClick.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, prova));
            provaClick.setColor(ChatColor.RED);


            if (prova != null) Message.ERROR.broadcast(new BaseComponent[]{
                    new ComponentBuilder("§c[!] Prova: ").color(ChatColor.RED).create()[0],
                    provaClick
            });
            Message.ERROR.broadcast(" ");

            ProxiedPlayer beingPunished = Main.getPlugin().getProxy().getPlayer(toPunish);
            if (beingPunished != null) {
                PunishmentAPI.Punimento p = new PunishmentAPI.Punimento(type, toPunish, author, prova != null ? prova : "Nenhuma",
                        System.currentTimeMillis());

                if (type.getType() == PunishmentType.Type.BAN) {
                    beingPunished.disconnect(new TextComponent("§c§lGOOD CRAFT \n\n" +
                            "§cVocê " + (p.isPermanent() ? "foi permanentemente" : "está") + " banido por " +
                            p.getType().toString() + "!\n" +
                            "§cData do banimento: " + p.getFormatedDate() +
                            (!p.isPermanent() ? "\n§cExpira em: " + p.getFormatedExpireDate() : "") +
                            "." + (prova != null ? " Prova: " + prova + "." : "")));

                } else if (type.getType() == PunishmentType.Type.MUTE) {
                    Message.ERROR.send(beingPunished,
                            new ComponentBuilder(
                                    "Você foi mutado " + (p.isPermanent() ? "permanentemente " : "") +
                                            "por " + type.toString() + "!" + (!p.isPermanent() ? " Expira em: " +
                                            p.getFormatedExpireDate() : "") + ". Prova: ").color(ChatColor.RED).create()[0]
                            , provaClick);

                }
            }
        }
        return punished;
    }
}
