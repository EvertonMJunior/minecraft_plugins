package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import net.goodcraft.hardcoregames.eventos.GamerDamage;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Boxer extends Kit {
    public Boxer() {
        super("Boxer", Material.STONE_SWORD,
                new ItemStack[]{null},
                Arrays.asList("§7Dê hits em seu adversário com a mão,",
                        "§7tendo o mesmo dano de uma Espada de Pedra.",
                        "§7Leve também menos dano de seu Adversário."));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (Main.estado == GameState.PREGAME) return;
        if (((e.getDamager() instanceof Player)) && ((e.getEntity() instanceof LivingEntity))) {
            Player p = (Player) e.getDamager();
            if (e.getDamager() instanceof Player && hasAbility(p) && e.getDamage() == 1)
                e.setDamage(GamerDamage.stone);
            if (e.getEntity() instanceof Player && hasAbility(p) && e.getDamage() > 0)
                e.setDamage(e.getFinalDamage() - 1);
        }
    }
}
