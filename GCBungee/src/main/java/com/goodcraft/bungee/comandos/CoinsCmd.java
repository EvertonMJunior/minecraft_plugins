package com.goodcraft.bungee.comandos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.Collections;
import java.util.UUID;

public class CoinsCmd extends Command implements Listener {

    public CoinsCmd() {
        super("coins", "bungeecord.command.default", "coin");
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if (sender instanceof ProxiedPlayer && !Rank.check((ProxiedPlayer) sender, Rank.ADMIN)) {
            return;
        }
        final String usage = "Use /coins <add/remove/view> <nick/uuid> [quantidade]";

        if (args.length < 2) {
            Message.ERROR.send(sender, usage);
            return;
        }
        if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove") &&
                !args[0].equalsIgnoreCase("view")) {
            Message.ERROR.send(sender, usage);
            return;
        }

        Main.getPlugin().getProxy().getScheduler().runAsync(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    UUID id = null;
                    String nick;
                    if (args[1].length() > 16) {
                        id = UUID.fromString(args[1]);
                    } else {
                        try {
                            id = new UUIDFetcher(Collections.singletonList(args[1])).call().get(args[1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    nick = new NameFetcher(Collections.singletonList(id)).call().get(id).getValue().getCurrentName();

                    if (!SQLStatus.exists(id)) {
                        Message.ERROR.send(sender, "Este jogador nunca entrou no servidor!");
                        return;
                    }

                    if (args[0].equalsIgnoreCase("view")) {
                        int value = SQLStatus.getCoins(id);
                        Message.INFO.send(sender, "O jogador " + nick + " tem " + value + " GoodCoin" +
                                (value > 1 ? "s" : "") + ".");
                        return;
                    }

                    if (args.length != 3) {
                        Message.ERROR.send(sender, usage);
                        return;
                    }

                    int value = Integer.valueOf(args[2]);
                    String s = (value > 1 ? "s" : "");

                    if (args[0].equalsIgnoreCase("add")) {
                        SQLStatus.addCoins(id, value);
                        Message.INFO.send(sender, "Você adicionou " + args[2] + " GoodCoin" + s + " à conta de " + nick);
                        return;
                    }
                    SQLStatus.removeCoins(id, value);
                    Message.INFO.send(sender, "Você removeu " + args[2] + " GoodCoin" + s + " da conta de " + nick);
                    return;

                } catch (NumberFormatException e) {
                    Message.ERROR.send(sender, "Use números!");
                } catch (Exception e) {
                    Message.ERROR.send(sender, "Este jogador não existe!");
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTab(TabCompleteEvent e) {
        if (!e.getSuggestions().isEmpty()) {
            return;
        }

        if(!e.getCursor().startsWith("/coin")) return;
        String[] args = e.getCursor().split(" ");

        final String checked = (args.length > 0 ? args[args.length - 1] : e.getCursor()).toLowerCase();

        if(args.length == 2){
            for(Action a : Action.values()){
                if(a.name().toLowerCase().startsWith(checked)){
                    e.getSuggestions().add(a.name());
                }
            }
            return;
        }

        if(args.length == 3){
            for (ProxiedPlayer player : Main.getPlugin().getProxy().getPlayers()) {
                if (player.getName().toLowerCase().startsWith(checked)) {
                    e.getSuggestions().add(player.getName());
                }
            }
        }
    }

    public enum Action{
        ADD,
        REMOVE,
        VIEW;
    }

}
