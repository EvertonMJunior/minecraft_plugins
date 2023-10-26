package net.goodcraft.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.Team;

public class PlayerTagEvent extends Event implements Cancellable {
    public static final HandlerList handlers = new HandlerList();

    private Tag tag;
    private boolean isCancelled = false;
    private String prefix;
    private Player beingUpdatedFor;
    private Team team;

    public PlayerTagEvent(Tag tag, Player beingUpdatedFor, String prefix, Team team) {
        this.tag = tag;
        this.beingUpdatedFor = beingUpdatedFor;
        this.prefix = prefix;
        this.team = team;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Tag getTag() {
        return tag;
    }

    public String getPrefix() {
        return tag.getRank() != Rank.NORMAL ? prefix : "§7";
    }

    public Team getTeam() {
        return team;
    }

    public Player getBeingUpdatedFor() {
        return beingUpdatedFor;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
