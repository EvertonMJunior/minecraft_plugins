package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class Urgal extends Kit {

    public Urgal() {
        super("Urgal", PotionType.STRENGTH,
                new ItemStack[]{Item.item(PotionType.STRENGTH, 3, "", new String[]{null}, false, false, 1)},
                Arrays.asList("§7Começe a partida com 3",
                        "§7poções de força"));
    }
}
