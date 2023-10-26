package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import net.goodcraft.hardcoregames.eventos.GamerDamage;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class Achilles extends Kit {
    public Achilles() {
        super("Achilles", Material.WOOD_SWORD,
                new ItemStack[]{null},
                Arrays.asList("§7O kit Achilles reduz o dano de itens que",
                        "§7não são feitos de Madeira, ou seja, se",
                        "§7você estiver enfrentando um adversário e ele",
                        "§7estiver com espada de Pedra, você não vai levar",
                        "§7exatamente um dano de uma Espada de Pedra, o dano",
                        "§7será menor. Lembre-se, itens de madeira",
                        "§7lhe dão um dano muito elevado!"));
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void attack(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            if (Main.getMg().getGameState() == GameState.PREGAME) return;
            Player p = (Player) e.getEntity();
            Player atacante = (Player) e.getDamager();
            if (hasAbility(p)) {
                if (atacante.getItemInHand() != null
                        && atacante.getItemInHand().getType().name()
                        .contains("WOOD_")) {
                    e.setDamage(GamerDamage.diamond + 1.0D);
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
