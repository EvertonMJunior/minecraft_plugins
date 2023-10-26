package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Random;

public class Snail extends Kit {
    public Snail() {
        super("Snail", Material.SOUL_SAND,
                new ItemStack[]{},
                Arrays.asList(
                        "§7Um em cada 3 de seus hits",
                        "§7deixarão seu inimigo lento!"
                ));
        setPrice(2250);
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
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 1, false, false));
    }

}
