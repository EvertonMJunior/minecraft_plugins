package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Listener.GamerDamage;
import me.dark.kit.Kit;

public class Boxer extends Kit {
	public Boxer() {
		super("Boxer", Material.STONE_SWORD, 
				new ItemStack[] { null },
				Arrays.asList(""));
	}	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (Main.estado == GameState.PREGAME) return;
		if (((e.getDamager() instanceof Player)) && ((e.getEntity() instanceof LivingEntity))) {
			Player p = (Player)e.getDamager();
			if (e.getDamager() instanceof Player && hasAbility(p) && e.getDamage() == 1)
				e.setDamage(GamerDamage.stone);
			if (e.getEntity() instanceof Player && hasAbility(p) && e.getDamage() > 0)
				e.setDamage(e.getFinalDamage() - 1);
          	}
    }
}
