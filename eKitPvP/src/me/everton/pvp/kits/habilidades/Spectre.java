package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.SpawnProtection;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Spectre implements Listener{
	public static HashMap<String, Integer> task = new HashMap<>(),cd = new HashMap<>();;
	public static HashMap<String, Integer> taskInvisible = new HashMap<>(),cdInvisible = new HashMap<>();
	
	@EventHandler
	public void titan(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(KitManager.getKit(p) != KitType.SPECTRE) {
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
		
		if(!API.getGamers().contains(p)) {
			p.sendMessage("�7[�c!�7] Voc� n�o pode usar seu kit aqui!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("�7[�c!�7] Kit em cooldown de �c�l" + cd.get(p.getName()) + " segundos�7!");
			return;
		}
		
		API.startCd(p, 80, 0, cd, "�7[�c!�7] O �c�lcooldown �7acabou!");
		
		p.sendMessage("�7[�c!�7] Agora voc� est� invis�vel por �c�l20 segundos�7! N�o leve hits nem d�-os, se n�o ir� perder a invisibilidade! Lembre-se que o item em sua m�o e sua armadura continuam vis�veis!");
		p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 15.5F, 15.5F);
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 20, 0));
	}
	
	@EventHandler
	public void noGetDamage(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		
		if(KitManager.getKit(p) != KitType.SPECTRE) {
			return;
		}
		
		if(!cdInvisible.containsKey(p.getName())) {
			return;
		}
		
		if(e.getDamager() instanceof Player) {
			Player d = (Player) e.getDamager();
			if(SpawnProtection.hasProtection(d)) {
				return;
			}
		}
		
		p.removePotionEffect(PotionEffectType.INVISIBILITY);
		p.sendMessage("�7[�c!�7] Voc� perdeu sua invisibilidade pois foi hitado!");
		p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
	}
	
	@EventHandler
	public void noDamageOthers(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getDamager();
		
		if(KitManager.getKit(p) != KitType.SPECTRE) {
			return;
		}
		
		if(!cdInvisible.containsKey(p.getName())) {
			return;
		}
		
		if(e.getEntity() instanceof Player) {
			Player d = (Player) e.getEntity();
			if(SpawnProtection.hasProtection(d)) {
				return;
			}
		}
		
		p.removePotionEffect(PotionEffectType.INVISIBILITY);
		p.sendMessage("�7[�c!�7] Voc� perdeu sua invisibilidade pois foi hitado!");
		p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
	}
}
