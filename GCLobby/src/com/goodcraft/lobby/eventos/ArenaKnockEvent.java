package com.goodcraft.lobby.eventos;

import com.goodcraft.GameMode;
import com.goodcraft.Main;
import com.goodcraft.api.Item;
import com.goodcraft.api.Message;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ArenaKnockEvent implements Listener {

    public static ArrayList<String> onArenaKnock = new ArrayList<>();
    public static ArrayList<String> cd = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block b = e.getFrom().subtract(0, 1, 0).getBlock();
        Block b2 = e.getFrom().subtract(0, 2, 0).getBlock();
        Block b3 = e.getFrom().subtract(0, 3, 0).getBlock();

        if (b.getType() != Material.MOSSY_COBBLESTONE && b2.getType() != Material.MOSSY_COBBLESTONE && b3.getType() != Material.MOSSY_COBBLESTONE) {
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
            p.getInventory().clear();
            p.getInventory().setItem(0, Item.addGlow(Item.item(Material.STICK, 1, "§eArena Knock")));
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
        e.setCancelled(true);
        onArenaKnock.remove(p.getName());
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
        
        if(!onArenaKnock.contains(d.getName())){
            return;
        }

        if (d.getItemInHand().getType() != Material.STICK) {
            return;
        }
        
        if(!onArenaKnock.contains(p.getName())){
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
        p.setFireTicks(1);

        e.setDamage(0D);
    }

}
