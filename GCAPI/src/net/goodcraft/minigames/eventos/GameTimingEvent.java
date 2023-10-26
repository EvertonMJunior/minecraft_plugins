package net.goodcraft.minigames.eventos;

import net.goodcraft.minigames.Minigame;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameTimingEvent extends Event {
    public static final HandlerList handlers = new HandlerList();

    private final Minigame MINIGAME;

    public GameTimingEvent(Minigame minigame) {
        this.MINIGAME = minigame;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Minigame getMiniGame() {
        return MINIGAME;
    }
}