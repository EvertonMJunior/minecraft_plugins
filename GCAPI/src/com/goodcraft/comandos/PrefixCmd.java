package com.goodcraft.comandos;

import com.goodcraft.api.Comando;
import com.goodcraft.api.Message;
import com.goodcraft.api.Tag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class PrefixCmd extends Comando{
    
    public PrefixCmd(){
        super("setprefix", "setprefix");
        setGetPlayerByName(0);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            Message.ERROR.send(sender, "Use /setprefix <jogador> <prefixo>");
            return false;
        }
        new Tag(player, args[1].replace("&", "§"));
        Message.INFO.send(sender, "Prefixo setado.");
        return false;
    }

}
