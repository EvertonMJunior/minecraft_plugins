package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Wither implements Listener{
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	
	@EventHandler
	public void noInteract(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if(!e.getAction().name().contains("RIGHT")) {
			return;
		}
		
		if(KitManager.getKit(p) != KitType.WITHER) {
			return;
		}
		
		if(!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		e.setCancelled(true);
		p.updateInventory();
		
		if (!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + cd.get(p.getName()) + " segundos§7!");
			return;
		}
		
		API.startCd(p, 15, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
		
		for(int i = 0; i < 2; i++) {
			Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					p.launchProjectile(WitherSkull.class).setVelocity(p.getEyeLocation().getDirection().multiply(3D));
					p.getWorld().playSound(p.getLocation(), Sound.WITHER_SHOOT, 1F, 1F);
				}
			}, i * 5L);
		}
	}
}
