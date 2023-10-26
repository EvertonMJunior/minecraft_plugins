package com.goodcraft.bungee.comandos;

import com.goodcraft.bungee.api.Message;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.Arrays;

public class HelpCmd implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(ChatEvent e) {
        if (!e.getMessage().startsWith("/")) {
            return;
        }
        ArrayList<String> deniedPrefixes = (ArrayList<String>) Arrays.asList("pl", "help", "?", "ver");
        boolean denied = false;
        for (String pre : deniedPrefixes) {
            if (e.getMessage().startsWith("/" + pre)) {
                denied = true;
            }
        }
        if (!denied) return;
        e.setCancelled(true);

        if (!(e.getSender() instanceof ProxiedPlayer)) return;

        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        Message.INFO.send(p, "O servidor foi desenvolvido pela equipe de programação GoodCraft.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabComplete(TabCompleteEvent e) {
        if (!e.getCursor().startsWith("/")) {
            return;
        }
        ArrayList<String> deniedPrefixes = (ArrayList<String>) Arrays.asList("pl", "help", "?", "ver");
        boolean denied = false;
        for (String pre : deniedPrefixes) {
            if (e.getCursor().startsWith("/" + pre)) {
                denied = true;
            }
        }
        if (!denied) return;
        e.setCancelled(true);
    }

}
