package me.everton.pvp.kits.habilidades;

import java.util.HashMap;
import java.util.Random;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Pyro implements Listener{
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	
	@EventHandler
	public void noInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!e.getAction().name().contains("RIGHT")) {
			return;
		}
		
		if(KitManager.getKit(p) != KitType.PYRO) {
			return;
		}
		
		if(!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		e.setCancelled(true);
		
		if(!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + cd.get(p.getName()) + " segundos§7!");
			return;
		}
		
		API.startCd(p, 15, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
		
		p.launchProjectile(org.bukkit.entity.Fireball.class).setVelocity(p.getEyeLocation().getDirection().multiply(3D));
		p.getWorld().playSound(p.getLocation(), Sound.GHAST_FIREBALL, 1F, 1F);
		p.getWorld().playSound(p.getLocation(), Sound.GHAST_CHARGE, 1F, 1F);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof LivingEntity)) {
			return;
		}
		if(!(e.getDamager() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getDamager();
		final LivingEntity en = (LivingEntity) e.getEntity();
		
		if(KitManager.getKit(p) != KitType.PYRO) {
			return;
		}
		
		if(!API.getGamers().contains(p)) {
			return;
		}
		
		if(en instanceof Player) {
			Player ent = (Player) en;
			if(!API.getGamers().contains(ent)) {
				return;
			}
		}
		
		if(new Random().nextInt(100) <= 33) {
			en.setFireTicks(90);
		}
	}
}
