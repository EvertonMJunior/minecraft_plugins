package com.goodcraft.skywars;

import com.goodcraft.ScoreboardManager;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Scoreboard implements Runnable {

    private final HashMap<UUID, ScoreboardManager> SCOREBOARDS = new HashMap<>();

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!hasBoard(p)) {
                createBoard(p);
            }

            ScoreboardManager board = getScoreboardManager(p);

            board.setText(" ", "", 14);
            board.setText("§fRank §b➛ ", "§4DONO", 13);
            board.setText(" ", "", 12);
            board.setText("§fMiniGame §b➛ ", "§7SkyWars", 11);
            board.setText("§fKit §b➛ ", "§7", 10);
            board.setText(" ", "", 9);
            board.setText("§fTempo §b➛ ", "§7" + Main.getGm().getFormatedTime(), 8);
            board.setText("§fFase §b➛ ", "§7" + Main.getGm().getState(), 7);
            board.setText("§fKills §b➛ ", "§73", 6);
            board.setText(" ", "", 5);
            board.setText(" ", "", 4);
            board.setText("§fJogadores §b➛ ", "§7" + Main.getGm().getPlayingCount(), 3);
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
