package net.goodcraft.hardcoregames.utils;

import net.goodcraft.api.Message;
import net.minecraft.server.v1_8_R3.EntityFishingHook;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntitySnowball;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSnowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class CopyOfFishingHook extends EntityFishingHook {
    private EntitySnowball controller;
    public int a;
    public EntityHuman owner;
    public Entity hooked;
    public boolean lastControllerDead;
    public boolean isHooked;

    public CopyOfFishingHook(org.bukkit.World world, EntityHuman entityhuman) {
        super(((CraftWorld) world).getHandle(), entityhuman);
        owner = entityhuman;
    }

    protected void c() {
    }

    public void h() {
        if ((!lastControllerDead) && (controller.dead)) {
            Message.INFO.send(owner.getBukkitEntity(), "Gancho preso!");
            if ((hooked != null) && ((hooked instanceof Player))) {
                Message.INFO.send(hooked, "Um gancho de Grappler prendeu em você!");
            }
        }
        lastControllerDead = controller.dead;
        controller.world.getWorld().getEntities().stream().filter(entity -> ((entity instanceof LivingEntity))
                && (entity.getEntityId() != getBukkitEntity().getEntityId())
                && (entity.getEntityId() != owner.getBukkitEntity()
                .getEntityId())).filter(entity -> entity.getEntityId() != controller.getBukkitEntity()
                .getEntityId()).forEach(entity -> {
            if (entity.getLocation().distance(
                    controller.getBukkitEntity().getLocation()) >= 2.0D) {
                if ((entity instanceof Player)) {
                    ((Player) entity).getEyeLocation().distance(
                            controller.getBukkitEntity()
                                    .getLocation());
                }
            } else {
                controller.die();
                hooked = entity;
                isHooked = true;
                locX = entity.getLocation().getX();
                locY = entity.getLocation().getY();
                locZ = entity.getLocation().getZ();
                motX = 0.0D;
                motY = 0.04D;
                motZ = 0.0D;
            }
        });
        try {
            locX = hooked.getLocation().getX();
            locY = hooked.getLocation().getY();
            locZ = hooked.getLocation().getZ();
            motX = 0.0D;
            motY = 0.04D;
            motZ = 0.0D;
            isHooked = true;
        } catch (Exception e) {
            if (controller.dead) {
                isHooked = true;
            }
            locX = controller.locX;
            locY = controller.locY;
            locZ = controller.locZ;
        }
    }

    public void die() {
    }

    public void remove() {
        super.die();
    }

    @SuppressWarnings("deprecation")
    public void spawn(Location location) {
        Snowball sb = ((Snowball) owner.getBukkitEntity().launchProjectile(
                Snowball.class));
        controller = ((CraftSnowball) sb).getHandle();

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(
                controller.getId());
        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
        ((CraftWorld) location.getWorld()).getHandle().addEntity(this);
    }

    public boolean isHooked() {
        return isHooked;
    }

    public void setHookedEntity(Entity damaged) {
        hooked = damaged;
    }
}
