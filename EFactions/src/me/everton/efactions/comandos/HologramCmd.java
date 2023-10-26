package me.everton.efactions.comandos;

import me.everton.eapi.Comando;
import me.everton.eapi.Hologram;
import me.everton.eapi.Message;
import me.everton.eapi.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HologramCmd extends Comando {

    public HologramCmd() {
        super("hologram", "efactions.hologram");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!cmd.testPermission(sender)){
            return false;
        }
        
        if (!(sender instanceof Player)) {
            Message.ERROR.send(sender, "Isto nao pode ser executado no Console!");
            return true;
        }

        Player p = (Player) sender;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("item")) {
                ItemStack itemToFloat = p.getItemInHand();
                if (itemToFloat.getType() != Material.AIR) {
                    Hologram.floatingItem(itemToFloat, p.getLocation().clone().subtract(0, 1, 0));
                    Message.GOOD.send(p, "Item flutuante(" + itemToFloat.getType().toString() + ") criado!");
                    return true;
                }

            }

            String messageToBeShowed = Utils.getSentence(args);
            Hologram.hologram(messageToBeShowed, p.getLocation().clone().subtract(0, 1, 0));

            Message.GOOD.send(p, "Holograma criado!");

            return true;
        }

        return false;
    }

}
