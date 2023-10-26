package net.goodcraft.minigames.eventos;

import net.goodcraft.minigames.Minigame;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StartEvent extends Event implements Cancellable {
    public static final HandlerList handlers = new HandlerList();

    private final Minigame MINIGAME;
    private boolean isCancelled = false;

    public StartEvent(Minigame minigame) {
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

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}