package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


public class Fireman extends Kit {
    public Fireman() {
        super("Fireman", Material.WATER_BUCKET,
                new ItemStack[]{Item.item(Material.WATER_BUCKET, 1, "§3Fireman")},
                Arrays.asList("§7Começe a partida com um balde de água,",
                        "§7facilitando a coleta de cogumelos. Você",
                        "§7também pode colocar lava em seu balde."));
    }

    @EventHandler
    public void onFireman(EntityDamageEvent e) {
        if (Main.estado == GameState.PREGAME) return;
        if (e.getEntity() instanceof Player) {
            if (hasAbility((Player) e.getEntity())) {
                if (e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.LAVA) {
                    e.setCancelled(true);
                }
            }
        }
    }
}