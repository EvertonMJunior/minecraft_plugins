package com.goodcraft.bungee.api;

import dev.wolveringer.BungeeUtil.Material;
import dev.wolveringer.BungeeUtil.MaterialData;
import dev.wolveringer.BungeeUtil.item.ItemStack;
import dev.wolveringer.BungeeUtil.item.itemmeta.ItemMeta;

import java.util.Arrays;
@SuppressWarnings("deprecation")
public class Item {

    public static ItemStack item(Material m) {
        return new ItemStack(m) {
            @Override
            public void click(Click click) {
            }
        };
    }

    public static ItemStack item(Material m, int q) {
        ItemStack iS = new ItemStack(m) {
            @Override
            public void click(Click click) {

            }
        };
        iS.setAmount(q);
        return iS;
    }

    public static ItemStack item(Material m, int q, String n) {
        ItemStack it = new ItemStack(m) {
            @Override
            public void click(Click click) {

            }
        };
        it.setAmount(q);
        ItemMeta me = it.getItemMeta();
        me.setDisplayName(n);
        it.setTag(me.getTag());
        return it;
    }

    public static ItemStack item(Material m, int q, String n, int t) {
        ItemStack it = new ItemStack(m) {
            @Override
            public void click(Click click) {

            }
        };
        MaterialData md = it.getData();
        md.setData((byte)t);
        it.setData(md);
        it.setAmount(q);
        ItemMeta me = it.getItemMeta();
        me.setDisplayName(n);
        it.setTag(me.getTag());
        return it;
    }

    public static ItemStack item(Material m, int q, String n, String[] l) {
        ItemStack it = new ItemStack(m) {
            @Override
            public void click(Click click) {

            }
        };
        it.setAmount(q);
        ItemMeta me = it.getItemMeta();
        me.setLore(Arrays.asList(l));
        me.setDisplayName(n);
        it.setTag(me.getTag());
        return it;
    }

    public static ItemStack item(Material m, int q, String n, String[] l, int t) {
        ItemStack it = new ItemStack(m) {
            @Override
            public void click(Click click) {

            }
        };
        it.setAmount(q);
        MaterialData md = it.getData();
        md.setData((byte)t);
        it.setData(md);
        ItemMeta me = it.getItemMeta();
        me.setLore(Arrays.asList(l));
        me.setDisplayName(n);
        it.setTag(me.getTag());
        return it;
    }

    public static ItemStack addGlow(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        im.setGlow(true);
        item.setTag(im.getTag());
        return item;
    }
}
