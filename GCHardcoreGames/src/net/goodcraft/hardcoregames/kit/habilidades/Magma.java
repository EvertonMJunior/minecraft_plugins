package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;

public class Magma extends Kit {
    public Magma() {
        super("Magma", Material.FIRE,
                new ItemStack[]{null},
                Arrays.asList("§7Bata em seu adversário várias",
                        "§7vezes e deixe-o pegando fogo!"));
    }

    @EventHandler
    public void kitMagma(EntityDamageByEntityEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player dmg = (Player) e.getDamager();
            Player p = (Player) e.getEntity();
            if (hasAbility(p) && (new Random().nextInt(4) == 1)) {
                dmg.setFireTicks(10 * 20);
            }
        }
    }

    @EventHandler
    public void kitMagma2(BlockDamageEvent e) {
        Player p = e.getPlayer();
        if (hasAbility(p)
                && p.getItemInHand().getType() == Material.AIR
                && (new Random().nextInt(10) == 1)) {
            Block b = e.getBlock().getLocation().clone().add(0, 1, 0)
                    .getBlock();
            if (b.getType() == Material.AIR) {
                b.setType(Material.FIRE);
            }
        }
    }

    @EventHandler
    public void kitMagma3(EntityDamageEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (hasAbility(p)
                    && (e.getCause() == DamageCause.FIRE
                    || e.getCause() == DamageCause.FIRE_TICK || e
                    .getCause() == DamageCause.LAVA)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void kitMagma4(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        Block b = p.getLocation().getBlock();
        if (Main.getMg().getGameState() != GameState.PREGAME) {
            if ((b.getType().name().contains("WATER") || b
                    .getRelative(BlockFace.UP).getType().name()
                    .contains("WATER"))
                    && hasAbility(p)) {
                p.damage(0.5);
                return;
            }
        }
    }
}