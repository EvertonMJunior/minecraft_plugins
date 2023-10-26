package me.everton.epvp.Kits.Habilidades;

import java.util.ArrayList;

import me.everton.epvp.Main;
import me.everton.epvp.Listeners.OnMove;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

public class Stomper implements Listener{
	public static ArrayList<String> stomp = new ArrayList<>();
	
	@EventHandler(priority=EventPriority.HIGH)
	  public void onPlayerStomp(EntityDamageEvent e)
	  {
	    if (!(e.getEntity() instanceof Player)) {
	      return;
	    }
	    if(e.isCancelled()) {
	    	return;
	    }
	    if (e.getCause() == DamageCause.FALL)
	    {
			Player p = (Player) e.getEntity();
			if(OnMove.jump.contains(p.getName())) {
				return;
			}
		      if (Main.stomper.contains(p.getName()))
		      {
		    	  stomp.remove(p.getName());
		        for (Entity ent : p.getNearbyEntities(5.0D, 2.0D, 5.0D)) {
		          if ((ent instanceof Player))
		          {
		            Player plr = (Player)ent;
		            if (e.getDamage() <= 4.0D)
		            {
		              e.setCancelled(true);
		              return;
		            }
		            if (plr.isSneaking())
		            {
		              plr.damage(6.0D, p);
		              plr.sendMessage("§7[§c!§7] Você foi stompado por " + ChatColor.stripColor(p.getDisplayName()) + "!");
		            }
		            else
		            {
		              plr.damage(e.getDamage(), p);
		              plr.sendMessage("§7[§c!§7] Você foi stompado por " + ChatColor.stripColor(p.getDisplayName()) + "!");
		            }
		          }
		        }
		        e.setDamage(4.0D);
		        return;
		      }
		      return;
		    }
	  }
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(Main.stomper.contains(p.getName())) {
			if(!p.isOnGround()) {
				if(!stomp.contains(p.getName())) {
					stomp.add(p.getName());
				}
			}
		}
	}
}
