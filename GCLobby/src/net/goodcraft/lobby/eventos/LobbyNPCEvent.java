package net.goodcraft.lobby.eventos;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.goodcraft.GameMode;
import net.goodcraft.api.Message;
import net.goodcraft.lobby.npcs.NPCAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class LobbyNPCEvent implements Listener {
    @EventHandler
    public void onNPCClick(NPCRightClickEvent e) {
        Player p = e.getClicker();
        NPC npc = e.getNPC();

        UUID id = npc.getUniqueId();
        GameMode gm = null;

        FileConfiguration c = NPCAPI.cfg;

        for(GameMode g : GameMode.values()){
            if(!c.contains(g.name() + "-NPC-LOBBY")) continue;
            UUID uid = UUID.fromString(c.getString(g.name() + "-NPC-LOBBY"));
            if(id.equals(uid)){
                gm = g;
                break;
            }
        }

        if(gm == null){
            return;
        }

        if(!gm.isActive()){
            Message.ERROR.send(p, "Este modo de jogo ainda não foi lançado!");
            return;
        }

        try{
            p.teleport(gm.getLocation());
        } catch(Exception ex){
            Message.ERROR.send(p, "Não há um lobby disponível para este modo de jogo. Tente mais tarde!");
        }
    }
}
