package me.everton.hg.kits.habilidades;

import me.everton.hg.Main;
import me.everton.hg.kits.KitManager;
import me.everton.hg.kits.KitType;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class Worm implements Listener{
	@EventHandler
	public void worm(BlockDamageEvent e) {
		Player p = e.getPlayer();
		if(!Main.EM_JOGO) {
			return;
		}
		
		if(KitManager.getKit(p) != KitType.TOWER && KitManager.getKit(p) != KitType.WORM) {
			return;
		}
		
		if(e.getBlock().getType() != Material.DIRT) {
			return;
		}
		
		if(!Main.STATE_KITS) {
			p.sendMessage("§7[§c!§7] Os kits estao desativados!");
			return;
		}
		
		e.setInstaBreak(true);
		
		if(KitManager.getKit(p) == KitType.WORM) {
			Damageable hp = p;
			if(p.getFoodLevel() < 20) {
				p.setFoodLevel(p.getFoodLevel() + 1);
			}
			if(hp.getHealth() < 20) {
				p.setHealth(hp.getHealth() + 1.0D);
			}
		}
	}
}
	