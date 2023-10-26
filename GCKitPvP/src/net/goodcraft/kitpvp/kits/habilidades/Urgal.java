package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class Urgal extends Kit {
    public Urgal() {
        super("Urgal", PotionType.STRENGTH,
                new ItemStack[]{
                        Item.item(PotionType.STRENGTH, 3, "§3Urgal", new String[]{}, false, false, 1)
                },
                Arrays.asList(
                        "§7Use suas poções de força, assim fazendo",
                        "§7com que seu adversário leve muito mais dano!"
                ));
        setPrice(6000);
    }
}
