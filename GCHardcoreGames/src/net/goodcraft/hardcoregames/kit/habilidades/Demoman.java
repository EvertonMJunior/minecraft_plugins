package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;

public class Demoman extends Kit {
    public Demoman() {
        super("Demoman", Material.GRAVEL,
                new ItemStack[]{Item.item(Material.GRAVEL, 5, "§3Demoman"), new ItemStack(Material.STONE_PLATE, 5)},
                Arrays.asList("§7Inicie a partida com alguns cascalhos",
                        "§7e placas de pressão que ao colocar",
                        "§7sobre o cascalho, quem passar",
                        "§7sobre a ela irá explodirá"));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (event.getBlock().getType() == Material.STONE_PLATE && hasAbility(p)) {
            event.getBlock().setMetadata(
                    "Demoman",
                    new FixedMetadataValue(Main.getMain(), event.getPlayer()
                            .getName()));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        final Block b = e.getClickedBlock();
        if (Main.players.contains(p.getUniqueId()) && e.getAction() == Action.PHYSICAL && b != null && b.hasMetadata("Demoman") && b.getType() == Material.STONE_PLATE && b.getRelative(BlockFace.DOWN).getType() == Material.GRAVEL) {
            b.removeMetadata("Demoman", Main.getMain());
            b.setType(Material.AIR);
            b.getWorld().createExplosion(
                    b.getLocation().clone().add(0.5D, 0.5D, 0.5D), 4.0F);
        }
    }

}
