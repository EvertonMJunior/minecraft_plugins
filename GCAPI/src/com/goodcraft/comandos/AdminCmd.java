package com.goodcraft.comandos;

import com.goodcraft.api.AdminAPI;
import com.goodcraft.api.Comando;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AdminCmd extends Comando {

    public AdminCmd() {
        super("admin", "admin");
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
