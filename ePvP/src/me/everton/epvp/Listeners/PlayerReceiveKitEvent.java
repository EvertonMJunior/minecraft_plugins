package me.everton.epvp.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerReceiveKitEvent extends Event{
	private Player p;
	private String kit;
	 
    public PlayerReceiveKitEvent(Player pl, String kit) {
        this.p = pl;
        this.kit = kit;
    }
    
    public Player getPlayer() {
    	return p;
    }
    
    public String getKit() {
    	return kit;
    }

    private static final HandlerList handlers = new HandlerList();
    
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
