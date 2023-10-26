package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Launcher extends Kit {
    public Launcher() {
        super("Launcher", Material.SPONGE,
                new ItemStack[]{Item.item(Material.SPONGE, 8, "§3Launcher")},
                Arrays.asList("§7Ganhe esponjas no início.",
                        "§7Com elas, jogue-se para o alto!"));
    }

    public ArrayList<UUID> nofall = new ArrayList<>();

    @EventHandler
    public void kitLauncher(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock()
                .getRelative(BlockFace.DOWN);
        if (block.getType().equals(Material.SPONGE)) {
            int xblock = 0;
            double xvel = 0.0D;
            int yblock = -1;
            double yvel = 1.0D;
            int zblock = 0;
            double zvel = 0.0D;
            while (block.getLocation().add(xblock - 1, -1.0D, 0.0D).getBlock()
                    .getType() == Material.SPONGE) {
                xblock--;
                xvel += 2.0D;
            }
            while (block.getLocation().add(0.0D, yblock, 0.0D).getBlock()
                    .getType() == Material.SPONGE) {
                yblock--;
                yvel += 1.0D;
            }
            while (block.getLocation().add(0.0D, -1.0D, zblock - 1).getBlock()
                    .getType() == Material.SPONGE) {
                zblock--;
                zvel += 1.0D;
            }
            xblock = 0;
            zblock = 0;
            while (block.getLocation().add(xblock + 1, -1.0D, 0.0D).getBlock()
                    .getType() == Material.SPONGE) {
                xblock++;
                xvel -= 2.0D;
            }
            while (block.getLocation().add(0.0D, -1.0D, zblock + 1).getBlock()
                    .getType() == Material.SPONGE) {
                zblock++;
                zvel -= 2.0D;
            }
            if ((xvel != 0.0D) || (yvel != 0.0D) || (zvel != 0.0D)) {
                player.setVelocity(new Vector(xvel, yvel, zvel));
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_HIT,
                        5.0F, -5.0F);
                if (hasAbility(player)) {
                    if (!nofall.contains(player.getUniqueId())) {
                        nofall.add(player.getUniqueId());
                    }
                }
            }
        }
    }

    @EventHandler
    public void kitLauncher2(EntityDamageEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)
                    && nofall.contains(p.getUniqueId())) {
                e.setCancelled(true);
                nofall.remove(p.getUniqueId());
            }
        }
    }
}
