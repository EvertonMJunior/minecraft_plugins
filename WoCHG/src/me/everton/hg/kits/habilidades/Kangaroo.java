package me.everton.hg.kits.habilidades;

import java.util.ArrayList;

import me.everton.hg.Main;
import me.everton.hg.kits.KitManager;
import me.everton.hg.kits.KitType;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class Kangaroo implements Listener {
	ArrayList<String> onAir = new ArrayList<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.KANGAROO) {
			return;
		}

		if (!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		
		e.setCancelled(true);
		e.setUseInteractedBlock(Result.DENY);
		p.updateInventory();

		if (!Main.STATE_KITS) {
			p.sendMessage("§7[§c!§7] Os kits estao desativados!");
			return;
		}
		
		if (p.getItemInHand().getType() == Material.FIREWORK) {
			if (!onAir.contains(p.getName())) {
				if (!p.isSneaking()) {
					p.setFallDistance(-5.0F);
					Vector vector = p.getEyeLocation().getDirection();
					vector.multiply(0.6F);
					vector.setY(1.2F);
					p.setVelocity(vector);
				} else {
					p.setFallDistance(-5.0F);
					Vector vector = p.getEyeLocation().getDirection();
					vector.multiply(1.2F);
					vector.setY(0.8D);
					p.setVelocity(vector);
				}
				onAir.add(p.getName());
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (onAir.contains(p.getName())) {
			Block b = p.getLocation().getBlock();
			if ((b.getType() != Material.AIR)
					|| (b.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
				onAir.remove(p.getName());

				return;
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		Entity en = e.getEntity();
		if ((en instanceof Player)) {
			Player p = (Player) e.getEntity();
			if (KitManager.getKit(p) != KitType.KANGAROO) {
				return;
			}

			if (!Main.STATE_KITS) {
				return;
			}
			
			if (e.getDamage() >= 7.0D) {
				e.setDamage(7.0D);
			}
		}
	}
}
