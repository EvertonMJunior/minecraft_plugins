package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

public class Stomper implements Listener {
	public static ArrayList<String> stompando = new ArrayList<>();

	@EventHandler
	public void stompar(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (KitManager.getKit(p) != KitType.STOMPER) {
				return;
			}

			if (e.getCause() != DamageCause.FALL) {
				return;
			}

			if (!API.getGamers().contains(p)) {
				return;
			}

			int players = 0;

			for (Entity n : p.getNearbyEntities(5.0D, 5.0D, 5.0D)) {
				if (n instanceof Player) {
					players++;
					Player ne = (Player) n;

					if (API.getGamers().contains(ne)) {
						ne.sendMessage("§7[§c!§7] Você foi stompado por §c§l"
								+ p.getName() + "§7!");
						ne.damage(e.getDamage(), p);
					}
				}
			}

			if (players > 0) {
				p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND,
						15.5F, 15.5F);
			}

			if (e.getDamage() >= 4.0D) {
				e.setDamage(4.0D);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void isStomping(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.STOMPER) {
			return;
		}

		if (p.isOnGround()) {
			return;
		}

		if (stompando.contains(p.getName())) {
			return;
		}

		stompando.add(p.getName());
	}
}
