package com.goodcraft.bungee.api;

import com.goodcraft.bungee.Main;
import dev.wolveringer.BungeeUtil.Player;
import dev.wolveringer.BungeeUtil.packets.PacketPlayOutScoreboardTeam;
import dev.wolveringer.api.scoreboard.Scoreboard;
import dev.wolveringer.api.scoreboard.Team;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Tag {

    public static Map<UUID, Tag> tags = new HashMap<>();

    public static Tag getByUUID(UUID id) {
        return tags.get(id);
    }

    public static void updateFor(Player p) {
        for (UUID id : tags.keySet()) {
            Player t = (Player) Main.getPlugin().getProxy().getPlayer(id);
            if (t == null) {
                continue;
            }
            if(!t.getServer().getInfo().getName().equalsIgnoreCase(p.getServer().getInfo().getName())){
                continue;
            }
            Tag obj = tags.get(id);
            obj.update(p);
        }
    }

    private Player p;
    private Rank rank;

    public Tag(Player p, Rank rank) {
        this.p = p;
        this.rank = rank;
        Tag.tags.put(p.getUniqueId(), this);
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank r) {
        this.rank = r;
    }

    public void update(Player toUpdate) {
        Scoreboard sb = toUpdate.getScoreboard();
        if (sb == null) return;

        String prefixo = getRank().getColor() + "[" + getRank().getAliases().get(0) + "] ";
        if (prefixo.length() > 16) prefixo = prefixo.substring(0, 16);

        Team tag = sb.getTeam(rank.toString()) != null ?
                sb.getTeam(rank.toString()) : sb.createTeam(rank.toString());

        tag.setPrefix(prefixo);
        tag.setDisplayName(rank.getColor() + rank.toString());
        tag.setTagVisibility(PacketPlayOutScoreboardTeam.NameTag.VISIABLE);

        tag.addMember(p.getName());
    }

    public void updateForAll() {
        for(ProxiedPlayer p : Main.getPlugin().getProxy().getPlayers()){
            if(!p.getServer().getInfo().getName().equals(p.getServer().getInfo().getName())) continue;
            Player pl = (Player) p;
            this.update(pl);
        }
    }
}
