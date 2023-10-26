package me.everton.jpvp.utils;

import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_8_R1.*;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.*;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Hologram {
	public static List<Integer> showLine(Location loc, String text) {
	      WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
	      EntityWitherSkull skull = new EntityWitherSkull(world);
	      skull.setLocation(loc.getX(), loc.getY() + 1 + 55, loc.getZ(), 0, 0);
	      PacketPlayOutSpawnEntity skull_packet = new PacketPlayOutSpawnEntity(skull, 972);
	 
	      EntityHorse horse = new EntityHorse(world);
	      horse.setLocation(loc.getX(), loc.getY() + 55, loc.getZ(), 0, 0);
	      horse.setAge(-1700000);
	      horse.setCustomName(text);
	      horse.setCustomNameVisible(true);
	      PacketPlayOutSpawnEntityLiving packedt = new PacketPlayOutSpawnEntityLiving(horse);
	      for (Player player : loc.getWorld().getPlayers()) {
	         EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
	         nmsPlayer.playerConnection.sendPacket(packedt);
	         nmsPlayer.playerConnection.sendPacket(skull_packet);
	 
	         PacketPlayOutAttachEntity pa = new PacketPlayOutAttachEntity(0, horse, skull);
	         nmsPlayer.playerConnection.sendPacket(pa);
	      }
	      return Arrays.asList(skull.getId(), horse.getId());
	   }
}
