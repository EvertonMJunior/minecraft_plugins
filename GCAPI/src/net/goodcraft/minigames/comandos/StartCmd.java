package net.goodcraft.minigames.comandos;

import net.goodcraft.Main;
import net.goodcraft.api.Comando;
import net.goodcraft.api.Rank;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class StartCmd extends Comando {

    public StartCmd() {
        super("start", Rank.MODPLUS);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Minigame mg = Main.minigame;

        if (mg.getGameState() == GameState.PREGAME) {
            mg.getGameManager().start();
        } else if(mg.getGameState() == GameState.INVENCIBILITY){
            mg.setGameTime(10);
        }
        return false;
    }

}