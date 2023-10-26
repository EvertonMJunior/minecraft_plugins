package com.goodcraft.bungee.comandos;

import com.goodcraft.bungee.servers.Lobby;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyCmd extends Command {
    public LobbyCmd() {
        super("lobby", "bungeecord.command.default", "hub", "spawn");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer) sender;

        if (p.getServer().getInfo().getName().startsWith("lobby")) {
            p.chat("/gotospawn");
            return;
        }
        Lobby.connect((ProxiedPlayer) sender);
    }
}
