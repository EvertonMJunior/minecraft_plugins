package me.everton.WocPvP.Listeners;

import java.util.ArrayList;

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
	public static ArrayList<Player> jump = new ArrayList<>();
	public static ArrayList<Player> jumpprot = new ArrayList<>();

	@EventHandler(priority = EventPriority.MONITOR)
	public static void aoMover(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		if ((p.getLocation().getBlock().getRelative(BlockFace.DOWN)
				.getRelative(BlockFace.DOWN).getType() == Material.SIGN)
				|| (p.getLocation().getBlock().getRelative(BlockFace.DOWN)
						.getRelative(BlockFace.DOWN).getType() == Material.SIGN_POST)) {
			Sign placa = (Sign) p.getLocation().getBlock()
					.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN)
					.getState();
			Double v = Double.valueOf(placa.getLine(0));

			jump.remove(p);
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
			jump.add(p);

		}
	}

	@EventHandler
	public void onFall(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if ((e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
					&& (jump.contains(p))) {
				e.setCancelled(true);
				jump.remove(p);
			} else if ((e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
					&& (jumpprot.contains(p))) {
				e.setCancelled(true);
				jumpprot.remove(p);
			}
		}
	}
}
