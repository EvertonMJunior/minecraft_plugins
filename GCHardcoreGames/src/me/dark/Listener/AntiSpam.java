package me.dark.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.dark.Main;

public class AntiSpam implements Listener {

	public static HashMap<UUID, String> spam = new HashMap<>();
	public static HashMap<UUID, Integer> spamint = new HashMap<>();
	public static HashMap<UUID, Integer> chances = new HashMap<>();
	public static HashMap<UUID, Long> jcool = new HashMap<>();
	public static ArrayList<UUID> mute = new ArrayList<>();
	int cool = 60;

	@EventHandler
	public void onPlayerSpam(AsyncPlayerChatEvent e) {
		final Player p = e.getPlayer();
		String s = e.getMessage();
		if (!p.hasPermission(Main.perm_mod)) {
			if (!spam.containsKey(p.getUniqueId())) {
				spam.put(p.getUniqueId(), s);
				return;
			}
			if (chances.containsKey(p.getUniqueId())
					&& chances.get(p.getUniqueId()).equals(1)) {
				e.setCancelled(true);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
						"tempban " + p.getName() + " 30 minutes Spam");
				mute.remove(p.getUniqueId());
				jcool.remove(p.getUniqueId());
				chances.remove(p.getUniqueId());
				spamint.remove(p.getUniqueId());
				return;
			}
			if ((chances.containsKey(p.getUniqueId()))
					&& (chances.get(p.getUniqueId()) >= 0)) {
				e.setCancelled(true);
				chances.put(p.getUniqueId(),
						Integer.valueOf(chances.get(p.getUniqueId()) - 1));
				long seconds = jcool.get(p.getUniqueId()).longValue() / 1000L
						+ cool - System.currentTimeMillis() / 1000L;
				p.sendMessage(ChatColor.RED + "VOCE AINDA ESTA SILENCIADO POR "
						+ seconds + " SEGUNDOS");
				p.sendMessage(ChatColor.RED
						+ "SE VOCE CONTINUAR USANDO O CHAT ENQUANTO SILENCIADO, VOCE SERA KICKADO!");
				p.sendMessage(ChatColor.RED + "VOCE TEM "
						+ chances.get(p.getUniqueId()) + " CHANCES FALTANTES!");
				return;
			}
			if (spam.get(p.getUniqueId()).equalsIgnoreCase(s)) {
				if ((spamint.containsKey(p.getUniqueId()))
						&& (spamint.get(p.getUniqueId()).equals(2))) {
					e.setCancelled(true);
					spamint.put(p.getUniqueId(),
							Integer.valueOf(spamint.get(p.getUniqueId()) + 1));
					jcool.put(p.getUniqueId(),
							Long.valueOf(System.currentTimeMillis()));
					mute.add(p.getUniqueId());
					chances.put(p.getUniqueId(), Integer.valueOf(10));
					p.sendMessage(ChatColor.RED
							+ "VOCE FOI SILENCIADO POR SPAM POR 60 SEGUNDOS");
					p.sendMessage(ChatColor.RED
							+ "SE VOCE CONTINUAR USANDO O CHAT ENQUANTO SILENCIADO, VOCE SERA KICKADO!");
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(),
							new Runnable() {
								public void run() {
									AntiSpam.mute.remove(p.getUniqueId());
									AntiSpam.jcool.remove(p.getUniqueId());
									AntiSpam.chances.remove(p.getUniqueId());
									AntiSpam.spamint.remove(p.getUniqueId());
								}
							}, 1200L);
					return;
				}
				if (spamint.containsKey(p.getUniqueId())) {
					spamint.put(p.getUniqueId(),
							Integer.valueOf(spamint.get(p.getUniqueId()) + 1));
					return;
				}
				spamint.put(p.getUniqueId(), Integer.valueOf(1));
				return;
			}
			spam.put(p.getUniqueId(), s);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMute(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (mute.contains(p.getUniqueId())) {
			e.setCancelled(true);
			return;
		}
	}
}