package com.goodcraft.comandos;

import com.goodcraft.api.Comando;
import com.goodcraft.api.Message;
import com.goodcraft.api.Title;
import com.goodcraft.api.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TitleCmd extends Comando {

    public TitleCmd() {
        super("title", "title", new String[]{"titlebc"});
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("titlebc")) {
            if(!(args.length >= 1)){
                Message.ERROR.send(sender, "Use /titlebc [mensagem]");
                return false;
            }
            Title.INFO.broadcast("Atenção!", Utils.getSentence(args));
            return false;
        }

        if (args.length <= 2) {
            Message.ERROR.send(sender, "Use /title [jogador] [GOOD/ERROR/INFO] [mensagem]");
            return false;
        }

        Player whoToSend = Bukkit.getPlayer(args[0]);
        if (whoToSend == null) {
            Message.ERROR.send(sender, "Este jogador encontra-se offline.");
            return false;
        }

        try {
            Title.valueOf(args[1].toUpperCase()).send(whoToSend, "Atenção!", Utils.getSentence(args, 2));
        } catch (Exception e) {
            Message.ERROR.send(sender, "Use /title [jogador] [GOOD/ERROR/INFO] [mensagem]");
        }

        return false;
    }

}
