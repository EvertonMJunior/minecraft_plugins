package net.goodcraft.minigames.comandos;

import net.goodcraft.Main;
import net.goodcraft.api.Comando;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Utils;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TempoCmd extends Comando {

    public TempoCmd() {
        super("tempo", Rank.MODPLUS);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            Message.ERROR.send(sender, "Use /tempo <tempo>");
            return false;
        }
        try {
            Minigame mg = Main.minigame;

            int time = Integer.valueOf(args[0]);

            if (mg.getGameState() == GameState.PREGAME) {
                mg.setTimeToStart(time);
            } else {
                mg.setGameTime(time);
            }
            Message.INFO.send(sender, "Tempo de jogo alterado para " + Utils.secondsToSentence(time) + ".");

        } catch (Exception e) {
            Message.ERROR.send(sender, "Use /tempo <tempo>");
        }

        return false;
    }

}
