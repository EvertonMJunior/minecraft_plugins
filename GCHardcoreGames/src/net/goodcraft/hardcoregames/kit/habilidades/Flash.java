package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.Item;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Flash extends Kit {
    private HashSet<Byte> Blocos = new HashSet<>();


    @SuppressWarnings("deprecation")
    public Flash() {
        super("Flash", Material.REDSTONE_TORCH_ON,
                new ItemStack[]{Item.item(Material.REDSTONE_TORCH_ON, 1, "§3Flash")},
                Arrays.asList("§7Assim como o Flash, teleporte-se",
                        "§7rapidamente para qualquer lugar"));
        Blocos.add((byte) 0);
        for (byte b = 8; b < 12; b++)
            Blocos.add(b);
        Blocos.add((byte) Material.SNOW.getId());
        Blocos.add((byte) Material.LONG_GRASS.getId());
        Blocos.add((byte) Material.RED_MUSHROOM.getId());
        Blocos.add((byte) Material.RED_ROSE.getId());
        Blocos.add((byte) Material.YELLOW_FLOWER.getId());
        Blocos.add((byte) Material.BROWN_MUSHROOM.getId());
        Blocos.add((byte) Material.SIGN_POST.getId());
        Blocos.add((byte) Material.WALL_SIGN.getId());
        Blocos.add((byte) Material.FIRE.getId());
        Blocos.add((byte) Material.TORCH.getId());
        Blocos.add((byte) Material.REDSTONE_WIRE.getId());
        Blocos.add((byte) Material.REDSTONE_TORCH_OFF.getId());
        Blocos.add((byte) Material.REDSTONE_TORCH_ON.getId());
        Blocos.add((byte) Material.VINE.getId());
        Blocos.add((byte) Material.WATER_LILY.getId());
    }

    @EventHandler
    public void mark(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (b.getType() == Material.REDSTONE_TORCH_ON) {
            if (hasAbility(p)) {
                e.setCancelled(true);
                p.updateInventory();
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void teleport(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().getType() == Material.REDSTONE_TORCH_ON) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                if (hasAbility(p)) {
                    if (hasCooldown(p)) {
                        e.setCancelled(true);
                        mensagemcooldown(p);
                    } else {
                        e.setCancelled(true);
                        List<Block> b = p.getLastTwoTargetBlocks(Blocos, 200);
                        if (b.size() > 1 && b.get(1).getType() != Material.AIR) {
                            double distancia = p.getLocation().distance(b.get(0).getLocation());
                            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                            p.getEyeLocation().getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 5);
                            Location loc = b.get(0).getLocation().clone().add(0.5, 0.5, 0.5);
                            loc.setPitch(p.getLocation().getPitch());
                            loc.setYaw(p.getLocation().getYaw());
                            p.teleport(loc);
                            p.getWorld().strikeLightningEffect(loc);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (int) ((distancia / 2) * 20), 1), true);
                            addCooldown(p, 60);
                        }
                    }
                }
            }
        }
    }
}