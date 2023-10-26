package me.everton.hg.jogo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import me.everton.hg.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

public class Feast {
	public static Location feast = null;
	public static ArrayList<Block> blocos = new ArrayList<>();
	
	public static void spawnFeast() {
		if(feast != null) {
			return;
		}
		
		if(!Main.EM_JOGO) {
			return;
		}
		
		WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        File schematic = new File("plugins/WoCHG/FeastBiladaHG.schematic");
        EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(Bukkit.getWorlds().get(0)), 1000000);
        Random r = new Random();
        try {
        	int xC = r.nextInt(100);
            int zC = r.nextInt(100);
            int yC = Bukkit.getWorlds().get(0).getHighestBlockYAt(xC, zC);
            
            while(yC < 65) {
        		zC = r.nextInt(100);
        		xC = r.nextInt(100);
        		yC = Bukkit.getWorlds().get(0).getHighestBlockYAt(xC, zC);
        	}
            MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, new Vector(xC, yC, zC), false);
            
            feast = new Location(Bukkit.getWorlds().get(0), xC + 0.5D, yC + 3.5D, zC + 0.5D);
            return;
        } catch (MaxChangedBlocksException
                | com.sk89q.worldedit.data.DataException | IOException e2) {
            e2.printStackTrace();
        }
	}
	
	public static void teleport(Player p) {
		if(feast == null) {
			p.sendMessage("§7[§c!§7] O §c§lfeast §7ainda nao spawnou!");
			return;
		}
		p.teleport(feast);
	}
	
	public static void spawnChests() {
		Location l = feast.clone().subtract(0, 1, 0).getBlock().getLocation();
		Block mB = l.getBlock();
		mB.setType(Material.ENCHANTMENT_TABLE);
		ArrayList<Chest> baus = new ArrayList<>();
		
		l.clone().add(0, 0, 2).getBlock().setType(Material.CHEST);
		Chest c1 = (Chest) l.clone().add(0, 0, 2).getBlock().getState(); 
		baus.add(c1);
		
		l.clone().subtract(0, 0, 2).getBlock().setType(Material.CHEST);
		Chest c2 = (Chest) l.clone().subtract(0, 0, 2).getBlock().getState();
		baus.add(c2);
		
		l.clone().add(2, 0, 0).getBlock().setType(Material.CHEST);
		Chest c3 = (Chest) l.clone().add(2, 0, 0).getBlock().getState();
		baus.add(c3);
		
		l.clone().subtract(2, 0, 0).getBlock().setType(Material.CHEST);
		Chest c4 = (Chest) l.clone().subtract(2, 0, 0).getBlock().getState();
		baus.add(c4);
		
		l.clone().add(2, 0, 2).getBlock().setType(Material.CHEST);
		Chest c5 = (Chest) l.clone().add(2, 0, 2).getBlock().getState();
		baus.add(c5);
		
		l.clone().subtract(2, 0, 2).getBlock().setType(Material.CHEST);
		Chest c6 = (Chest) l.clone().subtract(2, 0, 2).getBlock().getState();
		baus.add(c6);
		
		l.clone().add(1, 0, 1).getBlock().setType(Material.CHEST);
		Chest c7 = (Chest) l.clone().add(1, 0, 1).getBlock().getState();
		baus.add(c7);
		
		l.clone().subtract(1, 0, 1).getBlock().setType(Material.CHEST);
		Chest c8 = (Chest) l.clone().subtract(1, 0, 1).getBlock().getState();
		baus.add(c8);
		
		l.clone().subtract(0, 0, 1).add(1, 0, 0).getBlock().setType(Material.CHEST);
		Chest c9 = (Chest) l.clone().subtract(0, 0, 1).add(1, 0, 0).getBlock().getState();
		baus.add(c9);
		
		l.clone().subtract(1, 0, 0).add(0, 0, 1).getBlock().setType(Material.CHEST);
		Chest c10 = (Chest) l.clone().subtract(1, 0, 0).add(0, 0, 1).getBlock().getState();
		baus.add(c10);
		
		l.clone().subtract(0, 0, 2).add(2, 0, 0).getBlock().setType(Material.CHEST);
		Chest c11 = (Chest) l.clone().subtract(0, 0, 2).add(2, 0, 0).getBlock().getState();
		baus.add(c11);
		
		l.clone().subtract(2, 0, 0).add(0, 0, 2).getBlock().setType(Material.CHEST);
		Chest c12 = (Chest) l.clone().subtract(2, 0, 0).add(0, 0, 2).getBlock().getState();
		baus.add(c12);
		
		for(Chest bau : baus) {
			fillChest(bau);
			
		}
	}
	
	public static int TEMPO = 0;
	public static Integer shed_id = null;
	
	public static void cancelContagem() {
		if(shed_id != null) {
			Main.sh.cancelTask(shed_id);
			shed_id = null;
		}
	}
	
	public static void contagemFeast() {
		if(feast != null) {
			return;
		} else {
			spawnFeast();
		}
		cancelContagem();
		
		shed_id = Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(TEMPO == 300) {
					Bukkit.broadcastMessage("§7[§c!§7] O §c§lfeast §7spawnou em §cX: §l" + feast.getX() + " §cZ: §l" + feast.getZ() + "§7!");
					spawnChests();
					cancelContagem();
				}
				
				if(TEMPO % 30 == 0 && TEMPO < 300 || TEMPO <= 15) {
					Bukkit.broadcastMessage("§7[§c!§7] O §c§lfeast §7spawnará em §c§l" + Main.secToMinSec(300 - TEMPO) + "§7 nas coordenadas §cX: §l<x> §cZ: §l<z>§7!".replace("<x>", feast.getBlockX() + "").replace("<z>", feast.getBlockZ() + ""));
				}
				
				TEMPO++;
			}
		}, 0L, 20L);
	}
	
	public static void fillChest(Chest chest)
	  {
	    if (chance(15)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_SWORD, 1) });
	    }
	    if (chance(15)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_HELMET, 1) });
	    }
	    if (chance(15)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_CHESTPLATE, 1) });
	    }
	    if (chance(15)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_LEGGINGS, 1) });
	    }
	    if (chance(15)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS, 1) });
	    }
	    if (chance(10)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ENDER_PEARL, aleatorio(16)) });
	    }
	    if (chance(10)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.EXP_BOTTLE, aleatorio(20)) });
	    }
	    if (chance(10)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.MUSHROOM_SOUP, aleatorio(14)) });
	    }
	    if (chance(15)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.TNT, aleatorio(20)) });
	    }
	    if (chance(20)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLDEN_APPLE, aleatorio(10)) });
	    }
	    if (chance(20)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.INK_SACK, aleatorio(32), (short) 3) });
	    }
	    if (chance(7)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, (short) 16420) });
	    }
	    if (chance(7)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, (short) 8259) });
	    }
	    if (chance(7)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, (short) 16393) });
	    }
	    if (chance(7)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, (short) 16428) });
	    }
	    if (chance(7)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, (short) 16421) });
	    }
	    if (chance(7)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, (short) 16418) });
	    }
	    if (chance(7)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, (short) 16417) });
	    }
	    if (chance(7)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, (short) 16424) });
	    }
	    if (chance(7)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, (short) 16426) });
	    }
	    if (chance(20)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COOKED_BEEF, aleatorio(64)) });
	    }
	    if (chance(20)) {
	      chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.BREAD, aleatorio(64)) });
	    }
	  }
	  
	  private static int aleatorio(int chance)
	  {
	    int random = new Random().nextInt(chance);
	    if (random == 0) {
	      random = 1;
	    }
	    return random;
	  }
	  
	  private static boolean chance(int i)
	  {
	    if (new Random().nextInt(100) <= i) {
	      return true;
	    }
	    return false;
	  }
}
