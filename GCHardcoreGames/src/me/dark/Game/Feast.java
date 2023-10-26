package me.dark.Game;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;
import me.dark.Utils.Schematic;

public class Feast {
	public static Location feastLoc = new Location(Main.usingWorld, 56, Main.usingWorld.getHighestBlockYAt(56, 0)+1, 56);
	
	public static void spawnFeast(final Location loc) {
		feastLoc = loc;
	    Integer r = Integer.valueOf(32);
	    for (double x = -r.intValue(); x <= r.intValue(); x += 1.0D) {
	      for (double z = -r.intValue(); z <= r.intValue(); z += 1.0D) {
	        Location l = new Location(Main.usingWorld, loc.getX() + x, loc.getY(), loc.getZ() + z);
	        if (l.distance(loc) <= r.intValue()) 
	          removeAbove(l.getBlock());
	       }
	    }
	    new BukkitRunnable() {
			public void run() {
			    Schematic.spawn(loc, "Feast");
			}
		}.runTaskLater(Main.getMain(), 3);
	}
	
	public static void removeAbove(Block block){
		Location loc = block.getLocation();
	    loc.setY(loc.getY() + 1.0D);
	    Block newBlock = Main.usingWorld.getBlockAt(loc);
	    while (loc.getY() < Main.usingWorld.getMaxHeight()){
	    	newBlock.setType(Material.AIR);
	        loc.setY(loc.getY() + 1.0D);
	        newBlock = Main.usingWorld.getBlockAt(loc);
	    }
	}
	  
	public static void spawnChests(Location loc) {
		Block b = loc.add(0,1,0).getBlock();
		b.setType(Material.ENCHANTMENT_TABLE);

		for (int x = -2; x <= 2; x++) {
			if (x != 0) {
				loc.clone().add(x, 0, x).getBlock().setType(Material.CHEST);
				fillChest(loc.clone().add(x, 0, x).getBlock());
			}
		}
		for (int x = -2; x <= 2; x++) {
			if (x != 0 && x != 1 && x != -1) {
				loc.clone().add(0, 0, x).getBlock().setType(Material.CHEST);
				fillChest(loc.clone().add(0, 0, x).getBlock());
				loc.clone().add(x, 0, 0).getBlock().setType(Material.CHEST);
				fillChest(loc.clone().add(x, 0, 0).getBlock());
			}
		}
		loc.clone().add(-1, 0, 1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(-1, 0, 1).getBlock());

		loc.clone().add(-2, 0, 2).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(-2, 0, 2).getBlock());

		loc.clone().add(1, 0, -1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(1, 0, -1).getBlock());

		loc.clone().add(2, 0, -2).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(2, 0, -2).getBlock());
	}

	private static void fillChest(Block block) {
		if (!(block.getState() instanceof Chest)) {
			Bukkit.getLogger().info(
					"§cThe block at " + block.getX() + "," + block.getY() + ","
							+ block.getZ() + " dont is chest!");
		} else {
			Inventory inv = ((Chest) block.getState()).getInventory();
			Random r = new Random();
			if (r.nextInt(100) <= 16) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.DIAMOND_SWORD, 1));
			}
			if (r.nextInt(100) <= 16) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.DIAMOND_HELMET, 1));
			}
			if (r.nextInt(100) <= 13) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.DIAMOND_CHESTPLATE, 1));
			}
			if (r.nextInt(100) <= 14) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.DIAMOND_LEGGINGS, 1));
			}
			if (r.nextInt(100) <= 15) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.DIAMOND_BOOTS, 1));
			}
			if (r.nextInt(100) <= 45) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.COOKED_BEEF, Int(32)));
			}
			if (r.nextInt(100) <= 45) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.ENDER_PEARL, Int(5)));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.POTION, 1, (short) 8201));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.POTION, 1, (short) 16420));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.POTION, 1, (short) 8227));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.POTION, 1, (short) 16424));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.POTION, 1, (short) 16426));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.POTION, 1, (short) 16428));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.POTION, 1, (short) 16418));
			}
			if (r.nextInt(100) <= 33) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.WEB, Int(15)));
			}
			if (r.nextInt(100) <= 33) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.BOW, 1));
			}
			if (r.nextInt(100) <= 33) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.ARROW, Int(32)));
			}
			if (r.nextInt(100) <= 50) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.EXP_BOTTLE, Int(8)));
			}
			if (r.nextInt(100) <= 75) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.FLINT_AND_STEEL, 1));
			}
			if (r.nextInt(100) <= 55) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.TNT, Int(7)));
			}
			if (r.nextInt(100) <= 60) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.WATER_BUCKET, 1));
			}
			if (r.nextInt(100) <= 60) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.LAVA_BUCKET, 1));
			}

		}
	}
	public static int Int(int i) {
		int random = new Random().nextInt(i);
		if (random == 0) {
			random = 1;
		}
		return random;
	}

	public static int Int(int i, int Max, int Min) {
		int random = new Random().nextInt(i);
		if (random < Min) {
			random = Min;
		}
		if (random > Max) {
			random = Max;
		}
		return random;
	}

}
