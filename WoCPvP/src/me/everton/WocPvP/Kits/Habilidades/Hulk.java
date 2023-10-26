package me.everton.WocPvP.Kits.Habilidades;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import me.everton.WocPvP.KitManager;
import me.everton.WocPvP.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Hulk
  implements Listener, CommandExecutor
{
  public static Main plugin;
  
  public Hulk(Main main)
  {
    plugin = main;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args)
	{
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("hulk"))
		{
			if (Main.usandokit.contains(p)) {
				p.sendMessage("§cVocê ja esta usando um kit!");
				return true;
			}
			KitManager.kitHulk(p);
			}
		return false;
		}
  
  public static HashMap<String, Long> cooldown = new HashMap<>();
  
  @SuppressWarnings("deprecation")
@EventHandler
  public void pegar(PlayerInteractEntityEvent e)
  {
    Player p = e.getPlayer();
    if ((e.getRightClicked() instanceof Player))
    {
      Player r = (Player)e.getRightClicked();
      if (Main.hulk.contains(p)) {
        if ((!cooldown.containsKey(p.getName())) || (((Long)cooldown.get(p.getName())).longValue() <= System.currentTimeMillis()))
        {
          if (p.getItemInHand().getType() == Material.SADDLE)
          {
            e.setCancelled(true);
            p.updateInventory();
            p.setPassenger(r);
            cooldown.put(p.getName(), Long.valueOf(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(15L)));
            p.sendMessage("§cVocê pegou §4" + ChatColor.stripColor(r.getDisplayName()));
            r.sendMessage("§cVocê foi pego por um Hulk: §4" + ChatColor.stripColor(p.getDisplayName()));
          }
        }
        else if((!cooldown.containsKey(p.getName())) || (((Long)cooldown.get(p.getName())).longValue() <= System.currentTimeMillis()) && (p.getItemInHand().getType() == Material.SADDLE)){
          p.sendMessage(ChatColor.RED + "Faltam " + TimeUnit.MILLISECONDS.toSeconds(((Long)cooldown.get(p.getName())).longValue() - System.currentTimeMillis()) + " segundos para poder usar novamente.");
        }
      }
    }
  }
  
  @EventHandler
  public static void playerInteract(PlayerInteractEvent event)
  {
    if (!event.getAction().equals(Action.LEFT_CLICK_AIR)) {
      return;
    }
    Player player = event.getPlayer();
    if ((player.getPassenger() == null) || (!(player.getPassenger() instanceof Player))) {
      return;
    }
    Player pass = (Player)player.getPassenger();
    player.eject();
    pass.damage(0.0D, player);
    pass.setVelocity(new Vector(pass.getVelocity().getX(), 1.0D, pass.getVelocity().getZ()));
    pass.sendMessage(ChatColor.RED + "Voc\u00EA foi jogado por " + ChatColor.DARK_RED + ChatColor.stripColor(player.getDisplayName()));
  }
}
