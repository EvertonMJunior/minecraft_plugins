package me.everton.epvp.e1v1;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Listener1v1 implements Listener{
	@EventHandler
	public void desafiar(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if(e.getRightClicked() instanceof Player) {
			Player r = (Player) e.getRightClicked();
			if((Main1v1.on1v1.contains(p.getName())) && (Main1v1.on1v1.contains(r.getName()))) {
				if(p.getItemInHand().getType() == Material.BLAZE_ROD) {
					Main1v1.desafiarPlayer(p, r, "normal");
				} else if(p.getItemInHand().getType() == Material.IRON_INGOT) {
					Main1v1.desafiarPlayer(p, r, "refil");
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void fast1v1(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(Main1v1.on1v1.contains(p.getName())) {
			if(e.getAction().name().contains("RIGHT")) {
				if(p.getItemInHand().getType() == Material.INK_SACK) {
					if(p.getItemInHand().getData().getData() == 10) {
						Main1v1.Fila1v1Rapido(p);
					} else if(p.getItemInHand().getData().getData() == 8) {
						Main1v1.Fila1v1Rapido(p);
					}
				}
			}
		}
	}
}
