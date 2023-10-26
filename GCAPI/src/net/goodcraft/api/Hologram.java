package net.goodcraft.api;

import net.goodcraft.Main;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class Hologram {
    public static ArmorStand hologram(String messageToBeShowed, Location whereToBeCreated) {
        ArmorStand hologram = whereToBeCreated.getWorld().spawn(whereToBeCreated, ArmorStand.class);
        hologram.setVisible(false);
        hologram.setCustomName(messageToBeShowed.replace("&", "§"));
        hologram.setCustomNameVisible(true);
        hologram.setGravity(false);
        hologram.setMetadata("Hologram", new FixedMetadataValue(Main.getPlugin(), "Hologram"));
        hologram.setRemoveWhenFarAway(false);
        return hologram;
    }

    public static ArmorStand floatingItem(ItemStack itemToFloat, Location whereToFloat) {
        Item floatingItem = whereToFloat.getWorld().dropItem(whereToFloat, itemToFloat);
        floatingItem.setCustomName("floatingItem");
        floatingItem.setCustomNameVisible(false);
        floatingItem.setPickupDelay(2_147_483_647);
        floatingItem.setMetadata("FloatingItem", new FixedMetadataValue(Main.getPlugin(), "FloatingItem"));

        ArmorStand base = whereToFloat.getWorld().spawn(whereToFloat, ArmorStand.class);
        base.setVisible(false);
        base.setGravity(false);
        base.setPassenger(floatingItem);
        base.setRemoveWhenFarAway(false);
        base.setMetadata("Hologram", new FixedMetadataValue(Main.getPlugin(), "Hologram"));
        if (floatingItem.getItemStack().getItemMeta().hasDisplayName()) {
            base.setCustomName(floatingItem.getItemStack().getItemMeta().getDisplayName());
            base.setCustomNameVisible(true);
        }

        return base;
    }
}
