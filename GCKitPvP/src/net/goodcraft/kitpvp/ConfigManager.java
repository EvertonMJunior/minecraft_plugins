package net.goodcraft.kitpvp;

import net.goodcraft.GameMode;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.minigames.Scoreboard;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class ConfigManager implements Listener {

    public static ItemStack getRegenerationItem(int type) {
        return type == 0 ?
                Item.item(Material.MUSHROOM_SOUP, 1, "§fSopa de Cogumelos", new String[]{
                        "§7Clique para usar sopas"
                }) : Item.item(PotionType.INSTANT_HEAL, 1, "§fPoção de Regeneração", new String[]{
                "§7Clique para usar poções"
        }, true, false, 1);
    }

    public static ItemStack getScoreboardItem(int type) {
        return type == 0 ?
                Item.item(Material.REDSTONE, 1, "§cScoreboard desativada", new String[]{
                        "§7Clique para ativá-la"
                }) : Item.item(Material.GLOWSTONE_DUST, 1, "§aScoreboard ativada", new String[]{
                "§7Clique para desativá-la"
        });
    }

    public static void open(Player p) {
        SQLStatus.getStat(p.getUniqueId(), "good_kitpvp", "regenerationitem", new SQLStatus.Callback<Object>() {
            @Override
            public void onSucess(Object o) {
                Integer regenType = (Integer) o;

                final ItemStack regenerationItem = Item.addItemFlags(getRegenerationItem(regenType),
                        ItemFlag.HIDE_ATTRIBUTES);
                ;

                SQLStatus.getStat(p.getUniqueId(), "good_kitpvp", "scoreboardstate", new SQLStatus.Callback<Object>() {
                    @Override
                    public void onSucess(Object o) {
                        Integer scoreboardType = (Integer) o;

                        final ItemStack scoreboardItem = Item.addItemFlags(getScoreboardItem(scoreboardType),
                                ItemFlag.HIDE_ATTRIBUTES);

                        Inventory inv = Bukkit.createInventory(p, 27, "Configurações");

                        inv.setItem(12, regenerationItem);
                        inv.setItem(14, scoreboardItem);

                        p.openInventory(inv);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Message.ERROR.send(p, "Estamos enfrentando um problema com nosso Banco de Dados. Tente mais tarde!");
                    }
                });
            }

            @Override
            public void onFailure(Throwable throwable) {
                Message.ERROR.send(p, "Estamos enfrentando um problema com nosso Banco de Dados. Tente mais tarde!");
            }
        });
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack item = e.getCurrentItem();

        if (inv == null) return;
        if (!inv.getName().equalsIgnoreCase("Configurações")) return;
        if (item == null) return;
        if (item.getItemMeta() == null) return;
        if (!item.getItemMeta().hasDisplayName()) return;

        e.setCancelled(true);

        if(e.getSlot() == 12){
            Integer newType = item.getItemMeta().getDisplayName().equalsIgnoreCase("§fSopa de Cogumelos") ? 1 : 0;
            SQLStatus.addStatus(p.getUniqueId(), GameMode.KITPVP, "regenerationitem", newType == 1 ? 1 : -1);
            e.setCurrentItem(Item.addItemFlags(getRegenerationItem(newType),
                    ItemFlag.HIDE_ATTRIBUTES));
            return;
        }

        if(e.getSlot() == 14){
            Integer newType = item.getItemMeta().getDisplayName().equalsIgnoreCase("§cScoreboard desativada") ? 1 : 0;
            SQLStatus.addStatus(p.getUniqueId(), GameMode.KITPVP, "scoreboardstate", newType == 1 ? 1 : -1);
            e.setCurrentItem(Item.addItemFlags(getScoreboardItem(newType),
                    ItemFlag.HIDE_ATTRIBUTES));


            Scoreboard sb = Main.getMg().getScoreboard();
            if(newType == 1){
                sb.createBoard(p);
                sb.SCOREOFF.remove(p.getUniqueId());
            } else {
                sb.removeBoard(p);
                sb.SCOREOFF.add(p.getUniqueId());
            }
            return;
        }
    }

}
