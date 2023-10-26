package net.goodcraft.hardcoregames.chests;

import net.goodcraft.api.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Chests implements Listener {
    public static ItemStack chest = Item.item(Material.ENDER_CHEST, 1, "§bChests");
    public static HashMap<Player, ItemStack[]> item = new HashMap<Player, ItemStack[]>();

    public static void inv(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER, "§7Chests");
        inv.setItem(0, Item.item(Material.CHEST, 1, "§aSeus itens ganhos"));
        inv.setItem(2, Item.item(Material.ENDER_CHEST, 1,
                "§aG1 §7[§c" + "0" + "§7]", new String[]{
                        "§7Tenha a sorte de ganhar itens de armaduras aleatórias §c(Capacete, Peitoral..)",
                        "§7Você poderá usar o item eternamente!",
                        " ",
                        "§7Compre por §b15000 GoodCoins§7 ou pela loja do servidor (§3loja.good-craft.net§7)"}));
        inv.setItem(3, Item.item(Material.ENDER_CHEST, 1,
                "§aG2 §7[§c" + "0" + "§7]", new String[]{
                        "§7Tenha a sorte de ganhar tanto itens de armaduras §c(Capacete, Peitoral..)§7 quanto §cespadas§7.",
                        "§7Armaduras e espadas de Diamantes também!",
                        "§7Você poderá usar o item eternamente!",
                        " ",
                        "§7Compre por §b16500 GoodCoins§7 ou pelo Site do Servidor (§3loja.good-craft.net§7)"}));
        inv.setItem(4, Item.item(Material.ENDER_CHEST, 1,
                "§aG3 §7[§c" + "0" + "§7]", new String[]{"§7Tenha a sorte de ganhar 1 §ckit§7 aleatoriamente.",
                        "§7Você poderá usar o kit sorteado eternamente!",
                        " ",
                        "§7Compre por §b17500 GoodCoins§7 ou pelo site do servidor (§3loja.good-craft.net§7)"}));

        p.openInventory(inv);
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null) return;
        if (e.getCurrentItem() == null) return;

        if (e.getInventory().getName().equalsIgnoreCase("§7Chests")) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage("§cEm breve.");
        }

    }


}
