package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PvP extends Kit {
    public PvP() {
        super("PvP", Material.STONE_SWORD,
                new ItemStack[]{},
                Arrays.asList(
                        "§7Tenha uma vantagem em sua espada!"
                ));
        setPrice(0);
    }
}
