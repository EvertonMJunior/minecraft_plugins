package me.everton.epvp.Kits.Habilidades;

import java.util.Random;

import me.everton.epvp.API;
import me.everton.epvp.Main;
import me.everton.epvp.Listeners.PlayerReceiveKitEvent;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Cactus implements Listener{
	@EventHandler
	public void espinhos(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player)) {
			return;
		}
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		Player d = (Player) e.getDamager();
		
		if(Main.cactus.contains(p.getName())) {
			Double chance = 15D / 100;
			if(new Random().nextDouble() <= chance) {
				d.damage(2.0D, p);
			}
		}
	}
	
	@EventHandler
	public void setCactus(PlayerReceiveKitEvent e) {
		Player p = e.getPlayer();
		String kit = e.getKit();
		if(kit.equalsIgnoreCase("cactus")) {
			p.getInventory().setHelmet(API.item(Material.CACTUS, 1, "§a§lCactus", new String[] {"§bQuando lhe derem hits, você terá a chance", "§bde dar dano em quem lhe hitou!"}));
		}
	}
}
