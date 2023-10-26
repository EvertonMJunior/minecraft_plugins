package me.everton.epvp.Kits.Habilidades;

import java.util.ArrayList;

import me.everton.epvp.Main;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Sniper implements Listener{
	public static ArrayList<String> cd = new ArrayList<>();
	public static ArrayList<String> zoom = new ArrayList<>();
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(Main.sniper.contains(p.getName())) {
		if(e.getAction().name().contains("RIGHT")) {
	    	if(p.getItemInHand().getType() == Material.BLAZE_ROD) {
	    		if(cd.contains(p.getName())) {
					p.sendMessage("§7[§c!§7] Kit em cooldown!");
					return;
				}
	    		cd.add(p.getName());
	    		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					
					@Override
					public void run() {
						p.sendMessage("§7[§a!§7] O cooldown acabou!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
						cd.remove(p.getName());
					}
				}, 5 * 20L);
	    		if(zoom.contains(p.getName())) {
	    			p.removePotionEffect(PotionEffectType.SLOW);
	    			zoom.remove(p.getName());
	    		}
		    	Vector Sniper = p.getLocation().getDirection().normalize().multiply(100);
	  	        Arrow SniperH = (Arrow)p.launchProjectile(Arrow.class);
	  	        SniperH.setVelocity(Sniper);
	  	        SniperH.setMetadata("Sniper", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
	  	        
	  	        Location pegou = p.getEyeLocation();
		        BlockIterator Sniperz = new BlockIterator(pegou, 0.0D, 100);
		        
		        while (Sniperz.hasNext())
	  	        {
	  	          Location Sniper2 = Sniperz.next().getLocation();
	  	          
	  	          Effect efeito = Effect.VOID_FOG;
	  	          p.getLocation().getWorld().playEffect(Sniper2, Effect.STEP_SOUND, 55);
	  	          p.getWorld().playSound(p.getLocation(), Sound.DIG_GRASS, 1F, 1F);
	  	        }
		 
	    	}} else if(e.getAction().name().contains("LEFT")) {
	    		if(p.getItemInHand().getType() == Material.BLAZE_ROD) {
	    			if(zoom.contains(p.getName())) {
		    			p.removePotionEffect(PotionEffectType.SLOW);
		    			zoom.remove(p.getName());
		    			p.playSound(p.getLocation(), Sound.CLICK, 3F, 3F);
		    		} else {
		    			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 255));
		    			zoom.add(p.getName());
		    			p.playSound(p.getLocation(), Sound.CLICK, 3F, 3F);
		    		}
	    		}
	    	}
	    }
	}
	
	@EventHandler
	public void zoomCheckMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(zoom.contains(p.getName())) {
			if(p.getItemInHand().getType() != Material.BLAZE_ROD) {
				zoom.remove(p.getName());
				p.playSound(p.getLocation(), Sound.CLICK, 3F, 3F);
				p.removePotionEffect(PotionEffectType.SLOW);
			}
		}
	}
	
	@EventHandler
	public void zoomCheckHeld(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		if(zoom.contains(p.getName())) {
			if(p.getInventory().getItem(e.getNewSlot()).getType() != Material.BLAZE_ROD) {
				zoom.remove(p.getName());
				p.playSound(p.getLocation(), Sound.CLICK, 3F, 3F);
				p.removePotionEffect(PotionEffectType.SLOW);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void EDE(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if ((e.getDamager() instanceof Arrow))
	    {
	      Arrow Tomou = (Arrow)e.getDamager();
	      if (Tomou.hasMetadata("Sniper"))
	      {
	    	if(e.getEntity() instanceof Player) {
	    		Player p = (Player) e.getEntity();
	    		Player s = (Player) Tomou.getShooter();
	    		p.sendMessage("§7[§c!§7] Fosses atingido por um tiro de " + s.getName());
	    	}
	        e.setDamage(7.0D);
	      }
	    }
	}
}
