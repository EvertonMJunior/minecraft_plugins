package com.goodcraft.comandos;

import com.goodcraft.api.Comando;
import com.goodcraft.api.Item;
import com.goodcraft.api.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class HeadCmd extends Comando{
    public HeadCmd(){
        super("head", "head");
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 1){
            Message.ERROR.send(p, "Use /head <jogador>");
            return false;
        }
        p.getInventory().addItem(Item.getHead(args[0], 1, "§fCabeça de §a§l" + args[0].toUpperCase()));
        
        return false;
    }
    

}
