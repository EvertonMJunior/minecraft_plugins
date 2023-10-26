package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Enchanter extends Kit {
    public Enchanter() {
        super("Enchanter", Material.ENCHANTMENT_TABLE,
                new ItemStack[]{Item.item(Material.ENCHANTMENT_TABLE),
                        Item.item(Material.EXP_BOTTLE, 3)}, Arrays.asList(
                        "§7Ganhe um Altar de Encantamentos",
                        "§7e 3 frascos de XP. Fique muito",
                        "§7mais forte que seus oponentes!"
                ));
        setPrice(1500);
    }
}
