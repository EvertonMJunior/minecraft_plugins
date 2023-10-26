package me.everton.WocPvP.Kits.Habilidades;

import me.everton.WocPvP.KitManager;
import me.everton.WocPvP.Main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class Kangaroo
  implements Listener,CommandExecutor
{
  public static ArrayList<Player> kangaroo = new ArrayList<>();
  public Main plugin;
  
  public Kangaroo(Main plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args)
	{
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("kangaroo"))
		{
			if (Main.usandokit.contains(p)) {
				p.sendMessage("§cVocê ja esta usando um kit!");
				return true;
			}
			KitManager.kitKangaroo(p);
			}
		return false;
		}
  
  public Kangaroo() {}
  
  @EventHandler
  public void onInteract(PlayerInteractEvent event)
  {
    Player p = event.getPlayer();
    if ((p.getItemInHand().getType() == Material.FIREWORK) && (Main.kangaroo.contains(p)))
    {
      if ((event.getAction() == Action.LEFT_CLICK_AIR) || 
        (event.getAction() == Action.LEFT_CLICK_BLOCK) || 
        (event.getAction() == Action.RIGHT_CLICK_BLOCK) || 
        (event.getAction() == Action.RIGHT_CLICK_AIR)) {
        event.setCancelled(true);
      }
      if (!kangaroo.contains(p)) {
        if (Main.areaPvP(p))
        {
        	if (!p.isSneaking())
            {
              Vector v = p.getEyeLocation().getDirection();
              v.multiply(0.1F);
              v.setY(0.99F);
              p.setVelocity(v);
              p.setFallDistance(p.getFallDistance() - 1.0F);
            }
            else
            {
              Vector v = p.getEyeLocation().getDirection();
              v.multiply(1.0F);
              v.setY(0.6F);
              p.setVelocity(v);
              p.setFallDistance(p.getFallDistance() - 1.0F);
            }
          kangaroo.add(p);
        }
      }
    }
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent event)
  {
    Player p = event.getPlayer();
    if (kangaroo.contains(p))
    {
      Block b = p.getLocation().getBlock();
      if ((b.getType() != Material.AIR) || 
        (b.getRelative(BlockFace.DOWN).getType() != Material.AIR))
      {
        kangaroo.remove(p);
        
        return;
      }
    }
  }
  
  @EventHandler
  public void onDamage(EntityDamageEvent event)
  {
    Entity e = event.getEntity();
    if ((e instanceof Player))
    {
      Player player = (Player)e;
      if (((event.getEntity() instanceof Player)) && 
        (event.getCause() == EntityDamageEvent.DamageCause.FALL) && 
        (player.getInventory().contains(Material.FIREWORK)) && 
        (event.getDamage() >= 7.0D)) {
        event.setDamage(7.0D);
      }
    }
  }
}
