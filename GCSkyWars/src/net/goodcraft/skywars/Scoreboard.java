package net.goodcraft.skywars;

import net.goodcraft.ScoreboardManager;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Tag;
import net.goodcraft.api.Utils;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.minigames.kits.KitManager;
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

    private void refresh(Player p, Rank r) {
        ScoreboardManager board = getScoreboardManager(p);

        GameState gs = Main.getMg().getGameState();

        Kit pKit = KitManager.getKit(p);
        String kit = (pKit != null ? pKit.toString() : "Nenhum");

        board.setText(" ", "", 10);

        board.setText("§fRank §b➛ ", r.getColor() + r, 9);

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

                board.setText("§fGoodCoins §b➛ ", "§a" + coins, 8);
            }

            @Override
            public void onFailure(Throwable throwable) {
                board.setText("§fGoodCoins §b➛ ", "§a" + 0, 8);
            }
        });

        board.setText(" ", "", 7);

        board.setText("§fKit §b➛ ", "§7" + kit, 6);

        String timeType, time;
        if (gs == null) {
            timeType = "Aguarde";
            time = "Ligando servidor";
        } else if (gs == GameState.PREGAME) {
            timeType = "Iniciando";
            time = Utils.secondsToString(Main.getMg().getTimeToStart());
        } else if (gs == GameState.INVENCIBILITY) {
            timeType = "Acaba em";
            time = Utils.secondsToString(10 - Main.getMg().getGameTime());
        } else {
            timeType = "Tempo";
            time = Utils.secondsToString(Main.getMg().getGameTime());
        }

        board.setText("§f" + timeType + " §b➛ ", "§7" + time, 5);

        board.setText(" ", " ", 4);

        board.setText("§fJogadores §b➛ ", "§7" + Main.minigame.getCurrentPlayers() + "/" + Main.minigame.getMaximumPlayers(), 3);

        board.setText(" ", " ", 2);

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
            SCOREBOARDS.put(p.getUniqueId(), new ScoreboardManager(p, "      §6§lSkyWars  "));
        }
    }

    public void removeBoard(Player p) {
        if (SCOREBOARDS.containsKey(p.getUniqueId())) {
            SCOREBOARDS.get(p.getUniqueId()).reset();
            SCOREBOARDS.remove(p.getUniqueId());
        }
    }

}
