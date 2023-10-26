package com.goodcraft.bungee.comandos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Message;
import com.goodcraft.bungee.api.Rank;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.Map;

public class OnlinePlayersCmd extends Command {

    public OnlinePlayersCmd() {
        super("online");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            Message.INFO.send(sender, "Jogadores online: " + Main.getPlugin().getProxy().getPlayers().size() + "");
            return;
        }

        Message.INFO.send(sender, "No momento, há " + Main.getPlugin().getProxy().getPlayers().size() + " jogadores onlines.");

        if (Rank.has(((ProxiedPlayer) sender).getUniqueId(), Rank.BUILDER)) {
            final Map<Rank, Integer> count = new HashMap<>();
            for (final Rank r : Rank.values()) {
                for (final ProxiedPlayer p : Main.getPlugin().getProxy().getPlayers()) {
                    Main.getPlugin().getProxy().getScheduler().runAsync(Main.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            if (Rank.getPlayerRank(p.getUniqueId()) != r) return;
                            if (count.containsKey(r)) {
                                count.replace(r, count.get(r) + 1);
                            } else {
                                count.put(r, 1);
                            }
                        }
                    });
                }
            }
            String message = "";
            int round = 0;
            for (Rank r : count.keySet()) {
                message += r.getColor() + "[" + (r != Rank.NORMAL ? r.getAliases().get(0) : "NORMAL") + "]§f: " +
                        count.get(r) + (round != count.size() - 1 ? ", §r" : "§r");
                round++;
            }
            Message.INFO.send(sender, message);
        }
    }
}
