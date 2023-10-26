package me.everton.pvp.villagers;

import me.everton.pvp.API;
import me.everton.pvp.LocationsManager;
import me.everton.pvp.Main;
import me.everton.pvp.ScoreboardManager;
import me.everton.pvp.ScoreManager.Money;
import me.everton.pvp.ScoreManager.VillagerCoins;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import ru.tehkode.permissions.bukkit.PermissionsEx;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.DnaEffect;
import de.slikey.effectlib.effect.FlameEffect;

public class Villager4 implements Listener {
	public static void spawnVillager(Player p) {
		final Villager v = (Villager) p.getWorld().spawnEntity(p.getLocation(),
				EntityType.VILLAGER);
		LocationsManager.setLocation(p, "villager4");
		v.setCustomName("§e§lCompre Itens");
		v.setCustomNameVisible(true);
		v.setProfession(Profession.BLACKSMITH);
		v.setRemoveWhenFarAway(false);

		new BukkitRunnable() {

			@Override
			public void run() {
				if (!v.isDead()) {
					if (v.getNoDamageTicks() < 1000) {
						v.setNoDamageTicks(999999999);
					}
					v.setVelocity(new Vector());
				} else {
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 0L);
	}

	public static void spawnVillager(Location loc) {
		final Villager v = (Villager) loc.getWorld().spawnEntity(loc,
				EntityType.VILLAGER);
		v.setCustomName("§e§lCompre Itens");
		v.setCustomNameVisible(true);
		v.setProfession(Profession.BLACKSMITH);
		v.setRemoveWhenFarAway(false);

		new BukkitRunnable() {

			@Override
			public void run() {
				if (!v.isDead()) {
					if (v.getNoDamageTicks() < 1000) {
						v.setNoDamageTicks(999999999);
					}
					v.setVelocity(new Vector());
				} else {
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 0L);
	}

	@EventHandler
	public void onIE(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (!(e.getRightClicked() instanceof Villager)) {
			return;
		}
		Villager v = (Villager) e.getRightClicked();
		if (v.isCustomNameVisible()
				&& !v.getCustomName().equalsIgnoreCase("§e§lCompre Itens")) {
			return;
		}

		e.setCancelled(true);
		openInv(p, Pages.INICIAL);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onIC(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack i = e.getCurrentItem();
		if (inv == null) {
			return;
		}

		if (inv.getTitle().equalsIgnoreCase("§4Compre Itens")) {
			e.setCancelled(true);
			e.setCursor(null);

			if (inv.getContents()[12].getType() == Material.EMERALD) {
				if (i.getType() == Material.EMERALD) {
					openInv(p, Pages.ITENS_NORMAIS);
					return;
				}
				if (i.getType() == Material.DIAMOND) {
					if (p.hasPermission("pvp.villager4.vips")) {
						openInv(p, Pages.ITENS_VIP);
					} else {
						p.sendMessage("§eVocê nao é VIP!");
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F,
								15.5F);
						p.closeInventory();
					}
					return;
				}

				return;
			}

			if (i.getType() == Material.CARPET && i.getData().getData() == 14) {
				openInv(p, Pages.INICIAL);
				return;
			}

			if (i.getType() == Material.AIR || i == null
					|| i.getType() == Material.STAINED_GLASS_PANE
					|| i.getType() == Material.CARPET
					&& i.getData().getData() == 14) {
				return;
			}

			if (inv.getContents()[11].getType() == Material.POTION) {
				buyItem(p, i);
				return;
			}

			if (inv.getContents()[11].getType() == Material.MAGMA_CREAM) {
				buyItem(p, i);
				return;
			}
		}
	}

	public static enum Pages {
		INICIAL, ITENS_NORMAIS, ITENS_VIP;
	}

	private void openInv(Player p, Pages pa) {
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
		if (pa == Pages.INICIAL) {
			Inventory inv = Bukkit.createInventory(p, 27, "§4Compre Itens");
			API.fillVillagerInv(inv);

			inv.setItem(12,
					API.item(Material.EMERALD, 1, "§f» §a§lItens Normais"));
			inv.setItem(14, API.item(Material.DIAMOND, 1, "§f» §b§lItens Vip"));

			p.openInventory(inv);
		} else if (pa == Pages.ITENS_NORMAIS) {
			Inventory inv = Bukkit.createInventory(p, 54, "§4Compre Itens");
			API.fillVillagerInv(inv);

			inv.setItem(0, API.item(Material.CARPET, 1, "§f« §cVoltar", 14));
			inv.setItem(8, API.item(Material.EMERALD, 1, "§d§lTigerCoins:",
					new String[] { "§f» §5" + Money.getMoney(p.getName()) }));

			// POÇÕES
			inv.setItem(11, API.item(PotionType.INSTANT_HEAL, 5,
					"§dPoçoes de Cura",
					new String[] { "§dPreço: §55000 §d§lTigerCoins" }, true,
					false, 2));
			inv.setItem(13, API.item(PotionType.REGEN, 5,
					"§dPoçoes de Regeneraçao",
					new String[] { "§dPreço: §56000 §d§lTigerCoins" }, true,
					false, 2));
			inv.setItem(15, API.item(PotionType.INSTANT_DAMAGE, 5,
					"§dPoçoes de Dano Instantâneo",
					new String[] { "§dPreço: §58000 §d§lVillagerCoins" }, true,
					false, 1));

			// ARMADURA
			inv.setItem(19, API.item(Material.IRON_HELMET, 1,
					"§dCapacete de Ferro",
					new String[] { "§dPreço: §56.000 §d§lTigerCoins" }));
			inv.setItem(21, API.item(Material.IRON_CHESTPLATE, 1,
					"§dPeitoral de Ferro",
					new String[] { "§dPreço: §59.000 §d§lTigerCoins" }));
			inv.setItem(23, API.item(Material.IRON_LEGGINGS, 1,
					"§dCalça de Ferro",
					new String[] { "§dPreço: §58.000 §d§lTigerCoins" }));
			inv.setItem(25, API.item(Material.IRON_BOOTS, 1,
					"§dBotas de Ferro",
					new String[] { "§dPreço: §55.000 §d§lTigerCoins" }));

			// MAÇA DOURADA
			inv.setItem(31, API.item(Material.GOLDEN_APPLE, 5,
					"§dMaças Douradas",
					new String[] { "§dPreço: §5700 §d§lTigerCoins" }));

			// KITS
			inv.setItem(37, API.item(Material.REDSTONE_TORCH_ON, 1,
					"§dKit Flash",
					new String[] { "§dPreço: §530.000 §d§lTigerCoins" }));
			inv.setItem(39, API.item(Material.WOOD_AXE, 1, "§dKit Thor",
					new String[] { "§dPreço: §529.000 §d§lTigerCoins" }));
			inv.setItem(41, API.item(Material.IRON_FENCE, 1, "§dKit Gladiator",
					new String[] { "§dPreço: §540.000 §d§lTigerCoins" }));
			inv.setItem(43, API.item(Material.CAKE, 1, "§dKit Surprise",
					new String[] { "§dPreço: §530.500 §d§lTigerCoins" }));

			p.openInventory(inv);
		} else if (pa == Pages.ITENS_VIP) {
			Inventory inv = Bukkit.createInventory(p, 54, "§4Compre Itens");
			API.fillVillagerInv(inv);

			inv.setItem(0, API.item(Material.CARPET, 1, "§f« §cVoltar", 14));
			inv.setItem(8, API.item(Material.EMERALD, 1, "§d§lTigerCoins:",
					new String[] { "§f» §5" + Money.getMoney(p.getName()) }));

			// KITS
			inv.setItem(11, API.item(Material.MAGMA_CREAM, 1,
					"§dKit Terrorista",
					new String[] { "§dPreço: §570.000 §d§lTigerCoins" }));
			inv.setItem(13, API.item(Material.BEACON, 1, "§dKit Avatar",
					new String[] { "§dPreço: §5100.000 §d§lTigerCoins" }));
			inv.setItem(15, API.item(Material.COAL_BLOCK, 1, "§dKit Sombra",
					new String[] { "§dPreço: §550.000 §d§lTigerCoins" }));
			inv.setItem(19, API.item(Material.LEASH, 1, "§dKit Grappler",
					new String[] { "§dPreço: §590.000 §d§lTigerCoins" }));
			inv.setItem(21, API.item(Material.NETHER_STAR, 1, "§dKit Ninja",
					new String[] { "§dPreço: §5100.000 §d§lTigerCoins" }));
			inv.setItem(23, API.item(Material.DIAMOND_SWORD, 1, "§dKit Madman",
					new String[] { "§dPreço: §5150.000 §d§lTigerCoins" }));
			inv.setItem(25, API.item(Material.WATCH, 1, "§dKit Timelord",
					new String[] { "§dPreço: §560.000 §d§lTigerCoins" }));

			// ITENS
			inv.setItem(39, API.item(Material.IRON_SWORD, 1,
					"§dEspada de Ferro",
					new String[] { "§dPreço: §520.000 §d§lTigerCoins" }));
			inv.setItem(41, API.item(Material.DIAMOND_CHESTPLATE, 1,
					"§dPeitoral de Diamante",
					new String[] { "§dPreço: §5100.000 §d§lTigerCoins" }));

			p.openInventory(inv);
		}
	}

	private void buyItem(final Player p, ItemStack it) {
		int value = Integer.valueOf(it.getItemMeta().getLore().get(0)
				.replace("§dPreço: §5", "").replace(" §d§lTigerCoins", "")
				.replace(".", ""));
		if (VillagerCoins.getVillagerCoins(p.getName()) >= value) {
			if (KitManager.getKit(p) == KitType.NONE
					&& !it.getItemMeta().getDisplayName().contains("Kit")) {
				p.sendMessage("§dVocê precisa ter escolhido um kit!");
				p.closeInventory();
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
				return;
			}
			if (API.getHowMuchClearSlots(p) < 1
					&& !it.getItemMeta().getDisplayName().contains("Kit")) {
				p.sendMessage("§dDeixe um slot livre em seu inventário para comprar este item!");
				p.closeInventory();
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
				return;
			}

			if (it.getItemMeta().getDisplayName().contains("Kit")) {
				if (p.hasPermission("kit."
						+ ChatColor
								.stripColor(it.getItemMeta().getDisplayName())
								.replace("Kit ", "").toLowerCase())) {
					p.sendMessage("§dVocê já possui este kit!");
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
					return;
				}

				PermissionsEx.getUser(p).addPermission(
						"kit."
								+ ChatColor
										.stripColor(
												it.getItemMeta()
														.getDisplayName())
										.replace("Kit ", "").toLowerCase());
				API.sendTitle(p, "§d" + it.getItemMeta().getDisplayName(),
						"§c§lAdquirido! ;D", 1, 5, 1);
			} else {
				p.getInventory().addItem(it);
				API.sendTitle(
						p,
						"§d" + it.getItemMeta().getDisplayName(),
						"§c§lAdquirido e item adicionado em seu inventário! ;D",
						1, 5, 1);
				new BukkitRunnable() {

					@Override
					public void run() {
						p.sendMessage("§dVocê pode usar o comando §5/bau§d e guardar os itens!");
					}
				}.runTaskLater(Main.getPlugin(), 1L);
			}
			API.broadcastMessage("§d"
					+ ChatColor.stripColor(p.getPlayerListName())
					+ " acabou de comprar " + it.getItemMeta().getDisplayName()
					+ "§d! §lGG ;D");

			Effect ef = new DnaEffect(Main.getEM());
			ef.iterations = 5;
			ef.setLocation(p.getLocation());
			ef.setEntity(p);
			ef.start();

			Effect ef3 = new FlameEffect(Main.getEM());
			ef3.iterations = 5;
			ef3.setLocation(p.getLocation());
			ef3.setEntity(p);
			ef3.start();

			p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
			Money.removeMoney(p.getName(), value);
			ScoreboardManager.refreshSidebar(p);
			p.closeInventory();
		} else {
			p.sendMessage("§dVocê nao possui §lTigerCoins§e suficientes para comprar isso! Faltam §5"
					+ (value - Money.getMoney(p.getName()))
					+ " §d§lTigerCoins§d!");
			p.closeInventory();
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
		}
	}
}
