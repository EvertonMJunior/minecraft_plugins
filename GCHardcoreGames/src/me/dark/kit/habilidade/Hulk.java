package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;

public class Hulk extends Kit{
	public Hulk() {
		super("Hulk", Material.BONE, 
				new ItemStack[] { null },
				Arrays.asList("�7Clique com bot�o direito e pegue seu",
						      "�7advers�rio, facilitando",
						      "�7seu combate contra ele."));
	}

	@EventHandler
	  public void hulk(PlayerInteractEntityEvent e) {
		if (Main.estado == GameState.PREGAME) return;
	    Player p = e.getPlayer();
	    if ((e.getRightClicked() instanceof Player)) {
	      Player clicked = (Player)e.getRightClicked();
	      if (clicked.getVehicle() == p) {
	    	  clicked.eject();
	    	  clicked.setVelocity(p.getEyeLocation().getDirection().multiply(1.3));
	      }
	      if ((!p.isInsideVehicle()) && (!clicked.isInsideVehicle()) && 
	        (p.getItemInHand().getType() == Material.AIR) && 
	        (hasAbility(p))){
	        if (!hasCooldown(p)) {
	        	addCooldown(p, 10);
	          p.setPassenger(clicked);
	        } else {
	        	mensagemcooldown(p);
	        }
	      }
	    }
	  }
	}
