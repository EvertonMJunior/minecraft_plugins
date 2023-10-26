package me.everton.pvp.cmds;

import me.everton.pvp.API;
import me.everton.pvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Troll implements Listener, CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cComando in-game!");
			return true;
		}
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("troll")) {
			if (!p.hasPermission("pvp.cmd.troll")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			if (args.length == 1) {
				Player t = Bukkit.getPlayerExact(args[0]);
				if (t == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}
				Inventory inv = Bukkit.createInventory(p, 9,
						"Troll - " + t.getName());
				inv.setItem(0, API.item(Material.TNT, 1, "§c➤§l Explodir"));
				inv.setItem(1,
						API.item(Material.JUKEBOX, 1, "§c➤§l Sons Loucos"));
				inv.setItem(2, API.item(Material.BEDROCK, 1, "§c➤§l Cage"));
				inv.setItem(3,
						API.item(Material.FLINT_AND_STEEL, 1, "§c➤§l Queimar"));
				inv.setItem(4,
						API.item(Material.FIREWORK, 1, "§c➤§l Jogar para Cima"));
				inv.setItem(5,
						API.item(Material.MAGMA_CREAM, 1, "§c➤§l Crashar"));
				inv.setItem(6, API.item(Material.BOWL, 1, "§c➤§l Dropar Itens"));
				inv.setItem(7,
						API.item(Material.DIAMOND_SWORD, 1, "§c➤§l Matar"));
				inv.setItem(8, API.item(Material.EMERALD, 1, "§c➤§l OP Fake"));

				p.openInventory(inv);
			} else {
				p.sendMessage("§cUse /troll <jogador>");
			}
		}
		return false;
	}

	@EventHandler
	public void invClick(InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		if (inv == null) {
			return;
		}
		ItemStack i = e.getCurrentItem();

		if (inv.getTitle().contains("Troll - ")) {
			e.setCancelled(true);
			final Player t = Bukkit.getPlayerExact(inv.getTitle().substring(8,
					inv.getTitle().length()));
			if (t == null) {
				p.closeInventory();
				p.sendMessage("§cO jogador saiu! §7("
						+ inv.getTitle().substring(8, inv.getTitle().length())
						+ ")");
				return;
			}
			p.closeInventory();

			if (i.getType() == Material.TNT) {
				p.closeInventory();
				for (int z = 0; z < 6; z++) {
					final int zi = z;
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {

								@Override
								public void run() {
									if (zi == 5) {
										if (t.getGameMode() == GameMode.CREATIVE) {
											t.setGameMode(GameMode.SURVIVAL);
										}
										t.getWorld().createExplosion(
												t.getLocation().getX(),
												t.getLocation().getY(),
												t.getLocation().getZ(), 15.0F);
										p.sendMessage("§aExplodido com sucesso! :D");
									} else {
										p.sendMessage("§cExplodindo "
												+ t.getName() + " em "
												+ (5 - zi) + " segundos!");
									}
								}
							}, z * 20L);
				}
			}

			if (i.getType() == Material.JUKEBOX) {
				for (int z = 0; z < 101; z++) {
					final int zi = z;
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {

								@Override
								public void run() {
									t.playSound(t.getLocation(),
											Sound.NOTE_BASS, 15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.NOTE_PLING, 15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.LEVEL_UP, 15.5F, 15.5F);
									t.playSound(t.getLocation(), Sound.CLICK,
											15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.DIG_GRASS, 15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.DIG_SNOW, 15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.DIG_WOOL, 15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.DIG_WOOD, 15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.NOTE_PLING, 15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.ENDERDRAGON_DEATH, 15.5F,
											15.5F);
									t.playSound(t.getLocation(),
											Sound.ANVIL_LAND, 15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.BAT_DEATH, 15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.ZOMBIE_INFECT, 15.5F, 15.5F);
									t.playSound(t.getLocation(),
											Sound.COW_HURT, 15.5F, 15.5F);
									if (zi == 100) {
										p.sendMessage("§aSons Loucos tocados! :D");
									}
								}
							}, z * 1L);
				}
			}

			if (i.getType() == Material.BEDROCK) {
				Location l = t.getLocation().clone();
				l.clone().subtract(0, 1, 0).getBlock()
						.setType(Material.BEDROCK);
				l.clone().add(0, 2, 0).getBlock().setType(Material.BEDROCK);
				l.clone().add(1, 0, 0).getBlock().setType(Material.BEDROCK);
				l.clone().add(0, 0, 1).getBlock().setType(Material.BEDROCK);
				l.clone().subtract(1, 0, 0).getBlock()
						.setType(Material.BEDROCK);
				l.clone().subtract(0, 0, 1).getBlock()
						.setType(Material.BEDROCK);
				t.teleport(l.getBlock().getLocation().clone().add(0.5, 0, 0.5));
				p.sendMessage("§aCage criada! :D");
			}

			if (i.getType() == Material.FLINT_AND_STEEL) {
				t.setFireTicks(999999);
				p.sendMessage("§a" + t.getName()
						+ " pegará fogo até a morte! :D");
			}

			if (i.getType() == Material.FIREWORK) {
				t.teleport(t.getLocation().clone().add(0, 0.5, 0));
				t.setVelocity(new Vector(0, 9, 0));
				p.sendMessage("§aJogado para o alto! :D");
			}

			if (i.getType() == Material.MAGMA_CREAM) {
				;
				p.sendMessage("§aCrashado! :D");
				t.setHealthScale(465654656465465484.10);
			}

			if (i.getType() == Material.BOWL) {
				for (ItemStack it : t.getInventory().getContents()) {
					if (it != null) {
						if (it.getType() != Material.AIR) {
							Item item = t.getWorld().dropItem(
									t.getEyeLocation(), it);
							item.setVelocity(t.getEyeLocation().getDirection()
									.multiply(0.5D));
							item.setPickupDelay(999999);
							it.setType(Material.AIR);
						}
					}
				}

				for (ItemStack it : t.getInventory().getArmorContents()) {
					if (it != null) {
						if (it.getType() != Material.AIR) {
							Item item = t.getWorld().dropItem(
									t.getEyeLocation(), it);
							item.setVelocity(t.getEyeLocation().getDirection()
									.multiply(0.5D));
							item.setPickupDelay(999999);
							it.setType(Material.AIR);
						}
					}
				}
				t.getInventory().clear();
				t.getInventory().setArmorContents(null);
				t.updateInventory();

				p.sendMessage("§aItens Dropados!");
			}

			if (i.getType() == Material.DIAMOND_SWORD) {
				p.sendMessage("§aAssasinado! :D");
				t.setHealth(0D);
			}

			if (i.getType() == Material.EMERALD) {
				p.sendMessage("§aMensagem de OP Fake mandada!");
				t.sendMessage("§7§o[CONSOLE: Opped "
						+ t.getName().toLowerCase() + "]");
			}
		}
	}
}
