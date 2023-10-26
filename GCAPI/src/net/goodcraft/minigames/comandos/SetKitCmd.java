package net.goodcraft.minigames.comandos;

import net.goodcraft.Main;
import net.goodcraft.api.Comando;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Utils;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.minigames.kits.KitCommand;
import net.goodcraft.minigames.kits.KitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class SetKitCmd extends Comando {

    public SetKitCmd() {
        super("setkit", Rank.MODPLUS);
        setGetPlayerByName(0);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 2) {
            Message.ERROR.send(sender, "Use /setkit <player> <kit>");
            return false;
        }
        Minigame mg = Main.minigame;

        Kit k = KitManager.getKitByString(Utils.getSentence(args, 1));
        if (k == null) {
            Message.ERROR.send(sender, "O kit " + args[1] + " não foi encontrado.");
            return false;
        }
        p.getInventory().clear();
        p.getInventory().setArmorContents(new ItemStack[]{});
        KitCommand.add(player, k);

        Message.INFO.send(sender, "Agora " + player.getName() + " está usando o kit " + k.toString() + ".");

        return false;
    }

}