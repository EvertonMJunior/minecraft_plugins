package net.goodcraft.lobby.pets;

import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Utils;
import net.goodcraft.lobby.Main;
import net.goodcraft.lobby.eventos.ExtrasEvent;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class PetSelector implements Listener {

    private final Player p;

    public PetSelector() {
        this.p = null;
    }

    public PetSelector(Player p) {
        this.p = p;
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(p, 54, "Pets");

        inv.setItem(3, Item.item(Material.INK_SACK, 1, "§eVoltar", 10));
        inv.setItem(5, Item.item(Material.INK_SACK, 1, "§eFechar", 8));

        inv.setItem(9, Item.item(Material.PAPER, 1, "§f§lPara todos"));
        inv.setItem(27, Item.item(Material.PAPER, 1, "§f§lPara VIPs"));

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

                inv.setItem(49, Item.item(Material.EMERALD, 1, "§fGoodCoins§7: §a" + myFormatter.format(value)));
            }

            @Override
            public void onFailure(Throwable throwable) {}
        });
        inv.setItem(53, Item.item(Material.NAME_TAG, 1, "§fConfigurar PET"));

        final int[] slotNormais = {11};
        final int[] slotVips = {29};
        boolean isVip = Rank.has(p.getUniqueId(), Rank.BRONZE);

        PetAPI.getPets(p.getUniqueId(), new SQLStatus.Callback<ArrayList<String>>() {
            @Override
            public void onSucess(ArrayList<String> pPets) {
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        for(Pet pet : Pet.values()){
                            if(!pet.isVip()){
                                ItemStack item = pet.getItem().clone();
                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setDisplayName((pPets.contains(pet.getName()) ? "§a" : "§c") + pet.getName());

                                if (!pPets.contains(pet.getName())) {
                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add("§7Preço: " + pet.getPrice() + " GoodCoins");
                                    itemMeta.setLore(lore);
                                }
                                item.setItemMeta(itemMeta);

                                inv.setItem(slotNormais[0], item);
                                slotNormais[0]++;
                            } else {
                                ItemStack item = pet.getItem().clone();
                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setDisplayName((isVip ? "§a" : "§c") + pet.getName());
                                item.setItemMeta(itemMeta);
                                inv.setItem(slotVips[0], item);

                                slotVips[0]++;
                            }
                        }
                        p.openInventory(inv);
                    }
                }.runTask(Main.getPlugin());
            }

            @Override
            public void onFailure(Throwable cause) {}
        });
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (inv == null || !inv.getName().equalsIgnoreCase("Pets")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }

        if (i.getType().equals(Material.INK_SACK)) {
            if (i.getData().getData() == (short) 10) {
                ExtrasEvent.open(p);
            } else {
                p.closeInventory();
            }
            return;
        }

        if (i.getType().equals(Material.NAME_TAG)) {
            if (!Main.getPetAPI().hasPet(p)) {
                p.closeInventory();
                Message.ERROR.send(p, "Você não tem nenhum PET ativo.");
                return;
            }
            new PetSettings(p).open();
            return;
        }

        if (!i.getItemMeta().hasDisplayName()) {
            return;
        }
        String petName = ChatColor.stripColor(i.getItemMeta().getDisplayName());

        for (Pet pet : Pet.values()) {
            if (!petName.equalsIgnoreCase(pet.getName())) {
                continue;
            }

            if (!pet.isVip()) {
                PetAPI.hasBoughtPet(p.getUniqueId(), pet, new SQLStatus.Callback<Boolean>() {
                    @Override
                    public void onSucess(Boolean hasBought) {
                        if (!hasBought) {
                            p.closeInventory();
                            new PetBuyMenu(p, pet).open();
                            return;
                        }
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                p.closeInventory();
                                Main.getPetAPI().givePet(p, pet.getType(), false);
                                Main.getPetAPI().getPet(p).setPetName(pet.getName() + " de " + p.getName());
                                Message.GOOD.send(p, "Um(a) " + pet.getName() + " agora está te seguindo!");
                            }
                        }.runTask(Main.getPlugin());
                    }

                    @Override
                    public void onFailure(Throwable cause) {
                        p.closeInventory();
                    }
                });
                return;
            }
            if (!Rank.has(p.getUniqueId(), Rank.BRONZE)) {
                Utils.onlyVip(p);
                p.closeInventory();
                break;
            }
            p.closeInventory();
            Main.getPetAPI().givePet(p, pet.getType(), false);
            Main.getPetAPI().getPet(p).setPetName(pet.getName() + " de " + p.getName());
            Message.GOOD.send(p, "Um(a) " + pet.getName() + " agora está te seguindo!");
        }
    }
}
