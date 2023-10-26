package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.api.SecondsEvent;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class Poseidon extends Kit {
    public Poseidon() {
        super("Poseidon", Material.WATER_BUCKET,
                new ItemStack[]{},
                Arrays.asList(
                        "§7Seja mais veloz e forte",
                        "§7quando dentro da água,",
                        "§7além de que não morrerás afogado!"
                ));
        setPrice(2500);
    }

    @EventHandler
    public void onSecond(SecondsEvent e){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(!hasAbility(p)) continue;
            if(!p.getLocation().getBlock().getType().name().contains("WATER")) continue;
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1, 1, false, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1, 1, false, false));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDronwningDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(e.isCancelled()) return;

        Player p = (Player) e.getEntity();

        if(!hasAbility(p)) return;
        if(e.getCause() != EntityDamageEvent.DamageCause.DROWNING) return;
        e.setCancelled(true);
    }
}
