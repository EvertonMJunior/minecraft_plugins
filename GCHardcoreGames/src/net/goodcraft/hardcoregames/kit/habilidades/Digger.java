package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Digger extends Kit {

    public Digger() {
        super("Digger", Material.DRAGON_EGG,
                new ItemStack[]{Item.item(Material.DRAGON_EGG, 5, "§3Digger")},
                Arrays.asList("§7Inicia a partida com alguns ovos.",
                        "§7Colocando-os no chão, um enorme buraco",
                        "§7irá se abrir, fazendo assim com",
                        "§7que seu adversário não fuja."));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (p.getItemInHand().getType() == Material.DRAGON_EGG
                && hasAbility(p)) {
            final Block b = event.getBlock();
            b.setType(Material.AIR);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
                public void run() {
                    int dist = (int) Math.ceil((double) (5 - 1) / 2);
                    for (int y = -1; y >= -5; y--) {
                        for (int x = -dist; x <= dist; x++) {
                            for (int z = -dist; z <= dist; z++) {
                                if (b.getY() + y <= 0)
                                    continue;
                                Block block = b.getWorld().getBlockAt(b.getX() + x, b.getY() + y, b.getZ() + z);
                                if (block.getType() != Material.BEDROCK)
                                    block.setType(Material.AIR);
                            }
                        }
                    }
                }
            }, 30);
        }
    }
}
