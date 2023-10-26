package net.goodcraft.minigames.timers;

import net.goodcraft.api.*;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PreGameTimer implements Runnable {

    private final Minigame MINIGAME;
    private Integer taskId;

    public PreGameTimer(Minigame minigame){
        this.MINIGAME = minigame;
        MINIGAME.getGameManager().preGameTask = this;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public void cancel(){
        if(taskId == null){
            return;
        }
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void run() {
        if(!MINIGAME.hasOption(Option.HAS_PREGAME)) return;

        int toStart = MINIGAME.getTimeToStart();

        GameState state = MINIGAME.getGameState();
        int minP = MINIGAME.getMinimumPlayers();
        int maxP = MINIGAME.getMaximumPlayers();
        int curP = MINIGAME.getCurrentPlayers();

        World w = MINIGAME.getUsingWorld();

        if(MINIGAME.hasOption(Option.DISABLE_DAYCYLE) || MINIGAME.hasOption(Option.DISABLE_DAYCYLE_DURINGPREGAME)){
            w.setTime(0);
        }
        if(MINIGAME.hasOption(Option.DISABLE_RAIN) || MINIGAME.hasOption(Option.DISABLE_RAIN_DURINGPREGAME)){
            w.setStorm(false);
            w.setThundering(false);
        }

        if(state == null) state = GameState.PREGAME;
        if(state != GameState.PREGAME){
            MINIGAME.getGameManager().preGameTask = null;
            cancel();
            return;
        }

        if(curP < minP){
            int left = minP - curP;
            ActionBar.INFO.broadcast("Mínimo de " + minP + " jogador" + (left != 1 ? "es" : "") + ". Falta" +
                    (left != 1 ? "m " : " ") + left + " jogador" + (left != 1 ? "es" : "") + ".");
            return;
        }
        if(curP >= maxP && toStart >= 40){
            MINIGAME.setTimeToStart(29);
            broadcastStarting(30);
            playSound(Sound.CLICK);
        } else if((toStart % 15 == 0 && toStart > 15)){
            broadcastStarting(toStart);
        } else if(toStart == 15 || toStart <= 10 && toStart > 0){
            Title.INFO.broadcast(MINIGAME.getName(), "A partida começa em " + Utils.secondsToSentence(toStart) + "!");
            playSound(Sound.CLICK);
        } else if(toStart == 0){
            MINIGAME.getGameManager().start();
        }
        ActionBar.INFO.broadcast("Começa em: " + Utils.secondsToString(toStart));

        MINIGAME.setTimeToStart(toStart - 1);
    }

    private void broadcastStarting(int seconds){
        Message.INFO.broadcast("§6[" + MINIGAME.getName() + "]§e A partida inicia em §f" + Utils.secondsToSentence(seconds) + "§e!");
    }

    private void playSound(Sound s) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), s, 1.0F, 1.0F);
        }
    }
}
