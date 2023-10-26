package net.goodcraft;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {

    private final Scoreboard BOARD;
    private final Objective OBJECTIVE;

    public ScoreboardManager(Player p, String displayName) {
        this.BOARD = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        this.OBJECTIVE = BOARD.registerNewObjective(p.getName(), "dummy");
        this.OBJECTIVE.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.OBJECTIVE.setDisplayName(displayName);
        p.setScoreboard(BOARD);
    }

    public void setDisplayName(String newDisplayName) {
        OBJECTIVE.setDisplayName(newDisplayName);
    }

    public void setText(String prefix, String suffix, int score) {
        Preconditions.checkArgument(score <= 15);
        Preconditions.checkArgument(prefix.length() + suffix.length() <= 32, "Text cannot be over 32 characters in length.");
        String name = getTeamName(score);
        String player = getScoreName(score);
        if (BOARD.getTeam(name) == null) {
            OBJECTIVE.getScore(player).setScore(score);
            Team team = BOARD.registerNewTeam(name);
            if (!team.hasEntry(player)) {
                team.addEntry(player);
                team.setPrefix(prefix);
                team.setSuffix(suffix);
            }
        } else {
            OBJECTIVE.getScore(player).setScore(score);
            Team team = BOARD.getTeam(name);
            team.setPrefix(prefix);
            team.setSuffix(suffix);
        }
    }

    public void removeText(int score) {
        String name = getTeamName(score);
        String player = getScoreName(score);
        BOARD.resetScores(player);
        if (BOARD.getTeam(name) != null) {
            BOARD.getTeam(name).unregister();
        }
    }

    public boolean hasText(int score) {
        return BOARD.getTeam(getTeamName(score)) != null;
    }

    public void reset() {
        for (int i = 0; i <= 15; i++) {
            removeText(i);
        }
    }

    public void resetTeams() {
        for (Team team : BOARD.getTeams()) {
            team.unregister();
        }
    }

    public void resetObjectives() {
        for (Objective objective : BOARD.getObjectives()) {
            objective.unregister();
        }
    }

    public Scoreboard getScoreboard() {
        return BOARD;
    }

    public Objective getOBJECTIVE() {
        return OBJECTIVE;
    }

    private String getTeamName(int value) {
        return String.format("[Team-%s]", value);
    }

    private String getScoreName(int value) {
        ChatColor color = ChatColor.values()[value];
        return String.format("%s" + color.getChar() + ChatColor.RESET, ChatColor.COLOR_CHAR);
    }

}
