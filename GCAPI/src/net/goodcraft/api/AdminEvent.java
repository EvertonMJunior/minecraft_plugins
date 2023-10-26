package net.goodcraft.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AdminEvent extends Event {
    public static final HandlerList handlers = new HandlerList();

    private final AdminAPI admin;
    private final boolean entering;

    public AdminEvent(AdminAPI admin, boolean entering) {
        this.admin = admin;
        this.entering = entering;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public AdminAPI getAdmin() {
        return admin;
    }

    public boolean isEntering() {
        return entering;
    }

    public Player getPlayer() {
        return admin.getPlayer();
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
