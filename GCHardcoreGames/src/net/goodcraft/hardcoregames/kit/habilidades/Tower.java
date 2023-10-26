package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Tower extends Kit {
    public Tower() {
        super("Tower", Material.DIRT,
                new ItemStack[]{null},
                Arrays.asList("§7Esse kit contém o kit Stomper e Worm.",
                        "§7Stomper: pule de grandes alturas em cima do",
                        "§7seu adversário, fazendo com que ele morra!",
                        "§7Worm: quebre terras rapidamente! Atenção:",
                        "§7quebrar terra não regenerará sua vida",
                        "§7nem aumentará seu nível de fome. Isso é",
                        "§7exclusivo do kit Worm!"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void stomper(EntityDamageEvent e) {
        Stomper.event(e);
    }

    @EventHandler
    public void onDamage(BlockDamageEvent event) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        Player p = event.getPlayer();
        if (hasAbility(p)
                && (Main.getMg().getGameState() != GameState.PREGAME)
                && (event.getBlock().getType() == Material.DIRT)) {
            event.setInstaBreak(true);
        }
    }
}