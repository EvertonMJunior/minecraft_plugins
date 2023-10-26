package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.SecondsEvent;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Madman implements Listener {
	public static HashMap<String, Integer> afetados = new HashMap<>();
	public static HashMap<String, String> afetadosAutor = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void madman(SecondsEvent e) {
		for (Player on : Bukkit.getOnlinePlayers()) {
			if (KitManager.getKit(on) == KitType.MADMAN && API.getGamers().contains(on)) {
				ArrayList<String> pP = new ArrayList<>();
				for (Entity ent : on.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
					if (ent instanceof Player) {
						Player near = (Player) ent;
						if (API.getGamers().contains(near)) {
							pP.add(near.getName());
						}
					}
				}

				if (pP.size() > 0) {
					for (String pN : pP) {
						Player p = Bukkit.getPlayerExact(pN);
						if (p != null) {
							if (afetados.containsKey(p.getName())) {
								afetados.put(p.getName(),
										afetados.get(p.getName()) + 3);
							} else {
								afetados.put(p.getName(), 3);
							}
							afetadosAutor.put(p.getName(), on.getName());
						}
					}
				}
			}

			if (afetados.containsKey(on.getName())) {
				Player au = Bukkit.getPlayerExact(afetadosAutor.get(on
						.getName()));
				int value = afetados.get(on.getName()) - 3;

				if (afetados.get(on.getName()) <= 0) {
					afetados.remove(on.getName());
					afetadosAutor.remove(on.getName());
					return;
				}
				
				if(!API.getGamers().contains(on)) {
					afetados.remove(on.getName());
					afetadosAutor.remove(on.getName());
					return;
				}

				if (au != null) {
					if (!au.getNearbyEntities(10.0D, 10.0D, 10.0D).contains(
							(Entity) on)) {
						afetados.put(on.getName(), value);
					}
					if(!API.getGamers().contains(au)) {
						afetados.put(on.getName(), value);
					}
					if(KitManager.getKit(au) != KitType.MADMAN) {
						afetados.put(on.getName(), value);
					}
				} else if (au == null) {
					afetados.put(on.getName(), value);
				}

				API.sendActionBar(on,
						"§6§lEfeito do Madman: " + afetados.get(on.getName())
								+ "%");
			}
		}
	}
}
