package net.goodcraft.skywars.eventos;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.kits.KitSelector;
import net.goodcraft.minigames.kits.ShopManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PreGameItemsListener implements Listener {
    public static ItemStack kitsChest = Item.item(Material.CHEST, 1, "§a§lKits §7(Clique Direito)");
    public static ItemStack shopChest = Item.item(Material.ENDER_CHEST, 1, "§c§lLoja §7(Clique Direito)");

    public static void setInitialItems(Player p) {
        p.getInventory().clear();
        p.getInventory().setItem(2, kitsChest);
        p.getInventory().setItem(6, shopChest);

        p.getInventory().setHeldItemSlot(2);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        setInitialItems(e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() != null && e.getAction().name().contains("RIGHT")) {
            if (e.getItem().equals(kitsChest)) {
                KitSelector.openInv(e.getPlayer());
            } else if (e.getItem().equals(shopChest)) {
                ShopManager.open(p);
            } else {
                return;
            }
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
        }
    }
}
