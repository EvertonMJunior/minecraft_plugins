package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class Potion extends Kit {
    public Potion() {
        super("Potion", PotionType.SPEED,
                new ItemStack[]{
                        Item.item(PotionType.REGEN, 1, "§ePoção de Regeneração", new String[]{}, true, false, 1),
                        Item.item(PotionType.FIRE_RESISTANCE, 1, "§ePoção de Resistência ao Fogo", new String[]{}, true, false, 1),
                        Item.item(PotionType.SPEED, 1, "§ePoção de Velocidade", new String[]{}, false, false, 1),
                }, Arrays.asList(
                        "§7Ganhe 3 poções que lhe ajudarão:",
                        "§71. Poção de Regeneração (33 seg.)",
                        "§72. Poção de Resistência ao Fogo (2:15 min.)",
                        "§73. Poção de Velocidade (3 min.)"
                ));
        setPrice(3250);
    }
}
