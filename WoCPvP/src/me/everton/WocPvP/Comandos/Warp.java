package me.everton.WocPvP.Comandos;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.WocPvP.Main;
import me.everton.WocPvP.HG.HG;
import me.everton.WocPvP.Listeners.KitListener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Warp implements Listener, CommandExecutor {

	public static ArrayList<Player> cd = new ArrayList<>();
	public static HashMap<Player, Integer> task = new HashMap<>();

	public static void tpcoolDown(final Player p, final String msg,
			int seconds, final String tp, final Boolean rt,
			final Boolean pvp, final Boolean hg, final Boolean fps,
			final Boolean lava) {
		if (Spec.specs.contains(p)) {
			p.teleport(Main.loc(tp, p));
			p.sendMessage(msg);
		} else {
			if (!cd.contains(p)) {
				if ((!p.hasPermission("tag.vip"))
						|| (!p.hasPermission("tag.pro"))
						|| (!p.hasPermission("tag.trial"))
						|| (!p.hasPermission("tag.mod"))
						|| (!p.hasPermission("tag.builder"))
						|| (!p.hasPermission("tag.admin"))
						|| (!p.hasPermission("tag.dono"))
						|| (!p.hasPermission("*"))) {
					p.sendMessage("§aAguarde 5 segundos sem mover-se!");
					cd.add(p);
					int coold = Main.sh.scheduleSyncDelayedTask(
							Main.getPlugin(), new Runnable() {

								@Override
								public void run() {
									p.sendMessage(msg);
									p.teleport(Main.loc(tp, p));

									if (hg) {
										if (!HG.hg.contains(p)) {
											if (!Spec.specs.contains(p)) {
												UltraKits.Main.resetKit(p);
												p.getInventory().clear();
												p.getInventory()
														.setArmorContents(null);
												ItemStack fogo = new ItemStack(
														Material.BLAZE_POWDER);
												ItemMeta metafogo = fogo
														.getItemMeta();
												metafogo.setDisplayName(" ");
												fogo.setItemMeta(metafogo);

												ItemStack kits = new ItemStack(
														Material.ENDER_CHEST);
												ItemMeta metakits = kits
														.getItemMeta();
												metakits.setDisplayName("§b§lKits HG");
												kits.setItemMeta(metakits);
												p.getInventory().setItem(4,
														kits);
												p.getInventory().setItem(3,
														fogo);
												p.getInventory().setItem(5,
														fogo);

												for (Player hgs : HG.hg) {
													hgs.sendMessage("§7[§a+§7] "
															+ p.getName()
															+ " entrou no Warp HG");
												}
												HG.hg.add(p);
											}
										} else {
											p.getInventory().clear();
											p.getInventory().setArmorContents(
													null);
											HG.hg.remove(p);
											HG.hgin.remove(p);
											HG.hgcd.remove(p);
											p.teleport(Main.loc("spawn", p));
											KitListener.hgkit.remove(p);
											UltraKits.Main.resetKit(p);
											Main.spawnItens(p);
											HG.hgin.remove(p);
											KitListener.hgkit.remove(p);
											for (Player hg : HG.hg) {
												hg.sendMessage("§7[§c-§7] "
														+ p.getName()
														+ " saiu do Warp HG");
											}
										}
									}

									if (lava) {
										p.getInventory().clear();
										p.getInventory().setArmorContents(null);
										for (int i = 0; i < 36; i++) {
											p.getInventory()
													.setItem(
															i,
															new ItemStack(
																	Material.MUSHROOM_SOUP));
										}
										p.getInventory()
												.setChestplate(
														new ItemStack(
																Material.LEATHER_CHESTPLATE));
										p.getInventory()
												.setBoots(
														new ItemStack(
																Material.CHAINMAIL_BOOTS));
										p.getInventory().setItem(
												0,
												new ItemStack(
														Material.STONE_SWORD));
										p.getInventory()
												.setItem(
														14,
														new ItemStack(
																Material.BOWL,
																64));
										p.getInventory().setItem(
												15,
												new ItemStack(
														Material.RED_MUSHROOM,
														64));
										p.getInventory()
												.setItem(
														16,
														new ItemStack(
																Material.BROWN_MUSHROOM,
																64));
									}

									if (rt) {
										UltraKits.Main.resetKit(p);
									}
									if (pvp) {
										UltraKits.KitItems.kitPvP(p);
									}
									cd.remove(p);
									task.remove(p);
								}
							}, seconds * 20);
					task.put(p, coold);
				} else {
					p.sendMessage(msg);
					p.teleport(Main.loc(tp, p));

					if (hg) {
						if (!HG.hg.contains(p)) {
							if (!Spec.specs.contains(p)) {
								UltraKits.Main.resetKit(p);
								p.getInventory().clear();
								p.getInventory().setArmorContents(null);
								ItemStack fogo = new ItemStack(
										Material.BLAZE_POWDER);
								ItemMeta metafogo = fogo.getItemMeta();
								metafogo.setDisplayName(" ");
								fogo.setItemMeta(metafogo);

								ItemStack kits = new ItemStack(
										Material.ENDER_CHEST);
								ItemMeta metakits = kits.getItemMeta();
								metakits.setDisplayName("§b§lKits HG");
								kits.setItemMeta(metakits);
								p.getInventory().setItem(4, kits);
								p.getInventory().setItem(3, fogo);
								p.getInventory().setItem(5, fogo);

								for (Player hgs : HG.hg) {
									hgs.sendMessage("§7[§a+§7] " + p.getName()
											+ " entrou no Warp HG");
								}
								HG.hg.add(p);
							}
						} else {
							p.getInventory().clear();
							p.getInventory().setArmorContents(null);
							HG.hg.remove(p);
							HG.hgin.remove(p);
							HG.hgcd.remove(p);
							p.teleport(Main.loc("spawn", p));
							KitListener.hgkit.remove(p);
							UltraKits.Main.resetKit(p);
							Main.spawnItens(p);
							HG.hgin.remove(p);
							KitListener.hgkit.remove(p);
							for (Player hgz : HG.hg) {
								hgz.sendMessage("§7[§c-§7] " + p.getName()
										+ " saiu do Warp HG");
							}
						}
					}

					if (rt) {
						UltraKits.Main.resetKit(p);
					}
					if (pvp) {
						UltraKits.KitItems.kitPvP(p);
					}
					cd.remove(p);
					task.remove(p);
				}
			} else {
				p.sendMessage("§cAguarde!");
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("warps")) {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("set")) {
					Main.setLoc((Player) sender, args[1]);
					return true;
				}
				return true;
			}
			MenuWarp((Player) sender, "principal");
		}
		return false;
	}

	public static void MenuWarp(Player p, String tipo) {
		p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
		Inventory warps = Bukkit.createInventory(p, 27, "§4Warps");
		Inventory arenas = Bukkit.createInventory(p, 27, "§4Warps - Arenas");
		Inventory gerais = Bukkit.createInventory(p, 27, "§4Warps - Gerais");
		if (tipo.equalsIgnoreCase("principal")) {
			ItemStack glass = new ItemStack(Material.THIN_GLASS);
			ItemMeta spameta2 = glass.getItemMeta();
			spameta2.setDisplayName(" ");
			glass.setItemMeta(spameta2);

			ItemStack arena = new ItemStack(Material.PAPER);
			ItemMeta ameta2 = arena.getItemMeta();
			ameta2.setDisplayName("§b§lArenas");
			arena.setItemMeta(ameta2);

			ItemStack evento = new ItemStack(Material.CAKE);
			ItemMeta emeta2 = evento.getItemMeta();
			emeta2.setDisplayName("§b§lGerais");
			evento.setItemMeta(emeta2);

			ItemStack spawn = new ItemStack(Material.REDSTONE);
			ItemMeta smeta2 = spawn.getItemMeta();
			smeta2.setDisplayName("§c§lSpawn");
			spawn.setItemMeta(smeta2);

			for (int i = 0; i < warps.getContents().length; i++) {
				warps.setItem(i, glass);
			}

			warps.setItem(11, arena);
			warps.setItem(15, evento);
			warps.setItem(8, spawn);

			p.openInventory(warps);
		} else if (tipo.equalsIgnoreCase("arenas")) {
			ItemStack glass = new ItemStack(Material.THIN_GLASS);
			ItemMeta spameta2 = glass.getItemMeta();
			spameta2.setDisplayName(" ");
			glass.setItemMeta(spameta2);

			ItemStack m = new ItemStack(Material.QUARTZ_BLOCK);
			ItemMeta mmeta = m.getItemMeta();
			mmeta.setDisplayName("§aModern");
			m.setItemMeta(mmeta);

			ItemStack ar = new ItemStack(Material.SANDSTONE);
			ItemMeta armeta = ar.getItemMeta();
			armeta.setDisplayName("§aColiseu");
			ar.setItemMeta(armeta);

			ItemStack fp = new ItemStack(Material.GLASS);
			ItemMeta fpmeta = fp.getItemMeta();
			fpmeta.setDisplayName("§aFPS");
			fp.setItemMeta(fpmeta);

			ItemStack ap = new ItemStack(Material.ROTTEN_FLESH);
			ItemMeta apmeta = ap.getItemMeta();
			apmeta.setDisplayName("§aApocalipse");
			ap.setItemMeta(apmeta);

			ItemStack sn = new ItemStack(Material.SNOW_BALL);
			ItemMeta snmeta = sn.getItemMeta();
			snmeta.setDisplayName("§aSnow Lands");
			sn.setItemMeta(snmeta);

			ItemStack hg = new ItemStack(Material.BOWL);
			ItemMeta hgmeta = hg.getItemMeta();
			hgmeta.setDisplayName("§aEarlyHG");
			hg.setItemMeta(hgmeta);

			ItemStack voltar = new ItemStack(Material.CARPET, 1, (byte) 5);
			ItemMeta vm = voltar.getItemMeta();
			vm.setDisplayName("§2Voltar");
			voltar.setItemMeta(vm);

			for (int i = 0; i < warps.getContents().length; i++) {
				arenas.setItem(i, glass);
			}

			arenas.setItem(10, ar);
			arenas.setItem(12, fp);
			arenas.setItem(14, sn);
			arenas.setItem(16, hg);
			arenas.setItem(0, voltar);

			p.openInventory(arenas);
		} else if (tipo.equalsIgnoreCase("gerais")) {

			ItemStack glass = new ItemStack(Material.THIN_GLASS);
			ItemMeta spameta2 = glass.getItemMeta();
			spameta2.setDisplayName(" ");
			glass.setItemMeta(spameta2);

			ItemStack v = new ItemStack(Material.BLAZE_ROD);
			ItemMeta vmeta = v.getItemMeta();
			vmeta.setDisplayName("§a1v1");
			v.setItemMeta(vmeta);

			ItemStack lc = new ItemStack(Material.LAVA_BUCKET);
			ItemMeta lcmeta = lc.getItemMeta();
			lcmeta.setDisplayName("§aLava Challenge");
			lc.setItemMeta(lcmeta);

			ItemStack tx = new ItemStack(Material.FIREWORK);
			ItemMeta txmeta = tx.getItemMeta();
			txmeta.setDisplayName("§aTexturas");
			tx.setItemMeta(txmeta);

			ItemStack ml = new ItemStack(Material.CLAY);
			ItemMeta mlmeta = ml.getItemMeta();
			mlmeta.setDisplayName("§aParkour");
			ml.setItemMeta(mlmeta);

			ItemStack voltar = new ItemStack(Material.CARPET, 1, (byte) 5);
			ItemMeta vm = voltar.getItemMeta();
			vm.setDisplayName("§2Voltar");
			voltar.setItemMeta(vm);

			for (int i = 0; i < warps.getContents().length; i++) {
				gerais.setItem(i, glass);
			}

			gerais.setItem(10, v);
			gerais.setItem(12, lc);
			gerais.setItem(14, tx);
			gerais.setItem(16, ml);
			gerais.setItem(0, voltar);
			p.openInventory(gerais);
		}
	}

	@EventHandler
	public void aoClickInv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName().equalsIgnoreCase("§4Warps")) {
			e.setCancelled(true);
			if (e.getSlot() == 11) {
				e.setCancelled(true);
				MenuWarp((Player) e.getWhoClicked(), "arenas");
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
			} else if (e.getSlot() == 15) {
				e.setCancelled(true);
				MenuWarp((Player) e.getWhoClicked(), "gerais");
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
			} else if (e.getSlot() == 8) {
				e.setCancelled(true);
				if (Spec.specs.contains(p)) {
					if (Spec.specs.contains(p)) {
						p.teleport(Main.loc("spawn", p));
					}
				} else if (!Spec.specs.contains(p)) {
					p.chat("/spawn");
				}
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
			}
		} else if (e.getInventory().getName()
				.equalsIgnoreCase("§4Warps - Arenas")) {
			e.setCancelled(true);
			if (e.getSlot() == 10) {
				e.setCancelled(true);
				p.closeInventory(); tpcoolDown(p, "§aVocê foi teleportado à arena Coliseu!", 5,
						"coliseu", true, true, false, false, false);
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
				p.closeInventory();
			} else if (e.getSlot() == 12) {
				e.setCancelled(true);
				p.closeInventory(); tpcoolDown(p, "§aVocê foi teleportado à arena FPS!", 5,
						"fps", true, true, false, false, false);
				if (!Fps.fps.contains(p)) {
					Fps.fps.add(p);
				}
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
				p.closeInventory();
			} else if (e.getSlot() == 14) {
				e.setCancelled(true);
				p.closeInventory(); tpcoolDown(p, "§aVocê foi teleportado à arena SnowLands!", 5,
						"snowlands", true, false, false, false, false);
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
				p.closeInventory();
			} else if (e.getSlot() == 16) {
				e.setCancelled(true);
				p.chat("/hg");
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
				p.closeInventory();
			} else if (e.getSlot() == 0) {
				e.setCancelled(true);
				MenuWarp((Player) e.getWhoClicked(), "principal");
			}
		} else if (e.getInventory().getName()
				.equalsIgnoreCase("§4Warps - Gerais")) {
			e.setCancelled(true);
			if (e.getSlot() == 10) {
				e.setCancelled(true);
				p.chat("/1v1");
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
				p.closeInventory();
			} else if (e.getSlot() == 12) {
				e.setCancelled(true);
				p.closeInventory(); tpcoolDown(p, "§aVocê foi teleportado ao Lava Challenge!", 5,
						"lavachallenge", false, false, false, false, true);
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
				p.closeInventory();
			} else if (e.getSlot() == 14) {
				e.setCancelled(true);
				p.closeInventory(); tpcoolDown(p, "§aVocê foi teleportado ao warp Texturas!", 5, "texturas", true, true, false, false, false);
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
				p.closeInventory();
			} else if (e.getSlot() == 16) {
				e.setCancelled(true);
				p.closeInventory(); tpcoolDown(p, "§aVocê foi teleportado ao Parkour!", 5, "parkour", true, true, false, false, false);
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
				p.closeInventory();
			} else if (e.getSlot() == 0) {
				e.setCancelled(true);
				MenuWarp((Player) e.getWhoClicked(), "principal");
			}
		}
	}

	@EventHandler
	public void aoClicar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.PAPER) {
			if ((e.getAction() == Action.RIGHT_CLICK_AIR)
					|| (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				MenuWarp(p, "principal");
			}
		} else if (p.getItemInHand().getType() == Material.EMERALD) {
			if ((e.getAction() == Action.RIGHT_CLICK_AIR)
					|| (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				p.chat("/loja");
			}
		}
	}

	@EventHandler
	public void aoMover(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (cd.contains(p)) {
			if ((e.getFrom().getX() != e.getTo().getX())
					|| (e.getFrom().getY() != e.getTo().getY())
					|| (e.getFrom().getZ() != e.getTo().getZ())) {
				p.sendMessage("§cTeleporte cancelado!");
				Main.sh.cancelTask(task.get(p));
				cd.remove(p);
				task.remove(p);
			}
		}
	}
}
