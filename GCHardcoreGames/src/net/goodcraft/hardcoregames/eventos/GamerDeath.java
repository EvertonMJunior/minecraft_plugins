package net.goodcraft.hardcoregames.eventos;

import net.goodcraft.GameMode;
import net.goodcraft.api.BungeeUtils;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Utils;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.sql.SQLStatus;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class GamerDeath implements Listener {
    @EventHandler
    public void death(PlayerDeathEvent e) {
        final Player p = e.getEntity();
        e.setDeathMessage(null);
        GamerDamage.combatLog.remove(p);
        if (Main.estado == GameState.PREGAME) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
                public void run() {
                    if (p.isDead()) {
                        p.spigot().respawn();
                    }

                    PreGameFight.in1v1.remove(p.getUniqueId());
                    LavaChallenge.playersOnLavaChallenge.remove(p.getUniqueId());
                    GamerJoin.preInv(p);
                    p.teleport(p.getWorld().getSpawnLocation());
                    if (p.getKiller() instanceof Player) {
                        p.getKiller().playSound(p.getKiller().getLocation(), Sound.ANVIL_LAND, 1f, 1f);

                        ItemStack cogu1 = new ItemStack(Material.BROWN_MUSHROOM, 16);
                        p.getKiller().getInventory().addItem(cogu1);

                        ItemStack cogu2 = new ItemStack(Material.RED_MUSHROOM, 16);
                        p.getKiller().getInventory().addItem(cogu2);
                    }
                }
            }, 1 / 100);
            return;
        }
        if (Rank.has(p.getUniqueId(), Rank.BRONZE) && Main.gameTime <= 300) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
                public void run() {
                    if (p.isDead()) {
                        p.spigot().respawn();
                    }
                    int x = 300 + new Random().nextInt(120);
                    int z = 300 + new Random().nextInt(120);
                    if (new Random().nextBoolean()) {
                        x = -x;
                    }
                    if (new Random().nextBoolean()) {
                        z = -z;
                    }
                    p.setCanPickupItems(false);
                    Location loc = p.getWorld().getHighestBlockAt(x, z).getLocation().clone().add(0, 4, 0);
                    p.teleport(loc);
                    p.setCanPickupItems(true);
                    p.updateInventory();
                    p.setFallDistance(0);
                    p.setFoodLevel(20);
                    p.setHealth(20.0D);
                    p.getInventory().addItem(new ItemStack(Material.COMPASS));
                }
            }, 1 / 100);
        } else if (Main.players.contains(p.getUniqueId())) {
            Main.players.remove(p.getUniqueId());
            SQLStatus.addStatus(p.getUniqueId(), GameMode.HARDCOREGAMES, "deaths", 1);
            Main.killstreak.remove(p.getUniqueId());

            EntityDamageEvent cause2 = p.getLastDamageCause();
            EntityDamageEvent.DamageCause cause = cause2.getCause();
            if (cause == EntityDamageEvent.DamageCause.FALL) {
                String msg = doubleMsg("nunca ouviu falar em escadas",
                        "caiu de um lugar alto");
                broadCastDeath(p, msg);
            } else if (cause == EntityDamageEvent.DamageCause.CONTACT) {
                String msg = doubleMsg("morreu por um cacto",
                        "morreu por um cacto");
                broadCastDeath(p, msg);
            } else if (cause == EntityDamageEvent.DamageCause.DROWNING) {
                String msg = doubleMsg("morreu afogado",
                        "morreu afogado");
                broadCastDeath(p, msg);
            } else if (cause == EntityDamageEvent.DamageCause.LAVA) {
                String msg = doubleMsg("tentou nadar na lava",
                        "estava brincando muito perto da lava");
                broadCastDeath(p, msg);
            } else if (cause == EntityDamageEvent.DamageCause.SUFFOCATION) {
                broadCastDeath(p, "morreu sufocado");
            } else if (cause == EntityDamageEvent.DamageCause.FIRE) {
                broadCastDeath(p, "tostou como uma carne assada");
            } else if (cause == EntityDamageEvent.DamageCause.POISON) {
                broadCastDeath(p, "morreu envenenado");
            } else if (cause == EntityDamageEvent.DamageCause.FIRE_TICK) {
                broadCastDeath(p, "queimado");
            } else if (cause == EntityDamageEvent.DamageCause.LIGHTNING) {
                broadCastDeath(p, "morreu por raios");
            } else if (cause == EntityDamageEvent.DamageCause.STARVATION) {
                broadCastDeath(p, "morreu de fome");
            } else if (cause == EntityDamageEvent.DamageCause.WITHER) {
                broadCastDeath(p, "morreu em envenenado");
            } else if (cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                broadCastDeath(p, "morreu explodido");
            } else if (cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                broadCastDeath(p, "morreu explodido");
            } else if (cause == EntityDamageEvent.DamageCause.CUSTOM) {
                broadCastDeath(p, "morreu pela borda do mundo");
            } else if (cause2.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                Entity entity = ((EntityDamageByEntityEvent) cause2)
                        .getDamager();
                if (entity.getType().equals(EntityType.PLAYER)) {
                    Player killer = (Player) entity;
                    Main.addKillstreak(killer);
                    SQLStatus.addCoins(killer.getUniqueId(), 100);
                    String kill;
                    if (!KitManager.hasAnyKit(killer)) {
                        kill = killer.getName() + "§7(Nenhum)§e";
                    } else {
                        kill = killer.getName() + "§7(" + KitManager.getKit(killer).toString() + ")";

                    }
                    String id = killer.getItemInHand().getType().name();
                    broadCastDeath(p, "foi morto por " + kill + " usando "
                            + getItem(id));
                } else {
                    String msg = doubleMsg("morreu por um monstro",
                            "estava brincando com um monstro");
                    broadCastDeath(p, msg);
                }
            } else {
                broadCastDeath(p, "morreu!");
            }
        }
    }

    public static void broadCastDeath(final Player p, String msg) {
        String message;
        if (!KitManager.hasAnyKit(p)) {
            message = "§e" + p.getName() + "§7(Nenhum)§e";
        } else {
            message = "§e" + p.getName() + "§7(" + KitManager.getKit(p).toString() + ")§e";

        }
        Utils.broadcast(message + " " + msg);
        KitManager.removeKit(p);

        if (Rank.has(p.getUniqueId(), Rank.MOD)) {
            new BukkitRunnable() {
                public void run() {
                    if (p.isOnline()) {
                        p.chat("/admin");
                        p.setHealth(p.getMaxHealth());
                    }
                }
            }.runTask(Main.getMain());
        } else {
            if (p.isOnline()) {
                BungeeUtils.sendToServer(p.getName(), "lobby1");
            }
        }
    }

    public String doubleMsg(String msg, String msg2) {
        if (new Random().nextBoolean()) {
            return msg;
        } else {
            return msg2;
        }
    }

    public static String getItem(String name) {
        String prefix = "";
        String Name = "";
        switch (name) {
            case "AIR":
                prefix = "seus";
                Name = "Punhos";
                break;
            case "DIRT":
                prefix = "sua";
                Name = "Terra";
                break;
            case "IRON_AXE":
                prefix = "seu";
                Name = "Machado de Ferro";
                break;
            case "FLINT_AND_STEEL":
                prefix = "seu";
                Name = "Isqueiro";
                break;
            case "BOW":
                prefix = "seu";
                Name = "Arco de flechas";
                break;
            case "IRON_SWORD":
                prefix = "sua";
                Name = "Espada de Ferro";
                break;
            case "WOOD_SWORD":
                prefix = "sua";
                Name = "Espada de Madeira";
                break;
            case "WOOD_AXE":
                prefix = "seu";
                Name = "Machado de Madeira";
                break;
            case "STONE_SWORD":
                prefix = "sua";
                Name = "Espada de Pedra";
                break;
            case "STONE_AXE":
                prefix = "seu";
                Name = "Machado de Pedra";
                break;
            case "DIAMOND_SWORD":
                prefix = "sua";
                Name = "Espada de Diamante";
                break;
            case "DIAMOND_AXE":
                prefix = "seu";
                Name = "Machado de Diamante";
                break;
            case "BOWL":
                prefix = "seu";
                Name = "Pote";
                break;
            case "MUSHROOM_SOUP":
                prefix = "sua";
                Name = "Sopa";
                break;
            case "GOLD_SWORD":
                prefix = "sua";
                Name = "Espada de Ouro";
                break;
            case "GOLD_AXE":
                prefix = "seu";
                Name = "Machado de Ouro";
                break;
            case "COMPASS":
                prefix = "sua";
                Name = "Bússola";
                break;
            default:
                prefix = "sua";
                Name = toReadable(name);
                break;
        }
        return prefix + " " + Name;
    }

    public static String toReadable(String string) {
        String[] names = string.split("_");
        for (int i = 0; i < names.length; i++) {
            names[i] = (names[i].substring(0, 1) + names[i].substring(1)
                    .toLowerCase());
        }
        return StringUtils.join(names, " ");
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent e) {
        if (!Main.players.contains(e.getPlayer().getUniqueId())) {
            e.setRespawnLocation(e.getPlayer().getLocation());
            if (e.getPlayer().getAllowFlight()) {
                e.getPlayer().setFlying(true);
            }
        }
    }

}
