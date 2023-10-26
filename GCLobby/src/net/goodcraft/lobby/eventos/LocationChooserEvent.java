package net.goodcraft.lobby.eventos;

import net.goodcraft.GameMode;
import net.goodcraft.api.Title;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LocationChooserEvent implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (inv == null || !inv.getName().equalsIgnoreCase("Setar localização")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }

        if (!i.getItemMeta().hasDisplayName()) {
            return;
        }

        for (GameMode tP : GameMode.values()) {
            if (!ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase(tP.getName())) {
                continue;
            }
            tP.setLocation(p.getLocation());

            p.closeInventory();
            Title.GOOD.send(p, tP.getName().toUpperCase(), "A localização foi setada!");
        }
    }
}
