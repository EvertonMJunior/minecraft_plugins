package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class Grappler implements Listener {
	public String message = ChatColor.RED
			+ "Seu gancho ainda nao prendeu em nada!";
	public boolean sound = true;

	public static ArrayList<String> nerf = new ArrayList<>();

	public static HashMap<String, CopyOfFishingHook> hooks = new HashMap<>();

	@EventHandler
	public void Nerf(EntityDamageByEntityEvent event) {
		if ((event.isCancelled())) {
			return;
		}
		if (((event.getEntity() instanceof Player))
				&& ((event.getDamager() instanceof Player))) {
			final Player p1 = (Player) event.getEntity();
			if (!nerf.contains(p1.getName())) {
				nerf.add(p1.getName());
				Bukkit.getServer()
						.getScheduler()
						.scheduleSyncDelayedTask(Main.getPlugin(),
								new Runnable() {
									public void run() {
										nerf.remove(p1.getName());
									}
								}, 3 * 20L);
			}
		}
	}

	@EventHandler
	public void onSlot(PlayerItemHeldEvent e) {
		if (hooks.containsKey(e.getPlayer().getName())) {
			((CopyOfFishingHook) hooks.get(e.getPlayer().getName())).remove();
			hooks.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (hooks.containsKey(e.getPlayer().getName())) {
			if (!KitManager.isWithKitItemInHand(e.getPlayer())) {
				((CopyOfFishingHook) hooks.get(e.getPlayer().getName()))
						.remove();
				hooks.remove(e.getPlayer().getName());
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLeash(PlayerLeashEntityEvent e) {
		Player p = e.getPlayer();
		if (((e.getEntity() instanceof Player))
				&& (hooks.containsKey(p.getName()))
				&& ((Player) e.getEntity() == ((CopyOfFishingHook) hooks.get(p
						.getName())).owner)) {
			return;
		}
		if ((e.getPlayer().getItemInHand().getType().equals(Material.LEASH))
				&& (KitManager.getKit(p) == KitType.GRAPPLER)) {
			e.setCancelled(true);
			e.getPlayer().updateInventory();
			if (!hooks.containsKey(p.getName())) {
				return;
			}
			if (!((CopyOfFishingHook) hooks.get(p.getName())).isHooked()) {
				return;
			}

			if (!API.getGamers().contains(p)) {
				p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
				return;
			}

			double d = ((CopyOfFishingHook) hooks.get(p.getName()))
					.getBukkitEntity().getLocation().distance(p.getLocation());
			double t = d;
			double v_x = (1.0D + 0.03000000000000001D * t)
					* (((CopyOfFishingHook) hooks.get(p.getName()))
							.getBukkitEntity().getLocation().getX() - p
							.getLocation().getX()) / t;

			double v_z = (1.0D + 0.03000000000000001D * t)
					* (((CopyOfFishingHook) hooks.get(p.getName()))
							.getBukkitEntity().getLocation().getZ() - p
							.getLocation().getZ()) / t;
			if (p.isOnGround()) {
				((CopyOfFishingHook) hooks.get(p.getName())).getBukkitEntity()
						.getLocation().getY();
				p.getLocation().getY();
			}
			double v_y;
			if (((CopyOfFishingHook) hooks.get(p.getName())).getBukkitEntity()
					.getLocation().getY()
					- p.getLocation().getY() < 0.5D) {
				v_y = p.getVelocity().getY();
			} else {
				v_y = (0.9D + 0.03D * t)
						* ((((CopyOfFishingHook) hooks.get(p.getName()))
								.getBukkitEntity().getLocation().getY() - p
								.getLocation().getY()) / t);
			}
			Vector v = p.getVelocity();
			v.setX(v_x);
			v.setY(v_y);
			v.setZ(v_z);
			p.setVelocity(v);
			p.setFallDistance(0.0F);
			if (sound) {

				for (Entity ent : p.getNearbyEntities(20D, 20D, 20D)) {
					if (ent instanceof Player) {
						Player en = (Player) ent;
						en.playSound(p.getLocation(), Sound.STEP_GRAVEL, 3.0F,
								1.0F);
					}
				}
				p.playSound(p.getLocation(), Sound.STEP_GRAVEL, 3.0F, 1.0F);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((KitManager.isWithKitItemInHand(p))
				&& (KitManager.getKit(p) == KitType.GRAPPLER)) {
			e.setCancelled(true);

			if (!API.getGamers().contains(p)) {
				p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
				return;
			}

			if ((e.getAction() == Action.LEFT_CLICK_AIR)
					|| (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
				if (nerf.contains(p.getName())) {
					p.sendMessage("§7[§c!§7] Você está em combate, aguarde!");
					return;
				}
				if (hooks.containsKey(p.getName())) {
					((CopyOfFishingHook) hooks.get(p.getName())).remove();
				}
				CopyOfFishingHook nmsHook = new CopyOfFishingHook(p.getWorld(),
						((CraftPlayer) p).getHandle());
				nmsHook.spawn(p.getEyeLocation().add(
						p.getLocation().getDirection().getX(),
						p.getLocation().getDirection().getY(),
						p.getLocation().getDirection().getZ()));
				nmsHook.move(p.getLocation().getDirection().getX() * 5.0D, p
						.getLocation().getDirection().getY() * 5.0D, p
						.getLocation().getDirection().getZ() * 5.0D);
				hooks.put(p.getName(), nmsHook);
			} else {
				if (nerf.contains(p.getName())) {
					p.sendMessage("§7[§c!§7] Você está em combate, aguarde!");
					return;
				}
				if (!hooks.containsKey(p.getName())) {
					return;
				}
				if (!((CopyOfFishingHook) hooks.get(p.getName())).isHooked()) {
					p.sendMessage(message);
					CopyOfFishingHook gh = (CopyOfFishingHook) hooks.get(p
							.getName());
					gh.move(gh.motX, gh.motY - 2.0D, gh.motY);
					return;
				}
				double d = ((CopyOfFishingHook) hooks.get(p.getName()))
						.getBukkitEntity().getLocation()
						.distance(p.getLocation());
				double t = d;
				double v_x = (1.0D + 0.03000000000000001D * t)
						* (((CopyOfFishingHook) hooks.get(p.getName()))
								.getBukkitEntity().getLocation().getX() - p
								.getLocation().getX()) / t;

				double v_z = (1.0D + 0.03000000000000001D * t)
						* (((CopyOfFishingHook) hooks.get(p.getName()))
								.getBukkitEntity().getLocation().getZ() - p
								.getLocation().getZ()) / t;
				if (p.isOnGround()) {
					((CopyOfFishingHook) hooks.get(p.getName()))
							.getBukkitEntity().getLocation().getY();
					p.getLocation().getY();
				}
				double v_y;
				if (((CopyOfFishingHook) hooks.get(p.getName()))
						.getBukkitEntity().getLocation().getY()
						- p.getLocation().getY() < 0.5D) {
					v_y = p.getVelocity().getY();
				} else {
					v_y = (0.9D + 0.03D * t)
							* ((((CopyOfFishingHook) hooks.get(p.getName()))
									.getBukkitEntity().getLocation().getY() - p
									.getLocation().getY()) / t);
				}
				Vector v = p.getVelocity();
				v.setX(v_x);
				v.setY(v_y);
				v.setZ(v_z);
				p.setVelocity(v);
				p.setFallDistance(0.0F);
				if (p.getLocation().getY() < ((CopyOfFishingHook) hooks.get(p
						.getName())).getBukkitEntity().getLocation().getY()) {
					p.setFallDistance(0.0F);
				}
				if (sound) {
					p.getWorld().playSound(p.getLocation(), Sound.STEP_GRAVEL,
							3.0F, 1.0F);
				}
			}
		}
	}
}
