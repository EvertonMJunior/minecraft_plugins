package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Fisherman extends Kit {
    public Fisherman() {
        super("Fisherman", Material.FISHING_ROD,
                new ItemStack[]{Item.item(Material.FISHING_ROD, 1, "§3Fisherman")},
                Arrays.asList("§7Use uma vara de pesca para fisgar",
                        "§7seus inimigos. Use o botão direito",
                        "§7para puxá-lo até você!"));
    }

    @EventHandler
    public void fisherMan(PlayerFishEvent event) {
        Player p = event.getPlayer();
        if ((event.getCaught() instanceof Player)) {
            Player caught = (Player) event.getCaught();
            if (hasAbility(p) && (p.getItemInHand().getType() == Material.FISHING_ROD)) {
                caught.teleport(p.getLocation());
            }
        }
    }

}
