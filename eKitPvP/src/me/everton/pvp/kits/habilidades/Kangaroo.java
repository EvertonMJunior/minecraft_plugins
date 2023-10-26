package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class Kangaroo implements Listener {
	ArrayList<String> kangaroo = new ArrayList<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (KitManager.getKit(p) == KitType.KANGAROO) {
			if (KitManager.isWithKitItemInHand(p)) {
				if ((event.getAction() == Action.LEFT_CLICK_AIR)
						|| (event.getAction() == Action.LEFT_CLICK_BLOCK)
						|| (event.getAction() == Action.RIGHT_CLICK_BLOCK)
						|| (event.getAction() == Action.RIGHT_CLICK_AIR)) {
					event.setCancelled(true);
					
					if(!API.getGamers().contains(p)) {
						p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
						return;
					}
					
					if(Grappler.nerf.contains(p.getName())) {
						p.sendMessage("§7[§c!§7] Você está em §a§lcombate§7!");
						return;
					}
				}
				if (!kangaroo.contains(p.getName())) {
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
					kangaroo.add(p.getName());
				}
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (kangaroo.contains(p.getName())) {
			Block b = p.getLocation().getBlock();
			if ((b.getType() != Material.AIR)
					|| (b.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
				kangaroo.remove(p.getName());

				return;
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if ((e instanceof Player)) {
			Player player = (Player) e;
			if (((event.getEntity() instanceof Player))
					&& (event.getCause() == EntityDamageEvent.DamageCause.FALL)
					&& (KitManager.getKit(player) == KitType.KANGAROO)
					&& (event.getDamage() >= 7.0D)) {
				event.setDamage(7.0D);
			}
		}
	}
}
