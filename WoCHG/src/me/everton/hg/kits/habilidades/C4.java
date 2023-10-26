package me.everton.hg.kits.habilidades;

import java.util.HashMap;

import me.everton.hg.API;
import me.everton.hg.Main;
import me.everton.hg.kits.KitManager;
import me.everton.hg.kits.KitType;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.player.PlayerInteractEvent;

public class C4 implements Listener{
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Item> bombas = new HashMap<>();
	
	@EventHandler
	public void c4(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.C4) {
			return;
		}

		if (!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		
		e.setCancelled(true);
		e.setUseInteractedBlock(Result.DENY);
		p.updateInventory();

		if (!Main.STATE_KITS) {
			p.sendMessage("§7[§c!§7] Os kits estao desativados!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + Main.secToMinSec(cd.get(p.getName())) + " §7!");
			return;
		}
		
		if(e.getAction().name().contains("LEFT")) {
			if(bombas.containsKey(p.getName())) {
				bombas.get(p.getName()).remove();
				bombas.remove(p.getName());
				p.setItemInHand(KitManager.getKit(p).getItemKit());
			} else {
				Item bomb = p.getWorld().dropItem(p.getEyeLocation(), API.item(Material.TNT, 1, "C4"));
				bomb.setPickupDelay(999999);
				bomb.setVelocity(p.getEyeLocation().getDirection().multiply(1.2D));
				bombas.put(p.getName(), bomb);
				p.setItemInHand(API.item(Material.STONE_BUTTON, 1, "§6C4"));
			}
		}
		
		if(e.getAction().name().contains("RIGHT")) {
			if(!bombas.containsKey(p.getName())) {
				return;
			}
			
			if(p.getItemInHand().getType() != Material.STONE_BUTTON) {
				return;
			}
			
			API.startCd(p, 1, 0, task, cd, true, "§7[§c!§7] O §c§lcooldown §7acabou!");
			Item bomb = bombas.get(p.getName());
			bomb.getLocation().getWorld().createExplosion(bomb.getLocation(), 2.5F);
			bomb.remove();
			bombas.remove(p.getName());
			p.setItemInHand(KitManager.getKit(p).getItemKit());
		}
	}
}
