package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;
import java.util.Random;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class Icicles implements Listener {
	public static ArrayList<String> congelados = new ArrayList<>();

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getDamager();
		final Player en = (Player) e.getEntity();

		if (KitManager.getKit(p) != KitType.ICICLES) {
			return;
		}

		if(!API.getGamers().contains(p)) {
			return;
		}
		
		if(!API.getGamers().contains(en)) {
			return;
		}

		if(new Random().nextInt(100) < 10) {
			if (congelados.contains(en.getName())) {
				return;
			}
			congelados.add(en.getName());
			en.sendMessage("§7[§c!§7] Você foi congelado por um Icicles! Aguarde para trocar de slot!");
			en.playSound(en.getLocation(), Sound.NOTE_PLING, 1F, 1F);
			p.sendMessage("§7[§c!§7] Você congelou " + ChatColor.stripColor(en.getPlayerListName()) + "!");
			Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					congelados.remove(en.getName());
					en.sendMessage("§7[§c!§7] Você já pode trocar de slot agora!");
				}
			}, 2 * 20L);
		}
	}
	
	@EventHandler
	public void freezeHot(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		if(congelados.contains(p.getName())) {
			p.sendMessage("§7[§c!§7] Você foi congelado por um Icicles! Aguarde para trocar de slot!");
			e.setCancelled(true);
		}
	}
}
