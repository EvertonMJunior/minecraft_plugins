package me.everton.WocPvP.Comandos;

import java.util.ArrayList;
import java.util.HashMap;

import me.confuser.barapi.BarAPI;
import me.everton.WocPvP.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Admin implements CommandExecutor, Listener {
	public static Inventory hacks;
	public static Player rightclick;
	public static ArrayList<Player> admin = new ArrayList<>();
	public static ArrayList<Player> vis = new ArrayList<>();
	public static ArrayList<Player> pot = new ArrayList<>();
	public HashMap<String, ItemStack[]> inv = new HashMap<>();
	public HashMap<String, ItemStack[]> armor = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("adm")) {
			if (p.hasPermission("wocpvp.admin")) {
				if (!(admin.contains(p))) {
					for (Player on : Bukkit.getServer().getOnlinePlayers()) {
						on.hidePlayer(p);
						on.sendMessage("§7[§c-§7] " + p.getDisplayName());
					}
					for (Player vendospec : Specs.vendospecs) {
						vendospec.showPlayer(p);
					}
					admin.add(p);
					vis.add(p);
					Main.log(sender.getName() + " entrou no modo Admin.");
					this.inv.put(p.getName(), p.getInventory().getContents());
					this.armor.put(p.getName(), p.getInventory()
							.getArmorContents());
					p.getInventory().setArmorContents(null);
					p.getInventory().clear();
					ItemStack openInv = new ItemStack(Material.BLAZE_ROD, 1);
					ItemMeta openInvmeta = openInv.getItemMeta();
					openInvmeta.setDisplayName(ChatColor.RED
							+ "Abrir Inventário");
					openInv.setItemMeta(openInvmeta);

					ItemStack stickKb = new ItemStack(Material.BONE, 1);
					stickKb.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
					ItemMeta stickKbmeta = stickKb.getItemMeta();
					stickKbmeta.setDisplayName(ChatColor.AQUA
							+ "Testar Anti-Kb");
					stickKb.setItemMeta(stickKbmeta);

					ItemStack testFF = new ItemStack(Material.NETHER_STAR, 1);
					ItemMeta testFFmeta = testFF.getItemMeta();
					testFFmeta.setDisplayName(ChatColor.GREEN
							+ "Testar FF/KillAura");
					testFF.setItemMeta(testFFmeta);

					ItemStack tHacks = new ItemStack(Material.ARROW, 1);
					ItemMeta tHacksmeta = tHacks.getItemMeta();
					tHacksmeta.setDisplayName(ChatColor.RED + "Testar Hacks");
					tHacks.setItemMeta(tHacksmeta);
					p.getInventory().setItem(3, tHacks);

					ItemStack trocaR = new ItemStack(Material.MAGMA_CREAM, 1);
					ItemMeta trocaRmeta = trocaR.getItemMeta();
					trocaRmeta.setDisplayName(ChatColor.GOLD + "Troca Rápida");
					trocaR.setItemMeta(trocaRmeta);
					p.getInventory().setItem(5, trocaR);

					ItemStack i = new ItemStack(Material.SKULL_ITEM, 1,
							(short) SkullType.PLAYER.ordinal());
					SkullMeta m = (SkullMeta) i.getItemMeta();
					m.setDisplayName("§bJogadores Onlines");
					m.setOwner("Notch");
					i.setItemMeta(m);
					p.getInventory().setItem(4, i);

					ItemStack autoS = new ItemStack(Material.MUSHROOM_SOUP, 1);
					ItemMeta autoSmeta = autoS.getItemMeta();
					autoSmeta.setDisplayName(ChatColor.DARK_PURPLE
							+ "Testar Auto-Soup");
					autoS.setItemMeta(autoSmeta);

					hacks = Bukkit.createInventory(p, 9, "Teste Hack");
					hacks.setItem(2, stickKb);
					hacks.setItem(4, testFF);
					hacks.setItem(6, autoS);

					p.setGameMode(GameMode.CREATIVE);
					BarAPI.setMessage(p, ChatColor.AQUA
							+ "Você entrou no modo admin!", 2);
					p.sendMessage(ChatColor.LIGHT_PURPLE
							+ "Você entrou no modo admin!");

				} else if (admin.contains(p)) {
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						pl.showPlayer(p);
					}
					admin.remove(p);
					vis.remove(p);
					Main.log(sender.getName() + " saiu do modo Admin.");
					p.getInventory().clear();
					p.getInventory().setArmorContents(
							(ItemStack[]) this.armor.get(p.getName()));
					p.getInventory().setContents(
							(ItemStack[]) this.inv.get(p.getName()));
					inv.remove(p);
					armor.remove(p);
					p.setHealth(20D);
					p.setFoodLevel(20);
					p.setGameMode(GameMode.SURVIVAL);
					p.removePotionEffect(PotionEffectType.INVISIBILITY);

					BarAPI.setMessage(p, ChatColor.AQUA
							+ "Você saiu do modo admin!", 2);
					p.sendMessage(ChatColor.LIGHT_PURPLE
							+ "Você saiu do modo admin!");
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("invis")) {
			if (p.hasPermission("wocpvp.admin")) {
				if (!pot.contains(p)) {
					p.sendMessage("§cPoçao de invisibilidade ativada!");
					Main.log(sender.getName()
							+ " ficou com poção de invisibilidade.");
					p.addPotionEffect(new PotionEffect(
							PotionEffectType.INVISIBILITY, 100000000, 100000));
					pot.add(p);
				} else if (pot.contains(p)) {
					p.sendMessage("§cPoçao de invisibilidade desativada!");
					Main.log(sender.getName()
							+ " tirou a poção de invisibilidade.");
					p.removePotionEffect(PotionEffectType.INVISIBILITY);
					pot.remove(p);
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("vis")) {
			if (p.hasPermission("wocpvp.admin")) {
				if (!vis.contains(p)) {
					for (Player on : Bukkit.getServer().getOnlinePlayers()) {
						on.hidePlayer(p);
					}
					vis.add(p);
					for (Player vendospec : Specs.vendospecs) {
						vendospec.showPlayer(p);
					}
					BarAPI.setMessage(p, ChatColor.GOLD
							+ "Agora você está invisível!", 2);
					p.sendMessage(ChatColor.GOLD + "Agora você está invisível!");
					Main.log(sender.getName() + " ficou invisível.");
				} else if (vis.contains(p)) {
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						pl.showPlayer(p);
					}
					vis.remove(p);
					BarAPI.setMessage(p, ChatColor.GOLD
							+ "Agora você está visível!", 2);
					p.sendMessage(ChatColor.GOLD + "Agora você está visível!");
					Main.log(sender.getName() + " ficou visível.");
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}

	public static void verInv(Player p, Player de) {
		PlayerInventory inv = de.getInventory();
		p.openInventory(inv);
		Main.log(p.getName() + " abriu o inventário de " + de.getName());
	}

	@EventHandler
	public void eventoClick(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.PLAYER) {
			rightclick = (Player) e.getRightClicked();
			if (admin.contains(e.getPlayer())) {
				if (e.getPlayer().hasPermission("wocpvp.admin")) {
					String playerclicado = rightclick.getName();
					if (e.getPlayer().getItemInHand().getType() == Material.AIR) {
						if (e.getRightClicked().getType() == EntityType.PLAYER) {
							e.getPlayer().sendMessage(
									"§e[§9WoCPvP§e]" + ChatColor.GREEN
											+ ChatColor.GREEN
											+ " Você abriu o inventário de "
											+ playerclicado);
							BarAPI.setMessage(e.getPlayer(), ChatColor.GREEN
									+ "Você abriu o inventário de "
									+ playerclicado, 2);
							verInv(e.getPlayer(), rightclick);

						}
					}

					if (e.getPlayer().getItemInHand().getType() == Material.ARROW) {
						if (e.getRightClicked().getType() == EntityType.PLAYER) {
							e.getPlayer().sendMessage(
									"§e[§9WoCPvP§e]" + ChatColor.GREEN
											+ ChatColor.GREEN + " Teste se "
											+ playerclicado
											+ " está usando hack!");
							BarAPI.setMessage(e.getPlayer(), ChatColor.GREEN
									+ "Testando se " + playerclicado
									+ " está usando hack!", 2);
							e.getPlayer().openInventory(hacks);

						}
					}
				}
			}
		}
	}

	@EventHandler
	public void CancelarPickDrop(PlayerPickupItemEvent e) {
		if (admin.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void InteracaoItem(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (admin.contains(e.getPlayer())) {
			if (e.getPlayer().hasPermission("wocpvp.admin")) {
				if (e.getPlayer().getItemInHand().getType() == Material.MAGMA_CREAM) {
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						pl.showPlayer(p);
						pl.hidePlayer(p);
					}
					for (Player specs : Specs.vendospecs) {
						specs.showPlayer(p);
					}
					BarAPI.setMessage(p, ChatColor.AQUA
							+ "Você apareceu e desapareceu rapidamente!", 2);
					p.sendMessage(ChatColor.AQUA
							+ "Você apareceu e desapareceu rapidamente!");
					Main.log(p.getName()
							+ " apareceu e desapareceu rapidamente no modo Admin.");
				}
				if (e.getPlayer().getItemInHand().getType() == Material.SKULL_ITEM) {
					Spec.abrirGuiSpectador(e.getPlayer(),
							Bukkit.getOnlinePlayers(), 6, false);
				}
			}
		}
	}

	@EventHandler
	public void InteracaoInv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (admin.contains(p)) {
			if (e.getInventory().getName().equalsIgnoreCase("Teste Hack")) {
				if (e.getSlot() == 2) {
					rightclick.setVelocity(rightclick.getVelocity().setY(0.2D));
					p.sendMessage("§e[§9WoCPvP§e]" + ChatColor.GREEN
							+ " Testando AK de " + rightclick.getName());
					BarAPI.setMessage(p, ChatColor.GREEN + "Testando AK de "
							+ rightclick.getName(), 2);
					Main.log(p.getName() + " testou se " + rightclick.getName()
							+ " estava usando Anti-KB.");
					p.closeInventory();
				} else if (e.getSlot() == 4) {
					p.chat("/f " + rightclick.getName());
					p.sendMessage("§e[§9WoCPvP§e]" + ChatColor.GREEN
							+ " Testando FF/KA de " + rightclick.getName());
					BarAPI.setMessage(p, ChatColor.GREEN + "Testando FF/KA de "
							+ rightclick.getName(), 2);
					Main.log(p.getName() + " testou se " + rightclick.getName()
							+ " estava usando KillAura/FF.");
					p.closeInventory();
				} else if (e.getSlot() == 6) {
					p.chat("/autosoup " + rightclick.getName());
					p.sendMessage("§e[§9WoCPvP§e]" + ChatColor.GREEN
							+ " Testando AutoSoup de " + rightclick.getName());
					BarAPI.setMessage(p, ChatColor.GREEN
							+ "Testando AutoSoup de " + rightclick.getName(), 2);
				}
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		vis.remove(e.getPlayer());
		admin.remove(e.getPlayer());
	}

	@EventHandler
	public void aoPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (admin.contains(p)) {
			if (p.getItemInHand().getType() == Material.SKULL_ITEM) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void CancelarInv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (admin.contains(p)) {
			if (e.getInventory().getName()
					.equalsIgnoreCase("§4§lJogadores Onlines")) {
				if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
					Player cl = Bukkit.getPlayerExact(e.getCurrentItem()
							.getItemMeta().getDisplayName());
					if (cl != p) {
						p.setFlying(true);
						Location clloc = new Location(cl.getWorld(), cl
								.getLocation().getX(),
								cl.getLocation().getY() + 6, cl.getLocation()
										.getZ());
						p.teleport(clloc);
					} else {
						p.sendMessage("§cVocê nao pode teleportar-se a si mesmo!");
						p.closeInventory();
					}
				}
				e.setCancelled(true);
			}

		}
	}
}
