package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Stomper extends Kit {
    public Stomper() {
        super("Stomper", Material.IRON_BOOTS,
                new ItemStack[]{},
                Arrays.asList(
                        "§7Caia em cima da cabeça de seus",
                        "§7inimigos e esmague-os como tomates!"
                ));
        setPrice(3750);
    }

    @EventHandler
    public void onFall(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        if(!hasAbility(p)) return;

        for(Entity en : p.getNearbyEntities(5, 5, 5)){
            if(!(en instanceof Player)) continue;
            Player stomped = (Player) en;
            if(stomped.isSneaking()){
                stomped.damage(3D, p);
                continue;
            }
            stomped.damage(e.getDamage(), p);
        }

        if(e.getDamage() > 4D) e.setDamage(4D);
    }
}
