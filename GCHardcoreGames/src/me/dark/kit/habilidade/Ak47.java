package me.dark.kit.habilidade;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;

public class Ak47 extends Kit{
	public Ak47() {
		super("Ak47", Material.IRON_BARDING, 
				new ItemStack[] { DarkUtils.create_item(Material.IRON_BARDING, "§3Ak-47", 1, 0, null) },
				Arrays.asList(""));
	}
	@EventHandler
	public void inte(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (hasAbility(p)) {
			if (p.getItemInHand() != null) {
				if (p.getItemInHand().getType() == Material.IRON_BARDING) {
					if (Main.estado == GameState.PREGAME) return;
					e.setCancelled(true);
					if (hasCooldown(p)) {
						mensagemcooldown(p);
					} else {
						addCooldown(p, 40);
						p.setVelocity(p.getLocation().getDirection().multiply(-0.8));
		                BlockIterator blocksToAdd = new BlockIterator(p.getEyeLocation(), 0.0, 40);
		                Location blockToAdd;   
		                while(blocksToAdd.hasNext()) {
		                	 blockToAdd = blocksToAdd.next().getLocation();
		                     p.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.REDSTONE_BLOCK, 40);
		 
		                        double radius = 1.5D;
		                        List<Entity> near = p.getEyeLocation().getWorld().getEntities();
		 
		                        for(Entity ee : near) {
		                            if(ee.getLocation().distance(blockToAdd) <= radius && ee != p){
		                            	ee.setFireTicks(5*20);
		                            	if (ee instanceof Player) {
		                            		((Player) ee).damage(7.0D,p);
		                            	}
		                            }
		                        }
		                }
					}
				}
			}
		}
	}

}
