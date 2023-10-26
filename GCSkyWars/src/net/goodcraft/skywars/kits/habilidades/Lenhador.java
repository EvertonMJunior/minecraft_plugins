package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Lenhador extends Kit {
    public Lenhador() {
        super("Lenhador", Material.STONE_AXE,
                new ItemStack[]{Item.item(Material.STONE_AXE, 1),
                        Item.item(Material.LOG, 7)}, Arrays.asList(
                        "§7Ganhe 7 madeiras brutas e",
                        "§7um machado de pedra! Assim",
                        "§7você poderá ir para todas",
                        "§7as ilhas rapidamente e ser",
                        "§7tão bruto quanto um lenhador!"
                ));
        setPrice(1750);
    }
}
