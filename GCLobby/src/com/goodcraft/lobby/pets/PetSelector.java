package com.goodcraft.lobby.pets;

import com.goodcraft.api.Item;
import com.goodcraft.api.Message;
import com.goodcraft.api.Utils;
import com.goodcraft.lobby.Main;
import com.goodcraft.lobby.eventos.ExtrasEvent;
import com.goodcraft.sql.SQLStatus;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PetSelector implements Listener {

    private final Player p;
//    private final Integer[] normais = new Integer[]{50, 101, 54, 65, 90, 96};
//    private final Integer[] vips = new Integer[]{61, 0, 68, 95, 98, 100, 92, 57, 62, 66, 55, 52};
//
//    private final String[] nomesNormais = new String[]{
//        "Creeper",
//        "Coelho",
//        "Zumbi",
//        "Morcego",
//        "Porco",
//        "Vaca Cogumelo"
//    };
//    private final String[] nomesVips = new String[]{
//        "Blaze",
//        "Iron Golem",
//        "Guardian",
//        "Lobo",
//        "Jaguatirica",
//        "Cavalo",
//        "Vaca",
//        "Zombie Pigman",
//        "Magma Cube",
//        "Bruxa",
//        "Slime",
//        "Aranha"
//    };
//
//    private final PetType[] petsNormais = new PetType[]{
//        PetType.CREEPER,
//        PetType.RABBIT,
//        PetType.ZOMBIE,
//        PetType.BAT,
//        PetType.PIG,
//        PetType.MUSHROOMCOW
//    };
//    private final PetType[] petsVips = new PetType[]{
//        PetType.BLAZE,
//        PetType.IRONGOLEM,
//        PetType.GUARDIAN,
//        PetType.WOLF,
//        PetType.OCELOT,
//        PetType.HORSE,
//        PetType.COW,
//        PetType.PIGZOMBIE,
//        PetType.MAGMACUBE,
//        PetType.WITCH,
//        PetType.SLIME,
//        PetType.SPIDER
//
//    };

    public PetSelector(Player p) {
        this.p = p;
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(p, 54, "Pets");

        inv.setItem(3, Item.item(Material.INK_SACK, 1, "§eVoltar", 10));
        inv.setItem(5, Item.item(Material.INK_SACK, 1, "§eFechar", 8));

        inv.setItem(9, Item.item(Material.PAPER, 1, "§f§lPara todos"));
        inv.setItem(27, Item.item(Material.PAPER, 1, "§f§lPara VIPs"));

        inv.setItem(49, Item.item(Material.EMERALD, 1, "§aGood§fCoins§7: §8" + SQLStatus.getFormattedCoins(p.getUniqueId())));
        inv.setItem(53, Item.item(Material.NAME_TAG, 1, "§fConfigurar PET"));

        int slotNormais = 11;
        int slotVips = 29;

        for (Pet pet : Pet.values()) {
            if (!pet.isVip()) {
                boolean hasBought = PetAPI.hasBoughtPet(p.getUniqueId(), pet);

                ItemStack item = pet.getItem().clone();
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName((hasBought ? "§a" : "§c") + pet.getName());

                if (!hasBought) {
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add("§ePreço: §7" + pet.getPrice() + " §aGoodCoins");
                    itemMeta.setLore(lore);
                }
                item.setItemMeta(itemMeta);

                inv.setItem(slotNormais, item);

                slotNormais++;
            } else {
                ItemStack item = pet.getItem().clone();
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName((p.hasPermission("gc.vippets") ? "§a" : "§c") + pet.getName());
                item.setItemMeta(itemMeta);
                inv.setItem(slotVips, item);

                slotVips++;
            }
        }

        p.openInventory(inv);
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
                boolean hasBought = PetAPI.hasBoughtPet(p.getUniqueId(), pet);
                if (!hasBought) {
                    new PetBuyMenu(p, pet).open();
                    break;
                }
            } else if (!p.hasPermission("gc.vippets")) {
                Utils.onlyVip(p);
                p.closeInventory();
                break;
            }
            Main.getPetAPI().givePet(p, pet.getType(), false);
            Main.getPetAPI().getPet(p).setPetName(pet.getName() + " de " + p.getName());
            Message.GOOD.send(p, "Um(a) " + pet.getName() + " agora está te seguindo!");

            if (ExtrasEvent.coloredSheepOn.containsKey(p.getName())) {
                Sheep sheep = ExtrasEvent.coloredSheepOn.get(p.getName());
                sheep.eject();
                sheep.remove();
                ExtrasEvent.coloredSheepOn.remove(p.getName());
                return;
            }

            p.closeInventory();
        }
    }
}
