package me.everton.WocPvP.HG;

import java.util.ArrayList;

import me.everton.WocPvP.Main;
import me.everton.WocPvP.Comandos.Fps;
import me.everton.WocPvP.Comandos.Spec;
import me.everton.WocPvP.Comandos.Warp;
import me.everton.WocPvP.Listeners.KitListener;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

public class HG implements Listener, CommandExecutor {
	public static ArrayList<Player> hg = new ArrayList<>();
	public static ArrayList<Player> hgin = new ArrayList<>();
	public static ArrayList<Player> hgcd = new ArrayList<>();
	public static ArrayList<Player> jump = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public static void itensHG(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		ItemStack fogo = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta metafogo = fogo.getItemMeta();
		metafogo.setDisplayName(" ");
		fogo.setItemMeta(metafogo);

		ItemStack kits = new ItemStack(Material.ENDER_CHEST);
		ItemMeta metakits = kits.getItemMeta();
		metakits.setDisplayName("§b§lKits HG");
		kits.setItemMeta(metakits);
		p.getInventory().setItem(4, kits);
		p.getInventory().setItem(3, fogo);
		p.getInventory().setItem(5, fogo);
		p.updateInventory();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		final Player p = (Player) sender;
		if (label.equalsIgnoreCase("hg")) {
			if (Fps.fps.contains(p)) {
				Fps.fps.remove(p);
			}
			if (!hg.contains(p)) {
				Warp.tpcoolDown(p, "§aVocê foi teleportado ao warp HG!", 5,
						"hg", false, false, true, false, false);
			} else if (hg.contains(p)) {
				if (!hgcd.contains(p)) {
					Warp.tpcoolDown(p, "§aVocê foi teleportado ao Spawn!", 5,
							"spawn", false, false, true, false, false);
				}
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void aoMexer(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (!Spec.specs.contains(p)) {
			Block b = p.getLocation().subtract(0, 1, 0).getBlock();
			if (b.getType() == Material.SPONGE) {
				if (b.getWorld().getName().equalsIgnoreCase("hg")) {
					if (!hg.contains(p)) {
						UltraKits.Main.resetKit(p);
						itensHG(p);

						p.teleport(Main.loc("hg", p));
						for (Player hg : hg) {
							hg.sendMessage("§7[§a+§7] " + p.getName()
									+ " entrou no Warp HG");
						}
						hg.add(p);
					}
				}
				if (b.getWorld().getName().equalsIgnoreCase("hg")) {
					if (KitListener.hgkit.containsKey(p)) {
						if ((hg.contains(p)) && (!hgin.contains(p))) {
							jump.remove(p);

							p.getInventory().clear();
							p.getInventory().setArmorContents(null);

							for (int i = 0; i < 36; i++) {
								ItemStack sopa = new ItemStack(
										Material.MUSHROOM_SOUP);
								ItemMeta smeta = sopa.getItemMeta();
								smeta.setDisplayName("§2Sopa de Cogumelos");
								sopa.setItemMeta(smeta);
								p.getInventory().setItem(i, sopa);
							}
							ItemStack espada = new ItemStack(
									Material.STONE_SWORD);
							ItemMeta em = espada.getItemMeta();
							em.setDisplayName("§6Espada HG");
							espada.setItemMeta(em);

							ItemStack espadam = new ItemStack(
									Material.WOOD_SWORD);
							ItemMeta emm = espadam.getItemMeta();
							emm.setDisplayName("§6Espada HG");
							espadam.setItemMeta(emm);

							// ITEMS KITS
							ItemStack kkangaroo = new ItemStack(
									Material.FIREWORK);
							ItemMeta metakkangaroo = kkangaroo.getItemMeta();
							metakkangaroo.setDisplayName("§bKangaroo");
							kkangaroo.setItemMeta(metakkangaroo);

							ItemStack karcher = new ItemStack(Material.BOW);
							ItemMeta metakarcher = karcher.getItemMeta();
							metakarcher.setDisplayName("§2Arco do Legolas");
							karcher.setItemMeta(metakarcher);
							karcher.addEnchantment(Enchantment.ARROW_INFINITE,
									1);
							karcher.addEnchantment(Enchantment.ARROW_KNOCKBACK,
									1);
							karcher.addUnsafeEnchantment(
									Enchantment.DURABILITY, 10);

							ItemStack kfisherman = new ItemStack(
									Material.FISHING_ROD);
							ItemMeta metakfisherman = kfisherman.getItemMeta();
							metakfisherman.setDisplayName("§3Fisherman");
							kfisherman.setItemMeta(metakfisherman);

							ItemStack kmonk = new ItemStack(Material.BLAZE_ROD);
							ItemMeta metakmonk = kmonk.getItemMeta();
							metakmonk.setDisplayName("§1Monk");
							kmonk.setItemMeta(metakmonk);

							ItemStack kurgal = new ItemStack(Material.POTION,
									3, (short) 8201);
							ItemMeta metakurgal = kurgal.getItemMeta();
							metakurgal.setDisplayName("§4Urgal");
							kurgal.setItemMeta(metakurgal);

							ItemStack kendermage = new ItemStack(
									Material.PORTAL);
							ItemMeta im = kendermage.getItemMeta();
							im.setDisplayName("§5Endermage");
							kendermage.setItemMeta(im);

							ItemStack kstomper = new ItemStack(
									Material.LEATHER_BOOTS);
							LeatherArmorMeta kbotam = (LeatherArmorMeta) kstomper
									.getItemMeta();
							kbotam.setColor(Color.RED);
							kstomper.setItemMeta(kbotam);

							// KITS
							if (KitListener.hgkit.get(p) == "normal") {
								p.getInventory().setItem(0, espada);
								p.getInventory().setChestplate(
										new ItemStack(
												Material.LEATHER_CHESTPLATE));
								p.getInventory().setBoots(
										new ItemStack(Material.LEATHER_BOOTS));
							} else if (KitListener.hgkit.get(p) == "kangaroo") {
								p.getInventory().setItem(0, espadam);
								p.getInventory().setItem(1, kkangaroo);
								p.getInventory().setChestplate(
										new ItemStack(
												Material.LEATHER_CHESTPLATE));
								p.getInventory().setBoots(
										new ItemStack(Material.LEATHER_BOOTS));
							} else if (KitListener.hgkit.get(p) == "archer") {
								p.getInventory().setItem(0, espadam);
								p.getInventory().setItem(1, karcher);
								p.getInventory().setItem(2,
										new ItemStack(Material.ARROW));
								p.getInventory().setChestplate(
										new ItemStack(
												Material.LEATHER_CHESTPLATE));
								p.getInventory().setBoots(
										new ItemStack(Material.LEATHER_BOOTS));
							} else if (KitListener.hgkit.get(p) == "fisherman") {
								p.getInventory().setItem(0, espadam);
								p.getInventory().setItem(1, kfisherman);
								p.getInventory().setChestplate(
										new ItemStack(
												Material.LEATHER_CHESTPLATE));
								p.getInventory().setBoots(
										new ItemStack(Material.LEATHER_BOOTS));
							} else if (KitListener.hgkit.get(p) == "monk") {
								p.getInventory().setItem(0, espadam);
								p.getInventory().setItem(1, kmonk);
								p.getInventory().setChestplate(
										new ItemStack(
												Material.LEATHER_CHESTPLATE));
								p.getInventory().setBoots(
										new ItemStack(Material.LEATHER_BOOTS));
							} else if (KitListener.hgkit.get(p) == "urgal") {
								p.getInventory().setItem(0, espadam);
								p.getInventory().setItem(1, kurgal);
								p.getInventory().setChestplate(
										new ItemStack(
												Material.LEATHER_CHESTPLATE));
								p.getInventory().setBoots(
										new ItemStack(Material.LEATHER_BOOTS));
							} else if (KitListener.hgkit.get(p) == "endermage") {
								p.getInventory().setItem(0, espadam);
								p.getInventory().setItem(1, kendermage);
								p.getInventory().setChestplate(
										new ItemStack(
												Material.LEATHER_CHESTPLATE));
								p.getInventory().setBoots(
										new ItemStack(Material.LEATHER_BOOTS));
							} else if (KitListener.hgkit.get(p) == "stomper") {
								p.getInventory().setItem(0, espadam);
								p.getInventory().setChestplate(
										new ItemStack(
												Material.LEATHER_CHESTPLATE));
								p.getInventory().setBoots(kstomper);
							} else if (KitListener.hgkit.get(p) == "ninja") {
								p.getInventory().setItem(0, espadam);
								p.getInventory().setChestplate(
										new ItemStack(
												Material.LEATHER_CHESTPLATE));
								p.getInventory().setBoots(
										new ItemStack(Material.LEATHER_BOOTS));
							}

							// GERAIS

							p.getInventory().setItem(13,
									new ItemStack(Material.BOWL, 64));
							p.getInventory().setItem(14,
									new ItemStack(Material.RED_MUSHROOM, 64));
							p.getInventory().setItem(15,
									new ItemStack(Material.BROWN_MUSHROOM, 64));

							p.setVelocity(p.getLocation().getDirection()
									.multiply(7.0D));
							p.setVelocity(new Vector(p.getVelocity().getX(),
									1.5D, p.getVelocity().getZ()));
							p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 1F,
									1F);
							p.playEffect(p.getLocation(),
									Effect.MOBSPAWNER_FLAMES, 20);
							p.sendMessage("§6Boa Sorte!");
							hgin.add(p);
							jump.add(p);
						}
					} else {
						p.sendMessage("§c§lEscolha algum kit primeiro!");
						p.teleport(Main.loc("hg", p));
						itensHG(p);
					}
				}
			}
		}
	}

	@EventHandler
	public void aoTp(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		if (hg.contains(p)) {
			if (!e.getTo().getWorld().getName().equalsIgnoreCase("hg")) {
				hg.remove(p);
				hgin.remove(p);
				hgcd.remove(p);
				jump.remove(p);
				KitListener.hgkit.remove(p);
			}
		}
	}

	@EventHandler
	public void onFall(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if ((e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
					&& (jump.contains(p))) {
				e.setCancelled(true);
				jump.remove(p);
			}
		}
	}
}
