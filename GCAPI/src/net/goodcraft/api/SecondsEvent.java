package net.goodcraft.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SecondsEvent extends Event {
    public static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
