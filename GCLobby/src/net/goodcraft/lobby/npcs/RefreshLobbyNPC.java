package net.goodcraft.lobby.npcs;

import net.goodcraft.GameMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;

import java.util.HashMap;
import java.util.UUID;

public class RefreshLobbyNPC implements Runnable {

    public static HashMap<UUID, GameMode> holograms = new HashMap<>();

    @Override
    public void run() {
        for (GameMode gm : GameMode.values()) {
            String path = gm.name() + "-LOBBY";
            if (!NPCAPI.cfg.contains(path)) {
                continue;
            }
            holograms.put(UUID.fromString(NPCAPI.cfg.getString(path)), gm);
        }

        for (ArmorStand as : Bukkit.getWorld("Mundo").getEntitiesByClass(ArmorStand.class)) {
            if (!holograms.containsKey(as.getUniqueId())) {
                continue;
            }
            GameMode gm = holograms.get(as.getUniqueId());
            if (!gm.isActive()) {
                as.setCustomName("§eEM BREVE!");
                continue;
            }

            int online = 0;

            as.setCustomName("§e" + online + " jogadores");
        }
    }
}
