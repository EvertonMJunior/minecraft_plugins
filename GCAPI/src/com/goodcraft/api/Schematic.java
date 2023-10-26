package com.goodcraft.api;

import com.goodcraft.Main;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class Schematic {

    public static void spawn(Location loc, String name) {

        File file = new File(Main.getPlugin().getDataFolder() + File.separator + "Schematics" + File.separator + name + ".schematic");
        World world = loc.getWorld();

        if (world == null) {
            Bukkit.getLogger().log(Level.SEVERE, "Erro ao carregar o Schematic {0}", name);
        }

        SchematicFormat format = SchematicFormat.getFormat(file);
        @SuppressWarnings("deprecation")
        CuboidClipboard cc = null;
        try {
            cc = format.load(file);
        } catch (@SuppressWarnings("deprecation") IOException | DataException e1) {
        }

        Vector v = new Vector(loc.getX(), loc.getBlockY() + 2, loc.getZ());
        @SuppressWarnings("deprecation")
        EditSession es = new EditSession(new BukkitWorld(world), 999999999);

        try {
            cc.paste(es, v, true);
        } catch (MaxChangedBlocksException ex) {
        }
    }

    WorldEditPlugin we = getWorldEdit();

    public WorldEditPlugin getWorldEdit() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if ((plugin == null) || (!(plugin instanceof WorldEditPlugin))) {
            return null;
        }
        return (WorldEditPlugin) plugin;
    }

}
