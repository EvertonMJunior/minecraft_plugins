package com.goodcraft.eventos;

import com.goodcraft.api.AdminAPI;
import com.goodcraft.api.TestHack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AdminEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!AdminAPI.admins.containsKey(p.getName())) {
            return;
        }
        AdminAPI admin = AdminAPI.admins.get(p.getName());

        if (p.getItemInHand().getType() == Material.AIR || p.getItemInHand().getType() == null) {
            return;
        }

        if (p.getItemInHand().getType() == Material.MAGMA_CREAM) {
            admin.fastChange();
            return;
        }
    }

    @EventHandler
    public void onClickPlayer(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();

        if (!AdminAPI.admins.containsKey(p.getName())) {
            return;
        }
        AdminAPI admin = AdminAPI.admins.get(p.getName());

        if (!(e.getRightClicked() instanceof Player)) {
            return;
        }
        Player r = (Player) e.getRightClicked();

        if (p.getItemInHand().getType() == Material.AIR || p.getItemInHand().getType() == null) {
            p.openInventory(r.getInventory());
            return;
        }

        if (p.getItemInHand().getType() == Material.ARROW) {
            admin.testHacks(r);
            return;
        }

        if (p.getItemInHand().getType() == Material.BLAZE_ROD) {
            admin.showInfo(r);
        }
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onInfoInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (!AdminAPI.admins.containsKey(p.getName())) {
            return;
        }
        AdminAPI admin = AdminAPI.admins.get(p.getName());

        if (inv == null || !inv.getName().equalsIgnoreCase("Informações do Jogador")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }

        if (i.getType() == Material.CHEST) {
            Player toOpen = Bukkit.getPlayer(admin.playerInfo);
            if (toOpen != null) {
                p.openInventory(toOpen.getInventory());
                return;
            }
            p.closeInventory();
        }
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onHackInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (!AdminAPI.admins.containsKey(p.getName())) {
            return;
        }
        AdminAPI admin = AdminAPI.admins.get(p.getName());

        if (inv == null || !inv.getName().equalsIgnoreCase("Verificar Hacks")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }
        String hack = ChatColor.stripColor(i.getItemMeta().getDisplayName());
        Player toTest = Bukkit.getPlayer(admin.playerBeingTested);
        
        if (toTest == null) {
            p.closeInventory();
            return;
        }

        if (hack.equalsIgnoreCase("NoFall")) {
            new TestHack(p, toTest, TestHack.Hack.NOFALL).test();
        }

        p.closeInventory();
    }
}
