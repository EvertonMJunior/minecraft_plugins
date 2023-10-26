package net.goodcraft.api;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class Header {

    public static void set(Player p, String head, String foot) {
        if (head == null) {
            head = "";
        }
        if (foot == null) {
            foot = "";
        }
        PlayerConnection pc = ((CraftPlayer) p).getHandle().playerConnection;

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        IChatBaseComponent header = ChatSerializer.a("{'color':'" + "', 'text':'" + head + "'}");
        IChatBaseComponent footer = ChatSerializer.a("{'color':'" + "', 'text':'" + foot + "'}");
        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, header);
            headerField.setAccessible(!headerField.isAccessible());

            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, footer);
            footerField.setAccessible(!footerField.isAccessible());

        } catch (Exception e) {
            e.printStackTrace();
        }
        pc.sendPacket(packet);
    }
}
