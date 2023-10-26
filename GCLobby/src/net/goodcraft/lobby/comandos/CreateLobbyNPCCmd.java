package net.goodcraft.lobby.comandos;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.entity.EntityHumanNPC;
import net.goodcraft.GameMode;
import net.goodcraft.api.Comando;
import net.goodcraft.api.Hologram;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.lobby.npcs.NPCAPI;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class CreateLobbyNPCCmd extends Comando {

    public CreateLobbyNPCCmd() {
        super("createlobbynpc", Rank.DEVELOPER);
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
            np.setGravityEnabled(false);

            Location l = npc.getEntity().getLocation().clone().subtract(0, 0.25, 0);

            Hologram.hologram("§6§l" + gm.getName().toUpperCase(), l.clone().add(0, 0.45, 0));
            ArmorStand as = Hologram.hologram((gm.isActive() ? "§e0 jogadores" : "§eEM BREVE!"), l.clone().add(0, 0.1, 0));

            NPCAPI.cfg.set(gm.name() + "-LOBBY", as.getUniqueId().toString());
            NPCAPI.cfg.set(gm.name() + "-NPC-LOBBY", npc.getUniqueId().toString());
            NPCAPI.cfg.save(NPCAPI.file);
        } catch (Exception e) {
            e.printStackTrace();
            Message.ERROR.send(p, "Use /createlobbynpc <minigame> [skin]");
            return false;
        }

        return false;
    }

}
