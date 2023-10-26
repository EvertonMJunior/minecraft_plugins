package net.goodcraft.eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChunkUnloadEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChunkUnload(org.bukkit.event.world.ChunkUnloadEvent e) {
        e.setCancelled(true);
        if (!e.getChunk().isLoaded()) {
            e.getChunk().load();
        }
    }
}
