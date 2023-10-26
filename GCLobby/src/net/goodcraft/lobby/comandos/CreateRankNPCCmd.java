package net.goodcraft.lobby.comandos;

import net.goodcraft.GameMode;
import net.goodcraft.api.Comando;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.lobby.npcs.RankNPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class CreateRankNPCCmd extends Comando {

    public CreateRankNPCCmd() {
        super("createranknpc", Rank.DEVELOPER);
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            Message.ERROR.send(p, "Use /createranknpc <minigame> <top: 1/2/3>");
            return false;
        }
        GameMode gm = null;
        try {
            gm = GameMode.valueOf(args[0].toUpperCase());
            if (gm.getStatus() == null) {
                Message.ERROR.send(p, "Use /createranknpc <minigame> <top: 1/2/3>");
                return false;
            }
            int top = Integer.valueOf(args[1]);
            new RankNPC(top, gm, p.getLocation()).spawn();
        } catch (Exception e) {
            e.printStackTrace();
            Message.ERROR.send(p, "Use /createranknpc <minigame> <top: 1/2/3>");
            return false;
        }
        return false;
    }

}
