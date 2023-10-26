package net.goodcraft.minigames.game;

import net.goodcraft.Main;
import net.goodcraft.api.*;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.Stat;
import net.goodcraft.minigames.eventos.StartEvent;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.minigames.kits.KitManager;
import net.goodcraft.minigames.timers.GameTimer;
import net.goodcraft.minigames.timers.PreGameTimer;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GameManager {

    private final Minigame MINIGAME;
    public PreGameTimer preGameTask;
    public GameTimer gameTask;
    private String name;

    public GameManager(Minigame minigame){
        this.MINIGAME = minigame;
    }

    public void startPreGame(){
        PreGameTimer p = new PreGameTimer(MINIGAME);
        final int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(MINIGAME.getPlugin(), p, 20L, 20L);
        p.setTaskId(i);
    }

    public boolean start() {
        if (preGameTask != null) {
            preGameTask.cancel();
            preGameTask = null;
        }
        StartEvent s = new StartEvent(MINIGAME);
        Bukkit.getPluginManager().callEvent(s);

        if(s.isCancelled()){
            return false;
        }

        Bukkit.getOnlinePlayers().forEach(BarAPI::removeBar);

        MINIGAME.setGameState(MINIGAME.hasOption(Option.HAS_INVENCIBILITY) ?
                GameState.INVENCIBILITY : GameState.STARTED);

        MINIGAME.getUsingWorld().getEntities().stream().filter(en -> en instanceof Villager).forEach(Entity::remove);
        MINIGAME.getUsingWorld().getEntities().stream().filter(en -> en instanceof Item).forEach(Entity::remove);
        MINIGAME.getUsingWorld().getEntities().stream().filter(en -> en instanceof ArmorStand).forEach(Entity::remove);

        if(MINIGAME.hasOption(Option.HAS_KITS)){
            Kit.kits.forEach(Kit::onStartGame);
        }

        Title.INFO.broadcast(MINIGAME.getName(), "Boa sorte! :D");

        MINIGAME.getUsingWorld().setTime(0);
        MINIGAME.getUsingWorld().setThundering(false);
        MINIGAME.getUsingWorld().setStorm(false);

        for (UUID id : MINIGAME.getPlayers()) {
            Player p = Bukkit.getPlayer(id);
            p.closeInventory();
            p.getInventory().clear();
            p.playSound(p.getLocation(), Sound.ANVIL_LAND, 10.0F, 1.0F);
            p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 10.0F, 1.0F);
            p.playSound(p.getLocation(), Sound.PISTON_RETRACT, 10.0F, 1.0F);
            p.setGameMode(GameMode.SURVIVAL);
            p.setAllowFlight(false);
            p.setWalkSpeed(0.2f);
            p.setFlySpeed(0.1f);
            p.setFireTicks(0);

            if(MINIGAME.hasOption(Option.HAS_KITS)){
                Kit k = KitManager.getKit(p);
                if (k != null) {
                    k.addItems(p);
                }
            }
        }
        GameTimer g = new GameTimer(MINIGAME);
        final int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), g, 0L, 20L);
        g.setTaskId(i);
        return true;
    }

    public boolean check() {
        if(false) return false;

        if (MINIGAME.getCurrentPlayers() != 1) return false;

        final Player p = Bukkit.getPlayer(MINIGAME.getPlayers().get(0));

        if(p == null){
            return false;
        }

        win(p);
        return true;
    }

    public void win(Player p){
        MINIGAME.setGameState(GameState.WIN);
        gameTask.cancel();
        if (p == null || !p.isOnline()) {
            for(Player l : Bukkit.getOnlinePlayers()){
                BungeeUtils.sendToServer(l.getName(), "lobby1");
            }
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(Bukkit.getOnlinePlayers().size() == 0){
                        Bukkit.shutdown();
                    }
                }
            }.runTaskTimer(MINIGAME.getPlugin(), 0L, 20L);
            return;
        }
        SQLStatus.addStatus(p.getUniqueId(), net.goodcraft.GameMode.SKYWARS, "wins", 1);
        SQLStatus.addCoins(p.getUniqueId(), 350);

        p.getInventory().clear();

        Location loc = p.getWorld().getSpawnLocation().clone().add(0.5, 0, 0.5);
        p.setAllowFlight(true);
        p.setFlying(true);
        p.teleport(loc.add(0, 3, 0));
        p.sendMessage(" ");

        String prefix = "§6[" + MINIGAME.getName() + "]§e ";

        Message.INFO.send(p, prefix + "Parabéns! Você ganhou a partida!");
        Message.INFO.send(p, prefix + "Tempo de jogo: " + Utils.secondsToString(MINIGAME.getGameTime()));
        String kit = "Nenhum";
        if (KitManager.hasAnyKit(p)) {
            kit = KitManager.getKit(p).toString();
        }
        Message.INFO.send(p, prefix + "Seu kit: " + kit);

        for (Stat s : MINIGAME.getStatsOnKill()) {
            p.sendMessage(prefix + s.getName() + ": " + s.getStat(p.getUniqueId()));
        }

        if(MINIGAME.hasOption(Option.UNLIMITED_LIFES)){
            for (Stat s : MINIGAME.getStatsOnDeath()) {
                p.sendMessage(prefix + s.getName() + ": " + s.getStat(p.getUniqueId()));
            }
        }

        p.sendMessage(" ");

        Title.INFO.broadcast(name, p.getName() + " venceu!");

        p.getWorld().playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 1F);

        final boolean[] verify = new boolean[]{false};

        new BukkitRunnable() {
            public void run() {
                if(verify[0]){
                    cancel();
                    return;
                }

                if (p.isOnline()) {
                    Utils.firework(p.getLocation(), Color.AQUA);
                } else {
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        BungeeUtils.sendToServer(other.getName(), "lobby1");
                    }
                    verify[0] = true;
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(MINIGAME.getPlugin(), 0, 2 * 20L);

        new BukkitRunnable() {
            public void run() {
                for (Player other : Bukkit.getOnlinePlayers()) {
                    BungeeUtils.sendToServer(other.getName(), "lobby1");
                }
            }
        }.runTaskLater(MINIGAME.getPlugin(), 20 * 20L);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(verify[0]){
                    if(Bukkit.getOnlinePlayers().size() == 0){
                        Bukkit.shutdown();
                    }
                }
            }
        }.runTaskTimer(MINIGAME.getPlugin(), 20L, 20L);
    }
}
