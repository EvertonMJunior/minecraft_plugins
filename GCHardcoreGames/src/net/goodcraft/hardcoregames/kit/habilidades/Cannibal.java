package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Random;

public class Cannibal extends Kit {
    public Cannibal() {
        super("Cannibal", Material.ROTTEN_FLESH,
                new ItemStack[]{null},
                Arrays.asList("§7Dê fome em seu adversário batendo nele,",
                        "§7assim ele não conseguirá correr, e terá",
                        "§7que tomar sopas para recuperar sua fome."));
    }

    @EventHandler
    public void evenenandoOCara(EntityDamageByEntityEvent event) {
        if (Main.estado == GameState.PREGAME) return;
        if (event.isCancelled()) {
            return;
        }
        if (((event.getDamager() instanceof Player))
                && ((event.getEntity() instanceof LivingEntity))) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            Player p = (Player) event.getDamager();
            if (hasAbility(p)
                    && (new Random().nextInt(3) == 1)) {
                entity.addPotionEffect(new PotionEffect(
                        PotionEffectType.HUNGER, 5 * 20, 0), true);
            }
        }
    }


}
