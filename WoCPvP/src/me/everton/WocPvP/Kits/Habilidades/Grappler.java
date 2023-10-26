package me.everton.WocPvP.Kits.Habilidades;

import java.util.HashMap;
import java.util.Map;

import me.everton.WocPvP.KitManager;
import me.everton.WocPvP.Main;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

public class Grappler
implements Listener, CommandExecutor
{
	public static Main plugin;

	
	public Grappler(Main main)
	{
		plugin = main;
		}

	
	Map<Player, Cordinha> hooks = new HashMap<>();

	
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args)
	{
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("grappler"))
		{
			if (Main.usandokit.contains(p)) {
				p.sendMessage("§cVocê ja esta usando um kit!");
				return true;
			}
			KitManager.kitGrappler(p);
			}
		return false;
		}

	@EventHandler
	public void onSlot(PlayerItemHeldEvent e) {
		if (this.hooks.containsKey(e.getPlayer())) {
			((Cordinha) this.hooks.get(e.getPlayer())).remove();
			this.hooks.remove(e.getPlayer());
		}
	}

	@EventHandler
	public void grapplerDamageNoLeash(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
			return;
		}
		if ((this.hooks.containsKey(player))
				&& (((Cordinha) this.hooks.get(player)).isHooked())
				&& (event.getDamage() > 3.0D)) {
			event.setDamage(3.0D);
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if ((this.hooks.containsKey(e.getPlayer()))
				&& (!e.getPlayer().getItemInHand().getType()
						.equals(Material.LEASH))) {
			((Cordinha) this.hooks.get(e.getPlayer())).remove();
			this.hooks.remove(e.getPlayer());
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLeash(PlayerLeashEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getPlayer().getItemInHand().getType().equals(Material.LEASH)) {
			e.setCancelled(true);
			e.getPlayer().updateInventory();
			e.setCancelled(true);
			if (!this.hooks.containsKey(p)) {
				return;
			}
			if (!((Cordinha) this.hooks.get(p)).isHooked()) {
				return;
			}
			double d = ((Cordinha) this.hooks.get(p)).getBukkitEntity()
					.getLocation().distance(p.getLocation());
			double t = d;
			double v_x = (1.0D + 0.07000000000000001D * t)
					* (((Cordinha) this.hooks.get(p)).getBukkitEntity()
							.getLocation().getX() - p.getLocation().getX()) / t;
			double v_y = (1.0D + 0.03D * t)
					* (((Cordinha) this.hooks.get(p)).getBukkitEntity()
							.getLocation().getY() - p.getLocation().getY()) / t;
			double v_z = (1.0D + 0.07000000000000001D * t)
					* (((Cordinha) this.hooks.get(p)).getBukkitEntity()
							.getLocation().getZ() - p.getLocation().getZ()) / t;

			Vector v = p.getVelocity();
			v.setX(v_x);
			v.setY(v_y);
			v.setZ(v_z);
			p.setVelocity(v);
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getPlayer().getItemInHand().getType().equals(Material.LEASH)) {
			if (Main.grappler.contains(p)) {
				e.setCancelled(true);
				if ((e.getAction() == Action.LEFT_CLICK_AIR)
						|| (e.getAction() == Action.LEFT_CLICK_BLOCK)
						|| (e.getAction() == Action.PHYSICAL)) {

					if (this.hooks.containsKey(p)) {
						((Cordinha) this.hooks.get(p)).remove();
					}
					Cordinha nmsHook = new Cordinha(p.getWorld(),
							((CraftPlayer) p).getHandle());
					nmsHook.spawn(p.getEyeLocation().add(
							p.getLocation().getDirection().getX(),
							p.getLocation().getDirection().getY(),
							p.getLocation().getDirection().getZ()));
					nmsHook.move(p.getLocation().getDirection().getX() * 5.0D,
							p.getLocation().getDirection().getY() * 5.0D, p
									.getLocation().getDirection().getZ() * 5.0D);
					this.hooks.put(p, nmsHook);
				} else {
					if (!this.hooks.containsKey(p)) {
						return;
					}
					if (!((Cordinha) this.hooks.get(p)).isHooked()) {
						p.sendMessage("§cSeu gancho nao esta preso em nada!");
						return;
					}
					double d = ((Cordinha) this.hooks.get(p)).getBukkitEntity()
							.getLocation().distance(p.getLocation());
					double t = d;
					double v_x = (1.0D + 0.2D * t)
							* (((Cordinha) this.hooks.get(p)).getBukkitEntity()
									.getLocation().getX() - p.getLocation()
									.getX()) / t;
					double v_y = (1.0D + 0.03D * t)
							* (((Cordinha) this.hooks.get(p)).getBukkitEntity()
									.getLocation().getY() - p.getLocation()
									.getY()) / t;
					double v_z = (1.0D + 0.2D * t)
							* (((Cordinha) this.hooks.get(p)).getBukkitEntity()
									.getLocation().getZ() - p.getLocation()
									.getZ()) / t;

					Vector v = p.getVelocity();
					v.setX(v_x);
					v.setY(v_y);
					v.setZ(v_z);
					p.setVelocity(v);
				}
			}
		}
	}
	
	@EventHandler
	public void aoQuit(PlayerQuitEvent e){
		if (this.hooks.containsKey(e.getPlayer())) {
			((Cordinha) this.hooks.get(e.getPlayer())).remove();
			this.hooks.remove(e.getPlayer());
		}
	}
}