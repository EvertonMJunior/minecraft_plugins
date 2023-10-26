package me.dark.kit.habilidade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;

public class Gladiator extends Kit {
	public Gladiator() {
		super("Gladiator", Material.IRON_FENCE, 
				new ItemStack[] { DarkUtils.create_item(Material.IRON_FENCE, "§3Gladiator", 1, 0, null) },
				Arrays.asList(""));
	}
	static ArrayList<String> inPvP = new ArrayList<String>();
	static ArrayList<String> Morreu = new ArrayList<String>();
	public Map<String, Location> local = new HashMap<String, Location>();
	  

	  @EventHandler
	  public void death(PlayerDeathEvent e) {
	    if ((e.getEntity() instanceof Player)) {
	      Player p = e.getEntity();
	      if ((p.getKiller() instanceof Player)) {
	        Player killer = p.getKiller();
	        if (inPvP.contains(p.getName())) {
	    				for (ItemStack s : e.getDrops()){
	    					Bukkit.getServer().getWorld("world").dropItemNaturally(local.get(killer.getName()), s);
	    				}
	    			
	    			e.getDrops().clear();
	          inPvP.remove(p.getName());
	          if (inPvP.contains(killer.getName())) {
	            inPvP.remove(killer.getName());
	          }
	        }
	          if (Morreu.contains(p.getName())) {
	        	  Morreu.remove(p.getName());
	        	  
	          }
	          if (Morreu.contains(killer.getName())) {
	        	  Morreu.remove(killer.getName());
	          }
	      }
	    }
	  }
	  
