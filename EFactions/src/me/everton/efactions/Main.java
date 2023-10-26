package me.everton.efactions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import me.everton.eapi.ClassFinder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        try {
            registerCommands();
        } catch (Exception ex) {
            getLogger().info(ex.getMessage());
        }

        super.onEnable();
    }

    @Override
    public void onDisable() {
        plugin = null;
        
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.sendMessage("MENSAGEM DO BROADCAST");
                }
            }
        }.runTaskTimer(plugin, 0L, 60 * 20L);

        super.onDisable();
    }

    private void registerCommands() throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, IOException, ClassNotFoundException {
//            Class classe = Class.forName("me.everton.efactions.comandos.BanCmd");
        for (Class classe : ClassFinder.getClassesInPackage("me.everton.efactions.comandos")) {
            Object c = classe.newInstance();
            Method m = classe.getMethod("register");
            m.invoke(c);
            getLogger().log(Level.INFO, "Comando adicionado: /{0}", classe.getSimpleName().replace("Cmd", "").toLowerCase());
        }
    }

}
