package me.dark.kit.habilidade;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;

public class Snail extends Kit {
	public Snail() {
		super("Snail", Material.SOUL_SAND, 
				new ItemStack[] { null },
				Arrays.asList("§7Dê Hit em seu adversário, fazendo",
						      "§7com que ele fique lento!"));
	}
	@EventHandler
	public void onViper(EntityDamageByEntityEvent e) {
		if (Main.estado == GameState.PREGAME) return;
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			if (hasAbility((Player) e.getDamager())) {
				Random rand = new Random();
				int r = rand.nextInt(100);
				if (r < 33) {
					((Player)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5*20, 0));
				}
			}
		}
	}

}
