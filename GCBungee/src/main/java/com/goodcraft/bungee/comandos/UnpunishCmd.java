package com.goodcraft.bungee.comandos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Message;
import com.goodcraft.bungee.api.NameFetcher;
import com.goodcraft.bungee.api.Rank;
import com.goodcraft.bungee.api.UUIDFetcher;
import com.goodcraft.bungee.punishments.PunishmentAPI;
import com.goodcraft.bungee.punishments.PunishmentType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UnpunishCmd extends Command {

    public UnpunishCmd() {
        super("despunir", "bungeecord.command.default", "unpunish");
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if (sender instanceof ProxiedPlayer && !Rank.check((ProxiedPlayer) sender, Rank.ADMIN)) {
            return;
        }

        if (args.length != 2) {
            Message.ERROR.send(sender, "Use /despunir <nome> <motivo do punimento a ser retirado>");
            return;
        }
        final PunishmentType tipo = PunishmentType.getByName(args[1]);
        if (tipo == null) {
            Message.ERROR.send(sender, "O motivo [" + args[1].toUpperCase() + "] não existe.");
            return;
        }

        Main.getPlugin().getProxy().getScheduler().runAsync(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, UUID> ids = new UUIDFetcher(Arrays.asList(args[0])).call();
                    Map<UUID, NameFetcher.Lookup<NameFetcher.NameHistory>> nomes = new NameFetcher(Arrays.asList(ids.values().
                            toArray(new UUID[]{}))).call();
                    Set<UUID> uuids = nomes.keySet();
                    String name = nomes.get(uuids.toArray(new UUID[uuids.size()])[0])
                            .getValue().getCurrentName();
                    boolean unpunished = PunishmentAPI.removePunishment(ids.get(name), tipo);
                    if (unpunished) {
                        Message.ERROR.broadcast(" ", Rank.MOD);
                        Message.ERROR.broadcast("[!] " + name + " foi despunido por " + sender.getName() + ".", Rank.MOD);
                        Message.ERROR.broadcast("[!] Motivo do punimento retirado: " + tipo.toString(), Rank.MOD);
                        Message.ERROR.broadcast(" ", Rank.MOD);
                    } else {
                        Message.ERROR.send(sender, "Este(a) jogador(a) não possui nenhum punimento deste tipo!");
                        Message.ERROR.send(sender, "Use /punimentos " + name + " para ver os punimentos dele(a).");
                    }
                } catch (Exception e) {
                    Message.ERROR.send(sender, "Ocorreu um erro!");
                }
            }
        });
    }
}
