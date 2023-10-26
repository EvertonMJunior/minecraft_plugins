package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class MobSpawner extends Kit {
    public MobSpawner() {
        super("MobSpawner", Material.MOB_SPAWNER,
                new ItemStack[]{Item.item(Material.MONSTER_EGG, 1, "§3MobSpawner")},
                Arrays.asList("§7Usando um ovo de creeper, você irá",
                        "§7spawnar um mob agressivo aleatório que",
                        "§7será a seu favor. Atacará apenas inimigos."
                ));
    }

    public static HashMap<UUID, UUID> mobs = new HashMap<>();

    @EventHandler
    public void inte(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (hasAbility(p)) {
            if (p.getItemInHand() != null) {
                if (p.getItemInHand().getType() == Material.MONSTER_EGG) {
                    e.setCancelled(true);
                    if (hasCooldown(p)) {
                        mensagemcooldown(p);
                    } else {
                        Random ra = new Random();
                        int r = ra.nextInt(2) + 1;
                        Monster mob = null;
                        if (r == 1) {
                            mob = p.getWorld().spawn(e.getClickedBlock().getLocation().add(0, 1.3, 0), Zombie.class);
                        } else if (r == 2) {
                            mob = p.getWorld().spawn(e.getClickedBlock().getLocation().add(0, 1.3, 0), Skeleton.class);
                        } else if (r == 3) {
                            mob = p.getWorld().spawn(e.getClickedBlock().getLocation().add(0, 1.3, 0), Spider.class);
                        }
                        if(mob == null) return;
                        mobs.put(p.getUniqueId(), mob.getUniqueId());
                        addCooldown(p, 25);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent e){
        if(!(e.getTarget() instanceof Player)) return;
        Player p = (Player) e.getTarget();
        if(!mobs.containsKey(p.getUniqueId())) return;
        if(!mobs.get(p.getUniqueId()).equals(e.getEntity().getUniqueId())) return;
        e.setCancelled(true);
    }

}
