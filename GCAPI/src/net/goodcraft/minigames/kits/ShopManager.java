package net.goodcraft.minigames.kits;

import net.goodcraft.Main;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Title;
import net.goodcraft.minigames.Option;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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

public class ShopManager implements Listener {
    public static void open(Player p) {

        if(!Main.minigame.hasOption(Option.HAS_KITS)){
            return;
        }

        KitAPI.getKits(p.getUniqueId(), new SQLStatus.Callback<ArrayList<Kit>>() {
            @Override
            public void onSucess(ArrayList<Kit> pKits) {
                if(pKits.containsAll(Kit.kits)){
                    Message.ERROR.send(p, "Você já tem todos os kits!");
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                    return;
                }
                Inventory inv = Bukkit.createInventory(p, 36, "Loja de Kits");

                inv.clear();
                for (int i = 0; i < 9; i++) {
                    inv.setItem(i, Item.item(Material.STAINED_GLASS_PANE, 1, " ", 1));
                }
                for (int i = 27; i < 36; i++) {
                    inv.setItem(i, Item.item(Material.STAINED_GLASS_PANE, 1, " ", 1));
                }
                for (Kit kit : Kit.kits) {
                    if (pKits.contains(kit)) continue;
                    if(kit.getPrice() == 0) continue;
                    ItemStack i = kit.getKitSelectorItem().clone();
                    ItemMeta iM = i.getItemMeta();
                    iM.setDisplayName((kit.isVip() ? "§4[VIP] §c" : "§c") + ChatColor.stripColor(iM.getDisplayName()));
                    iM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

                    List<String> lore = new ArrayList<>();
                    lore.addAll(kit.getDesc());
                    lore.add(" ");
                    lore.add("§aPreço: " + kit.getPrice());
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

        if (inv == null) return;
        if (!inv.getName().equalsIgnoreCase("Loja de Kits")) return;
        ItemStack item = e.getCurrentItem();
        if (item == null) return;
        e.setCancelled(true);

        boolean isVip = Rank.has(p.getUniqueId(), Rank.BRONZE);

        Boolean[] stop = new Boolean[]{false};

        for (Kit k : Kit.kits) {
            if(stop[0]){
                break;
            }
            if (item.hasItemMeta()) {
                if (item.getItemMeta().hasDisplayName()) {
                    if (item.getItemMeta().getDisplayName().contains(k.toString())) {
                        SQLStatus.getStat(p.getUniqueId(), "good_global", "coins", new SQLStatus.Callback<Object>() {
                            @Override
                            public void onSucess(Object o) {
                                if(k.isVip() && !isVip){
                                    p.closeInventory();
                                    Message.ERROR.send(p, "Este kit é destinado apenas para jogadores VIPs! " +
                                            "Adquira o seu em loja.good-craft.net!");
                                    return;
                                }
                                if(((Integer)o) < k.getPrice()){
                                    p.closeInventory();
                                    Message.ERROR.send(p, "Você não tem GoodCoins suficientes!");
                                    return;
                                }
                                SQLStatus.removeCoins(p.getUniqueId(), k.getPrice());
                                KitAPI.addKit(p.getUniqueId(), k);
                                p.closeInventory();
                                Title.GOOD.send(p, k.toString(), "Kit adquirido com sucesso!");
                                p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                                stop[0] = true;
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Message.ERROR.send(p, "Você não tem GoodCoins suficientes!");
                                p.closeInventory();
                            }
                        });
                    }
                }
            }
        }
    }
}
