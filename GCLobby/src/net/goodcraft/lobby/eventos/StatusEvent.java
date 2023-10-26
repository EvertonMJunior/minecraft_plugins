package net.goodcraft.lobby.eventos;

import net.goodcraft.GameMode;
import net.goodcraft.minigames.Stat;
import net.goodcraft.sql.SQLStatus;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class StatusEvent implements Listener {

    public static HashMap<UUID, Inventory> invs = new HashMap<>();

    public static void saveStatusInv(UUID id, String name) {
        if (invs.containsKey(id)) {
            return;
        }
        Inventory inv = Bukkit.createInventory(null, 18, "Status de " + name);

        Integer[] slots = new Integer[]{
                0, 1, 3, 4, 5, 7, 8,
                10, 11, 13, 15, 16
        };
        int value = 0;

        for (GameMode gm : GameMode.values()) {
            if (gm == GameMode.LOBBY) {
                continue;
            }
            if (gm.getStatus().length == 0) {
                continue;
            }
            if (value >= slots.length) {
                break;
            }

            ItemStack item = gm.getItem().clone();
            ItemMeta im = item.getItemMeta();
            im.setDisplayName("§f§l" + gm.getName());

            ArrayList<String> lore = new ArrayList<>();
            if (!gm.isActive()) {
                lore.add("§6§lEM BREVE");
            }

            im.setLore(lore);
            item.setItemMeta(im);

            inv.setItem(slots[value], item);
            value++;
        }
        invs.put(id, inv);
    }

    public static void openInv(Player p) {
        Inventory inv = invs.get(p.getUniqueId());

        Integer[] slots = new Integer[]{0, 1, 3, 4, 5, 7, 8, 9, 11, 12, 14, 15, 17};
        int value = 0;

        loop1:
        for (ItemStack i : inv.getContents()) {
            if (i == null || i.getType() == Material.AIR) {
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
                for (Stat s : gm.getStatus()) {
                    SQLStatus.getStat(p.getUniqueId(), "good_" + gm.name().toLowerCase(), s.getName().toLowerCase(), new SQLStatus.Callback<Object>() {
                        @Override
                        public void onSucess(Object o) {
                            lore.add("§f" + s.getName() + ": §7" + o);
                            im.setLore(lore);
                            i.setItemMeta(im);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                        }
                    });
                }
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
