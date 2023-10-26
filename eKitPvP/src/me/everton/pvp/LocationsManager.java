package me.everton.pvp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LocationsManager {
	public static void setLocation(Player p, String nome) {
		String name = nome.toLowerCase();
		Main.getPlugin().getConfig().set("warps." + name + ".world", p.getLocation().getWorld().getName());
		Main.getPlugin().getConfig().set("warps." + name + ".x", p.getLocation().getX());
		Main.getPlugin().getConfig().set("warps." + name + ".y", p.getLocation().getY());
		Main.getPlugin().getConfig().set("warps." + name + ".z", p.getLocation().getZ());
		Main.getPlugin().getConfig().set("warps." + name + ".yaw", p.getLocation().getYaw());
		Main.getPlugin().getConfig().set("warps." + name + ".pitch", p.getLocation().getPitch());
		Main.getPlugin().saveConfig();
	}
	
	public static Location getLocation(String nome) {
		try {
			String name = nome.toLowerCase();
			World w = Bukkit.getWorld(Main.getPlugin().getConfig().getString("warps." + name + ".world"));
			Double x = Main.getPlugin().getConfig().getDouble("warps." + name + ".x");
			Double y = Main.getPlugin().getConfig().getDouble("warps." + name + ".y");
			Double z = Main.getPlugin().getConfig().getDouble("warps." + name + ".z");
			Float yaw = (float) Main.getPlugin().getConfig().getDouble("warps." + name + ".yaw");
			Float pitch = (float) Main.getPlugin().getConfig().getDouble("warps." + name + ".pitch");
			return new Location(w, x, y, z, yaw, pitch);
		} catch(Exception e) {
			return null;
		}
	}
	
	public static Location getSpawn() {
		if(getLocation("spawn") == null) {
			return Bukkit.getWorld("world").getSpawnLocation();
		} else {
			return getLocation("spawn");
		}
	}
}
