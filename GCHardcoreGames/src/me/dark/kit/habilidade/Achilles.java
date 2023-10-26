package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Listener.GamerDamage;
import me.dark.kit.Kit;

public class Achilles extends Kit{
	public Achilles() {
		super("Achilles", Material.WOOD_SWORD, 
				new ItemStack[] { null },
				Arrays.asList("§7O Kit Achilles reduz o dano de",
						      "§7itens que não são feitos de madeira",
                              "§7Lembre-se, itens de madeira você levará",
                              "§7um Dano muito alto!"));
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void attack(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			if (Main.estado == GameState.PREGAME) return;
			Player p = (Player) e.getEntity();
			Player atacante = (Player) e.getDamager();
			if (hasAbility(p)) {
				if (atacante.getItemInHand() != null
						&& atacante.getItemInHand().getType().name()
								.contains("WOOD")) {
					e.setDamage(GamerDamage.diamond+1.0D);
				}
				if (atacante.getItemInHand().getType() == Material.STONE_SWORD) {
					e.setDamage(GamerDamage.wood);
				}
				if (atacante.getItemInHand().getType() == Material.IRON_SWORD) {
					e.setDamage(GamerDamage.wood);
				}
				if (atacante.getItemInHand().getType() == Material.DIAMOND_SWORD) {
					e.setDamage(GamerDamage.wood);
				}
				if (atacante.getItemInHand().containsEnchantment(
						Enchantment.DAMAGE_ALL)) {
					e.setDamage(e.getDamage() + GamerDamage.sharp);
				}
				if (atacante.getFallDistance() > 0.0F
						&& !atacante.isOnGround()
						&& !atacante
								.hasPotionEffect(PotionEffectType.BLINDNESS)) {
					if (atacante.getItemInHand() != null
							&& atacante.getItemInHand().getType().name()
									.contains("WOOD")) {
						e.setDamage(e.getDamage() + GamerDamage.critical);
					}
					if (atacante.getItemInHand().getType() == Material.STONE_SWORD) {
						e.setDamage(e.getDamage() + GamerDamage.critical);
					}
					if (atacante.getItemInHand().getType() == Material.IRON_SWORD) {
						e.setDamage(e.getDamage() + GamerDamage.critical);
					}
					if (atacante.getItemInHand().getType() == Material.DIAMOND_SWORD)
						e.setDamage(e.getDamage() + GamerDamage.critical);
				}
			}

		}
	}
}
