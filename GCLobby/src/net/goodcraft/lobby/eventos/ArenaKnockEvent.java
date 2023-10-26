package net.goodcraft.lobby.eventos;

import net.goodcraft.GameMode;
import net.goodcraft.Main;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class ArenaKnockEvent implements Listener {

    public static ArrayList<String> onArenaKnock = new ArrayList<>();
    public static ArrayList<String> cd = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block b = e.getFrom().getBlock();
        Block b2 = e.getFrom().subtract(0, 1, 0).getBlock();
        Block b3 = e.getFrom().subtract(0, 2, 0).getBlock();

        if (b.getType() != Material.ENDER_PORTAL_FRAME && b2.getType() != Material.ENDER_PORTAL_FRAME && b3.getType() != Material.ENDER_PORTAL_FRAME) {
            return;
        }

        if (!p.getLocation().getWorld().equals(GameMode.LOBBY.getLocation().getWorld()) ||
                p.getLocation().distance(GameMode.LOBBY.getLocation()) > 400) {
            return;
        }

        if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()) {
            return;
        }

        if (cd.contains(p.getName())) {
            return;
        }

        if (!onArenaKnock.contains(p.getName())) {
            onArenaKnock.add(p.getName());
            Message.INFO.send(p, "Você entrou na Arena Knock!");
            p.teleport(p.getLocation().clone().add(0, 0.5, 0));
            p.setVelocity(new Vector(-2D, -2D, 0));
            p.getInventory().clear();
            p.getInventory().setItem(0, Item.item(Material.STICK, 1, "§eArena Knock", Enchantment.KNOCKBACK, 1));
            p.getInventory().setHeldItemSlot(0);
            cd.add(p.getName());

            new BukkitRunnable() {
                @Override
                public void run() {
                    cd.remove(p.getName());
                }
            }.runTaskLater(Main.getPlugin(), 20L);

            return;
        }

        cd.add(p.getName());

        new BukkitRunnable() {
            @Override
            public void run() {
                cd.remove(p.getName());
            }
        }.runTaskLater(Main.getPlugin(), 10L);

        p.teleport(p.getLocation().clone().add(0, 0.5, 0));
        p.setVelocity(new Vector(-2D, -2D, 0));
        Message.INFO.send(p, "Para sair, morra na lava.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();
        Player d = (Player) e.getDamager();

        if (!onArenaKnock.contains(d.getName())) {
            return;
        }

        if (d.getItemInHand().getType() != Material.STICK) {
            return;
        }

        if (!onArenaKnock.contains(p.getName())) {
            return;
        }

        e.setCancelled(false);
        e.setDamage(0D);
    }

    @EventHandler
    public void onDeath(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();

        if ((p.getHealth() - e.getOriginalDamage(EntityDamageEvent.DamageModifier.BASE)) > 0) {
            return;
        }

        if (onArenaKnock.contains(p.getName())) {
            onArenaKnock.remove(p.getName());
        } else {
            return;
        }

        JoinEvent.setInitialItems(p);
        p.teleport(GameMode.LOBBY.getLocation());
        p.setHealth(20D);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.setFireTicks(1);
            }
        }.runTask(Main.getPlugin());

        e.setDamage(0D);
    }

}
