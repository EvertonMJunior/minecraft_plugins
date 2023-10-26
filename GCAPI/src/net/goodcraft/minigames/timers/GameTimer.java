package net.goodcraft.minigames.timers;

import net.goodcraft.api.ActionBar;
import net.goodcraft.api.Message;
import net.goodcraft.api.Title;
import net.goodcraft.api.Utils;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.Stat;
import net.goodcraft.minigames.eventos.GameTimingEvent;
import net.goodcraft.minigames.game.GameManager;
import net.goodcraft.minigames.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameTimer implements Runnable {

    private final Minigame MINIGAME;
    private Integer taskId;

    public GameTimer(Minigame minigame) {
        this.MINIGAME = minigame;
        MINIGAME.getGameManager().gameTask = this;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public void cancel() {
        if (taskId == null) {
            return;
        }
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void run() {
        int time = MINIGAME.getGameTime();

        GameState state = MINIGAME.getGameState();
        GameManager manager = MINIGAME.getGameManager();

        World w = MINIGAME.getUsingWorld();

        if(MINIGAME.hasOption(Option.DISABLE_DAYCYLE)){
            w.setTime(0);
        }
        if(MINIGAME.hasOption(Option.DISABLE_RAIN)){
            w.setStorm(false);
            w.setThundering(false);
        }

        if (MINIGAME.hasOption(Option.HAS_WIN) && manager.check()) {
            MINIGAME.getGameManager().gameTask = null;
            cancel();
            return;
        }

        if (MINIGAME.hasOption(Option.HAS_TIMELIMIT) && MINIGAME.getMaximumTime() != 0 && time == MINIGAME.getMaximumTime()) {
            Title.INFO.broadcast(MINIGAME.getName(), "A partida excedeu " + Utils.secondsToSentence(time) + ".");
            new BukkitRunnable() {
                @Override
                public void run() {
                    Map<UUID, Integer> stats = new HashMap<>();
                    for (UUID id : MINIGAME.getPlayers()) {
                        stats.put(id, MINIGAME.getStatToSetWinner().getStat(id));
                    }

                    Map.Entry<UUID, Integer> topStat;
                    if (MINIGAME.getStatToSetWinner().getType() == Stat.Type.MAIOR) {
                        topStat = Collections.max(stats.entrySet(),
                                (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
                    } else {
                        topStat = Collections.min(stats.entrySet(),
                                (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
                    }

                    final Player winner = Bukkit.getPlayer(topStat.getKey());
                    Title.INFO.broadcast(MINIGAME.getName(), "E o vencedor é: §k" + winner.getName());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            MINIGAME.getGameManager().win(winner);
                            Title.INFO.broadcast(MINIGAME.getName(), "E o vencedor é: " + winner.getName() + "!");
                            for (UUID id : MINIGAME.getPlayers()) {
                                if (id.equals(winner.getUniqueId())) continue;
                                Player pl = Bukkit.getPlayer(id);
                                if (pl == null) continue;
                                pl.setHealth(0D);
                            }
                        }
                    }.runTaskLater(MINIGAME.getPlugin(), 59L);
                }
            }.runTaskLater(MINIGAME.getPlugin(), 59L);
            MINIGAME.getGameManager().gameTask = null;
            cancel();
            return;
        }

        if(MINIGAME.hasOption(Option.HAS_INVENCIBILITY)){
            if ((time < MINIGAME.getInvencibilityTime() && time % 5 == 0) && time != MINIGAME.getInvencibilityTime()) {
                if (state != GameState.INVENCIBILITY) MINIGAME.setGameState(GameState.INVENCIBILITY);
                Message.INFO.broadcast("§6[" + MINIGAME.getName() + "]§e A invencibilidade acaba em " +
                        Utils.secondsToSentence(MINIGAME.getInvencibilityTime() - time) + "!");
            }
            if (time == MINIGAME.getInvencibilityTime()) {
                MINIGAME.setGameState(GameState.STARTED);
                Title.INFO.broadcast(MINIGAME.getName(), "A invencibilidade acabou!");
                playSound(Sound.WITHER_DEATH);

            }
            if (state == GameState.INVENCIBILITY) {
                ActionBar.INFO.broadcast("A invencibilidade acaba em: " + Utils.secondsToString(MINIGAME.getInvencibilityTime() - time));
            }
        }

        if (time >= MINIGAME.getMaximumTime() - 60) {
            ActionBar.INFO.broadcast("A partida acaba em: " + Utils.secondsToString((MINIGAME.getMaximumTime()) - time));
        }
        MINIGAME.setGameTime(MINIGAME.getGameTime() + 1);

        Bukkit.getPluginManager().callEvent(new GameTimingEvent(MINIGAME));
    }

    private static void playSound(Sound s) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), s, 1.0F, 1.0F);
        }
    }
}
