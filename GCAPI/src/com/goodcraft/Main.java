package com.goodcraft;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.goodcraft.eventos.AdminEvent;
import com.goodcraft.eventos.JoinEvent;
import com.goodcraft.eventos.QuitEvent;
import com.goodcraft.eventos.TestHackEvent;
import com.goodcraft.sql.MySQL;
import com.goodcraft.sql.SQLStatus;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scoreboard.Scoreboard;

public class Main extends JavaPlugin implements PluginMessageListener {

    private static Plugin plugin;
    private static MySQL sql;
    private static SQLStatus status;
    private static Scoreboard sb;
    private static ProtocolManager protocolManager;
    private static String serverName;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Scoreboard getSb() {
        return sb;
    }

    public static String getServerName() {
        return serverName;
    }
    
    public static MySQL getSQL(){
        return sql;
    }
    
    public static ProtocolManager getPM(){
        return protocolManager;
    }
    
    public static void setSQL(MySQL sql){
        Main.sql = sql;
    }
    
    public static SQLStatus getStatus(){
        return status;
    }

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Enabled {0} v{1}", new Object[]{this.getName(), this.getDescription().getVersion()});
        setup();
        try {
            registerCommands();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        registerListeners();

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
        
        MySQL.sqlConnect();
        status = new SQLStatus(this);
        
        sb = Bukkit.getScoreboardManager().getMainScoreboard();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        Bukkit.getWorlds().stream().forEach((w) -> {
            w.setGameRuleValue("doDaylightCycle", "false");
            w.setTime(6000);
            w.setDifficulty(Difficulty.PEACEFUL);
            w.setThundering(false);
            w.setStorm(false);
        });
    }

    private void disable() {
        plugin = null;
        
        sb = null;
        serverName = null;

        sql.closeConnection();
        sql = null;
        status = null;
    }

    private void registerCommands() throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try (JarInputStream is = new JarInputStream(new FileInputStream(getFile()))) {
            for (JarEntry entry; (entry = is.getNextJarEntry()) != null;) {
                if (!entry.getName().endsWith(".class")) {
                    continue;
                }
                String path = entry.getName().replace("/", ".").replace(".class", "");
                if (path.contains(".comandos.")) {
                    Class cl = Class.forName(path);
                    Object o = cl.newInstance();
                    Method md = cl.getMethod("register");
                    md.invoke(o);
                }
            }
        }
    }

    private void registerListeners() {
        registerListener(new JoinEvent());
        registerListener(new QuitEvent());
        registerListener(new AdminEvent());
        registerListener(new TestHackEvent());
//        registerListener(new ChatEvent());
    }

    private void registerListener(Listener l) {
        getServer().getPluginManager().registerEvents(l, this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String sName = in.readUTF();
        serverName = sName;
    }

}
