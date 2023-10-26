package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;

public class Fireman extends Kit {
    public Fireman() {
        super("Fireman", Material.LAVA_BUCKET,
                new ItemStack[]{},
                Arrays.asList(
                        "§7Nunca tome dano para a",
                        "§7Lava e tenha a chance de",
                        "§7botar fogo em seus inimigos",
                        "§7ao bater neles!"
                ));
        setPrice(3000);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLavaDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(e.isCancelled()) return;

        Player p = (Player) e.getEntity();

        if(!hasAbility(p)) return;
        if(e.getCause() != EntityDamageEvent.DamageCause.LAVA &&
                e.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK &&
                e.getCause() != EntityDamageEvent.DamageCause.FIRE) return;
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamageByEntity(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Player)) return;
        if(e.isCancelled()) return;

        Player p = (Player) e.getEntity();
        Player d = (Player) e.getDamager();

        if(!hasAbility(d)) return;

        int i = new Random().nextInt(100);
        if(i % 33 != 0) return;
        p.setFireTicks(20 * 5);
    }
}
