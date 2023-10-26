package me.everton.hg.kits.habilidades;

import java.util.HashMap;

import me.everton.hg.API;
import me.everton.hg.Main;
import me.everton.hg.kits.KitManager;
import me.everton.hg.kits.KitType;

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
		
		if(!Main.STATE_KITS) {
			p.sendMessage("§7[§c!§7] Os kits estao desativados!");
			return;
		}
		
		if(Main.INVENCIBILIDADE) {
			p.sendMessage("§7[§c!§7] Kit desativado durante a invencibilidade!");
			return;
		}
		
		e.setCancelled(true);
		e.setUseInteractedBlock(Result.DENY);
		p.updateInventory();
		
		if(cdnoDamage.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Você ainda tem §c§l" + Main.secToMinSec(cdnoDamage.get(p.getName())) + " §7de proteçao!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + Main.secToMinSec(cd.get(p.getName())) + " §7!");
			return;
		}
		
		API.startCd(p, 80, 10, task, cd, true, "§7[§c!§7] O §c§lcooldown §7acabou!");
		API.startCd(p, 10, 0, tasknoDamage, cdnoDamage, true, "§7[§c!§7] A §c§lproteçao §7acabou!");
		
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
