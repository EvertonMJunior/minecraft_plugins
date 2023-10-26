package com.goodcraft.comandos;

import com.goodcraft.api.Comando;
import com.goodcraft.api.Hologram;
import com.goodcraft.api.Message;
import com.goodcraft.api.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HologramCmd extends Comando {

    public HologramCmd() {
        super("hologram", "hologram", new String[]{"floatingitem", "removehologram"});
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("floatingitem")) {
            try {
                Hologram.floatingItem(p.getItemInHand(), p.getLocation().clone().subtract(0, 1, 0));
                Message.GOOD.send(p, "Item flutuante criado!");
            } catch (Exception e) {
                Message.ERROR.send(p, "Você precisa estar com um item na mão!");
            }

            return false;
        }

        if (label.equalsIgnoreCase("removehologram")) {

            return false;
        }

        if (args.length < 1) {
            Message.ERROR.send(p, "Use /hologram [texto, /n = nova linha]");
            return false;
        }

        Location hologram = p.getLocation().clone();
        String[] texts = Utils.getSentence(args).split("/n");

        for (String s : texts) {
            Hologram.hologram(s.replace("&", "§"), hologram.subtract(0, 0.25, 0));
        }
        Message.GOOD.send(p, "Holograma criado!");

        return false;
    }

}
