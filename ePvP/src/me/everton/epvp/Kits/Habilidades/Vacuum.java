package me.everton.epvp.Kits.Habilidades;

import java.util.ArrayList;

import me.everton.epvp.Main;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Vacuum implements Listener{
	public ArrayList<String> kiton = new ArrayList<>();
	public ArrayList<String> cd = new ArrayList<>();
	
	@EventHandler
	public void onLaunchProj(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if((p.getItemInHand().getType() == Material.EYE_OF_ENDER) && (e.getAction().name().contains("RIGHT"))) {
				if(Main.vacuum.contains(p.getName())) {
					e.setCancelled(true);
					
					if(cd.contains(p.getName())) {
						p.sendMessage("§7[§c!§7] Kit em cooldown!");
						return;
					}
					if(!kiton.contains(p.getName())) {
						kiton.add(p.getName());
						Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
							
							@Override
							public void run() {
								if(kiton.contains(p.getName())) {
									kiton.remove(p.getName());
									cd.add(p.getName());
									Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
										
										@Override
										public void run() {
											if(cd.contains(p.getName())) {
												cd.remove(p.getName());
												p.sendMessage("§7[§c!§7] O cooldown acabou!");
												p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
											}
										}
									}, 15 * 20L);
								}
							}
						}, 5 * 20L);
					}
					for(Entity ent : p.getNearbyEntities(15.0D, 15.0D, 15.0D)) {
						double d = p.getLocation().distance(ent.getLocation());
						      double t = d;
						      double v_x = (1.0D + 0.03000000000000001D * t) * (p.getLocation().getX() - ent.getLocation().getX()) / t;
						      
						      double v_z = (1.0D + 0.03000000000000001D * t) * (p.getLocation().getZ() - ent.getLocation().getZ()) / t;
						      if (ent.isOnGround())
						      {
						    	  p.getLocation().getY();ent.getLocation().getY();
						      }
						      double v_y;
						      if (p.getLocation().getY() - ent.getLocation().getY() < 0.5D) {
						        v_y = ent.getVelocity().getY();
						      } else {
						        v_y = (0.9D + 0.03D * t) * (
						          (p.getLocation().getY() - ent.getLocation()
						          .getY()) / t);
						      }
						      Vector v = ent.getVelocity();
						      v.setX(v_x);
						      v.setY(v_y);
						      v.setZ(v_z);
						      ent.setVelocity(v);
					}
				}
		}
	}
}
