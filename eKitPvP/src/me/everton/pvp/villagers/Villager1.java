package me.everton.pvp.villagers;

import me.everton.pvp.API;
import me.everton.pvp.LocationsManager;
import me.everton.pvp.Main;
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

public class Villager1 implements Listener {
	public static void spawnVillager(final Player p) {
		final Villager v = (Villager) p.getWorld().spawnEntity(p.getLocation(),
				EntityType.VILLAGER);
		LocationsManager.setLocation(p, "villager1");
		v.setCustomName("§e§lGanhe Prêmios");
		v.setCustomNameVisible(true);
		v.setProfession(Profession.LIBRARIAN);
		v.setRemoveWhenFarAway(false);
		
		for(ItemStack i : p.getInventory().getContents()) {
			if(i != null && i.getType() != Material.AIR) {
				p.getWorld().dropItemNaturally(p.getLocation(), i);
			}
		}

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
		v.setCustomName("§e§lGanhe Prêmios");
		v.setCustomNameVisible(true);
		v.setProfession(Profession.LIBRARIAN);
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
		if (p.getItemInHand().getType() == Material.NAME_TAG) {
			if (p.hasPermission("pvp.removevillager")) {
				v.remove();
				p.sendMessage("§7[§c!§7] Villager " + v.getCustomName()
						+ " §7removido com sucesso!");
				e.setCancelled(true);
				return;
			}
		}
		if (v.isCustomNameVisible()
				&& !v.getCustomName().equalsIgnoreCase("§e§lGanhe Prêmios")) {
			return;
		}

		e.setCancelled(true);

		openInv(p, null, Pages.INICIAL);
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

		if (inv.getTitle().equalsIgnoreCase("§4Ganhe Prêmios")) {
			e.setCancelled(true);
			e.setCursor(null);

			if (inv.getContents()[15].getType() == Material.EMERALD) {
				if (i.getType() == Material.PAPER) {
					p.closeInventory();
					openInv(p, null, Pages.OBJETIVOS);
					return;
				}

				if (i.getType() == Material.EMERALD) {
					openInv(p, null, Pages.LOJA);
					return;
				}
				return;
			}

			if (inv.getContents()[0].getType() == Material.CARPET) {
				if (i.getType() == Material.LOG) {
					buyVip(p, Vips.MADEIRA);
					return;
				}
				if (i.getType() == Material.STONE) {
					buyVip(p, Vips.PEDRA);
					return;
				}
				if (i.getType() == Material.IRON_BLOCK) {
					buyVip(p, Vips.FERRO);
					return;
				}
				if (i.getType() == Material.DIAMOND_BLOCK) {
					buyVip(p, Vips.DIAMANTE);
					return;
				}

				if (i.getType() == Material.CARPET
						&& i.getData().getData() == 14) {
					openInv(p, null, Pages.INICIAL);
					return;
				}

				if (i.getType() != Material.STAINED_GLASS_PANE
						&& i.getType() != Material.EMERALD) {
					buyItem(p, i);
					return;
				}
				return;
			}
		}
	}

	public static enum Pages {
		INICIAL, OBJETIVOS, LOJA;
	}

	private void openInv(Player p, Inventory openInv, Pages pa) {
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);

		Inventory inv;
		if (openInv == null) {
			int slots = 27;
			if (pa == Pages.LOJA) {
				slots += 27;
			}

			inv = Bukkit.createInventory(p, slots, "§4Ganhe Prêmios");
		} else {
			inv = openInv;
		}

