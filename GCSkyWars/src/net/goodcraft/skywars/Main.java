package net.goodcraft.skywars;

import com.sk89q.worldedit.EditSession;
import net.goodcraft.GameMode;
import net.goodcraft.api.ClassGetter;
import net.goodcraft.api.Message;
import net.goodcraft.api.Schematic;
import net.goodcraft.api.Utils;
import net.goodcraft.eventos.HologramKillEvent;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.minigames.timers.PreGameTimer;
import net.goodcraft.skywars.eventos.PreGameItemsListener;
import net.goodcraft.skywars.eventos.SkywarsCmdEvent;
import net.goodcraft.skywars.eventos.StartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
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

    public static EditSession editSession;

    public static int lobbyY;
    
    public static Plugin getPlugin() {
        return plugin;
    }

    public static Minigame getMg() {
        return minigame;
    }

    @Override
    public void onLoad() {
        Bukkit.getServer().unloadWorld(Bukkit.getWorld("world"), false);
        deleteDir(new File("world"));
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

        minigame = new Minigame("SkyWars", GameMode.SKYWARS, this);

        minigame.setUsingWorld(Bukkit.getWorld("world"));
        minigame.setMaximumTime(10 * 60);
        minigame.setInvencibilityTime(10);
        minigame.setStatToSetWinner(GameMode.SKYWARS.getStatus()[1]);
        minigame.setStatsOnKill(GameMode.SKYWARS.getStatus()[1]);
        minigame.setStatsOnDeath(GameMode.SKYWARS.getStatus()[2]);
        minigame.setMaximumPlayers(Integer.valueOf(getConfig().getString("MAX_PLAYERS")));
        minigame.setMinimumPlayers(minigame.getMaximumPlayers() / 3);

        minigame.enableOption(Option.HAS_INVENCIBILITY, Option.HAS_KITS, Option.HAS_TIMELIMIT,
                Option.CAN_BUILD, Option.CAN_DESTROY, Option.HAS_PREGAME, Option.CAN_DROP_ITEMS,
                Option.CAN_PICKUP_DROPS, Option.CAN_USE_INVENTORY, Option.DISABLE_DAYCYLE, Option.HAS_SCOREBOARD,
                Option.DISABLE_RAIN, Option.FREE_LAPIS_ON_ENCHANTMENTTABLE, Option.DROP_ITEMS_ON_DEATH,
                Option.HAS_COMBATLOG, Option.TELEPORT_SPAWN_ONJOIN, Option.NERF_DAMAGE, Option.MAXHEALTH_ONJOIN,
                Option.MAXFOODLEVEL_ONJOIN, Option.HAS_WIN, Option.HAS_SPECTATORMODE, Option.GETS_DAMAGE);

        minigame.setTimeToStart(300);

        minigame.getUsingWorld().getEntities().stream().filter(en -> en instanceof Villager).forEach(Entity::remove);
        for(org.bukkit.entity.Item i : minigame.getUsingWorld().getEntitiesByClass(org.bukkit.entity.Item.class)){
            HologramKillEvent.allowedItems.add(i);
            i.remove();
        }
        minigame.getUsingWorld().getEntities().stream().filter(en -> en instanceof ArmorStand).forEach(Entity::remove);

        Utils.registerCommands(getFile());

        lobbyY = Integer.valueOf(getConfig().getString("WAITING-LOBBY_Y"));

        minigame.setMaxY(lobbyY + 3);
        minigame.getUsingWorld().setSpawnLocation(0, lobbyY + 3, 0);

        minigame.getSpectatorManager().inv = Bukkit.createInventory(null, ((((minigame.getMaximumPlayers() / 9) +
                ((minigame.getMaximumPlayers() % 9 == 0) ? 0 : 1)) * 9)), "Jogadores");

        registerListeners();
        registerKits("net.goodcraft.skywars.kits.habilidades");

        Collections.sort(Kit.kits, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                Kit c1 = (Kit) o1;
                Kit c2 = (Kit) o2;
                return c1.toString().compareToIgnoreCase(c2.toString());
            }
        });

        Location lobbyEspera = new Location(minigame.getUsingWorld(), minigame.getUsingWorld().getSpawnLocation().getX(),
                lobbyY, minigame.getUsingWorld().getSpawnLocation().getZ());
        Location mapa = new Location(minigame.getUsingWorld(), minigame.getUsingWorld().getSpawnLocation().getX(),
                70, minigame.getUsingWorld().getSpawnLocation().getZ());

        minigame.getPlayers().
                addAll(Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toList()));

        new BukkitRunnable() {

            @Override
            public void run() {
                minigame.setGameState(GameState.PREGAME);

                PreGameTimer p = new PreGameTimer(minigame);
                final int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(net.goodcraft.Main.getPlugin(), p, 20L, 20L);
                p.setTaskId(i);

                editSession = Schematic.paste("lobbyespera", lobbyEspera);
                Schematic.paste("mapa", mapa);
                minigame.getFreeKits().retrieveAllData();
                Message.INFO.send(Bukkit.getConsoleSender(), "[" + minigame.getName() + "] Pronto para o jogo.");
                minigame.getUsingWorld().setAutoSave(false);
                Bukkit.setSpawnRadius(0);
            }
        }.runTaskLater(this, 20L);
    }

    private void disable() {
        plugin = null;
        editSession = null;
    }

    private void registerListeners() {
        rL(new SkywarsCmdEvent());
        rL(new PreGameItemsListener());
        rL(new StartEvent());
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
