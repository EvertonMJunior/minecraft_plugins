package me.dark.kit.habilidade;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;

public class Cannibal extends Kit {
	public Cannibal() {
		super("Cannibal", Material.ROTTEN_FLESH, 
				new ItemStack[] { null },
				Arrays.asList(""));
	}
	@EventHandler
	public void evenenandoOCara(EntityDamageByEntityEvent event) {
		if (Main.estado == GameState.PREGAME) return;
		if (event.isCancelled()) {
			return;
		}
		if (((event.getDamager() instanceof Player))
				&& ((event.getEntity() instanceof LivingEntity))) {
			LivingEntity entity = (LivingEntity) event.getEntity();
			Player p = (Player) event.getDamager();
			if (hasAbility(p)
					&& (new Random().nextInt(3) == 1)) {
				entity.addPotionEffect(new PotionEffect(
						PotionEffectType.HUNGER, 5 * 20, 0), true);
			}
		}
	}

	
}
