package me.dark.Game;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;
import me.dark.Utils.Schematic;

public class Minifeast {
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
	public static void spawnMinifeast(final Location loc) {
	    Integer r = Integer.valueOf(11);
	    for (double x = -r.intValue(); x <= r.intValue(); x += 1.0D) {
	      for (double z = -r.intValue(); z <= r.intValue(); z += 1.0D) {
	        Location l = new Location(Main.usingWorld, loc.getX() + x, loc.getY(), loc.getZ() + z);
	        if (l.distance(loc) <= r.intValue()) 
	          removeAbove(l.getBlock());
	       }
	    }
	    new BukkitRunnable() {
			public void run() {
			    Schematic.spawn(loc, "Minifeast");
				spawnChests(loc);
			}
		}.runTaskLater(Main.getMain(), 5);
	}
	protected static void spawnChests(Location loc) {
		loc = loc.clone().add(0, 2, 0);
		Block b = loc.getBlock();
		b.setType(Material.ENCHANTMENT_TABLE);

		loc.clone().add(-1, 0, -1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(-1, 0, -1).getBlock());

		loc.clone().add(-1, 0, 1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(-1, 0, 1).getBlock());

		loc.clone().add(1, 0, 1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(1, 0, 1).getBlock());

		loc.clone().add(1, 0, -1).getBlock().setType(Material.CHEST);
		fillChest(loc.clone().add(1, 0, -1).getBlock());
	}

	private static void fillChest(Block block) {
		if (!(block.getState() instanceof Chest)) {
			block.setType(Material.CHEST);
		} 
			Inventory inv = ((Chest) block.getState()).getInventory();
			Random r = new Random();
			if (r.nextInt(100) <= 15) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.DIAMOND, Feast.Int(4,4,1)));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.POTION, 1, (short) 16388));
			}
			if (r.nextInt(100) <= 20) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.POTION, 1, (short) 16426));
			}
			if (r.nextInt(100) <= 70) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.EXP_BOTTLE, Feast.Int(8)));
			}
			if (r.nextInt(100) <= 60) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.IRON_INGOT, Feast.Int(5)));
			}
			if (r.nextInt(100) <= 70) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.GOLD_INGOT, Feast.Int(6)));
			}
			if (r.nextInt(100) <= 80) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.MUSHROOM_SOUP, Feast.Int(5)));
			}
			if (r.nextInt(100) <= 80) {
				inv.setItem(new Random().nextInt(inv.getSize()), new ItemStack(
						Material.INK_SACK, Feast.Int(12), (short) 3));
			
		}
	}

}
