package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;

public class Stomper extends Kit{
	public Stomper() {
		super("Stomper", Material.IRON_BOOTS, 
				new ItemStack[] { null },
				Arrays.asList("§7Pule de grandes alturas em cima do seu",
					          "§7adversário fazendo com que ele morra!"));
	}	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void stomper(EntityDamageEvent e) {
		if (Main.estado == GameState.PREGAME) return;
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (e.getCause() == DamageCause.FALL && e.getEntity() instanceof Player && hasAbility(p)) {
			final double dmg = e.getDamage();
			if(dmg > 4D){
				e.setDamage(4D);
			}
			for(Entity nearby : p.getNearbyEntities(5, 5, 5)){
				if(nearby instanceof Player){
					Player nearbyPlayer = (Player) nearby;
					if(nearbyPlayer.isSneaking()){
						nearbyPlayer.damage(3D, p);
					} else {
						nearbyPlayer.damage(dmg, p);
                  }
              }
          }
	  }
	}
}