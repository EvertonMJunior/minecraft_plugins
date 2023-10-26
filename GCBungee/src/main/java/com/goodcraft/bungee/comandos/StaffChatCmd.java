package com.goodcraft.bungee.comandos;

import com.goodcraft.bungee.api.Message;
import com.goodcraft.bungee.api.Rank;
import com.goodcraft.bungee.api.Utils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.UUID;

public class StaffChatCmd extends Command {
    public static ArrayList<UUID> inChat = new ArrayList<>();

    public StaffChatCmd() {
        super("sc", "bungeecord.command.default", "s", "ac");
    }

    public static void broadcast(ProxiedPlayer p, String msg) {
        Rank pRank = Rank.getPlayerRank(p.getUniqueId());
        String message = "§d§l[STAFF] §r" + p.getDisplayName() + ": " + msg;
        Utils.broadcast(message, Rank.BUILDER);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if (!Rank.check(p, Rank.BUILDER)) {
            return;
        }

        if (args.length == 0) {
            if (!inChat.contains(p.getUniqueId())) {
                inChat.add(p.getUniqueId());
                Message.GOOD.send(p, "Você entrou no chat da STAFF.");
            } else {
                inChat.remove(p.getUniqueId());
                Message.GOOD.send(p, "Você saiu do chat da STAFF.");
            }
            return;
        }
        broadcast(p, Utils.getSentence(args));
    }
}
