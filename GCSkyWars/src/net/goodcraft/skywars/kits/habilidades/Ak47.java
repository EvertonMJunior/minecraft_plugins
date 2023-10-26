package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.skywars.Main;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import java.util.Arrays;
import java.util.List;

public class Ak47 extends Kit {
    public Ak47() {
        super("Ak47", Material.IRON_BARDING,
                new ItemStack[]{
                        Item.item(Material.IRON_BARDING, 1, "§3Ak47")
                },
                Arrays.asList(
                        "§7Use sua AK-47 em seu Adversário fazendo",
                        "§7com que ele leve um dano de 4 corações e",
                        "§7fique pegando fogo por 5 segundos."
                ));
        setVip();
        setPrice(6000);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(!e.getAction().name().contains("RIGHT")) return;
        if (!hasAbility(p)) return;
        if (p.getItemInHand() == null) return;
        if (p.getItemInHand().getType() != Material.IRON_BARDING) return;
        if (Main.getMg().getGameState() == GameState.PREGAME) return;

        e.setCancelled(true);
        e.setUseInteractedBlock(Event.Result.DENY);
        e.setUseItemInHand(Event.Result.DENY);

        if (hasCooldown(p)) {
            mensagemcooldown(p);
            return;
        }
        addCooldown(p, 30);

        p.setVelocity(p.getLocation().getDirection().multiply(-0.8));

        BlockIterator blocksToAdd = new BlockIterator(p.getEyeLocation(), 0.0, 40);
        Location blockToAdd;

        while (blocksToAdd.hasNext()) {
            blockToAdd = blocksToAdd.next().getLocation();
            p.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.REDSTONE_BLOCK, 40);

            double radius = 1.5D;
            List<Entity> near = p.getEyeLocation().getWorld().getEntities();

            for (Entity ee : near) {
                if (ee.getLocation().distance(blockToAdd) > radius || ee.getUniqueId().equals(p.getUniqueId()))
                    continue;
                if (!(ee instanceof Player)) continue;
                ((Player) ee).damage(8.0D, p);
                ee.setFireTicks(5 * 20);
            }
        }
    }
}
