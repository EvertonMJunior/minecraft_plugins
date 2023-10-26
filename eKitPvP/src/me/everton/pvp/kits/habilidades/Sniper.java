package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Sniper implements Listener {
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();
	public static ArrayList<String> zoom = new ArrayList<>();

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (KitManager.getKit(p) == KitType.SNIPER) {
			if (KitManager.getKit(p) == KitType.SNIPER
					&& e.getAction().name().contains("LEFT")
					|| e.getAction().name().contains("RIGHT")
					&& KitManager.isWithKitItemInHand(p)) {
				if (!API.getGamers().contains(p)) {
					p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
					return;
				}
			}
			if (e.getAction().name().contains("RIGHT")) {
				if (KitManager.isWithKitItemInHand(p)) {
					if (cd.containsKey(p.getName())) {
						p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
								+ cd.get(p.getName()) + " segundos§7!");
						return;
					}

					if (zoom.contains(p.getName())) {
						p.removePotionEffect(PotionEffectType.SLOW);
						zoom.remove(p.getName());
						p.sendMessage("§7[§c!§7] Zoom desativado!");
					}
					API.startCd(p, 5, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
					Vector Sniper = p.getLocation().getDirection().normalize()
							.multiply(100);
					Arrow SniperH = (Arrow) p.launchProjectile(Arrow.class);
					SniperH.setVelocity(Sniper);
					SniperH.setMetadata("Sniper",
							new FixedMetadataValue(Main.getPlugin(), true));

					Location pegou = p.getEyeLocation();
					BlockIterator Sniperz = new BlockIterator(pegou, 0.0D, 100);

					while (Sniperz.hasNext()) {
						Location Sniper2 = Sniperz.next().getLocation();

						p.getLocation()
								.getWorld()
								.playEffect(Sniper2, Effect.MOBSPAWNER_FLAMES,
										55);
						p.getWorld().playSound(p.getLocation(),
								Sound.DIG_GRASS, 1F, 1F);
					}

				}
			} else if (e.getAction().name().contains("LEFT")) {
				if (KitManager.isWithKitItemInHand(p)) {
					if (zoom.contains(p.getName())) {
						p.removePotionEffect(PotionEffectType.SLOW);
						zoom.remove(p.getName());
						p.sendMessage("§7[§c!§7] Zoom desativado!");
						p.playSound(p.getLocation(), Sound.CLICK, 3F, 3F);
					} else {
						if (cd.containsKey(p.getName())) {
							p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
									+ cd.get(p.getName()) + " segundos§7!");
							return;
						}
						p.addPotionEffect(new PotionEffect(
								PotionEffectType.SLOW, 1000000, 255));
						zoom.add(p.getName());
						p.sendMessage("§7[§c!§7] Zoom ativado!");
						p.playSound(p.getLocation(), Sound.CLICK, 3F, 3F);
					}
				}
			}
		}
	}

	@EventHandler
	public void zoomCheckMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (zoom.contains(p.getName())) {
			if (!KitManager.isWithKitItemInHand(p)) {
				zoom.remove(p.getName());
				p.sendMessage("§7[§c!§7] Zoom desativado!");
				p.playSound(p.getLocation(), Sound.CLICK, 3F, 3F);
				p.removePotionEffect(PotionEffectType.SLOW);
			}
		}
	}

	@EventHandler
	public void zoomCheckHeld(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		if (zoom.contains(p.getName())) {
			if (p.getInventory().getItem(e.getNewSlot()).getType() != Material.BLAZE_ROD) {
				zoom.remove(p.getName());
				p.sendMessage("§7[§c!§7] Zoom desativado!");
				p.playSound(p.getLocation(), Sound.CLICK, 3F, 3F);
				p.removePotionEffect(PotionEffectType.SLOW);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void EDE(EntityDamageByEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}

		if ((e.getDamager() instanceof Arrow)) {
			Arrow Tomou = (Arrow) e.getDamager();
			if (Tomou.hasMetadata("Sniper")) {
				if (e.getEntity() instanceof Player) {
					Player p = (Player) e.getEntity();
					Player s = (Player) Tomou.getShooter();
					if (!API.getGamers().contains(p)) {
						return;
					}
					p.sendMessage("§7[§c!§7] Fosses atingido por um tiro de "
							+ s.getName());
				}
				e.setDamage(7.0D);
			}
		}
	}
}
