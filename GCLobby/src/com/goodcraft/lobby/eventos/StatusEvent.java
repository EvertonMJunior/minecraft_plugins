package com.goodcraft.lobby.eventos;

import com.goodcraft.GameMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StatusEvent implements Listener {

    public static HashMap<UUID, Inventory> invs = new HashMap<>();

    public static void saveStatusInv(Player p) {
        if (invs.containsKey(p.getUniqueId())) {
            return;
        }
        Inventory inv = Bukkit.createInventory(p, 18, "Status de " + p.getName());

        Integer[] slots = new Integer[]{0, 1, 3, 4, 5, 7, 8, 9, 11, 12, 14, 15, 17};
        int value = 0;

        for (GameMode gm : GameMode.values()) {
            if (gm == GameMode.LOBBY) {
                continue;
            }
            if (gm.getStatus() == null) {
                continue;
            }
            if (value >= slots.length) {
                break;
            }

            ItemStack item = gm.getItem().clone();
            ItemMeta im = item.getItemMeta();
            im.setDisplayName("§f§l" + gm.getName());

            ArrayList<String> lore = new ArrayList<>();
            if (gm.isActive()) {
                for (String s : gm.getStatus()) {
                    lore.add("§7" + s + ": §81");
                }
            } else {
                lore.add("§6§lEM BREVE");
            }

            im.setLore(lore);
            item.setItemMeta(im);

            inv.setItem(slots[value], item);
            value++;
        }
        invs.put(p.getUniqueId(), inv);
    }

    public static void openInv(Player p) {
        Inventory inv = invs.get(p.getUniqueId());

        Integer[] slots = new Integer[]{0, 1, 3, 4, 5, 7, 8, 9, 11, 12, 14, 15, 17};
        int value = 0;

        loop1:
        for (ItemStack i : inv.getContents()) {
            if(i == null || i.getType() == Material.AIR){
                continue;
            }
            ItemMeta im = i.getItemMeta();

            for (GameMode gm : GameMode.values()) {
                if (!gm.isActive()) {
                    continue;
                }

                if (!im.getDisplayName().contains(gm.getName())) {
                    continue;
                }

                ArrayList<String> lore = new ArrayList<>();
                for (String s : gm.getStatus()) {
                    lore.add("§7" + s + ": §81");
                }
                im.setLore(lore);
                continue loop1;
            }
        }
        p.openInventory(inv);
    }

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

        if (!i.getType().equals(Material.SKULL_ITEM)) {
            return;
        }

        if (!i.getItemMeta().getDisplayName().contains("Status")) {
            return;
        }

        openInv(p);

        e.setCancelled(true);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e
    ) {
        Inventory inv = e.getInventory();

        if (inv == null || !inv.getName().contains("Status")) {
            return;
        }
        e.setCancelled(true);
    }

}
