package me.everton.hg.jogo;

import java.util.ArrayList;

import me.everton.hg.Main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ArenaFinal {
	public static Location arena = null;
	
	public static ArrayList<Block> blocosArena = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public static void spawnar() {
		int aleatorioX = (int) (1.0D + Math.random() * 10.0D);
		int aleatorioZ = (int) (1.0D + Math.random() * 10.0D);
		int aleatorioY = 0;

		World world = Main.spawn.getWorld();
		for (int i = 90; i > 40; i--) {
			Block blockY = world.getBlockAt(aleatorioX, i, aleatorioZ);
			int y = blockY.getTypeId();
			if (y == 0) {
				aleatorioY = i;
			}
		}
		Location loc = new Location(world, aleatorioX, aleatorioY, aleatorioZ);
		AreaBattle(loc, 21, Material.BEDROCK, 11);

		Location loc2 = new Location(world, aleatorioX, aleatorioY + 9,
				aleatorioZ);
		AreaBattle(loc2, 20, Material.GLOWSTONE, 1);

		Location loc3 = new Location(world, aleatorioX, aleatorioY + 1,
				aleatorioZ);
		AreaBattle(loc3, 20, Material.AIR, 8);

		Location loc4 = new Location(world, aleatorioX, aleatorioY + 10,
				aleatorioZ);
		AreaBattle(loc4, 21, Material.BEDROCK, 1);
		arena = new Location(world, aleatorioX, aleatorioY + 3, aleatorioZ);
	}
	
	public static void spawnar(Player p) {
		int aleatorioX = (int) p.getLocation().getX();
		int aleatorioZ = (int) p.getLocation().getZ();
		int aleatorioY = (int) p.getLocation().getY();

		World world = Main.spawn.getWorld();
		
		Location loc = new Location(world, aleatorioX, aleatorioY, aleatorioZ);
		AreaBattle(loc, 21, Material.BEDROCK, 11);

		Location loc2 = new Location(world, aleatorioX, aleatorioY + 9,
				aleatorioZ);
		AreaBattle(loc2, 20, Material.GLOWSTONE, 1);

		Location loc3 = new Location(world, aleatorioX, aleatorioY + 1,
				aleatorioZ);
		AreaBattle(loc3, 20, Material.AIR, 8);

		Location loc4 = new Location(world, aleatorioX, aleatorioY + 10,
				aleatorioZ);
		AreaBattle(loc4, 21, Material.BEDROCK, 1);
		arena = new Location(world, aleatorioX, aleatorioY + 3, aleatorioZ);
		teleportar(p);
	}

	public static void teleportar(Player p) {
		p.teleport(arena);
	}

	public static void AreaBattle(Location loc, int r, Material mat, int alturaY) {
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		World w = loc.getWorld();
		int rSquared = r * r;
		for (int x = cx - r; x <= cx + r; x++) {
			for (int z = cz - r; z <= cz + r; z++) {
				for (int y = cy + 1; y <= cy + alturaY; y++) {
					if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
						w.getBlockAt(x, y, z).setType(mat);
						if(!blocosArena.contains(w.getBlockAt(x, y, z))) {
							blocosArena.add(w.getBlockAt(x, y, z));
						}
					}
				}
			}
		}
	}
}
