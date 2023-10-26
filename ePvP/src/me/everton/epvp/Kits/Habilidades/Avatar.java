package me.everton.epvp.Kits.Habilidades;

import java.util.ArrayList;

import me.everton.epvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Avatar implements Listener{
	public static ArrayList<String> Ferro2 = new ArrayList<>();
	public static ArrayList<String> Red2 = new ArrayList<>();
	public static ArrayList<String> Terra2 = new ArrayList<>();
	public static ArrayList<String> cd = new ArrayList<>();
	public static ArrayList<String> A = new ArrayList<>();
	
	@EventHandler
	  public void Trocar(PlayerInteractEvent e)
	  {
	    Player p = e.getPlayer();
	    String b = ChatColor.BOLD + "";
	    
	    if(Main.avatar.contains(p.getName())) {
	    	ItemStack Terra = new ItemStack(Material.GRASS);
		    ItemMeta terram = Terra.getItemMeta();
		    terram.setDisplayName(ChatColor.DARK_GREEN + b + "Terra");
		    Terra.setItemMeta(terram);
		    
		    ItemStack Agua = new ItemStack(Material.LAPIS_BLOCK);
		    ItemMeta Aguam = Agua.getItemMeta();
		    Aguam.setDisplayName(ChatColor.DARK_AQUA + b + "Agua");
		    Agua.setItemMeta(Aguam);
		    
		    ItemStack Aguac = new ItemStack(Material.BEACON);
		    ItemMeta Aguacm = Aguac.getItemMeta();
		    Aguacm.setDisplayName(ChatColor.DARK_AQUA + b + "B");
		    Aguac.setItemMeta(Aguacm);
		    
		    ItemStack AR = new ItemStack(Material.WOOL);
		    ItemMeta ARm = AR.getItemMeta();
		    ARm.setDisplayName(b + "AR");
		    AR.setItemMeta(ARm);
		    

		    ItemStack Fogo = new ItemStack(Material.REDSTONE_BLOCK);
		    ItemMeta Fogom = Fogo.getItemMeta();
		    Fogom.setDisplayName(ChatColor.RED + b + "Fogo");
		    Fogo.setItemMeta(Fogom);
		    if (((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) && 
		      (p.getItemInHand().getType() == Material.BEACON))
		    {
		      p.setItemInHand(AR);
		      Ferro2.add(p.getName());
		    }
		    else if ((Ferro2.contains(p.getName())) && ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) && 
		      (p.getItemInHand().equals(AR)))
		    {
		      p.setItemInHand(Agua);
		      Ferro2.remove(p.getName());
		      A.add(p.getName());
		    }
		    else if ((A.contains(p.getName())) && ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) && 
		      (p.getItemInHand().equals(Agua)))
		    {
		      p.setItemInHand(Terra);
		      A.remove(p.getName());
		      Terra2.add(p.getName());
		    }
		    else if ((Terra2.contains(p.getName())) && (Terra2.contains(p.getName())) && ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) && 
		      (p.getItemInHand().equals(Terra)))
		    {
		      p.setItemInHand(Fogo);
		      Terra2.remove(p.getName());
		      Red2.add(p.getName());
		    }
		    else if ((Red2.contains(p.getName())) && ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) && 
		      (p.getItemInHand().equals(Fogo)))
		    {
		      p.setItemInHand(AR);
		      Red2.remove(p.getName());
		      Ferro2.add(p.getName());
		    }
	    }
	  }

	  public int id1;
	  
	  @EventHandler
	  public void AvatarAr(PlayerInteractEvent e)
	  {
	    final Player p = e.getPlayer();
	    if(Main.avatar.contains(p.getName())) {
	    	
	    	//AR
	    	
	    if ((Ferro2.contains(p.getName())) && ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
	      (p.getItemInHand().getType() == Material.WOOL))
	    {
	      e.setCancelled(true);
	      if (!cd.contains(p.getName()))
	      {
	        cd.add(p.getName());
	        
	        Vector Ferro = p.getLocation().getDirection().normalize().multiply(55);
	        Snowball FerroH = (Snowball)p.launchProjectile(Snowball.class);
	        FerroH.setVelocity(Ferro);
	        FerroH.setMetadata("Ar", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
	        
	        Location pegou = p.getEyeLocation();
	        
	        BlockIterator Ferrao = new BlockIterator(pegou, 0.0D, 40);
	        while (Ferrao.hasNext())
	        {
	          Location Ferrao2 = Ferrao.next().getLocation();
	          
	          Effect camelo = Effect.STEP_SOUND;
	          p.playSound(p.getLocation(), Sound.STEP_WOOL, 5.5F, 5.5F);
	          p.playSound(p.getLocation(), Sound.STEP_WOOL, 1.5F, 1.5F);
	          p.playSound(p.getLocation(), Sound.STEP_WOOL, 2.5F, 2.5F);
	          p.playSound(p.getLocation(), Sound.STEP_WOOL, 3.5F, 3.5F);
	          p.playSound(p.getLocation(), Sound.STEP_WOOL, 4.5F, 4.5F);
	          
	          p.getWorld().playEffect(Ferrao2, camelo, 35);
	        }
	        id1 = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
	        {
	          public void run()
	          {
	            if (cd.contains(p.getName()))
	            {
	              cd.remove(p.getName());
	              p.sendMessage("§7[§a!§7] O cooldown acabou!");
	              p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
	            }
	          }
	        }, 600L);
	      }
	      else
	      {
	    	  p.sendMessage("§7[§c!§7] Kit em cooldown!");
	      }
	    }
	  }
	   
	    
	    //AGUA
	    
	    if ((Main.avatar.contains(p.getName())) && ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
	  	      (p.getItemInHand().getType() == Material.LAPIS_BLOCK))
	  	    {
	  	      e.setCancelled(true);
	  	      if (!cd.contains(p.getName()))
	  	      {
	  	        cd.add(p.getName());
	  	        
	  	        Vector Agua = p.getLocation().getDirection().normalize().multiply(55);
	  	        Snowball AguaH = (Snowball)p.launchProjectile(Snowball.class);
	  	        AguaH.setVelocity(Agua);
	  	        AguaH.setMetadata("Agua", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
	  	        
	  	        Location pegou = p.getEyeLocation();
	  	        
	  	        BlockIterator Aguao = new BlockIterator(pegou, 0.0D, 40);
	  	        while (Aguao.hasNext())
	  	        {
	  	          Location Aguao2 = Aguao.next().getLocation();
	  	          
	  	          Effect camelo = Effect.STEP_SOUND;
	  	          p.playSound(p.getLocation(), Sound.STEP_WOOD, 5.5F, 5.5F);
	  	          p.playSound(p.getLocation(), Sound.STEP_WOOD, 1.5F, 1.5F);
	  	          p.playSound(p.getLocation(), Sound.STEP_WOOD, 2.5F, 2.5F);
	  	          p.playSound(p.getLocation(), Sound.STEP_WOOD, 3.5F, 3.5F);
	  	          p.playSound(p.getLocation(), Sound.STEP_WOOD, 4.5F, 4.5F);
	  	          
	  	          p.getWorld().playEffect(Aguao2, camelo, 9);
	  	        }
	  	        id1 = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
	  	        {
	  	          public void run()
	  	          {
	  	            if (cd.contains(p.getName()))
	  	            {
	  	              cd.remove(p.getName());
	  	            p.sendMessage("§7[§a!§7] O cooldown acabou!");
	  	              p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
	  	            }
	  	          }
	  	        }, 600L);
	  	      }
	  	      else
	  	      {
	  	    	p.sendMessage("§7[§c!§7] Kit em cooldown!");
	  	      }
	  	    }
	    
	    //TERRA
	    
	    if ((Terra2.contains(p.getName())) && ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
	    	      (p.getItemInHand().getType() == Material.GRASS))
	    	    {
	    	      e.setCancelled(true);
	    	      if (!cd.contains(p.getName()))
	    	      {
	    	        cd.add(p.getName());
	    	        Vector Terra = p.getLocation().getDirection().normalize().multiply(55);
	    	        Snowball TerraH = (Snowball)p.launchProjectile(Snowball.class);
	    	        TerraH.setVelocity(Terra);
	    	        TerraH.setMetadata("Terra", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
	    	        
	    	        Location pegou = p.getEyeLocation();
	    	        
	    	        BlockIterator Terrao = new BlockIterator(pegou, 0.0D, 40);
	    	        while (Terrao.hasNext())
	    	        {
	    	          Location Terrao2 = Terrao.next().getLocation();
	    	          
	    	          Effect camelo = Effect.STEP_SOUND;

	    	          p.playSound(p.getLocation(), Sound.STEP_GRASS, 5.5F, 5.5F);
	    	          p.playSound(p.getLocation(), Sound.STEP_GRASS, 1.5F, 1.5F);
	    	          p.playSound(p.getLocation(), Sound.STEP_GRASS, 2.5F, 2.5F);
	    	          p.playSound(p.getLocation(), Sound.STEP_GRASS, 3.5F, 3.5F);
	    	          p.playSound(p.getLocation(), Sound.STEP_GRASS, 4.5F, 4.5F);
	    	          
	    	          p.getWorld().playEffect(Terrao2, camelo, 2);
	    	        }
	    	        this.id1 = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
	    	        {
	    	          public void run()
	    	          {
	    	            if (cd.contains(p.getName()))
	    	            {
	    	              cd.remove(p.getName());
	    	              p.sendMessage("§7[§a!§7] O cooldown acabou!");
	    	              p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
	    	            }
	    	          }
	    	        }, 600L);
	    	      }
	    	      else
	    	      {
	    	    	  p.sendMessage("§7[§c!§7] Kit em cooldown!");
	    	      }
	    	    }
	    
	    //FOGO
	    
	    if ((Red2.contains(p.getName())) && ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
	    	      (p.getItemInHand().getType() == Material.REDSTONE_BLOCK))
	    	    {
	    	      e.setCancelled(true);
	    	      if (!cd.contains(p.getName()))
	    	      {
	    	        cd.add(p.getName());
	    	        
	    	        Vector Fogo = p.getLocation().getDirection().normalize().multiply(55);
	    	        Snowball FogoH = (Snowball)p.launchProjectile(Snowball.class);
	    	        FogoH.setVelocity(Fogo);
	    	        FogoH.setMetadata("Fogo", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
	    	        
	    	        Location pegou = p.getEyeLocation();
	    	        
	    	        BlockIterator Fogao = new BlockIterator(pegou, 0.0D, 40);
	    	        while (Fogao.hasNext())
	    	        {
	    	          Location Fogao2 = Fogao.next().getLocation();
	    	          
	    	          Effect camelo = Effect.STEP_SOUND;
	    	          p.playSound(p.getLocation(), Sound.FIRE, 5.5F, 5.5F);
	    	          p.playSound(p.getLocation(), Sound.FIRE, 1.5F, 1.5F);
	    	          p.playSound(p.getLocation(), Sound.FIRE, 2.5F, 2.5F);
	    	          p.playSound(p.getLocation(), Sound.FIRE, 3.5F, 3.5F);
	    	          p.playSound(p.getLocation(), Sound.FIRE, 4.5F, 4.5F);
	    	          
	    	          p.getWorld().playEffect(Fogao2, camelo, 10);
	    	        }
	    	        this.id1 = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
	    	        {
	    	          public void run()
	    	          {
	    	            if (cd.contains(p.getName()))
	    	            {
	    	              cd.remove(p.getName());
	    	              p.sendMessage("§7[§a!§7] O cooldown acabou!");
	    	              p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
	    	            }
	    	          }
	    	        }, 600L);
	    	      }
	    	      else
	    	      {
	    	    	  p.sendMessage("§7[§c!§7] Kit em cooldown!");
	    	      }
	    	    }
	  }
	  
	@EventHandler
	  public void EntityDamageEvent(EntityDamageByEntityEvent e)
	  {
		  if(e.isCancelled()) {
			  return;
		  }
	    Entity Tomou2 = e.getEntity();
	    if ((e.getDamager() instanceof Snowball))
	    {
	      Snowball Tomou = (Snowball)e.getDamager();
	      if (Tomou.hasMetadata("Ar"))
	      {
	        e.setDamage(16.0D);
	        ((LivingEntity) Tomou2).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0), true);
	      }
	    }
	    
	    if ((e.getDamager() instanceof Snowball))
	    {
	      Snowball Tomou = (Snowball)e.getDamager();
	      if (Tomou.hasMetadata("Agua"))
	      {
	        e.setDamage(11.0D);
	        ((LivingEntity)Tomou2).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 0), true);
	        Vector vector = Tomou2.getLocation().getDirection();
	        vector.multiply(-1.0F);
	        Tomou2.setVelocity(vector);
	      }
	    }
	    
	    if ((e.getDamager() instanceof Snowball))
	    {
	      Snowball Tomou = (Snowball)e.getDamager();
	      if (Tomou.hasMetadata("Terra"))
	      {
	        e.setDamage(14.0D);
	        
	        Vector vector = Tomou2.getLocation().getDirection();
	        vector.multiply(-3.2F);
	        Tomou2.setVelocity(vector);
	      }
	    }
	    
	    if ((e.getDamager() instanceof Snowball))
	    {
	      Snowball Tomou = (Snowball)e.getDamager();
	      if (Tomou.hasMetadata("Fogo"))
	      {
	        e.setDamage(12.0D);
	        Tomou2.setFireTicks(150);
	      }
	    }
	  }
}
