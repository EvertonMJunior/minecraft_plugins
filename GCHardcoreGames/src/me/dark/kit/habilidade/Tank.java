package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;

public class Tank extends Kit {
	public Tank() {
		super("Tank", Material.TNT, 
				new ItemStack[] { null },
				Arrays.asList(""));
	}	
	
	  @EventHandler
	    public void onEntityDamage(EntityDamageEvent e) {
			if (Main.estado == GameState.PREGAME) return;
	          if(e.getEntity() instanceof Player){
	          if(e.getCause() == DamageCause.ENTITY_EXPLOSION) {
	        	   Player p = (Player) e.getEntity();
	        	   if(hasAbility(p)) {
	        		   e.setCancelled(true);
	        	   }
	          }
	          }
	  }
	  
	  @EventHandler
	  public void tank(PlayerDeathEvent event) {
	    if ((event.getEntity() instanceof Player)) {
	      Player p = event.getEntity();
	      Player matador = p.getKiller();
	       if(matador instanceof Player) {
   	   if(hasAbility(matador)) {
	        p.getWorld().createExplosion(p.getLocation(), 2.0F, false);
   	   }
	       }
	    }
	  }
}