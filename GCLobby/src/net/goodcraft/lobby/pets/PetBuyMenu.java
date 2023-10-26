package net.goodcraft.lobby.pets;

import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.api.Title;
import net.goodcraft.sql.SQLStatus;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class PetBuyMenu implements Listener {

    private final Player p;
    private final Pet pet;

    public PetBuyMenu() {
        this.p = null;
        this.pet = null;
    }

    public PetBuyMenu(Player p, Pet pet) {
        this.p = p;
        this.pet = pet;
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(null, 54, "Pets - Compra de PET");

        inv.setItem(3, Item.item(Material.INK_SACK, 1, "§eVoltar", 10));
        inv.setItem(5, Item.item(Material.INK_SACK, 1, "§eFechar", 8));

        assert p != null;
        SQLStatus.getStat(p.getUniqueId(), "good_global", "coins", new SQLStatus.Callback<Object>() {
            @Override
            public void onSucess(Object o) {
                int value = (Integer) o;
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator(',');
                symbols.setGroupingSeparator('.');
                String pattern = "#,###,###";
                DecimalFormat myFormatter = new DecimalFormat(pattern, symbols);

                String coins = myFormatter.format(value);

                inv.setItem(49, Item.item(Material.EMERALD, 1, "§fGoodCoins§7: §a" + coins));
            }

            @Override
            public void onFailure(Throwable throwable) {
                inv.setItem(49, Item.item(Material.EMERALD, 1, "§fGoodCoins§7: §a0"));
            }
        });

        assert pet != null;
        inv.setItem(19, Item.item(Material.PAPER, 1, "§ePreço", new String[]{"§7" + pet.getPrice() + " GoodCoins"}));

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
            final boolean[] stop = {false};
            for (Pet pet : Pet.values()) {
                if (!petName.equalsIgnoreCase(pet.getName())) {
                    continue;
                }
                if (stop[0]) break;
                SQLStatus.getStat(p.getUniqueId(), "good_global", "coins", new SQLStatus.Callback<Object>() {
                    @Override
                    public void onSucess(Object o) {
                        if (((Integer) o) < pet.getPrice()) {
                            p.closeInventory();
                            Message.ERROR.send(p, "Você não possui GoodCoins suficientes!");
                            stop[0] = true;
                            return;
                        }

                        PetAPI.addPet(p.getUniqueId(), pet, new SQLStatus.Callback<Boolean>() {
                            @Override
                            public void onSucess(Boolean bought) {
                                if (bought) {
                                    Title.GOOD.send(p, pet.getName(), "Pet adquirido! Parabéns!");
                                    SQLStatus.removeCoins(p.getUniqueId(), pet.getPrice());
                                    return;
                                }
                                Message.ERROR.send(p, "Ocorreu um erro, tente mais tarde. Não mexemos em seu dinheiro.");
                            }

                            @Override
                            public void onFailure(Throwable cause) {

                            }
                        });
                        p.closeInventory();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Message.ERROR.send(p, "Ocorreu um erro, tente mais tarde. Não mexemos em seu dinheiro.");
                        p.closeInventory();
                    }
                });
            }
        }
    }
}
