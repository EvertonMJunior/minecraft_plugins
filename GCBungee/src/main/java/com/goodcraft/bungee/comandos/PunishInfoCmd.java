package com.goodcraft.bungee.comandos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Message;
import com.goodcraft.bungee.api.NameFetcher;
import com.goodcraft.bungee.api.Rank;
import com.goodcraft.bungee.api.UUIDFetcher;
import com.goodcraft.bungee.punishments.PunishmentAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PunishInfoCmd extends Command {

    public PunishInfoCmd() {
        super("punimentos", "bungeecord.command.default");
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if (sender instanceof ProxiedPlayer && !Rank.check((ProxiedPlayer) sender, Rank.MOD)) {
            return;
        }
        if (args.length != 1) {
            Message.ERROR.send(sender, "Use /punimentos <nome/uuid>");
            return;
        }

        Main.getPlugin().getProxy().getScheduler().runAsync(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    UUID id = null;
                    if (args[0].length() > 16) {
                        id = UUID.fromString(args[0]);
                    } else {
                        try {
                            id = new UUIDFetcher(Collections.singletonList(args[0])).call().get(args[0]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    List<PunishmentAPI.Punimento> punimentos = PunishmentAPI.getPunishments(id);
                    if (punimentos.isEmpty()) {
                        Message.ERROR.send(sender, "Este jogador não existe/não tem nenhum punimento.");
                        return;
                    }
                    int count = 1;
                    for (PunishmentAPI.Punimento pu : punimentos) {
                        Message.INFO.send(sender, " ");
                        Message.INFO.send(sender, "Punimento #" + count);
                        Message.INFO.send(sender, "Motivo: " + pu.getType().toString());
                        Message.INFO.send(sender, "Prova: " + pu.getProof());
                        Message.INFO.send(sender, "Autor: " + new NameFetcher(Collections.singletonList(pu.getAuthor())).call()
                                .get(pu.getAuthor()).getValue().getCurrentName());
                        Message.INFO.send(sender, "Expira em: " + (pu.isPermanent() ? "Permanente" : pu.getFormatedExpireDate()));
                        Message.INFO.send(sender, "Data: " + pu.getFormatedDate());
                        Message.INFO.send(sender, " ");
                        count++;
                    }
                } catch (Exception e) {
                    Message.ERROR.send(sender, "Use /punimentos <nome/uuid>");
                }
            }
        });
    }
}
