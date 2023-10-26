package net.goodcraft.minigames;

import net.goodcraft.GameMode;
import net.goodcraft.Main;
import net.goodcraft.api.ClassGetter;
import net.goodcraft.minigames.eventos.*;
import net.goodcraft.minigames.game.GameManager;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.game.SpectatorManager;
import net.goodcraft.minigames.kits.FreeKits;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.minigames.kits.KitSelector;
import net.goodcraft.minigames.kits.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Minigame {
    private final String NAME;
    private final GameMode MODE;
    private final GameManager GAMEMANAGER;
    private final Scoreboard SCOREBOARD;
    private final SpectatorManager SPECTATORMANAGER;
    private final Plugin PLUGIN;
    private final FreeKits freeKits;
    private final ArrayList<UUID> PLAYERS = new ArrayList<>();
    private final ArrayList<Option> OPTIONS = new ArrayList<>();
    private final ArrayList<Stat> STATSONKILL = new ArrayList<>();
    private final ArrayList<Stat> STATSONDEATH = new ArrayList<>();
    private final ArrayList<Kit> disabledKits = new ArrayList<>();
    private boolean kitsState = true;
    private Integer minimumPlayers;
    private Integer maximumPlayers;
    private Integer timeToStart = 300;
    private Integer gameTime = 0;
    private Integer maximumTime = 0;
    private Integer invencibilityTime = 10;
    private Integer maxY = 128;
    private Stat statToSetWinner;
    private GameState gameState;
    private World usingWorld;
    private ItemStack[] itemsOnKitAdd;

    public Minigame(String name, GameMode mode, Plugin plugin) {
        this.NAME = name;
        this.MODE = mode;
        this.PLUGIN = plugin;
        this.GAMEMANAGER = new GameManager(this);
        this.SPECTATORMANAGER = new SpectatorManager(this);
        this.SCOREBOARD = new Scoreboard(this);
        this.freeKits = new FreeKits(this);
        if(Main.minigame == null){
            Main.minigame = this;
            for(Player p : Bukkit.getOnlinePlayers()){
                PlayerJoinEvent e = new PlayerJoinEvent(p, null);
                Bukkit.getPluginManager().callEvent(e);
            }
        }
        registerListeners();
    }

    public String getName() {
        return NAME;
    }

    public GameMode getMode() {
        return MODE;
    }

    public void enableOption(Option... option) {
        if (!OPTIONS.containsAll(Arrays.asList(option))) OPTIONS.addAll(Arrays.asList(option));
        if(OPTIONS.contains(Option.HAS_SCOREBOARD)){
            Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), SCOREBOARD, 20L, 20L);
        }
    }

    public void disableOption(Option... option) {
        if (OPTIONS.containsAll(Arrays.asList(option))) OPTIONS.removeAll(Arrays.asList(option));
    }

    public boolean hasOption(Option option) {
        return OPTIONS.contains(option);
    }

    public Integer getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(Integer timeToStart) {
        this.timeToStart = timeToStart;
    }

    public Integer getGameTime() {
        return gameTime;
    }

    public void setGameTime(Integer gameTime) {
        this.gameTime = gameTime;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Plugin getPlugin() {
        return PLUGIN;
    }

    public Integer getCurrentPlayers() {
        return getPlayers().size();
    }

    public Integer getMaximumPlayers() {
        return maximumPlayers;
    }

    public void setMaximumPlayers(Integer maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
    }

    public Integer getMinimumPlayers() {
        return minimumPlayers;
    }

    public void setMinimumPlayers(Integer minimumPlayers) {
        this.minimumPlayers = minimumPlayers;
    }

    public GameManager getGameManager() {
        return GAMEMANAGER;
    }

    public SpectatorManager getSpectatorManager() {
        return SPECTATORMANAGER;
    }

    public World getUsingWorld() {
        return usingWorld;
    }

    public void setUsingWorld(World usingWorld) {
        this.usingWorld = usingWorld;
    }

    public Integer getMaximumTime() {
        return maximumTime;
    }

    public void setMaximumTime(Integer maximumTime) {
        this.maximumTime = maximumTime;
    }

    public Stat getStatToSetWinner() {
        return statToSetWinner;
    }

    public void setStatToSetWinner(Stat statToSetWinner) {
        this.statToSetWinner = statToSetWinner;
    }

    public ArrayList<UUID> getPlayers() {
        return PLAYERS;
    }

    public boolean isPlayer(UUID id) {
        return PLAYERS.contains(id);
    }

    public Integer getMaxY() {
        return maxY;
    }

    public void setMaxY(Integer maxY) {
        this.maxY = maxY;
    }

    public ArrayList<Stat> getStatsOnKill() {
        return STATSONKILL;
    }

    public void setStatsOnKill(Stat... stat) {
        this.STATSONKILL.clear();
        this.STATSONKILL.addAll(Arrays.asList(stat));
    }

    public ArrayList<Stat> getStatsOnDeath() {
        return STATSONDEATH;
    }

    public void setStatsOnDeath(Stat... stat) {
        this.STATSONDEATH.clear();
        this.STATSONDEATH.addAll(Arrays.asList(stat));
    }

    public void setKitsState(boolean kitsState) {
        this.kitsState = kitsState;
    }

    public boolean areKitsEnabled() {
        return kitsState;
    }

    public ArrayList<Kit> getDisabledKits() {
        return disabledKits;
    }

    public FreeKits getFreeKits() {
        return freeKits;
    }

    public Integer getInvencibilityTime() {
        return invencibilityTime;
    }

    public void setInvencibilityTime(Integer invencibilityTime) {
        this.invencibilityTime = invencibilityTime;
    }

    public Scoreboard getScoreboard() {
        return SCOREBOARD;
    }

    public void setItemsOnKitAdd(ItemStack... items){
        itemsOnKitAdd = items;
    }

    public ItemStack[] getItemsOnKitAdd() {
        return itemsOnKitAdd;
    }

    public void registerKits(JavaPlugin javaPlugin, String kitsPackage) {
        for (Class<?> kits : ClassGetter.getClassesForPackage(javaPlugin, kitsPackage)) {
            if (Kit.class.isAssignableFrom(kits)) {
                try {
                    kits.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Collections.sort(Kit.kits, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                Kit c1 = (Kit) o1;
                Kit c2 = (Kit) o2;
                return c1.toString().compareToIgnoreCase(c2.toString());
            }
        });
    }

    public void registerListeners() {
        rL(new DamageEvent());
        rL(new DeathEvent(this));
        rL(new DropItemEvent());
        rL(new net.goodcraft.minigames.eventos.GeneralEvents(this));
        rL(new QuitEvent());
        rL(new RelogEvent());
        rL(new MotdEvent());
        rL(new JoinEvent(this));
        rL(new KitSelector());
        rL(new ShopManager());
        rL(getSpectatorManager());
    }

    private void rL(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, PLUGIN);
    }
}
