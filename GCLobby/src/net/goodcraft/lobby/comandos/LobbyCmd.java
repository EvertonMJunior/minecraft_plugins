package net.goodcraft.lobby.comandos;

import net.goodcraft.GameMode;
import net.goodcraft.api.Comando;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class LobbyCmd extends Comando {

    public LobbyCmd() {
        super("gotospawn");
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        p.teleport(GameMode.LOBBY.getLocation());
        return false;
    }
}
