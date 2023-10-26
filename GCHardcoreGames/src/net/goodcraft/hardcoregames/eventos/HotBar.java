package net.goodcraft.hardcoregames.eventos;

import net.goodcraft.GameMode;
import net.goodcraft.api.Item;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HotBar implements Listener {
    public static ItemStack soup = Item.item(Material.MUSHROOM_SOUP, 1, "§aSopas");

    public static void invSoup(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER, "Tipos de Sopa");

        inv.setItem(0, Item.item(Material.MUSHROOM_SOUP, 1, "§eSopa de Cogumelos", new String[]{"§7Pote",
                "§7Cogumelo Vermelho", "§7Cogumelo Marrom"}));

        inv.setItem(1, Item.item(Material.CACTUS, 1, "§eSopa de Cactus", new String[]{"§7Pote", "§7Cactus"}));

        inv.setItem(2, Item.item(Material.INK_SACK, 1, "§eSopa de Cacau", new String[]{"§7Pote", "§7Cacau"}, 3));

        inv.setItem(3, Item.item(Material.RED_ROSE, 1, "§eSopa de Flor", new String[]{"§7Pote",
                "§7Flor Vermelha", "§7Flor Amarela"}));


        p.openInventory(inv);
    }

    public static void invStatus(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER, "Status de " + p.getName());
        inv.setItem(0, Item.item(Material.CAKE, 1, "§7Wins: §8" + SQLStatus.getStatus(
                p.getUniqueId(), GameMode.HARDCOREGAMES, "wins")));
        inv.setItem(2, Item.item(Material.STONE_SWORD, 1, "§7Kills: §8" + SQLStatus.getStatus(
                p.getUniqueId(), GameMode.HARDCOREGAMES, "kills")));
        inv.setItem(4, Item.item(Material.REDSTONE, 1, "§7Deaths: §8" + SQLStatus.getStatus(
                p.getUniqueId(), GameMode.HARDCOREGAMES, "deaths")));


        p.openInventory(inv);
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null) return;
        if (e.getCurrentItem() == null) return;

        if (e.getInventory().getName().equalsIgnoreCase("Tipos de Sopa")) {
            if (e.getCurrentItem().getType() == Material.MUSHROOM_SOUP) {
                e.setCancelled(true);
                Inventory inv = Bukkit.createInventory(p, InventoryType.DISPENSER, "Sopa de Cogumelos");

                inv.setItem(3, Item.item(Material.BOWL));
                inv.setItem(4, Item.item(Material.RED_MUSHROOM));
                inv.setItem(5, Item.item(Material.BROWN_MUSHROOM));

                p.openInventory(inv);

            } else if (e.getCurrentItem().getType() == Material.CACTUS) {
                e.setCancelled(true);
                Inventory inv = Bukkit.createInventory(p, InventoryType.DISPENSER, "Sopa de Cactus");

                inv.setItem(3, Item.item(Material.BOWL));
                inv.setItem(4, Item.item(Material.CACTUS));

                p.openInventory(inv);
            } else if (e.getCurrentItem().getType() == Material.INK_SACK) {
                e.setCancelled(true);
                Inventory inv = Bukkit.createInventory(p, InventoryType.DISPENSER, "Sopa de Cacau");

                inv.setItem(3, Item.item(Material.BOWL));
                inv.setItem(4, Item.item(Material.INK_SACK, 1, "Cacau", 3));

                p.openInventory(inv);
            } else if (e.getCurrentItem().getType() == Material.RED_ROSE) {
                e.setCancelled(true);

                Inventory inv = Bukkit.createInventory(p, InventoryType.DISPENSER, "Sopa de Flor");

                inv.setItem(3, Item.item(Material.BOWL));
                inv.setItem(4, Item.item(Material.YELLOW_FLOWER));
                inv.setItem(5, Item.item(Material.RED_ROSE));

                p.openInventory(inv);
            }
        }
    }
}
