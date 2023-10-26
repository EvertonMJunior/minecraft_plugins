package net.goodcraft.hardcoregames.comandos;

import net.goodcraft.api.Comando;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Title;
import net.goodcraft.api.Utils;
import net.goodcraft.hardcoregames.game.Feast;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ForceFeastCmd extends Comando {

    public ForceFeastCmd() {
        super("forcefeast", Rank.MODPLUS, new String[]{"ffeast"});
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Location loc = p.getLocation().clone();
        if(Feast.feastLoc != null){
            Feast.spawnFeast(loc);
        }
        Feast.spawnChests(loc.clone().add(0, 1, 0));

        Title.INFO.broadcast("O feast spawnou!", Utils.getXYZ(Feast.feastLoc));
        return false;
    }

}
