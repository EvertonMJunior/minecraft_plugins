package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;

public class Worm extends Kit {
    public Worm() {
        super("Worm", Material.DIRT,
                new ItemStack[]{null},
                Collections.singletonList("§7Quebre blocos de terra rapidamente!"));
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onDamage(BlockDamageEvent event) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        Player p = event.getPlayer();
        if (hasAbility(p)
                && (Main.getMg().getGameState() != GameState.PREGAME)
                && (event.getBlock().getType() == Material.DIRT)) {
            boolean drop = true;
            if (p.getHealth() < 20.0D) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, 1));
                drop = false;
            } else if (p.getFoodLevel() < 20) {
                p.setFoodLevel(p.getFoodLevel() + 1);
                drop = false;
            }
            event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.STEP_SOUND, Material.DIRT.getId());
            event.getBlock().setType(Material.AIR);
            if (drop)
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.0D, 0.5D), new ItemStack(Material.DIRT));
        }
    }
}