package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Specialist extends Kit {
    public Specialist() {
        super("Specialist", Material.ENCHANTED_BOOK,
                new ItemStack[]{Item.item(Material.ENCHANTED_BOOK, 1, "§3Specialist")},
                Arrays.asList("§7Ganhe uma mesa de encantamentos",
                        "§7portátil! A cada jogador morto,",
                        "§7você ganha 1 nível de XP!"));
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (e.getEntity() != null) {
            if (e.getEntity().getKiller() != null) {
                Player p = e.getEntity().getKiller();
                if (hasAbility(p)) {
                    p.setLevel(p.getLevel() + 1);
                    p.sendMessage("§bVocê recebeu um 1 de xp");
                }
            }
        }
    }

    @EventHandler
    public void onInteractWithBook(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().getType() == Material.ENCHANTED_BOOK && hasAbility(p)) {
            p.openEnchanting(p.getLocation(), true);
            return;
        }
    }

}
