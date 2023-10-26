package net.goodcraft.minigames.comandos;

import net.goodcraft.Main;
import net.goodcraft.api.*;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.minigames.kits.KitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ToggleKitsCmd extends Comando {

    public ToggleKitsCmd() {
        super("togglekits", Rank.MODPLUS);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Minigame mg = Main.minigame;

        if (args.length == 0) {
            if (mg.areKitsEnabled()) {
                for (Kit k : Kit.kits) {
                    k.getPlayers().clear();
                }
                Title.INFO.broadcast(mg.getName(), "Os kits foram desativados!");
                mg.setKitsState(false);
                return false;
            }
            Title.INFO.broadcast(mg.getName(), "Os kits foram ativados!");
            mg.setKitsState(true);
            return false;
        }
        Kit kit = KitManager.getKitByString(Utils.getSentence(args));
        if (kit == null) {
            Message.ERROR.send(sender, "Este kit não existe!");
            return false;
        }
        if (!mg.getDisabledKits().contains(kit)) {
            mg.getDisabledKits().add(kit);
            kit.getPlayers().forEach(kit::removePlayer);
            Title.INFO.broadcast(""+ mg.getName() +"", "O kit " + kit.toString() + " foi desativado!");
            return false;
        }
        Title.INFO.broadcast(mg.getName(), "O kit " + kit.toString() + " foi ativado!");
        mg.getDisabledKits().remove(kit);
        return false;
    }
}