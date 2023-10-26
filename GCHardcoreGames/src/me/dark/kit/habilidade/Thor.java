package me.dark.kit.habilidade;

import java.util.Arrays;
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

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;

public class Thor extends Kit{
	public Thor() {
		super("Thor", Material.WOOD_AXE, 
				new ItemStack[] { DarkUtils.create_item(Material.WOOD_AXE, "§3Thor", 1, 0, null) },
				Arrays.asList(""));
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void kitThor(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (hasAbility(p)
				&& Main.estado != GameState.INVENCIBILITY
				&& p.getItemInHand().getType() == Material.WOOD_AXE
				&& (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)) {
			if (!hasCooldown(p)) {
				Location loc = p
						.getWorld()
						.getHighestBlockAt(
								p.getTargetBlock(null, 20).getLocation())
						.getLocation();
				LightningStrike strike = p.getWorld().strikeLightning(loc);
				strike.setMetadata("Thor", new FixedMetadataValue(Main.getMain(),
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
		if (Main.estado == GameState.PREGAME) return;
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
