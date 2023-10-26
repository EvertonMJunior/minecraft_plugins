package com.goodcraft.lobby.comandos;

import com.goodcraft.GameMode;
import com.goodcraft.api.Comando;
import com.goodcraft.api.Hologram;
import com.goodcraft.api.Message;
import com.goodcraft.lobby.npcs.NPCAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.entity.EntityHumanNPC;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class CreateLobbyNPCCmd extends Comando {

    public CreateLobbyNPCCmd() {
        super("createlobbynpc", "createlobbynpc");
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            Message.ERROR.send(p, "Use /createlobbynpc <minigame> [skin]");
            return false;
        }
        GameMode gm = null;
        try {
            gm = GameMode.valueOf(args[0].toUpperCase());

            NPCRegistry registry = CitizensAPI.getNPCRegistry();
            NPC npc = registry.createNPC(EntityType.PLAYER, "§7");

            npc.spawn(p.getLocation());
            CitizensNPC cnpc = (CitizensNPC) npc;
            EntityHumanNPC.PlayerNPC np = (EntityHumanNPC.PlayerNPC) cnpc.getEntity();
            np.setSkinName(args[1]);
            
            Location l = npc.getEntity().getLocation().clone().subtract(0, 0.25, 0);

            Hologram.hologram("§6§l" + gm.getName().toUpperCase(), l.clone().add(0, 0.45, 0));
            ArmorStand as = Hologram.hologram("§e0 jogadores", l.clone().add(0, 0.1, 0));
            
            NPCAPI.cfg.set(gm.name() + "-LOBBY", as.getUniqueId().toString());
            NPCAPI.cfg.save(NPCAPI.file);
        } catch (Exception e) {
            e.printStackTrace();
            Message.ERROR.send(p, "Use /createlobbynpc <minigame> [skin]");
            return false;
        }

        return false;
    }

}
