package me.everton.pvp.kits.habilidades;

import net.minecraft.server.v1_7_R4.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftSnowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class CopyOfFishingHook extends EntityFishingHook {
	private Snowball sb;
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
			((Player) owner.getBukkitEntity()).sendMessage(ChatColor.GREEN
					+ "Gancho preso!");
			if ((hooked != null) && ((hooked instanceof Player))) {
				((Player) hooked).sendMessage(ChatColor.RED
						+ "Um gancho de grappler prendeu em Você!");
			}
		}
		lastControllerDead = controller.dead;
		for (Entity entity : controller.world.getWorld().getEntities()) {
			if (((entity instanceof LivingEntity))
					&& (entity.getEntityId() != getBukkitEntity().getEntityId())
					&& (entity.getEntityId() != owner.getBukkitEntity()
							.getEntityId())) {
				if (entity.getEntityId() != controller.getBukkitEntity()
						.getEntityId()) {
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
				}
			}
		}
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
		sb = ((Snowball) owner.getBukkitEntity().launchProjectile(
				Snowball.class));
		controller = ((CraftSnowball) sb).getHandle();

		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(
				new int[] { controller.getId() });
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
