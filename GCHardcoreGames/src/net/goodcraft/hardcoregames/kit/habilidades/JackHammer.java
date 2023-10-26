package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class JackHammer extends Kit {

    public JackHammer() {
        super("JackHammer", Material.STONE_AXE,
                new ItemStack[]{Item.item(Material.STONE_AXE, 1, "§3JackHammer")},
                Arrays.asList("§7Quebre apenas 1 bloco com seu" +
                        "§7machado e crie um buraco até a Bedrock."));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();
        Block b = e.getBlock();
        Location bLoc = b.getLocation();
        if (i == null) return;
        if (!hasAbility(p)) return;
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (i.getType() != Material.STONE_AXE) return;
        if (hasCooldown(p)) {
            mensagemcooldown(p);
            return;
        }
        int blocksToBreak = bLoc.getBlockY() - 1;
        new BukkitRunnable(){
            int times = blocksToBreak;
            @Override
            public void run() {
                if(times == 0){
                    cancel();
                    return;
                }
                bLoc.getBlock().setType(Material.AIR);
                bLoc.subtract(0, 1, 0);
                times--;
            }
        }.runTaskTimer(Main.getPlugin(), 0L, 1L);
        addCooldown(p, 10);
    }
}
