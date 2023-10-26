package me.everton.pvp.kits.habilidades;

import java.util.HashMap;
import java.util.Random;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Monk implements Listener{
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	
	@EventHandler
	public void onIE(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if(KitManager.getKit(p) != KitType.MONK) {
			return;
		}
		
		if(!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		
		if(!(e.getRightClicked() instanceof Player)) {
			return;
		}
		Player r = (Player) e.getRightClicked();
		
		if(!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}
		
		if(!API.getGamers().contains(r)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit contra esse player!");
			return;
		}
		
		if (cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
					+ cd.get(p.getName()) + " segundos§7!");
			return;
		}
		API.startCd(p, 10, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
		
		Random ra = new Random();
		int slot = ra.nextInt(r.getInventory().getSize());
		while(r.getInventory().getItem(ra.nextInt(r.getInventory().getSize())) == null) {
			slot = ra.nextInt(r.getInventory().getSize());
		}
		
		ItemStack item = r.getItemInHand().clone();
		r.setItemInHand(r.getInventory().getItem(slot));
		r.getInventory().setItem(slot, item);
		r.sendMessage("§7[§c!§7] Você foi monkado!");
	}
}
