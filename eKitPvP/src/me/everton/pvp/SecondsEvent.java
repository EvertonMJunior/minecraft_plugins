package me.everton.pvp;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SecondsEvent extends Event {
	public static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
