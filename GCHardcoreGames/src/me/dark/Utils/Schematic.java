package me.dark.Utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import me.dark.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

@SuppressWarnings("deprecation")
public class Schematic {
	
	WorldEditPlugin we = getWorldEdit();
	public WorldEditPlugin getWorldEdit() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if ((plugin == null) || (!(plugin instanceof WorldEditPlugin))) {
			return null;
		}
		return (WorldEditPlugin)plugin;
	}
	
	public static void spawn(Location loc,String arena) {
		
		File file = new File(Main.getMain().getDataFolder() + File.separator + "Schematics" + File.separator  + arena +".schematic");
		World world = loc.getWorld();
		
		if (world == null) {
			Bukkit.getLogger().log(Level.SEVERE, "Error while loading schematic for arena " + arena);
		}
		
		SchematicFormat format = SchematicFormat.getFormat(file);	
		CuboidClipboard cc = null;
		try{
			cc = format.load(file);
		}catch (IOException|DataException e1){
			e1.printStackTrace();
		}
		
		Vector v = new Vector(loc.getX(),loc.getBlockY()+2,loc.getZ());
		EditSession es = new EditSession(new BukkitWorld(world), 999999999);
		
		try{
			cc.paste(es, v, true);
		}catch (MaxChangedBlocksException e){
			e.printStackTrace();
		}
	}
  
 

}
  
