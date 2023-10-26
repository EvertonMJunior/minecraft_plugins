package com.goodcraft.bungee.api;

import com.google.common.base.Preconditions;
import dev.wolveringer.BungeeUtil.Player;
import dev.wolveringer.BungeeUtil.packets.PacketPlayOutScoreboardDisplayObjective;
import dev.wolveringer.BungeeUtil.packets.PacketPlayOutScoreboardObjective;
import dev.wolveringer.api.scoreboard.Objektive;
import dev.wolveringer.api.scoreboard.Scoreboard;
import dev.wolveringer.api.scoreboard.Team;
import net.md_5.bungee.api.ChatColor;

public class ScoreboardManager {

    private final Scoreboard BOARD;
    private final Objektive OBJECTIVE;

    public ScoreboardManager(Player player, String displayName) {
        this.BOARD = player.getScoreboard();
        this.OBJECTIVE = BOARD.createObjektive(player.getName(), PacketPlayOutScoreboardObjective.Type.INTEGER);
        this.OBJECTIVE.display(PacketPlayOutScoreboardDisplayObjective.Position.SIDEBAR);
        this.OBJECTIVE.setDisplayName(displayName);
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
            OBJECTIVE.setScore(player, score);
            Team team = BOARD.createTeam(name);
            if (!team.getMember().contains(player)) {
                team.addMember(player);
                team.setPrefix(prefix);
                team.setSuffix(suffix);
            }
        } else {
            OBJECTIVE.setScore(player, score);
            Team team = BOARD.getTeam(name);
            team.setPrefix(prefix);
            team.setSuffix(suffix);
        }
    }

    public void removeText(int score) {
        String name = getTeamName(score);
        String player = getScoreName(score);
        if (BOARD.getTeam(name) != null) {
            BOARD.removeTeam(name);
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

    @Deprecated
    public void resetTeams() {
//        for (Team team : BOARD.g) {
//            team.unregister();
//        }
    }

    @Deprecated
    public void resetObjectives() {
//        for (Objektive objective : BOARD.) {
//            BOARD.removeObjektive(objective.getName());
//        }
    }

    public Scoreboard getScoreboard() {
        return BOARD;
    }

    public Objektive getOBJECTIVE() {
        return OBJECTIVE;
    }

    private String getTeamName(int value) {
        return String.format("[Team-%s]", value);
    }

    private String getScoreName(int value) {
        ChatColor color = ChatColor.values()[value];
        return String.format("%s" + color + ChatColor.RESET, ChatColor.COLOR_CHAR);
    }

}
