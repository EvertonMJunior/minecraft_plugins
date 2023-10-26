package com.goodcraft.bungee.comandos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Message;
import com.goodcraft.bungee.api.NameFetcher;
import com.goodcraft.bungee.api.Rank;
import com.goodcraft.bungee.api.UUIDFetcher;
import com.goodcraft.bungee.punishments.Punishment;
import com.goodcraft.bungee.punishments.PunishmentType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.*;

public class PunishCmd extends Command {
    public static ArrayList<UUID> inChat = new ArrayList<>();

    public PunishCmd() {
        super("punir", "bungeecord.command.default", "punish");
    }

    @Override
    public void execute(final CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer && !Rank.check((ProxiedPlayer) sender, Rank.MOD)) {
            return;
        }
        if (args.length != 3) {
            Message.ERROR.send(sender, "Use /punir <nomes> <motivos> <provas>");
            return;
        }
        final String[] names = args[0].split(",");
        String[] reasons = args[1].split(",");
        final String[] provas = args[2].split(",");

        final List<PunishmentType> types = new ArrayList<>();
        for (String s : reasons) {
            PunishmentType tipo = PunishmentType.getByName(s);
            if (tipo == null) {
                Message.ERROR.send(sender, "O motivo [" + s.toUpperCase() + "] não existe.");
                break;
            }
            types.add(tipo);
        }
        if (types.size() != names.length || names.length != provas.length || types.size() != provas.length) {
            Message.ERROR.send(sender, "Há um nick/motivo/prova a mais ou a menos!");
            return;
        }

        Main.getPlugin().getProxy().getScheduler().runAsync(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, UUID> ids = new UUIDFetcher(Arrays.asList(names)).call();
                    Map<UUID, NameFetcher.Lookup<NameFetcher.NameHistory>> nomes = new NameFetcher(Arrays.asList(ids.values().toArray(new UUID[]{}))).call();
                    for (int i = 0; i < names.length; i++) {
                        Set<UUID> uuids = nomes.keySet();
                        String name = nomes.get(uuids.toArray(new UUID[uuids.size()])[i])
                                .getValue().getCurrentName();
                        Punishment punimento = new Punishment(types.get(i), name, ids.get(name),
                                (sender instanceof ProxiedPlayer ? ((ProxiedPlayer)sender).getUniqueId() :
                                        UUID.fromString("f78a4d8d-d51b-4b39-98a3-230f2de0c670")), provas[i]);
                        punimento.punish();
                    }
                } catch (Exception e) {
                    Message.ERROR.send(sender, "Ocorreu um erro!");
                }
            }
        });
    }
}