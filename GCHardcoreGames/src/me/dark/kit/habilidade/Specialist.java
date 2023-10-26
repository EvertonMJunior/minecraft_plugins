package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;

public class Specialist extends Kit {
	public Specialist() {
		super("Specialist", Material.ENCHANTED_BOOK, 
				new ItemStack[] { DarkUtils.create_item(Material.ENCHANTED_BOOK, "§3Specialist", 1, 0, null) },
				Arrays.asList(""));
	}
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		if (Main.estado == GameState.PREGAME) return;
		if(e.getEntity() instanceof Player) {
			if(e.getEntity().getKiller() instanceof Player) {
				Player p = e.getEntity().getKiller();
				if(hasAbility(p)) {
					p.setLevel(p.getLevel() + 1);
					p.sendMessage("§bVocê recebeu um 1 de xp");
				}
			}
		}
	}
	
	@EventHandler
	public void InteractBook(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(p.getItemInHand().getType() == Material.ENCHANTED_BOOK && hasAbility(p)) {
			p.openEnchanting(p.getLocation(), true);
			return;
		}
	}

}
