package net.goodcraft.lobby.npcs;

import net.goodcraft.GameMode;

import java.util.UUID;

public class RefreshRankNPC implements Runnable {

    @Override
    public void run() {
        for (GameMode gm : GameMode.values()) {
            for (int i = 1; i < 4; i++) {
                String path = gm.name() + "-" + i + "-NPC";
                if (!NPCAPI.cfg.contains(path)) {
                    continue;
                }
                UUID id = UUID.fromString(NPCAPI.cfg.getString(path));
                if (RankNPC.getByUniqueID(id) != null) {
                    RankNPC.getByUniqueID(id).refresh();
                    continue;
                }
            }
        }
    }
}
