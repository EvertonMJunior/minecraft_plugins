package net.goodcraft.minigames.eventos;

import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.api.SecondsEvent;
import net.goodcraft.api.Utils;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.Stat;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.KitManager;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

public class DeathEvent implements Listener {

    private final Minigame MINIGAME;

    public DeathEvent(Minigame minigame) {
        this.MINIGAME = minigame;
    }

    public void broadcastDeath(final Player p, String msg) {
        String message;
        if (!KitManager.hasAnyKit(p)) {
            message = p.getName() + "§7(Nenhum)§e ";
        } else {
            message = p.getName() + "§7(" + KitManager.getKit(p).toString() + ")§e ";

        }
        if (!MINIGAME.hasOption(Option.UNLIMITED_LIFES)) {
            Utils.broadcast("§6[" + MINIGAME.getName() + "]§e " + message + msg + "§e!");
        } else {
            Message.ERROR.send(p, "Você " + msg + ".");
            SQLStatus.removeCoins(p.getUniqueId(), 50);
        }
        KitManager.removeKit(p);

        new BukkitRunnable() {
            public void run() {
                if (!p.isOnline()) return;
                p.teleport(p.getLocation().getWorld().getSpawnLocation().clone().add(0.5, 0, 0.5));
                MINIGAME.getSpectatorManager().put(p);
            }
        }.runTaskLater(MINIGAME.getPlugin(), 2L);
    }

    @EventHandler
    public void death(PlayerDeathEvent e) {
        final Player p = e.getEntity();
        e.setDeathMessage(null);
        DamageEvent.combatLog.remove(p.getUniqueId());
        if (MINIGAME.isPlayer(p.getUniqueId())) {
            if (!MINIGAME.hasOption(Option.UNLIMITED_LIFES)) MINIGAME.getPlayers().remove(p.getUniqueId());

            for (Stat s : MINIGAME.getStatsOnDeath()) {
                s.addStat(p.getUniqueId());
            }

            EntityDamageEvent cause2 = p.getLastDamageCause();
            EntityDamageEvent.DamageCause cause = cause2.getCause();
            if (cause == EntityDamageEvent.DamageCause.FALL) {
                String msg = doubleMsg("nunca ouviu falar em escadas",
                        "caiu de um lugar alto");
                broadcastDeath(p, msg);
            } else if (cause == EntityDamageEvent.DamageCause.CONTACT) {
                String msg = doubleMsg("morreu por um cacto",
                        "morreu por um cacto");
                broadcastDeath(p, msg);
            } else if (cause == EntityDamageEvent.DamageCause.DROWNING) {
                String msg = doubleMsg("morreu afogado",
                        "morreu afogado");
                broadcastDeath(p, msg);
            } else if (cause == EntityDamageEvent.DamageCause.LAVA) {
                String msg = doubleMsg("tentou nadar na lava",
                        "estava brincando muito perto da lava");
                broadcastDeath(p, msg);
            } else if (cause == EntityDamageEvent.DamageCause.SUFFOCATION) {
                broadcastDeath(p, "morreu sufocado");
            } else if (cause == EntityDamageEvent.DamageCause.FIRE) {
                broadcastDeath(p, "tostou como uma carne assada");
            } else if (cause == EntityDamageEvent.DamageCause.POISON) {
                broadcastDeath(p, "morreu envenenado");
            } else if (cause == EntityDamageEvent.DamageCause.FIRE_TICK) {
                broadcastDeath(p, "queimado");
            } else if (cause == EntityDamageEvent.DamageCause.LIGHTNING) {
                broadcastDeath(p, "morreu por raios");
            } else if (cause == EntityDamageEvent.DamageCause.STARVATION) {
                broadcastDeath(p, "morreu de fome");
            } else if (cause == EntityDamageEvent.DamageCause.WITHER) {
                broadcastDeath(p, "morreu em envenenado");
            } else if (cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                broadcastDeath(p, "morreu explodido");
            } else if (cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                broadcastDeath(p, "morreu explodido");
            } else if (cause == EntityDamageEvent.DamageCause.CUSTOM) {
                broadcastDeath(p, "morreu pela borda do mundo");
            } else if (cause == EntityDamageEvent.DamageCause.VOID) {
                broadcastDeath(p, "caiu do mundo");
            } else if (cause2.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                Entity entity = ((EntityDamageByEntityEvent) cause2)
                        .getDamager();
                if (entity.getType().equals(EntityType.PLAYER)) {
                    Player killer = (Player) entity;

                    for (Stat s : MINIGAME.getStatsOnKill()) {
                        s.addStat(killer.getUniqueId());
                    }

                    SQLStatus.addCoins(killer.getUniqueId(), 100);
                    Message.GOOD.send(killer, "Você matou " + p.getName() + ".");

                    String kill;
                    if (!KitManager.hasAnyKit(killer)) {
                        kill = killer.getName() + "§7(Nenhum)§e";
                    } else {
                        kill = killer.getName() + "§7(" + KitManager.getKit(killer).toString() + ")§e";

                    }
                    String id = killer.getItemInHand().getType().name();
                    broadcastDeath(p, "foi morto por " + kill);
                } else {
                    String msg = doubleMsg("morreu por um monstro",
                            "estava brincando com um monstro");
                    broadcastDeath(p, msg);
                }
            } else {
                broadcastDeath(p, "morreu");
            }
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!p.isOnline()) return;
                    if (Rank.has(p.getUniqueId(), Rank.MOD)) {
                        p.chat("/admin");
                    } else {
                        p.teleport(p.getLocation().getWorld().getSpawnLocation().clone().add(0.5, 0, 0.5));
                        MINIGAME.getSpectatorManager().put(p);
                    }
                }
            }.runTask(MINIGAME.getPlugin());
        }
    }

    public String doubleMsg(String msg, String msg2) {
        if (new Random().nextBoolean()) {
            return msg;
        } else {
            return msg2;
        }
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent e) {
        if (!MINIGAME.isPlayer(e.getPlayer().getUniqueId())) {
            e.setRespawnLocation(e.getPlayer().getWorld().getSpawnLocation().clone().add(0.5, 0, 0.5));
            if (e.getPlayer().getAllowFlight()) {
                e.getPlayer().setFlying(true);
            }
        }
    }

    @EventHandler
    public void onVoidDeath(SecondsEvent e) {
        if (MINIGAME.getGameState() == GameState.PREGAME) return;

        for (UUID id : MINIGAME.getPlayers()) {
            Player p = Bukkit.getPlayer(id);
            if (p == null) continue;
            if (!(p.getLocation().getY() <= 0)) continue;

            broadcastDeath(p, "caiu do mundo");
            p.setNoDamageTicks(10);
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            MINIGAME.getSpectatorManager().put(p);
            p.setHealth(p.getMaxHealth());
            p.teleport(p.getLocation().getWorld().getSpawnLocation().clone().add(0.5, 0, 0.5));
            if (!MINIGAME.hasOption(Option.UNLIMITED_LIFES)) MINIGAME.getPlayers().remove(p.getUniqueId());
        }
    }

}
