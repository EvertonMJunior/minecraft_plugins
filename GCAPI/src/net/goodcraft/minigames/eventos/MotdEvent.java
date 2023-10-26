package net.goodcraft.minigames.eventos;

import net.goodcraft.Main;
import net.goodcraft.api.SecondsEvent;
import net.goodcraft.minigames.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MotdEvent implements Listener {
    @EventHandler
    public void onSecond(SecondsEvent e){
        Minigame mg = Main.minigame;
        ((CraftServer)Bukkit.getServer()).getServer().setMotd((mg.getGameState() == null ? "REINICIANDO" : mg.getGameState()) +
                ":" + mg.getCurrentPlayers() + ":" + mg.getMaximumPlayers() + ":" + mg.getPlugin().getConfig().getString("MAPA"));
    }
}
