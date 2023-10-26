package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.cmds.Admin;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.player.PlayerInteractEvent;

public class Sombra implements Listener {
	public static ArrayList<String> hided = new ArrayList<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void esconder(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if ((KitManager.getKit(p) == KitType.SOMBRA)
				&& (e.getAction().name().contains("RIGHT"))) {
			if (KitManager.isWithKitItemInHand(p)) {
				e.setCancelled(true);
				e.setUseInteractedBlock(Result.DENY);
				p.updateInventory();
				
				if(!API.getGamers().contains(p)) {
					p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
					return;
				}
				
				if (hided.contains(p.getName())) {
					p.sendMessage("§7[§c!§7] Você já está invisível!");
					return;
				}
				if (cd.containsKey(p.getName())) {
					p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
							+ cd.get(p.getName()) + " segundos§7!");
					return;
				}
				for (Player on : Bukkit.getOnlinePlayers()) {
					if (!Admin.admins.contains(on.getName())) {
						on.hidePlayer(p);
					}
				}
				hided.add(p.getName());
				API.startCd(p, 30, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
				p.sendMessage("§7[§a!§7] Você está invisível por 5 segundos!");
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);

				Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
						new Runnable() {

							@Override
							public void run() {
								if (hided.contains(p.getName())) {
									p.sendMessage("§7[§c!§7] Agora você está visível!");
									p.playSound(p.getLocation(),
											Sound.NOTE_BASS, 1F, 1F);
									hided.remove(p.getName());
									for (Player on : Bukkit.getOnlinePlayers()) {
										on.showPlayer(p);
									}
								}
							}
						}, 5 * 20L);
			}
		}
	}
}
