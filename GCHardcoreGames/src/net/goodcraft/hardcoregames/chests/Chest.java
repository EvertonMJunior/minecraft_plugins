package net.goodcraft.hardcoregames.chests;

import com.google.gson.reflect.TypeToken;
import net.goodcraft.api.json.JSONUtils;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.inventory.ItemStack;

public class Chest {
    private final String name;
    private final String[] desc;
    private final boolean usesItems;
    private boolean isRandom;
    private int quantity;

    public Chest(String name, String[] desc, ItemStack... items) {
        this.name = name;
        this.desc = desc;
        this.usesItems = true;
    }

    public Chest(String name, String[] desc, Kit... kits) {
        this.name = name;
        this.desc = desc;
        this.usesItems = false;
    }

    public static Chest fromJson(String json) {
        try {
            return (Chest) JSONUtils.fromJson(json, new TypeToken<Chest>() {
            }.getType());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String[] getDesc() {
        return desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setRandom() {
        isRandom = true;
    }

    public boolean isRandom() {
        return isRandom;
    }

    public boolean usesItems() {
        return usesItems;
    }

    public String toJson() {
        return JSONUtils.toJson(this);
    }
}
