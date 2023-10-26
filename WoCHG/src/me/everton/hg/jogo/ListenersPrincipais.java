package me.everton.hg.jogo;

import java.util.Random;

import me.everton.hg.API;
import me.everton.hg.Main;
import me.everton.hg.Cmds.Admin;
import me.everton.hg.Cmds.Tag;
import me.everton.hg.ScoreBoard.Board;
import me.everton.hg.jogo.Timers.TimerInvencibilidade;
import me.everton.hg.jogo.Timers.TimerJogo;
import me.everton.hg.jogo.Timers.TimerPreJogo;
import me.everton.hg.kits.KitManager;
import me.everton.hg.kits.KitSelector;
import me.everton.hg.kits.KitType;
import me.everton.hg.kits.KitSelector.Inv;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

public class ListenersPrincipais implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.setScoreboard(Main.Scoreboard);

		if (Main.PRE_JOGO) {
			API.itensIniciais(p);
			if (!Main.kit.containsKey(p.getName())) {
				Main.kit.put(p.getName(), KitType.NONE);
			}
			if (!Main.kills.containsKey(p.getName())) {
				Main.kills.put(p.getName(), 0);
			}

			if (new Random().nextBoolean()) {
				p.teleport(Main.spawn.add(new Random().nextInt(10), 0,
						new Random().nextInt(10)));
			} else {
				p.teleport(Main.spawn.subtract(new Random().nextInt(10), 0,
						new Random().nextInt(10)));
			}
		}

		if (!Main.participando.contains(p.getName())) {
			Main.participando.add(p.getName());
			if (p.hasPermission("hg.admin")) {
				Admin.entrarAdmin(p);
			}
		}

		e.setJoinMessage(null);

		Tag.setTag(p);

		if (Main.PRE_JOGO) {
			if (TimerPreJogo.shed_id == null) {
				TimerPreJogo.contagem();
			}
		} else if (Main.INVENCIBILIDADE) {
			if (TimerInvencibilidade.shed_id == null) {
				TimerInvencibilidade.contagem();
			}
		} else if (Main.EM_JOGO) {
			if (TimerJogo.shed_id == null) {
				TimerJogo.contagem();
			}
		}

		Board.refreshScore(p);
	}

	@EventHandler
	public void pegarDrops(PlayerPickupItemEvent e) {
		if (Main.PRE_JOGO) {
			e.setCancelled(true);
		}
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void droparItens(final PlayerDropItemEvent e) {
		if (Main.PRE_JOGO) {
			e.setCancelled(true);
			if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
				return;
			}
			Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				@Override
				public void run() {
					API.itensIniciais(e.getPlayer());
				}
			}, 5L);
		}

		try {
			String itemName = e.getItemDrop().getItemStack().getItemMeta()
					.getDisplayName();

			if (itemName.equalsIgnoreCase(KitManager.getKit(e.getPlayer())
					.getItemKit().getItemMeta().getDisplayName())) {
				e.setCancelled(true);
			}
		} catch (Exception ex) {

		}
	}

	@EventHandler
	public void inventoryInteract(InventoryClickEvent e) {
		if (Main.PRE_JOGO) {
			if (e.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
				return;
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMobTarget(EntityTargetLivingEntityEvent e) {
		if (Main.PRE_JOGO) {
			e.setCancelled(true);
		} else if (Main.INVENCIBILIDADE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMobTarget(EntityTargetEvent e) {
		if (Main.PRE_JOGO) {
			e.setCancelled(true);
		} else if (Main.INVENCIBILIDADE) {
			e.setCancelled(true);
		}
	}

	// DOUBLE-JUMP
	@EventHandler
	public void moveDoubleJump(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (Main.PRE_JOGO && Main.TEMPO_PREJOGO > 10) {
			if ((p.getGameMode() != GameMode.CREATIVE)
					&& (p.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR)
					&& (!p.isFlying())) {
				p.setAllowFlight(true);
			}
		}
	}

	@EventHandler
	public void toggleFlightDoubleJump(PlayerToggleFlightEvent e) {
		final Player p = e.getPlayer();
		if (Main.PRE_JOGO && Main.TEMPO_PREJOGO > 10) {
			if (p.getGameMode() != GameMode.CREATIVE) {
				e.setCancelled(true);
				p.setAllowFlight(false);
				p.setFlying(false);
				p.setVelocity(p.getLocation().getDirection().multiply(1.5D)
						.setY(0.5D));
				for (int i = 0; i < 20; i++) {
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {

								@Override
								public void run() {
									for (int z = 0; z < 25; z++) {
										p.getWorld().playEffect(
												p.getLocation(),
												Effect.FIREWORKS_SPARK, 1);
									}
									p.getWorld().playEffect(p.getLocation(),
											Effect.COLOURED_DUST, 1);
									p.getWorld().playEffect(p.getLocation(),
											Effect.MOBSPAWNER_FLAMES, 1);
								}
							}, i);
				}
				p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 1F, 1F);
			}
		}
	}

	// DOUBLE-JUMP

	@EventHandler
	public void playerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (Main.PRE_JOGO) {
			if (e.getAction().name().contains("RIGHT")) {
				if (p.getItemInHand().getType() == Material.ENDER_CHEST) {
					KitSelector.openSelector(p, Inv.SEUS_KITS);
				}
			}

			if (p.getGameMode() == GameMode.CREATIVE) {
				return;
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void noMoveKitItemOtherInv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (e.getAction().name().contains("PLACE")) {
			// if(e.getClickedInventory() != )

			try {
				if (e.getCurrentItem().getItemMeta().getDisplayName() == KitManager
						.getKit(p).getItemKit().getItemMeta().getDisplayName()) {
					e.setCancelled(true);
					e.setResult(org.bukkit.event.Event.Result.DENY);
					p.updateInventory();
				}
			} catch (Exception ex) {

			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void sopas(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		Damageable hp = p;

		if (ArenaFinal.blocosArena.contains(e.getClickedBlock())) {
			if (!e.getAction().name().contains("BLOCK")) {
				return;
			}
			e.setCancelled(true);
			e.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
			e.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
		}
		
		if (Feast.blocos.contains(e.getClickedBlock())) {
			if (!e.getAction().name().contains("BLOCK")) {
				return;
			}
			e.setCancelled(true);
			e.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
			e.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
		}

		int sopa = 7;
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (p.getItemInHand().getType() == Material.MUSHROOM_SOUP)) {
			if (hp.getHealth() != 20) {
				p.setHealth(hp.getHealth() + sopa > hp.getMaxHealth() ? hp
						.getMaxHealth() : hp.getHealth() + sopa);
				if (KitManager.getKit(p) == KitType.QUICKDROPPER) {
					p.getItemInHand().setType(Material.BOWL);
					if (!Main.STATE_KITS) {
						p.sendMessage("§7[§c!§7] Os kits estao desativados!");
						return;
					}
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {
								@Override
								public void run() {
									p.setItemInHand(new ItemStack(Material.AIR));
									p.getWorld().dropItemNaturally(
											p.getLocation(),
											API.item(Material.BOWL));
								}
							}, 1L);
				} else {
					p.getItemInHand().setType(Material.BOWL);
				}
			} else if (p.getFoodLevel() != 20) {
				p.setFoodLevel(p.getFoodLevel() + 6 > 20 ? 20 : p
						.getFoodLevel() + 6);
				if (KitManager.getKit(p) == KitType.QUICKDROPPER) {
					p.getItemInHand().setType(Material.BOWL);
					if (!Main.STATE_KITS) {
						p.sendMessage("§7[§c!§7] Os kits estao desativados!");
						return;
					}
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
							new Runnable() {
								@Override
								public void run() {
									p.setItemInHand(new ItemStack(Material.AIR));
									p.getWorld().dropItemNaturally(
											p.getLocation(),
											API.item(Material.BOWL));
								}
							}, 1L);
				} else {
					p.getItemInHand().setType(Material.BOWL);
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if ((Main.INVENCIBILIDADE) || (Main.PRE_JOGO)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void buildingLimit(BlockPlaceEvent e) {
		if (e.getBlock().getLocation().getY() >= 128) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(
					"§cO limite de construçao é de 128 blocos!");
		}
	}

	@EventHandler
	public void buildingLimit2(PlayerBucketEmptyEvent e) {
		if (e.getBlockClicked().getRelative(BlockFace.UP).getLocation().getY() >= 128) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(
					"§cO limite de construçao é de 128 blocos!");
		}
	}

	@EventHandler
	public void aoExplodir(EntityExplodeEvent e) {
		if (Main.PRE_JOGO) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void aoExplodir(ExplosionPrimeEvent e) {
		if (Main.PRE_JOGO) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if ((Main.PRE_JOGO) && (!p.isOp())
				&& (p.getGameMode() != GameMode.CREATIVE)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if ((Main.PRE_JOGO) && (!p.isOp())
				&& (p.getGameMode() != GameMode.CREATIVE)) {
			e.setCancelled(true);
		}
	}

	public static String nomeItem(ItemStack i) {
		String nome = null;
		if (i.getType() == Material.AIR) {
			nome = "Soco";
		} else if (i.getType() == Material.WOOD_SWORD) {
			nome = "Espada de Madeira";
		} else if (i.getType() == Material.STONE_SWORD) {
			nome = "Espada de Pedra";
		} else if (i.getType() == Material.IRON_SWORD) {
			nome = "Espada de Ferro";
		} else if (i.getType() == Material.DIAMOND_SWORD) {
			nome = "Espada de Diamante";
		} else if (i.getType() == Material.GOLD_SWORD) {
			nome = "Espada de Ouro";
		} else if (i.getType() == Material.WOOD_AXE) {
			nome = "Machado de Madeira";
		} else if (i.getType() == Material.STONE_AXE) {
			nome = "Machado de Pedra";
		} else if (i.getType() == Material.IRON_AXE) {
			nome = "Machado de Ferro";
		} else if (i.getType() == Material.DIAMOND_AXE) {
			nome = "Machado de Diamante";
		} else if (i.getType() == Material.GOLD_AXE) {
			nome = "Machado de Ouro";
		} else if (i.getType() == Material.WOOD_SPADE) {
			nome = "Pa de Madeira";
		} else if (i.getType() == Material.STONE_SPADE) {
			nome = "Pa de Pedra";
		} else if (i.getType() == Material.IRON_SPADE) {
			nome = "Pa de Ferro";
		} else if (i.getType() == Material.DIAMOND_SPADE) {
			nome = "Pa de Diamante";
		} else if (i.getType() == Material.GOLD_SPADE) {
			nome = "Pa de Ouro";
		} else if (i.getType() == Material.WOOD_PICKAXE) {
			nome = "Picareta de Madeira";
		} else if (i.getType() == Material.STONE_PICKAXE) {
			nome = "Picareta de Pedra";
		} else if (i.getType() == Material.IRON_PICKAXE) {
			nome = "Picareta de Ferro";
		} else if (i.getType() == Material.DIAMOND_PICKAXE) {
			nome = "Picareta de Diamante";
		} else if (i.getType() == Material.GOLD_PICKAXE) {
			nome = "Picareta de Ouro";
		} else if (i.getType() == Material.STICK) {
			nome = "Graveto";
		} else if (i.getType() == Material.MUSHROOM_SOUP) {
			nome = "Sopa";
		} else if (i.getType() == Material.BOWL) {
			nome = "Tigela";
		} else if (i.getType() == Material.COMPASS) {
			nome = "Bussola";
		} else {
			nome = i.getType().toString().toLowerCase();
		}
		return nome;
	}

	@EventHandler
	public void preLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPermission("hg.admin")) {
			if ((!p.hasPermission("hg.spec")) && (Main.EM_JOGO)
					&& (Main.TEMPO_JOGO > 300)) {
				e.disallow(
						Result.KICK_OTHER,
						"§7[§3§lWoCHG§7] \n§bO jogo ja iniciou! \n§6Compre vip em loja.wocpvp.com e especte partidas!");
			} else if ((!p.hasPermission("hg.vip.join")) && (Main.EM_JOGO)
					&& (Main.TEMPO_JOGO <= 300)) {
				e.disallow(
						Result.KICK_OTHER,
						"§7[§3§lWoCHG§7] \n§bO jogo ja iniciou! \n§6Compre vip em loja.wocpvp.com e entre até 5 minutos depois do início!");
			}
		}
	}

	@EventHandler
	public void onFoodLevel(FoodLevelChangeEvent e) {
		if (Main.PRE_JOGO || Main.INVENCIBILIDADE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		e.setQuitMessage(null);
		if (!Admin.admins.contains(p.getName())) {
			if(!Main.PRE_JOGO) {
				e.setQuitMessage("§e" + p.getName() + " saiu do servidor.");
				Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

					@Override
					public void run() {
						if (Bukkit.getPlayerExact(p.getName()) == null) {
							Main.participando.remove(p.getName());
							Bukkit.broadcastMessage("§b"
									+ p.getName()
									+ " demorou para relogar e foi desclassificado!");
						}
					}
				}, 60 * 20L);
			}
		} else {
			Admin.admins.remove(p.getName());
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();

		e.setDeathMessage(null);
		if ((p.getKiller() != null) && ((p.getKiller() instanceof Player))) {
			Player matou = p.getKiller();
			Main.kills
					.put(matou.getName(), Main.kills.get(matou.getName()) + 1);
		}
		p.setHealth(20.0D);
		EntityDamageEvent cause = e.getEntity().getLastDamageCause();
		String deathMessage = ChatColor.stripColor(e.getDeathMessage());
		String ps = p.getName() + "("
				+ ChatColor.stripColor(Main.kit.get(p.getName()).getName())
				+ ")";
		if (cause != null) {
			if ((cause instanceof EntityDamageByEntityEvent)) {
				EntityDamageByEntityEvent entitye = (EntityDamageByEntityEvent) cause;
				if ((entitye.getDamager() instanceof Player)) {
					Player dmg = (Player) entitye.getDamager();
					deathMessage = String.format(
							ChatColor.AQUA + "%s matou %s usando uma %s",
							new Object[] { dmg.getName() + "(" + "Kit" + ")",
									p.getName() + "(" + "Kit" + ")",
									nomeItem(dmg.getItemInHand()) });
				} else if ((entitye.getDamager() instanceof Monster)) {
					deathMessage = String
							.format(ChatColor.AQUA
									+ "%s morreu para um monstro!",
									new Object[] { ps });
				} else if ((entitye.getDamager() instanceof Arrow)) {
					deathMessage = String
							.format(ChatColor.AQUA
									+ "%s morreu para um instrumento usado por Legolas!",
									new Object[] { ps });
				}
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.FALL) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s nao lembrou de abrir o paraquedas e morreu!",
						new Object[] { ps });
			} else if ((cause.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
					|| (cause.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu explodido!", new Object[] { ps });
			} else if ((cause.getCause() == EntityDamageEvent.DamageCause.FIRE)
					|| (cause.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)
					|| (cause.getCause() == EntityDamageEvent.DamageCause.LAVA)
					|| (cause.getCause() == EntityDamageEvent.DamageCause.MELTING)) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu queimado!", new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s esqueceu da máscara de mergulho e morreu!",
						new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu para um raio de Jesuisss!",
						new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
				deathMessage = String
						.format(ChatColor.AQUA
								+ "%s morreu esmagado! Que burro!",
								new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu para um monstro!", new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.POISON) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu de veneno!", new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu para um monstro!", new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
				deathMessage = String
						.format(ChatColor.AQUA
								+ "%s morreu de fome! Falo nada..",
								new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu sufocado!", new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
				deathMessage = String.format(ChatColor.AQUA + "%s se matou!",
						new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.THORNS) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu para espinhos!", new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.VOID) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu para o void!", new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.WITHER) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu para efeito de wither!",
						new Object[] { ps });
			} else if (cause.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
				deathMessage = String.format(ChatColor.AQUA
						+ "%s morreu para um cactus!", new Object[] { ps });
			} else {
				deathMessage = String.format(ChatColor.AQUA + "%s morreu para "
						+ cause.getCause().name().toLowerCase() + "!",
						new Object[] { ps });
			}
		}
		OrganizarMorte(p, deathMessage);
	}

	public static void OrganizarMorte(final Player morreu, String deathMessage) {
		Bukkit.broadcastMessage(deathMessage);
		Player p = morreu;

		API.resetAll(p);
		p.getInventory().addItem(API.item(Material.COMPASS, 1, "§6Bússola"));
		if(KitManager.getKit(p).getItemKit() != null) {
			p.getInventory().addItem(KitManager.getKit(p).getItemKit());
		}

		if ((morreu.hasPermission("hg.respawn")) && (Main.TEMPO_JOGO <= 300)) {
			Bukkit.getServer().getScheduler()
					.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						public void run() {
							API.respawnPlayer(morreu);
						}
					}, 2L);
			return;
		}
		if (morreu.hasPermission("hg.admin")) {
			Bukkit.getServer().broadcastMessage(
					"§e" + ChatColor.stripColor(morreu.getPlayerListName())
							+ " saiu do servidor");
			morreu.getInventory().clear();
			Admin.entrarAdmin(morreu);
		} else if (morreu.hasPermission("hg.spec")) {
			morreu.getInventory().clear();
			// SETAR SPEC
		} else {
			deathKick(morreu, deathMessage);
		}
	}

	public static void deathKick(Player p, String string) {
		p.kickPlayer(string
				+ "\n"
				+ "§6Compre VIP para espectar depois de morto e renascer em até 5 minutos! \n§6Loja: loja.wocpvp.com");
	}

	@EventHandler
	public void creatureSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof PigZombie)) {
			e.setCancelled(true);
		}
		if ((e.getEntity() instanceof Ghast)) {
			e.setCancelled(true);
		}
		if ((e.getEntity() instanceof MagmaCube)) {
			e.setCancelled(true);
		}
		if ((e.getEntity() instanceof Slime)) {
			e.setCancelled(true);
		}
	}
}
