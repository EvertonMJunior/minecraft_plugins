package me.everton.pvp.e1v1;

import java.util.HashMap;
import java.util.Random;

import me.everton.pvp.API;
import me.everton.pvp.LocationsManager;
import me.everton.pvp.e1v1.bot.Bot;
import me.everton.pvp.e1v1.bot.Bot.Dificuldade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Desafiar implements Listener {
	public static HashMap<String, String> pendentes = new HashMap<>();
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!e.getAction().name().contains("RIGHT")) {
			return;
		}

		if (!Main1v1.on1v1.contains(p.getName())) {
			return;
		}

		if (p.getItemInHand().getType() != Material.INK_SACK) {
			return;
		}
		desafiar(p, null, Desafio.Rápido);
	}
	
	@EventHandler
	public void onClick2(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!e.getAction().name().contains("RIGHT")) {
			return;
		}

		if (!Main1v1.on1v1.contains(p.getName())) {
			return;
		}

		if (p.getItemInHand().getType() != Material.SKULL_ITEM) {
			return;
		}
		
		Inventory inv = Bukkit.createInventory(p, 27, "1v1 Bot - Dificuldades");
		for(int i = 0; i < 27; i++) {
			inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, " "));
		}
		inv.setItem(11, API.item(Material.WOOL, 1, "§7> §a§lNormal", 5));
		inv.setItem(13, API.item(Material.WOOL, 1, "§7> §e§lMédio", 4));
		inv.setItem(15, API.item(Material.WOOL, 1, "§7> §c§lDifícil", 14));
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack i = e.getCurrentItem();
		if(inv == null) {
			return;
		}
		
		if(inv.getTitle().equalsIgnoreCase("1v1 Bot - Dificuldades")) {
			if(i.getItemMeta().getDisplayName().contains("Normal")) {
				Bot.entrarBattle(p, Dificuldade.NORMAL);
			}
			if(i.getItemMeta().getDisplayName().contains("Médio")) {
				Bot.entrarBattle(p, Dificuldade.MÉDIO);
			}
			if(i.getItemMeta().getDisplayName().contains("Difícil")) {
				Bot.entrarBattle(p, Dificuldade.DIFÍCIL);
			}
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onIE(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (!(e.getRightClicked() instanceof Player)) {
			return;
		}
		Player r = (Player) e.getRightClicked();

		if (!Main1v1.on1v1.contains(r.getName())) {
			return;
		}
		
		if (!Main1v1.on1v1.contains(p.getName())) {
			return;
		}
		
		if(p.getItemInHand().getType() == Material.BLAZE_ROD) {
			desafiar(p, r, Desafio.Normal);
		}
	}
	
	@EventHandler
	public void onDe(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if(e.getEntity().getKiller() instanceof Player) {
			Player k = e.getEntity().getKiller();
			
			if(Main1v1.onBattle.containsKey(p.getName()) && Main1v1.onBattle.containsKey(k.getName())) {
				Main1v1.onBattle.remove(p.getName());
				Main1v1.onBattle.remove(k.getName());
				
				p.teleport(LocationsManager.getLocation("1v1"));
				k.teleport(LocationsManager.getLocation("1v1"));
				p.sendMessage("§7§l1v1 §8> §aVocê perdeu o 1v1 contra " + ChatColor.stripColor(k.getPlayerListName()) + "! Ele ficou com " + API.getAmount(k, Material.MUSHROOM_SOUP) + " sopas e " + (((Damageable) k).getHealth()) + " coraçoes!");
				k.sendMessage("§7§l1v1 §8> §aVocê ganhou o 1v1 contra " + ChatColor.stripColor(p.getPlayerListName()) + "! Ele ficou com " + API.getAmount(p, Material.MUSHROOM_SOUP) + " sopas!");
				Main1v1.giveItens(p);
				Main1v1.giveItens(k);
				
				p.setHealth(20.0D);
				k.setHealth(20.0D);
			}
		}
	}

	public static enum Desafio {
		Normal, Rápido, Refil, RefilEFullIron;
	}

	@SuppressWarnings("deprecation")
	public static void desafiar(Player p, Player d, Desafio de) {
		if (de == Desafio.Rápido) {
			if (Main1v1.fila1v1rapido.contains(p.getName())) {
				p.sendMessage("§7§l1v1 §8> §aVocê saiu da fila do 1v1 rápido!");
				Main1v1.fila1v1rapido.remove(p.getName());
				p.setItemInHand(API.item(Material.INK_SACK, 1,
						"§8> §c§l1v1 Rápido", 8));
			} else {
				p.sendMessage("§7§l1v1 §8> §aVocê entrou na fila do 1v1 rápido!");
				p.setItemInHand(API.item(Material.INK_SACK, 1,
						"§8> §a§l1v1 Rápido", 10));
				Main1v1.fila1v1rapido.add(p.getName());

				Random r = new Random();
				int random = r.nextInt(Main1v1.fila1v1rapido.size());
				if (Main1v1.fila1v1rapido.size() > 1) {
					while (Main1v1.fila1v1rapido.get(random).equalsIgnoreCase(
							p.getName()) && Main1v1.onBattle.containsKey(Main1v1.fila1v1rapido.get(random))) {
						random = r.nextInt(Main1v1.fila1v1rapido.size());
					}
					d = Bukkit
							.getPlayerExact(Main1v1.fila1v1rapido.get(random));
					if (d != null) {
						p.sendMessage("§7§l1v1 §8> §aVocê entrou no 1v1 Rápido contra "
								+ ChatColor.stripColor(d.getPlayerListName()) + "!");
						d.sendMessage("§7§l1v1 §8> §aVocê entrou no 1v1 Rápido contra "
								+ ChatColor.stripColor(p.getPlayerListName()) + "!");

						Kits1v1.kitNormal(p);
						Kits1v1.kitNormal(d);

						p.teleport(LocationsManager.getLocation("1v1pos1"));
						d.teleport(LocationsManager.getLocation("1v1pos2"));
						Main1v1.onBattle.put(p.getName(), d.getName());
						Main1v1.onBattle.put(d.getName(), p.getName());
						
						for(Player on : Bukkit.getOnlinePlayers()) {
							p.hidePlayer(on);
							d.hidePlayer(on);
						}
						p.showPlayer(d);
						d.showPlayer(p);
					}
				}
			}
		} else if(de == Desafio.Normal) {
			if(!Main1v1.on1v1.contains(d.getName())) {
				p.sendMessage("§7§l1v1 §8> §aEste jogador não está no 1v1!");
				return;
			}
			
			if(pendentes.containsKey(p.getName()) && pendentes.get(p.getName()).equalsIgnoreCase(d.getName())) {
				pendentes.remove(p.getName());
				p.sendMessage("§7§l1v1 §8> §aVocê entrou no 1v1 Normal contra "
						+ ChatColor.stripColor(d.getPlayerListName()) + "!");
				d.sendMessage("§7§l1v1 §8> §aVocê entrou no 1v1 Normal contra "
						+ ChatColor.stripColor(p.getPlayerListName()) + "!");

				Kits1v1.kitNormal(p);
				Kits1v1.kitNormal(d);

				p.teleport(LocationsManager.getLocation("1v1pos1"));
				d.teleport(LocationsManager.getLocation("1v1pos2"));
				Main1v1.onBattle.put(p.getName(), d.getName());
				Main1v1.onBattle.put(d.getName(), p.getName());
				
				for(Player on : Bukkit.getOnlinePlayers()) {
					p.hidePlayer(on);
					d.hidePlayer(on);
				}
				p.showPlayer(d);
				d.showPlayer(p);
				return;
			}
			
			if(pendentes.containsKey(d.getName()) && pendentes.get(d.getName()).equalsIgnoreCase(p.getName())) {
				p.sendMessage("§7§l1v1 §8> §aVocê já desafiou " + ChatColor.stripColor(d.getPlayerListName()) + " para um 1v1 normal!");
				return;
			}
			
			if(!pendentes.containsKey(p.getName())) {
				pendentes.put(d.getName(), p.getName());
				p.sendMessage("§7§l1v1 §8> §aVocê desafiou " + ChatColor.stripColor(d.getPlayerListName()) + " para um 1v1 normal!");
				d.sendMessage("§7§l1v1 §8> §aVocê foi desafiado por " + ChatColor.stripColor(p.getPlayerListName()) + " para um 1v1 normal!");
				d.sendMessage("§7§l1v1 §8> §aClique nele com a §6Blaze-Rod §apara aceitar o pedido!");
				return;
			}
		}
	}
}
