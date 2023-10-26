package me.everton.pvp.cmds;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.ScoreboardManager;
import me.everton.pvp.kits.KitManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Admin implements CommandExecutor, Listener {
	public static ArrayList<String> admins = new ArrayList<>();
	public static ArrayList<String> tr = new ArrayList<>();
	public static HashMap<String, ItemStack[]> inventory = new HashMap<>();
	public static HashMap<String, ItemStack[]> armor = new HashMap<>();
	public static HashMap<String, String> rc = new HashMap<>();

	public static HashMap<String, Inventory> adminInv = new HashMap<>();

	@SuppressWarnings("deprecation")
	public static void entrarOuSair(final Player p) {
		if (admins.contains(p.getName())) {
			if (inventory.containsKey(p.getName())) {
				p.getInventory().setContents(inventory.get(p.getName()));
				p.getInventory().setArmorContents(armor.get(p.getName()));
				admins.remove(p.getName());
				inventory.remove(p.getName());
				armor.remove(p.getName());
				p.sendMessage("§cVocê saiu do modo Admin!");
				p.sendMessage("§cAgora você está visível para todos!");

				for (Player on : Bukkit.getOnlinePlayers()) {
					on.showPlayer(p);

					if (on.hasPermission("hg.admin")) {
						on.sendMessage("§7["
								+ ChatColor.stripColor(p.getPlayerListName())
								+ " saiu do modo Admin]");
					}
				}
				p.setGameMode(GameMode.SURVIVAL);
				p.setHealth(20.0D);
			}
		} else {
			API.deleteArrayList(admins, p.getName());
			API.deleteHashMapKey(inventory, p.getName());
			API.deleteHashMapKey(armor, p.getName());
			admins.add(p.getName());
			inventory.put(p.getName(), p.getInventory().getContents());
			armor.put(p.getName(), p.getInventory().getArmorContents());
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);

			p.getInventory().setItem(1,
					API.item(Material.ARROW, 1, "§c➤ §lHacks"));
			p.getInventory().setItem(
					4,
					API.item(Material.BLAZE_ROD, 1,
							"§e➤ §lInformaçoes do Jogador"));
			p.getInventory().setItem(7,
					API.item(Material.MAGMA_CREAM, 1, "§b➤ §lTroca Rápida"));

			Inventory inv = Bukkit.createInventory(p, 9, "§c➤ §lHacks");
			inv.setItem(1, API.item(Material.DIAMOND_SWORD, 1, "§6§lFF/KA"));
			inv.setItem(2, API.item(Material.MUSHROOM_SOUP, 1, "§6§lAutoSoup"));
			adminInv.put(p.getName(), inv);

			for (Player on : Bukkit.getOnlinePlayers()) {
				// SPECS

				if (on.hasPermission("hg.admin")) {
					on.sendMessage("§7["
							+ ChatColor.stripColor(p.getPlayerListName())
							+ " entrou no modo Admin]");
				} else {
					on.hidePlayer(p);
				}
			}

			p.sendMessage("§dVocê entrou no modo Admin!");
			p.sendMessage("§dAgora você está invisível para todos!");
			p.setGameMode(GameMode.CREATIVE);
			p.setHealth(20.0D);
		}
		for (Player on : Bukkit.getOnlinePlayers()) {
			ScoreboardManager.refreshSidebar(on);
		}
	}

	public static HashMap<String, ItemStack[]> inv = new HashMap<>();

	public static boolean comAs = false;

	public static void testAutosoup(final Player p, Player t) {
		inv.put(p.getName(), p.getInventory().getContents());
		t.openInventory(p.getInventory());
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				p.getInventory().clear();
				p.getInventory().setItem(2,
						new ItemStack(Material.MUSHROOM_SOUP));
				p.getInventory().setItem(11,
						new ItemStack(Material.MUSHROOM_SOUP));
			}
		}, 10L);

		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				p.getInventory().setContents(inv.get(p.getName()));
				inv.remove(p.getName());
			}
		}, 20L);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cComando in-game!");
			return true;
		}
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("admin") || label.equalsIgnoreCase("adm")) {
			if (!p.hasPermission("pvp.cmd.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			entrarOuSair(p);
		}
		if (label.equalsIgnoreCase("vis")) {
			if (!p.hasPermission("pvp.cmd.vis")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			for (Player on : Bukkit.getOnlinePlayers()) {
				on.showPlayer(p);
			}
			p.sendMessage("§7[§c!§7] Agora você está visível para todos!");
		}

		if (label.equalsIgnoreCase("invis")) {
			if (!p.hasPermission("pvp.cmd.invis")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			if (args.length == 1) {
				// INVIS PARA APENAS ALGUNS RANKS
			} else {
				for (Player on : Bukkit.getOnlinePlayers()) {
					// SPECS
					on.hidePlayer(p);
				}
				p.sendMessage("§7[§c!§7] Agora você está invisível para todos!");
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (admins.contains(p.getName())) {
			if (!e.getAction().name().contains("RIGHT")) {
				return;
			}

			if (p.getItemInHand().getType() == Material.MAGMA_CREAM) {
				e.setCancelled(true);
				p.setItemInHand(API.item(Material.SLIME_BALL, 1,
						"§b➤ §lTroca Rápida"));
				for (Player on : Bukkit.getOnlinePlayers()) {
					on.showPlayer(p);
				}
				for (int i = 0; i < 6; i++) {
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {

								@Override
								public void run() {
									p.setVelocity(new Vector());
								}
							}, i * 1L);
				}
				tr.add(p.getName());
				p.setGameMode(GameMode.SURVIVAL);
				p.setAllowFlight(true);
				p.setFlying(true);
				p.setHealth(20.0D);
				p.setFoodLevel(20);
				p.addPotionEffect(new PotionEffect(
						PotionEffectType.DAMAGE_RESISTANCE, 100000, 255));
				Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
						new Runnable() {

							@Override
							public void run() {
								for (Player on : Bukkit.getOnlinePlayers()) {
									on.hidePlayer(p);
								}
								tr.remove(p.getName());
								p.setGameMode(GameMode.CREATIVE);
								p.setFlying(true);
								p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								p.setItemInHand(API.item(Material.MAGMA_CREAM,
										1, "§b➤ §lTroca Rápida"));
							}
						}, 5L);
			}
		}
	}

	@EventHandler
	public void onInteractEnt(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			Player r = (Player) e.getRightClicked();
			if (admins.contains(p.getName())) {
				if (p.getItemInHand().getType() == Material.AIR) {
					p.openInventory(r.getInventory());
				}
				if (p.getItemInHand().getType() == Material.ARROW) {
					Hack.openInv(p, r);
				}

				if (p.getItemInHand().getType() == Material.BLAZE_ROD) {
					e.setCancelled(true);
					Inventory inv = Bukkit.createInventory(p, 27,
							"§4➤ Sobre o Jogador");
					for (int i = 0; i < inv.getSize(); i++) {
						inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1,
								" ", 5));
					}

					inv.setItem(11, API.getHead(r.getName(), 1,
							"§4" + r.getName(), new String[] { "§b➤ Vida",
									"§7> " + (((Damageable) r).getHealth()) }));
					inv.setItem(13, API.item(Material.ENDER_CHEST, 1,
							"§b➤ Kit", new String[] { "§7> "
									+ KitManager.getKit(r).getName() }));
					inv.setItem(15,
							API.item(
									Material.MUSHROOM_SOUP,
									1,
									"§b➤ Sopas",
									new String[] { "§7> "
											+ API.getAmount(r,
													Material.MUSHROOM_SOUP) }));
					p.openInventory(inv);
				}
			}
		}
	}

	@EventHandler
	public void itemHeld(PlayerItemHeldEvent e) {
		if (tr.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInvInteract(InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		if (inv == null) {
			return;
		}
		if (ChatColor.stripColor(inv.getName()).equalsIgnoreCase(
				"Jogadores Onlines")) {
			e.setCancelled(true);
			if ((e.getCurrentItem() != null)) {
				if (Bukkit.getPlayerExact(e.getCurrentItem().getItemMeta()
						.getDisplayName()) != null) {
					Player t = Bukkit.getPlayerExact(e.getCurrentItem()
							.getItemMeta().getDisplayName());
					if (t != p) {
						Location l = t.getLocation().add(0, 2, 0);
						l.setPitch(90);
						p.teleport(l);
						if (!p.getAllowFlight()) {
							p.setAllowFlight(true);
						}
						p.setFlying(true);
						p.closeInventory();
					} else {
						p.sendMessage("§7[§c!§7] Você nao pode teleportar-se à si mesmo!");
						p.closeInventory();
					}
				}
			}
		}

		if (inv.getName().equalsIgnoreCase("§c➤ §lHacks")) {
			e.setCancelled(true);
			if ((e.getCurrentItem() != null)) {
				if (Bukkit.getPlayerExact(Hack.getRC(p)) == null) {
					p.closeInventory();
					return;
				}
				final Player r = Bukkit.getPlayerExact(Hack.getRC(p));
				if (e.getCurrentItem().getType() == Material.MUSHROOM_SOUP) {
					p.closeInventory();
					p.openInventory(r.getInventory());
					p.sendMessage("§7[§c!§7] Realizando o teste, aguarde...");

					inventory.put(r.getName(), r.getInventory().getContents());
					armor.put(r.getName(), r.getInventory().getArmorContents());

					r.getInventory().clear();
					r.getInventory().setArmorContents(null);

					r.getInventory().setItem(2,
							API.item(Material.MUSHROOM_SOUP));
					r.getInventory().setItem(11,
							API.item(Material.MUSHROOM_SOUP));
					r.setHealth(6.0D);
					r.updateInventory();

					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {

								@Override
								public void run() {
									if (API.getAmount(r, Material.BOWL) >= 2) {
										p.sendMessage(" ");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage("§c"
												+ API.getSpaces(r.getName(), 19)
												+ r.getName()
												+ " está provavelmente usando AutoSoup!");
										p.sendMessage("       §cTenha certeza antes de tomar alguma providência!");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage(" ");
									} else if (API.getAmount(r, Material.BOWL) >= 1) {
										p.sendMessage(" ");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage("§c"
												+ API.getSpaces(r.getName(), 27)
												+ r.getName()
												+ " pode estar usando AutoSoup!");
										p.sendMessage("                                       §cÉ melhor checar!");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage(" ");
									} else {
										p.sendMessage(" ");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage("§c"
												+ API.getSpaces(r.getName(), 23)
												+ r.getName()
												+ " nao deve estar usando AutoSoup!");
										p.sendMessage("                                    §cCheque manualmente!");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage(" ");
									}
									p.closeInventory();

									r.setHealth(20.0D);
									r.getInventory().setContents(
											inventory.get(r.getName()));
									r.getInventory().setArmorContents(
											armor.get(r.getName()));
									armor.remove(r.getName());
									inventory.remove(r.getName());
									r.updateInventory();
								}
							}, 20L);

				} else if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
					p.closeInventory();
					p.sendMessage("§7[§c!§7] Realizando o teste, aguarde...");
					final Bat b = (Bat) r.getWorld().spawnEntity(
							r.getLocation().clone().add(0, 1.5D, 0),
							EntityType.BAT);
					b.addPotionEffect(new PotionEffect(
							PotionEffectType.INVISIBILITY, 100000, 255));
					b.setHealth(0.1D);
					final Bat b1 = (Bat) r.getWorld().spawnEntity(
							r.getLocation().clone().add(0, 1.5D, 0),
							EntityType.BAT);
					b1.addPotionEffect(new PotionEffect(
							PotionEffectType.INVISIBILITY, 100000, 255));
					b1.setHealth(0.1D);
					for (int i = 0; i < 50; i++) {
						Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
								new Runnable() {

									@Override
									public void run() {
										if (!b.isDead()) {
											b.setVelocity(new Vector());
										}

										if (!b1.isDead()) {
											b1.setVelocity(new Vector());
										}
									}
								}, i * 1L);
					}

					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {
								@Override
								public void run() {
									int killedBats = 0;
									if (b1.isDead()
											&& b1.getKiller()
													.getName()
													.equalsIgnoreCase(
															r.getName())) {
										killedBats++;
									}
									if (b.isDead()
											&& b.getKiller()
													.getName()
													.equalsIgnoreCase(
															r.getName())) {
										killedBats++;
									}

									if (killedBats >= 2) {
										p.sendMessage(" ");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage("§c"
												+ API.getSpaces(r.getName(), 22)
												+ r.getName()
												+ " está provavelmente usando FF/KA!");
										p.sendMessage("§c                                 Hitou 2 de 2 morcegos!");
										p.sendMessage("§c       Tenha certeza antes de tomar alguma providência!");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage(" ");
									} else if (killedBats >= 1) {
										p.sendMessage(" ");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage("§c"
												+ API.getSpaces(r.getName(), 30)
												+ r.getName()
												+ " pode estar usando FF/KA!");
										p.sendMessage("§c                                 Hitou 1 de 2 morcegos!");
										p.sendMessage("§c                                       É melhor checar!");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage(" ");
									} else {
										p.sendMessage(" ");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage("§c"
												+ API.getSpaces(r.getName(), 18)
												+ r.getName()
												+ " provavelmente nao está usando FF/KA!");
										p.sendMessage("§c                                 Hitou 0 de 2 morcegos!");
										p.sendMessage("§c                                    Cheque manualmente!");
										p.sendMessage("§6§m----------(-------------------------------)----------");
										p.sendMessage(" ");
									}
									b.remove();
									b1.remove();
								}
							}, 40L);
				}
			}
		}

		if (inv.getTitle().equalsIgnoreCase("§4➤ Sobre o Jogador")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPickItem(PlayerPickupItemEvent e) {
		if (admins.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}

}
