package com.goodcraft.lobby.eventos;

import com.goodcraft.api.TestHack;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class GeneralEvents implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (LavaChallengeEvent.playersOnLavaChallenge.contains(e.getPlayer().getName())) {
            e.getItemDrop().remove();
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickupDrop(PlayerPickupItemEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        ItemStack i = e.getPlayer().getItemInHand();
        if (i.getType() != Material.AIR && i != null && i.getType() == Material.WRITTEN_BOOK && e.getAction().name().contains("RIGHT")) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPut(BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        e.getPlayer().updateInventory();
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (LavaChallengeEvent.playersOnLavaChallenge.contains(((Player) e.getWhoClicked()).getName())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.CUSTOM || e.getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            return;
        }
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (TestHack.testing.containsKey(p.getUniqueId())) {
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.CUSTOM || e.getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDayTimeChange(WeatherChangeEvent e) {
        if (e.toWeatherState() == false) {
            return;
        }
        e.setCancelled(true);
    }
}
