package me.everton.esw.Kits.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.everton.esw.Main;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;

public class EnderMan implements Listener{

	public static ArrayList<Player> jump = new ArrayList<>();
	public static ArrayList<Player> jump1 = new ArrayList<>();
	public static ArrayList<Player> usandokit = new ArrayList<>();
	public static HashMap<Player, Integer> cd = new HashMap<>();
	public static HashMap<Player, Integer> task = new HashMap<>();
	
	public static Configuration cf = Main.getPlugin().getConfig();

	public static void addKit(Player p) {
		usandokit.add(p);
	}

	public static void removeKit(Player p) {
		usandokit.remove(p);
		jump.remove(p);
		jump1.remove(p);
	}

	public static boolean usandoKit(Player p) {
		if (usandokit.contains(p)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void aoHitProj(ProjectileHitEvent e) {
		Projectile pj = e.getEntity();
		ProjectileSource ps = pj.getShooter();
		if (pj instanceof EnderPearl && ps instanceof Player) {
			if (usandoKit((Player) ps)) {
				int blocos = cf.getInt("Blocos_Para_Cima_Ender");
				Double radius = cf.getDouble("Radius_Aviso_Ender");

				final Player p = (Player) ps;

				final Location ender = new Location(
						pj.getLocation().getWorld(), pj.getLocation().getX(),
						pj.getLocation().getY() + blocos, pj.getLocation()
								.getZ(), pj.getLocation().getYaw(), pj
								.getLocation().getPitch());
				if (cf.getInt("Tempo_Cooldown_Enderman") != 0) {
					p.sendMessage(cf
							.getString("Aguarde_CoolDown_Enderman")
							.replace("&", "§")
							.replace(
									"%TEMPO%",
									String.valueOf(cf.getInt(
											"Tempo_Cooldown_Enderman"))));
				}

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin,
						new Runnable() {
							@Override
							public void run() {
								p.teleport(ender.setDirection(p.getLocation()
										.getDirection()));
								jump.add(p);
							}
						}, cf.getInt("Tempo_Cooldown_Enderman") * 20L);

				for (Entity ent : pj.getNearbyEntities(radius, radius, radius)) {
					if ((ent instanceof Player)) {
						Player plr = (Player) ent;
						if (!cf.getBoolean(
								"Efeitos_e_Som_Onde_Foi_Jogada_Ender")) {
							plr.playSound(plr.getLocation(), Sound.ANVIL_LAND,
									3F, 1F);
							plr.playEffect(plr.getLocation(),
									Effect.MOBSPAWNER_FLAMES, 20);
						} else {
							pj.getWorld().playEffect(pj.getLocation(),
									Effect.MOBSPAWNER_FLAMES, 20);
							pj.getWorld().playSound(pj.getLocation(),
									Sound.ANVIL_LAND, 3F, 1F);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void aoTp(PlayerTeleportEvent e) {
		if (e.getCause().equals(TeleportCause.ENDER_PEARL)) {
			if (usandokit.contains(e.getPlayer())) {
				e.setCancelled(true);
			}
		}
	}

	private void addEnderpearl(Player p) {

		ItemStack ender = new ItemStack(Material.ENDER_PEARL, 1);
		ItemMeta em = ender.getItemMeta();
		em.setDisplayName(cf.getString("Nome_Ender").replace("&", "§"));
		ender.setItemMeta(em);

		ItemStack pearls = p.getItemInHand();
		if ((pearls != null) && (pearls.getType() == Material.ENDER_PEARL)
				&& (pearls.getAmount() < 16)) {
			pearls.setAmount(pearls.getAmount() + 1);
		} else {
			ItemStack[] inv = p.getInventory().getContents();
			for (int i = 0; i < inv.length; i++) {
				if ((inv[i] != null)
						&& (inv[i].getType() == Material.ENDER_PEARL)
						&& (inv[i].getAmount() < 16)) {
					inv[i].setAmount(inv[i].getAmount() + 1);
					return;
				}
			}
			if (p.getItemInHand() == null) {
				p.setItemInHand(new ItemStack(ender));
			} else {
				p.getInventory().addItem(
						new ItemStack[] { new ItemStack(ender) });
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void aoLaunchProj(ProjectileLaunchEvent e) {
		Projectile pj = e.getEntity();
		ProjectileSource ps = pj.getShooter();
		if (pj instanceof EnderPearl && ps instanceof Player) {
			if (usandoKit((Player) ps)) {
				final Player p = (Player) ps;
				if (task.containsKey(p)) {
					p.sendMessage(cf.getString("Aguarde_Lancar_Ender")
							.replace("&", "§")
							.replace("%TEMPO%", String.valueOf(cd.get(p))));
					e.setCancelled(true);
					ItemStack ender = new ItemStack(Material.ENDER_PEARL,
							cf.getInt("Quantidade_de_EnderPearls"));
					ItemMeta em = ender.getItemMeta();
					em.setDisplayName(cf.getString("Nome_Ender")
							.replace("&", "§"));
					ender.setItemMeta(em);
					addEnderpearl(p);
					p.updateInventory();
				} else {
					jump1.add(p);
					int coolDown = Bukkit.getScheduler()
							.scheduleSyncRepeatingTask(Main.plugin,
									new Runnable() {
										int tempo = cf.getInt(
												"Tempo_Cooldown_Enderman");

										public void run() {
											if (tempo > 0) {
												tempo--;
												cd.put(p, tempo);
											}
											if (tempo == 0) {
												Bukkit.getScheduler()
														.cancelTask(task.get(p));
												cd.remove(p);
												task.remove(p);
											}
										}
									}, 0, 20);
					task.put(p, coolDown);
				}
			}
		}
	}

	@EventHandler
	public void onFall(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			if (usandoKit((Player) e.getEntity())) {
				Player p = (Player) e.getEntity();
				if ((e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
						&& (jump.contains(p))) {
					e.setCancelled(true);
					jump.remove(p);
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		Player p = (Player) e.getEntity();
		if (usandoKit((Player) e.getEntity())) {
			if (jump1.contains(p)) {
				if (e.getEntityType() != EntityType.PLAYER) {
					return;
				}
				if (e.getDamager().getType().equals(EntityType.ENDER_PEARL)) {
					e.setCancelled(true);
				}
				jump1.remove(p);
			}
		}
	}

	@EventHandler
	public void aoMorrer(PlayerDeathEvent e) {
		Player p = e.getEntity().getPlayer();
		Player killer = e.getEntity().getKiller();
		if (usandoKit(killer)) {
			double chance = cf.getDouble("dropChance",
					cf.getInt("Chance_Drop_Ender")) / 100;
			Random r = new Random();
			if (r.nextDouble() <= chance) {
				ItemStack ender = new ItemStack(Material.ENDER_PEARL);
				ItemMeta em = ender.getItemMeta();
				em.setDisplayName(cf.getString("Nome_Ender").replace(
						"&", "§"));
				ender.setItemMeta(em);
				e.getDrops().add(ender);
			}
		}
		if (usandoKit(p)) {
			removeKit(p);
		}
	}
}
