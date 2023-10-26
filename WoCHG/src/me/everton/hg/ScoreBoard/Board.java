package me.everton.hg.ScoreBoard;

import me.everton.hg.API;
import me.everton.hg.API.GameStage;
import me.everton.hg.Main;
import me.everton.hg.Cmds.Admin;
import me.everton.hg.kits.KitManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Board {

	
	@SuppressWarnings("deprecation")
	public static void refreshScore(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective ob = sb.registerNewObjective("scores", "dummy");
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		ob.setDisplayName("§6§lWoC§b§lHG");
		ob.getScore("§r§m------------").setScore(13);
		ob.getScore("§a§lKit:").setScore(12);
		ob.getScore("» §2" + KitManager.getKit(p).getName()).setScore(11);
		ob.getScore("§r").setScore(10);
		ob.getScore("§b§lPlayers:").setScore(9);
		ob.getScore("» §3" + (Bukkit.getOnlinePlayers().length - Admin.admins.size())).setScore(8);
		ob.getScore("§r§a").setScore(7);
		if(API.getGameStage() == GameStage.PREJOGO) {
			ob.getScore("§d§lComeça em:").setScore(6);
			ob.getScore("» §5" + Main.secToMin(Main.TEMPO_PREJOGO)).setScore(5);
		} else if(API.getGameStage() == GameStage.INVENCIBILIDADE) {
			ob.getScore("§d§lAcaba em:").setScore(6);
			ob.getScore("» §5" + Main.secToMin(Main.TEMPO_INVENCIBILIDADE)).setScore(5);
		} else {
			ob.getScore("§d§lTempo:").setScore(6);
			ob.getScore("» §5" + Main.secToMin(Main.TEMPO_JOGO)).setScore(5);
		}
		if(API.getGameStage() == GameStage.JOGO) {
			ob.getScore("§r§a§9").setScore(4);
			ob.getScore("§6§lKills:").setScore(3);
			if(Main.kills.containsKey(p.getName())) {
				ob.getScore("» §e" + Main.kills.get(p.getName())).setScore(2);
			} else {
				ob.getScore("» §e0").setScore(2);
			}
		}
		ob.getScore("§m------------").setScore(1);
		
		p.setScoreboard(sb);
	}
}
