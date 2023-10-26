package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.hardcoregames.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Endermage extends Kit {
    public Endermage() {
        super("Endermage", Material.ENDER_PORTAL_FRAME,
                new ItemStack[]{Item.item(Material.ENDER_PORTAL_FRAME, 1, "§3Endermage")},
                Arrays.asList("§7Use um portal para puxar os outros. Para",
                        "§7puxar jogadores, coloque o portal no chão."));
    }

    public static Map<UUID, Integer> invenc = new HashMap<>();
    private transient ArrayList<Block> endermages = new ArrayList<>();

    private boolean isEnderable(Location portal, Location player) {
        return (Math.abs(portal.getX() - player.getX()) < 3.0D)
                && (Math.abs(portal.getZ() - player.getZ()) < 3.0D)
                && (Math.abs(portal.getY() - player.getY()) >= 3.5D);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void kitMage(PlayerInteractEvent e) {
        final Player mage = e.getPlayer();
        final Block b = e.getClickedBlock();
        ItemStack item = e.getItem();
        if (hasAbility(mage)
                && mage.getItemInHand().getType().equals(Material.ENDER_PORTAL_FRAME)) {
            e.setCancelled(true);
            if (endermages.contains(b)) {
                mage.updateInventory();
                return;
            }
            Block b1 = b.getLocation().add(0, 1, 0).getBlock();
            Block b2 = b.getLocation().add(0, 2, 0).getBlock();
            if (b1.getType() != Material.AIR && b2.getType() != Material.AIR) {
                mage.sendMessage("§7Você precisa ter 2 blocos livres em cima para puxar!");
                mage.updateInventory();
                return;
            } else {
                endermages.add(b);
                item.setAmount(0);
                if (item.getAmount() == 0) {
                    e.getPlayer().setItemInHand(new ItemStack(0));
                }
                final Location portal = b.getLocation().clone()
                        .add(0.5D, 0.5D, 0.5D);
                final Material material = b.getType();
                final byte dataValue = b.getData();
                portal.getBlock()
                        .setTypeId(Material.ENDER_PORTAL_FRAME.getId());
                for (int i = 0; i <= 5; i++) {
                    final int ii = i;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(),
                            new Runnable() {
                                public void run() {
                                    if (portal.getBlock().getType() == Material.ENDER_PORTAL_FRAME) {
                                        if (ii < 5) {
                                            for (UUID gamer : Main.players) {
                                                final Player pl = Bukkit.getPlayer(gamer);
                                                if ((pl != mage.getPlayer())
                                                        && (isEnderable(
                                                        portal,
                                                        pl.getLocation()))
                                                        && (!hasAbility(pl))) {
                                                    if (pl.getLocation()
                                                            .distance(portal) > 3.5D) {
                                                        pl.teleport(portal
                                                                .clone().add(
                                                                        0.0D,
                                                                        0.5D,
                                                                        0.0D));
                                                        Message.INFO.send(mage, "Puxado!");
                                                        Message.INFO.send(mage, "Você está invencível por 5 segundos!");
                                                        Message.INFO.send(pl, "Puxado!");
                                                        Message.INFO.send(pl, "Você está invencível por 5 segundos!");
                                                        addMage(mage);
                                                        addMage(pl);
                                                        mage.teleport(portal
                                                                .clone().add(
                                                                        0.0D,
                                                                        0.5D,
                                                                        0.0D));
                                                        mage.getInventory()
                                                                .addItem(new ItemStack(Material.PORTAL));
                                                        portal.getBlock()
                                                                .setTypeIdAndData(
                                                                        material.getId(),
                                                                        dataValue,
                                                                        true);
                                                        endermages.remove(b);
                                                    }
                                                }

                                            }

                                        }

                                        if (ii == 5) {
                                            portal.getBlock().setTypeIdAndData(
                                                    material.getId(),
                                                    dataValue, true);
                                            mage.getInventory().addItem(
                                                    new ItemStack(
                                                            Material.PORTAL));
                                            endermages.remove(b);
                                        }
                                    }
                                }
                            }, i * 20);
                }

            }
        }
    }

    @EventHandler
    public void onDmg(EntityDamageEvent e) {
        if (Main.estado == GameState.PREGAME) return;
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (invenc.containsKey(p.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDmg2(EntityDamageByEntityEvent e) {
        if (Main.estado == GameState.PREGAME) return;
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (invenc.containsKey(p.getUniqueId())) {
                e.setCancelled(true);
            }
        }
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (invenc.containsKey(p.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void addMage(final Player p) {
        if (invenc.containsKey(p.getUniqueId())) {
            Bukkit.getServer().getScheduler()
                    .cancelTask(invenc.get(p.getUniqueId()));
        }
        invenc.put(
                p.getUniqueId(), Bukkit.getServer().getScheduler()
                        .scheduleAsyncDelayedTask(Main.getMain(), new Runnable() {
                            public void run() {
                                invenc.remove(p.getUniqueId());
                                Message.INFO.send(p, "Sua invencibilidade acabou!");
                            }
                        }, 5 * 20L));
    }
}
