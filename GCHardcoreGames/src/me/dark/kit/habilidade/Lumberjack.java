package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;

public class Lumberjack extends Kit {
	public Lumberjack() {
		super("Lumberjack", Material.WOOD_AXE, 
				new ItemStack[] { DarkUtils.create_item(Material.WOOD_AXE, "§3Lumberjack", 1, 0, null) },
				Arrays.asList(""));
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void onBlockBreak1(BlockBreakEvent event) {
		Player p = event.getPlayer();
	    Block b = event.getBlock();
	    if (hasAbility(p) && b.getType() == Material.LOG && p.getItemInHand().getType() == Material.WOOD_AXE) {
	      World w = (World)Bukkit.getServer().getWorlds().get(0);
	      Double y = Double.valueOf(b.getLocation().getY() + 1.0D);
	      Location l = new Location(w, b.getLocation().getX(), y.doubleValue(), b.getLocation().getZ());
	      while (l.getBlock().getType() == Material.LOG) {
	    	  DarkUtils.sendBlockBreakParticles(l, l.getBlock().getType());
	    	  l.getBlock().breakNaturally();
	    	  y = Double.valueOf(y.doubleValue() + 1.0D);
	    	  l.setY(y.doubleValue());
	      }
	   }
	}
	
}
