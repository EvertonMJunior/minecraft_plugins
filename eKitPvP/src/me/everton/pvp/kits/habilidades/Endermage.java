package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Endermage implements Listener {
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.ENDERMAGE) {
			return;
		}

		if (!e.getAction().name().contains("RIGHT")) {
			return;
		}

		if (!KitManager.isWithKitItemInHand(p)) {
			return;
		}

		e.setCancelled(true);
		e.setUseInteractedBlock(Result.DENY);
		e.setUseItemInHand(Result.DENY);

		if (!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}

		p.setItemInHand(API.item(Material.AIR));
		p.updateInventory();

		final Block b = e.getClickedBlock();
		final Location bLoc = b.getLocation();
		final BlockState bs = b.getState();

		b.setType(Material.ENDER_PORTAL_FRAME);

		new BukkitRunnable() {
			int z = 5;
			final KitType pKit = KitManager.getKit(p);
			
			@Override
			public void run() {
				z--;
				
				if(pKit != KitManager.getKit(p)) {
					b.setType(bs.getType());
					b.setData(bs.getBlock().getData());
					if (!p.getInventory().contains(
							API.item(Material.PORTAL))
							&& KitManager.getKit(p) == KitType.ENDERMAGE) {
						if (p.getInventory().getItem(8) != null) {
							if (!API.isInvFull(p)) {
								p.getInventory().addItem(
										p.getInventory().getItem(8));
							} else {
								p.getWorld().dropItem(p.getLocation(),
										p.getInventory().getItem(8));
							}
							p.getInventory().setItem(8,
									API.item(Material.AIR));
						}
						p.getInventory().setItem(8,
								KitType.ENDERMAGE.getKitItem());
						p.updateInventory();
					}
					cancel();
				}
				
				for (Player on : Bukkit.getOnlinePlayers()) {
					if (KitManager.getKit(on) != KitType.ENDERMAGE
							&& isEnderable(bLoc, on.getLocation())
							&& !on.getName().equalsIgnoreCase(p.getName())) {
						b.setType(bs.getType());
						b.setData(bs.getBlock().getData());
						TeleportP(bLoc, p, on);
						if (!p.getInventory().contains(
								API.item(Material.PORTAL))
								&& KitManager.getKit(p) == KitType.ENDERMAGE) {
							p.getInventory().setItem(8,
									KitType.ENDERMAGE.getKitItem());
							p.updateInventory();
						}
						break;
					}
					if (z == 0) {
						b.setType(bs.getType());
						b.setData(bs.getBlock().getData());
						if (!p.getInventory().contains(
								API.item(Material.PORTAL))
								&& KitManager.getKit(p) == KitType.ENDERMAGE) {
							if (p.getInventory().getItem(8) != null) {
								if (!API.isInvFull(p)) {
									p.getInventory().addItem(
											p.getInventory().getItem(8));
								} else {
									p.getWorld().dropItem(p.getLocation(),
											p.getInventory().getItem(8));
								}
								p.getInventory().setItem(8,
										API.item(Material.AIR));
							}
							p.getInventory().setItem(8,
									KitType.ENDERMAGE.getKitItem());
							p.updateInventory();
						}
						cancel();
						break;
					}
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 20L);
	}

	public void TeleportP(Location portal, Player p1, Player p2) {
		p1.teleport(portal.clone().add(0.0D, 1.0D, 0.0D));
		p2.teleport(portal.clone().add(0.0D, 1.0D, 0.0D));
		p1.setNoDamageTicks(100);
		p2.setNoDamageTicks(100);
		p1.sendMessage("§7[§c!§7] Você foi puxado por um §a§lEndermage§7! Você tem §a§l5 segundos §7de invencibilidade!");
		p2.sendMessage("§7[§c!§7] Você foi puxado por um §a§lEndermage§7! Você tem §a§l5 segundos §7de invencibilidade!");
		p2.getWorld().playEffect(p2.getLocation(), Effect.ENDER_SIGNAL, 9);
		p1.getWorld().playEffect(portal, Effect.ENDER_SIGNAL, 9);
		p2.playSound(p2.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.2F);
		p1.playSound(portal, Sound.ENDERMAN_TELEPORT, 1.0F, 1.2F);
	}

	private boolean isEnderable(Location portal, Location player) {
		return (Math.abs(portal.getX() - player.getX()) < 3.0D)
				&& (Math.abs(portal.getZ() - player.getZ()) < 3.0D)
				&& (Math.abs(portal.getY() - player.getY()) >= 3.5D);
	}
}
