package me.everton.od.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import me.confuser.barapi.BarAPI;
import me.everton.od.API;
import me.everton.od.Board;
import me.everton.od.API.GameStage;
import me.everton.od.cmds.Time;
import me.everton.od.Main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class Principais implements Listener {

	private static ArrayList<String> podeEntrar = new ArrayList<>();

	@EventHandler
	public void onjoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		if (API.getGameStage() == GameStage.JOGO) {
			if (!podeEntrar.contains(p.getName())) {
				p.kickPlayer("§7[§b§lObsidian §6§lDestroyer§7] \n§cA partida já começou! \nPlugin criado por §lEvertonPvP");
			} else {
				podeEntrar.remove(p.getName());
			}
		}
		BarAPI.setMessage(p, "§b§lObsidian §6§lDestroyer§a ➤ Bem-Vindo!", 3);
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				BarAPI.setMessage(p, "§b§lObsidian §6§lDestroyer§a ➤ "
						+ API.getGameStage().name() + "!");
			}
		}, 3 * 20L);

		e.setJoinMessage("§e" + p.getName() + " entrou no servidor.");
		
		if(API.getGameStage() == GameStage.PRE_JOGO) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.getInventory().setItem(4, API.item(Material.NETHER_STAR, 1, "§7>> §cSeletor de Times §7(Clique direito)"));
		}
	}

	@EventHandler
	public void onquit(PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		if (API.getGameStage() == GameStage.JOGO) {
			podeEntrar.add(p.getName());
			Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					if (Bukkit.getPlayerExact(p.getName()) == null) {
						Bukkit.broadcastMessage("§7[§c!§7] "
								+ p.getName()
								+ " demorou muito para relogar e foi desclassificado!");
						podeEntrar.remove(p.getName());
					}
				}
			}, 120 * 20L);
		}

		e.setQuitMessage("§e" + p.getName() + " saiu do servidor.");
	}

	private static ArrayList<String> mortos = new ArrayList<>();
	private static HashMap<String, ItemStack[]> itens = new HashMap<>();
	private static HashMap<String, ItemStack[]> armor = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDie(PlayerDeathEvent e) {
		final Player p = e.getEntity();
		Player killer = e.getEntity().getKiller();
		
		if(Main.deaths.containsKey(p.getName())) {
			Main.deaths.put(p.getName(), Main.deaths.get(p.getName()) + 1);
		} else {
			Main.deaths.put(p.getName(), 1);
			
		}
		Board.refresh(p);
			
		p.setHealth(20.0D);
		e.getDrops().clear();

		for (Player on : Bukkit.getOnlinePlayers()) {
			on.hidePlayer(p);
		}
		p.teleport(p.getLocation().clone().add(0, 4, 0));
		p.setGameMode(GameMode.CREATIVE);
		p.setFlying(true);
		mortos.add(p.getName());
		itens.put(p.getName(), p.getInventory().getContents());
		armor.put(p.getName(), p.getInventory().getArmorContents());
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.updateInventory();
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				for (Player on : Bukkit.getOnlinePlayers()) {
					on.showPlayer(p);
				}
				p.setGameMode(GameMode.SURVIVAL);
				p.setFoodLevel(20);
				mortos.remove(p.getName());
				p.getInventory().setContents(itens.get(p.getName()));
				p.getInventory().setArmorContents(armor.get(p.getName()));
				itens.remove(p.getName());
				armor.remove(p.getName());
				p.updateInventory();
				p.teleport(API.getSpawn(p));
			}
		}, 3 * 20L);

		if (killer instanceof Player) {
			if(Main.kills.containsKey(killer.getName())) {
				Main.kills.put(killer.getName(), Main.kills.get(killer.getName()) + 1);
			} else {
				Main.kills.put(killer.getName(), 1);
			}
			Board.refresh(killer);
			Bukkit.broadcastMessage("§b" + p.getName() + " foi morto por "
					+ killer.getName());
		} else {
			Bukkit.broadcastMessage("§b" + p.getName() + " morreu!");
		}
		p.sendMessage("§7[§c!§7] Você respawnará em 3 segundos!");
		p.playSound(p.getLocation(), Sound.NOTE_BASS, 15.5F, 15.5F);
		e.setDeathMessage(null);
	}

	@EventHandler
	public void onDamageEnt(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (mortos.contains(p.getName())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(API.getGameStage() != GameStage.JOGO) {
			e.setCancelled(true);
		}
		if(e.getEntity() instanceof Player) {
			if(mortos.contains(((Player) e.getEntity()).getName())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBurt(BlockBurnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (mortos.contains(p.getName())) {
			e.setCancelled(true);
		}
		if(API.getGameStage() == GameStage.PRE_JOGO) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void invInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(mortos.contains(p.getName())) {
			e.setCancelled(true);
		}
		if(API.getGameStage() == GameStage.PRE_JOGO) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void weatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(API.getGameStage() == GameStage.PRE_JOGO) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPickDrop(PlayerPickupItemEvent e) {
		if(API.getGameStage() == GameStage.PRE_JOGO) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void entityTarget(EntityTargetEvent e) {
		if(Main.INVENCIBILIDADE || Main.PRE_JOGO) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		
		if (msg.contains("%")) {
			p.sendMessage(
					"§7[§c!§7] O símbolo §c§l% §7está desativado!");
			e.setCancelled(true);
			return;
		}
		
		e.setCancelled(true);
		if(API.getGameStage() == GameStage.PRE_JOGO) {
			Bukkit.broadcastMessage(p.getDisplayName() + " §6§l>> §f" + msg.replace("&", "§"));
			return;
		}
		if(Time.getTeam(p).equalsIgnoreCase("vermelho")) {
			if(msg.startsWith("!")) {
				Bukkit.broadcastMessage("§7[§cVERMELHO§7] " + p.getDisplayName() + " §6§l>> §f" + msg.replace("&", "§").substring(1, msg.length()));
				return;
			}
			
			for(OfflinePlayer time : Main.red.getPlayers()) {
				Player t = (Player) time;
				t.sendMessage("§7[§cTIME§7] " + p.getDisplayName() + " §6§l>> §f" + msg.replace("&", "§"));
			}
		} else if(Time.getTeam(p).equalsIgnoreCase("azul")){
			if(msg.startsWith("!")) {
				Bukkit.broadcastMessage("§7[§9AZUL§7] " + p.getDisplayName() + " §6§l>> §f" + msg.replace("&", "§").substring(1, msg.length()));
				return;
			}
			for(OfflinePlayer time : Main.blue.getPlayers()) {
				Player t = (Player) time;
				t.sendMessage("§7[§9TIME§7] " + p.getDisplayName() + " §6§l>> §f" + msg.replace("&", "§"));
			}
		} else {
			Bukkit.broadcastMessage(p.getName() + " §6§l>> §f" + msg.replace("&", "§"));
		}
	}
}
