package com.goodcraft.api;

import com.goodcraft.Main;
import com.goodcraft.sql.SQLStatus;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AdminAPI {

    public static HashMap<String, AdminAPI> admins = new HashMap<>();
    private final HashMap<String, ItemStack[]> invItems = new HashMap<>();

    public UUID playerBeingTested = null;
    public UUID playerInfo = null;
    private final Player p;

    public AdminAPI(Player p) {
        this.p = p;
    }

    public boolean putOrRemove() {
        if (!admins.containsKey(p.getName())) {
            admins.put(p.getName(), this);
            invItems.put(p.getName(), p.getInventory().getContents());

            Utils.hideForAll(p, "gc.admin");
            p.setGameMode(GameMode.CREATIVE);
            giveItems();

            Message.INFO.send(p, "Você entrou no modo ADMINISTRADOR.");
            Message.INFO.send(p, "Agora você está invisível para todos.");

            return true;
        }
        admins.remove(p.getName());

        p.getInventory().setContents(invItems.get(p.getName()));
        invItems.remove(p.getName());

        Utils.showForAll(p);
        p.setGameMode(GameMode.ADVENTURE);

        Message.INFO.send(p, "Você saiu do modo ADMINISTRADOR.");
        Message.INFO.send(p, "Agora você está visível para todos.");
        return false;
    }

    public void testHacks(Player t) {
        playerBeingTested = t.getUniqueId();

        Inventory inv = Bukkit.createInventory(p, 27, "Verificar Hacks");

        inv.setItem(10, Item.item(Material.STONE_SWORD, 1, "§eKillAura/FF"));
        inv.setItem(11, Item.item(Material.WOOD_AXE, 1, "§eMacro"));
        inv.setItem(13, Item.item(Material.IRON_BOOTS, 1, "§eNoFall"));
        inv.setItem(15, Item.item(Material.MUSHROOM_SOUP, 1, "§eAutoSoup"));
        inv.setItem(16, Item.item(Material.ANVIL, 1, "§eAntiKnockback"));

        p.openInventory(inv);
    }

    public void fastChange() {
        int slot = p.getInventory().getHeldItemSlot();
        ItemStack i = p.getInventory().getItem(slot);
        i.setType(Material.SLIME_BALL);

        p.setGameMode(GameMode.ADVENTURE);
        p.setAllowFlight(true);
        p.setFlying(true);

        Utils.showForAll(p);

        new BukkitRunnable() {
            int seconds = 1;

            @Override
            @SuppressWarnings("deprecation")
            public void run() {
                seconds++;
                if (seconds >= 5) {
                    Utils.hideForAll(p, "gc.admin");

                    p.setGameMode(GameMode.CREATIVE);
                    p.setFlying(true);

                    p.getInventory().getItem(slot).setType(Material.MAGMA_CREAM);

                    cancel();
                    return;
                }
                p.setVelocity(new Vector());
                p.setHealth(20D);
            }
        }.runTaskTimer(Main.getPlugin(), 0L, 1L);
    }

    public void showInfo(Player of) {
        playerInfo = of.getUniqueId();
        Inventory inv = Bukkit.createInventory(p, 27, "Informações do Jogador");

        inv.setItem(10, Item.item(Material.EMERALD, 1, "§aGood§fCoins§7: §8" + SQLStatus.getFormattedCoins(of.getUniqueId())));
        inv.setItem(11, Item.item(Material.NAME_TAG, 1, "§eNick: §7" + of.getName(), new String[]{
            "§eUUID: §7" + of.getUniqueId(),
            "§eRank: §4DONO",
            "§ePrimeira vez que entrou: §7" + SQLStatus.getFirstJoin(of.getUniqueId()),
            "§eFake: §7Nenhum"
        }));
        inv.setItem(13, Item.item(Material.CHEST, 1, "§eAbrir inventário"));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.getOpenInventory().getTitle().equalsIgnoreCase("Informações do Jogador")) {
                    cancel();
                    return;
                }
                if (!playerInfo.equals(of.getUniqueId())) {
                    cancel();
                    return;
                }
                if (Bukkit.getPlayer(playerInfo) == null) {
                    cancel();
                    return;
                }
                inv.setItem(15, Item.item(Material.GOLDEN_APPLE, 1, "§eVida: §7" + new DecimalFormat("#.#").format(of.getHealth() / 2)));
                inv.setItem(16, Item.item(Material.COOKED_BEEF, 1, "§eSaturação: §7" + new DecimalFormat("#.#").format(of.getFoodLevel() / 2)));
            }
        }.runTaskTimer(Main.getPlugin(), 0L, 5L);

        p.openInventory(inv);
    }

    private void giveItems() {
        p.getInventory().clear();

        ItemStack testHackItem = Item.item(Material.ARROW, 1, "§eTestar Hacks §7(Clique Direito)");
        ItemStack playerInfoItem = Item.item(Material.BLAZE_ROD, 1, "§eInformações do Jogador §7(Clique Direito)");
        ItemStack fastChangeItem = Item.item(Material.MAGMA_CREAM, 1, "§eTroca Rápida §7(Clique Direito)");

        p.getInventory().setItem(1, testHackItem);
        p.getInventory().setItem(4, playerInfoItem);
        p.getInventory().setItem(7, fastChangeItem);
    }
}
