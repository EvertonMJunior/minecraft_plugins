package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Minerador extends Kit {
    public Minerador(){
        super("Minerador", Material.IRON_PICKAXE,
                new ItemStack[]{
                        Item.item(Material.IRON_PICKAXE),
                        Item.item(Material.FURNACE),
                        Item.item(Material.COAL, 6)
                }, Arrays.asList(
                        "§7Ganhe 1 picareta de ferro, uma",
                        "§7fornalha e 6 carvões. Assim,",
                        "§7você será um nato minerador!"
                ));
        setPrice(1275);
    }
}
