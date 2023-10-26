package me.everton.WocPvP.Listeners;

import java.util.HashMap;

import me.everton.WocPvP.Comandos.Kit;
import me.everton.WocPvP.HG.HG;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class KitListener implements Listener {
	public static HashMap<Player, String> hgkit = new HashMap<>();

	@EventHandler
	public void invAction(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if ((e.getInventory().getName().equalsIgnoreCase("§4Kits - 1"))
				|| (e.getInventory().getName().equalsIgnoreCase("§4Kits - 2"))) {
			p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);

			if (e.getSlot() == 0) {
				Kit.InvKits(p, "1");
			} else if (e.getSlot() == 8) {
				Kit.InvKits(p, "2");
			} else {
				if (e.getCurrentItem().getType() == Material.STONE_SWORD) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit pvp");
					return;
				}
				if (e.getCurrentItem().getType() == Material.FIREWORK_CHARGE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit pyro");
					return;
				}
				if (e.getCurrentItem().getType() == Material.FIRE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit berserker");
					return;
				}
				if (e.getCurrentItem().getType() == Material.IRON_CHESTPLATE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit soldado");
					return;
				}
				if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit shooter");
					return;
				}
				if (e.getCurrentItem().getType() == Material.COMPASS) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit ninja");
					return;
				}
				if (e.getCurrentItem().getType() == Material.SAPLING) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit rhino");
					return;
				}
				if (e.getCurrentItem().getType() == Material.DIAMOND_CHESTPLATE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit trocador");
					return;
				}
				if (e.getCurrentItem().getType() == Material.POTION) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit urgal");
					return;
				}
				if (e.getCurrentItem().getType() == Material.BOW) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit arqueiro");
					return;
				}
				if (e.getCurrentItem().getType() == Material.MUSHROOM_SOUP) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit vitality");
					return;
				}
				if (e.getCurrentItem().getType() == Material.COAL) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit darkmage");
					return;
				}
				if (e.getCurrentItem().getType() == Material.WATER_BUCKET) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit poseidon");
					return;
				}
				if (e.getCurrentItem().getType() == Material.LAVA_BUCKET) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit fireman");
					return;
				}
				if (e.getCurrentItem().getType() == Material.REDSTONE_TORCH_ON) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit flash");
					return;
				}
				if (e.getCurrentItem().getType() == Material.ANVIL) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit anchor");
					return;
				}
				if (e.getCurrentItem().getType() == Material.SPONGE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit launcher");
					return;
				}
				if (e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit turtle");
					return;
				}
				if (e.getCurrentItem().getType() == Material.BONE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit skeleton");
					return;
				}
				if (e.getCurrentItem().getType() == Material.MILK_BUCKET) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit milkman");
					return;
				}
				if (e.getCurrentItem().getType() == Material.FISHING_ROD) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit fisherman");
					return;
				}
				if (e.getCurrentItem().getType() == Material.TNT) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit tank");
					return;
				}
				if (e.getCurrentItem().getType() == Material.SAND) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit camel");
					return;
				}
				if (e.getCurrentItem().getType() == Material.STONE_AXE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit thor");
					return;
				}
				if (e.getCurrentItem().getType() == Material.EXP_BOTTLE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit specialist");
					return;
				}
				if (e.getCurrentItem().getType() == Material.SNOW_BLOCK) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit frosty");
					return;
				}
				if (e.getCurrentItem().getType() == Material.FEATHER) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit phantom");
					return;
				}
				if (e.getCurrentItem().getType() == Material.FIREWORK) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit Kangaroo");
					return;
				}
				if (e.getCurrentItem().getType() == Material.PORTAL) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit endermage");
					return;
				}
				if (e.getCurrentItem().getType() == Material.IRON_BOOTS) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit stomper");
					return;
				}
				if (e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit turtle");
					return;
				}
				if (e.getCurrentItem().getType() == Material.WOOD_HOE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit reaper");
					return;
				}
				if (e.getCurrentItem().getType() == Material.SPIDER_EYE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit viper");
					return;
				}
				if (e.getCurrentItem().getType() == Material.SOUL_SAND) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit snail");
					return;
				}
				if (e.getCurrentItem().getType() == Material.BLAZE_ROD) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit monk");
					return;
				}
				if (e.getCurrentItem().getType() == Material.SNOW_BALL) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit switcher");
					return;
				}
				if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit wither");
					return;
				}
				if (e.getCurrentItem().getType() == Material.DISPENSER) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit granadier");
					return;
				}
				if (e.getCurrentItem().getType() == Material.IRON_FENCE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit gladiator");
					return;
				}
				if (e.getCurrentItem().getType() == Material.TRAP_DOOR) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit jumper");
					return;
				}
				if (e.getCurrentItem().getType() == Material.JACK_O_LANTERN) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit madman");
					return;
				}
				if (e.getCurrentItem().getType() == Material.GOLD_AXE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit viking");
					return;
				}
				if (e.getCurrentItem().getType() == Material.STICK) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit grandpa");
					return;
				}
				if (e.getCurrentItem().getType() == Material.IRON_HOE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit ghost");
					return;
				}
				if (e.getCurrentItem().getType() == Material.BOWL) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit quickdropper");
					return;
				}
				if (e.getCurrentItem().getType() == Material.ENDER_PEARL) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit teleporter");
					return;
				}
				if (e.getCurrentItem().getType() == Material.WEB) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit spiderman");
					return;
				}
				if (e.getCurrentItem().getType() == Material.IRON_ORE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit barbarian");
					return;
				}
				if (e.getCurrentItem().getType() == Material.REDSTONE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit berserker");
					return;
				}
				if (e.getCurrentItem().getType() == Material.PUMPKIN_SEEDS) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit indio");
					return;
				}
				if (e.getCurrentItem().getType() == Material.ICE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit freezer");
					return;
				}
				if (e.getCurrentItem().getType() == Material.BEACON) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit ryu");
					return;
				}
				if (e.getCurrentItem().getType() == Material.EYE_OF_ENDER) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit neji");
					return;
				}
				if (e.getCurrentItem().getType() == Material.MONSTER_EGG) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit lobisomem");
					return;
				}
				if (e.getCurrentItem().getType() == Material.EGG) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit neo");
					return;
				}
				if (e.getCurrentItem().getType() == Material.SADDLE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit hulk");
					return;
				}
				if (e.getCurrentItem().getType() == Material.GOLDEN_APPLE) {
					e.setCancelled(true);
					p.closeInventory();
					p.chat("/kit critical");
					return;
				}
			}

			e.setCancelled(true);
		} else if ((e.getInventory().getName()
				.equalsIgnoreCase("§4Kits HG - Free"))
				|| (e.getInventory().getName()
						.equalsIgnoreCase("§4Kits HG - Vips"))) {
			p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
			if (e.getCurrentItem().getType() == Material.DIAMOND) {
				e.setCancelled(true);
				Kit.InvKits(p, "hg2");
			} else if (e.getCurrentItem().getType() == Material.IRON_INGOT) {
				e.setCancelled(true);
				Kit.InvKits(p, "hg1");
			} else {
				//
				//
				// KITS
				// KITS
				//
				//
				if (!HG.hgin.contains(p)) {
					if (e.getCurrentItem().getType() == Material.IRON_BOOTS) {
						e.setCancelled(true);
						if ((p.hasPermission("tag.vip"))
								|| (p.hasPermission("tag.pro"))
								|| (p.hasPermission("tag.youtuber"))
								|| (p.hasPermission("tag.trial"))
								|| (p.hasPermission("tag.mod"))
								|| (p.hasPermission("tag.admin"))
								|| (p.hasPermission("tag.dono"))
								|| (p.hasPermission("*"))) {
							p.closeInventory();
							UltraKits.KitItems.kitstomper(p);
							p.getInventory().clear();
							p.getInventory().setArmorContents(null);
							HG.itensHG(p);
							p.sendMessage("§3Você escolheu o kit Stomper! Passe pela esponja para ganhá-lo!");
							hgkit.put(p, "stomper");

						} else {
							p.sendMessage("§cVocê nao possui este kit! Possua-o comprando Vip com /loja!");
							e.setCancelled(true);
							p.closeInventory();
						}
						return;
					}

					if (e.getCurrentItem().getType() == Material.PORTAL) {
						e.setCancelled(true);
						if ((p.hasPermission("tag.vip"))
								|| (p.hasPermission("tag.pro"))
								|| (p.hasPermission("tag.youtuber"))
								|| (p.hasPermission("tag.trial"))
								|| (p.hasPermission("tag.mod"))
								|| (p.hasPermission("tag.admin"))
								|| (p.hasPermission("tag.dono"))
								|| (p.hasPermission("*"))) {
							p.closeInventory();
							UltraKits.KitItems.kitEndermage(p);
							p.getInventory().clear();
							p.getInventory().setArmorContents(null);
							HG.itensHG(p);
							p.sendMessage("§3Você escolheu o kit Endermage! Passe pela esponja para ganhá-lo!");
							hgkit.put(p, "endermage");

						} else {
							p.sendMessage("§cVocê nao possui este kit! Possua-o comprando Vip com /loja!");
							e.setCancelled(true);
							p.closeInventory();
						}
						return;
					}

					if (e.getCurrentItem().getType() == Material.COMPASS) {
						e.setCancelled(true);
						if ((p.hasPermission("tag.vip"))
								|| (p.hasPermission("tag.pro"))
								|| (p.hasPermission("tag.youtuber"))
								|| (p.hasPermission("tag.trial"))
								|| (p.hasPermission("tag.mod"))
								|| (p.hasPermission("tag.admin"))
								|| (p.hasPermission("tag.dono"))
								|| (p.hasPermission("*"))) {
							p.closeInventory();
							UltraKits.KitItems.kitNinja(p);
							p.getInventory().clear();
							p.getInventory().setArmorContents(null);
							HG.itensHG(p);
							p.sendMessage("§3Você escolheu o kit Ninja! Passe pela esponja para ganhá-lo!");
							hgkit.put(p, "ninja");

						} else {
							p.sendMessage("§cVocê nao possui este kit! Possua-o comprando Vip com /loja!");
							e.setCancelled(true);
							p.closeInventory();
						}
						return;
					}

					if (e.getCurrentItem().getType() == Material.POTION) {
						e.setCancelled(true);
						if ((p.hasPermission("tag.vip"))
								|| (p.hasPermission("tag.pro"))
								|| (p.hasPermission("tag.youtuber"))
								|| (p.hasPermission("tag.trial"))
								|| (p.hasPermission("tag.mod"))
								|| (p.hasPermission("tag.admin"))
								|| (p.hasPermission("tag.dono"))
								|| (p.hasPermission("*"))) {
							p.closeInventory();
							UltraKits.KitItems.kitUrgal(p);
							p.getInventory().clear();
							p.getInventory().setArmorContents(null);
							HG.itensHG(p);
							p.sendMessage("§3Você escolheu o kit Urgal! Passe pela esponja para ganhá-lo!");
							hgkit.put(p, "urgal");

						} else {
							p.sendMessage("§cVocê nao possui este kit! Possua-o comprando Vip com /loja!");
							e.setCancelled(true);
							p.closeInventory();
						}
						return;
					}

					if (e.getCurrentItem().getType() == Material.FIREWORK) {
						e.setCancelled(true);

						p.closeInventory();
						UltraKits.KitItems.kitKangaroo(p);
						p.getInventory().clear();
						p.getInventory().setArmorContents(null);
						HG.itensHG(p);
						p.sendMessage("§3Você escolheu o kit Kangaroo! Passe pela esponja para ganhá-lo!");
						hgkit.put(p, "kangaroo");
						return;
					}

					if (e.getCurrentItem().getType() == Material.BLAZE_ROD) {
						e.setCancelled(true);

						p.closeInventory();
						UltraKits.KitItems.kitMonk(p);
						p.getInventory().clear();
						p.getInventory().setArmorContents(null);
						HG.itensHG(p);
						p.sendMessage("§3Você escolheu o kit Monk! Passe pela esponja para ganhá-lo!");
						hgkit.put(p, "monk");
						return;
					}
					if (e.getCurrentItem().getType() == Material.BOW) {
						e.setCancelled(true);

						p.closeInventory();
						UltraKits.KitItems.kitArcher(p);
						p.getInventory().clear();
						p.getInventory().setArmorContents(null);
						HG.itensHG(p);
						hgkit.put(p, "archer");
						p.sendMessage("§3Você escolheu o kit Archer! Passe pela esponja para ganhá-lo!");
						return;
					}
					if (e.getCurrentItem().getType() == Material.FISHING_ROD) {
						e.setCancelled(true);

						p.closeInventory();
						UltraKits.KitItems.kitFisherman(p);
						p.getInventory().clear();
						p.getInventory().setArmorContents(null);
						HG.itensHG(p);
						p.sendMessage("§3Você escolheu o kit Fisherman! Passe pela esponja para ganhá-lo!");
						hgkit.put(p, "fisherman");

						return;
					}

					if (e.getCurrentItem().getType() == Material.STONE_SWORD) {
						e.setCancelled(true);

						p.closeInventory();
						UltraKits.KitItems.kitPvP(p);
						p.getInventory().clear();
						p.getInventory().setArmorContents(null);
						HG.itensHG(p);
						p.sendMessage("§3Você escolheu o kit Normal! Passe pela esponja para ganhá-lo!");
						hgkit.put(p, "normal");
						return;
					}
				} else {
					p.sendMessage("§cVocê ja esta usando um kit!");
				}
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void aoClicar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.ENDER_CHEST) {
			if ((e.getAction() == Action.RIGHT_CLICK_AIR)
					|| (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				if (HG.hg.contains(p)) {
					Kit.InvKits(p, "hg1");
				} else {
					Kit.InvKits(p, "1");
				}
			}
		}
	}
}
