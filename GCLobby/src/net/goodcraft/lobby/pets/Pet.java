package net.goodcraft.lobby.pets;

import net.goodcraft.api.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Pet {
    CREEPER("Creeper", Item.item(Material.MONSTER_EGG, 1, "", 50), 3000),
    RABBIT("Coelho", Item.item(Material.MONSTER_EGG, 1, "", 101), 2000),
    ZOMBIE("Zumbi", Item.item(Material.MONSTER_EGG, 1, "", 54), 3000),
    BAT("Morcego", Item.item(Material.MONSTER_EGG, 1, "", 65), 2500),
    PIG("Porco", Item.item(Material.MONSTER_EGG, 1, "", 90), 1500),
    MUSHROOMCOW("Vaca Cogumelo", Item.item(Material.MONSTER_EGG, 1, "", 96), 2000),
    BLAZE("Blaze", Item.item(Material.MONSTER_EGG, 1, "", 61), 0),
    IRONGOLEM("Iron Golem", Item.item(Material.PUMPKIN, 1, ""), 0),
    GUARDIAN("Guardião", Item.item(Material.MONSTER_EGG, 1, "", 68), 0),
    WOLF("Lobo", Item.item(Material.MONSTER_EGG, 1, "", 95), 0),
    OCELOT("Jaguatirica", Item.item(Material.MONSTER_EGG, 1, "", 98), 0),
    HORSE("Cavalo", Item.item(Material.MONSTER_EGG, 1, "", 100), 0),
    COW("Vaca", Item.item(Material.MONSTER_EGG, 1, "", 92), 0),
    PIGZOMBIE("Zombie Pigman", Item.item(Material.MONSTER_EGG, 1, "", 57), 0),
    MAGMACUBE("Magma Cube", Item.item(Material.MONSTER_EGG, 1, "", 62), 0),
    WITCH("Bruxa", Item.item(Material.MONSTER_EGG, 1, "", 66), 0),
    SLIME("Slime", Item.item(Material.MONSTER_EGG, 1, "", 55), 0),
    SPIDER("Aranha", Item.item(Material.MONSTER_EGG, 1, "", 52), 0);

    private final String name;
    private final ItemStack item;
    private final int price;

    private Pet(String name, ItemStack item, int price) {
        this.name = name;
        this.item = item;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public com.dsh105.echopet.compat.api.entity.PetType getType() {
        return com.dsh105.echopet.compat.api.entity.PetType.valueOf(name());
    }

    public ItemStack getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public boolean isVip() {
        return price == 0;
    }
}
