package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.Set;

public class Thor extends Kit {
	public Thor() {
		super("Thor", Material.WOOD_AXE, 
				new ItemStack[] { Item.item(Material.WOOD_AXE, 1, "§3Thor") },
				Arrays.asList("§7Ganhe um machado que lança raios!"));
	}

	@EventHandler
	public void kitThor(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (hasAbility(p)
				&& Main.getMg().getGameState() != GameState.INVENCIBILITY
				&& p.getItemInHand().getType() == Material.WOOD_AXE
				&& (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)) {
			if (!hasCooldown(p)) {
				Location loc = p
						.getWorld()
						.getHighestBlockAt(
								p.getTargetBlock((Set<Material>) null, 20).getLocation())
						.getLocation();
				LightningStrike strike = p.getWorld().strikeLightning(loc);
				strike.setMetadata("Thor", new FixedMetadataValue(Main.getPlugin(),
						"Thor"));
				addCooldown(p, 6);
				if (loc.getBlockY() >= 80) {
					if (loc.getBlock().getType() == Material.NETHERRACK
							|| (loc.getBlock().getType() == Material.FIRE && loc
									.subtract(0, 1, 0).getBlock().getType() == Material.NETHERRACK)) {
						p.getWorld().createExplosion(loc, 5F, true);
						return;
					}
					if (loc.getBlock().getType() != Material.BEDROCK) {
						loc.getBlock().setType(Material.NETHERRACK);
						loc.getBlock().getRelative(BlockFace.UP)
								.setType(Material.FIRE);
						return;
					}
				}
			} else {
				mensagemcooldown(p);
				return;
			}
		}
	}

	@EventHandler
	public void onDmg(EntityDamageByEntityEvent e) {
		if (Main.getMg().getGameState() == GameState.PREGAME) return;
		if (e.getDamager() instanceof LightningStrike
				&& e.getEntity() instanceof Player) {
			if (e.getDamager().hasMetadata("Thor")) {
				if (hasAbility((Player) e.getEntity())) {
					e.setCancelled(true);
				} else {
					if (e.getDamage() > 6) {
						e.setDamage((double)6);
					}
				}
			}
		}
	}
}
