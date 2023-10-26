package com.goodcraft.comandos;

import com.goodcraft.api.Comando;
import com.goodcraft.api.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCmd extends Comando {

    public FlyCmd() {
        super("fly", "fly");
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 1) {
            Message.ERROR.send(p, "Use /fly [jogador]");
            return false;
        }
        Player toFly = p;
        if(args.length == 1){
            if(Bukkit.getPlayerExact(args[0]) != null){
                toFly = Bukkit.getPlayerExact(args[0]);
            } else {
                Message.ERROR.send(p, "Este jogador encontra-se offline.");
                return false;
            }
        }
        
        if (toFly.getAllowFlight()) {
            toFly.setFlying(false);
            toFly.setAllowFlight(false);
            Message.INFO.send(toFly, "Você não pode mais voar.");
            if(!toFly.getUniqueId().equals(p.getUniqueId())) Message.INFO.send(p, toFly.getName() + " não pode mais voar.");
            return false;
        }
        toFly.setAllowFlight(true);
        toFly.setFlying(true);
        Message.INFO.send(toFly, "Agora você pode voar.");
        if(!toFly.getUniqueId().equals(p.getUniqueId())) Message.INFO.send(p, toFly.getName() + " agora pode voar.");
        
        return false;
    }

}
