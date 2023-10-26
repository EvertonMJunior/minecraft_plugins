package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.kits.Kit;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Aladdin extends Kit implements Listener {
	public Aladdin(String nome, String[] desc, ItemStack iS, ItemStack iP, Color c) {
		super(nome, desc, iS, iP, c);
	}

	public static HashMap<String, Integer> cd = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void rain(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (KitManager.getKit(p) != KitType.ALADDIN) {
			return;
		}

		if (!e.getAction().name().contains("RIGHT")) {
			return;
		}

		if (!KitManager.isWithKitItemInHand(p)) {
			return;
		}

		if (!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}

		if (Grappler.nerf.contains(p.getName())) {
			p.sendMessage("§7[§c!§7] Você está em §a§lcombate§7!");
			return;
		}

		e.setCancelled(true);
		p.updateInventory();

		if (cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
					+ cd.get(p.getName()) + " segundos§7!");
			return;
		}

		API.startCd(p, 15, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
		final FallingBlock f = p.getWorld().spawnFallingBlock(p.getLocation(),
				Material.CARPET, (byte) 0);
		f.setPassenger(p);
		f.setVelocity(p.getEyeLocation().getDirection().multiply(1.8D).setY(1D));
		f.setDropItem(false);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void noBlockForm(EntityChangeBlockEvent e) {
		if (e.getEntityType() == EntityType.FALLING_BLOCK) {
			FallingBlock f = (FallingBlock) e.getEntity();
			if (f.getMaterial() == Material.CARPET
					&& f.getBlockData() == (byte) 0) {
				f.setDropItem(false);
				f.getWorld().playSound(f.getLocation(), Sound.EXPLODE, 1F, 1F);
				f.getWorld()
						.playEffect(f.getLocation(), Effect.ENDER_SIGNAL, 1);
				e.setCancelled(true);
			}
		}
	}
}
