package net.goodcraft.comandos;

import net.goodcraft.api.Comando;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.api.SpecialPlayerInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class InvSeeCmd extends Comando {

    public InvSeeCmd() {
        super("invsee", Rank.MOD, new String[]{"viewinv", "openinv", "abririnv", "verinv"});
        setInGameOnly(true);
        setGetPlayerByName(0);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            Message.ERROR.send(p, "Use /" + label + " <jogador>");
            return false;
        }
        SpecialPlayerInventory sPl = new SpecialPlayerInventory(player, true);
        p.openInventory(sPl.getBukkitInventory());

        return false;
    }
}
