package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class Anchor extends Kit {
    public Anchor() {
        super("Anchor", Material.ANVIL,
                new ItemStack[]{},
                Arrays.asList(
                        "§7Não leve knockback nem dê",
                        "§7knockback, assim evitando fugas!"
                ));
        setPrice(4000);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.isCancelled()) return;
        if(!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        if(!hasAbility(p)) return;
        p.setVelocity(new Vector());
        e.getEntity().setVelocity(new Vector());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage2(EntityDamageByEntityEvent e){
        if(e.isCancelled()) return;
        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if(!hasAbility(p)) return;
        p.setVelocity(new Vector());
        e.getEntity().setVelocity(new Vector());
    }
}
