package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Listener.GamerDamage;
import me.dark.kit.Kit;

public class Viking extends Kit {
	public Viking() {
		super("Viking", Material.STONE_AXE, 
				new ItemStack[] { null },
				Arrays.asList(""));
	}
	@EventHandler
	public void vikingDamage(EntityDamageByEntityEvent e) {
		if (Main.estado == GameState.PREGAME) return;
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasAbility(p)) {
				ItemStack item = p.getItemInHand();
				if ((item != null) && (item.getType().name().contains("AXE"))) {
					GamerDamage.Nerf(p, e);
					e.setDamage(e.getDamage() + 2);
				}
			}
		}
	}
}
