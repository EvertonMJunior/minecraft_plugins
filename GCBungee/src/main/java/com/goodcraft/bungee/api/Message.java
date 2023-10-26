package com.goodcraft.bungee.api;

import com.goodcraft.bungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public enum Message {

    GOOD(ChatColor.GREEN),
    ERROR(ChatColor.RED),
    INFO(ChatColor.YELLOW);

    private final ChatColor attachedColor;

    private Message(ChatColor atacchedColor) {
        this.attachedColor = atacchedColor;
    }

    public void send(CommandSender whoToSend, String whatToSend) {
        if (whoToSend == null) {
            return;
        }
        String message = whatToSend.replace("<p>", "§f" + whoToSend.getName() + attachedColor);

        TextComponent msg = new TextComponent(message);
        msg.setColor(attachedColor);
        whoToSend.sendMessage(msg);
    }

    public void send(CommandSender whoToSend, BaseComponent... msg) {
        if (whoToSend == null) {
            return;
        }
        whoToSend.sendMessage(msg);
    }

    public String send(String whatToSend) {
        String message = attachedColor + whatToSend;

        return message;
    }

    public void broadcast(String whatToSend) {
        Utils.broadcast(attachedColor + whatToSend);
    }

    public void broadcast(BaseComponent[] whatToSend) {
        for (ProxiedPlayer p : Main.getPlugin().getProxy().getPlayers()) {
            p.sendMessage(whatToSend);
        }
    }

    public void broadcast(BaseComponent[] whatToSend, Rank rankToBroadcast) {
        if (rankToBroadcast == null) rankToBroadcast = Rank.NORMAL;

        for (ProxiedPlayer p : Main.getPlugin().getProxy().getPlayers()) {
            if (!Rank.has(p.getUniqueId(), rankToBroadcast)) continue;
            p.sendMessage(whatToSend);
        }
    }

    public void broadcast(String whatToSend, Rank rankToBroadcast) {
        if (rankToBroadcast == null) rankToBroadcast = Rank.NORMAL;
        Utils.broadcast(attachedColor + whatToSend, rankToBroadcast);
    }
}
