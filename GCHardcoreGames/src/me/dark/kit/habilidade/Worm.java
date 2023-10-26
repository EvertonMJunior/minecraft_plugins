package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;

public class Worm extends Kit{
	public Worm() {
		super("Worm", Material.DIRT, 
				new ItemStack[] { null },
				Arrays.asList(""));
	}	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(BlockDamageEvent event) {
		if (Main.estado == GameState.PREGAME) return;
		Player p = event.getPlayer();
		if (hasAbility(p)
				&& (Main.estado != GameState.PREGAME)
				&& (event.getBlock().getType() == Material.DIRT)) {
			boolean drop = true;
			if (((CraftPlayer) p).getHealth() < 20.0D) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, 1));
				drop = false;
			} else if (p.getFoodLevel() < 20) {
				p.setFoodLevel(p.getFoodLevel() + 1);
				drop = false;
			}
			event.getBlock().getWorld().playEffect(event.getBlock().getLocation(),Effect.STEP_SOUND, Material.DIRT.getId());
			event.getBlock().setType(Material.AIR);
			if (drop)
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.0D, 0.5D),new ItemStack(Material.DIRT));
		}
	}
}