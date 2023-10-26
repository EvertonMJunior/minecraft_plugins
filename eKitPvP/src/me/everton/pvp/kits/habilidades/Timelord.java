package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Timelord implements Listener {
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static ArrayList<String> congelados = new ArrayList<>();

	@EventHandler
	public void onIE(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.TIMELORD) {
			return;
		}

		if (!KitManager.isWithKitItemInHand(p)) {
			return;
		}

		if (!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}

		if (cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
					+ cd.get(p.getName()) + " segundos§7!");
			return;
		}
		int jogadores = 0;
		for (Entity ent : p.getNearbyEntities(5.0D, 5.0D, 5.0D)) {
			if (ent instanceof Player) {
				final Player en = (Player) ent;
				if (API.getGamers().contains(en)) {
					jogadores++;
					if (congelados.contains(en.getName())) {
						congelados.remove(en.getName());
					}
					congelados.add(en.getName());
					jogadores++;
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {

								@Override
								public void run() {
									if (congelados.contains(en.getName())) {
										congelados.remove(en.getName());
									}
								}
							}, 5 * 20L);
				}
			}
		}
		if (jogadores > 0) {
			API.startCd(p, 30, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
		} else {
			p.sendMessage("§cNão há nenhum jogador próximo de você!");
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (congelados.contains(p.getName())) {
			e.setTo(p.getLocation());
			p.sendMessage("§7[§c!§7] Você foi congelado por um §b§lTimelord§7!");
		}
	}
}
