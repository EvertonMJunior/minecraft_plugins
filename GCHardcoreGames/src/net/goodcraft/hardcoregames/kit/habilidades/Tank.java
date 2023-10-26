package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Tank extends Kit {
    public Tank() {
        super("Tank", Material.TNT,
                new ItemStack[]{null},
                Arrays.asList("§7Ao matar jogadores, cause uma",
                        "§7gigantesca explosão, matando inimigos."));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (e.getEntity() instanceof Player) {
            if (e.getCause() == DamageCause.ENTITY_EXPLOSION) {
                Player p = (Player) e.getEntity();
                if (hasAbility(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void tank(PlayerDeathEvent event) {
        Player p = event.getEntity();
        Player matador = event.getEntity().getKiller();
        if (matador != null) {
            if (hasAbility(matador)) {
                p.getWorld().createExplosion(p.getLocation(), 2.0F, false);
            }
        }
    }
}