		if (pa == Pages.INICIAL) {
			API.fillVillagerInv(inv);

			inv.setItem(11, API.item(Material.PAPER, 1, "§f» §f§lObjetivos"));
			inv.setItem(15, API.item(Material.EMERALD, 1, "§f» §a§lLoja"));

			if (openInv == null) {
				p.openInventory(inv);
			}
		} else if (pa == Pages.OBJETIVOS) {
			p.sendMessage(" ");
			p.sendMessage("§7Hmm, Olá §a"
					+ ChatColor.stripColor(p.getPlayerListName())
					+ "§7, entao quer dizer que queres ganhar §aPrêmios§7, certo? A cada §aPlayer §7que você mata você ganha §a§l10 VillagerCoins §7e se clicares em mim novamente poderás ir no Menu '§aUse suas VillagersCoins§7' e trocar por §aItens bons§7! Quanto mais §aVillagersCoins, §7melhor! Afinal eu sou um Villager, eu troco coisas com você, nao é verdade? §aBoa sorte§7! ;D");
			p.sendMessage(" ");
		} else if (pa == Pages.LOJA) {
			API.fillVillagerInv(inv);

			inv.setItem(0, API.item(Material.CARPET, 1, "§f« §cVoltar", 14));
			inv.setItem(8, API.item(Material.EMERALD, 1, "§e§lVillagerCoins:", new String[] {"§f» §6" + VillagerCoins.getVillagerCoins(p.getName())}));

			// VIPS
			inv.setItem(
					10,
					API.item(Material.LOG, 1, "§eVip §lMADEIRA", new String[] {
							"§ePreço: §65.000 §e§lVillagerCoins",
							"§ePeríodo: §61 §e§lmês" }));
			inv.setItem(
					12,
					API.item(Material.STONE, 1, "§8Vip §lPEDRA", new String[] {
							"§ePreço: §67.500 §e§lVillagerCoins",
							"§ePeríodo: §63 §e§lmeses" }));
			inv.setItem(14, API.item(Material.IRON_BLOCK, 1, "§7Vip §lFERRO",
					new String[] { "§ePreço: §610.000 §e§lVillagerCoins",
							"§ePeríodo: §66 §e§lmeses" }));
			inv.setItem(16, API.item(Material.DIAMOND_BLOCK, 1,
					"§1Vip §lDIAMANTE", new String[] {
							"§ePreço: §615.000 §e§lVillagerCoins",
							"§ePeríodo: §6Eterno" }));

			// ARMADURA
			inv.setItem(18, API.item(Material.IRON_HELMET, 1,
					"§eCapacete de Ferro",
					new String[] { "§ePreço: §61.000 §e§lVillagerCoins" }));
			inv.setItem(36, API.item(Material.DIAMOND_CHESTPLATE, 1,
					"§ePeitoral de Diamante",
					new String[] { "§ePreço: §62.500 §e§lVillagerCoins" }));
			inv.setItem(26, API.item(Material.IRON_LEGGINGS, 1,
					"§eCalça de Ferro",
					new String[] { "§ePreço: §62.000 §e§lVillagerCoins" }));
			inv.setItem(44, API.item(Material.IRON_BOOTS, 1,
					"§eBotas de Ferro",
					new String[] { "§ePreço: §6500 §e§lVillagerCoins" }));

			// KITS
			inv.setItem(20, API.item(Material.NETHER_STAR, 1, "§eKit Ninja",
					new String[] { "§ePreço: §65.000 §e§lVillagerCoins" }));
			inv.setItem(22, API.item(Material.SLIME_BALL, 1, "§eKit C4",
					new String[] { "§ePreço: §67.000 §e§lVillagerCoins" }));
			inv.setItem(24, API.item(Material.CARPET, 1, "§eKit Aladdin",
					new String[] { "§ePreço: §64.000 §e§lVillagerCoins" }));

			// POÇÕES
			inv.setItem(30, API.item(PotionType.SPEED, 5,
					"§ePoçoes de Velocidade",
					new String[] { "§ePreço: §6500 §e§lVillagerCoins" },
					true, true, 1));
			inv.setItem(31, API.item(PotionType.POISON, 5,
					"§ePoçoes de Veneno",
					new String[] { "§ePreço: §6700 §e§lVillagerCoins" },
					true, true, 1));
			inv.setItem(32, API.item(PotionType.FIRE_RESISTANCE, 5,
					"§ePoçoes de Resistência ao Fogo",
					new String[] { "§ePreço: §6600 §e§lVillagerCoins" },
					true, true, 1));

			// ITENS E MAÇA
			inv.setItem(38, API.item(Material.IRON_SWORD, 1,
					"§eEspada de Ferro",
					new String[] { "§ePreço: §62.500 §e§lVillagerCoins" }));
			inv.setItem(40, API.item(Material.GOLDEN_APPLE, 10,
					"§eMaças Douradas",
					new String[] { "§ePreço: §61.500 §e§lVillagerCoins" }));
			inv.setItem(42, API.item(Material.DIAMOND_SWORD, 1,
					"§eEspada de Diamante",
					new String[] { "§ePreço: §65.000 §e§lVillagerCoins" }));

			if (openInv == null) {
				p.openInventory(inv);
			}
		}
	}

	public static enum Vips {
		MADEIRA("§eVip §lMADEIRA", "1 mês", 30, 5000), PEDRA("§8Vip §lPEDRA",
				"3 meses", 30 * 3, 7500), FERRO("§7Vip §lFERRO", "6 meses",
				30 * 6, 10000), DIAMANTE("§1Vip §lDIAMANTE", "Todo o sempre",
				-1, 15000);

		String nome;
		String periodo;
		int duration;
		int value;

		private Vips(String nome, String periodo, int duration, int value) {
			this.nome = nome;
			this.periodo = periodo;
			this.duration = duration;
			this.value = value;
		}

		public String getName() {
			return nome;
		}

		public String getPeriod() {
			return periodo;
		}

		public int getDuration() {
			return duration;
		}

		public int getValue() {
			return value;
		}
	}

	private void buyVip(Player p, Vips vip) {
		if (VillagerCoins.getVillagerCoins(p.getName()) >= vip.getValue()) {
			API.broadcastMessage("§e"
					+ ChatColor.stripColor(p.getPlayerListName())
					+ " acabou de comprar o " + vip.getName() + "§e! §lGG ;D");
			API.sendTitle(p, vip.getName(),
					"§c§lAdquirido por " + vip.getPeriod() + "!", 1, 5, 1);

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
			VillagerCoins.removeVillagerCoins(p.getName(), vip.getValue());
			p.closeInventory();
		} else {
			p.sendMessage("§eVocê nao possui §lVillagerCoins§e suficientes para comprar o "
					+ vip.getName()
					+ "§e! Faltam "
					+ (vip.getValue() - VillagerCoins.getVillagerCoins(p
							.getName())) + " §lVillagerCoins§e!");
			p.closeInventory();
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
		}
	}

	private void buyItem(final Player p, ItemStack it) {
		int value = Integer.valueOf(it.getItemMeta().getLore().get(0)
				.replace("§ePreço: §6", "").replace(" §e§lVillagerCoins", "")
				.replace(".", ""));
		if (VillagerCoins.getVillagerCoins(p.getName()) >= value) {
			if (KitManager.getKit(p) == KitType.NONE
					&& !it.getItemMeta().getDisplayName().contains("Kit")) {
				p.sendMessage("§eVocê precisa ter escolhido um kit!");
				p.closeInventory();
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
				return;
			}
			if (API.getHowMuchClearSlots(p) < 1
					&& !it.getItemMeta().getDisplayName().contains("Kit")) {
				p.sendMessage("§eDeixe um slot livre em seu inventário para comprar este item!");
				p.closeInventory();
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
				return;
			}

			if (it.getItemMeta().getDisplayName().contains("Kit")) {
				if (p.hasPermission("kit."
						+ ChatColor
								.stripColor(it.getItemMeta().getDisplayName())
								.replace("Kit ", "").toLowerCase())) {
					p.sendMessage("§eVocê já possui este kit!");
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
				API.sendTitle(p, "§e" + it.getItemMeta().getDisplayName(),
						"§c§lAdquirido! ;D", 1, 5, 1);
			} else {
				p.getInventory().addItem(it);
				API.sendTitle(
						p,
						"§e" + it.getItemMeta().getDisplayName(),
						"§c§lAdquirido e item adicionado em seu inventário! ;D",
						1, 5, 1);
				new BukkitRunnable() {
					
					@Override
					public void run() {
						p.sendMessage("§eVocê pode usar o comando §6/bau §ee guardar os itens!");
					}
				}.runTaskLater(Main.getPlugin(), 1L);
			}
			API.broadcastMessage("§e"
					+ ChatColor.stripColor(p.getPlayerListName())
					+ " acabou de comprar " + it.getItemMeta().getDisplayName()
					+ "§e! §lGG ;D");

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
			VillagerCoins.removeVillagerCoins(p.getName(), value);
			p.closeInventory();
		} else {
			p.sendMessage("§eVocê nao possui §lVillagerCoins§e suficientes para comprar isso! Faltam "
					+ (value - VillagerCoins.getVillagerCoins(p.getName()))
					+ " §lVillagerCoins§e!");
			p.closeInventory();
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
		}
	}
}
