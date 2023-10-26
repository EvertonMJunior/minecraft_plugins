package com.goodcraft.lobby.npcs;

import com.goodcraft.GameMode;
import java.util.HashMap;
import java.util.UUID;
import net.citizensnpcs.api.npc.NPC;

public class RefreshRankNPC implements Runnable {

    public static HashMap<UUID, NPC> npcs = new HashMap<>();
    public static HashMap<UUID, UUID> holograms = new HashMap<>(); //NPC, HOLOGRAM
    public static String[] npcs2 = new String[]{"1", "2", "3"};

    @Override
    public void run() {
        for (GameMode gm : GameMode.values()) {
            for (int i = 1; i < 4; i++) {
                String path = gm.name() + "-" + i + "-NPC";
                if(!NPCAPI.cfg.contains(path)){
                    continue;
                }
                UUID id = UUID.fromString(NPCAPI.cfg.getString(path));
                if(RankNPC.getByUniqueID(id) != null){
                    RankNPC.getByUniqueID(id).refresh();
                    continue;
                }
            }
        }
//        for (GameMode gm : GameMode.values()) {
//            for (String rank : npcs2) {
//                String path = gm.name() + "-" + rank + "-NPC";
//                if (!NPCAPI.cfg.contains(path)) {
//                    continue;
//                }
//                UUID id = UUID.fromString(NPCAPI.cfg.getString(path));
//                npcs.put(id, CitizensAPI.getNPCRegistry().getByUniqueIdGlobal(id));
//                
//                ArrayList<String> types = new ArrayList<>();
//                types.addAll(Arrays.asList(gm.getStatus()));
//                types.add("NICK");
//
//                
//                for (String status : types) {
//                    String statusPath = gm.name() + "-" + rank + "-" + status.toUpperCase();
//                    if (!NPCAPI.cfg.contains(statusPath)) {
//                        continue;
//                    }
//                    UUID idStatus = UUID.fromString(NPCAPI.cfg.getString(statusPath));
//                    holograms.put(id, idStatus);
//                }
//            }
//        }
//        
//        
//
//        for (ArmorStand as : Bukkit.getWorld("Mundo").getEntitiesByClass(ArmorStand.class)) {
//            if (!holograms.containsKey(as.getUniqueId())) {
//                continue;
//            }
//            int online = Integer.valueOf(ChatColor.stripColor(
//                    as.getCustomName().replace(" jogadores", "")));
//
//            as.setCustomName("§e" + (online < 850 ? online + 10 : 0) + " jogadores");
//        }
    }
}
