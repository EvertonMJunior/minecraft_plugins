package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.api.Utils;
import net.goodcraft.hardcoregames.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class Explosion extends Kit {
    public Explosion() {
        super("Explosion", Material.TNT,
                new ItemStack[]{Item.item(Material.TNT, 1, "§3Explosion")},
                Arrays.asList("§7O Kit será usado com uma TNT.",
                        "§7Pressionando o botão direito você",
                        "§7joga a TNT longe, e automaticamente",
                        "§7ela irá explodir como uma TNT normal."));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();
        if (i == null) return;
        if (!hasAbility(p)) return;
        if (Main.estado == GameState.PREGAME) return;
        if (!Utils.getDisplayName(i).equalsIgnoreCase(Utils.getDisplayName(getItems()[0]))) return;
        if (hasCooldown(p)) {
            mensagemcooldown(p);
            return;
        }

        addCooldown(p, 40);
        Vector v = p.getEyeLocation().getDirection().multiply(1D).setY(1D);
        TNTPrimed tnt = p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
        tnt.setVelocity(v);
        tnt.setIsIncendiary(true);
        tnt.setFuseTicks(40);
        tnt.setCustomName("§4Explodindo! Saia de perto!");
        tnt.setCustomNameVisible(true);
        e.setCancelled(true);
    }

    @EventHandler
    public void onTNTDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (!hasAbility(p)) return;
        if (Main.estado == GameState.PREGAME) return;
        if (e.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) return;
        e.setDamage(0D);
    }

}
