package com.goodcraft.lobby.comandos;

import com.goodcraft.GameMode;
import com.goodcraft.api.Comando;
import com.goodcraft.api.Message;
import com.goodcraft.lobby.npcs.RankNPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class CreateRankNPCCmd extends Comando{

    public CreateRankNPCCmd() {
        super("createranknpc", "createranknpc");
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
            int top = Integer.valueOf(args[1]);
            new RankNPC(top > 3 ? 3 : top, gm, p.getLocation()).spawn();
        } catch (Exception e) {
            e.printStackTrace();
            Message.ERROR.send(p, "Use /createranknpc <minigame> <top: 1/2/3>");
            return false;
        }
        return false;
    }

}
