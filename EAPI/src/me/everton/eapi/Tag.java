package me.everton.eapi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Tag {

    public static HashMap<String, String> tags = new HashMap<>();

    public static void set(Player p, String tag) {
        Scoreboard sb = new Scoreboard();
        ScoreboardTeam sbT = new ScoreboardTeam(sb, UUID.randomUUID().toString().substring(0, 15));
        sbT.setDisplayName(tag);
        sbT.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS);
        sbT.setPrefix(tag.replace("&", "§"));
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(sbT, Arrays.asList(new String[]{p.getName()}), 3);

        for (Player o : Bukkit.getOnlinePlayers()) {
            CraftPlayer cP = (CraftPlayer) o;
            cP.getHandle().playerConnection.sendPacket(packet);
        }

        if (tags.containsKey(p.getName())) {
            tags.remove(p.getName());
        }
        tags.put(p.getName(), tag);
    }

    public static void update(Player p) {
        for (Player o : Bukkit.getOnlinePlayers()) {
            if (!tags.containsKey(o.getName())) {
                continue;
            }

            set(o, tags.get(o.getName()));
        }
    }
}
