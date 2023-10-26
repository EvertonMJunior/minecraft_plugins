package net.goodcraft.comandos;

import net.goodcraft.api.AdminAPI;
import net.goodcraft.api.Comando;
import net.goodcraft.api.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AdminCmd extends Comando {

    public AdminCmd() {
        super("admin", Rank.MOD);
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (AdminAPI.admins.containsKey(p.getName())) {
            AdminAPI.admins.get(p.getName()).putOrRemove();
            return false;
        }
        new AdminAPI(p).putOrRemove();
        return false;
    }

}
