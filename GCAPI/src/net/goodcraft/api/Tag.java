package net.goodcraft.api;

import net.goodcraft.Main;
import net.goodcraft.minigames.Option;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Tag {

    public static Map<UUID, Tag> tags = new HashMap<>();
    private Player player;
    private String p;
    private Rank rank;

    public Tag(String p, Rank rank) {
        this.p = p;
        this.player = Bukkit.getPlayerExact(p);
        this.rank = rank;
        Tag.tags.put(Bukkit.getPlayerExact(p).getUniqueId(), this);
    }

    public static Tag getByUUID(UUID id) {
        return tags.get(id);
    }

    public static void updateFor(Player p) {
        for (UUID id : tags.keySet()) {
            Player t = Bukkit.getPlayer(id);
            if (t == null) {
                continue;
            }
            Tag obj = tags.get(id);
            obj.update(p);
        }
    }

    public void setNick(String p) {
        this.p = p;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank r) {
        this.rank = r;
    }

    public Player getPlayer() {
        return player;
    }

    public void update(Player toUpdate) {
        if(Main.minigame != null && !Main.minigame.hasOption(Option.USE_RANKTAGS)){
            return;
        }

        Rank r = getRank() == null ? Rank.NORMAL : getRank();

        String prefixo = r.getColor() + "[" + r.getAliases().get(0) + "] ";

        Scoreboard sb = toUpdate.getScoreboard();
        if (sb == null) return;

        String teamName = ("0" + r.ordinal()) + r.getAliases().get(0);

        Team tag = sb.getTeam(teamName) != null ?
                sb.getTeam(teamName) : sb.registerNewTeam(teamName);

        PlayerTagEvent event = new PlayerTagEvent(this, toUpdate, prefixo, tag);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            tag.unregister();
            return;
        }
        tag.setPrefix(event.getPrefix());

        tag.setDisplayName(r != Rank.NORMAL ? prefixo : "§7NORMAL");
        tag.setNameTagVisibility(NameTagVisibility.ALWAYS);

        tag.addEntry(p);
    }

    public void updateForAll() {
        Bukkit.getOnlinePlayers().forEach(this::update);
    }
}
