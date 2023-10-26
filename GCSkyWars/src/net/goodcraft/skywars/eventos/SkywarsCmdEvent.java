package net.goodcraft.skywars.eventos;

import net.goodcraft.api.Message;
import net.goodcraft.skywars.comandos.SkywarsCmd;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SkywarsCmdEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();
        if (i == null) return;
        if (!SkywarsCmd.settingLocations.contains(p.getUniqueId())) return;
        if (i.getType() != Material.SLIME_BALL) return;
        e.setCancelled(true);
        SkywarsCmd.locations.add(p.getLocation());
        p.sendMessage(" ");
        Message.INFO.send(p, "Localização #" + SkywarsCmd.locations.size() + " setada!");
        Message.INFO.send(p, "Para salvar todas as localizações, digite §6/skywars setspawnlocations §enovamente!");
        p.sendMessage(" ");
    }
}
