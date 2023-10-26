package com.goodcraft.comandos;

import com.goodcraft.api.Comando;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class EnderChestCmd extends Comando implements Listener {

    public EnderChestCmd() {
        super("enderchest", "enderchest", new String[]{"echest", "ec"});
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player whoToOpen = p;
        
        Inventory inv = Bukkit.createInventory(p, 27, "Enderchest de " + whoToOpen.getName());
        inv.setContents(whoToOpen.getEnderChest().getContents());
        p.openInventory(inv);
        return false;
    }

    @EventHandler
    public void onOpen(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getInventory().getName().equalsIgnoreCase("Enderchest de " + p.getName())) {
            @SuppressWarnings("deprecation")
            OfflinePlayer whoToOpen = Bukkit.getOfflinePlayer(e.getInventory().getTitle().replace("Enderchest de ", ""));
            whoToOpen.getPlayer().getEnderChest().setContents(e.getInventory().getContents());
        }
    }
}
