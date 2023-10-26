package me.everton.epvp.AntiCheat;

import java.util.HashMap;

import me.everton.epvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MacroTest implements Listener,CommandExecutor{
	public static HashMap<String, String> testador = new HashMap<>();
	public static HashMap<String, Integer> clicks = new HashMap<>();
	public static HashMap<String, Integer> sons = new HashMap<>();
	
	public static boolean isInt(String s) {
		for (int a = 0; a < s.length(); a++) {
			if (a == 0 && s.charAt(a) == '-')
				continue;
			if (!Character.isDigit(s.charAt(a)))
				return false;
		}
		return true;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("macrotest")) {
			if(!p.hasPermission("pvp.admin")) {
				p.sendMessage("§7[§c!§7] Permissoes Insuficientes!");
				return true;
			}
			
			if(args.length == 2) {
				if(Bukkit.getPlayerExact(args[0]) == null) {
					p.sendMessage("§7[§c!§7] Jogador Offline!");
					return true;
				}
				Player t = Bukkit.getPlayerExact(args[0]);
				if(!isInt(args[1])) {
					p.sendMessage("♠BÜ◙j*¬");
					return true;
				}
				if(Integer.valueOf(args[1]) == 1) {
					clicks.put(t.getName(), 0);
					testador.put(t.getName(), p.getName());
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						@Override
						public void run() {
							if(clicks.get(t.getName()) >= 5) {
								p.sendMessage("§7[§c!§7] " + t.getName() + " deu " + clicks.get(t.getName()) + " clicks em 5 segundos! (" + (Integer.valueOf(clicks.get(t.getName()) / 5)) + " p/s)");
							} else {
								p.sendMessage("§7[§c!§7] " + t.getName() + " deu " + clicks.get(t.getName()) + " clicks em 5 segundos!");
							}
							clicks.remove(t.getName());
							testador.remove(t.getName());
						}
					}, 5 * 20L);
				} else if(Integer.valueOf(args[1]) == 2) {
					testador.put(t.getName(), p.getName());
					sons.put(t.getName(), 0);
					Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						@Override
						public void run() {
							sons.remove(t.getName());
							testador.remove(t.getName());
						}
					}, 5 * 20L);
				} else {
					p.sendMessage("§cUse 1 para saber quantos clicks o jogador dá em 5 segundos.");
					p.sendMessage("§cUse 2 para escutar os clicks do jogador por 5 segundos.");
				}
			} else {
				p.sendMessage("§cUse /macrotest <jogador> <1 ou 2>");
			}
		}
		return false;
	}
	
	@EventHandler
	public void hitsAr(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(clicks.containsKey(p.getName())) {
			if((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
				/*if(Bukkit.getPlayerExact(testador.get(p.getName())) != null) {
					Player t = Bukkit.getPlayerExact(testador.get(p.getName()));
				}*/
				
				clicks.put(p.getName(), clicks.get(p.getName()) + 1);
			}
		} else if(sons.containsKey(p.getName())) {
			if((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
				if(Bukkit.getPlayerExact(testador.get(p.getName())) != null) {
					Player t = Bukkit.getPlayerExact(testador.get(p.getName()));
					t.playSound(t.getLocation(), Sound.HURT_FLESH, 1F, 1F);
				}
			}
		}
	}
	
	@EventHandler
	public void hits(EntityDamageByEntityEvent e) {
		if((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
			Player p = (Player) e.getDamager();
			if(clicks.containsKey(p.getName())) {
					/*if(Bukkit.getPlayerExact(testador.get(p.getName())) != null) {
						Player t = Bukkit.getPlayerExact(testador.get(p.getName()));
					}*/
					
					clicks.put(p.getName(), clicks.get(p.getName()) + 1);
			} else if(sons.containsKey(p.getName())) {
					if(Bukkit.getPlayerExact(testador.get(p.getName())) != null) {
						Player t = Bukkit.getPlayerExact(testador.get(p.getName()));
						t.playSound(t.getLocation(), Sound.HURT_FLESH, 1F, 1F);
					}
			}
		}
	}
	
	@EventHandler
	public void invClicks(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(clicks.containsKey(p.getName())) {
				/*if(Bukkit.getPlayerExact(testador.get(p.getName())) != null) {
					Player t = Bukkit.getPlayerExact(testador.get(p.getName()));
				}*/
				
				clicks.put(p.getName(), clicks.get(p.getName()) + 1);
		} else if(sons.containsKey(p.getName())) {
				if(Bukkit.getPlayerExact(testador.get(p.getName())) != null) {
					Player t = Bukkit.getPlayerExact(testador.get(p.getName()));
					t.playSound(t.getLocation(), Sound.CLICK, 1F, 1F);
				}
		}
	}
	
}
