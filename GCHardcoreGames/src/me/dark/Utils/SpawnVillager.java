package me.dark.Utils;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.PlayerWatcher;
import net.minecraft.server.v1_7_R4.AttributeInstance;
import net.minecraft.server.v1_7_R4.AttributeModifier;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.GenericAttributes;

public class SpawnVillager {
	public static void spawnVillager(String name, final Location loc, VillagerType tipo) {
		final Villager v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		v.setCustomName(name);
		v.setCustomNameVisible(true);
		UUID movementSpeedUID = UUID.randomUUID();
        EntityInsentient nmsEntity = (EntityInsentient) ((CraftLivingEntity) v).getHandle();
        AttributeInstance attributes = nmsEntity.getAttributeInstance(GenericAttributes.d);
        AttributeModifier modifier = new AttributeModifier(movementSpeedUID, "Villger", -100D, 1);
        attributes.b(modifier);
        attributes.a(modifier);
        
        Disguise d = new PlayerDisguise(name);
    	PlayerWatcher w = (PlayerWatcher) d.getWatcher();
    	
        if (tipo==VillagerType.LAVA) {
        	w.setItemInHand(new ItemStack(Material.MUSHROOM_SOUP));
        	w.setBurning(true);
         	w.setSkin("DarknessPlayss");
        } else if (tipo==VillagerType.SHOP) {
        	w.setItemInHand(new ItemStack(Material.CHEST));
        	w.setSkin("MGamerBrazil");
        }else if (tipo==VillagerType.HOLOGRAM) {
        	
        }
        
        DisguiseAPI.disguiseToAll(v, d);
		new BukkitRunnable() {
			public void run() {
				if (v.isDead()) {
					cancel();
					return;
				}
				if (!DisguiseAPI.isDisguised(v)) {
					
				}
				if (v.getLocation() != loc) {
					v.teleport(loc);
				}
				
			}
		}.runTaskTimer(Main.getMain(), 60, 20);
	}
}
