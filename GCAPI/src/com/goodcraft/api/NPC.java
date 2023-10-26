package com.goodcraft.api;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class NPC implements Listener {
    public static ArrayList<NPC> npcs = new ArrayList<>();
    
    private EntityPlayer npc;
    private Location l;

    @SuppressWarnings("deprecation")
    public NPC(String nome, Location l) {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) l.getWorld()).getHandle();
        npc = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(UUID.randomUUID(), nome), new PlayerInteractManager(nmsWorld));
        npc.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
        this.l = l;
        
        Bukkit.getOnlinePlayers().stream().forEach((p) -> {
            show(p);
        });
        npcs.add(this);
    }
    
    public void show(Player p){
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
    }
}
