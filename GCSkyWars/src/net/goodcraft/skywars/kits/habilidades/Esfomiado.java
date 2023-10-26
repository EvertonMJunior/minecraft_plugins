package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Esfomiado extends Kit {
    public Esfomiado() {
        super("Esfomiado", Material.GRILLED_PORK,
                new ItemStack[]{Item.item(Material.GRILLED_PORK, 3),
                        Item.item(Material.CAKE, 2)}, Arrays.asList(
                        "§7Ganhe 3 carnes de porco e",
                        "§72 bolos! Assim você nunca mais",
                        "§7vai ficar com fome!"
                ));
        setPrice(1250);
    }
}
