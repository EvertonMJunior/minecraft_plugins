package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import net.goodcraft.hardcoregames.eventos.GamerDamage;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Viking extends Kit {
    public Viking() {
        super("Viking", Material.STONE_AXE,
                new ItemStack[]{null},
                Arrays.asList("§7Batalhe com um machado, fazendo",
                        "§7com que seu Machado substitua uma Espada.",
                        "§7Um machado de pedra dá o dano de uma espada de",
                        "§7ferro, assim como um de madeira dá o de uma",
                        "§7espada de pedra e assim por diante."));
    }

    @EventHandler
    public void vikingDamage(EntityDamageByEntityEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (hasAbility(p)) {
                ItemStack item = p.getItemInHand();
                if ((item != null) && (item.getType().name().contains("AXE"))) {
                    GamerDamage.Nerf(p, e);
                    e.setDamage(e.getDamage() + 2);
                }
            }
        }
    }
}
