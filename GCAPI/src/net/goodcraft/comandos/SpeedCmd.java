package net.goodcraft.comandos;

import net.goodcraft.api.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCmd extends Comando {

    public SpeedCmd() {
        super("speed", Rank.MODPLUS, new String[]{"velocidade"});
        setInGameOnly(true);
        setGetPlayerByName(1);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1 && args.length != 2) {
            Message.ERROR.send(p, "Use /speed <velocidade> [jogador]");
            return false;
        }
        Player playerToSet = p;

        if (args.length == 2) {
            playerToSet = player;
        }
        try {
            int speed = Integer.parseInt(args[0]);
            if (playerToSet.isFlying()) {
                playerToSet.setFlySpeed((float) speed / 10.0F);
            } else {
                playerToSet.setWalkSpeed((float) (speed / 10.0F) + (speed != 10 ? 0.1F : 0F));
            }
            Message.INFO.send(playerToSet, "Agora sua velocidade é " + speed + "!");
            if (args.length == 2) {
                Message.INFO.send(p, "Agora a velocidade de " + playerToSet.getName() + " é " + speed + "!");
            }
        } catch (IllegalArgumentException e) {
            Message.ERROR.send(p, "Digite uma velocidade entre 1 e 10!");
        }
        return false;
    }
}
