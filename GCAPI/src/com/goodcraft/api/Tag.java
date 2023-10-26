package com.goodcraft.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.goodcraft.Main;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Tag {

    public static HashMap<UUID, String> tags = new HashMap<>();
    
    public static void setAll(Player p){
        for(UUID id : tags.keySet()){
            Player t = Bukkit.getPlayer(id);
            if(t == null){
                continue;
            }
            String tag = tags.get(id);
            new Tag(t, tag);
        }
    }

    private final Player p;
    private final String tag;

    public Tag(Player p, String tag) {
        this.p = p;
        this.tag = (tag.length() > 16 ? tag.substring(0, 15) : tag);
    }

    public void set() {
        String name = UUID.randomUUID().toString().substring(0, 15);
        
        PacketContainer packet = Main.getPM().createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        packet.getIntegers().write(1, 0);
        packet.getStrings().write(0, name);
        packet.getStrings().write(1, tag);
        packet.getStrings().write(2, tag);
        packet.getSpecificModifier(Collection.class).write(0, Arrays.asList(p.getName()));
        
//        PacketContainer addedPacket = Main.getPM().createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
//        addedPacket.getIntegers().write(1, 3);
//        addedPacket.getStrings().write(0, name);
//        addedPacket.getStrings().write(1, tag);
//        addedPacket.getStrings().write(2, tag);
        
        p.setDisplayName(tag + p.getName() + "§r");
        
        if(tags.containsKey(p.getUniqueId())){
            tags.remove(p.getUniqueId());
        }
        tags.put(p.getUniqueId(), tag);

        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                Main.getPM().sendServerPacket(player, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
