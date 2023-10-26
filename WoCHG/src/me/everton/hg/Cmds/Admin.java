package me.everton.hg.Cmds;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.hg.API;
import me.everton.hg.Main;
import me.everton.hg.TagType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Admin implements CommandExecutor, Listener {
	public static ArrayList<String> admins = new ArrayList<>();
	public static ArrayList<String> tr = new ArrayList<>();
	public static HashMap<String, ItemStack[]> inventory = new HashMap<>();
	public static HashMap<String, ItemStack[]> armor = new HashMap<>();
	public static HashMap<String, String> rc = new HashMap<>();

	public static HashMap<String, Inventory> adminInv = new HashMap<>();

	@SuppressWarnings("deprecation")
	public static void entrarAdmin(Player p) {
		API.deleteArrayList(admins, p.getName());
		API.deleteHashMapKey(inventory, p.getName());
		API.deleteHashMapKey(armor, p.getName());
		admins.add(p.getName());
		inventory.put(p.getName(), p.getInventory().getContents());
		armor.put(p.getName(), p.getInventory().getArmorContents());
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);

		p.getInventory().setItem(1, API.item(Material.ARROW, 1, "§c§lHacks"));
		ItemStack i = new ItemStack(Material.SKULL_ITEM, 1,
				(short) SkullType.PLAYER.ordinal());
		SkullMeta m = (SkullMeta) i.getItemMeta();
		m.setDisplayName("§b§lJogadores Onlines");
		m.setOwner("MHF_Exclamation");
		i.setItemMeta(m);
		p.getInventory().setItem(4, i);
		p.getInventory().setItem(7,
				API.item(Material.MAGMA_CREAM, 1, "§6§lTroca Rápida"));
		p.getInventory().setItem(0,
				API.item(Material.COMPASS, 1, "§c§lServidores"));

		Inventory inv = Bukkit.createInventory(p, 9, "§c§lHacks");
		inv.setItem(0, API.item(Material.BONE, 1, "§6§lAntiKnockback"));
		inv.setItem(1, API.item(Material.NETHER_STAR, 1, "§6§lFF/KA"));
		inv.setItem(2, API.item(Material.MUSHROOM_SOUP, 1, "§6§lAutoSoup"));
		adminInv.put(p.getName(), inv);

		for (Player on : Bukkit.getOnlinePlayers()) {
			if (!Gerais.specson.contains(on.getName())) {
				on.hidePlayer(p);
			}
			
			if(on.hasPermission("hg.admin")) {
				on.sendMessage("§7[" + ChatColor.stripColor(p.getDisplayName()) + " entrou no modo Admin]");
			}
		}

		p.sendMessage("§dVocê entrou no modo Admin!");
		p.sendMessage("§dAgora você está invisível para todos!");
		p.setGameMode(GameMode.CREATIVE);
	}

	@SuppressWarnings("deprecation")
	public static void sairAdmin(Player p) {
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

					if(on.hasPermission("hg.admin")) {
						on.sendMessage("§7[" + ChatColor.stripColor(p.getDisplayName()) + " saiu do modo Admin]");
					}
				}
				p.setGameMode(GameMode.SURVIVAL);
			}
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
			if (!p.hasPermission("woc.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			if (!admins.contains(p.getName())) {
				entrarAdmin(p);
			} else {
				sairAdmin(p);
			}
		}
		if (label.equalsIgnoreCase("vis")) {
			if (!p.hasPermission("woc.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			for (Player on : Bukkit.getOnlinePlayers()) {
				on.showPlayer(p);
			}
			p.sendMessage("§7[§c!§7] Agora você está visível para todos!");
		}

		if (label.equalsIgnoreCase("invis")) {
			if (!p.hasPermission("woc.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			
			if(args.length == 1) {
				for(TagType tag : TagType.values()) {
					if(tag.name().equalsIgnoreCase(args[0])) {
						for(String o : API.belowRanks(tag)) {
							Player on = Bukkit.getPlayerExact(o);
							if(on == null) {
								return true;
							}
							if(Gerais.specson.contains(on.getName())) {
								return true;
							}
							on.hidePlayer(p);
						}
						p.sendMessage("§7[§c!§7] Agora você está invisível para o rank " + tag.toString().replace("&", "§") + tag.name() + " §7e abaixo!");
						break;
					}
				}
			} else{
				for (Player on : Bukkit.getOnlinePlayers()) {
					if (Gerais.specson.contains(on.getName())) {
						return true;
					}
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

			if (p.getItemInHand().getType() == Material.SKULL_ITEM) {
				e.setCancelled(true);
				Main.abrirGuiSpectador(p, Bukkit.getOnlinePlayers(), 6, true);
			}
			if (p.getItemInHand().getType() == Material.MAGMA_CREAM) {
				e.setCancelled(true);
				p.setItemInHand(API.item(Material.SLIME_BALL, 1,
						"§6§lTroca Rápida"));
				for (Player on : Bukkit.getOnlinePlayers()) {
					on.showPlayer(p);
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
									if (!Gerais.specson.contains(on.getName())) {
										on.hidePlayer(p);
									}
								}
								tr.remove(p.getName());
								p.setGameMode(GameMode.CREATIVE);
								p.setFlying(true);
								p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								p.setItemInHand(API.item(Material.MAGMA_CREAM,
										1, "§6§lTroca Rápida"));
							}
						}, 3L);
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
					Inventory inv = adminInv.get(p.getName());
					p.openInventory(inv);
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
					API.deleteHashMapKey(rc, p.getName());
					rc.put(e.getPlayer().getName(), r.getName());
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
		if (ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase(
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

		if (ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase(
				"Hacks")) {
			e.setCancelled(true);
			if ((e.getCurrentItem() != null)) {
				if (Bukkit.getPlayerExact(rc.get(p.getName())) == null) {
					p.closeInventory();
					return;
				}
				final Player r = Bukkit.getPlayerExact(rc.get(p.getName()));
				if (e.getCurrentItem().getType() == Material.MUSHROOM_SOUP) {
					p.closeInventory();
					// testAutosoup(p, r);

					inventory.put(r.getName(), r.getInventory().getContents());
					armor.put(r.getName(), r.getInventory().getArmorContents());

					r.getInventory().clear();
					r.getInventory().setArmorContents(null);

					r.getInventory().setItem(2,
							API.item(Material.MUSHROOM_SOUP));
					r.getInventory().setItem(11,
							API.item(Material.MUSHROOM_SOUP));
					r.setHealth(6.0D);

					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {

								@Override
								public void run() {
									r.setHealth(20.0D);
									if ((r.getInventory().getItem(2).getType() == Material.BOWL)
											&& (r.getInventory().getItem(0)
													.getType() == Material.BOWL)) {
										p.sendMessage("§7[§a!§7] "
												+ r.getName()
												+ " está usando Auto-Soup!");
									} else {
										p.sendMessage("§7[§a!§7] "
												+ r.getName()
												+ " nao está usando Auto-Soup!");
									}
									r.getInventory().setContents(
											inventory.get(r.getName()));
									r.getInventory().setArmorContents(
											armor.get(r.getName()));
								}
							}, 20L);

				}
			}
		}
	}

	@EventHandler
	public void onPickItem(PlayerPickupItemEvent e) {
		if (admins.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}
}
