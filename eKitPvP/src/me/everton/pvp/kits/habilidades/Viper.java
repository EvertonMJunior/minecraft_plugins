package me.everton.pvp.kits.habilidades;

import java.util.Random;

import me.everton.pvp.API;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Viper implements Listener{
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof LivingEntity)) {
			return;
		}
		if(!(e.getDamager() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getDamager();
		final LivingEntity en = (LivingEntity) e.getEntity();
		
		if(KitManager.getKit(p) != KitType.VIPER) {
			return;
		}
		
		if (!API.getGamers().contains(p)) {
			return;
		}
		
		if(en instanceof Player) {
			Player ent = (Player) en;
			if (!API.getGamers().contains(ent)) {
				return;
			}
		}
		
		if(new Random().nextInt(100) < 33) {
			en.removePotionEffect(PotionEffectType.POISON);
			en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 5 * 20, 1));
		}
	}
}
