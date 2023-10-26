package net.goodcraft.api;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class Item {

    public static ItemStack item(Material m) {
        return new ItemStack(m);
    }

    public static ItemStack item(Material m, int q) {
        return new ItemStack(m, q);
    }

    public static ItemStack item(Material m, int q, String n) {
        ItemStack it = new ItemStack(m, q);
        ItemMeta me = it.getItemMeta();
        me.setDisplayName(n);
        it.setItemMeta(me);
        return it;
    }

    public static ItemStack item(Material m, int q, String n, int t) {
        ItemStack it = new ItemStack(m, q, (byte) t);
        ItemMeta me = it.getItemMeta();
        me.setDisplayName(n);
        it.setItemMeta(me);
        return it;
    }

    public static ItemStack item(Material m, int q, String n, Enchantment en, int level) {
        ItemStack it = new ItemStack(m, q);
        ItemMeta me = it.getItemMeta();
        me.setDisplayName(n);
        it.setItemMeta(me);
        it.addUnsafeEnchantment(en, level);
        return it;
    }

    public static ItemStack item(Material m, int q, String n, Enchantment en, int level, int t) {
        ItemStack it = new ItemStack(m, q, (byte) t);
        ItemMeta me = it.getItemMeta();
        me.setDisplayName(n);
        it.setItemMeta(me);
        it.addUnsafeEnchantment(en, level);
        return it;
    }

    public static ItemStack item(Material m, int q, String n, String[] l) {
        ItemStack it = new ItemStack(m, q);
        ItemMeta me = it.getItemMeta();
        me.setLore(Arrays.asList(l));
        me.setDisplayName(n);
        it.setItemMeta(me);
        return it;
    }

    public static ItemStack item(Material m, int q, String n, String[] l, int t) {
        ItemStack it = new ItemStack(m, q, (byte) t);
        ItemMeta me = it.getItemMeta();
        me.setLore(Arrays.asList(l));
        me.setDisplayName(n);
        it.setItemMeta(me);
        return it;
    }

    public static ItemStack item(PotionType pot, int q, String n, String[] l, boolean splash, boolean extended, int level) {
        Potion pot1 = new Potion(pot, level);
        if (!pot.name().contains("INSTANT")) {
            pot1.setHasExtendedDuration(extended);
        }
        pot1.setSplash(splash);
        ItemStack it = pot1.toItemStack(q);
        ItemMeta me = it.getItemMeta();
        me.setLore(Arrays.asList(l));
        if(n != null){
            me.setDisplayName(n);
        }
        it.setItemMeta(me);
        return it;
    }

    public static ItemStack leatherArmor(Material m, String n, Color cor) {
        if (!m.name().contains("LEATHER_")) {
            return null;
        }
        ItemStack it = new ItemStack(m);
        LeatherArmorMeta me = (LeatherArmorMeta) it.getItemMeta();
        me.setColor(cor);
        me.setDisplayName(n);
        it.setItemMeta(me);
        return it;
    }

    public static ItemStack leatherArmor(Material m, int q, String n, String[] l, Color cor) {
        if (!m.name().contains("LEATHER_")) {
            return null;
        }
        ItemStack it = new ItemStack(m, q);
        LeatherArmorMeta me = (LeatherArmorMeta) it.getItemMeta();
        me.setColor(cor);
        me.setDisplayName(n);
        me.setLore(Arrays.asList(l));
        it.setItemMeta(me);
        return it;
    }

    public static ItemStack getHead(String name, int q, String n) {
        ItemStack it = new ItemStack(Material.SKULL_ITEM, q,
                (short) SkullType.PLAYER.ordinal());
        SkullMeta me = (SkullMeta) it.getItemMeta();
        me.setDisplayName(n);
        me.setOwner(name);
        it.setItemMeta(me);
        return it;
    }

    public static ItemStack getHead(String name, int q, String n, String[] l) {
        ItemStack it = new ItemStack(Material.SKULL_ITEM, q,
                (short) SkullType.PLAYER.ordinal());
        SkullMeta me = (SkullMeta) it.getItemMeta();
        me.setDisplayName(n);
        me.setOwner(name);
        me.setLore(Arrays.asList(l));
        it.setItemMeta(me);
        return it;
    }

    public static ItemStack addGlow(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack removeGlow(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        im.removeEnchant(Enchantment.ARROW_DAMAGE);
        im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);
        return item;
    }

    public static boolean hasGlow(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        return im.hasEnchant(Enchantment.ARROW_DAMAGE) && im.hasItemFlag(ItemFlag.HIDE_ENCHANTS);
    }

    public static ItemStack addItemFlags(ItemStack item, ItemFlag... flags){
        ItemMeta im = item.getItemMeta();
        im.addItemFlags(flags);
        item.setItemMeta(im);
        return item;
    }
}
