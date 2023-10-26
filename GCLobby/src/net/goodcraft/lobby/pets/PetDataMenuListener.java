package net.goodcraft.lobby.pets;

import com.dsh105.echopet.compat.api.entity.PetType;
import com.dsh105.echopet.compat.api.event.PetInteractEvent;
import net.goodcraft.lobby.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class PetDataMenuListener implements Listener {

    @EventHandler
    public void onInteract(PetInteractEvent e) {
        e.setCancelled(true);

        if (!e.getPet().getOwnerUUID().equals(e.getPlayer().getUniqueId())) {
            return;
        }
        if(e.getAction() != PetInteractEvent.Action.RIGHT_CLICK){
            return;
        }
        new PetSettings(e.getPlayer()).open();
    }

    @EventHandler
    public void onDismount(EntityDismountEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        if (!Main.getPetAPI().getPet(p).getPetType().equals(PetType.SHEEP)) return;

        Main.getPetAPI().removePet(p, false, false);
    }
}
