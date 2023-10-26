package net.goodcraft;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.goodcraft.api.*;
import net.goodcraft.eventos.AdminEvent;
import net.goodcraft.eventos.*;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Stat;
import net.goodcraft.sql.MySQL;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin implements PluginMessageListener {

    public static boolean worldEditSupport;
    public static WorldEditPlugin worldEdit;
    public static HashMap<UUID, Rank> rankCache;
    public static NickAPI nickAPI;
    public static Minigame minigame;
    private static Plugin plugin;
    private static MySQL sql;
    private static SQLStatus status;
    private static ProtocolManager protocolManager;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static MySQL getSQL() {
        return sql;
    }

    public static void setSQL(MySQL sql) {
        Main.sql = sql;
    }

    public static ProtocolManager getPM() {
        return protocolManager;
    }

    public static SQLStatus getStatus() {
        return status;
    }

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Enabled {0} v{1}", new Object[]{this.getName(), this.getDescription().getVersion()});
        setup();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Disable {0} v{1}", new Object[]{this.getName(), this.getDescription().getVersion()});
        disable();

        super.onDisable();
    }

    private void setup() {
        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        rankCache = new HashMap<>();
        nickAPI = new NickAPI(this);

        new BukkitRunnable(){
            @Override
            public void run() {
                Utils.registerCommands(getFile());
            }
        }.runTaskLater(this, 80L);

        registerListeners();

        MySQL.sqlConnect();
        status = new SQLStatus();

        for (GameMode gm1 : GameMode.values()) {
            if (gm1.getStatus() == null) continue;

            for (Stat st : gm1.getStatus()) {
                if (GameMode.statusType.contains(st.getName())) {
                    continue;
                }
                GameMode.statusType.add(st.getName());
            }
        }
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        setupWorldEdit();

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new SecondsEvent());
            }
        }.runTaskTimer(this, 20L, 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getWorlds().stream().forEach((w) -> {
                    w.setGameRuleValue("doDaylightCycle", "false");
                    w.setTime(6000);
                    w.setThundering(false);
                    w.setStorm(false);
                    w.setAutoSave(false);
                });
            }
        }.runTaskTimer(this, 20L, 2 * (60 * 20L));

        new BukkitRunnable() {
            boolean hasGottenServers = false;
            boolean hasGottenServerName = false;

            @Override
            public void run() {
                if (hasGottenServers && hasGottenServerName) {
                    cancel();
                    return;
                }

                if (BungeeUtils.servers.isEmpty()) {
                    BungeeUtils.retrieveServers();
                } else {
                    hasGottenServers = true;
                }

                if (BungeeUtils.serverName == null) {
                    BungeeUtils.retrieveServerName();
                } else {
                    hasGottenServerName = true;
                }

            }
        }.runTaskTimer(this, 20L, 20L);
    }


    private void disable() {
        plugin = null;
        worldEdit = null;
        protocolManager = null;
        rankCache = null;
        nickAPI = null;
        BungeeUtils.serverName = null;
        BungeeUtils.servers = null;

        sql.closeConnection();
        sql = null;
        status = null;
    }

    private void setupWorldEdit() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
        if (plugin == null || !(plugin instanceof WorldEditPlugin))
            return;

        worldEditSupport = true;
        worldEdit = (WorldEditPlugin) plugin;
    }

    private void registerListeners() {
        registerListener(new JoinEvent());
        registerListener(new QuitEvent());
        registerListener(new AdminEvent());
        registerListener(new TestHackEvent());
        registerListener(new ChatEvent());
        registerListener(new ChunkUnloadEvent());
        registerListener(new OnSecondsEvent());
        registerListener(new HologramKillEvent());
        registerListener(new BungeeUtils());
        registerListener(new GeneralEvents());
    }

    private void registerListener(Listener l) {
        getServer().getPluginManager().registerEvents(l, this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equalsIgnoreCase("BungeeCord")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        Bukkit.getPluginManager().callEvent(new ProxyMessageEvent(subchannel, player, message, in));
    }

}
