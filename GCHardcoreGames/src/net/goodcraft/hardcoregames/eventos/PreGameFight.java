package net.goodcraft.hardcoregames.eventos;

import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.hardcoregames.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class PreGameFight {
    public static ItemStack p1v1 = Item.item(Material.STONE_SWORD, 1, "§9PvP");
    public static ArrayList<UUID> in1v1 = new ArrayList<>();

    public static void add1v1(Player p) {
        if (Main.toStart < 15) {
            Message.INFO.send(p, "O jogo vai começar em breve!");
            return;
        }
        in1v1.add(p.getUniqueId());

        p.teleport(new Location(p.getWorld(), 0.5, 192, 0.5));
        p.getInventory().clear();

        p.getInventory().addItem(Item.item(Material.STONE_SWORD, 1, ""));

        ItemStack cogu1 = new ItemStack(Material.BROWN_MUSHROOM, 32);
        p.getInventory().setItem(13, cogu1);

        ItemStack cogu2 = new ItemStack(Material.RED_MUSHROOM, 32);
        p.getInventory().setItem(14, cogu2);

        ItemStack pote = new ItemStack(Material.BOWL, 32);
        p.getInventory().setItem(15, pote);
        for (int i = 0; i < 34; i++) {
            p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
        }
    }
}
