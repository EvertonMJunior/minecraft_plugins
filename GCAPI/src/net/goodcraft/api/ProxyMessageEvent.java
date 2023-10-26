package net.goodcraft.api;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ProxyMessageEvent extends Event {
    public static final HandlerList handlers = new HandlerList();

    private final String channel;
    private final Player player;
    private final byte[] message;
    private final ByteArrayDataInput in;

    public ProxyMessageEvent(String channel, Player player, byte[] message, ByteArrayDataInput in) {
        this.channel = channel;
        this.player = player;
        this.message = message;
        this.in = in;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getChannel() {
        return channel;
    }

    public Player getPlayer() {
        return player;
    }

    public byte[] getMessage() {
        return message;
    }

    public ByteArrayDataInput getIn() {
        return in;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
