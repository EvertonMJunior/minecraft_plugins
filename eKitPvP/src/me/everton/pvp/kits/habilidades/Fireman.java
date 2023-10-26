package me.everton.pvp.kits.habilidades;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;

public class Fireman implements Listener{
	@EventHandler
	public void onFireDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		final Player p = (Player) e.getEntity();
		
		if(e.getCause() != DamageCause.FIRE && e.getCause() != DamageCause.FIRE_TICK && e.getCause() != DamageCause.LAVA) {
			return;
		}
		
		if(KitManager.getKit(p) != KitType.FIREMAN) {
			return;
		}
		
		if(!API.getGamers().contains(p)) {
			return;
		}
		
		e.setCancelled(true);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				p.setFireTicks(0);
			}
		}.run();
	}
}
