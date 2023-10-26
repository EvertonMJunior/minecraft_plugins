package me.everton.pvp.kits.habilidades;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poseidon implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (KitManager.getKit(p) == KitType.POSEIDON) {
			if ((p.getLocation().getBlock().getType() == Material.WATER)
					|| (p.getLocation().getBlock().getType() == Material.STATIONARY_WATER)) {
				
				if(!API.getGamers().contains(p)) {
					p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
					return;
				}
				
				p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				p.removePotionEffect(PotionEffectType.SPEED);
				p.addPotionEffect(new PotionEffect(
						PotionEffectType.INCREASE_DAMAGE, 5 * 20, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
						5 * 20, 0));
			}
		}
	}
}
