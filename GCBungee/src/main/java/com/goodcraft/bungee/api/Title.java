package com.goodcraft.bungee.api;

import com.goodcraft.bungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public enum Title {
    GOOD(ChatColor.GREEN),
    ERROR(ChatColor.RED),
    INFO(ChatColor.YELLOW);

    private final ChatColor titleColor;

    private Title(ChatColor titleColor) {
        this.titleColor = titleColor;
    }

    public void send(ProxiedPlayer whoToSend, String titleMessage, String subtitleMessage) {
        if (whoToSend == null || (titleMessage == null && subtitleMessage == null)) {
            return;
        }
        net.md_5.bungee.api.Title t = Main.getPlugin().getProxy().createTitle();
        if (titleMessage != null) {
            t.title(new TextComponent(titleColor + titleMessage));
        }
        if (subtitleMessage != null) {
            t.subTitle(new TextComponent("§f§l" + subtitleMessage));
        }
        t.fadeIn(10);
        t.stay(50);
        t.fadeOut(10);
        t.send(whoToSend);

    }

    public void broadcast(String titleMessage, String subtitleMessage) {
        for (ProxiedPlayer p : Main.getPlugin().getProxy().getPlayers()) {
            this.send(p, titleMessage, subtitleMessage);
        }
    }
}
