package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;


public class Anchor extends Kit {
    public Anchor() {
        super("Anchor", Material.ANVIL,
                new ItemStack[]{null},
                Arrays.asList("§7O kit Anchor faz tanto seu adversário",
                        "§7quanto você não levarem repulsão/knockback!"));
    }

    @EventHandler
    public void Damage(final EntityDamageByEntityEvent e) {
        if ((e.getEntity() instanceof Player)) {
            if (Main.estado == GameState.PREGAME) return;
            final Player p = (Player) e.getEntity();
            if (!(e.getDamager() instanceof Player)) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
                    public void run() {
                        p.setVelocity(new Vector());
                    }
                });
                return;
            }
            final Player d = (Player) e.getDamager();
            if ((hasAbility(p)) || hasAbility(d)) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
                    public void run() {
                        p.setVelocity(new Vector());
                        d.playSound(e.getDamager().getLocation(), Sound.ANVIL_LAND, 1, 1);
                    }
                });
            }
        }
    }

}
