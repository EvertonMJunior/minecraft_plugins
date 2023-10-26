package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Hulk extends Kit {
    public Hulk() {
        super("Hulk", Material.BONE,
                new ItemStack[]{null},
                Arrays.asList("§7Clique com o botão direito em seu",
                        "§7adversário e pegue-o, facilitando",
                        "§7seu combate contra o mesmo."));
    }

    @EventHandler
    public void hulk(PlayerInteractEntityEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        Player p = e.getPlayer();
        if ((e.getRightClicked() instanceof Player)) {
            Player clicked = (Player) e.getRightClicked();
            if (clicked.getVehicle() == p) {
                clicked.eject();
                clicked.setVelocity(p.getEyeLocation().getDirection().multiply(1.3));
            }
            if ((!p.isInsideVehicle()) && (!clicked.isInsideVehicle()) &&
                    (p.getItemInHand().getType() == Material.AIR) &&
                    (hasAbility(p))) {
                if (!hasCooldown(p)) {
                    addCooldown(p, 10);
                    p.setPassenger(clicked);
                } else {
                    mensagemcooldown(p);
                }
            }
        }
    }
}
