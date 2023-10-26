package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.api.Laser;
import net.goodcraft.api.Message;
import net.goodcraft.api.Utils;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.skywars.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class Shulker extends Kit {
    public Shulker() {
        super("Shulker", Material.FIREWORK_CHARGE,
                new ItemStack[]{
                        Item.item(Material.FIREWORK_CHARGE, 1, "§3Shulker")
                }, Arrays.asList(
                        "§7Como um Shulker, jogue projéteis em ",
                        "§7seus inimigos e faça-os levitar por",
                        "§710 segundos, assim aplicando-lhes",
                        "§72 corações de dano, além do dano",
                        "§7que lhes será causado pela queda!",
                        "§7Alcance: 100 blocos"
                ));
        setPrice(4600);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();
        if (!e.getAction().name().contains("RIGHT")) return;
        if (!hasAbility(p)) return;
        if (i == null) return;
        if (i.getType() != Material.FIREWORK_CHARGE) return;
        if (Main.getMg().getGameState() == GameState.PREGAME) return;

        e.setCancelled(true);
        e.setUseInteractedBlock(Event.Result.DENY);
        e.setUseItemInHand(Event.Result.DENY);

        if (hasCooldown(p)) {
            mensagemcooldown(p);
            return;
        }
        addCooldown(p, 15);

        Entity en = Utils.getNearestEntityInSight(p, 100);
        if (en == null || !(en instanceof LivingEntity)) {
            Message.ERROR.send(p, "Você errou!");
            return;
        }
        ((LivingEntity) en).damage(2 * 2);

        p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 1F, 1F);

        if (en instanceof Player) {
            Message.INFO.send(en, "Você foi acertado por um Shulker! (" + p.getName() + ")");
            ((Player) en).playSound(en.getLocation(), Sound.NOTE_BASS, 1F, 1F);
        }

        Laser laser = new Laser(p.getLocation());
        laser.setTarget((LivingEntity) en);
        Laser laser2 = new Laser(en.getLocation());
        laser2.setTarget(p);

        final int[] repeated = {1};

        new BukkitRunnable() {
            @Override
            public void run() {
                if (repeated[0] >= 20 * 5) {
                    if (en instanceof Player) ((Player) en).playSound(en.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                    en.setVelocity(en.getVelocity().setY(-1.4D));
                    laser.despawn(null);
                    laser2.despawn(null);
                    cancel();
                    return;
                }
                laser.setLocation(p.getLocation().clone());
                laser.despawn(null);
                laser.setTarget((LivingEntity) en);

                laser2.setLocation(en.getLocation().clone());
                laser2.despawn(null);
                laser2.setTarget(p);

                en.setVelocity(en.getVelocity().setY(0.15D));

                repeated[0]++;
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(), 1L, 1L);
    }
}
