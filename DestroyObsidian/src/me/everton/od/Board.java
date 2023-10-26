package me.everton.od;

import me.everton.od.listeners.Obsidians;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class Board {
	@SuppressWarnings("deprecation")
	public static void refresh(Player p) {
		if(Main.sb == null) {
			Main.sb = Bukkit.getScoreboardManager().getNewScoreboard();
		}
		if(Main.sb.getObjective("sb") != null) {
			Main.sb.getObjective("sb").unregister();
		}
		
		if(!p.getScoreboard().equals(Main.sb)) {
			p.setScoreboard(Main.sb);
		}
		Objective Obj = Main.sb.registerNewObjective("sb", "dummy");
		Obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		Obj.setDisplayName("§b§lObsidian §6§lDestroyer");
		
		Score s1 = Obj.getScore(Bukkit.getOfflinePlayer("§7> §4§lVermelho"));
		s1.setScore(8);
		
		Score s2 = Obj.getScore(Bukkit.getOfflinePlayer("§6Obsidians: §c" + Obsidians.redObsidians));
		s2.setScore(7);
		
		Score linha1 = Obj.getScore(Bukkit.getOfflinePlayer("§1"));
		linha1.setScore(6);
		
		Score s3 = Obj.getScore(Bukkit.getOfflinePlayer("§7> §4§lAzul"));
		s3.setScore(5);
		
		Score s4 = Obj.getScore(Bukkit.getOfflinePlayer("§6Obsidians: §9" + Obsidians.blueObsidians));
		s4.setScore(4);
		
		Score linha2 = Obj.getScore(Bukkit.getOfflinePlayer("§6"));
		linha2.setScore(3);
		
		Score s5 = Obj.getScore(Bukkit.getOfflinePlayer("§7> §4§lPlayers"));
		s5.setScore(2);
		
		Score s6 = Obj.getScore(Bukkit.getOfflinePlayer("§6" + Bukkit.getOnlinePlayers().length));
		s6.setScore(1);
	}
	
	public static void start() {
		Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				for(Player on : Bukkit.getOnlinePlayers()) {
					refresh(on);
				}
			}
		}, 0L, 20L);
	}
}
