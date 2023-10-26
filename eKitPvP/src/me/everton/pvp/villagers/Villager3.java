package me.everton.pvp.villagers;

import me.everton.pvp.LocationsManager;
import me.everton.pvp.Main;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Villager3 implements Listener{
	public static void spawnVillager(Player p) {
		final Villager v = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
		LocationsManager.setLocation(p, "villager3");
		v.setCustomName("§e§lCompre Kits");
		v.setCustomNameVisible(true);
		v.setProfession(Profession.PRIEST);
		v.setRemoveWhenFarAway(false);
		
		new BukkitRunnable() {

			@Override
			public void run() {
				if (!v.isDead()) {
					if (v.getNoDamageTicks() < 1000) {
						v.setNoDamageTicks(999999999);
					}
					v.setVelocity(new Vector());
				} else {
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 0L);
	}
	
	public static void spawnVillager(Location loc) {
		final Villager v = (Villager) loc.getWorld().spawnEntity(loc,
				EntityType.VILLAGER);
		v.setCustomName("§e§lCompre Kits");
		v.setCustomNameVisible(true);
		v.setProfession(Profession.PRIEST);
		v.setRemoveWhenFarAway(false);
		
		new BukkitRunnable() {

			@Override
			public void run() {
				if (!v.isDead()) {
					if (v.getNoDamageTicks() < 1000) {
						v.setNoDamageTicks(999999999);
					}
					v.setVelocity(new Vector());
				} else {
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 0L);
	}
	
	@SuppressWarnings("unused")
	@EventHandler
	public void onIE(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (!(e.getRightClicked() instanceof Villager)) {
			return;
		}
		Villager v = (Villager) e.getRightClicked();
		if (v.isCustomNameVisible()
				&& !v.getCustomName().equalsIgnoreCase("§e§lInformaçoes")) {
			return;
		}

		e.setCancelled(true);
		
	}
	
	@SuppressWarnings("unused")
	private void openInv(Player p) {
		
	}
}