	  @EventHandler(priority=EventPriority.HIGHEST)
	  public void block(PlayerInteractEvent e) {
	      final Block b = e.getClickedBlock();
		    if ((e.getAction() == Action.LEFT_CLICK_BLOCK) && (b.hasMetadata("Gladiator")) && (e.getClickedBlock().getType() == Material.GLASS)) {
	      b.setType(Material.BEDROCK);
	      Main.getMain().getServer().getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
	 		public void run() {
	 		   if(b.getType() == Material.AIR) {
	 			 b.setType(Material.AIR);
	 		   } else {
	 		   b.setType(Material.GLASS);
	 		  }
	 		}
	 	}, 20L);
	    }
	  }
	  
	  @EventHandler
	  public void colocar(BlockBreakEvent e) {
	    Block b = e.getBlock();
	    if(b.hasMetadata("Gladiator"))
	      e.setCancelled(true);
	    }
	  
	  
	  @EventHandler
	  public void UsarKit(EntityExplodeEvent e) {
		    for (Block b : e.blockList()) {
		  if(b.hasMetadata("Gladiator"))
			      e.setCancelled(true);
		   }
	  }
	  
	  @EventHandler
	  public void colocar(BlockPlaceEvent e) {
	    Player p = e.getPlayer();
	    Block b = e.getBlock();

	    if (inPvP.contains(p.getName())) {
			if(!(b.getType() == Material.FIRE)) {
	      e.setCancelled(true);
	    }
	  }
	  }
	  
	  @EventHandler
	  public void PlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
	    final Player pessoa1 = event.getPlayer();
	    
	    if ((pessoa1.getItemInHand().getType().equals(Material.IRON_FENCE))){
	    	if(hasAbility(pessoa1)) {
	    		event.setCancelled(true);
	    	if(Main.estado != GameState.INVENCIBILITY) {
	    		if (!(pessoa1.getLocation().getBlockX() > Bukkit.getWorld("world").getSpawnLocation().getBlockX() + 490)) {
	    		if (!(pessoa1.getLocation().getBlockX() < -(490 - Bukkit.getWorld("world").getSpawnLocation().getBlockX()))) {
	    		 if (!(pessoa1.getLocation().getBlockZ() > Bukkit.getWorld("world").getSpawnLocation().getBlockZ() + 490)) {
	    		  if (!(pessoa1.getLocation().getBlockZ() < -(490 - Bukkit.getWorld("world").getSpawnLocation().getBlockZ()))) {
	      final Player pessoa2 = (Player)event.getRightClicked();
	      Location pLoc = pessoa1.getLocation();
	      if ((!inPvP.contains(pessoa1.getName())) && (!inPvP.contains(pessoa2.getName()))) {
	        local.put(pessoa1.getName(), pLoc);
	        local.put(pessoa2.getName(), pessoa2.getLocation());
	        
	        Location GladLoc = pessoa1.getLocation();
	        final Location GladFence = new Location(pessoa1.getWorld(), GladLoc.getBlockX(), /*GladLoc.getWorld().getHighestBlockYAt(GladLoc) + 70*/124, GladLoc.getBlockZ());
	        
	        generateArena(GladFence, pessoa2, pessoa1);
	        new BukkitRunnable()  {
	          int tempo = 120;
	          
	          public void run() {
	            tempo -= 1;
	            if (!inPvP.contains(pessoa1.getName())) {
	              inPvP.add(pessoa1.getName());
	            }
	            if (!inPvP.contains(pessoa2.getName())) {
	              inPvP.add(pessoa2.getName());
	            }
	            
	            if(!pessoa1.isOnline()) {
	            	
	                pessoa1.teleport((Location)local.get(pessoa1.getName()));
	                pessoa1.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
	                local.remove(pessoa1.getName());
		              inPvP.remove(pessoa1.getName());

		              
	                pessoa2.teleport((Location)local.get(pessoa2.getName()));
	                pessoa2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
	                local.remove(pessoa2.getName());
		              inPvP.remove(pessoa2.getName());
	                clearArena(GladFence);
		              cancel();
		                }
	            
	            if(!pessoa2.isOnline()) {
	            	
	                pessoa1.teleport((Location)local.get(pessoa1.getName()));
	                pessoa1.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
	                local.remove(pessoa1.getName());
		              inPvP.remove(pessoa1.getName());

		              
	                pessoa2.teleport((Location)local.get(pessoa2.getName()));
	                pessoa2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
	                local.remove(pessoa2.getName());
		              inPvP.remove(pessoa2.getName());
	                clearArena(GladFence);
		              cancel();
		                }
	            
	            
	            if (!Morreu.contains(pessoa1.getName())) {
	              inPvP.remove(pessoa1.getName());
	              inPvP.remove(pessoa2.getName());
	              Morreu.remove(pessoa1.getName());
	              Morreu.remove(pessoa2.getName());
	              clearArena(GladFence);
	              
	              cancel();
	              if (!pessoa1.hasPotionEffect(PotionEffectType.BLINDNESS)) {
	                pessoa1.teleport((Location)local.get(pessoa1.getName()));
	                 pessoa1.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));

	                local.remove(pessoa1.getName());
	                if (pessoa1.hasPotionEffect(PotionEffectType.WITHER)) {
	                  pessoa1.removePotionEffect(PotionEffectType.WITHER);
	                }
	              }
	              if (!pessoa2.hasPotionEffect(PotionEffectType.BLINDNESS)) {
	                pessoa2.teleport((Location)local.get(pessoa2.getName()));
	                pessoa2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
	                local.remove(pessoa2.getName());
	                if (pessoa2.hasPotionEffect(PotionEffectType.WITHER)) {
	                  pessoa2.removePotionEffect(PotionEffectType.WITHER);
	                }
	              }
	            }else{
	              if (tempo == 60) {
	                if ((!pessoa1.isDead()) && (pessoa1.isOnline()) && (inPvP.contains(pessoa1.getName()))) {
	                  pessoa1.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 999999, 4));
	                }
	                if ((!pessoa2.isDead()) && (pessoa2.isOnline()) && (inPvP.contains(pessoa2.getName()))) {
	                  pessoa2.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 999999, 4));
	                }
	              }
	              if (tempo == 0) {
	                inPvP.remove(pessoa1.getName());
	                inPvP.remove(pessoa2.getName());
	                
	                clearArena(GladFence);
	                
	                cancel();
	                if ((!pessoa1.isDead()) && (pessoa1.isOnline())) {
	                  pessoa1.teleport((Location)local.get(pessoa1.getName()));
		                 pessoa1.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));

	                  if (pessoa1.hasPotionEffect(PotionEffectType.WITHER)) {
	                    pessoa1.removePotionEffect(PotionEffectType.WITHER);
	                  }
	                  local.remove(pessoa2);
	                }
	                if ((!pessoa2.isDead()) && (pessoa2.isOnline()))
	                {
	                  pessoa2.teleport((Location)local.get(pessoa2.getName()));
	                  pessoa2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));

	                  if (pessoa2.hasPotionEffect(PotionEffectType.WITHER)) {
	                    pessoa2.removePotionEffect(PotionEffectType.WITHER);
	                  }
	                  local.remove(pessoa2);
	                }
	              }
	            }
	          }
	        }.runTaskTimer(Main.getMain(), 0L, 20L);
	      }
	    }else{
	    	pessoa1.sendMessage("§cVocê está muito perto do forcefield e nÃo pode usar este kit!");
	    }
	    }else{
	    	pessoa1.sendMessage("§cVocê está muito perto do forcefield e nÃo pode usar este kit!");

	    }
	  }else{
	    	pessoa1.sendMessage("§cVocê está muito perto do forcefield e nÃo pode usar este kit!");

	  }
	    		}else{
	    	    	pessoa1.sendMessage("§cVocê está muito perto do forcefield e nÃo pode usar este kit!");

	    		}
	    	}
	    }
	  }
	  }
	  
	  public void clearArena(Location loc) {
	    int x = 0;
	    int y = 0;
	    int z = 0;
	    for (x = -7; x < 7; x++) {
	      for (z = -7; z < 7; z++) {
	        for (y = 0; y < 7; y++) {
	          Block b = loc.clone().add(x, 0.0D, z).getBlock();
	          Block b2 = loc.clone().add(x, 7.0D, z).getBlock();
	          Block b3 = loc.clone().add(-7.0D, y, z).getBlock();
	          Block b4 = loc.clone().add(x, y, -7.0D).getBlock();
	          Block b5 = loc.clone().add(x, y, 7.0D).getBlock();
	          Block b6 = loc.clone().add(7.0D, y, z).getBlock();
	          
	          b.setType(Material.AIR);
	          b2.setType(Material.AIR);
	          b3.setType(Material.AIR);
	          b4.setType(Material.AIR);
	          b5.setType(Material.AIR);
	          b6.setType(Material.AIR);
	          
	          b.removeMetadata("Gladiator", Main.getMain());
	          b2.removeMetadata("Gladiator", Main.getMain());
	          b3.removeMetadata("Gladiator", Main.getMain());
	          b4.removeMetadata("Gladiator", Main.getMain());
	          b5.removeMetadata("Gladiator", Main.getMain());
	          b6.removeMetadata("Gladiator", Main.getMain());

	        }
	      }
	    }
	  }
	  
	  public void generateArena(Location loc, Player gladiator, Player target) {
	    int x = 0;
	    int y = 0;
	    int z = 0;
	    if(!Morreu.contains(gladiator.getName()))
	    Morreu.add(gladiator.getName());
	    if(!Morreu.contains(target.getName()))
	    Morreu.add(target.getName());
	    if (loc.clone().add(x, y, z).getBlock().getType() == Material.AIR) {
	    for (x = -7; x < 7; x++) {
	      for (z = -7; z < 7; z++) {
	        for (y = 0; y < 7; y++) {
	          Block b = loc.clone().add(x, 0.0D, z).getBlock();
	          Block b2 = loc.clone().add(x, 7.0D, z).getBlock();
	          Block b3 = loc.clone().add(-7.0D, y, z).getBlock();
	          Block b4 = loc.clone().add(x, y, -7.0D).getBlock();
	          Block b5 = loc.clone().add(x, y, 7.0D).getBlock();
	          Block b6 = loc.clone().add(7.0D, y, z).getBlock();
	          
	          b.setType(Material.GLASS);
	          b2.setType(Material.AIR);
	          b3.setType(Material.GLASS);
	          b4.setType(Material.GLASS);
	          b5.setType(Material.GLASS);
	          b6.setType(Material.GLASS);
	          
	          b.setMetadata("Gladiator",
	                    new FixedMetadataValue(Main.getMain(), "Gladiator"));
	          b2.setMetadata("Gladiator",
	                    new FixedMetadataValue(Main.getMain(), "Gladiator"));
	          b3.setMetadata("Gladiator",
	                    new FixedMetadataValue(Main.getMain(), "Gladiator"));
	          b4.setMetadata("Gladiator",
	                    new FixedMetadataValue(Main.getMain(), "Gladiator"));
	          b5.setMetadata("Gladiator",
	                    new FixedMetadataValue(Main.getMain(), "Gladiator"));
	          b6.setMetadata("Gladiator",
	                    new FixedMetadataValue(Main.getMain(), "Gladiator"));
	        }
	      }
	    }
	    gladiator.teleport(loc.clone().add(x - 1, y - 2, -4.0D));
	    target.teleport(loc.clone().add(-4.0D, y - 4, z - 1));
	  }
	  }
}
