package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Boxer extends Kit {
    public Boxer() {
        super("Boxer", Material.WOOD_SWORD,
                new ItemStack[]{},
                Arrays.asList(
                        "§7Faça com que sua mão dê danos",
                        "§7de uma espada de madeira com",
                        "§7encantamento"
                ));
        setPrice(0);
    }
}
