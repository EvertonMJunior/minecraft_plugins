package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Leiteiro extends Kit {
    public Leiteiro(){
        super("Leiteiro", Material.MILK_BUCKET,
                new ItemStack[]{
                        Item.item(Material.MILK_BUCKET, 3),
                }, Arrays.asList(
                        "§7Ganhe 3 baldes de leite!"
                ));
        setPrice(750);
    }
}