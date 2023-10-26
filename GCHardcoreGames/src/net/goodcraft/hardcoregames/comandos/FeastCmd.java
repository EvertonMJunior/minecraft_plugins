package net.goodcraft.hardcoregames.comandos;

import net.goodcraft.hardcoregames.game.Feast;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.api.Comando;
import net.goodcraft.api.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class FeastCmd extends Comando {

    public FeastCmd() {
        super("feast");
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (Main.estado == GameState.PREGAME) {
            Message.ERROR.send(p, "O jogo não iniciou!");
            return false;
        }

        if (Feast.feastLoc != null) {
            p.setCompassTarget(Feast.feastLoc);
            Message.INFO.send(p, "Bússola apontando para o Feast!");
            return false;
        }
        Message.ERROR.send(p, "O Feast ainda não saiu!");

        return false;
    }

}
