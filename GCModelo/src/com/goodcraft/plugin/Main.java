package com.goodcraft.plugin;

import com.goodcraft.eventos.QuitEvent;
import com.goodcraft.plugin.eventos.JoinEvent;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{
    private static Plugin plugin;
    private static Scoreboard sb;
    
    public static Plugin getPlugin() {
        return plugin;
    }
    
    public static Scoreboard getScoreboard() {
        return sb;
    }
    
    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Enabled {0} v{1}", new Object[]{this.getName(), this.getDescription().getVersion()});
        setup();
        try {
            registerCommands();
            registerListeners();
        } catch (Exception ex) {}

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
        sb = new Scoreboard();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, sb, 20L, 20L);
    }

    private void disable() {
        plugin = null;
        sb = null;
    }

    private void registerCommands() throws Exception {
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
    }

    private void registerListener(Listener l) {
        getServer().getPluginManager().registerEvents(l, this);
    }
}
