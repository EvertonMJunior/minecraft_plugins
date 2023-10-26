package me.dark.kit.habilidade;

import java.util.Arrays;

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

import me.dark.Main;
import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;

public class Demoman extends Kit{
	public Demoman() {
		super("Demoman", Material.GRAVEL, 
				new ItemStack[] { DarkUtils.create_item(Material.GRAVEL, "§3Demoman", 5, 0, null), new ItemStack(Material.STONE_PLATE,5) },
				Arrays.asList(""));
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		if (event.getBlock().getType() == Material.STONE_PLATE&& hasAbility(p)) {
			event.getBlock().setMetadata(
					"Demoman",
					new FixedMetadataValue(Main.getMain(), event.getPlayer()
							.getName()));
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		final Block b = e.getClickedBlock();
		Player p = e.getPlayer();
		if (Main.players.contains(p) && e.getAction() == Action.PHYSICAL && b != null&& b.hasMetadata("Demoman")&& b.getType() == Material.STONE_PLATE && b.getRelative(BlockFace.DOWN).getType() == Material.GRAVEL) {
			b.removeMetadata("Demoman", Main.getMain());
			b.setType(Material.AIR);
			b.getWorld().createExplosion(
					b.getLocation().clone().add(0.5D, 0.5D, 0.5D), 4.0F);
		}
	}

}
