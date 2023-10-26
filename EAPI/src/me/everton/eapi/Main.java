package me.everton.eapi;

import java.util.HashMap;
import java.util.UUID;
import me.everton.eapi.comandos.ReloadCmd;
import me.everton.eapi.eventos.JoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    private static HashMap<UUID, PlayerData> jogadores;
    private static Plugin plugin;
    private static SQL sql;

    public static Plugin getPlugin() {
        return plugin;
    }
    
    public static SQL getSQL(){
        return sql;
    }
    
    public static HashMap<UUID, PlayerData> getJogadores(){
        return jogadores;
    }

    @Override
    public void onEnable() {
        plugin = this;
        jogadores = new HashMap<>();
        registerCommands();
        registerListeners();
        sql = new SQL("root", "vertrigo", "eFactions", "127.0.0.1", 3306);

        super.onEnable();
    }

    @Override
    public void onDisable() {
        plugin = null;
        jogadores = null;
        sql.close();
        sql = null;

        super.onDisable();
    }

    private void registerCommands() {
        new ReloadCmd().register();
    }
    
    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new JoinEvent(), getPlugin());
    }
}
