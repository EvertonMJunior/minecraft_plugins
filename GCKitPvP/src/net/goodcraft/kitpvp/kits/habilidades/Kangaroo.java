package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;

public class Kangaroo extends Kit {
    HashMap<Player, Integer> jumps = new HashMap<Player, Integer>();

    public Kangaroo() {
        super("Kangaroo", Material.FIREWORK,
                new ItemStack[]{
                        Item.item(Material.FIREWORK, 1, "§3Kangaroo")
                },
                Arrays.asList(
                        "§7Use um foguete para se locomover",
                        "§7rapidamente, assim correr",
                        "§7atrás de seus adversários",
                        "§7será mais fácil e rápido!"
                ));
    }

    @EventHandler
    public void jumpEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!hasAbility(p)) return;
        if (p.getItemInHand() == null) return;
        if (p.getItemInHand().getType() != Material.FIREWORK) return;
        if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasDisplayName() &&
                (!p.getItemInHand().getItemMeta().getDisplayName().contains(toString()))) return;

        e.setCancelled(true);

        if (hasCooldown(p)) {
            if ((jumps.containsKey(p)) && (jumps.get(p) >= 1)) {
                if (p.isSneaking()) {
                    Vector v1 = p.getLocation().getDirection().multiply(0.4D).setY(0.3D);
                    p.setVelocity(v1);

                    p.setFallDistance(-2F);

                    jumps.put(p, jumps.get(p) - 1);
                } else {
                    Vector v2 = p.getLocation().getDirection().multiply(0.035D).setY(0.4D);
                    p.setVelocity(v2);

                    p.setFallDistance(-2F);

                    jumps.put(p, jumps.get(p) - 1);
                }
            }
            return;
        }
        if ((jumps.containsKey(p)) && (jumps.get(p) >= 1)) {
            if (p.isSneaking()) {
                Vector v1 = p.getLocation().getDirection().multiply(1.5D).setY(0.6D);
                p.setVelocity(v1);

                p.setFallDistance(-2F);

                jumps.put(p, jumps.get(p) - 1);
            } else {
                Vector v2 = p.getLocation().getDirection().multiply(0.35D).setY(0.9D);
                p.setVelocity(v2);

                p.setFallDistance(-2F);

                jumps.put(p, jumps.get(p) - 1);
            }
        }

    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!hasAbility(p)) return;

        int value = jumps.get(p);

        if ((jumps.containsKey(p)) && (value > 2)) {
            jumps.put(p, 2);
        }

        if (!jumps.containsKey(p)) {
            jumps.put(p, 2);
            return;
        }

        if (p.isOnGround()) {
            if (value != 2) {
                jumps.put(p, 2);
            }
        } else if (value == 2) {
            jumps.remove(p);
            jumps.put(p, 1);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        if (!hasAbility(p)) return;

        if (e.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (e.getDamage() <= 7D) return;
        e.setDamage(7.0D);
    }

    @EventHandler
    public void addCooldown(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) return;
        final Player p = (Player) e.getEntity();

        if (!hasAbility(p)) return;
        addCooldown(p, 5);
    }

}
