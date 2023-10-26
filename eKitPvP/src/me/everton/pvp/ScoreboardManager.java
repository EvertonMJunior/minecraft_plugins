package me.everton.pvp;

import me.everton.pvp.ScoreManager.Deaths;
import me.everton.pvp.ScoreManager.Kills;
import me.everton.pvp.ScoreManager.Levels;
import me.everton.pvp.ScoreManager.Money;
import me.everton.pvp.kits.KitManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {
	public static void refreshSidebar(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective ob = sb.registerNewObjective("scores", "dummy");
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		ob.setDisplayName("§6§lTiger§f§lPvP");
		ob.getScore("§a§lKit:").setScore(14);
		ob.getScore("» §2" + KitManager.getKit(p).getName()).setScore(13);
		ob.getScore("§9").setScore(12);
		ob.getScore("§b§lKills:").setScore(11);
		ob.getScore("» §3" + Kills.getKills(p.getName())).setScore(10);
		ob.getScore("§9§r").setScore(9);
		ob.getScore("§4§lDeaths:").setScore(8);
		ob.getScore("» §c" + Deaths.getDeaths(p.getName())).setScore(7);
		ob.getScore("§9§r§f").setScore(6);
		ob.getScore("§d§lTigerCoins:").setScore(5);
		ob.getScore("» §5" + Money.getMoney(p.getName())).setScore(4);
		ob.getScore("§9§r§f§c").setScore(3);
		ob.getScore("§6§lNível:").setScore(2);
		ob.getScore("» §e" + Levels.getLevel(p.getName())).setScore(1);
		
		p.setScoreboard(sb);
	}
}
