package me.everton.pvp.kits.habilidades;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class Anchor implements Listener{
	@EventHandler
	public void noKb(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		final Player p = (Player) e.getEntity();
		
		if(!(e.getDamager() instanceof Player)) {
			return;
		}
		final Player d = (Player) e.getDamager();
		
		if(KitManager.getKit(p) != KitType.ANCHOR && KitManager.getKit(d) != KitType.ANCHOR) {
			return;
		}
		
		if(e.isCancelled()) {
			return;
		}
		
		if(!API.getGamers().contains(p)) {
			return;
		}
		
		if(!API.getGamers().contains(d)) {
			return;
		}
		
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				p.setVelocity(new Vector());
				d.setVelocity(new Vector());
			}
		}, 1L);
		p.playSound(p.getLocation(), Sound.ANVIL_USE, 15.5F, 15.5F);
		d.playSound(d.getLocation(), Sound.ANVIL_USE, 15.5F, 15.5F);
	}
}
