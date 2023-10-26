package net.goodcraft.lobby.npcs;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.entity.EntityHumanNPC;
import net.goodcraft.GameMode;
import net.goodcraft.Main;
import net.goodcraft.api.Hologram;
import net.goodcraft.minigames.Stat;
import net.goodcraft.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RankNPC {

    public static HashMap<UUID, RankNPC> map = new HashMap<>();
    private final UUID ID;
    private final int TOP;
    private final GameMode GM;
    private final Location LOC;
    private final HashMap<String, UUID> HOLOGRAMS = new HashMap<>();
    private EntityHumanNPC.PlayerNPC npc;
    private String nick;
    private boolean firstRefresh;
    public RankNPC(int top, GameMode gm, Location loc) {
        ID = UUID.randomUUID();
        this.TOP = top;
        this.GM = gm;
        this.LOC = loc;
        this.firstRefresh = true;

        map.put(ID, this);

        String path = gm.name() + "-" + TOP + "-NPC";
        NPCAPI.cfg.set(path, ID.toString());
        try {
            NPCAPI.cfg.save(NPCAPI.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getAndSave() {
        for (GameMode gm : GameMode.values()) {
            for (int i = 1; i < 4; i++) {
                String path = gm.name() + "-" + i;
                if (!NPCAPI.cfg.contains(path)) {
                    continue;
                }
                UUID id = UUID.fromString(NPCAPI.cfg.getString(path));
                new RankNPC(i, gm, getNPCLocation(id)).spawn();
            }
        }
    }

    public static Location getNPCLocation(UUID id) {
        for (Player en : Bukkit.getWorld("Mundo").getEntitiesByClass(Player.class)) {
            if (!en.hasMetadata("NPC")) {
                continue;
            }
            if (!en.getUniqueId().equals(id)) {
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

    public void spawn() {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();

        NPC npc = registry.createNPC(EntityType.PLAYER, "§" + TOP);
        npc.spawn(LOC);

        CitizensNPC cnpc = (CitizensNPC) npc;
        this.npc = (EntityHumanNPC.PlayerNPC) cnpc.getEntity();
        this.npc.setSkinName("Miner");
        this.refresh();
    }

    public void refresh() {
        if (!MySQL.ativo) {
            return;
        }
        try {
            ArrayList<String> types = new ArrayList<>();
            for(Stat s : GM.getStatus()){
                types.add(s.getName());
            }

            String orderBy = (types.contains("Wins") ? "wins DESC" : "")
                    + (types.contains("Kills") ? ", kills DESC" : "")
                    + (types.contains("Deaths") ? ", deaths ASC" : "")
                    + (types.contains("Acertos") ? ", acertos DESC" : "");

            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT * FROM good_" + GM.name().toLowerCase()
                            + " ORDER BY " + orderBy + " LIMIT " + TOP + ", " + TOP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.nick = Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("uuid"))).getName();
                Location l = LOC.clone().subtract(0, 0.25, 0);

                if (this.firstRefresh) {
                    npc.setSkinName(nick);

                    for (int i = types.size() - 1; i >= 0; i--) {
                        HOLOGRAMS.put(types.get(i), Hologram.hologram("§e" + types.get(i) + ":§f "
                                        + rs.getInt(types.get(i).toLowerCase()),
                                l.add(0, 0.25, 0)).getUniqueId());
                    }
                    HOLOGRAMS.put("Nick", Hologram.hologram("§6" + nick, l.add(0, 0.35, 0))
                            .getUniqueId());

                    firstRefresh = false;
                    break;
                }

                loop1:
                for (ArmorStand h : Bukkit.getWorld("Mundo").getEntitiesByClass(ArmorStand.class)) {
                    if (!HOLOGRAMS.containsValue(h.getUniqueId())) {
                        continue loop1;
                    }
                    String tipo = null;
                    for (String tipo1 : HOLOGRAMS.keySet()) {
                        if (!h.getUniqueId().equals(HOLOGRAMS.get(tipo1))) {
                            continue;
                        }
                        tipo = tipo1;
                    }
                    if (tipo == null) continue loop1;

                    if (tipo.equalsIgnoreCase("Nick")) {
                        if (!ChatColor.stripColor(h.getCustomName()).equalsIgnoreCase(nick)) {
                            h.setCustomName("§6" + nick);
                        }
                        return;
                    }

                    if (!h.isCustomNameVisible()) h.setCustomNameVisible(true);
                    h.setCustomName("§e" + tipo + ":§f " + rs.getInt(tipo.toLowerCase()));
                }
//
//                for (ArmorStand as : Bukkit.getWorld("Mundo").getEntitiesByClass(ArmorStand.class)) {
//                    if (!HOLOGRAMS.contains(as.getUniqueId())) {
//                        continue;
//                    }
//                    if (as.getCustomName().startsWith("§6")) {
//                        if (!ChatColor.stripColor(as.getCustomName()).equalsIgnoreCase(nick)) {
//                            if (!alreadySet.contains("Nick")) {
//                                as.setCustomName("§6" + nick);
//                                alreadySet.add("Nick");
//                            }
//                        }
//
//                        continue;
//                    }
//                    if (as.getCustomName().startsWith("§e")) {
//                        loop1:
//                        for (int i = types.size() - 1; i >= 0; i--) {
//                            if (!ChatColor.stripColor(as.getCustomName()).startsWith(types.get(i))) {
//                                continue loop1;
//                            }
//                            int stat = 0;
//                            try {
//                                stat = Integer.valueOf(ChatColor.stripColor(as.getCustomName()).replace(types.get(i) + ": ", ""));
//                            } catch (Exception e) {
//                                continue loop1;
//                            }
//                            int sqlStat = rs.getInt(types.get(i).toLowerCase());
//
//                            if (stat != sqlStat) {
//                                if (!alreadySet.contains(types.get(i))) {
//                                    as.setCustomName("§e" + types.get(i) + ":§f " + sqlStat);
//                                    alreadySet.add(types.get(i));
//                                }
//                            }
//                        }
//                    }
//                }
//
//                for (int i = types.size() - 1; i >= 0; i--) {
//                    if (!alreadySet.contains(types.get(i))) {
//                        HOLOGRAMS.add(Hologram.hologram("§e" + types.get(i) + ":§f "
//                                + rs.getInt(types.get(i).toLowerCase()), l.add(0, 0.25, 0)).getUniqueId());
//                    }
//                }
//
//                if (!alreadySet.contains("Nick")) {
//                    HOLOGRAMS.add(Hologram.hologram("§6" + nick, l.add(0, 0.35, 0))
//                            .getUniqueId());
//                }


                if (!npc.getSkinName().equalsIgnoreCase(nick)) {
                    npc.setSkinName(nick);
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
