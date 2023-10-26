package me.dark.kit.habilidade;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;

public class Fisherman extends Kit{
	public Fisherman() {
		super("Fisherman", Material.FISHING_ROD, 
				new ItemStack[] { DarkUtils.create_item(Material.FISHING_ROD, "§3Fisherman", 1, 0, null) },
				Arrays.asList("§7Use uma vara de pesca para fisgar",
						      "§7seus inimigos usando o botão direito",
						      "§7com o seu adversário já fisgado!"));
	}
	@EventHandler
	public void FisherHomi(PlayerFishEvent event) {
		
		Player p = event.getPlayer();
		if ((event.getCaught() instanceof Player)) {
			Player caught = (Player)event.getCaught();
			if(hasAbility(p) && (p.getItemInHand().getType() == Material.FISHING_ROD)) {
				caught.teleport(p.getLocation());
			}
	    }
	 }

}
