package com.goodcraft.lobby.eventos;

import com.goodcraft.api.Item;
import com.goodcraft.api.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LobbySelectorEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();

        if (!e.getAction().name().contains("RIGHT")) {
            return;
        }

        if (i.getType() == Material.AIR) {
            return;
        }

        if (!i.getType().equals(Material.SLIME_BALL)) {
            return;
        }

        if (!i.getItemMeta().getDisplayName().contains("Lobbys")) {
            return;
        }

        Inventory inv = Bukkit.createInventory(p, 27, "Lobbys");

        inv.setItem(11, Item.addGlow(Item.item(Material.SLIME_BALL, 1, "§e§lLobby 1", new String[]{
            "§aHá " + Bukkit.getOnlinePlayers().size() + " pessoas nesse lobby."
        })));
        inv.setItem(13, Item.item(Material.SLIME_BALL, 1, "§eLobby 2", new String[]{
            "§aHá " + Bukkit.getOnlinePlayers().size() + " pessoas nesse lobby."
        }));
        inv.setItem(15, Item.item(Material.SLIME_BALL, 1, "§eLobby 3", new String[]{
            "§aHá " + Bukkit.getOnlinePlayers().size() + " pessoas nesse lobby."
        }));

        p.openInventory(inv);

        e.setCancelled(true);
    }
    
    @EventHandler
    public void onInvClickRedesSociais(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (inv == null || !inv.getName().contains("Lobbys")) {
            return;
        }
        e.setCancelled(true);
        
        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }
        
        if(i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("§l")){
            p.closeInventory();
            Message.ERROR.send(p, "Você já está neste Lobby!");
            return;
        }
    }
}
