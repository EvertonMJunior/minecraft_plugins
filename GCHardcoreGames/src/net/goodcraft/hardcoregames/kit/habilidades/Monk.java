package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.Random;

public class Monk extends Kit {
    public Monk() {
        super("Monk", Material.BLAZE_ROD,
                new ItemStack[]{Item.item(Material.BLAZE_ROD, 1, "§3Monk")},
                Arrays.asList("§7Desorganize o inventário de",
                        "§7seu inimigo, colocando o item",
                        "§7que estiver na mão dele em",
                        "§7seu inventário."));
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        event.getPlayer().getItemInHand();
        Player p = event.getPlayer();
        if (hasAbility(p)
                && p.getItemInHand().getType() == Material.BLAZE_ROD) {
            if (hasCooldown(p)) {
                mensagemcooldown(p);
            } else {
                if (!(event.getPlayer() instanceof Player)) {
                    return;
                }
                PlayerInventory inv = ((Player) event.getRightClicked())
                        .getInventory();
                int slot = new Random().nextInt(35);
                ItemStack replaced = inv.getItemInHand();
                if (replaced == null)
                    replaced = new ItemStack(0);
                ItemStack replacer = inv.getItem(slot);
                if (replacer == null)
                    replacer = new ItemStack(0);
                inv.setItemInHand(replacer);
                inv.setItem(slot, replaced);
                addCooldown(p, 30);
                event.getRightClicked().sendMessage("§bVoce foi §3monkado§b!");
            }
        }
    }

}
