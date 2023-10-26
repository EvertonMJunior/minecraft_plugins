package me.dark.Listener;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;
import me.dark.Game.GameState;

public class GamerDamage implements Listener {

	boolean damage = true;
	public static HashMap<Player, Player> combatLog = new HashMap<>();
	public static double wood = 2;
	public static double stone = 3;
	public static double iron = 4;
	public static double diamond = 5;
	public static double critical = 1;
	public static double sharp = 1;

	@EventHandler
	public void mobDmg(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof PigZombie || e.getDamager() instanceof Zombie || e.getDamager() instanceof Slime || e.getDamager() instanceof Ghast || e.getDamager() instanceof Enderman || e.getDamager() instanceof Skeleton || e.getDamager() instanceof Spider || e.getDamager() instanceof CaveSpider) {
			if (e.getDamage() > 3.5D) {
				e.setDamage(3.5D);
			}
		}
	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player && !event.isCancelled()) {
			if (Main.estado == GameState.PREGAME) return;
			final Player p = (Player) event.getEntity();
			final Player damager = (Player) event.getDamager();
			addTag(p, damager);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void nerf(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			Nerf(player, event);
		}
	}

	@SuppressWarnings("deprecation")
	public static void Nerf(Player damager, EntityDamageByEntityEvent e) {
		if (e.getDamage() > 1.0D) {
			e.setDamage(e.getDamage() - 1.0D);
		}
		if (e.getDamager() instanceof Player) {
			if ((damager.getFallDistance() > 0.0F) && (!damager.isOnGround()) && (!damager.hasPotionEffect(PotionEffectType.BLINDNESS))) {
				int NewDamage = (int) (e.getDamage() * critical + 0.5D) - (int) e.getDamage();
				if (e.getDamage() > 1.0D) {
					e.setDamage(e.getDamage() - NewDamage);
				}
			}
			if (damager.getItemInHand().getType() == Material.WOOD_SWORD) {
				e.setDamage(wood);
			}
			if (damager.getItemInHand().getType() == Material.STONE_SWORD) {
				e.setDamage(stone);
			}
			if (damager.getItemInHand().getType() == Material.IRON_SWORD) {
				e.setDamage(iron);
			}
			if (damager.getItemInHand().getType() == Material.DIAMOND_SWORD) {
				e.setDamage(diamond);
			}
			if (damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
				for (PotionEffect Effect : damager.getActivePotionEffects()) {
					if (Effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
						double Division = (Effect.getAmplifier() + 1) * 1.3D + 1.0D;
						int NewDamage;
						if (e.getDamage() / Division <= 1.0D)
							NewDamage = (Effect.getAmplifier() + 1) * 3 + 1;
						else {
							NewDamage = (int) (e.getDamage() / Division) + (Effect.getAmplifier() + 1) * 3;
						}
						e.setDamage(NewDamage);
						break;
					}
				}
			}

			if (damager.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) {
				e.setDamage(e.getDamage() + sharp);
			}
			if ((damager.getFallDistance() > 0.0F) && (!damager.isOnGround()) && (!damager.hasPotionEffect(PotionEffectType.BLINDNESS))) {
				if (damager.getItemInHand().getType() == Material.WOOD_SWORD) {
					e.setDamage(e.getDamage() + critical);
				}
				if (damager.getItemInHand().getType() == Material.STONE_SWORD) {
					e.setDamage(e.getDamage() + critical);
				}
				if (damager.getItemInHand().getType() == Material.IRON_SWORD) {
					e.setDamage(e.getDamage() + critical);
				}
				if (damager.getItemInHand().getType() == Material.DIAMOND_SWORD)
					e.setDamage(e.getDamage() + critical);
			}
		}
	}

	public void addTag(final Player p, Player hit) {
		combatLog.put(p, hit);
		new BukkitRunnable() {
			public void run() {
				combatLog.remove(p);
			}
		}.runTaskLater(Main.getMain(), 8*20);
	}
}