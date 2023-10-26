package me.everton.epvp.Listeners;

import me.everton.epvp.Comandos.Uteis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Principais implements Listener{
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			if(!Uteis.dano){
				e.setCancelled(true);
			}
		}
	}

}
