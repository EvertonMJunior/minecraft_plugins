package net.goodcraft.api;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.goodcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

public class BungeeUtils implements Listener {
    public static String serverName;
    public static ArrayList<String> servers = new ArrayList<>();

    public static void sendMsg(String message, String channel) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player p = null;
        for (Player r : Bukkit.getOnlinePlayers()) {
            p = r;
            break;
        }
        if (p == null) return;
        p.sendPluginMessage(Main.getPlugin(),
                channel, stream.toByteArray());
    }

    public static void sendPluginMessage(String server, String channel, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        try {
            out.writeUTF("Forward");
            out.writeUTF(server);
            out.writeUTF(channel);

            ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
            DataOutputStream msgout = new DataOutputStream(msgbytes);
            msgout.writeUTF(message);

            out.writeShort(msgbytes.toByteArray().length);
            out.write(msgbytes.toByteArray());

            Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getPlayerCount(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        try {
            out.writeUTF("PlayerCount");
            out.writeUTF(server);

            Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String name, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        try {
            out.writeUTF("Message");
            out.writeUTF(name);
            out.writeUTF(message);

            Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendToServer(String p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ConnectOther");
        out.writeUTF(p);
        out.writeUTF(server);
        Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
    }

    public static void kickPlayer(String p, String reason) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(p);
        out.writeUTF(reason);
        Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
    }

    public static void broadcastToServer(String server, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        try {
            out.writeUTF("Forward");
            out.writeUTF(server);
            out.writeUTF("ServerMessage");

            ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
            DataOutputStream msgout = new DataOutputStream(msgbytes);
            msgout.writeUTF(message);

            out.writeShort(msgbytes.toByteArray().length);
            out.write(msgbytes.toByteArray());

            Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void broadcastToServer(String server, String message, Rank rank) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        try {
            out.writeUTF("Forward");
            out.writeUTF(server);
            out.writeUTF("ServerMessage:" + rank.name());

            ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
            DataOutputStream msgout = new DataOutputStream(msgbytes);
            msgout.writeUTF(message);

            out.writeShort(msgbytes.toByteArray().length);
            out.write(msgbytes.toByteArray());

            Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void retrieveServerName() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServer");
        Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
    }

    public static void retrieveServers() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");
        Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
    }

    @EventHandler
    public void onBungeeMessage(ProxyMessageEvent e) {
        if (e.getChannel().equals("GetServer")) {
            serverName = e.getIn().readUTF();
            Message.INFO.send(Bukkit.getConsoleSender(), "Nome do servidor: " + serverName);
            return;
        }

        if (e.getChannel().equals("GetServers")) {
            servers.clear();
            servers.addAll(Arrays.asList(e.getIn().readUTF().split(", ")));
            return;
        }

        if (e.getChannel().contains("ServerMessage")) {
            try {
                short len = e.getIn().readShort();
                byte[] msgbytes = new byte[len];
                e.getIn().readFully(msgbytes);

                DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
                String data = msgin.readUTF();
                if (!e.getChannel().equals("ServerMessage")) {
                    String rankName = e.getChannel().split(":")[1];
                    Rank r = Rank.getByName(rankName);
                    Utils.broadcast(data, r);
                    return;
                }
                Utils.broadcast(data);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return;
        }

    }

    public enum Connection {
        PLAYERS_ONLINE, SERVER_VERSION, MOTD;
    }

    public static class ServerStatus {
        private String host = null;
        private String bungeeName = null;
        private int port = 0;

        public ServerStatus(String host, int port, String bungeeName) {
            this.host = host;
            this.port = port;
            this.bungeeName = bungeeName;
        }

        public String parseData(Connection c) {
            try {
                Socket socket = new Socket();
                OutputStream os;
                DataOutputStream dos;
                InputStream is;
                InputStreamReader isr;

                socket.setSoTimeout(2500);
                socket.connect(new InetSocketAddress(host, port), 2500);

                os = socket.getOutputStream();
                dos = new DataOutputStream(os);

                is = socket.getInputStream();
                isr = new InputStreamReader(is, Charset.forName("UTF-16BE"));

                dos.write(new byte[]{(byte) 0xFE, (byte) 0x01});

                int packetId = is.read();

                if (packetId == -1) return "EOS1";
                if (packetId != 0xFF) return "EOS2";

                int lenght = isr.read();

                if (lenght == -1) return "EOS3";

                if (lenght == 0) return "EOS4";

                char[] chars = new char[lenght];

                if (isr.read(chars, 0, lenght) != lenght) return "EOS5";

                String string = new String(chars);
                String[] data = string.split("\0");

                if (c == Connection.PLAYERS_ONLINE) {
                    return Integer.parseInt(data[4]) + "/" +
                            Integer.parseInt(data[5]);
                } else if (c == Connection.MOTD) {
                    return data[3];
                } else if (c == Connection.SERVER_VERSION) {
                    return data[2];
                } else {
                    return "ERROR";
                }

            } catch (Exception e) {
                return "EOS10";
            }
        }

        public String getBungeeName() {
            return bungeeName;
        }
    }
}
