package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;


public class Anchor extends Kit{
	public Anchor() {
		super("Anchor", Material.ANVIL, 
				new ItemStack[] { null },
				Arrays.asList("§7O Kit Anchor faz tanto seu adversário quanto",
						      "§7você não levarem repulsão/knockback!"));
	}
	 @EventHandler
	  public void Damage(final EntityDamageByEntityEvent e) {
	    if ((e.getEntity() instanceof Player)) {
			if (Main.estado == GameState.PREGAME) return;
	      final Player p = (Player)e.getEntity();
	      if (!(e.getDamager() instanceof Player)) {
		      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
			        public void run() {
				          p.setVelocity(new Vector());
			        }
			      });
	    	  return;
	      }
	      final Player d = (Player) e.getDamager();
	      if ((hasAbility(p)) || hasAbility(d)) {
	      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
		        public void run() {
			          p.setVelocity(new Vector());
			          d.playSound(e.getDamager().getLocation(), Sound.ANVIL_LAND, 1, 1);
		        }
		      });
	      }
	    }
	 }

}
