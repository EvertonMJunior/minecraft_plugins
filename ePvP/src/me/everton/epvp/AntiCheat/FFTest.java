package me.everton.epvp.AntiCheat;

import java.util.HashMap;

import me.everton.epvp.Main;
import me.everton.epvp.Comandos.Admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class FFTest implements CommandExecutor,Listener{
	public static HashMap<String, String> tests = new HashMap<>();
	public static HashMap<String, Location> oldloc = new HashMap<>();
	public static HashMap<String, Integer> hits = new HashMap<>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("fftest")) {
			if(!p.hasPermission("pvp.admin")) {
				
			}
			
			if (args.length == 1) {
				if(Bukkit.getPlayerExact(args[0]) == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}
				
				Player t = Bukkit.getPlayerExact(args[0]);
				if(Admin.vis.contains(p.getName())) {
					t.showPlayer(p);
				}
				p.setGameMode(GameMode.SURVIVAL);
				p.setAllowFlight(true);
				p.setFlying(true);
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 255));
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 255));
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				
				p.sendMessage("§7[§a!§7] Teste iniciado!");
				hits.put(t.getName(), 0);
				oldloc.put(p.getName(), p.getLocation());
				p.teleport(t.getLocation().add(0, 2, 0));
				
				tests.put(t.getName(), p.getName());
				Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					
					@Override
					public void run() {
						if(oldloc.containsKey(p.getName())) {
							p.sendMessage("§7[§a!§7] O teste acabou! " + t.getName() + " lhe deu " + hits.get(t.getName()) + " hits!");
							p.teleport(oldloc.get(p.getName()));
							hits.remove(t.getName());
							tests.remove(t.getName());
							oldloc.remove(p.getName());
							if(Admin.vis.contains(p.getName())) {
								t.hidePlayer(p);
							}
							for (PotionEffect po : p.getActivePotionEffects()) {
								p.removePotionEffect(po.getType());
							}
							p.setAllowFlight(false);
						}
					}
				}, 10 * 20L);
			} else {
				p.sendMessage("§cUse: /fftest <jogador>");
			}
		}
		return false;
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getDamager() instanceof Player) {
				Player d = (Player) e.getEntity();
				Player p = (Player) e.getDamager();
				
				if((tests.containsKey(p.getName())) && (tests.get(p.getName()).equalsIgnoreCase(d.getName()))) {
					d.sendMessage("§7[§c!§7] " + p.getName() + " lhe deu 1 hit!");
					if(!hits.containsKey(p.getName())) {
						hits.put(p.getName(), 1);
					} else {
						hits.put(p.getName(), hits.get(p.getName()) + 1);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onWalk(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(oldloc.containsKey(p.getName())) {
			if(e.getFrom().getY() != e.getTo().getY()) {
				p.teleport(e.getFrom());
			}
		}
		if(tests.containsKey(p.getName())) {
			Player t = Bukkit.getPlayerExact(tests.get(p.getName()));
			if(t == null) {
				return;
			}
			
			double d = p.getLocation().distance(t.getLocation());
		      double a = d;
		      double v_x = (1.0D + 0.03000000000000001D * a) * (p.getLocation().getX() - t.getLocation().getX()) / a;
		      
		      double v_z = (1.0D + 0.03000000000000001D * a) * (p.getLocation().getZ() - t.getLocation().getZ()) / a;
		      if (t.isOnGround())
		      {
		    	  p.getLocation().getY();t.getLocation().getY();
		      }
		      double v_y;
		      if (p.getLocation().getY() - t.getLocation().getY() < 0.5D) {
		        v_y = t.getVelocity().getY();
		      } else {
		        v_y = (0.9D + 0.03D * a) * (
		          (p.getLocation().getY() - t.getLocation()
		          .getY()) / a);
		      }
		      Vector v = t.getVelocity();
		      v.setX(v_x);
		      v.setY(v_y);
		      v.setZ(v_z);
		      t.setVelocity(v);
		}
	}
}
