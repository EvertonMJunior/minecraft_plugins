package net.goodcraft.lobby.signs;

import net.goodcraft.GameMode;
import net.goodcraft.Main;
import net.goodcraft.api.BungeeUtils;
import net.goodcraft.api.Message;
import net.goodcraft.sql.MySQL;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameSign {

    public static ArrayList<GameSign> gameSigns = new ArrayList<>();

    private final GameMode gameMode;
    private final Location location;
    private final int rotation;
    private String map;
    private SignState signState;
    private ArrayList<BungeeUtils.ServerStatus> servers = new ArrayList<>();

    public GameSign(GameMode gameMode, Location location, int rotation, String map, SignState signState) {
        this.gameMode = gameMode;
        this.location = location;
        this.rotation = rotation;
        this.map = map;
        this.signState = signState;

        gameSigns.add(this);
    }

    public static int getGameModeCount(GameMode gameMode) {
        int count = 0;
        for (GameSign gameSign : gameSigns) {
            if (gameSign.gameMode != gameMode) continue;
            for (BungeeUtils.ServerStatus serverStatus : gameSign.servers) {
                try {
                    count += Integer.parseInt(serverStatus.parseData(BungeeUtils.Connection.MOTD).split(":")[1]);
                } catch (Exception e) {
                }
            }
        }
        return count;
    }

    public static GameSign getByLocation(Location l) {
        for (GameSign gameSign : gameSigns) {
            if (gameSign.getSign() == null) {
                return null;
            }
            if (gameSign.getSign().getLocation().equals(l)) {
                return gameSign;
            }
        }
        return null;
    }

    public static void syncAllSigns() {
        SQLStatus.Callback<HashMap<String, Object>> c = new SQLStatus.Callback<HashMap<String, Object>>() {
            @Override
            public void onSucess(HashMap<String, Object> o) {
                Location l = new Location((World) o.get("world"), (Integer) o.get("x"), (Integer) o.get("y"), (Integer) o.get("z"));

                GameSign gs = new GameSign((GameMode) o.get("gamemode"), l, (Integer) o.get("rotation"), (String) o.get("mapa"),
                        SignState.valueOf((String) o.get("state")));

                if (getByLocation(gs.location) != null) {
                    return;
                }
                gs.create();
            }

            @Override
            public void onFailure(Throwable cause) {
            }
        };

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    return;
                }
                try {
                    PreparedStatement ps = Main.getSQL().getConnection()
                            .prepareStatement("SELECT * FROM good_signs");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        final GameMode gm = GameMode.valueOf(rs.getString("gamemode"));
                        final World w = Bukkit.getWorld(rs.getString("world"));
                        final int x, y, z, r;
                        x = rs.getInt("x");
                        y = rs.getInt("y");
                        z = rs.getInt("z");
                        r = rs.getInt("rotation");
                        final String map, state;
                        map = rs.getString("mapa");
                        state = rs.getString("state");

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                HashMap<String, Object> o = new HashMap<>();
                                o.put("gamemode", gm);
                                o.put("world", w);
                                o.put("x", x);
                                o.put("y", y);
                                o.put("z", z);
                                o.put("rotation", r);
                                o.put("mapa", map);
                                o.put("state", state);
                                c.onSucess(o);
                            }
                        }.runTask(net.goodcraft.lobby.Main.getPlugin());
                    }
                    rs.close();
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    try {
                        if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e1) {

                }
            }
        }.runTaskAsynchronously(net.goodcraft.lobby.Main.getPlugin());

    }

    @SuppressWarnings("deprecation")
    public void create() {
        Block b = location.getBlock();
        b.setData((byte) rotation);
        b.setType(Material.WALL_SIGN);
        Sign s = (Sign) b.getState();
        s.setLine(0, "§l[" + gameMode.getSignName() + "]");
        s.setLine(1, map);
        s.setLine(2, " ");
        s.setLine(3, signState.getName());
        s.update(true);
    }

    public void setMap(String map) {
        this.map = map;
        Sign s = getSign();
        s.setLine(1, map);
        s.update(true);
    }

    public void setGameState(SignState signState) {
        this.signState = signState;
        Sign s = getSign();
        s.setLine(3, signState.getName());
        s.update(true);
    }

    @SuppressWarnings("deprecation")
    public boolean exists() {
        Block b = location.getBlock();
        b.setData((byte) rotation);
        return b.getType().name().contains("SIGN");
    }

    @SuppressWarnings("deprecation")
    public Sign getSign() {
        if (!exists()) return null;
        Block b = location.getBlock();
        if (b == null) {
            return null;
        }
        b.setData((byte) rotation);
        return (Sign) b.getState();
    }

    public void sync() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    return;
                }
                try {
                    PreparedStatement ps = Main.getSQL().getConnection()
                            .prepareStatement("SELECT * FROM good_servers WHERE gamemode='" + gameMode.name() + "' AND mapa='" + map + "'");
                    ResultSet rs = ps.executeQuery();
                    ArrayList<BungeeUtils.ServerStatus> servidores = new ArrayList<>();
                    while (rs.next()) {
                        servidores.add(new BungeeUtils.ServerStatus(rs.getString("ip"), rs.getInt("port"), rs.getString("bungeename")));
                    }
                    servers.clear();
                    servers.addAll(servidores);
                    servidores.clear();
                    rs.close();
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    try {
                        if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(net.goodcraft.lobby.Main.getPlugin());
    }

    public void reload() {
        SQLStatus.Callback<String> c = new SQLStatus.Callback<String>() {
            @Override
            public void onSucess(String state) {
                SignState signState = SignState.valueOf(state);

                if (signState != SignState.MANUTENCAO) {
                    ArrayList<Boolean> disponible = new ArrayList<>();

                    if(!servers.isEmpty()){
                        for (BungeeUtils.ServerStatus sv : servers) {
                            String[] data = sv.parseData(BungeeUtils.Connection.MOTD).split(":");
                            if (!data[0].equalsIgnoreCase("PREGAME")) {
                                disponible.add(false);
                                continue;
                            }
                            if (data[1].equalsIgnoreCase(data[2])) {
                                disponible.add(false);
                                continue;
                            }
                            disponible.add(true);
                        }
                    }

                    if (!disponible.contains(true)) {
                        signState = SignState.INDISPONIVEL;
                    }
                }

                Sign s = getSign();
                if (s == null) return;
                s.setLine(3, signState.getName());
                s.update(true);
            }

            @Override
            public void onFailure(Throwable cause) {

            }
        };

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    return;
                }
                try {
                    PreparedStatement ps = Main.getSQL().getConnection()
                            .prepareStatement("SELECT state FROM good_signs WHERE" +
                                    " gamemode = '" + gameMode.name() + "' AND mapa = '" + map + "';");
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        c.onSucess(rs.getString("state"));
                    }
                    rs.close();
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    try {
                        if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(net.goodcraft.lobby.Main.getPlugin());
    }

    public void sendToMostFullServer(Player p) {
        if (signState == SignState.MANUTENCAO) {
            Message.ERROR.send(p, "No momento este mapa está em manutenção!");
            return;
        }

        HashMap<BungeeUtils.ServerStatus, Integer> servidores = new HashMap<>();

        for (BungeeUtils.ServerStatus sv : servers) {
            String[] data = sv.parseData(BungeeUtils.Connection.MOTD).split(":");
            if (!data[0].equalsIgnoreCase("PREGAME")) continue;
            if (data[1].equalsIgnoreCase(data[2])) continue;
            servidores.put(sv, Integer.valueOf(data[1]));
        }
        if (servidores.isEmpty()) {
            Message.ERROR.send(p, "No momento não há nenhum servidor disponível para este mapa.");
            return;
        }
        Map.Entry<BungeeUtils.ServerStatus, Integer> server = Collections.max(servidores.entrySet(),
                (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        if (servidores.size() == 1) server = servidores.entrySet().iterator().next();

        BungeeUtils.sendToServer(p.getName(), server.getKey().getBungeeName());
    }
}
