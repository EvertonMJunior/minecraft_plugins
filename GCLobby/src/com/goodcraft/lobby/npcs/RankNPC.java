package com.goodcraft.lobby.npcs;

import com.goodcraft.GameMode;
import com.goodcraft.Main;
import com.goodcraft.api.Hologram;
import com.goodcraft.api.NameFetcher;
import com.goodcraft.sql.MySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.entity.EntityHumanNPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class RankNPC {

    public static HashMap<UUID, RankNPC> map = new HashMap<>();
    
    public static void getAndSave(){
        for(GameMode gm : GameMode.values()){
            for(int i = 1; i < 4; i++){
                String path = gm.name() + "-" + i;
                if(!NPCAPI.cfg.contains(path)){
                    continue;
                }
                UUID id = UUID.fromString(NPCAPI.cfg.getString(path));
                new RankNPC(i, gm, getNPCLocation(id)).spawn();
            }
        }
    }
    
    public static Location getNPCLocation(UUID id){
        for(Player en : Bukkit.getWorld("Mundo").getEntitiesByClass(Player.class)){
            if(!en.hasMetadata("NPC")){
                continue;
            }
            if(!en.getUniqueId().equals(id)){
                continue;
            }
            return en.getLocation();
        }
        return null;
    }

    public static RankNPC getByUniqueID(UUID id) {
        if (map.containsKey(id)) {
            return map.get(id);
        }
        return null;
    }

    private final UUID ID;
    private final int TOP;
    private final GameMode GM;
    private final Location LOC;
    private EntityHumanNPC.PlayerNPC npc;
    private final ArrayList<UUID> HOLOGRAMS = new ArrayList<>();

    public RankNPC(int top, GameMode gm, Location loc) {
        ID = UUID.randomUUID();
        this.TOP = top;
        this.GM = gm;
        this.LOC = loc;
        map.put(ID, this);
        
        String path = gm.name() + "-" + TOP;
        NPCAPI.cfg.set(path, ID.toString());
    }

    public void spawn() {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();

        NPC npc = registry.createNPC(EntityType.PLAYER, "§" + TOP);
        npc.spawn(LOC);

        CitizensNPC cnpc = (CitizensNPC) npc;
        this.npc = (EntityHumanNPC.PlayerNPC) cnpc.getEntity();
        this.refresh();
    }

    public void refresh() {
        if (!MySQL.ativo) {
            return;
        }
        try {
            ArrayList<String> types = new ArrayList<>();
            types.addAll(Arrays.asList(GM.getStatus()));

            String orderBy = (types.contains("Wins") ? "wins DESC" : "")
                    + (types.contains("Kills") ? ", kills DESC" : "")
                    + (types.contains("Deaths") ? ", deaths ASC" : "")
                    + (types.contains("Acertos") ? ", acertos DESC" : "");

            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT * FROM good_" + GM.name().toLowerCase()
                            + " ORDER BY " + orderBy + " LIMIT " + TOP + ", " + TOP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nick = null;
                try {
                    nick = NameFetcher.getNameOf(UUID.fromString(rs.getString("uuid")));
                } catch (Exception ex) {
                }
                npc.setSkinName(nick);

                for (ArmorStand as : Bukkit.getWorld("Mundo").getEntitiesByClass(ArmorStand.class)) {
                    if (!HOLOGRAMS.contains(as.getUniqueId())) {
                        continue;
                    }
                    as.remove();
                    HOLOGRAMS.remove(as.getUniqueId());
                }
                Location l = LOC.clone();
                
                for (int i = types.size(); i > 0; i--) {
                    HOLOGRAMS.add(Hologram.hologram("§e" + types.get(i) + ":§f "
                            + rs.getInt(types.get(i).toLowerCase()), l.add(0, 0.25, 0)).getUniqueId());
                }
                HOLOGRAMS.add(Hologram.hologram("§6" + nick, l.add(0, 0.35, 0))
                        .getUniqueId());
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
