package com.goodcraft.lobby.pets;

import com.dsh105.echopet.compat.api.event.PetInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PetDataMenuListener implements Listener {

    @EventHandler
    public void onInteract(PetInteractEvent e) {
        e.setCancelled(true);
        
        if (!e.getPet().getOwnerUUID().equals(e.getPlayer().getUniqueId())) {
            return;
        }
        new PetSettings(e.getPlayer()).open();
    }
}
