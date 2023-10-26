package net.goodcraft.api;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionFactory;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;

public class Schematic {
    @SuppressWarnings("deprecation")
    public static EditSession paste(String arena, Location loc) {
        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        File schematic = new File("plugins/schematics/" + arena + ".schematic");
        if (we.getWorldEdit().getEditSessionFactory() != null) {
            EditSessionFactory eSF = we.getWorldEdit().getEditSessionFactory();
            EditSession session = eSF.getEditSession(new BukkitWorld(loc.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, new Vector(loc.getX(), loc.getY() + 2, loc.getZ()), true);
                return session;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}