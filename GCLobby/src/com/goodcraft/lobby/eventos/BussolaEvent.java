package com.goodcraft.lobby.eventos;

import com.goodcraft.GameMode;
import com.goodcraft.api.Item;
import com.goodcraft.api.Message;
import com.goodcraft.api.Title;
import com.goodcraft.sql.SQLStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BussolaEvent implements Listener {

    public static Inventory socialMediasInv = null;
    public static HashMap<UUID, Inventory> gameModesInvs = new HashMap<>();

    public static void saveGameModesGui(Player p) {
        if(gameModesInvs.containsKey(p.getUniqueId())) return;
        Inventory inv = Bukkit.createInventory(p, 45, "Modos de Jogo");

        inv.setItem(36, Item.item(Material.COMPASS, 1, "§eVoltar ao Lobby"));
        inv.setItem(44, Item.item(Material.INK_SACK, 1, "§eFechar menu", 8));

        Integer[] slots = new Integer[]{10, 11, 13, 15, 16, 19, 20, 21, 22, 23, 24, 25};
        int value = 0;

        for (GameMode gm : GameMode.values()) {
            if (gm == GameMode.LOBBY) {
                continue;
            }
            if (value >= slots.length) {
                break;
            }

            List<String> l = new ArrayList<>();
            l.addAll(Arrays.asList(gm.getDescription()));
            l.add("§7 ");
            l.add("§7► Clique para entrar");

            ItemStack item = gm.getItem().clone();
            ItemMeta im = item.getItemMeta();
            im.setDisplayName("§f§l" + gm.getName() + (!gm.isActive() ? " §6§lEM BREVE" : ""));
            im.setLore(l);
            item.setItemMeta(im);

            inv.setItem(slots[value], item);
            value++;
        }
        inv.setItem(42, Item.item(Material.PAPER, 1, "§fRedes Sociais"));
        inv.setItem(40, Item.getHead(p.getName(), 1, "§fStatus"));
        
        gameModesInvs.put(p.getUniqueId(), inv);
    }

    public static void saveSocialMediasGui() {
        socialMediasInv = Bukkit.createInventory(null, 27, "Redes Sociais");

        socialMediasInv.setItem(0, Item.item(Material.INK_SACK, 1, "§eVoltar", 10));
        socialMediasInv.setItem(8, Item.item(Material.INK_SACK, 1, "§eSair", 8));
        socialMediasInv.setItem(10, Item.item(Material.REDSTONE_BLOCK, 1, "§fCanal no §cYoutube"));
        socialMediasInv.setItem(12, Item.item(Material.LAPIS_BLOCK, 1, "§fPágina no §9Facebook"));
        socialMediasInv.setItem(14, Item.item(Material.ICE, 1, "§fPerfil no §bTwitter"));
        socialMediasInv.setItem(16, Item.item(Material.EMERALD_BLOCK, 1, "§aSite"));
    }

    private static void openGameModesGui(Player p) {
        Inventory inv = gameModesInvs.get(p.getUniqueId());
        inv.setItem(38, Item.item(Material.EMERALD, 1, "§fGoodCoins§7: §a" + SQLStatus.getFormattedCoins(p.getUniqueId())));

        p.openInventory(inv);
    }

    private static void openSocialMediasGui(Player p) {
        if (socialMediasInv != null) {
            p.openInventory(socialMediasInv);
            return;
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();

        if (!e.getAction().name().contains("RIGHT")) {
            return;
        }

        if (i.getType() == Material.AIR) {
            return;
        }

        if (!i.getType().equals(Material.COMPASS)) {
            return;
        }

        if (!i.getItemMeta().getDisplayName().contains("Modos de Jogo")) {
            return;
        }
        openGameModesGui(p);

        e.setCancelled(true);
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (inventory == null || !inventory.getName().equalsIgnoreCase("Modos de Jogo")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }

        if (i.getType().equals(Material.INK_SACK) && i.getData().getData() == (short) 8) {
            p.closeInventory();
            return;
        }

        if (i.getType().equals(Material.PAPER)) {
            openSocialMediasGui(p);
            return;
        }
        
        if(i.getType().equals(Material.EMERALD)){
            return;
        }

        if (i.getItemMeta().getDisplayName().contains("Status")) {
            StatusEvent.openInv(p);
            return;
        }

        p.closeInventory();

        GameMode tp = null;
        for (GameMode tP : GameMode.values()) {
            if (!i.getItemMeta().getDisplayName().contains(tP.getName())) {
                continue;
            }
            if (!tP.isActive()) {
                Title.INFO.send(p, tP.getName().toUpperCase(), "Em breve!");
                break;
            }

            tp = tP;
            break;
        }
        if(tp == null){
            return;
        }

        try {
            p.teleport(tp.getLocation());
        } catch (Exception e2) {
            Message.ERROR.send(p, "Desculpe, no momento este lobby não está disponível.");
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInvClickRedesSociais(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (inventory == null || !inventory.getName().equalsIgnoreCase("Redes Sociais")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }

        if (i.getType().equals(Material.INK_SACK) && i.getData().getData() == (short) 8) {
            p.closeInventory();
            return;
        }

        if (i.getType().equals(Material.INK_SACK) && i.getData().getData() == (short) 10) {
            openGameModesGui(p);
            return;
        }

        switch (i.getType().toString()) {
            case "REDSTONE_BLOCK":
                Title.INFO.send(p, "Youtube", "youtube.com/user/GC");
                Message.INFO.send(p, "Youtube: §fyoutube.com/user/GC");
                break;
            case "LAPIS_BLOCK":
                Title.INFO.send(p, "Facebook", "facebook.com/GC");
                Message.INFO.send(p, "Facebook: §ffacebook.com/GC");
                break;
            case "ICE":
                Title.INFO.send(p, "Twitter", "twitter.com/GC");
                Message.INFO.send(p, "Twitter: §ftwitter.com/GC");
                break;
            case "EMERALD_BLOCK":
                Title.INFO.send(p, "Site", "good-craft.net");
                Message.INFO.send(p, "Site: §fgood-craft.net");
                break;
        }

        p.closeInventory();
    }
}
