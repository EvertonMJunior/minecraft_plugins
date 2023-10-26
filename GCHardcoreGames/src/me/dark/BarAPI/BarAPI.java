package me.dark.BarAPI;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;

public class BarAPI implements Listener {
	
	private static Map<UUID, Integer> bars_ids = new HashMap<>();
	private static Map<UUID, String> bars_mem = new HashMap<>();
	private static Main plugin = Main.getMain();

	
	private static void sendPacket(Player player, Object packet) {
		try {
			Object getHandle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = getHandle.getClass().getField("playerConnection").get(getHandle);
			Method sendPacket = playerConnection.getClass().getMethod("sendPacket", Reflection.getNMSClass("Packet"));
			sendPacket.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Object getMobPacket(Player player, String text) {
		Class<?> Entity = Reflection.getNMSClass("Entity");
        Class<?> EntityLiving = Reflection.getNMSClass("EntityLiving");
        Class<?> EntityEnderDragon = Reflection.getNMSClass(getEntityType(player));
        Object packet = null;
        try {
        	Location loc = null;
        	if(getEntityType(player).equals("EntityWither")) {
        		loc = player.getLocation().add(player.getEyeLocation().getDirection().multiply(20));
        	} else {
        		loc = player.getLocation().add(0, -300, 0);
        	}
            Object dragon = EntityEnderDragon.getConstructor(Reflection.getNMSClass("World")).newInstance(Reflection.getHandle(loc.getWorld()));
            Method setLocation = Reflection.getMethod(EntityEnderDragon, "setLocation", new Class<?>[] { double.class, double.class, double.class, float.class, float.class });
            setLocation.invoke(dragon, loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());

            Method setInvisible = Reflection.getMethod(EntityEnderDragon, "setInvisible", new Class<?>[] { boolean.class });
            setInvisible.invoke(dragon, true);

            Method setCustomName = Reflection.getMethod(EntityEnderDragon, "setCustomName", new Class<?>[] { String.class });
            setCustomName.invoke(dragon, text);

            float health = getEntityType(player).equals("EntityWither") ? 300F : 200F;
            Method setHealth = Reflection.getMethod(EntityEnderDragon, "setHealth", new Class<?>[] { float.class });
            setHealth.invoke(dragon, health);

            Field motX = Reflection.getField(Entity, "motX");
            motX.set(dragon, (byte)0);

            Field motY = Reflection.getField(Entity, "motY");
            motY.set(dragon, (byte)0);

            Field motZ = Reflection.getField(Entity, "motZ");
            motZ.set(dragon, (byte)0);

            Method getId = Reflection.getMethod(EntityEnderDragon, "getId", new Class<?>[] {});
            bars_ids.put(player.getUniqueId(), (int)getId.invoke(dragon));

            Class<?> PacketPlayOutSpawnEntityLiving = Reflection.getNMSClass("PacketPlayOutSpawnEntityLiving");
            packet = PacketPlayOutSpawnEntityLiving.getConstructor(new Class<?>[] { EntityLiving }).newInstance(dragon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return packet;
	}
	
	private static Object getDestroyEntityPacket(Integer id) {
		Object packet = null;
		try {
			packet = Reflection.getNMSClass("PacketPlayOutEntityDestroy").newInstance();
			
			Field a = packet.getClass().getDeclaredField("a");
			a.setAccessible(true);
			a.set(packet, new int[] { id });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packet;
	}
	
	private static Object getMetadataPacket(Integer id, Object watcher) {
		Object packet = null;
        try {
    		packet = Reflection.getNMSClass("PacketPlayOutEntityMetadata").newInstance();
    		
    		Field a = packet.getClass().getDeclaredField("a");
            a.setAccessible(true);
			a.set(packet, id);
			
			Field b = packet.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.set(packet, watcher.getClass().getMethod("c").invoke(watcher));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return packet;
	}

	private static Object getWatcher(Player player, String name, Float health) {
		Class<?> DataWatcher = Reflection.getNMSClass("DataWatcher");
		try {
			Object dragon = null;
			Object watcher = DataWatcher.getConstructor(Reflection.getNMSClass("Entity")).newInstance(dragon);
			Method a = Reflection.getMethod(DataWatcher, "a", new Class<?>[] { int.class, Object.class });
			if(getEntityType(player).equals("EntityWither")) {
				a.invoke(watcher, 0, (byte) 0x20);
				a.invoke(watcher, 2, name);
				a.invoke(watcher, 6, health);
				a.invoke(watcher, 10, name);
				a.invoke(watcher, 20, 881);
			} else {
				a.invoke(watcher, 0, (byte) 0x20);
				a.invoke(watcher, 6, (Float) health);
				a.invoke(watcher, 7, (Integer) 0);
				a.invoke(watcher, 8, (Byte) (byte) 0);
				a.invoke(watcher, 10, name);
				a.invoke(watcher, 11, (Byte) (byte) 1);
			}
			return watcher;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Object getTeleportPacket(Integer id, Location loc) {
		Class<?> PacketPlayOutEntityTeleport = Reflection.getNMSClass("PacketPlayOutEntityTeleport");
        try {
        	try {
        		// Spigot 1.7.x e 1.8.x
    			Class.forName("org.spigotmc.ProtocolInjector");
                return PacketPlayOutEntityTeleport.getConstructor(new Class<?>[]
                		{ int.class, int.class, int.class, int.class, byte.class, byte.class, boolean.class })
                		.newInstance(id,
                				loc.getBlockX() * 32,
                				loc.getBlockY() * 32,loc.getBlockZ() * 32,
                				(byte) ((int) loc.getYaw() * 256 / 360),
                				(byte) ((int) loc.getPitch() * 256 / 360),
                				false);
        	} catch (ClassNotFoundException e) {
        		// 1.7.x
        		if(Reflection.getVersion().contains("1_7")) {
        			return PacketPlayOutEntityTeleport.getConstructor(new Class<?>[] 
            				{ int.class, int.class, int.class, int.class, byte.class, byte.class })
            				.newInstance(id,
            						loc.getBlockX() * 32,
            						loc.getBlockY() * 32,
            						loc.getBlockZ() * 32,
            						(byte) ((int) loc.getYaw() * 256 / 360),
            						(byte) ((int) loc.getPitch() * 256 / 360));
        		} else if(Reflection.getVersion().contains("1_8")) {
        			// 1.8.x
        			return PacketPlayOutEntityTeleport.getConstructor(new Class<?>[] 
    						{ int.class, int.class, int.class, int.class, byte.class, byte.class, boolean.class })
    						.newInstance(id,
    								loc.getBlockX() * 32,
    								loc.getBlockY() * 32,
    								loc.getBlockZ() * 32,
    								(byte) ((int) loc.getYaw() * 256 / 360),
    								(byte) ((int) loc.getPitch() * 256 / 360),
    								false);
        		}
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(hasBar(player)) {
			if(getEntityType(player).equals("EntityWither")) {
				Location toSpawn = player.getEyeLocation().add(player.getEyeLocation().getDirection().normalize().multiply(42));
				sendPacket(player, getTeleportPacket(bars_ids.get(player.getUniqueId()), toSpawn));
			} else {
				Location toSpawn = player.getLocation().add(0, -300, 0);
				sendPacket(player, getTeleportPacket(bars_ids.get(player.getUniqueId()), toSpawn));
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(!hasBar(event.getPlayer())) {
			return;
		}
		removeBar(event.getPlayer());
	}

	@SuppressWarnings("deprecation")
	public static void setMessage(String text) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			float health = getEntityType(player).equals("EntityWither") ? 300F : 200F;
			setMessage(player, text, health);
		}
	}
	
	public static void setMessage(Player player, String text) {
		float health = getEntityType(player).equals("EntityWither") ? 300F : 200F;
		setMessage(player, text, health);
	}
	
	public static void setMessage(final Player player, final String text, final Integer seconds) {
		final float health = getEntityType(player).equals("EntityWither") ? 300F : 200F;
		new BukkitRunnable() {
			final float aa = health / seconds;
			float ab = health;
			public void run() {				
				ab -= aa;
				if(ab <= 1.0F) {
					cancel();
					removeBar(player);
				} else {
					setMessage(player, text, ab);
				}
			}
		}.runTaskTimer(plugin, 0, 20);
	}
	
	public static void setMessage(Player player, String text, Float health) {
		if(text.length() > 64) {
			text = text.substring(0, 63);
		}
		if(player == null) {
			return;
		}
		if(!hasBar(player)) {
			Object mobPacket = getMobPacket(player, text);
	        sendPacket(player, mobPacket);
		}
		Object watcher = getWatcher(player, text, health);
        Object metaPacket = getMetadataPacket(bars_ids.get(player.getUniqueId()), watcher);
        sendPacket(player, metaPacket);
		bars_mem.put(player.getUniqueId(), text);
	}
	
	@SuppressWarnings("deprecation")
	public static void removeBar() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			removeBar(player);
		}
	}
	public static void removeBar(Player player) {
		if(player == null) {
			return;
		}
		if(!hasBar(player)) {
			return;
		}
		Object destroyEntityPacket = getDestroyEntityPacket(bars_ids.get(player.getUniqueId()));
        sendPacket(player, destroyEntityPacket);
		bars_ids.remove(player.getUniqueId());
		bars_mem.remove(player.getUniqueId());

	}
	
	public static String getMessage(Player player) {
		return bars_mem.get(player.getUniqueId());
	}
	
	public static Boolean hasBar(Player player) {
		return bars_ids.containsKey(player.getUniqueId());
	}

	private static String getEntityType(Player player) {
		try {
			Class.forName("org.spigotmc.ProtocolInjector");
			Object getHandle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = getHandle.getClass().getField("playerConnection").get(getHandle);
			Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
			Object getVersion = networkManager.getClass().getMethod("getVersion").invoke(networkManager);
			return (int)getVersion > 5 ? "EntityWither" : "EntityEnderDragon";
		} catch (Exception e) {
			return Reflection.getVersion().contains("1_8") ? "EntityWither" : "EntityEnderDragon";
		}
	}
	
}
