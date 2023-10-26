package com.goodcraft.bungee;

import com.goodcraft.bungee.api.MySQL;
import com.goodcraft.bungee.api.Rank;
import com.goodcraft.bungee.comandos.*;
import com.goodcraft.bungee.eventos.ChatEvent;
import com.goodcraft.bungee.eventos.DisconnectHandlerEvent;
import com.goodcraft.bungee.eventos.JoinEvent;
import com.goodcraft.bungee.eventos.PingEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class Main extends Plugin {
    private static Plugin plugin;
    private static MySQL sql;

    public static MySQL getSQL() {
        return sql;
    }

    public static void setSQL(MySQL sql) {
        Main.sql = sql;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        setup();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        disable();

        super.onDisable();
    }

    private void setup() {

        registerCommands();
        registerListeners();

        getProxy().getScheduler().schedule(this, new Runnable() {
            @Override
            public void run() {
                for (ProxiedPlayer p : getProxy().getPlayers()) {
                    Rank r = Rank.getPlayerRank(p.getUniqueId());
                    p.setDisplayName(r.getColor() + (r != Rank.NORMAL ? "[" + r.getAliases().get(0) + "] " : "") + p.getName() +
                            (r != Rank.NORMAL ? "§f" : "§7"));
                }
            }
        }, 2 * 1000L, 30 * 1000L, TimeUnit.MILLISECONDS);


        plugin = this;

        MySQL.sqlConnect();
    }

    private void disable() {
        plugin = null;

        sql.closeConnection();
        sql = null;
    }

    private void registerCommands() {
        registerCommand(new StaffChatCmd());
        registerCommand(new LobbyCmd());
        registerCommand(new PunishCmd());
        registerCommand(new OnlinePlayersCmd());
        registerCommand(new PunishInfoCmd());
        registerCommand(new UnpunishCmd());
        registerCommand(new CoinsCmd());
        registerCommand(new KickCmd());
        registerCommand(new SetRankCmd());
    }

    private void registerListeners() {
        registerListener(new PingEvent());
        registerListener(new JoinEvent());
        registerListener(new ChatEvent());
        registerListener(new DisconnectHandlerEvent());
        registerListener(new CoinsCmd());
        registerListener(new HelpCmd());
    }

    private void registerCommand(Command cmd) {
        getProxy().getPluginManager().registerCommand(this, cmd);
    }

    private void registerListener(Listener l) {
        getProxy().getPluginManager().registerListener(this, l);
    }
}