package me.everton.WocPvP.Comandos;

import java.util.ArrayList;

import me.everton.WocPvP.Main;
import me.everton.WocPvP.Eventos.EManager;

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
	public static ArrayList<Player> specs = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("spec")) {
			if (p.hasPermission("wocpvp.spec")) {
				if (!(specs.contains(p))) {
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						pl.hidePlayer(p);
					}
					for (Player vendospec : Specs.vendospecs) {
						Player vspec = (Player) vendospec;
						vspec.showPlayer(p);
					}
					specs.add(p);
					Admin.vis.add(p);
					Main.log(p.getName() + " entrou no modo Espectador.");
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
				} else if (specs.contains(p)) {
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						pl.showPlayer(p);
					}
					specs.remove(p);
					Admin.vis.remove(p);
					p.getInventory().clear();
					p.setHealth(20D);
					p.setFoodLevel(20);
					p.setGameMode(GameMode.SURVIVAL);
					p.getInventory().clear();
					Main.log(p.getName() + " saiu do modo Espectador.");
					p.teleport(Main.loc("spawn", p));
					Main.spawnItens(p);
					p.sendMessage(ChatColor.LIGHT_PURPLE
							+ "Você saiu do modo espectador!");
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "Você não tem permissão!");
			}
		}
		return false;
	}

	@EventHandler
	public void Teste(EntityDamageByEntityEvent e) {
		if (specs.contains(e.getDamager())) {
			e.setCancelled(true);
		}
		if (EManager.pespec.contains(e.getDamager())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void CancelarPickDrop(PlayerPickupItemEvent e) {
		if (specs.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
		if (EManager.pespec.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	public static String getKit(Player p) {
		if (UltraKits.Main.hg.contains(p.getName())) {
			return "PvP";
		}
		if (UltraKits.Main.arqueiro.contains(p.getName())) {
			return "Arqueiro";
		}
		if (UltraKits.Main.urgal.contains(p.getName())) {
			return "Urgal";
		}
		if (UltraKits.Main.whister.contains(p.getName())) {
			return "Shooter";
		}
		if (UltraKits.Main.pyro.contains(p.getName())) {
			return "Pyro";
		}
		if (UltraKits.Main.Trocador.contains(p.getName())) {
			return "Trocador";
		}
		if (UltraKits.Main.soldado.contains(p.getName())) {
			return "Soldado";
		}
		if (UltraKits.Main.viper.contains(p.getName())) {
			return "Viper";
		}
		if (UltraKits.Main.ninja.contains(p.getName())) {
			return "Ninja";
		}
		if (UltraKits.Main.anchor.contains(p.getName())) {
			return "Anchor";
		}
		if (UltraKits.Main.tank.contains(p.getName())) {
			return "Tank";
		}
		if (UltraKits.Main.switcher.contains(p.getName())) {
			return "Switcher";
		}
		if (UltraKits.Main.darkmage.contains(p.getName())) {
			return "Darkmage";
		}
		if (UltraKits.Main.Teleporter.contains(p.getName())) {
			return "Teleporter";
		}
		if (UltraKits.Main.thor.contains(p.getName())) {
			return "Thor";
		}
		if (UltraKits.Main.specialist.contains(p.getName())) {
			return "Specialist";
		}
		if (UltraKits.Main.launcher.contains(p.getName())) {
			return "Launcher";
		}
		if (UltraKits.Main.milkman.contains(p.getName())) {
			return "Milkman";
		}
		if (UltraKits.Main.skeleton.contains(p.getName())) {
			return "Skeleton";
		}
		if (UltraKits.Main.fisherman.contains(p.getName())) {
			return "Fisherman";
		}
		if (UltraKits.Main.phantom.contains(p.getName())) {
			return "Phantom";
		}
		if (UltraKits.Main.gladiator.contains(p.getName())) {
			return "Gladiator";
		}
		if (UltraKits.Main.flash.contains(p.getName())) {
			return "Flash";
		}
		if (UltraKits.Main.grappler.contains(p.getName())) {
			return "Grappler";
		}
		if (UltraKits.Main.endermage.contains(p.getName())) {
			return "Endermage";
		}
		if (UltraKits.Main.monk.contains(p.getName())) {
			return "Monk";
		}
		if (UltraKits.Main.camel.contains(p.getName())) {
			return "Camel";
		}
		if (UltraKits.Main.frosty.contains(p.getName())) {
			return "Frosty";
		}
		if (UltraKits.Main.wither.contains(p.getName())) {
			return "Wither";
		}
		if (UltraKits.Main.poseidon.contains(p.getName())) {
			return "Poseidon";
		}
		if (UltraKits.Main.stomper.contains(p.getName())) {
			return "Stomper";
		}
		if (UltraKits.Main.reaper.contains(p.getName())) {
			return "Reaper";
		}
		if (UltraKits.Main.pisca.contains(p.getName())) {
			return "Remix";
		}
		if (UltraKits.Main.turtle.contains(p.getName())) {
			return "Turtle";
		}
		if (UltraKits.Main.jumper.contains(p.getName())) {
			return "Jumper";
		}
		if (UltraKits.Main.snail.contains(p.getName())) {
			return "Snail";
		}
		if (UltraKits.Main.fireman.contains(p.getName())) {
			return "Fireman";
		}
		if (UltraKits.Main.kangaroo.contains(p.getName())) {
			return "Kangaroo";
		}
		if (UltraKits.Main.viking.contains(p.getName())) {
			return "Viking";
		}
		if (UltraKits.Main.madman.contains(p.getName())) {
			return "Madman";
		}
		if (UltraKits.Main.grandpa.contains(p.getName())) {
			return "Grandpa";
		}
		if (UltraKits.Main.ghost.contains(p.getName())) {
			return "Ghost";
		}
		if (UltraKits.Main.barbarian.contains(p.getName())) {
			return "Barbarian";
		}
		if (UltraKits.Main.spiderman.contains(p.getName())) {
			return "SpiderMan";
		}
		if (UltraKits.Main.berserker.contains(p.getName())) {
			return "Berserker";
		}
		if (UltraKits.Main.indio.contains(p.getName())) {
			return "Indio";
		}
		if (UltraKits.Main.ryu.contains(p.getName())) {
			return "Ryu";
		}
		if (UltraKits.Main.neji.contains(p.getName())) {
			return "Neji";
		}
		if (UltraKits.Main.granadier.contains(p.getName())) {
			return "Granadier";
		}
		if (UltraKits.Main.rhino.contains(p.getName())) {
			return "Rhino";
		}
		if (UltraKits.Main.alien.contains(p.getName())) {
			return "Neo";
		}
		if (UltraKits.Main.critical.contains(p.getName())) {
			return "Critical";
		}
		if (UltraKits.Main.hulk.contains(p.getName())) {
			return "Hulk";
		}
		if (UltraKits.Main.lobisomem.contains(p.getName())) {
			return "Lobisomem";
		}
		if (UltraKits.Main.phantom.contains(p.getName())) {
			return "Phantom";
		}
		if (UltraKits.Main.vitality.contains(p.getName())) {
			return "Vitality";
		}
		if (UltraKits.Main.quickdropper.contains(p.getName())) {
			return "QuickDropper";
		}
		return "Sem Kit";
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
			if (Admin.admin.contains(players[z])) {
				adminc++;
			}
			if (!(players.length > 53)) {
				m.setDisplayName(players[z].getName());
				ArrayList<String> lore = new ArrayList<>();
				Damageable hp = players[z];
				String h = "" + hp.getHealth();
				String hmax = "" + hp.getMaxHealth();
				lore.add("§cVida: §2" + h.substring(0, 2) + "§c/" + hmax.substring(0, 2));
				lore.add("§cKit: " + Spec.getKit(players[z]));
				m.setLore(lore);
				i.setItemMeta(m);
				if (!showadmin) {
					if (!Admin.admin.contains(players[z])) {
						Spec.spectate.setItem(z - adminc, i);
					}
				}
				p.openInventory(Spec.spectate);
			} else if ((players.length > 53)) {
				p.sendMessage("§aSuportando por enquanto apenas 53 jogadores onlines!");
			}
		}
	}

	@EventHandler
	public void CancelarInteracao(PlayerInteractEvent e) {
		if (specs.contains(e.getPlayer())) {
			Player p = e.getPlayer();
			if (e.getPlayer().getItemInHand().getType() == Material.WATCH) {
				abrirGuiSpectador(p, Bukkit.getOnlinePlayers(), 6, false);
			}
			e.setCancelled(true);
		}
		if (EManager.pespec.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void CancelarInteracao(PlayerInteractEntityEvent e) {
		if ((e.getRightClicked() instanceof Player)) {
			Player r = (Player) e.getRightClicked();
			Player p = e.getPlayer();
			if (specs.contains(p)) {
				if (p.getItemInHand().getType() == Material.BLAZE_ROD) {
					Damageable hp = r;
					String h = "" + hp.getHealth();
					String hmax = "" + hp.getMaxHealth();
					p.sendMessage("§6Nome: §3§l" + ChatColor.stripColor(r.getDisplayName()));
					p.sendMessage("§6Vida: §3§l" + h.substring(0, 2) + "/"
							+ hmax.substring(0, 2));
					p.sendMessage("§6Sopas: §3§l"
							+ Main.getAmount(r, Material.MUSHROOM_SOUP));
				}
				e.setCancelled(true);

			}
		}
	}

	@EventHandler
	public void aoDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (specs.contains(p)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void CancelarInv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (specs.contains(p)) {
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
						p.sendMessage("§cVoc� nao pode teleportar-se a si mesmo!");
						p.closeInventory();
					}
				}
			}
			e.setCancelled(true);
		} else if (EManager.pespec.contains(e.getWhoClicked())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void aoMexer(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (specs.contains(p)) {
			for (Entity ent : p.getNearbyEntities(2, 3, 2)) {
				Player n = (Player) ent;
				p.teleport(new Location(n.getWorld(), n.getLocation().getX(), n
						.getLocation().getY() + 5, n.getLocation().getZ()));
				p.sendMessage("§6Voc� nao pode chegar perto de " + n.getName());
				p.setFlying(true);
			}
		}
	}
}
