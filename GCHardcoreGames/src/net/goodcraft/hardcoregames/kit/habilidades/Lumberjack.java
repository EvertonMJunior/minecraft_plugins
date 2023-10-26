package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
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

import java.util.Arrays;

public class Lumberjack extends Kit {
    public Lumberjack() {
        super("Lumberjack", Material.WOOD_AXE,
                new ItemStack[]{Item.item(Material.WOOD_AXE, 1, "§3Lumberjack")},
                Arrays.asList("§7Quebre madeiras tão rápido",
                        "§7quanto um lenhador!"));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Block b = event.getBlock();
        if (hasAbility(p) && b.getType() == Material.LOG && p.getItemInHand().getType() == Material.WOOD_AXE) {
            World w = Bukkit.getServer().getWorlds().get(0);
            Double y = b.getLocation().getY() + 1.0D;
            Location l = new Location(w, b.getLocation().getX(), y, b.getLocation().getZ());
            while (l.getBlock().getType() == Material.LOG) {
                l.getBlock().breakNaturally();
                y = y + 1.0D;
                l.setY(y);
            }
        }
    }

}
