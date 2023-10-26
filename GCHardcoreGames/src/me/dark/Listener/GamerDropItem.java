package me.dark.Listener;

import java.util.ListIterator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.kit.Kit;
import me.dark.kit.KitManager;

public class GamerDropItem implements Listener{
	
	@EventHandler
	public void kill(PlayerDeathEvent e) {
		final Player p = e.getEntity();
		Kit k = KitManager.getKit(p);
		if (k == null) {
			return;
		}
		for (ListIterator<ItemStack> item = e.getDrops().listIterator(); item.hasNext(); ) {
			ItemStack i = item.next();
			if (k.getItems() != null) {
				for (ItemStack todos : k.getItems()) {
					if (todos != null) {
						if (i.equals(todos)) {
							item.remove();
					    }
					}
				}
			}
		}
	}
	
	@EventHandler
	public void drop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		ItemStack i = e.getItemDrop().getItemStack();
		Kit k = KitManager.getKit(p);
		if (k == null) {
			return;
		}
		if (k.getItems() != null) {
			for (ItemStack todos : k.getItems()) {
				if (todos != null) {
					if (i.equals(todos)) {
						e.setCancelled(true);
				    }
				}
			}
		}
	}

}
