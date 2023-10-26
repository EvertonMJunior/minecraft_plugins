package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Set;

public class Firestorm extends Kit {
    public Firestorm() {
        super("Firestorm", Material.STAINED_CLAY, 14,
                new ItemStack[]{Item.item(Material.STAINED_CLAY, 14, "§3Firestorm - Fogo")},
                Arrays.asList("§7Jogue trovões em seus adversários",
                        "§7ou deixe-os pegando fogo! "));
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();
        if (i == null) return;
        if (!hasAbility(p)) return;
        if (Main.estado == GameState.PREGAME) return;
        if (i.getType() != Material.STAINED_CLAY) return;
        if ((i.getData().getData() != (byte) 3) && (i.getData().getData() != (byte) 14)) return;
        if (hasCooldown(p)) {
            mensagemcooldown(p);
            return;
        }
        addCooldown(p, 20);
        e.setCancelled(true);

        Location loc = p
                .getWorld()
                .getHighestBlockAt(
                        p.getTargetBlock((Set<Material>) null, 20).getLocation())
                .getLocation();
        ArmorStand as = loc.getWorld().spawn(loc, ArmorStand.class);
        as.setVisible(false);
        as.setGravity(false);

        if (i.getData().getData() == (byte) 3) {
            if (e.getAction().name().contains("LEFT")) {
                p.setItemInHand(Item.item(Material.CLAY, 14, "§3Firestorm - Fogo"));
                as.remove();
                return;
            }
            if (e.getAction().name().contains("RIGHT")) {
                for (Entity ent : as.getNearbyEntities(3, 3, 3)) {
                    if (!(ent instanceof Player)) {
                        continue;
                    }
                    Player eP = (Player) ent;
                    if (!eP.getUniqueId().equals(p.getUniqueId())) {
                        eP.getLocation().getWorld().strikeLightning(eP.getLocation());
                    }
                }
                as.remove();
            }
        } else if (i.getData().getData() == (byte) 14) {
            if (e.getAction().name().contains("LEFT")) {
                p.setItemInHand(Item.item(Material.STAINED_CLAY, 3, "§3Firestorm - Trovão"));
                as.remove();
                return;
            }
            if (e.getAction().name().contains("RIGHT")) {
                for (Entity ent : as.getNearbyEntities(3, 3, 3)) {
                    if (!(ent instanceof Player)) {
                        continue;
                    }
                    Player eP = (Player) ent;
                    if (!eP.getUniqueId().equals(p.getUniqueId())) {
                        eP.setFireTicks(3 * 20);
                    }
                }
                as.remove();
            }
        }
    }
}
