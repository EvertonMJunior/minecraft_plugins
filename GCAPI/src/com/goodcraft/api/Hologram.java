package com.goodcraft.api;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class Hologram {
    public static ArmorStand hologram(String messageToBeShowed, Location whereToBeCreated){
        ArmorStand hologram = whereToBeCreated.getWorld().spawn(whereToBeCreated, ArmorStand.class);
        hologram.setVisible(false);
        hologram.setCustomName(messageToBeShowed.replace("&", "§"));
        hologram.setCustomNameVisible(true);
        hologram.setGravity(false);
        return hologram;
    }
    
    public static ArmorStand floatingItem(ItemStack itemToFloat, Location whereToFloat){
        Item floatingItem = whereToFloat.getWorld().dropItem(whereToFloat, itemToFloat);
        floatingItem.setCustomName("floatingItem");
        floatingItem.setCustomNameVisible(false);
        floatingItem.setPickupDelay(2_147_483_647);
        
        ArmorStand base = whereToFloat.getWorld().spawn(whereToFloat, ArmorStand.class);
        base.setVisible(false);
        base.setGravity(false);
        base.setPassenger(floatingItem);
        if(floatingItem.getItemStack().getItemMeta().hasDisplayName()){
            base.setCustomName(floatingItem.getItemStack().getItemMeta().getDisplayName());
            base.setCustomNameVisible(true);
        }
        
        return base;
    }
}
