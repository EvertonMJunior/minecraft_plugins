package me.dark.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.dark.Main;
import me.dark.Cmd.Admin;
import me.dark.Cmd.Specs;

public class Vanish {
	public static void update(Player p) {
		for (Player all : Admin.admin) {
			if (p.hasPermission(Main.perm_mod)) {
				if (!Specs.specsOn.contains(p)) {
					p.hidePlayer(all);
				} else {
					p.showPlayer(all);
				}
			} else {
				p.hidePlayer(all);
			}
		}
	}
	@SuppressWarnings("deprecation")
	public static void hideSpec(Player p) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (!all.hasPermission(Main.perm_mod)) {
				all.hidePlayer(p);
			} else {
				if (!Specs.specsOn.contains(all)) {
					all.hidePlayer(p);
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	public static void showAll(Player p) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.showPlayer(p);
		}
	}

}
