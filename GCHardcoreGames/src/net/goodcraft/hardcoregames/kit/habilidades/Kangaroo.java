package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
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
                new ItemStack[]{Item.item(Material.FIREWORK, 1, "§3Kangaroo")},
                Arrays.asList("§7Use um foguete para se locomover",
                        "§7rapidamente, facilitando correr",
                        "§7atrás de seus adversários!"));
    }

    @EventHandler
    public void kang(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (hasAbility(p) && (p.getItemInHand().getType() == Material.FIREWORK)) {
            if (p.getItemInHand().hasItemMeta()) {
                if (p.getItemInHand().getItemMeta().hasDisplayName()) {
                    if (!p.getItemInHand().getItemMeta().getDisplayName().contains(toString())) {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
            e.setCancelled(true);
            if (hasCooldown(p)) {
                if ((this.jumps.containsKey(p)) && (this.jumps.get(p) >= 1)) {
                    if (p.isSneaking()) {
                        Vector v1 = p.getLocation().getDirection().multiply(0.4D).setY(0.3D);
                        p.setVelocity(v1);

                        p.setFallDistance(-2F);

                        this.jumps.put(p, this.jumps.get(p) - 1);
                    } else {
                        Vector v2 = p.getLocation().getDirection().multiply(0.035D).setY(0.4D);
                        p.setVelocity(v2);

                        p.setFallDistance(-2F);

                        this.jumps.put(p, this.jumps.get(p) - 1);
                    }
                }
                return;
            }
            if ((this.jumps.containsKey(p)) && (this.jumps.get(p) >= 1)) {
                if (p.isSneaking()) {
                    Vector v1 = p.getLocation().getDirection().multiply(1.5D).setY(0.6D);
                    p.setVelocity(v1);

                    p.setFallDistance(-2F);

                    this.jumps.put(p, this.jumps.get(p) - 1);
                } else {
                    Vector v2 = p.getLocation().getDirection().multiply(0.35D).setY(0.9D);
                    p.setVelocity(v2);

                    p.setFallDistance(-2F);

                    this.jumps.put(p, this.jumps.get(p) - 1);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void Move(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (hasAbility(p)) {
            if ((this.jumps.containsKey(p)) && (this.jumps.get(p) > 2)) {
                this.jumps.put(p, 2);
            }

            if (!this.jumps.containsKey(p)) {
                this.jumps.put(p, 2);
                return;
            }

            if (p.isOnGround()) {
                if (((Integer) this.jumps.get(p)).intValue() != 2) {
                    this.jumps.put(p, Integer.valueOf(2));
                }
            } else if (((Integer) this.jumps.get(p)).intValue() == 2) {
                {
                    this.jumps.remove(p);
                    this.jumps.put(p, Integer.valueOf(1));
                }
            }
        }
    }

    @EventHandler
    public void dmg(EntityDamageEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if ((e.getEntity() instanceof Player)) {
            Player p = (Player) e.getEntity();
            if (hasAbility(p)) {
                if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (e.getDamage() > 7.0D) {
                        e.setDamage(7.0D);
                    }
                }
            }
        }
    }

    @EventHandler
    public void Cooldown(EntityDamageByEntityEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player))) {
            final Player player = (Player) e.getEntity();

            if (hasAbility(player)) {
                addCooldown(player, 5);
            }
        }
    }


}
