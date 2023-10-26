package com.goodcraft.lobby;

import com.goodcraft.ScoreboardManager;
import com.goodcraft.api.AdminAPI;
import com.goodcraft.sql.SQLStatus;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Scoreboard implements Runnable {

    private final HashMap<UUID, ScoreboardManager> SCOREBOARDS = new HashMap<>();

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(p.hasMetadata("NPC")){
                continue;
            }
            if (!hasBoard(p)) {
                createBoard(p);
            }

            ScoreboardManager board = getScoreboardManager(p);

            board.setText(" ", "", 10);
            board.setText("§fRank §b➛ ", "§4DONO", 9);
            board.setText(" ", "", 8);
            board.setText("§fGoodCoins §b➛ ", "§a" + SQLStatus.getFormattedCoins(p.getUniqueId()), 7);
            board.setText(" ", "", 6);
            board.setText(" ", "", 5);
            board.setText("§fLobby §b➛ ", "§7" + "1", 4);
            board.setText("§fJogadores §b➛ ", "§7" + (Bukkit.getOnlinePlayers().size() - AdminAPI.admins.size()) + "/" + Bukkit.getMaxPlayers(), 3);
            board.setText(" ", "", 2);
            board.setText("§7     www.good-", "§7craft.net     ", 1);
        }
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
