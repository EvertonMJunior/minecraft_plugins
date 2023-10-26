package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Sumo implements Listener{
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Location> blocos = new HashMap<>(); 
	
	@EventHandler
	public void onI(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(KitManager.getKit(p) != KitType.SUMO) {
			return;
		}
		
		if(!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		e.setCancelled(true);
		p.updateInventory();
		
		if(!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + cd.get(p.getName()) + " segundos§7!");
			return;
		}
		
		if(blocos.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Caia no chão antes de criar outro bloco!");
			return;
		}
		
		if(p.getLocation().clone().add(0, 10, 0).getBlock().getType() != Material.AIR) {
			p.sendMessage("§cVocê nao pode usar seu kit aqui!");
			return;
		}
		
		if(p.getLocation().clone().add(0, 11, 0).getBlock().getType() != Material.AIR) {
			p.sendMessage("§cVocê nao pode usar seu kit aqui!");
			return;
		}
		
		if(p.getLocation().clone().add(0, 12, 0).getBlock().getType() != Material.AIR) {
			p.sendMessage("§7[§c!§7] Você nao pode usar seu kit aqui!");
			return;
		}
		
		API.startCd(p, 45, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
		p.getLocation().getBlock().getLocation().clone().add(0, 10, 0).getBlock().setType(Material.BEDROCK);
		blocos.put(p.getName(), p.getLocation().getBlock().getLocation().clone().add(0, 10, 0));
		p.teleport(p.getLocation().clone().add(0, 10, 0).getBlock().getLocation().clone().add(0.5, 1.5, 0.5));
		p.setNoDamageTicks(5);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		
		if(e.getCause() != DamageCause.FALL) {
			return;
		}
		
		if(KitManager.getKit(p) != KitType.SUMO) {
			return;
		}
		
		if(!blocos.containsKey(p.getName())) {
			return;
		}
		
		for(Entity ent : p.getNearbyEntities(5.0D, 3.0D, 5.0D)) {
			if(API.getGamers().contains(p)) {
				ent.setVelocity(new Vector(ent.getVelocity().getX(), 1.5D, ent.getVelocity().getX()));
			}
		}
		
		blocos.get(p.getName()).getBlock().setType(Material.AIR);
		blocos.remove(p.getName());
		e.setCancelled(true);
	}
}
