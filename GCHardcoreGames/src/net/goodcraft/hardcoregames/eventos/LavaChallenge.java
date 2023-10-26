package net.goodcraft.hardcoregames.eventos;

import net.goodcraft.api.ActionBar;
import net.goodcraft.api.Item;
import net.goodcraft.api.Utils;
import net.goodcraft.hardcoregames.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class LavaChallenge implements Listener {

    public static ArrayList<UUID> playersOnLavaChallenge = new ArrayList<>();
    public static HashMap<UUID, Double> timeOnLC = new HashMap<>();

    @EventHandler
    public void onEnterLava(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();
        Material m = p.getLocation().getBlock().getType();

        if (p.getGameMode() == org.bukkit.GameMode.CREATIVE) {
            return;
        }

        if (Main.estado != GameState.PREGAME) {
            return;
        }

        if (Main.toStart <= 15) {
            return;
        }

        if (m != Material.STATIONARY_LAVA && m != Material.LAVA) {
            if (playersOnLavaChallenge.contains(p.getUniqueId())) {
                playersOnLavaChallenge.remove(p.getUniqueId());
                GamerJoin.preInv(p);
                p.setHealth(20D);
                p.setFireTicks(1);
            }
            return;
        }

        if (playersOnLavaChallenge.contains(p.getUniqueId())) {
            return;
        }
        p.getInventory().clear();

        ItemStack sopa = Item.item(Material.MUSHROOM_SOUP);
        for (int i = 0; i < 36; i++) {
            p.getInventory().addItem(sopa);
        }

        p.getInventory().setItem(13, Item.item(Material.BOWL, 64));
        p.getInventory().setItem(14, Item.item(Material.RED_MUSHROOM, 64));
        p.getInventory().setItem(15, Item.item(Material.BROWN_MUSHROOM, 64));
        if (timeOnLC.containsKey(p.getUniqueId())) {
            timeOnLC.remove(p.getUniqueId());
        }
        timeOnLC.put(p.getUniqueId(), 0D);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!playersOnLavaChallenge.contains(p.getUniqueId()) || !p.getGameMode().equals(GameMode.SURVIVAL)) {
                    ActionBar.ERROR.send(p, "§lVocê perdeu!");
                    cancel();
                    return;
                }
                if (Main.toStart <= 15) {
                    p.teleport(p.getLocation().clone().add(0, 0.5, 0));
                    p.setVelocity(new Vector(0, 3, 0));
                    p.setHealth(20D);
                    p.setFireTicks(1);
                    ActionBar.ERROR.send(p, "§lA partida começa em " + Utils.secondsToSentence(Main.toStart) + "!");
                    playersOnLavaChallenge.remove(p.getUniqueId());
                    cancel();
                    return;
                }
                Double tempo1 = timeOnLC.get(p.getUniqueId());
                Double tempo = tempo1 + 0.1D;
                timeOnLC.remove(p.getUniqueId());
                timeOnLC.put(p.getUniqueId(), tempo);

                ActionBar.INFO.send(p, "Tempo no LavaChallenge: " + new DecimalFormat("#.#").format(tempo) + " segundos");
            }
        }.runTaskTimer(Main.getMain(), 0L, 2L);

        playersOnLavaChallenge.add(p.getUniqueId());
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

        if (playersOnLavaChallenge.contains(p.getUniqueId())) {
            playersOnLavaChallenge.remove(p.getUniqueId());
        } else {
            return;
        }

        GamerJoin.preInv(p);
        p.teleport(p.getLocation().clone().add(0, 0.5, 0));
        p.setVelocity(new Vector(0, 3, 0));
        p.setHealth(20D);
        p.setFireTicks(1);

        e.setDamage(0D);
    }

    @EventHandler
    public void onClickSoup(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Damageable hp = p;

        if (!playersOnLavaChallenge.contains(p.getUniqueId())) {
            return;
        }

        if (!e.getAction().name().contains("RIGHT")) {
            return;
        }

        if (p.getItemInHand().getType() != Material.MUSHROOM_SOUP) {
            return;
        }

        if (hp.getHealth() == 20) {
            return;
        }
        e.setCancelled(false);

        p.setHealth(hp.getHealth() + 7 > hp.getMaxHealth() ? hp.getMaxHealth() : hp.getHealth() + 7);
        p.getItemInHand().setType(Material.BOWL);
    }

}
