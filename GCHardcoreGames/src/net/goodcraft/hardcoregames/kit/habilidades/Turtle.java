package net.goodcraft.hardcoregames.kit.habilidades;

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
                new ItemStack[]{null},
                Arrays.asList("§7Segure SHIFT e tome pouco",
                        "§7dano, sendo de hits ou queda!"));
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        event(e);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        event(e);
    }

    private void event(EntityDamageEvent e){
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (!hasAbility(p)) return;
        if (!p.isSneaking()) return;
        e.setDamage(2D);
    }
}
