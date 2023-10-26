package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;

public class Hulk implements Listener {
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();

	@EventHandler
	public void hulk(PlayerInteractEntityEvent e) {
		final Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.HULK) {
			return;
		}

		if (!(e.getRightClicked() instanceof Player)) {
			return;
		}
		Player r = (Player) e.getRightClicked();

		if (!KitManager.isWithKitItemInHand(p)) {
			return;
		}

		e.setCancelled(true);
		p.updateInventory();
		
		if(!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}
		
		if(!API.getGamers().contains(r)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit contra esse jogador!");
			return;
		}

		if (cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
					+ cd.get(p.getName()) + " segundos§7!");
			return;
		}

		API.startCd(p, 15, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");

		p.setPassenger(r);
		r.sendMessage("§aVocê foi pego por um §aHulk§a! Aperte §2Shift§a antes que ele lhe dê um hit e você seja jogado para o alto!");
	}

	@EventHandler
	public void hulkHit(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}

		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		final Player p = (Player) e.getDamager();

		if (p.getPassenger() == null) {
			return;
		}

		if (!(p.getPassenger() instanceof Player)) {
			return;
		}
		final Player r = (Player) p.getPassenger();

		if (r.getName() != ((Player) e.getEntity()).getName()) {
			return;
		}

		if (KitManager.getKit(p) != KitType.HULK) {
			return;
		}
		p.eject();
		final Damageable hp = r;
		new Runnable() {

			@Override
			public void run() {
				hp.damage(0D, p);
			}
		};

		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				r.setVelocity(new Vector(r.getVelocity().getX(), 1D, r
						.getVelocity().getZ()));
			}
		}, 1L);
	}
}
