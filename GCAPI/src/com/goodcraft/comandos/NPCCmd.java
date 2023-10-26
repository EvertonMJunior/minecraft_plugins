package com.goodcraft.comandos;

import com.goodcraft.api.Comando;
import com.goodcraft.api.Message;
import com.goodcraft.api.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class NPCCmd extends Comando{

    public NPCCmd() {
        super("npc", "npc");
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 1){
            Message.ERROR.send(p, "Use /npc <nome>");
            return false;
        }
        NPC npc = new NPC(args[0], p.getLocation());
        
        return false;
    }

}
