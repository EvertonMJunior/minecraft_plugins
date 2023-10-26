package me.everton.esw;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class PrefixManager {
	public static void setPrefix(Player p, String prefix) {
		if(p.getScoreboard() != Main.sb) {
			p.setScoreboard(Main.sb);
		}
		
		Team t = null;
		if(Main.sb.getTeam(prefix) != null) {
			t = Main.sb.getTeam(prefix);
		} else {
			t = Main.sb.registerNewTeam(prefix);
		}
		t.addPlayer(p);
		t.setPrefix(prefix);
	}
}
