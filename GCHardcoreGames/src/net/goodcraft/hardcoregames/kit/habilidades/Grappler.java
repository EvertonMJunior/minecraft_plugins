package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.hardcoregames.utils.CopyOfFishingHook;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Grappler extends Kit {
    public Grappler() {
        super("Grappler", Material.LEASH,
                new ItemStack[]{Item.item(Material.LEASH, 1, "§3Grappler")},
                Arrays.asList("§7Jogue sua corda para onde",
                        "§7quiser e ande rapidamente pelo",
                        "§7mundo do HG com ela!"));
    }

    public static ArrayList<UUID> nerf = new ArrayList<>();

    public static HashMap<UUID, CopyOfFishingHook> hooks = new HashMap<>();

    @EventHandler
    public void nerf(EntityDamageByEntityEvent event) {
        if ((event.isCancelled())) {
            return;
        }
        if (((event.getEntity() instanceof Player))
                && ((event.getDamager() instanceof Player))) {
            final Player p1 = (Player) event.getEntity();
            if (!nerf.contains(p1.getUniqueId())) {
                nerf.add(p1.getUniqueId());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        nerf.remove(p1.getUniqueId());
                    }
                }.runTaskLater(Main.getPlugin(), 3 * 20L);
            }
        }
    }

    @EventHandler
    public void onSlotChange(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        if (!hooks.containsKey(p.getUniqueId())) return;

        hooks.get(p.getUniqueId()).remove();
        hooks.remove(p.getUniqueId());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        ItemStack iIH = p.getItemInHand();
        if (iIH == null) return;
        if (iIH.getType() != Material.LEASH) return;
        if (!hasAbility(p)) return;
        if (!hooks.containsKey(p.getUniqueId())) return;

        hooks.get(p.getUniqueId())
                .remove();
        hooks.remove(p.getUniqueId());
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onLeash(PlayerLeashEntityEvent e) {
        Player p = e.getPlayer();
        if (e.getEntity() instanceof Player && hooks.containsKey(p.getUniqueId()) && e.getEntity() ==
                hooks.get(p.getUniqueId()).owner) {
            return;
        }

        if (p.getItemInHand().getType().equals(Material.LEASH) && hasAbility(p)) {
            e.setCancelled(true);
            e.getPlayer().updateInventory();
            if (!hooks.containsKey(p.getUniqueId())) {
                return;
            }
            if (!hooks.get(p.getUniqueId()).isHooked()) {
                return;
            }

            double d = hooks.get(p.getUniqueId())
                    .getBukkitEntity().getLocation().distance(p.getLocation());
            double t = d;
            double v_x = (1.0D + 0.03000000000000001D * t)
                    * (hooks.get(p.getUniqueId())
                    .getBukkitEntity().getLocation().getX() - p
                    .getLocation().getX()) / t;

            double v_z = (1.0D + 0.03000000000000001D * t)
                    * (hooks.get(p.getUniqueId())
                    .getBukkitEntity().getLocation().getZ() - p
                    .getLocation().getZ()) / t;
            if (p.isOnGround()) {
                hooks.get(p.getUniqueId()).getBukkitEntity().getLocation().getY();
                p.getLocation().getY();
            }
            double v_y;
            if (hooks.get(p.getUniqueId()).getBukkitEntity()
                    .getLocation().getY()
                    - p.getLocation().getY() < 0.5D) {
                v_y = p.getVelocity().getY();
            } else {
                v_y = (0.9D + 0.03D * t)
                        * ((hooks.get(p.getUniqueId())
                        .getBukkitEntity().getLocation().getY() - p
                        .getLocation().getY()) / t);
            }
            Vector v = p.getVelocity();
            v.setX(v_x);
            v.setY(v_y);
            v.setZ(v_z);
            p.setVelocity(v);
            p.setFallDistance(0.0F);

            p.getWorld().playSound(p.getLocation(), Sound.STEP_GRAVEL, 3.0F, 1.0F);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() != null && p.getItemInHand().getType() == Material.LEASH && hasAbility(p)) {
            e.setCancelled(true);

            if ((e.getAction() == Action.LEFT_CLICK_AIR)
                    || (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
                if (nerf.contains(p.getUniqueId())) {
                    Message.INFO.send(p, "Você está em combate!");
                    return;
                }
                if (hooks.containsKey(p.getUniqueId())) {
                    hooks.get(p.getUniqueId()).remove();
                }
                CopyOfFishingHook nmsHook = new CopyOfFishingHook(p.getWorld(), ((CraftPlayer)p).getHandle());
                nmsHook.spawn(p.getEyeLocation().add(
                        p.getLocation().getDirection().getX(),
                        p.getLocation().getDirection().getY(),
                        p.getLocation().getDirection().getZ()));
                nmsHook.move(p.getLocation().getDirection().getX() * 5.0D, p
                        .getLocation().getDirection().getY() * 5.0D, p
                        .getLocation().getDirection().getZ() * 5.0D);
                hooks.put(p.getUniqueId(), nmsHook);
            } else {
                if (nerf.contains(p.getUniqueId())) {
                    Message.INFO.send(p, "Você está em combate!");
                    return;
                }
                if (!hooks.containsKey(p.getUniqueId())) {
                    return;
                }
                if (!hooks.get(p.getUniqueId()).isHooked()) {
                    Message.INFO.send(p, "Seu gancho ainda não prendeu em nada!");
                    CopyOfFishingHook gh = hooks.get(p.getUniqueId());
                    gh.move(gh.motX, gh.motY - 2.0D, gh.motY);
                    return;
                }
                double d = hooks.get(p.getUniqueId())
                        .getBukkitEntity().getLocation()
                        .distance(p.getLocation());
                double t = d;
                double v_x = (1.0D + 0.03000000000000001D * t)
                        * (hooks.get(p.getUniqueId())
                        .getBukkitEntity().getLocation().getX() - p
                        .getLocation().getX()) / t;

                double v_z = (1.0D + 0.03000000000000001D * t)
                        * (hooks.get(p.getUniqueId())
                        .getBukkitEntity().getLocation().getZ() - p
                        .getLocation().getZ()) / t;
                if (p.isOnGround()) {
                    hooks.get(p.getUniqueId())
                            .getBukkitEntity().getLocation().getY();
                    p.getLocation().getY();
                }
                double v_y;
                if (hooks.get(p.getUniqueId())
                        .getBukkitEntity().getLocation().getY()
                        - p.getLocation().getY() < 0.5D) {
                    v_y = p.getVelocity().getY();
                } else {
                    v_y = (0.9D + 0.03D * t)
                            * ((hooks.get(p.getUniqueId())
                            .getBukkitEntity().getLocation().getY() - p
                            .getLocation().getY()) / t);
                }
                Vector v = p.getVelocity();
                v.setX(v_x);
                v.setY(v_y);
                v.setZ(v_z);
                p.setVelocity(v);
                p.setFallDistance(0.0F);
                if (p.getLocation().getY() < hooks.get(p.getUniqueId()).getBukkitEntity().getLocation().getY()) {
                    p.setFallDistance(0.0F);
                }
                p.getWorld().playSound(p.getLocation(), Sound.STEP_GRAVEL,
                        3.0F, 1.0F);
            }
        }
    }
}
