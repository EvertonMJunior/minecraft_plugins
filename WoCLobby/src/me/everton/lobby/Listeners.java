package me.everton.lobby;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.lobby.tags.TagCmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class Listeners implements Listener {
	
	public static ArrayList<String> onHided = new ArrayList<>();
	public static HashMap<String, Integer> cdHide = new HashMap<>();
	public static HashMap<String, Integer> taskHide = new HashMap<>();
	
	public static void openInv(Player p, int inv) {
		if(inv == 1) {
			Inventory in = Bukkit.createInventory(p, 27, "§4§lServidores");
			for(int i = 0; i < in.getSize(); i++) {
				in.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0", 7));
			}
			in.setItem(12, API.item(Material.DIAMOND_SWORD, 1, "§6§lKitPvP"));
			in.setItem(14, API.item(Material.MUSHROOM_SOUP, 1, "§6§lHG"));
			p.openInventory(in);
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
		} else if(inv == 2) {
			Inventory in = Bukkit.createInventory(p, 27, "§4§lServidores - HG");
			for(int i = 0; i < in.getSize(); i++) {
				in.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0", 7));
			}
			in.setItem(10, API.item(Material.MUSHROOM_SOUP, 1, "§6§lHG 1"));
			in.setItem(11, API.item(Material.MUSHROOM_SOUP, 2, "§6§lHG 2"));
			
			p.openInventory(in);
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void openSelector(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if(!e.getAction().name().contains("RIGHT")) {
			return;
		}
		
		if(p.getItemInHand().getType() == Material.COMPASS) {
			openInv(p, 1);
		}
		if(p.getItemInHand().getType() == Material.INK_SACK) {
			if((p.getItemInHand().getData().getData() == 8) || (p.getItemInHand().getData().getData() == 10)) {
				if(taskHide.containsKey(p.getName())) {
					p.sendMessage("§cAguarde " + cdHide.get(p.getName()) + " segundos!");
					return;
				}
				int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
					int tempo = 5;
					@Override
					public void run() {
						if(tempo > 0) {
							cdHide.put(p.getName(), tempo);
							tempo--;
						} else if(tempo == 0) {
							cdHide.remove(p.getName());
							Bukkit.getScheduler().cancelTask(taskHide.get(p.getName()));
							taskHide.remove(p.getName());
						}
					}
				}, 0L, 20L);
				
				taskHide.put(p.getName(), task);
			}
			
			if(p.getItemInHand().getData().getData() == 8) {
				p.setItemInHand(API.item(Material.INK_SACK, 1, "§aMostrar Jogadores", 10));
				onHided.add(p.getName());
				for(Player on : Bukkit.getOnlinePlayers()) {
					if(!API.isRanked(p)) {
						p.hidePlayer(on);
					}
				}
			} else if(p.getItemInHand().getData().getData() == 10) {
				p.setItemInHand(API.item(Material.INK_SACK, 1, "§cEsconder Jogadores", 8));
				onHided.remove(p.getName());
				for(Player on : Bukkit.getOnlinePlayers()) {
					if(AdminMode.admins.contains(on.getName())) {
						return;
					}
					p.showPlayer(on);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void invClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Servidores")) {
			e.setCancelled(true);
			e.setCursor(null);
			if(e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
				Main.sendServer(p, "pvp");
			} else if(e.getCurrentItem().getType() == Material.MUSHROOM_SOUP) {
				openInv(p, 2);
			}
		} else if(ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Servidores - HG")) {
			e.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		API.sendTitle(p, "§6WoC§bNetwork", "§9Bem-Vindo!", 1, 3, 1);
		API.setHeaderFooter(p, "§6WoC§bNetwork", "§9Você está no Lobby!");
		
		TagCmd.updatePrefixes(p);
		TagCmd.setTag(p);
		p.getInventory().clear();
		p.setHealth(20.0D);
		p.setFoodLevel(20);
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(0, API.item(Material.COMPASS, 1, "§c§lServidores"));
		p.getInventory().setItem(8, API.item(Material.INK_SACK, 1, "§cEsconder Jogadores", 8));
		e.setJoinMessage(null);
		p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
		if(!p.hasPermission("woc.cmd.admin")) {
			for(Player on : Bukkit.getOnlinePlayers()) {
				on.sendMessage("§7[§a+§7] " + p.getName());
				if(AdminMode.admins.contains(on.getName())) {
					if(Bukkit.getPlayerExact(on.getName()) != null) {
						p.hidePlayer(Bukkit.getPlayerExact(on.getName()));
					}
				}
			}
		} else {
			AdminMode.entrarOuSair(p);
			for (String admins : AdminMode.admins) {
				if(Bukkit.getPlayerExact(admins) != null) {
					Player admin = Bukkit.getPlayerExact(admins);
					p.showPlayer(admin);
				}
			}
			Specs.vendospecs.add(p.getName());
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onSpawnC(CreatureSpawnEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage("§7[§c-§7] " + p.getName());
		API.deleteArrayList(onHided, p.getName());
		API.deleteArrayList(AdminMode.admins, p.getName());
		API.deleteHashMapKey(AdminMode.inv, p.getName());
		API.deleteHashMapKey(AdminMode.armor, p.getName());
		API.deleteArrayList(Specs.vendospecs, p.getName());
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("woc.chatcolor")) {
			e.setFormat(p.getDisplayName() + " §6§l>> §7" + e.getMessage());
		} else {
			e.setFormat(p.getDisplayName() + " §6§l>> §7" + e.getMessage().replace("&", "§"));
		}
	}
}
