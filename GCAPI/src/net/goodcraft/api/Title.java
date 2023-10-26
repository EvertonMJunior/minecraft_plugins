package net.goodcraft.api;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public enum Title {
    GOOD(ChatColor.GREEN),
    ERROR(ChatColor.RED),
    INFO(ChatColor.YELLOW);

    private final ChatColor titleColor;

    private Title(ChatColor titleColor) {
        this.titleColor = titleColor;
    }

    public void send(Player whoToSend, String titleMessage, String subtitleMessage) {
        if (whoToSend == null || (titleMessage == null && subtitleMessage == null)) {
            return;
        }
        if (titleMessage != null) {
            PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + titleColor + titleMessage + "\"}"), 10, 50, 10);
            ((CraftPlayer) whoToSend).getHandle().playerConnection.sendPacket(title);
        }
        if (subtitleMessage != null) {
            PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"" + "§f§l" + subtitleMessage + "\"}"), 10, 50, 10);
            ((CraftPlayer) whoToSend).getHandle().playerConnection.sendPacket(subtitle);
        }
    }

    public void broadcast(String titleMessage, String subtitleMessage) {
        if (titleMessage == null && subtitleMessage == null) {
            return;
        }
        PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + titleColor + titleMessage + "\"}"), 10, 50, 10);
        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"" + "§f§l" + subtitleMessage + "\"}"), 10, 50, 10);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (titleMessage != null) {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
            }
            if (subtitleMessage != null) {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(subtitle);
            }
        }
    }
}
