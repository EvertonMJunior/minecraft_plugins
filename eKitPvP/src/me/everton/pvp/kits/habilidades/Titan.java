package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Titan implements Listener{
	public static HashMap<String, Integer> task = new HashMap<>(),cd = new HashMap<>();;
	public static HashMap<String, Integer> tasknoDamage = new HashMap<>(),cdnoDamage = new HashMap<>();
	
	@EventHandler
	public void titan(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(KitManager.getKit(p) != KitType.TITAN) {
			return;
		}
		
		if(!e.getAction().name().contains("RIGHT")) {
			return;
		}
		
		if(!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		
		e.setCancelled(true);
		e.setUseInteractedBlock(Result.DENY);
		p.updateInventory();
		
		if (!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}
		
		if(cdnoDamage.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Você ainda tem §c§l" + cdnoDamage.get(p.getName()) + " segundos §7de proteçao!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + cd.get(p.getName()) + " segundos§7!");
			return;
		}
		
		API.startCd(p, 80, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
		API.startCd(p, 10, 0, cdnoDamage, "§7[§c!§7] A §c§lproteçao §7acabou!");
		
		p.sendMessage("§7[§c!§7] Agora você está invencível por §c§l10 §7segundos!");
		p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 15.5F, 15.5F);
	}
	
	@EventHandler
	public void noDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		
		Player p = (Player) e.getEntity();
		
		if(KitManager.getKit(p) != KitType.TITAN) {
			return;
		}
		
		if(!tasknoDamage.containsKey(p.getName())) {
			return;
		}
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void noDamageOthers(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player)) {
			return;
		}
		
		Player p = (Player) e.getDamager();
		
		if(KitManager.getKit(p) != KitType.TITAN) {
			return;
		}
		
		if(!tasknoDamage.containsKey(p.getName())) {
			return;
		}
		
		e.setCancelled(true);
	}
}
