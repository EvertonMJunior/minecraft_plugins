package net.goodcraft.minigames.game;

import net.goodcraft.api.*;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SpectatorManager implements Listener {
    public final HashMap<UUID, Inventory> spectators = new HashMap<>();
    public final ArrayList<UUID> hidingSpectators = new ArrayList<>();
    public Inventory inv = null;
    public final HashMap<UUID, BukkitTask> lobbyTasks = new HashMap<>();

    private final Minigame MINIGAME;

    public SpectatorManager(Minigame minigame){
        this.MINIGAME = minigame;
    }

    public void put(Player p) {
        if(!MINIGAME.hasOption(Option.HAS_SPECTATORMODE)) return;

        UUID id = p.getUniqueId();
        if (spectators.containsKey(id)) {
            spectators.remove(id);
            hidingSpectators.remove(id);
        }
        saveOptionsInventory(p);

        p.setGameMode(GameMode.ADVENTURE);
        p.setAllowFlight(true);
        p.setFlying(true);

        p.teleport(p.getWorld().getSpawnLocation().clone().add(0.5, 0, 0.5));
        p.setCompassTarget(p.getWorld().getSpawnLocation().clone().add(0.5, 0, 0.5));

        p.getInventory().setItem(0, Item.item(Material.REDSTONE, 1, "§cSair"));
        p.getInventory().setItem(4, Item.item(Material.COMPASS, 1, "§3Jogadores"));
        p.getInventory().setItem(8, Item.item(Material.FEATHER, 1, "§6Opções"));

        p.spigot().setCollidesWithEntities(false);

        for (UUID uid : MINIGAME.getPlayers()) {
            Player pl = Bukkit.getPlayer(uid);
            if (pl == null) continue;
            pl.hidePlayer(p);
            p.showPlayer(pl);
        }
        for (UUID uid : spectators.keySet()) {
            Player pl = Bukkit.getPlayer(uid);
            if (pl == null) continue;
            if (hidingSpectators.contains(pl.getUniqueId())) {
                pl.hidePlayer(p);
            } else {
                pl.showPlayer(p);
            }
            p.showPlayer(pl);
        }
    }

    private void openOptionsInventory(Player p) {
        p.openInventory(spectators.get(p.getUniqueId()));
    }

    private void saveOptionsInventory(Player p) {
        Inventory inv = Bukkit.createInventory(p, 45, "Opções");

        inv.setItem(11, Item.addGlow(Item.item(Material.STAINED_CLAY, 1, "§aVelocidade 1", 13)));
        inv.setItem(13, Item.item(Material.STAINED_CLAY, 1, "§eVelocidade 2", 4));
        inv.setItem(15, Item.item(Material.STAINED_CLAY, 1, "§cVelocidade 3", 14));

        inv.setItem(30, Item.addGlow(Item.item(Material.INK_SACK, 1, "§aEspectadores ON", 10)));
        inv.setItem(32, Item.item(Material.INK_SACK, 1, "§cEspectadores OFF", 8));

        spectators.put(p.getUniqueId(), inv);
    }

    private void hideOrShowSpectators(Player p) {
        if (hidingSpectators.contains(p.getUniqueId())) {
            hidingSpectators.remove(p.getUniqueId());
            for (UUID uid : spectators.keySet()) {
                Player pl = Bukkit.getPlayer(uid);
                if (pl == null) continue;
                p.showPlayer(pl);
            }
        } else {
            hidingSpectators.add(p.getUniqueId());
            for (UUID uid : spectators.keySet()) {
                Player pl = Bukkit.getPlayer(uid);
                if (pl == null) continue;
                p.hidePlayer(pl);
            }
        }
    }

    @EventHandler
    public void onPlayersInvClick(InventoryClickEvent e) {
        if (!spectators.containsKey(e.getWhoClicked().getUniqueId())) {
            return;
        }
        e.setCancelled(true);
        e.setResult(Event.Result.DENY);
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();

        if (!p.getGameMode().equals(GameMode.ADVENTURE)) {
            return;
        }
        if (!inv.getName().equals("Jogadores")) {
            return;
        }
        ItemStack i = e.getCurrentItem();
        if (i == null) {
            return;
        }
        if (!i.getItemMeta().hasDisplayName()) {
            return;
        }
        Player toTp = Bukkit.getPlayerExact(ChatColor.stripColor(i.getItemMeta().getDisplayName()));
        if (toTp == null) {
            return;
        }
        p.teleport(toTp.getLocation().clone().add(0, 1, 0));
    }

    @EventHandler
    public void onOptionsInvClick(InventoryClickEvent e) {
        if (!spectators.containsKey(e.getWhoClicked().getUniqueId())) {
            return;
        }
        e.setCancelled(true);
        e.setResult(Event.Result.DENY);
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();

        if (!p.getGameMode().equals(GameMode.ADVENTURE)) {
            return;
        }
        if (!inv.getName().equals("Opções")) {
            return;
        }
        ItemStack i = e.getCurrentItem();
        if (i == null) {
            return;
        }

        if (i.getType().equals(Material.STAINED_CLAY)) {
            if (!Item.hasGlow(i)) {
                for (int z = 11; z < 16; z++) {
                    if (inv.getItem(z) == null) continue;
                    if (z == e.getSlot()) continue;
                    if (!Item.hasGlow(inv.getItem(z))) continue;
                    Item.removeGlow(inv.getItem(z));
                }
                Item.addGlow(i);
                int velocidade = Integer.valueOf(ChatColor.stripColor(i.getItemMeta().getDisplayName().replace("Velocidade ", "")));
                p.setFlySpeed(velocidade * 0.1F);
            }
            return;
        }

        if (i.getType().equals(Material.INK_SACK)) {
            int slot = (e.getSlot() == 30 ? 32 : 30);
            if (!Item.hasGlow(i)) {
                if (Item.hasGlow(inv.getItem(slot))) {
                    Item.removeGlow(inv.getItem(slot));
                }
                Item.addGlow(i);

                hideOrShowSpectators(p);
                return;
            }
            if (Item.hasGlow(i)) {
                Item.removeGlow(i);
            }
            Item.addGlow(inv.getItem(slot));

            hideOrShowSpectators(p);
            return;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = e.getItem();
        if (!spectators.containsKey(p.getUniqueId())) {
            return;
        }
        if (!p.getGameMode().equals(GameMode.ADVENTURE)) {
            return;
        }

        if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.CHEST)) {
            e.setCancelled(true);
            Utils.activateChest(e.getPlayer(), true, true,
                    e.getClickedBlock().getLocation().getBlockX(),
                    e.getClickedBlock().getLocation().getBlockY(),
                    e.getClickedBlock().getLocation().getBlockZ());
            return;
        }

        if (i != null && e.getAction().name().contains("RIGHT")) {
            if (i.getType() == Material.FEATHER) {
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);

                openOptionsInventory(p);
                return;
            }

            if (i.getType() == Material.REDSTONE) {
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);

                if (!Item.hasGlow(i)) {
                    Item.addGlow(i);
                    Message.GOOD.send(p, "Você será enviado ao Lobby em 3 segundos. Clique novamente na redstone para cancelar!");
                    lobbyTasks.put(p.getUniqueId(), new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!Item.hasGlow(p.getInventory().getItem(0))) {
                                cancel();
                                return;
                            }
                            BungeeUtils.sendToServer(p.getName(), "lobby1");
                        }
                    }.runTaskLater(MINIGAME.getPlugin(), 3 * 20L));
                    return;
                }
                Item.removeGlow(i);
                Message.GOOD.send(p, "Cancelado!");
                if (lobbyTasks.containsKey(p.getUniqueId())) {
                    lobbyTasks.get(p.getUniqueId()).cancel();
                    lobbyTasks.remove(p.getUniqueId());
                }
                return;
            }

            if (i.getType() == Material.COMPASS) {
                p.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onSecond(SecondsEvent e) {
        if (MINIGAME.getPlayers().isEmpty()) {
            return;
        }

        if(inv == null) return;

        inv.clear();
        for (int i = 0; i < MINIGAME.getPlayers().size(); i++) {
            Player pl = Bukkit.getPlayer(MINIGAME.getPlayers().get(i));
            inv.setItem(i, Item.getHead(pl.getName(), 1, "§a" + pl.getName()));
        }
    }
//
//    @EventHandler
//    public void onPlayerHitByPlayer(EntityDamageByEntityEvent e) {
//        Entity entityDamager = e.getDamager();
//        Entity entityDamaged = e.getEntity();
//
//        if (entityDamager instanceof Projectile) {
//            Projectile projectile = (Projectile) entityDamager;
//            if (entityDamaged instanceof Player && projectile.getShooter() instanceof Player) {
//                Player damaged = (Player) entityDamaged;
//                if (spectators.containsKey(damaged.getUniqueId())) {
//                    Vector velocity = projectile.getVelocity();
//                    damaged.teleport(damaged.getLocation().add(0, 2, 0));
//                    Projectile proj = projectile.getShooter().launchProjectile(projectile.getClass());
//                    proj.setVelocity(velocity);
//                    proj.setBounce(false);
//                    proj.setShooter(projectile.getShooter());
//                    e.setCancelled(true);
//                    projectile.remove();
//                }
//            }
//        }
//    }
}
