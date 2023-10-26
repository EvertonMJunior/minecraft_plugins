package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Forger extends Kit {
    public Forger() {
        super("Forger", Material.COAL,
                new ItemStack[]{Item.item(Material.COAL, 5, "")},
                Arrays.asList("§7Esquente minérios rapidamente",
                        "§7sem a necessidade de uma fornalha,",
                        "§7basta apenas colocar ele sobre",
                        "§7os carvões e então já estará assado."));
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (Main.estado == GameState.PREGAME) return;
        ItemStack currentItem = event.getCurrentItem();
        if ((currentItem != null) && (currentItem.getType() != Material.AIR) && (hasAbility((Player) event.getWhoClicked()))) {
            int coalAmount = 0;
            Inventory inv = event.getView().getBottomInventory();
            for (ItemStack item : inv.getContents()) {
                if ((item != null) && (item.getType() == Material.COAL)) {
                    coalAmount += item.getAmount();
                }
            }
            if (coalAmount == 0) {
                return;
            }
            int hadCoal = coalAmount;
            if (currentItem.getType() == Material.COAL) {
                for (int slot = 0; slot < inv.getSize(); slot++) {
                    ItemStack item = inv.getItem(slot);
                    if ((item != null) && (item.getType().name().contains("ORE"))) {
                        while ((item.getAmount() > 0) && (coalAmount > 0) && ((item.getType() == Material.IRON_ORE) || (item.getType() == Material.GOLD_ORE))) {
                            item.setAmount(item.getAmount() - 1);
                            coalAmount--;
                            if (item.getType() == Material.IRON_ORE) {
                                event.getWhoClicked().getInventory().addItem(new ItemStack(Material.IRON_INGOT, 1));
                            } else if (item.getType() == Material.GOLD_ORE) {
                                event.getWhoClicked().getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 1));
                            }
                        }
                        if (item.getAmount() == 0) {
                            inv.setItem(slot, new ItemStack(0));
                        }
                    }
                }
            } else if (currentItem.getType().name().contains("ORE")) {
                while ((currentItem.getAmount() > 0) && (coalAmount > 0) && ((currentItem.getType() == Material.IRON_ORE) || (currentItem.getType() == Material.GOLD_ORE))) {
                    currentItem.setAmount(currentItem.getAmount() - 1);
                    coalAmount--;
                    if (currentItem.getType() == Material.IRON_ORE) {
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.IRON_INGOT, 1));
                    } else if (currentItem.getType() == Material.GOLD_ORE) {
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 1));
                    }
                }
                if (currentItem.getAmount() == 0) {
                    event.setCurrentItem(new ItemStack(0));
                }
            }
            if (coalAmount != hadCoal) {
                for (int slot = 0; slot < inv.getSize(); slot++) {
                    ItemStack item = inv.getItem(slot);
                    if ((item != null) && (item.getType() == Material.COAL)) {
                        while ((coalAmount < hadCoal) && (item.getAmount() > 0)) {
                            item.setAmount(item.getAmount() - 1);
                            coalAmount++;
                        }
                        if (item.getAmount() == 0) {
                            inv.setItem(slot, new ItemStack(0));
                        }
                    }
                }
            }
        }
    }
}