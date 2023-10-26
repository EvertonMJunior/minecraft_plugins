package me.everton.pvp.kits.habilidades;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class Fisherman implements Listener{
	@EventHandler
	public void onFish(PlayerFishEvent e) {
		Player p = e.getPlayer();
		if(KitManager.getKit(p) != KitType.FISHERMAN) {
			return;
		}
		if(!(e.getCaught() instanceof Player)) {
			return;
		}
		
		if(!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}
		
		Player c = (Player) e.getCaught();
		
		c.teleport(p.getLocation());
		p.getItemInHand().setDurability((short) 0);
		p.updateInventory();
	}
}
