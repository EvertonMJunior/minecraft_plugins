package me.everton.epvp.AntiCheat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Bc {
	public static void anunciar(Niveis n, Hacks h, Player p) {
		for(Player on : Bukkit.getOnlinePlayers()) {
			if(on.hasPermission("pvp.admin")) {
				on.sendMessage("§7[§a!§7] §c" + p.getName() + " está §l" + n.toString() + "§c usando §l" + h + "§c!");
			}
		}
	}
}
