package me.everton.epvp.Kits.Habilidades;

import me.everton.epvp.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Legolas implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onArrow(EntityDamageByEntityEvent e) {
		if((e.getDamager() instanceof Arrow) && (e.getEntity() instanceof Player)) {
			Arrow a = (Arrow) e.getDamager();
			if(!(a.getShooter() instanceof Player)) {
				return;
			}
			if(e.isCancelled()) {
				return;
			}
			
			Player s = (Player) a.getShooter();
			Player p = (Player) e.getEntity();
			
			if(Main.legolas.contains(s.getName())) {
				if(p.getLocation().distance(s.getLocation()) >= 30D) {
					e.setDamage(80.0D);
					p.sendMessage("§7[§c!§7] Você levou um headshot de " + ChatColor.stripColor(s.getName()) + "!");
					s.sendMessage("§7[§c!§7] Você deu um headshot em " + ChatColor.stripColor(p.getName()) + "!");
				} else {
					p.sendMessage("§7[§c!§7] Você foi atingido por uma flecha de " + ChatColor.stripColor(s.getName()) + "!");
					s.sendMessage("§7[§c!§7] Você acertou uma flecha em " + ChatColor.stripColor(p.getName()) + "! Fique a 30 blocos ou mais dele e mate-o em um headshot!");
					e.setDamage(8.0D);
				}
			}
		}
	}
}
