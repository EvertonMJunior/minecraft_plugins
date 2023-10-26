package net.goodcraft.minigames.eventos;

import net.goodcraft.Main;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.minigames.kits.KitManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ListIterator;

public class DropItemEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        final Player p = e.getEntity();

        if(!Main.minigame.hasOption(Option.DROP_ITEMS_ON_DEATH)){
            e.getDrops().clear();
            return;
        }

        Kit k = KitManager.getKit(p);
        if (k == null) {
            return;
        }
        for (ListIterator<ItemStack> item = e.getDrops().listIterator(); item.hasNext();) {
            ItemStack i = item.next();
            if (k.getItems() == null) return;
            for (ItemStack kItem : k.getItems()) {
                if (kItem == null) return;
                if (!i.equals(kItem)) return;
                item.remove();
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ItemStack i = e.getItemDrop().getItemStack();
        Kit k = KitManager.getKit(p);
        if (k == null) {
            return;
        }
        if (k.getItems() == null) return;
        for (ItemStack item : k.getItems()) {
            if (item == null) continue;
            if (!i.equals(item)) continue;
            e.setCancelled(true);
        }
    }
}