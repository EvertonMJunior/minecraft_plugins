package com.goodcraft.lobby.pets;

import com.goodcraft.api.Item;
import com.goodcraft.api.Message;
import com.goodcraft.api.Title;
import com.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PetBuyMenu implements Listener {

    private final Player p;
    private final Pet pet;

    public PetBuyMenu(Player p, Pet pet) {
        this.p = p;
        this.pet = pet;
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(null, 54, "Pets - Compra de PET");

        inv.setItem(3, Item.item(Material.INK_SACK, 1, "§eVoltar", 10));
        inv.setItem(5, Item.item(Material.INK_SACK, 1, "§eFechar", 8));

        inv.setItem(49, Item.item(Material.EMERALD, 1, "§aGood§fCoins§7: §8" + SQLStatus.getFormattedCoins(p.getUniqueId())));

        inv.setItem(19, Item.item(Material.PAPER, 1, "§ePreço", new String[]{"§7" + pet.getPrice() + " §aGoodCoins"}));

        ItemStack item = pet.getItem().clone();
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§a" + pet.getName());
        item.setItemMeta(itemMeta);

        inv.setItem(22, item);
        inv.setItem(25, Item.item(Material.DIAMOND, 1, "§eComprar"));

        p.openInventory(inv);
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (inv == null || !inv.getName().equalsIgnoreCase("Pets - Compra de PET")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }

        if (i.getType().equals(Material.INK_SACK)) {
            if (i.getData().getData() == (short) 10) {
                new PetSelector(p).open();
            } else {
                p.closeInventory();
            }
            return;
        }

        if (i.getType() == Material.DIAMOND) {
            String petName = ChatColor.stripColor(inv.getItem(22).getItemMeta().getDisplayName());
            for (Pet pet : Pet.values()) {
                if (!petName.equalsIgnoreCase(pet.getName())) {
                    continue;
                }
                if (SQLStatus.getCoins(p.getUniqueId()) < pet.getPrice()) {
                    p.closeInventory();
                    Message.ERROR.send(p, "Você não possui GoodCoins suficientes!");
                    break;
                }
                SQLStatus.removeCoins(p.getUniqueId(), pet.getPrice());

                if (PetAPI.addPet(p.getUniqueId(), pet)) {
                    Title.GOOD.send(p, pet.getName(), "Pet adquirido! Parabéns!");
                } else {
                    Message.ERROR.send(p, "Ocorreu um erro, tente mais tarde.");
                }

                p.closeInventory();
            }
            return;
        }
    }
}
