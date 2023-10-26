package me.everton.epvp.Comandos;

import java.util.ArrayList;

import me.everton.epvp.KitManager;
import me.everton.epvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Spec implements CommandExecutor, Listener {
	public static ArrayList<String> specs = new ArrayList<String>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("spec")) {
			if (p.hasPermission("pvp.spec")) {
				if (!(specs.contains(p.getName()))) {
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						pl.hidePlayer(p);
					}
					for (String vendospec : Specs.vendospecs) {
						if(Bukkit.getPlayerExact(vendospec) != null) {
							Player vspec = Bukkit.getPlayerExact(vendospec);
							vspec.showPlayer(p);
						}
					}
					specs.add(p.getName());
					Admin.vis.add(p.getName());
					p.setGameMode(GameMode.CREATIVE);
					p.getInventory().clear();

					ItemStack info = new ItemStack(Material.BLAZE_ROD);
					ItemMeta im = info.getItemMeta();
					im.setDisplayName("§6§lInformaçoes do Jogador");
					ArrayList<String> il = new ArrayList<>();
					il.add("§cClique em um jogador para");
					il.add("§cver informaçoes sobre ele!");
					im.setLore(il);
					info.setItemMeta(im);
					p.getInventory().setItem(2, info);

					ItemStack warps = new ItemStack(Material.PAPER);
					ItemMeta metawarps = warps.getItemMeta();
					metawarps.setDisplayName("§3§lWarps");
					ArrayList<String> wl = new ArrayList<>();
					wl.add("§cClique neste papel para acessar");
					wl.add("§cas warps!");
					metawarps.setLore(wl);
					warps.setItemMeta(metawarps);
					p.getInventory().setItem(6, warps);

					ItemStack inv = new ItemStack(Material.WATCH);
					ItemMeta metainv = inv.getItemMeta();
					metainv.setDisplayName("§5§lJogadores Onlines");
					ArrayList<String> inl = new ArrayList<>();
					inl.add("§cUse este item para ver todos os jogadores");
					inl.add("§conlines e teleportar-se até eles!");
					metainv.setLore(inl);
					inv.setItemMeta(metainv);
					p.getInventory().setItem(4, inv);
					p.teleport(Main.loc("spawn", p));
					p.sendMessage(ChatColor.LIGHT_PURPLE
							+ "Você entrou no modo espectador!");
				} else if (specs.contains(p.getName())) {
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						pl.showPlayer(p);
					}
					specs.remove(p.getName());
					Admin.vis.remove(p.getName());
					p.getInventory().clear();
					p.setHealth(20D);
					p.setFoodLevel(20);
					p.setGameMode(GameMode.SURVIVAL);
					p.getInventory().clear();
					p.teleport(Main.loc("spawn", p));
					Main.spawnItens(p);
					p.sendMessage(ChatColor.LIGHT_PURPLE
							+ "Você saiu do modo espectador!");
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}

	@EventHandler
	public void Teste(EntityDamageByEntityEvent e) {
		if (specs.contains(e.getDamager())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void CancelarPickDrop(PlayerPickupItemEvent e) {
		if (specs.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}

	private static Inventory spectate;

	public static void abrirGuiSpectador(Player p, Player[] players, int rows,
			Boolean showadmin) {
		ItemStack i = new ItemStack(Material.SKULL_ITEM, 1,
				(short) SkullType.PLAYER.ordinal());
		SkullMeta m = (SkullMeta) i.getItemMeta();
		Spec.spectate = Bukkit.createInventory(p, rows * 9,
				"§4§lJogadores Onlines");
		int adminc = 0;
		for (int z = 0; z < players.length; z++) {
			if (Admin.admins.contains(players[z].getName())) {
				adminc++;
			}
			if (!(players.length > 53)) {
				m.setDisplayName(players[z].getName());
				ArrayList<String> lore = new ArrayList<>();
				m.setOwner(players[z].getName());
				Damageable hp = players[z];
				String h = "" + hp.getHealth();
				String hmax = "" + hp.getMaxHealth();
				lore.add("§cVida: §2" + h.substring(0, 2) + "§c/" + hmax.substring(0, 2));
				lore.add("§cKit: §2" + KitManager.kitUsando(players[z]));
				m.setLore(lore);
				i.setItemMeta(m);
				if (!showadmin) {
					if (!Admin.admins.contains(players[z].getName()))  {
						Spec.spectate.setItem(z - adminc, i);
					}
				} else {
					Spec.spectate.setItem(z, i);
				}
				p.openInventory(Spec.spectate);
			} else if ((players.length > 53)) {
				p.sendMessage("§aSuportando por enquanto apenas 53 jogadores onlines!");
			}
		}
	}

	@EventHandler
	public void CancelarInteracao(PlayerInteractEvent e) {
		if (specs.contains(e.getPlayer().getName())) {
			Player p = e.getPlayer();
			if (e.getPlayer().getItemInHand().getType() == Material.WATCH) {
				abrirGuiSpectador(p, Bukkit.getOnlinePlayers(), 6, false);
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void CancelarInteracao(PlayerInteractEntityEvent e) {
		if ((e.getRightClicked() instanceof Player)) {
			Player r = (Player) e.getRightClicked();
			Player p = e.getPlayer();
			if (specs.contains(p.getName())) {
				if (p.getItemInHand().getType() == Material.BLAZE_ROD) {
					Damageable hp = r;
					String h = "" + hp.getHealth();
					String hmax = "" + hp.getMaxHealth();
					p.sendMessage("§6Nome: §3§l" + ChatColor.stripColor(r.getDisplayName()));
					p.sendMessage("§6Vida: §3§l" + h.substring(0, 2) + "/"
							+ hmax.substring(0, 2));
				}
				e.setCancelled(true);

			}
		}
	}

	@EventHandler
	public void aoDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (specs.contains(p.getName())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void CancelarInv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (specs.contains(p.getName())) {
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
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void aoMexer(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (specs.contains(p.getName())) {
			for (Entity ent : p.getNearbyEntities(2, 3, 2)) {
				Player n = (Player) ent;
				p.teleport(new Location(n.getWorld(), n.getLocation().getX(), n
						.getLocation().getY() + 5, n.getLocation().getZ()));
				p.sendMessage("§6Você nao pode chegar perto de " + n.getName());
				p.setFlying(true);
			}
		}
	}
}
