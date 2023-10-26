package net.goodcraft.lobby;

import net.goodcraft.ScoreboardManager;
import net.goodcraft.api.AdminAPI;
import net.goodcraft.api.BungeeUtils;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Tag;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.UUID;

public class Scoreboard implements Runnable {

    private final HashMap<UUID, ScoreboardManager> SCOREBOARDS = new HashMap<>();

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasMetadata("NPC")) {
                continue;
            }
            if (!hasBoard(p)) {
                createBoard(p);
                Rank.syncPlayerRank(p.getUniqueId(), new SQLStatus.Callback<Rank>() {
                    @Override
                    public void onSucess(Rank r) {
                        Tag t = Tag.getByUUID(p.getUniqueId()) != null ? Tag.getByUUID(p.getUniqueId()) : new Tag(p.getName(), null);
                        t.setRank(r);
                        t.updateForAll();
                        Tag.updateFor(p);

                        refresh(p, r);
                    }

                    @Override
                    public void onFailure(Throwable cause) {
                    }
                });
                continue;
            }
            refresh(p, Rank.getPlayerRank(p.getUniqueId()));
        }
    }

    public void refresh(Player p, Rank pRank) {
        ScoreboardManager board = getScoreboardManager(p);
        board.setText(" ", "", 10);
        board.setText("§fRank §b➛ ", pRank.getColor() + pRank, 9);
        board.setText(" ", "", 8);

        SQLStatus.getStat(p.getUniqueId(), "good_global", "coins", new SQLStatus.Callback<Object>() {
            @Override
            public void onSucess(Object o) {
                int value = (Integer) o;
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator(',');
                symbols.setGroupingSeparator('.');
                String pattern = "#,###,###";
                DecimalFormat myFormatter = new DecimalFormat(pattern, symbols);
                String coins = myFormatter.format(value);

                board.setText("§fGoodCoins §b➛ ", "§a" + coins, 7);
            }

            @Override
            public void onFailure(Throwable throwable) {
                board.setText("§fGoodCoins §b➛ ", "§a" + 0, 7);
            }
        });

        board.setText(" ", "", 6);
        board.setText(" ", "", 5);
        board.setText("§fLobby §b➛ ", "§7" + (BungeeUtils.serverName == null ? "§k1" : BungeeUtils.serverName.replace("lobby", "")), 4);
        board.setText("§fJogadores §b➛ ", "§7" + (Bukkit.getOnlinePlayers().size() - AdminAPI.admins.size()) + "/" + Bukkit.getMaxPlayers(), 3);
        board.setText(" ", "", 2);
        board.setText("§7     www.good-", "§7craft.net     ", 1);
    }

    public boolean hasBoard(Player p) {
        return SCOREBOARDS.containsKey(p.getUniqueId());
    }

    public ScoreboardManager getScoreboardManager(Player p) {
        return SCOREBOARDS.get(p.getUniqueId());
    }

    public void createBoard(Player p) {
        if (!SCOREBOARDS.containsKey(p.getUniqueId())) {
            SCOREBOARDS.put(p.getUniqueId(), new ScoreboardManager(p, "        §3§lGoodCraft     "));
        }
    }

    public void removeBoard(Player p) {
        if (SCOREBOARDS.containsKey(p.getUniqueId())) {
            SCOREBOARDS.get(p.getUniqueId()).reset();
            SCOREBOARDS.remove(p.getUniqueId());
        }
    }

}
