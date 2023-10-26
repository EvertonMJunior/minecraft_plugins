package me.everton.pvp.kits.habilidades;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Switcher implements Listener{
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Snowball)) {
			return;
		}
		@SuppressWarnings("deprecation")
		Player s = (Player) ((Snowball)e.getDamager()).getShooter();
		if(KitManager.getKit(s) != KitType.SWITCHER) {
			return;
		}
		e.setCancelled(true);
		
		if(!API.getGamers().contains(s)) {
			s.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}
		
		if(e.getEntity() instanceof Player) {
			if(!API.getGamers().contains((Player) e.getEntity())) {
				s.sendMessage("§7[§c!§7] Você não pode usar seu kit contra esse jogador!");
				return;
			}
		}
		
		final Location loc = e.getEntity().getLocation();
		
		e.getEntity().teleport(s.getLocation());
		s.teleport(loc);
	}
}
