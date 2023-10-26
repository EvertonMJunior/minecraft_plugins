package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;


public class Poseidon extends Kit {
	public Poseidon() {
		super("Poseidon", Material.WATER, 
				new ItemStack[] { null },
				Arrays.asList(""));
	}
	@EventHandler
	public void poseidon(PlayerMoveEvent event) {
		if (Main.estado == GameState.PREGAME) return;
		Player p = event.getPlayer();
	    Block b = p.getLocation().getBlock();
	    if ((b.getType().name().contains("WATER") || b.getRelative(BlockFace.UP).getType().name().contains("WATER")) && hasAbility(p)) {
	    	p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0), true);
	        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0), true);
	        }
	    }
}
