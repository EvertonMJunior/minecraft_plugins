package me.everton.pvp.kits.habilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Gladiator implements Listener {
	public boolean generateGlass = true;
	public HashMap<Player, Location> oldl = new HashMap<>();
	public static HashMap<Player, Player> fighting = new HashMap<>();
	public HashMap<Integer, ArrayList<Location>> blocks = new HashMap<>();
	public HashMap<String, Location> localizacao = new HashMap<>();
	public HashMap<Location, Block> bloco = new HashMap<>();
	public HashMap<Integer, Player[]> players = new HashMap<>();
	public HashMap<Player, Integer> tasks = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();
	public static HashMap<String, Integer> cd = new HashMap<>();
	int nextID = 0;
	public int id1;
	public int id2;
	ArrayList<Player> tempo = new ArrayList<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnGladnoKit(PlayerInteractEntityEvent event) {
		if ((event.getRightClicked() instanceof Player)) {
			final Player p = event.getPlayer();
			final Player r = (Player) event.getRightClicked();
			if ((p.getItemInHand().getTypeId() == 101)
					&& (KitManager.getKit(p) == KitType.GLADIATOR)) {
				
				if(!API.getGamers().contains(r)) {
					p.sendMessage("§7[§c!§7] Você não pode usar seu kit contra esse jogador!");
					return;
				}
				
				if(!API.getGamers().contains(p)) {
					p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
					return;
				}
				
				if (cd.containsKey(p.getName())) {
					p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
							+ cd.get(p.getName()) + " segundos§7!");
					return;
				}
				Location loc = new Location(p.getWorld(), p.getLocation()
						.getBlockX(), p.getLocation().getBlockY() + 70, p
						.getLocation().getBlockZ());
				localizacao.put(p.getName(), loc);
				localizacao.put(r.getName(), loc);
				Location loc2 = new Location(p.getWorld(), p.getLocation()
						.getBlockX() + 8, p.getLocation().getBlockY() + 73, p
						.getLocation().getBlockZ() + 8);
				Location loc3 = new Location(p.getWorld(), p.getLocation()
						.getBlockX() - 8, p.getLocation().getBlockY() + 73, p
						.getLocation().getBlockZ() - 8);
				if ((fighting.containsKey(p)) || (fighting.containsKey(r))) {
					event.setCancelled(true);
					p.sendMessage(ChatColor.RED + "Você já esta numa arena de Gladiator!");
					return;
				}
				Integer currentID = Integer.valueOf(nextID);
				nextID += 1;
				ArrayList<Player> list = new ArrayList<>();
				list.add(p);
				list.add(r);
				players.put(currentID,
						(Player[]) list.toArray(new Player[1]));
				oldl.put(p, p.getLocation());
				oldl.put(r, r.getLocation());
				tempo.add(p);
				if (generateGlass) {
					List<Location> cuboid = new ArrayList<>();
					cuboid.clear();
					int bY;
					for (int bX = -10; bX <= 10; bX++) {
						for (int bZ = -10; bZ <= 10; bZ++) {
							for (bY = -1; bY <= 10; bY++) {
								Block b = loc.clone().add(bX, bY, bZ)
										.getBlock();
								if (!b.isEmpty()) {
									event.setCancelled(true);
									p.sendMessage(ChatColor.RED
											+ "Você nao pode criar sua arena aqui!");
									return;
								}
								if (bY == 10) {
									cuboid.add(loc.clone().add(bX, bY, bZ));
								} else if (bY == -1) {
									cuboid.add(loc.clone().add(bX, bY, bZ));
								} else if ((bX == -10) || (bZ == -10)
										|| (bX == 10) || (bZ == 10)) {
									cuboid.add(loc.clone().add(bX, bY, bZ));
								}
							}
						}
					}
					for (Location loc1 : cuboid) {
						loc1.getBlock().setData(DyeColor.WHITE.getData());
						loc1.getBlock().setType(Material.STAINED_GLASS);
					}
					loc2.setYaw(135.0F);
					p.teleport(loc2);
					loc3.setYaw(-45.0F);
					r.teleport(loc3);
					fighting.put(p, r);
					fighting.put(r, p);

				}
				id2 = Bukkit.getScheduler().scheduleSyncDelayedTask(
						Main.getPlugin(), new Runnable() {
							public void run() {
								if ((Gladiator.fighting.containsKey(p))
										&& (Gladiator.fighting.get(p).getName()
												.equalsIgnoreCase(r.getName()))
										&& (Gladiator.fighting.containsKey(r))) {
									Gladiator.fighting.get(r).getName()
											.equalsIgnoreCase(p.getName());
								}
							}
						}, 1800L);
				id1 = Bukkit.getScheduler().scheduleSyncDelayedTask(
						Main.getPlugin(), new Runnable() {
							public void run() {
								if (fighting.containsKey(p)) {
									Player k = fighting.get(p);
									k.sendMessage("§7[§c!§7] Como nao houve nenhum vencedor, você foi teleportado ao local de origem!");
									p.sendMessage("§7[§c!§7] Como nao houve nenhum vencedor, você foi teleportado ao local de origem!");
									Location old = (Location) oldl.get(p);
									Location oldk = (Location) oldl.get(k);
									k.teleport(old);
									p.teleport(oldk);
									p.setFireTicks(1);
									k.setFireTicks(1);
									Bukkit.getScheduler().cancelTask(id1);
									Bukkit.getScheduler().cancelTask(id2);
									fighting.remove(k);
									fighting.remove(p);
									Location loc = (Location) localizacao.get(p
											.getName());
									List<Location> cuboid = new ArrayList<>();
									cuboid.clear();
									int bY;
									for (int bX = -10; bX <= 10; bX++) {
										for (int bZ = -10; bZ <= 10; bZ++) {
											for (bY = -1; bY <= 10; bY++) {
												if (bY == 10) {
													cuboid.add(loc.clone().add(
															bX, bY, bZ));
												} else if (bY == -1) {
													cuboid.add(loc.clone().add(
															bX, bY, bZ));
												} else if ((bX == -10)
														|| (bZ == -10)
														|| (bX == 10)
														|| (bZ == 10)) {
													cuboid.add(loc.clone().add(
															bX, bY, bZ));
												}
											}
										}
									}
									for (Location loc1 : cuboid) {
										loc1.getBlock().setType(Material.AIR);
										if (bloco.containsKey(loc1)) {
											((Block) bloco.get(loc1))
													.setType(Material.AIR);
										}
									}
									return;
								}
							}
						}, 2000L);
				API.startCd(p, 15, 0, cd, "§7[§c!§7] O §c§lcooldown §7acabou!");
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteractGlad(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getTypeId() == 101) {
			e.setCancelled(true);
			p.updateInventory();
			return;
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlyaerInteract(final PlayerInteractEvent e) {
		if ((e.getAction() == Action.LEFT_CLICK_BLOCK)
				&& (e.getClickedBlock().getType() == Material.STAINED_GLASS)
				&& (e.getPlayer().getGameMode() != GameMode.CREATIVE)
				&& (fighting.containsKey(e.getPlayer()))) {
			e.setCancelled(true);
			e.getClickedBlock().setType(Material.BEDROCK);
			Bukkit.getServer().getScheduler()
					.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						public void run() {
							if (Gladiator.fighting.containsKey(e.getPlayer())) {
								e.getClickedBlock().setType(
										Material.STAINED_GLASS);
							}
						}
					}, 30L);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(final BlockBreakEvent e) {
		if ((e.getBlock().getType() == Material.STAINED_GLASS)
				&& (e.getPlayer().getGameMode() != GameMode.CREATIVE)
				&& (fighting.containsKey(e.getPlayer()))) {
			e.setCancelled(true);
			e.getBlock().setType(Material.BEDROCK);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(),
					new Runnable() {
						public void run() {
							if ((e.getPlayer().getGameMode() != GameMode.CREATIVE)
									&& (Gladiator.fighting.containsKey(e
											.getPlayer()))) {
								e.getBlock().setType(Material.STAINED_GLASS);
							}
						}
					}, 30L);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlyaerInteract2(final PlayerInteractEvent e) {
		if ((e.getAction() == Action.LEFT_CLICK_BLOCK)
				&& (e.getClickedBlock().getType() == Material.STAINED_GLASS)
				&& (e.getPlayer().getGameMode() != GameMode.CREATIVE)
				&& (fighting.containsKey(e.getPlayer()))) {
			e.setCancelled(true);
			e.getClickedBlock().setType(Material.BEDROCK);
			Bukkit.getServer().getScheduler()
					.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						@SuppressWarnings("deprecation")
						public void run() {
							if (Gladiator.fighting.containsKey(e.getPlayer())) {
								e.getClickedBlock().setType(
										Material.STAINED_GLASS);
								e.getClickedBlock().setData(
										DyeColor.WHITE.getData());
							}
						}
					}, 30L);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak2(final BlockBreakEvent e) {
		if ((e.getBlock().getType() == Material.STAINED_GLASS)
				&& (e.getPlayer().getGameMode() != GameMode.CREATIVE)
				&& (fighting.containsKey(e.getPlayer()))) {
			e.setCancelled(true);
			e.getBlock().setType(Material.BEDROCK);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(),
					new Runnable() {
						public void run() {
							if ((e.getPlayer().getGameMode() != GameMode.CREATIVE)
									&& (Gladiator.fighting.containsKey(e
											.getPlayer()))) {
								e.getBlock().setType(Material.STAINED_GLASS);
							}
						}
					}, 30L);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLeft(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (fighting.containsKey(p)) {
			Player k = fighting.get(p);
			k.sendMessage("§7[§c!§7] §a"
					+ ChatColor.stripColor(p.getPlayerListName()) + " deslogou!");
			Location old = (Location) oldl.get(p);
			k.teleport(old);
			k.setFireTicks(1);
			Bukkit.getScheduler().cancelTask(id1);
			Bukkit.getScheduler().cancelTask(id2);
			k.addPotionEffect(new PotionEffect(
					PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
			fighting.remove(k);
			fighting.remove(p);
			Location loc = (Location) localizacao.get(p.getName());
			List<Location> cuboid = new ArrayList<>();
			cuboid.clear();
			int bY;
			for (int bX = -10; bX <= 10; bX++) {
				for (int bZ = -10; bZ <= 10; bZ++) {
					for (bY = -1; bY <= 10; bY++) {
						if (bY == 10) {
							cuboid.add(loc.clone().add(bX, bY, bZ));
						} else if (bY == -1) {
							cuboid.add(loc.clone().add(bX, bY, bZ));
						} else if ((bX == -10) || (bZ == -10) || (bX == 10)
								|| (bZ == 10)) {
							cuboid.add(loc.clone().add(bX, bY, bZ));
						}
					}
				}
			}
			for (Location loc1 : cuboid) {
				loc1.getBlock().setType(Material.AIR);
				if (bloco.containsKey(loc1)) {
					((Block) bloco.get(loc1)).setType(Material.AIR);
				}
			}
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeathGlad(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (fighting.containsKey(p)) {
			Player k = fighting.get(p);
			Location old = (Location) oldl.get(p);
			k.teleport(old);
			p.setFireTicks(1);
			k.setFireTicks(1);
			Bukkit.getScheduler().cancelTask(id1);
			Bukkit.getScheduler().cancelTask(id2);
			k.addPotionEffect(new PotionEffect(
					PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
			fighting.remove(k);
			fighting.remove(p);
			Location loc = (Location) localizacao.get(p.getName());
			List<Location> cuboid = new ArrayList<>();
			cuboid.clear();
			int bY;
			for (int bX = -10; bX <= 10; bX++) {
				for (int bZ = -10; bZ <= 10; bZ++) {
					for (bY = -1; bY <= 10; bY++) {
						if (bY == 10) {
							cuboid.add(loc.clone().add(bX, bY, bZ));
						} else if (bY == -1) {
							cuboid.add(loc.clone().add(bX, bY, bZ));
						} else if ((bX == -10) || (bZ == -10) || (bX == 10)
								|| (bZ == 10)) {
							cuboid.add(loc.clone().add(bX, bY, bZ));
						}
					}
				}
			}
			for (Location loc1 : cuboid) {
				loc1.getBlock().setType(Material.AIR);
				if (bloco.containsKey(loc1)) {
					((Block) bloco.get(loc1)).setType(Material.AIR);
				}
			}
			return;
		}
	}
}
