package net.goodcraft.skywars.eventos;

import net.goodcraft.skywars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StartEvent implements Listener {
    @EventHandler
    public void onStart(net.goodcraft.minigames.eventos.StartEvent e){
        Main.editSession.undo(Main.editSession);

        FileConfiguration c = Main.getPlugin().getConfig();
        Main.getPlugin().reloadConfig();
        int quantity = e.getMiniGame().getMaximumPlayers();
        String prefix = "SPAWN_LOCATIONS.";
        for(int i = 0; i < quantity; i++){
            if(e.getMiniGame().getCurrentPlayers() - 1 < i) break;
            Player p = Bukkit.getPlayer(e.getMiniGame().getPlayers().get(i));
            String w = c.getString(prefix + i + ".w");

            World world = Bukkit.getWorld(w);
            if(world == null) break;
            double x = c.getDouble(prefix + i + ".x");
            double y = c.getDouble(prefix + i + ".y");
            double z = c.getDouble(prefix + i + ".z");
            float yaw = (float) c.getDouble(prefix + i + ".yaw");
            float pitch = (float) c.getDouble(prefix + i + ".pitch");
            Location l = new Location(world, x, y, z, yaw, pitch);
            p.teleport(l);
        }
    }
}
