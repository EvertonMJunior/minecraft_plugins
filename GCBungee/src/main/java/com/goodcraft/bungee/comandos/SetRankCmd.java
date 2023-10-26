package com.goodcraft.bungee.comandos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Message;
import com.goodcraft.bungee.api.MySQL;
import com.goodcraft.bungee.api.Rank;
import com.goodcraft.bungee.api.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collections;
import java.util.UUID;

public class SetRankCmd extends Command {


    public SetRankCmd() {
        super("setrank", "bungeecord.command.default");
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        boolean isPlayer = sender instanceof ProxiedPlayer;
        ProxiedPlayer p = !isPlayer ? null : (ProxiedPlayer) sender;
        if (isPlayer && !Rank.check((ProxiedPlayer) sender, Rank.ADMIN)) {
            return;
        }
        if (args.length != 2) {
            Message.ERROR.send(sender, "Use /setrank <nome/uuid> <rank>");
            return;
        }
        final Rank r = Rank.getByName(args[1]);

        if(r == null){
            Message.ERROR.send(sender, "O rank ["+args[1]+"] não existe!");
            return;
        }

        if(isPlayer && !Rank.has(p.getUniqueId(), r)){
            Message.ERROR.send(sender, "Você só pode setar ranks abaixo ou iguais ao seu!");
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

                    MySQL.updateRank(id, r);
                } catch (Exception e) {
                    Message.ERROR.send(sender, "Use /setrank <nome/uuid> <rank>");
                }
            }
        });
    }
}
