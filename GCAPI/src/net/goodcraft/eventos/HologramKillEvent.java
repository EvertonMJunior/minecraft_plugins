package net.goodcraft.eventos;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import java.util.ArrayList;

public class HologramKillEvent implements Listener {
    public static ArrayList<Item> allowedItems = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public synchronized void onKill(ItemDespawnEvent e) {
        Item i = e.getEntity();
        if (i.hasMetadata("FloatingItem")) {
            if (allowedItems.contains(i)) {
                e.setCancelled(false);
                allowedItems.remove(i);
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickArmorStand(PlayerArmorStandManipulateEvent e) {
        if (e.getRightClicked().hasMetadata("Hologram")) {
            e.setCancelled(true);
        }
    }
}
