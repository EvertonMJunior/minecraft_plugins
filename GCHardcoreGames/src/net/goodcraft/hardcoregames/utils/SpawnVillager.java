package net.goodcraft.hardcoregames.utils;

import net.goodcraft.Main;
import net.goodcraft.api.Hologram;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class SpawnVillager {
    public static void spawnVillager(String name, final Location loc) {
        final Villager v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        v.setMetadata(ChatColor.stripColor(name), new FixedMetadataValue(Main.getPlugin(), ChatColor.stripColor(name)));
        Hologram.hologram(name, loc);
        UUID movementSpeedUID = UUID.randomUUID();
        EntityInsentient nmsEntity = (EntityInsentient) ((CraftLivingEntity) v).getHandle();
        AttributeInstance attributes = nmsEntity.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
        AttributeModifier modifier = new AttributeModifier(movementSpeedUID, "Villager", -100D, 1);
        attributes.b(modifier);
        attributes.a(modifier);
    }
}
