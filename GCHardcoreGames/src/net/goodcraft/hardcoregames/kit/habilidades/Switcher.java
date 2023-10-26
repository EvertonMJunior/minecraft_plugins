package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Switcher extends Kit {

    public Switcher(){
        super("Switcher", Material.SNOW_BALL,
                new ItemStack[]{Item.item(Material.SNOW_BALL, 16, "§3Switcher")},
                Arrays.asList("§7Comece a partida com algumas",
                        "§7Bolas de Neve. Jogando uma em",
                        "§7outro jogador, você trocará de",
                        "§7lugar com ele! (Teleporte)"));
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Snowball)) {
            return;
        }
        @SuppressWarnings("deprecation")
        Player s = (Player) ((Snowball)e.getDamager()).getShooter();
        if(!hasAbility(s)) return;
        e.setCancelled(true);

        if(e.getEntity() instanceof Player) {
            Player ent = (Player) e.getEntity();
            if(!Main.getMg().isPlayer(ent.getUniqueId())) return;

            final Location loc = e.getEntity().getLocation();

            ent.teleport(s.getLocation());
            s.teleport(loc);
        }
    }
}
