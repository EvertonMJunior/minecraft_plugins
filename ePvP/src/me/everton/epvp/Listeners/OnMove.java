package me.everton.epvp.Listeners;

import java.util.ArrayList;

import me.confuser.barapi.BarAPI;
import me.everton.epvp.Main;
import me.everton.epvp.Comandos.Construir;
import me.everton.epvp.Comandos.Money;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class OnMove implements Listener {
	public static ArrayList<String> jump = new ArrayList<>();
	public static ArrayList<String> jumpprot = new ArrayList<>();

	@EventHandler(priority = EventPriority.MONITOR)
	public static void aoMover(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		
		if(Construir.construir.contains(p.getName())) {
			BarAPI.setMessage(p, "§b§lMODO §a§lBuild ON", 2);
		}
		
		if((p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.BEACON) && (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.EMERALD_BLOCK)) {
			p.setVelocity(p.getLocation().getDirection().multiply(3.0D));
			p.setVelocity(new Vector(p.getVelocity().getX(), 0.5D, p
					.getVelocity().getZ()));
			Location loc = e.getTo().getBlock().getLocation();
			p.playEffect(loc, Effect.ENDER_SIGNAL, null);
			p.playSound(p.getLocation(), Sound.ARROW_HIT, 1.0F, 1.0F);
			jump.remove(p.getName());
			jump.add(p.getName());
			Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					if(jump.contains(p.getName())) {
						jump.remove(p.getName());
					}
				}
			}, 5 * 20L);
		}
			
		if ((p.getLocation().getBlock().getRelative(BlockFace.DOWN)
				.getRelative(BlockFace.DOWN).getType() == Material.SIGN)
				|| (p.getLocation().getBlock().getRelative(BlockFace.DOWN)
						.getRelative(BlockFace.DOWN).getType() == Material.SIGN_POST)) {
			Sign placa = (Sign) p.getLocation().getBlock()
					.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN)
					.getState();
			if((placa.getLine(1) != "x") || (placa.getLine(1) != "y") || (placa.getLine(1) != "z") || (placa.getLine(1) != "direcao")) {
				return;
			}
			if(!Money.isInt(placa.getLine(0))) {
				return;
			}
			
			Double v = Double.valueOf(placa.getLine(0));

			jump.remove(p.getName());
			if (placa.getLine(2).equalsIgnoreCase("cima")) {
				Double v2 = Double.valueOf(placa.getLine(0));
				p.setVelocity(new Vector(p.getVelocity().getX(), v2, p
						.getVelocity().getZ()));
			} else if (placa.getLine(2).equalsIgnoreCase("baixo")) {
				Double v2 = Double.valueOf(placa.getLine(0));
				p.setVelocity(new Vector(p.getVelocity().getX(), -v2, p
						.getVelocity().getZ()));
			}

			if (placa.getLine(1).equalsIgnoreCase("x")) {
				p.setVelocity(p.getVelocity().setX(v));
			} else if (placa.getLine(1).equalsIgnoreCase("y")) {
				p.setVelocity(p.getVelocity().setY(v));
			} else if (placa.getLine(1).equalsIgnoreCase("z")) {
				p.setVelocity(p.getVelocity().setZ(v));
			} else if (placa.getLine(1).equalsIgnoreCase("direcao")) {
				p.setVelocity(p.getLocation().getDirection().multiply(v));
			} else {
				placa.getBlock().breakNaturally();
			}
			Location loc = e.getTo().getBlock().getLocation();
			p.playEffect(loc, Effect.ENDER_SIGNAL, null);
			p.playSound(loc, Sound.FIREWORK_LAUNCH, 6.0F, 1.0F);
			jump.add(p.getName());
			Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				@Override
				public void run() {
					if(jump.contains(p.getName())) {
						jump.remove(p.getName());
					}
				}
			}, 5 * 20L);
		}
	}

	@EventHandler
	public void onFall(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if ((e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
					&& (jump.contains(p.getName()))) {
				e.setCancelled(true);
				jump.remove(p.getName());
			} else if ((e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
					&& (jumpprot.contains(p.getName()))) {
				e.setCancelled(true);
				jumpprot.remove(p.getName());
			}
		}
	}
}
