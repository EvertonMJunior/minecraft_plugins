package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.skywars.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Creeper extends Kit {
    public Creeper() {
        super("Creeper", Material.MONSTER_EGG, 50,
                new ItemStack[]{
                        Item.item(Material.TNT, 1, "§3Creeper"),
                }, Arrays.asList(
                        "§7Exploda todos com sua TNT!"
                ));
        setVip();
        setPrice(4500);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();
        if(!e.getAction().name().contains("RIGHT")) return;
        if (i == null) return;
        if (!hasAbility(p)) return;
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (i.getType() != Material.TNT) return;

        e.setCancelled(true);
        e.setUseInteractedBlock(Event.Result.DENY);

        if (hasCooldown(p)) {
            mensagemcooldown(p);
            return;
        }
        addCooldown(p, 30);
        Vector v = p.getEyeLocation().getDirection().multiply(0.8D).setY(0.5D);
        TNTPrimed tnt = p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
        tnt.setVelocity(v);
        tnt.setIsIncendiary(true);
        tnt.setFuseTicks(3 * 20);
        tnt.setCustomName("§4Explodindo em 3 segundos!");
        final double[] leftTime = {3};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (leftTime[0] == 0) {
                    cancel();
                    return;
                }
                tnt.setCustomName("§4Explodindo em " + new DecimalFormat("#.#").format(leftTime[0]) + " segundos!");
                leftTime[0] -= 0.1;
            }
        }.runTaskTimer(Main.getPlugin(), 0L, 2L);
        tnt.setCustomNameVisible(true);
        p.updateInventory();
    }

    @EventHandler
    public void onTNTDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (!hasAbility(p)) return;
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (e.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) return;
        e.setDamage(0D);
    }
}
