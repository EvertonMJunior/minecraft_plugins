package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Turtle extends Kit {
    public Turtle() {
        super("Turtle", Material.IRON_BLOCK,
                new ItemStack[]{},
                Arrays.asList(
                        "§7Proteja-se com seu casco de tartaruga,",
                        "§7apertando shift. Assim levarás muito",
                        "§7menos dano do que levarias normalmente!"
                ));
        setPrice(2750);
        setVip();
    }

    @EventHandler
    public void onDamageTaken(EntityDamageEvent e) {
        event(e);
    }

    @EventHandler
    public void onDamageTaken(EntityDamageByEntityEvent e) {
        event(e);
    }

    private void event(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        if (!hasAbility(p)) return;

        if (p.isSneaking()) {
            e.setDamage(1D);
        }
    }
}
