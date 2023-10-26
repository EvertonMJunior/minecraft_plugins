package me.everton.epvp.Kits.Habilidades;

import java.util.ArrayList;

import me.everton.epvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Sombra implements Listener{
	public static ArrayList<String> hided = new ArrayList<>();
	public static ArrayList<String> cd = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void esconder(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if((Main.sombra.contains(p.getName())) && (e.getAction().name().contains("RIGHT"))) {
			if(p.getItemInHand().getType() == Material.WOOL) {
				if(p.getItemInHand().getData().getData() == 15) {
					if(hided.contains(p.getName())) {
						p.sendMessage("§7[§c!§7] Você já está invisível!");
						return;
					}
					if(cd.contains(p.getName())) {
						p.sendMessage("§7[§c!§7] Kit em cooldown!");
						return;
					}
					for(Player on : Bukkit.getOnlinePlayers()) {
						on.hidePlayer(p);
					}
					hided.add(p.getName());
					cd.add(p.getName());
					p.sendMessage("§7[§a!§7] Você está invisível por 10 segundos!");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
					
					Main.sh.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
						
						@Override
						public void run() {
							if(cd.contains(p.getName())) {
								cd.remove(p.getName());
								p.sendMessage("§7[§a!§7] O cooldown acabou!");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
							}
						}
					}, 30 * 20L);
					
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						
						@Override
						public void run() {
							if(hided.contains(p.getName())) {
								p.sendMessage("§7[§c!§7] Agora você está visível!");
								p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
								hided.remove(p.getName());
								for(Player on : Bukkit.getOnlinePlayers()) {
									on.showPlayer(p);
								}
							}
						}
					}, 10 * 20L);
				}
			}
		}
	}
}
