package me.everton.hg.kits.habilidades;

import java.util.HashMap;

import me.everton.hg.Main;
import me.everton.hg.Cmds.Admin;
import me.everton.hg.kits.KitManager;
import me.everton.hg.kits.KitType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Endermage implements Listener {
	
	public static final HashMap<String, Material> oldBlock = new HashMap<>();
	public static final HashMap<String, Byte> oldBlockData = new HashMap<>();
	public static HashMap<String, Block> portals = new HashMap<>();
	public static final HashMap<String, Block> portais = new HashMap<>();
	public static HashMap<String, Integer> playerItemHeld = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void enderMage(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		final Block b = e.getClickedBlock();
		
		if(KitManager.getKit(p) != KitType.ENDERMAGE) {
			return;
		}
		
		if(e.getAction() == Action.PHYSICAL) {
			return;
		}
		
		if(!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		
		e.setCancelled(true);
		e.setUseInteractedBlock(Result.DENY);
		
		if(!Main.STATE_KITS) {
			p.sendMessage("§7[§c!§7] Os kits estao desativados!");
			return;
		}
		
		if(Main.INVENCIBILIDADE) {
			p.sendMessage("§7[§c!§7] Kit desativado durante a invencibilidade!");
			return;
		}
		
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), null);
		
		oldBlock.put(p.getName(), b.getType());
		oldBlockData.put(p.getName(), b.getData());
		playerItemHeld.put(p.getName(), p.getInventory().getHeldItemSlot());
		
		b.setType(Material.ENDER_PORTAL_FRAME);
		for(int i = 0; i < 5; i++) {
			final int n = i;
			Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					for(Player on : Bukkit.getOnlinePlayers()) {
						
						if(on.getName() != p.getName() && !Admin.admins.contains(on)) {
							if(isEnderable(b.getLocation(), on.getLocation())) {
								on.teleport(b.getLocation().clone().add(0, 1.5, 0));
								on.sendMessage("§7[§c!§7] Você foi puxado por um §c§lEndermage§7! Tens §c§l5 segundos §7de invenciblidade! Prepare-se ou corra!");
								on.setNoDamageTicks(100);
								p.teleport(b.getLocation().clone().add(0, 1.5, 0));
								p.sendMessage("§7[§c!§7] Tens §c§l5 segundos §7de invenciblidade! Prepare-se ou corra!");
								p.setNoDamageTicks(100);
							}
						}
					}
					p.sendMessage(n + "");
					
					if(n == 4) {
						int slot = playerItemHeld.get(p.getName());
						Block portal = portals.get(p.getName());
						Block portal2 = portais.get(p.getName());
						portal = portal2;
						p.getInventory().setItem(slot, KitManager.getKit(p).getItemKit());
						p.updateInventory();
						return;
					}
				}
			}, i * 20L);
		}
	}

	private boolean isEnderable(Location portal, Location player) {
		return (Math.abs(portal.getX() - player.getX()) < 2.0D)
				&& (Math.abs(portal.getZ() - player.getZ()) < 2.0D)
				&& (Math.abs(portal.getY() - player.getY()) > 1.0D);
	}
}
