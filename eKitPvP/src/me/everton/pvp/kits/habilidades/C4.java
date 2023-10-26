package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class C4 implements Listener{
	public static HashMap<String, Item> bombas = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();
	
	@EventHandler
	public void bombEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(KitManager.getKit(p) != KitType.C4) {
			return;
		}
		
		if(!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		e.setCancelled(true);
		e.setUseInteractedBlock(Result.DENY);
		p.updateInventory();
		
		if(!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}
		
		if(cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l" + API.secToString(cd.get(p.getName())) + "§7!");
			return;
		}
		
		if(e.getAction().name().contains("LEFT")) {
			if(bombas.containsKey(p.getName())) {
				Item bomb = bombas.get(p.getName());
				bomb.remove();
				bombas.remove(p.getName());
				p.getItemInHand().setType(Material.SLIME_BALL);
			} else {
				Item bomb = p.getWorld().dropItem(p.getEyeLocation(), API.item(Material.TNT, 1, "noClear"));
				bomb.setVelocity(p.getEyeLocation().getDirection().multiply(1.2D));
				bomb.setPickupDelay(999999999);
				bombas.put(p.getName(), bomb);
				p.getItemInHand().setType(Material.STONE_BUTTON);
			}
		}
		
		if(e.getAction().name().contains("RIGHT")) {
			if(!bombas.containsKey(p.getName())) {
				return;
			}
			
			if(p.getItemInHand().getType() != Material.STONE_BUTTON) {
				return;
			}
			
			Item bomb = bombas.get(p.getName());
			bomb.getLocation().getWorld().createExplosion(bomb.getLocation(), 4.5F);
			bomb.remove();
			bombas.remove(p.getName());
			API.startCd(p, 30, 0, cd, "§7[§c!§7] O cooldown acabou!");
			p.getItemInHand().setType(Material.SLIME_BALL);
		}
	}
}
