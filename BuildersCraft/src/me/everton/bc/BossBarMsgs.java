package me.everton.bc;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BossBarMsgs {
	private static int msg = 1;
	public static String[] msgs;
	
	public static void start() {
		Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				for(Player on : Bukkit.getOnlinePlayers()) {
					switch (msg) {
					case 1:
						msg = 2;
						BarAPI.se
						break;
					case 2:
						msg = 3;
						break;
					case 3:
						msg = 4;
						break;
					case 4:
						msg = 1;
						break;

					default:
						break;
					}
				}
			}
		}, 0L, 15 * 20L);
	}
}
