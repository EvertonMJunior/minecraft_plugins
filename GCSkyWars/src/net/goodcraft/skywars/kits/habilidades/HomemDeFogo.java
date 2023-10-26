package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.skywars.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class HomemDeFogo extends Kit {
    public HomemDeFogo() {
        super("Homem de Fogo", Material.LAVA_BUCKET,
                new ItemStack[]{
                        Item.item(Material.LAVA_BUCKET),
                }, Arrays.asList(
                        "§7Seja imune ao dano da lava",
                        "§7e ganhe um balde com ela!"
                ));
        setVip();
        setPrice(3000);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;

        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        if (!hasAbility(p)) return;

        if (e.getCause() != EntityDamageEvent.DamageCause.LAVA &&
                e.getCause() != EntityDamageEvent.DamageCause.FIRE &&
                e.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK) {
            return;
        }

        e.setCancelled(true);
    }
}
