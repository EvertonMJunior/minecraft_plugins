package com.goodcraft.lobby.npcs;

import com.goodcraft.GameMode;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;

public class RefreshLobbyNPC implements Runnable {

    public static ArrayList<UUID> holograms = new ArrayList<>();

    @Override
    public void run() {

        for (GameMode gm : GameMode.values()) {
            String path = gm.name() + "-LOBBY";
            if (!NPCAPI.cfg.contains(path)) {
                continue;
            }
            holograms.add(UUID.fromString(NPCAPI.cfg.getString(path)));
        }

        for (ArmorStand as : Bukkit.getWorld("Mundo").getEntitiesByClass(ArmorStand.class)) {
            if (!holograms.contains(as.getUniqueId())) {
                continue;
            }
            int online = Integer.valueOf(ChatColor.stripColor(
                    as.getCustomName().replace(" jogadores", "")));

            as.setCustomName("§e" + (online < 850 ? online + 10 : 0) + " jogadores");
        }
    }
}
