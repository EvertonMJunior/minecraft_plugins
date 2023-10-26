package me.everton.pvp.listeners;

import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;
import me.everton.pvp.kits.habilidades.Madman;
import me.everton.pvp.kits.habilidades.Stomper;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Dano implements Listener{
	public static enum SwordDamage{
		WOOD(2D),
		GOLD(2D),
		STONE(3D),
		IRON(4D),
		DIAMOND(5D);
		
		double dano; 
		
		private SwordDamage(double d) {
			dano = d;
		}
		
		public double getDamage() {
			return dano;
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void setDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			ItemStack i = p.getItemInHand();

			if(e.getCause() == DamageCause.ENTITY_ATTACK) {
				if(Stomper.stompando.contains(p.getName())) {
					Stomper.stompando.remove(p.getName());
					return;
				}
			}
			
			if(!i.getType().name().contains("SWORD")) {
				return;
			}
			double dano = 0.5D;
			if(i.getType() == Material.WOOD_SWORD) {
				dano = SwordDamage.WOOD.getDamage();
			}
			
			if(i.getType() == Material.STONE_SWORD) {
				dano = SwordDamage.STONE.getDamage();
			}
			
			if(i.getType() == Material.GOLD_SWORD) {
				dano = SwordDamage.GOLD.getDamage();
			}
			
			if(i.getType() == Material.IRON_SWORD) {
				dano = SwordDamage.IRON.getDamage();
			}
			
			if(i.getType() == Material.DIAMOND_SWORD) {
				dano = SwordDamage.DIAMOND.getDamage();
			}
			
			if(KitManager.getKit(p) == KitType.VIKING) {
				if(i.getType() == Material.STONE_AXE) {
					dano = 4D;
				}
			}
			for(PotionEffect pot : p.getActivePotionEffects()) {
				if(pot.getType() == PotionEffectType.INCREASE_DAMAGE) {
					dano += pot.getAmplifier() / 2;
				}
			}
			if(i.containsEnchantment(Enchantment.DAMAGE_ALL)) {
				dano += i.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
			}
			if(p.getPassenger() != null && p.getPassenger().equals(e.getEntity())) {
				dano += 6.0D;
			}
			if(!p.isOnGround()) {
				dano += 0.5D;
			}
			
			if(KitManager.getKit(p) == KitType.BOXER) {
				dano += 0.5D;
			}
			
			if(e.getEntity() instanceof Player) {
				Player p2 = (Player) e.getEntity();
				if(KitManager.getKit(p2) == KitType.BOXER) {
					dano -= 1.0D;
				}
				
				if(Madman.afetados.containsKey(p2.getName())) {
					dano += Madman.afetados.get(p2.getName()) / 50;
				}
			}
			e.setDamage(dano);
		}
	}
}
