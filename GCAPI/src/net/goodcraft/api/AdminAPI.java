package net.goodcraft.api;

import net.goodcraft.Main;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.UUID;

public class AdminAPI {

    public static HashMap<String, AdminAPI> admins = new HashMap<>();
    private final HashMap<String, ItemStack[]> invItems = new HashMap<>();
    private final Player p;
    public UUID playerBeingTested = null;
    public UUID playerInfo = null;

    public AdminAPI(Player p) {
        this.p = p;
    }

    public ItemStack[] getInvItems() {
        return invItems.get(p.getName());
    }

    public void clearInvItems() {
        invItems.remove(p.getName());
    }

    public Player getPlayer() {
        return this.p;
    }

    public boolean putOrRemove() {
        if (!admins.containsKey(p.getName())) {
            admins.put(p.getName(), this);
            invItems.put(p.getName(), p.getInventory().getContents());

            Utils.hideForAllExcept(p, Rank.MOD);
            p.setGameMode(GameMode.CREATIVE);
            giveItems();

            Message.INFO.send(p, "Você entrou no modo ADMINISTRADOR.");
            Message.INFO.send(p, "Agora você está invisível para todos.");

            Bukkit.getPluginManager().callEvent(new AdminEvent(this, true));

            p.spigot().setCollidesWithEntities(false);
            return true;
        }
        admins.remove(p.getName());

        p.getInventory().setContents(invItems.get(p.getName()));
        invItems.remove(p.getName());

        Utils.showForAll(p);
        p.setGameMode(GameMode.ADVENTURE);

        Message.INFO.send(p, "Você saiu do modo ADMINISTRADOR.");
        Message.INFO.send(p, "Agora você está visível para todos.");

        p.spigot().setCollidesWithEntities(true);

        Bukkit.getPluginManager().callEvent(new AdminEvent(this, false));

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
                    Utils.hideForAllExcept(p, Rank.MOD);

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
        Rank pRank = Rank.getPlayerRank(of.getUniqueId());

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

                inv.setItem(11, Item.item(Material.EMERALD, 1, "§fGoodCoins: §7" + coins));
            }

            @Override
            public void onFailure(Throwable throwable) {
                inv.setItem(11, Item.item(Material.EMERALD, 1, "§fGoodCoins: §70"));
            }
        });

        SQLStatus.getFirstJoin(p.getUniqueId(), new SQLStatus.Callback<String>() {
            @Override
            public void onSucess(String done) {
                inv.setItem(10, Item.item(Material.NAME_TAG, 1, "§eNick: §7" + of.getName(), new String[]{
                        "§eUUID: §7" + of.getUniqueId(),
                        "§eRank: " + pRank.getColor() + pRank.name(),
                        "§ePrimeira vez que entrou: §7" + done,
                        "§eFake: §7Nenhum"
                }));
            }

            @Override
            public void onFailure(Throwable cause) {
                inv.setItem(10, Item.item(Material.NAME_TAG, 1, "§eNick: §7" + of.getName(), new String[]{
                        "§eUUID: §7" + of.getUniqueId(),
                        "§eRank: " + pRank.getColor() + pRank.name(),
                        "§ePrimeira vez que entrou: §7Sem registros",
                        "§eFake: §7Nenhum"
                }));
            }
        });
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
                inv.setItem(15, Item.item(Material.GOLDEN_APPLE, 1, "§eVida: §7" + new DecimalFormat("#.#").
                        format(of.getHealth() / 2)));
                inv.setItem(16, Item.item(Material.COOKED_BEEF, 1, "§eSaturação: §7" + new DecimalFormat("#.#").
                        format(of.getFoodLevel() / 2)));
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
