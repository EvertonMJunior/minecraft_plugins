package com.goodcraft.api;

import com.google.common.collect.ImmutableList;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public abstract class Comando implements CommandExecutor, TabExecutor {

    public static String permissionsPrefix = "gc";

    private static CommandMap cmap;

    public static CommandMap getCmap() {
        return cmap;
    }

    public static void setCmap(CommandMap aCmap) {
        cmap = aCmap;
    }
    protected final String command;
    protected final String description;
    protected final List<String> alias;
    protected final String usage;
    protected final String permission;
    protected boolean inGameOnly;
    protected Player p;
    protected int playerArg;
    protected boolean getPlayer = false;
    protected Player player;
    protected int uuidArg;
    protected boolean getUUID = false;
    protected UUID uuid;

    public Comando(String cmd) {
        this.command = cmd;
        this.description = "Comando " + cmd;
        this.alias = null;
        this.usage = null;
        this.permission = null;
    }

    public Comando(String cmd, String permission) {
        this.command = cmd;
        this.description = "Comando " + cmd;
        this.alias = null;
        this.usage = null;
        this.permission = permission;
    }

    public Comando(String cmd, String permission, String[] aliases) {
        this.command = cmd;
        this.description = "Comando " + cmd;
        this.alias = Arrays.asList(aliases);
        this.usage = null;
        this.permission = permission;
    }

    public Comando(String cmd, String usage, String permission) {
        this.command = cmd;
        this.description = "Comando " + cmd;
        this.alias = null;
        this.usage = usage;
        this.permission = permission;
    }

    public Comando(String cmd, String usage, String permission, String[] aliases) {
        this.command = cmd;
        this.description = "Comando " + cmd;
        this.alias = Arrays.asList(aliases);
        this.usage = usage;
        this.permission = permission;
    }

    public void setInGameOnly(boolean b) {
        inGameOnly = b;
    }

    public void setGetPlayerByName(int argNumber) {
        getPlayer = true;
        playerArg = argNumber;
    }

    public void setGetUUIDByName(int argNumber) {
        getUUID = true;
        uuidArg = argNumber;
    }

    public void register() {
        ReflectCommand cmd = new ReflectCommand(this.command);
        if (this.alias != null) {
            cmd.setAliases(this.alias);
        }
        if (this.description != null) {
            cmd.setDescription(this.description);
        }
        if (this.usage != null) {
            cmd.setUsage("§cUse " + this.usage);
        }

        if (this.permission != null) {
            cmd.setPermission(permissionsPrefix + "." + this.permission);
        }
        cmd.setPermissionMessage("Unknown command. Type \"/help\" for help.");
        if (getCommandMap().getCommand(command) != null) {
            getCommandMap().getCommand(command).unregister(cmap);
        }
        getCommandMap().register("", cmd);
        cmd.setExecutor(this);
    }

    CommandMap getCommandMap() {
        if (getCmap() == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                setCmap((CommandMap) f.get(Bukkit.getServer()));
                return getCommandMap();
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            }
        } else if (getCmap() != null) {
            return getCmap();
        }
        return getCommandMap();
    }

    @Override
    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    @SuppressWarnings("deprecation")
    public List<String> tabComplete(String[] args) throws IllegalArgumentException {

        if (args.length == 0) {
            return ImmutableList.of();
        }

        String lastWord = args[args.length - 1];

        ArrayList<String> matchedPlayers = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            String name = player.getName();
            if (StringUtil.startsWithIgnoreCase(name, lastWord)) {
                matchedPlayers.add(name);
            }
        }

        Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1) {
            return tabComplete(args);
        }
        return ImmutableList.of();
    }

    private class ReflectCommand extends Command {

        private Comando exe = null;

        protected ReflectCommand(String command) {
            super(command);
        }

        public void setExecutor(Comando exe) {
            this.exe = exe;
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            if (exe != null) {
                if (!testPermission(sender)) {
                    return true;
                }

                if (inGameOnly && !(sender instanceof Player)) {
                    Message.ERROR.send(sender, "Comando apenas para jogadores!");
                    return false;
                }
                if (sender instanceof Player) {
                    p = (Player) sender;
                }

                if ((args.length - 1) == uuidArg && getUUID) {
                    try {
                        uuid = UUIDFetcher.getUUIDOf(args[uuidArg]);
                    } catch (Exception ex) {
                    }
                }

                if ((args.length - 1) == playerArg && getPlayer) {
                    Player toGet = Bukkit.getPlayerExact(args[playerArg]);
                    if (toGet == null) {
                        Message.ERROR.send(sender, args[playerArg] + " está offline.");
                        return false;
                    }
                    player = toGet;
                }

                return exe.onCommand(sender, this, commandLabel, args);
            }
            return false;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
            if (exe != null) {
                return exe.onTabComplete(sender, this, alias, args);
            }
            return null;
        }
    }
}
