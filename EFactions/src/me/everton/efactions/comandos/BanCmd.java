package me.everton.efactions.comandos;

import me.everton.eapi.Comando;
import me.everton.eapi.Message;
import me.everton.eapi.SQLUtils;
import me.everton.eapi.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCmd extends Comando {

    public BanCmd() {
        super("ban", "efactions.ban");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.testPermission(sender)) {
            return false;
        }
        if(args.length >= 3){
            String motivo = Utils.getSentence(args, 2);
            
            Message.INFO.send(sender, SQLUtils.getFirstJoin(((Player) sender).getUniqueId()));
            
            return true;
        }
        Message.ERROR.send(sender, "Use /ban [jogador] [tempo em minutos, 0=permanente] [motivo]");
        
        return false;
    }
}
