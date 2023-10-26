package net.goodcraft.lobby.eventos;

import net.goodcraft.api.ActionBar;
import net.goodcraft.api.Item;
import net.goodcraft.lobby.Main;
import org.bukkit.Material;
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

public class LavaChallengeEvent implements Listener {

    public static ArrayList<String> playersOnLavaChallenge = new ArrayList<>();
    public static HashMap<String, Double> timeOnLC = new HashMap<>();

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

        if (ArenaKnockEvent.onArenaKnock.contains(p.getName())) {
            return;
        }

        if (m != Material.STATIONARY_LAVA && m != Material.LAVA) {
            if (playersOnLavaChallenge.contains(p.getName())) {
                playersOnLavaChallenge.remove(p.getName());
                p.closeInventory();
                JoinEvent.setInitialItems(p);
                p.setHealth(20D);
            }
            return;
        }

        if (playersOnLavaChallenge.contains(p.getName())) {
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
        if (timeOnLC.containsKey(p.getName())) {
            timeOnLC.remove(p.getName());
        }
        timeOnLC.put(p.getName(), 0D);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!playersOnLavaChallenge.contains(p.getName())) {
                    ActionBar.ERROR.send(p, "§lVocê perdeu!");

                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            p.setFireTicks(1);
                        }
                    }.runTask(Main.getPlugin());

                    cancel();
                    return;
                }
                Double tempo1 = timeOnLC.get(p.getName());
                Double tempo = tempo1 + 0.1D;
                timeOnLC.remove(p.getName());
                timeOnLC.put(p.getName(), tempo);

                ActionBar.INFO.send(p, "Tempo no LavaChallenge: " + new DecimalFormat("#.#").format(tempo) + " segundos");
            }
        }.runTaskTimer(Main.getPlugin(), 0L, 2L);

        playersOnLavaChallenge.add(p.getName());
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

        if (playersOnLavaChallenge.contains(p.getName())) {
            playersOnLavaChallenge.remove(p.getName());
        } else {
            return;
        }
        p.closeInventory();
        JoinEvent.setInitialItems(p);
        p.teleport(p.getLocation().clone().add(0, 0.5, 0));
        p.setVelocity(new Vector(0, 5, 0));
        p.setHealth(20D);
        p.setFireTicks(1);

        e.setDamage(0D);
    }

    @EventHandler
    public void onClickSoup(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!e.getAction().name().contains("RIGHT")) {
            return;
        }

        if (p.getItemInHand().getType() != Material.MUSHROOM_SOUP) {
            return;
        }

        if (p.getHealth() == 20) {
            return;
        }
        e.setCancelled(false);

        p.setHealth(p.getHealth() + 7 > p.getMaxHealth() ? p.getMaxHealth() : p.getHealth() + 7);
        p.getItemInHand().setType(Material.BOWL);
    }

}
