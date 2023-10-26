package com.goodcraft.api;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public enum ActionBar {
    GOOD(ChatColor.GREEN),
    ERROR(ChatColor.DARK_RED),
    INFO(ChatColor.YELLOW);

    private final ChatColor aBColor;

    private ActionBar(ChatColor aBColor) {
        this.aBColor = aBColor;
    }

    public void send(Player whoToSend, String message) {
        if (whoToSend == null || message == null) {
            return;
        }
        PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + aBColor + message + "\"}"), (byte) 2);
        
        ((CraftPlayer) whoToSend).getHandle().playerConnection.sendPacket(packet);
    }

    public void broadcast(String message) {
        if (message == null) {
            return;
        }
        PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + message + "\"}"), (byte) 2);

        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }

    }
}
