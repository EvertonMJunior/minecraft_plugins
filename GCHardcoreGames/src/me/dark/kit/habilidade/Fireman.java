package me.dark.kit.habilidade;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;


public class Fireman extends Kit {
	public Fireman() {
		super("Fireman", Material.WATER_BUCKET, 
				new ItemStack[] { DarkUtils.create_item(Material.WATER_BUCKET, "§3Fireman", 1, 0, null) },
				Arrays.asList(""));
	}
	
	@EventHandler
	public void onFireman(EntityDamageEvent e) {
		if (Main.estado == GameState.PREGAME) return;
		if(e.getEntity() instanceof Player) {
			if(hasAbility((Player) e.getEntity())) {
				if(e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.LAVA) {
					e.setCancelled(true);
				}
			}
		}
	}
}