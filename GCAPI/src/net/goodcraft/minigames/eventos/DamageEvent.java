package net.goodcraft.minigames.eventos;

import net.goodcraft.Main;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.game.GameState;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class DamageEvent implements Listener {

    public static HashMap<UUID, UUID> combatLog = new HashMap<>();
    public static double wood = 2;
    public static double stone = 3;
    public static double iron = 4;
    public static double diamond = 5;
    public static double critical = 1;
    public static double sharp = 1;

    @EventHandler
    public void mobDmg(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof PigZombie || e.getDamager() instanceof Zombie || e.getDamager() instanceof Slime || e.getDamager() instanceof Ghast || e.getDamager() instanceof Enderman || e.getDamager() instanceof Skeleton || e.getDamager() instanceof Spider || e.getDamager() instanceof CaveSpider) {
            if (e.getDamage() > 3.5D) {
                e.setDamage(3.5D);
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player && !event.isCancelled()) {
            if (Main.minigame.getGameState() == GameState.PREGAME) return;
            if (Main.minigame.getGameState() == GameState.INVENCIBILITY) return;
            if (!Main.minigame.hasOption(Option.GETS_DAMAGE)){
                event.setCancelled(true);
                return;
            }
            final Player p = (Player) event.getEntity();
            final Player damager = (Player) event.getDamager();
            if(!Main.minigame.isPlayer(damager.getUniqueId())) return;
            addTag(p, damager);
        }
    }

    @EventHandler
    public void nerf(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if(!Main.minigame.isPlayer(player.getUniqueId())) {
                event.setCancelled(true);
                return;
            }
            nerfDamage(player, event);
        }
    }

    @SuppressWarnings("deprecation")
    public static void nerfDamage(Player damager, EntityDamageByEntityEvent e) {

        if(!Main.minigame.hasOption(Option.NERF_DAMAGE)){
            return;
        }

        if (e.getDamage() > 1.0D) {
            e.setDamage(e.getDamage() - 1.0D);
        }
        if (e.getDamager() instanceof Player) {
            if ((damager.getFallDistance() > 0.0F) && (!damager.isOnGround()) && (!damager.hasPotionEffect(PotionEffectType.BLINDNESS))) {
                int NewDamage = (int) (e.getDamage() * critical + 0.5D) - (int) e.getDamage();
                if (e.getDamage() > 1.0D) {
                    e.setDamage(e.getDamage() - NewDamage);
                }
            }
            if (damager.getItemInHand().getType() == Material.WOOD_SWORD) {
                e.setDamage(wood);
            }
            if (damager.getItemInHand().getType() == Material.STONE_SWORD) {
                e.setDamage(stone);
            }
            if (damager.getItemInHand().getType() == Material.IRON_SWORD) {
                e.setDamage(iron);
            }
            if (damager.getItemInHand().getType() == Material.DIAMOND_SWORD) {
                e.setDamage(diamond);
            }
            if (damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                for (PotionEffect Effect : damager.getActivePotionEffects()) {
                    if (Effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                        double Division = (Effect.getAmplifier() + 1) * 1.3D + 1.0D;
                        int NewDamage;
                        if (e.getDamage() / Division <= 1.0D)
                            NewDamage = (Effect.getAmplifier() + 1) * 3 + 1;
                        else {
                            NewDamage = (int) (e.getDamage() / Division) + (Effect.getAmplifier() + 1) * 3;
                        }
                        e.setDamage(NewDamage);
                        break;
                    }
                }
            }

            if (damager.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) {
                e.setDamage(e.getDamage() + sharp);
            }
            if ((damager.getFallDistance() > 0.0F) && (!damager.isOnGround()) && (!damager.hasPotionEffect(PotionEffectType.BLINDNESS))) {
                if (damager.getItemInHand().getType() == Material.WOOD_SWORD) {
                    e.setDamage(e.getDamage() + critical);
                }
                if (damager.getItemInHand().getType() == Material.STONE_SWORD) {
                    e.setDamage(e.getDamage() + critical);
                }
                if (damager.getItemInHand().getType() == Material.IRON_SWORD) {
                    e.setDamage(e.getDamage() + critical);
                }
                if (damager.getItemInHand().getType() == Material.DIAMOND_SWORD)
                    e.setDamage(e.getDamage() + critical);
            }
        }
    }

    public void addTag(final Player p, Player hit) {
        if(!Main.minigame.hasOption(Option.HAS_COMBATLOG)){
            return;
        }

        combatLog.put(p.getUniqueId()
                , hit.getUniqueId());
        new BukkitRunnable() {
            public void run() {
                combatLog.remove(p.getUniqueId());
            }
        }.runTaskLater(Main.getPlugin(), 8 * 20);
    }
}
