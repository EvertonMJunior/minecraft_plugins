package net.goodcraft.minigames.eventos;

import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AddToKitEvent extends Event implements Cancellable {
    public static final HandlerList handlers = new HandlerList();

    private final Player PLAYER;
    private final Kit KIT;
    private final Minigame MINIGAME;
    private boolean isCancelled;

    public AddToKitEvent(Player player, Kit kit, Minigame minigame) {
        this.PLAYER = player;
        this.KIT = kit;
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

    public Player getPlayer() {
        return PLAYER;
    }

    public Kit getKit() {
        return KIT;
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