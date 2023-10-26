package net.goodcraft.minigames.kits;

import net.goodcraft.Main;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitSelector implements Listener {

    public static void openInv(Player p) {
        Minigame mg = Main.minigame;

        if(!Main.minigame.hasOption(Option.HAS_KITS)){
            return;
        }

        KitAPI.getKits(p.getUniqueId(), new SQLStatus.Callback<ArrayList<Kit>>() {
            @Override
            public void onSucess(ArrayList<Kit> pKits) {
                if (pKits.isEmpty() && mg.getFreeKits().get().isEmpty()) {
                    Message.ERROR.send(p, "Você não tem kits!");
                    return;
                }

                Inventory inv = Bukkit.createInventory(p, 36, "Kits");
                inv.clear();
                for (int i = 0; i < 9; i++) {
                    inv.setItem(i, Item.item(Material.STAINED_GLASS_PANE, 1, " ", 1));
                }
                for (int i = 27; i < 36; i++) {
                    inv.setItem(i, Item.item(Material.STAINED_GLASS_PANE, 1, " ", 1));
                }
                for (Kit kit : Kit.kits) {
                    if (!mg.getFreeKits().isFree(kit) && !pKits.contains(kit)) continue;
                    ItemStack i = kit.getKitSelectorItem().clone();
                    ItemMeta iM = i.getItemMeta();
                    iM.setDisplayName((!pKits.contains(kit) && mg.getFreeKits().isFree(kit) ? "§2[FREE] " : "")
                            + (kit.isVip() ? "§b[VIP] §3" : "§3") + ChatColor.stripColor(iM.getDisplayName()));
                    iM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_PLACED_ON);

                    List<String> lore = new ArrayList<>();
                    lore.addAll(kit.getDesc());
                    iM.setLore(lore);
                    i.setItemMeta(iM);
                    inv.addItem(i);
                }

                p.openInventory(inv);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();

        if(!Main.minigame.hasOption(Option.HAS_KITS)){
            return;
        }

        if (inv != null) {
            if (inv.getName().equalsIgnoreCase("Kits")) {
                ItemStack item = e.getCurrentItem();
                if (item != null) {
                    e.setCancelled(true);
                    for (Kit k : Kit.kits) {
                        if (item.hasItemMeta()) {
                            if (item.getItemMeta().hasDisplayName()) {
                                if (item.getItemMeta().getDisplayName().contains(k.toString())) {
                                    p.chat("/kit " + k.toString());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
