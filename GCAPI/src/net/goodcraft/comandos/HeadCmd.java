package net.goodcraft.comandos;

import net.goodcraft.api.Comando;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class HeadCmd extends Comando {
    public HeadCmd() {
        super("head", Rank.BUILDER);
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            Message.ERROR.send(p, "Use /head <jogador>");
            return false;
        }

        p.getInventory().addItem(Item.getHead(args[0], 1, "§fCabeça de §a§l" + args[0].toUpperCase()));

        return false;
    }


}
