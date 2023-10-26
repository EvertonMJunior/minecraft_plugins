package me.dark.kit.habilidade;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Utils.CopyOfFishingHook;
import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;

public class Grappler extends Kit{
	public Grappler() {
		super("Grappler", Material.LEASH, 
				new ItemStack[] { DarkUtils.create_item(Material.LEASH, "§3Grappler", 1, 0, null) },
				Arrays.asList(""));
	}
	Map<Player, CopyOfFishingHook> hooks = new HashMap<Player, CopyOfFishingHook>();
	
	@EventHandler
	public void nerf(EntityDamageByEntityEvent e) {
		if (Main.estado == GameState.PREGAME) return;
		if (e.getEntity() instanceof Player) {
			final Player p = (Player) e.getEntity();
			if (hasAbility(p)) {
				addCooldown(p, 5);
			}
		}
	}
	  @EventHandler
	  public void onSlot(PlayerItemHeldEvent e) {
	    if (this.hooks.containsKey(e.getPlayer())) {
	      ((CopyOfFishingHook)this.hooks.get(e.getPlayer())).remove();
	      this.hooks.remove(e.getPlayer());
	    }
	  }

	  @EventHandler
	  public void grapplerDamageNoLeash(EntityDamageEvent event) {
			if (Main.estado == GameState.PREGAME) return;
	    if (!(event.getEntity() instanceof Player)) {
	      return;
	    }
	    Player player = (Player)event.getEntity();
	    if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
	      return;
	    }
	    if ((this.hooks.containsKey(player)) && 
	      (((CopyOfFishingHook)this.hooks.get(player)).isHooked()) && 
	      (event.getDamage() > 3.0D))
	      event.setDamage(3.0D);
	  }

	  @EventHandler
	  public void onMove(PlayerMoveEvent e){
	    if ((this.hooks.containsKey(e.getPlayer())) && (!e.getPlayer().getItemInHand().getType().equals(Material.LEASH) && hasAbility(e.getPlayer()))){
	      ((CopyOfFishingHook)this.hooks.get(e.getPlayer())).remove();
	      this.hooks.remove(e.getPlayer());
	    }
	  }

	@EventHandler
	  public void onLeash(PlayerLeashEntityEvent e){
	    Player p = e.getPlayer();
	    if (e.getPlayer().getItemInHand().getType().equals(Material.LEASH) && hasAbility(e.getPlayer())) {
	      e.setCancelled(true);
	      e.getPlayer().updateInventory();
	      e.setCancelled(true);
	      if (!this.hooks.containsKey(p)) {
	        return;
	      }
	      if (!((CopyOfFishingHook)this.hooks.get(p)).isHooked()) {
	        return;
	      }
	      
	      if (hasCooldown(p)) {
	    	  mensagemcooldown(p);
	      }
	      double d = ((CopyOfFishingHook)this.hooks.get(p)).getBukkitEntity().getLocation().distance(p.getLocation());
	      double t = d;
	      double v_x = (1.0D + 0.07000000000000001D * t) * (((CopyOfFishingHook)this.hooks.get(p)).getBukkitEntity().getLocation().getX() - p.getLocation().getX()) / t;
	      double v_y = (1.0D + 0.03D * t) * (((CopyOfFishingHook)this.hooks.get(p)).getBukkitEntity().getLocation().getY() - p.getLocation().getY()) / t;
	      double v_z = (1.0D + 0.07000000000000001D * t) * (((CopyOfFishingHook)this.hooks.get(p)).getBukkitEntity().getLocation().getZ() - p.getLocation().getZ()) / t;
	      Vector v = p.getVelocity();
	      v.setX(v_x);
	      v.setY(v_y);
	      v.setZ(v_z);
	      p.setVelocity(v);

	      p.getWorld().playSound(p.getLocation(), Sound.STEP_GRAVEL, 1.0F, 1.0F);
	    }
	  }

	  @EventHandler
	  public void onClick(PlayerInteractEvent e) {
	    Player p = e.getPlayer();
	    if (e.getPlayer().getItemInHand().getType().equals(Material.LEASH) && hasAbility(e.getPlayer())){
	      e.setCancelled(true);
	      if ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)){
		      if (hasCooldown(p)) {
		    	  mensagemcooldown(p);
		      }
	        if (this.hooks.containsKey(p)) {
	          ((CopyOfFishingHook)this.hooks.get(p)).remove();
	        }
	        CopyOfFishingHook nmsHook = new CopyOfFishingHook(p.getWorld(), ((CraftPlayer)p).getHandle());
	        nmsHook.spawn(p.getEyeLocation().add(p.getLocation().getDirection().getX(), p.getLocation().getDirection().getY(), p.getLocation().getDirection().getZ()));
	        nmsHook.move(p.getLocation().getDirection().getX() * 5.0D, p.getLocation().getDirection().getY() * 5.0D, p.getLocation().getDirection().getZ() * 5.0D);
	        this.hooks.put(p, nmsHook);
	      }else{
	        if (!this.hooks.containsKey(p)) {
	          return;
	        }
	        if (!((CopyOfFishingHook)this.hooks.get(p)).isHooked()) {
	          return;
	        }
		      if (hasCooldown(p)) {
		    	  mensagemcooldown(p);
		      }
	        double d = ((CopyOfFishingHook)this.hooks.get(p)).getBukkitEntity().getLocation().distance(p.getLocation());
	        double t = d;
	        double v_x = (1.0D + 0.2D * t) * (((CopyOfFishingHook)this.hooks.get(p)).getBukkitEntity().getLocation().getX() - p.getLocation().getX()) / t;
	        double v_y = (1.0D + 0.03D * t) * (((CopyOfFishingHook)this.hooks.get(p)).getBukkitEntity().getLocation().getY() - p.getLocation().getY()) / t;
	        double v_z = (1.0D + 0.2D * t) * (((CopyOfFishingHook)this.hooks.get(p)).getBukkitEntity().getLocation().getZ() - p.getLocation().getZ()) / t;

	        Vector v = p.getVelocity();
	        v.setX(v_x);
	        v.setY(v_y);
	        v.setZ(v_z);
	        p.setVelocity(v);

	        p.getWorld().playSound(p.getLocation(), Sound.STEP_GRAVEL, 1.0F, 1.0F);
	      }
	    }
	  }
}
