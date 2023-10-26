package com.goodcraft.eventos;

import com.goodcraft.Main;
import com.goodcraft.api.TestHack;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TestHackEvent implements Listener {

    public static HashMap<UUID, Boolean> hasGotDamage = new HashMap<>();
    public static HashMap<UUID, Boolean> hasHitGround = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void noFallTest1(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        
        if(hasHitGround.containsKey(p.getUniqueId())){
            return;
        }

        if (!TestHack.testing.containsKey(p.getUniqueId())) {
            return;
        }
        TestHack tH = TestHack.testing.get(p.getUniqueId());

        if (tH.getType() != TestHack.Hack.NOFALL) {
            return;
        }

        if (e.getFrom().getY() < e.getTo().getY()) {
            return;
        }
        Material m = e.getTo().clone().subtract(0, 1, 0).getBlock().getType();
        
        if (m == Material.AIR || m == null) {
            return;
        }
        hasHitGround.put(p.getUniqueId(), Boolean.TRUE);
        
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!hasGotDamage.containsKey(p.getUniqueId())){
                    tH.infoUsingHack();
                } else {
                    tH.infoNotUsingHack();
                    hasGotDamage.remove(p.getUniqueId());
                }
            }
        }.runTaskLater(Main.getPlugin(), 5L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void noFallTest2(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();
        
        if(e.getCause() != EntityDamageEvent.DamageCause.FALL){
            return;
        }
        
        if (!TestHack.testing.containsKey(p.getUniqueId())) {
            return;
        }
        TestHack tH = TestHack.testing.get(p.getUniqueId());

        if (tH.getType() != TestHack.Hack.NOFALL) {
            return;
        }
        hasGotDamage.put(p.getUniqueId(), Boolean.TRUE);
        e.setCancelled(false);
        p.setHealth(p.getMaxHealth());
    }
}
