package net.goodcraft.kitpvp;

import net.goodcraft.GameMode;
import net.goodcraft.api.ClassGetter;
import net.goodcraft.api.Message;
import net.goodcraft.api.Utils;
import net.goodcraft.kitpvp.eventos.GeneralEvents;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.stream.Collectors;


public class Main extends JavaPlugin {
    private static Plugin plugin;
    public static Minigame minigame;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Minigame getMg() {
        return minigame;
    }

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Enabled {0} v{1}", new Object[]{this.getName(), this.getDescription().getVersion()});
        setup();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Disabled {0} v{1}", new Object[]{this.getName(), this.getDescription().getVersion()});
        disable();

        super.onDisable();
    }

    private void setup() {
        plugin = this;

        minigame = new Minigame("KitPvP", GameMode.KITPVP, this);

        minigame.setUsingWorld(Bukkit.getWorld("world"));
        minigame.setStatsOnKill(GameMode.KITPVP.getStatus()[0]);
        minigame.setStatsOnDeath(GameMode.KITPVP.getStatus()[1]);
        minigame.setMaximumPlayers(Integer.valueOf(getConfig().getString("MAX_PLAYERS")));
        minigame.setMinimumPlayers(minigame.getMaximumPlayers() / 3);

        minigame.enableOption(Option.HAS_KITS, Option.CAN_DROP_ITEMS,
                Option.CAN_USE_INVENTORY, Option.DISABLE_DAYCYLE, Option.HAS_SCOREBOARD,
                Option.DISABLE_RAIN, Option.FREE_LAPIS_ON_ENCHANTMENTTABLE, Option.HAS_COMBATLOG,
                Option.TELEPORT_SPAWN_ONJOIN, Option.NERF_DAMAGE, Option.MAXHEALTH_ONJOIN, Option.MAXFOODLEVEL_ONJOIN,
                Option.GETS_DAMAGE, Option.CANJOIN_AFTERSTART, Option.UNLIMITED_LIFES);

        Utils.registerCommands(getFile());

        minigame.setMaxY(256);
        minigame.getUsingWorld().setSpawnLocation(0, 70, 0);

        registerListeners();
        registerKits("net.goodcraft.kitpvp.kits.habilidades");

        Collections.sort(Kit.kits, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                Kit c1 = (Kit) o1;
                Kit c2 = (Kit) o2;
                return c1.toString().compareToIgnoreCase(c2.toString());
            }
        });

        minigame.getPlayers().
                addAll(Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toList()));

        new BukkitRunnable() {

            @Override
            public void run() {
                minigame.setGameState(GameState.STARTED);

                minigame.getFreeKits().retrieveAllData();
                Message.INFO.send(Bukkit.getConsoleSender(), "[" + minigame.getName() + "] Pronto para o jogo.");
                minigame.getUsingWorld().setAutoSave(false);
                Bukkit.setSpawnRadius(0);
            }
        }.runTaskLater(this, 20L);
    }

    private void disable() {
        plugin = null;
        minigame = null;
    }

    private void registerListeners() {
        rL(new GeneralEvents());
        rL(new ConfigManager());
    }

    private void registerKits(String packageName) {
        for (Class<?> kits : ClassGetter.getClassesForPackage(this, packageName)) {
            if (Kit.class.isAssignableFrom(kits)) {
                try {
                    kits.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void rL(Listener l){
        getServer().getPluginManager().registerEvents(l, this);
    }

    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                deleteDir(new File(dir, children[i]));
            }
        }
        dir.delete();
    }
}
