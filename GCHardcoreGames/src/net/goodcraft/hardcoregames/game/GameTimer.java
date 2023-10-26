package net.goodcraft.hardcoregames.game;

import net.goodcraft.api.*;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.hardcoregames.eventos.LavaChallenge;
import net.goodcraft.hardcoregames.eventos.PreGameFight;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.UUID;

public class GameTimer {

    public static BukkitTask preGameTask;
    public static BukkitTask gameTask;

    public static void preGame() {
        preGameTask = new BukkitRunnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                if(Main.estado == null){
                    Main.estado = GameState.PREGAME;
                }
                if (Main.estado != GameState.PREGAME) {
                    preGameTask = null;
                    cancel();
                } else if (Main.players.size() < 4) {
                    Main.toStart = 300;
                    return;
                } else if (Main.players.size() >= Bukkit.getMaxPlayers() - 5) {
                    if (Main.toStart > 60) {
                        Main.toStart = 45;
                        Message.INFO.broadcast("Servidor lotado, iniciando em breve!");
                    }
                } else if (Main.toStart % 30 == 0 && Main.toStart != 0) {
                    brodcastStartingMsg(Main.toStart);
                } else if (Main.toStart == 15 || Main.toStart >= 10) {
                    brodcastStartingMsg(Main.toStart);
                    Title.INFO.broadcast("HardcoreGames", "Iniciando em " + Utils.secondsToSentence(Main.toStart) + "!");
                    playSound();
                } else if (Main.toStart == 0) {
                    Main.toStart = 61;
                    if (Main.players.size() < 4) {
                        Message.INFO.broadcast("É preciso pelo menos 4 jogadores para a partida iniciar!");
                    } else {
                        start();
                    }
                }
                Main.usingWorld.setTime(0);
                Main.toStart--;

            }
        }.runTaskTimer(Main.getMain(), 20L, 20L);

    }


    public static void playSound() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.0F);
        }
    }

    public static void clear() {
        for (int x = -33; x < 33; x++) {
            for (int y = 187; y < 220; y++) {
                for (int z = -33; z < 33; z++) {
                    Block block = Main.usingWorld.getBlockAt(x, y, z);
                    if (block.getType() == Material.FENCE) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }

    }

    public static void start() {
        Main.estado = GameState.INVENCIBILITY;
        for (UUID id : PreGameFight.in1v1) {
            Player p = Bukkit.getPlayer(id);
            p.closeInventory();
            p.getInventory().clear();
            p.setHealth(20D);
            p.setFoodLevel(20);
        }
        for (UUID id : LavaChallenge.playersOnLavaChallenge) {
            Player p = Bukkit.getPlayer(id);
            if (p == null) continue;
            p.closeInventory();
            p.getInventory().clear();
            p.setHealth(20D);
            p.setFoodLevel(20);
        }
        Main.usingWorld.getEntities().stream().filter(en -> en instanceof Villager).forEach(Entity::remove);
        Main.usingWorld.getEntities().stream().filter(en -> en instanceof Item).forEach(Entity::remove);
        Main.usingWorld.getEntities().stream().filter(en -> en instanceof ArmorStand).forEach(Entity::remove);

        PreGameFight.in1v1.clear();
        Kit.kits.forEach(Kit::onStartGame);
        clear();
        preGameTask.cancel();
        preGameTask = null;
        Title.INFO.broadcast("HardcoreGames", "Que vença o melhor!");
        Main.usingWorld.setTime(0);
        Main.usingWorld.setThundering(false);
        Main.usingWorld.setStorm(false);
        if (preGameTask != null) {
            preGameTask.cancel();
        }
        for (UUID id : Main.players) {
            Player p = Bukkit.getPlayer(id);
            p.closeInventory();
            p.getInventory().clear();
            p.playSound(p.getLocation(), Sound.ANVIL_LAND, 10.0F, 1.0F);
            p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 10.0F, 1.0F);
            p.playSound(p.getLocation(), Sound.PISTON_RETRACT, 10.0F, 1.0F);
            p.setGameMode(GameMode.SURVIVAL);
            p.setAllowFlight(false);
            p.setWalkSpeed(0.2f);
            p.setFireTicks(0);
            Kit k = KitManager.getKit(p);
            if (k != null) {
                if (k.getItems() != null) {
                    for (ItemStack items : k.getItems()) {
                        if (items != null) {
                            p.getInventory().addItem(items);
                        }
                    }
                }
            }
            p.getInventory().addItem(new ItemStack(Material.COMPASS));
        }
        game();
    }

    @SuppressWarnings("deprecation")
    public static void check() {
        if (Main.players.size() == 1) {
            Main.estado = GameState.WIN;
            GameTimer.gameTask.cancel();
            final Player p = Bukkit.getPlayer(Main.players.get(0));
            if (p == null) {
                for (Player other : Bukkit.getOnlinePlayers()) {
                    BungeeUtils.sendToServer(p.getName(), "lobby1");
                }
                return;
            }
            SQLStatus.addStatus(p.getUniqueId(), net.goodcraft.GameMode.HARDCOREGAMES, "wins", 1);
            SQLStatus.addCoins(p.getUniqueId(), 1000);

            p.getInventory().clear();
            p.getInventory().setItem(4, Item.item(Material.WATER_BUCKET, 1, "§3MLG"));
            new BukkitRunnable() {
                public void run() {
                    if (p.isOnline()) {
                        p.setVelocity(p.getVelocity().multiply(0).setY(4));
                    } else {
                        for (Player other : Bukkit.getOnlinePlayers()) {
                            BungeeUtils.sendToServer(p.getName(), "lobby1");
                        }
                        if(Bukkit.getOnlinePlayers().size() == 0){
                            Bukkit.shutdown();
                        }
                    }

                }
            }.runTaskLater(Main.getMain(), 1);
            Location loc = p.getLocation();
            loc.setY(160);
            Schematic.paste("Bolo", loc);
            p.teleport(loc.add(0, 3, 0));
            p.sendMessage(" ");
            Message.INFO.send(p, "§f[HardcoreGames]§e Parabéns! Você ganhou a partida!");
            Message.INFO.send(p, "§f[HardcoreGames]§e Tempo: " + Utils.secondsToString(Main.gameTime));
            String kit = "Nenhum";
            if (KitManager.getKit(p) != null) {
                kit = KitManager.getKit(p).toString();

            }
            Message.INFO.send(p, "§f[HardcoreGames]§e Kit: " + kit);
            Message.INFO.send(p, "§f[HardcoreGames]§e Kills: " + Main.getKillstreak(p));
            p.sendMessage(" ");

            Title.INFO.broadcast("HardcoreGames", p.getName() + " venceu!");

            new BukkitRunnable() {
                public void run() {
                    if (p.isOnline()) {
                        Utils.firework(p.getLocation(), Color.AQUA);
                    } else {
                        for (Player other : Bukkit.getOnlinePlayers()) {
                            BungeeUtils.sendToServer(p.getName(), "lobby1");
                        }
                        if(Bukkit.getOnlinePlayers().size() == 0){
                            Bukkit.shutdown();
                        }
                    }
                }
            }.runTaskTimer(Main.getMain(), 20, 20);

            new BukkitRunnable() {
                public void run() {
                    if (p.isOnline()) {
                        Message.INFO.send(p, "Você ganhou a partida! Parabéns!");
                        BungeeUtils.sendToServer(p.getName(), "lobby1");
                    }
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (other != p) {
                            BungeeUtils.sendToServer(p.getName(), "lobby1");
                        }
                    }
                    if(Bukkit.getOnlinePlayers().size() == 0){
                        Bukkit.shutdown();
                    }
                }
            }.runTaskLater(Main.getMain(), 20 * 20);

        }
    }

    public static void game() {
        gameTask = new BukkitRunnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                if ((Main.gameTime % 30 == 0 && Main.gameTime != 120 && Main.gameTime < 120) || (Main.gameTime > 110 && Main.gameTime != 120  && Main.gameTime < 120)) {
                    broadcastInvencibilityEnding(120 - Main.gameTime);
                } else if (Main.gameTime == 120) {
                    for (UUID id : Main.players) {
                        Player p = Bukkit.getPlayer(id);
                        if (p == null) continue;
                        p.playSound(p.getLocation(), Sound.ANVIL_LAND, 10.0F, 1.0F);
                        p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 10.0F, 1.0F);
                        p.playSound(p.getLocation(), Sound.PISTON_RETRACT, 10.0F, 1.0F);
                    }
                    Main.estado = GameState.STARTED;
                    Title.INFO.broadcast("HardcoreGames", "A invencibilidade acabou!");
                } else if (Main.gameTime == 900) {
                    int z = 33 + new Random().nextInt(100);
                    int x = 33 + new Random().nextInt(100);
                    if (new Random().nextBoolean()) {
                        z = -z;
                    }
                    if (new Random().nextBoolean()) {
                        x = -x;
                    }
                    int y = Main.usingWorld.getHighestBlockYAt(z, x) + 1;
                    Feast.spawnFeast(new Location(Main.usingWorld, x, y, z));
                    Feast.feastLoc = new Location(Main.usingWorld, x, y, z);
                    broadcastFeastSpawning(60 * 5);
                } else if (Main.gameTime >= 960 && Main.gameTime < 1200 && Main.gameTime % 60 == 0) {
                    broadcastFeastSpawning(1200 - Main.gameTime);
                } else if (Main.gameTime == 1200) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(Feast.feastLoc, Sound.AMBIENCE_THUNDER, 1f, 1f);
                    }
                    Title.INFO.broadcast("O feast spawnou!", Utils.getXYZ(Feast.feastLoc));

                    Feast.spawnChests(Feast.feastLoc.add(0, 1, 0));
                } else if (Main.gameTime == 300 || Main.gameTime == 500 || Main.gameTime == 700) {
                    int z = 30 + new Random().nextInt(400);
                    int x = 30 + new Random().nextInt(400);
                    if (new Random().nextBoolean()) {
                        z = -z;
                    }
                    if (new Random().nextBoolean()) {
                        x = -x;
                    }
                    int y = Main.usingWorld.getHighestBlockYAt(x, z);
                    Minifeast.spawnMinifeast(new Location(Main.usingWorld, x, y, z));

                    int toBroadX = x + new Random().nextInt(100);
                    int toBroadX2 = x - new Random().nextInt(100);
                    int toBroadZ = z + new Random().nextInt(100);
                    int toBroadZ2 = z - new Random().nextInt(100);

                    Message.INFO.broadcast("Um mini feast spawnou entre §7(X: "
                            + toBroadX + " e "
                            + toBroadX2 + ") e (Z: "
                            + toBroadZ + " e "
                            + toBroadZ2 + ")");


                }

                check();
                Main.gameTime++;
                if (Main.gameTime >= 900 && Main.gameTime <= 1200) {
                    ActionBar.INFO.broadcast("Feast spawnando em " + Utils.secondsToString(1200 - Main.gameTime));
                }
                if (Feast.feastLoc != null && Main.gameTime <= 1500) {
                    for (UUID id : Main.players) {
                        Player p = Bukkit.getPlayer(id);
                        if (p == null) continue;
                        if(p.getLocation().distance(Feast.feastLoc) > 60){
                            ActionBar.INFO.send(p, "Feast: " + Utils.getXYZ(Feast.feastLoc));
                        }
                    }
                }

                if (Main.estado == GameState.INVENCIBILITY) {
                    ActionBar.INFO.broadcast("A invencibilidade acaba em " + Utils.secondsToString(120 - Main.gameTime));
                }
            }
        }.runTaskTimer(Main.getMain(), 20, 20);
    }

    public static void brodcastStartingMsg(int tempo) {
        Message.INFO.broadcast("A partida inicia em " + Utils.secondsToSentence(tempo) + "!");
    }

    public static void broadcastInvencibilityEnding(int tempo) {
        Message.INFO.broadcast("A invencibilidade acaba em " + Utils.secondsToSentence(tempo) + "!");
    }

    public static void broadcastFeastSpawning(int tempo) {
        Message.INFO.broadcast("O feast vai spawnar em " + Utils.secondsToSentence(tempo) + "!");
        Message.INFO.broadcast("X: " + Feast.feastLoc.getBlockX() + ", Y: " + Feast.feastLoc.getBlockY() + ", Z: " + Feast.feastLoc.getBlockZ());
    }
}
