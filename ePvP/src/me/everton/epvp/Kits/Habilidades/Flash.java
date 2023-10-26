package me.everton.epvp.Kits.Habilidades;

import java.util.ArrayList;

import me.everton.epvp.Main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Flash implements Listener{
	public static ArrayList<String> cd = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void flash(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT")) {
			if(Main.flash.contains(p.getName())) {
				if(p.getItemInHand().getType() == Material.REDSTONE_TORCH_ON) {
					if(cd.contains(p.getName())) {
						p.sendMessage("§7[§c!§7] Kit em cooldown!");
						return;
					}
					Block b = p.getTargetBlock(null, 100).getRelative(BlockFace.UP);
					if(b == null) {
						return;	
					}
					if(b.getType() == Material.AIR) {
						return;
					}
			        Location loc = b.getLocation();
			        loc.setPitch(p.getLocation().getPitch());
			        loc.setYaw(p.getLocation().getYaw());
			        p.teleport(loc);
					p.sendMessage("§aTeleportado!");
					cd.add(p.getName());
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						
						@Override
						public void run() {
							if(cd.contains(p.getName())) {
								cd.remove(p.getName());
								p.sendMessage("§7[§a!§7] O cooldown acabou!");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
							}
						}
					}, 15 * 20L);
				}
			}
		}
	}
}
