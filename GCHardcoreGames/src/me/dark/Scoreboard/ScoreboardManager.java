package me.dark.Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Preconditions;

/**
 * Creates and defines a Scoreboard on the player
 * 
 * @author MrLuangamer
 */

public class ScoreboardManager {
	
	private Scoreboard board;
	private Objective objective;

	public ScoreboardManager(Player player, String displayName) {
		this.board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		this.objective = board.registerNewObjective(player.getName(), "dummy");
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.objective.setDisplayName(displayName);
		player.setScoreboard(this.board);
	}
	
	public void setDisplayName(String newDisplayName) {
		objective.setDisplayName(newDisplayName);
	}
	
	public void setText(String prefix, String suffix, int score) {
		Preconditions.checkArgument(score <= 15);				
		Preconditions.checkArgument(prefix.length() + suffix.length() <= 32, "Text cannot be over 32 characters in length.");
		String name = getTeamName(score);
		String player = getScoreName(score);
		if (board.getTeam(name) == null) {
			objective.getScore(player).setScore(score);			
			Team team = board.registerNewTeam(name);
			if (!team.hasEntry(player)) {
				team.addEntry(player);
				team.setPrefix(prefix);
				team.setSuffix(suffix);
			}
		} else {
			objective.getScore(player).setScore(score);						
			Team team = board.getTeam(name);
			team.setPrefix(prefix);
			team.setSuffix(suffix);
		}
	}

	public void removeText(int score) {
		String name = getTeamName(score);
		String player = getScoreName(score);
		board.resetScores(player);
		if (board.getTeam(name) != null) {
			board.getTeam(name).unregister();
		}
	}
	
	public boolean hasText(int score) {
		return board.getTeam(getTeamName(score)) != null;
	}
	
	public void reset() {
		for (int i = 0; i <= 15; i++) {
			removeText(i);
		}
	}
	
	public void resetTeams() {
		for (Team team : board.getTeams()) {
			team.unregister();
		}
	}
	
	public void resetObjectives() {
		for (Objective objective : board.getObjectives()) {
			objective.unregister();
		}
	}

	public Scoreboard getScoreboard() {
		return board;
	}
	
	public Objective getObjective() {
		return objective;
	}
	
	private String getTeamName(int value) {
		return String.format("[Team-%s]", value);
	}
	
	private String getScoreName(int value) {
		ChatColor color = ChatColor.values()[value];
		return String.format("%s" + color.getChar() + ChatColor.RESET, ChatColor.COLOR_CHAR);
	}

}
