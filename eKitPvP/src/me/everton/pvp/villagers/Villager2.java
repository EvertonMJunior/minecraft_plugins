package me.everton.pvp.villagers;

import me.everton.pvp.API;
import me.everton.pvp.JsonBuilder;
import me.everton.pvp.LocationsManager;
import me.everton.pvp.Main;
import me.everton.pvp.Msgs;
import me.everton.pvp.JsonBuilder.ClickAction;
import me.everton.pvp.JsonBuilder.HoverAction;
import me.everton.pvp.kits.habilidades.Grappler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Villager2 implements CommandExecutor, Listener {
	public static void spawnVillager(Player p) {
		final Villager v = (Villager) p.getWorld().spawnEntity(p.getLocation(),
				EntityType.VILLAGER);
		LocationsManager.setLocation(p, "villager2");
		v.setCustomName("§e§lInformaçoes");
		v.setCustomNameVisible(true);
		v.setProfession(Profession.FARMER);
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
		v.setCustomName("§e§lInformaçoes");
		v.setCustomNameVisible(true);
		v.setProfession(Profession.FARMER);
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
				&& !v.getCustomName().equalsIgnoreCase("§e§lInformaçoes")) {
			return;
		}

		e.setCancelled(true);
		openInv(p, Pages.INICIAL, null);
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

		if (inv.getTitle().equalsIgnoreCase("§4Informaçoes")) {
			e.setCancelled(true);
			e.setCursor(null);

			if (inv.getContents()[10].getType() == Material.BOOK) {
				if (i.getType() == Material.BOOK) {
					p.sendMessage("§6§m-----(------------------------)-----");
					JsonBuilder msg = new JsonBuilder(
							"§b§l    Entre em nosso site: ");
					msg.withText("§b§l§n" + Msgs.site.toUpperCase());
					msg.withHoverEvent(HoverAction.SHOW_TEXT,
							"§aClique para ir ao Site!");
					msg.withClickEvent(ClickAction.OPEN_URL, "http://"
							+ Msgs.site);
					msg.sendJson(p);
					p.sendMessage("§6§m-----(------------------------)-----");
					p.closeInventory();
					return;
				}

				if (i.getType() == Material.DIAMOND_BLOCK) {
					openInv(p, Pages.VIPS, inv);
					return;
				}
				return;
			}

			if (inv.getContents()[10].getType() == Material.LOG) {
				if (i.getType() == Material.LOG) {
					showVip(p, i);
					return;
				}

				return;
			}
		}
	}

	public static enum Pages {
		INICIAL, VIPS;
	}

	private void showVip(Player p, ItemStack it) {
		p.closeInventory();
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
		
		
	}

	private void openInv(Player p, Pages pa, Inventory openInv) {
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
		Inventory inv;
		if (openInv == null) {
			inv = Bukkit.createInventory(p, 27, "§4Informaçoes");
		} else {
			inv = openInv;
		}

		if (pa == Pages.INICIAL) {
			API.fillVillagerInv(inv);

			inv.setItem(10, API.item(Material.BOOK, 1, "§f» §6§lSite"));
			inv.setItem(12, API.item(Material.WOOL, 1, "§f» §c§lYoutuber", 14));
			inv.setItem(14, API.item(Material.WOOL, 1, "§f» §3§lTwitter", 9));
			inv.setItem(16, API.item(Material.DIAMOND_BLOCK, 1, "§f» §b§lVips"));

			if (openInv == null) {
				p.openInventory(inv);
			}
		} else if (pa == Pages.VIPS) {
			API.fillVillagerInv(inv);

			inv.setItem(10, API.item(Material.LOG, 1, "§eVip §lMADEIRA",
					new String[] { "§ePreço: §67 §e§lReais",
							"§ePeríodo: §61 §e§lmês" }));
			inv.setItem(
					12,
					API.item(Material.STONE, 1, "§8Vip §lPEDRA", new String[] {
							"§ePreço: §615 §e§lReais",
							"§ePeríodo: §63 §e§lmeses" }));
			inv.setItem(14, API.item(Material.IRON_BLOCK, 1, "§7Vip §lFERRO",
					new String[] { "§ePreço: §620 §e§lReais",
							"§ePeríodo: §66 §e§lmeses" }));
			inv.setItem(16, API.item(Material.DIAMOND_BLOCK, 1,
					"§1Vip §lDIAMANTE", new String[] {
							"§ePreço: §630 §e§lReais", "§ePeríodo: §6Eterno" }));

			if (openInv == null) {
				p.openInventory(inv);
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game!");
			return true;
		}
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("bau")) {
			if (Grappler.nerf.contains(p.getName())) {
				p.sendMessage("§cVocê está em combate!");
				return true;
			}

			p.openInventory(p.getEnderChest());
		}
		return false;
	}
}
