package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Vacuum implements Listener {
	public static ArrayList<String> kiton = new ArrayList<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();

	@EventHandler
	public void onLaunchProj(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if ((KitManager.isWithKitItemInHand(p))
				&& (e.getAction().name().contains("RIGHT"))) {
			if (KitManager.getKit(p) == KitType.VACUUM) {
				e.setCancelled(true);

				if (!API.getGamers().contains(p)) {
					p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
					return;
				}

				if (cd.containsKey(p.getName())) {
					p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
							+ cd.get(p.getName()) + " segundos§7!");
					return;
				}

				if (!kiton.contains(p.getName())) {
					kiton.add(p.getName());
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {

								@Override
								public void run() {
									if (kiton.contains(p.getName())) {
										kiton.remove(p.getName());
										API.startCd(p, 15, 5, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
									}
								}
							}, 5 * 20L);
				}
				for (Entity ent : p.getNearbyEntities(15.0D, 15.0D, 15.0D)) {
					double d = p.getLocation().distance(ent.getLocation());
					double t = d;
					double v_x = (1.0D + 0.03000000000000001D * t)
							* (p.getLocation().getX() - ent.getLocation()
									.getX()) / t;

					double v_z = (1.0D + 0.03000000000000001D * t)
							* (p.getLocation().getZ() - ent.getLocation()
									.getZ()) / t;
					if (ent.isOnGround()) {
						p.getLocation().getY();
						ent.getLocation().getY();
					}
					double v_y;
					if (p.getLocation().getY() - ent.getLocation().getY() < 0.5D) {
						v_y = ent.getVelocity().getY();
					} else {
						v_y = (0.9D + 0.03D * t)
								* ((p.getLocation().getY() - ent.getLocation()
										.getY()) / t);
					}
					Vector v = ent.getVelocity();
					v.setX(v_x);
					v.setY(v_y);
					v.setZ(v_z);
					if (ent instanceof Player) {
						Player en = (Player) ent;
						if (API.getGamers().contains(en)) {
							ent.setVelocity(v);
						}
					} else {
						ent.setVelocity(v);
					}
				}
			}
		}
	}
}
