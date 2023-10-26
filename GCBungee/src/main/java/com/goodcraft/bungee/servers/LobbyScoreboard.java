package com.goodcraft.bungee.servers;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Rank;
import com.goodcraft.bungee.api.SQLStatus;
import com.goodcraft.bungee.api.ScoreboardManager;
import dev.wolveringer.BungeeUtil.Player;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.UUID;

public class LobbyScoreboard implements Runnable {

    private final HashMap<UUID, ScoreboardManager> SCOREBOARDS = new HashMap<>();

    public void run() {
        for (ProxiedPlayer pl : Main.getPlugin().getProxy().getPlayers()) {
            Player p = (Player) pl;
            if(!p.getServer().getInfo().getName().contains("lobby")){
                return;
            }
            if (!hasBoard(p)) {
                createBoard(p);
            }
            ScoreboardManager board = getScoreboardManager(p);

            board.setText(" ", "", 10);
            Rank pRank = Rank.getPlayerRank(p.getUniqueId());
            board.setText("§fRank §b➛ ", pRank.getColor() + pRank, 9);
            board.setText(" ", "", 8);
            board.setText("§fGoodCoins §b➛ ", "§a" + SQLStatus.getFormattedCoins(p.getUniqueId()), 7);
            board.setText(" ", "", 6);
            board.setText("§fLobby §b➛ ", "§7" + p.getServer().getInfo().getName().replace("lobby", ""), 5);
            board.setText(" ", "", 4);
            board.setText("§fJogadores §b➛ ", "§7" + Main.getPlugin().getProxy().getPlayers().size(), 3);
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