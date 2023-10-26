package me.everton.epvp.e1v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.confuser.barapi.BarAPI;
import me.everton.epvp.API;
import me.everton.epvp.KitManager;
import me.everton.epvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

public class Main1v1 {
	public static ArrayList<String> on1v1 = new ArrayList<>();
	
	public static HashMap<String, String> desafiados = new HashMap<>();
	public static HashMap<String, String> onbattle = new HashMap<>();
	public static HashMap<String, String> tipo = new HashMap<>();
	
	public static ArrayList<String> cd = new ArrayList<>();
	
	public static ArrayList<String> fast1v1 = new ArrayList<>();
	
	public static void entrarOuSair1v1(Player p) {
		if(on1v1.contains(p.getName())) {
			on1v1.remove(p.getName());
			p.sendMessage("§7[§a!§7] Você saiu do 1v1!");
			desafiados.remove(p.getName());
			onbattle.remove(p.getName());
			cd.remove(p.getName());
			fast1v1.remove(p.getName());
			p.teleport(Main.loc("spawn", p));
			Main.spawnItens(p);
			KitManager.resetKit(p, false);
			p.updateInventory();
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
			BarAPI.setMessage(p, "§c§lVocê saiu do 1v1!", 2);
		} else {
			on1v1.add(p.getName());
			p.sendMessage("§7[§a!§7] Você entrou no 1v1!");
			itens1v1(p);
			p.updateInventory();
			p.teleport(Main.loc("1v1", p));
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
			BarAPI.setMessage(p, "§a§lVocê entrou no 1v1!", 2);
		}
	}
	
	public static void itens1v1(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(0, API.item(Material.BLAZE_ROD, 1, "§61v1 Normal", new String[] {"§bClique nos jogadores com esta", "§bBlaze-Rod e desafie-os para um 1v1 normal!", "§aPara aceitar um pedido de 1v1 normal, clique em quem solicitou!"}));
		p.getInventory().setItem(1, API.item(Material.IRON_INGOT, 1, "§51v1 Com Refil", new String[] {"§bClique nos jogadores com esta", "§bBlaze-Rod e desafie-os para um 1v1 com refil!", "§aPara aceitar um pedido de 1v1 com refil, clique em quem solicitou!"}));
		p.getInventory().setItem(8, API.item(Material.INK_SACK, 1, "§aEntrar na fila do 1v1 rápido", 8));
		p.updateInventory();
	}
	
	public static void itens1v1Normal(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		for(int i = 0; i < 9; i++) {
			p.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
		}
		p.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
		p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		p.updateInventory();
	}
	
	public static void itens1v1Refil(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		for(int i = 0; i < p.getInventory().getSize(); i++) {
			p.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
		}
		p.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
		p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		p.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public static void desafiarPlayer(Player p, Player desafiado, String type) {
		if(!on1v1.contains(desafiado.getName())) {
			p.sendMessage("§7[§c!§7] Este jogador nao está no warp 1v1!");
			return;
		}
		
		if((onbattle.containsKey(desafiado.getName())) || (onbattle.containsValue(desafiado.getName()))) {
			p.sendMessage("§7[§c!§7] Este jogador já está em combate!");
			return;
		}
		
		if(cd.contains(p.getName())) {
			p.sendMessage("§6Aguarde!");
			return;
		}
		
		if((desafiados.containsKey(desafiado.getName())) && (desafiados.get(desafiado.getName()).equalsIgnoreCase(p.getName()))) {
			if(type.equalsIgnoreCase("normal")) {
				if(!tipo.get(desafiado.getName()).equalsIgnoreCase("normal")) {
					tipo.remove(desafiado.getName());
					tipo.put(desafiado.getName(), "refil");
					return;
				}
				p.sendMessage("§a" + ChatColor.stripColor(p.getName()) + " aceitou sua solicitaçao de 1v1 normal!");
				desafiado.sendMessage("§aVocê aceitou a solicitaçao de 1v1 normal de " + ChatColor.stripColor(desafiado.getName()) + "!");
				desafiado.teleport(Main.loc("1v1pos1", desafiado));
				p.teleport(Main.loc("1v1pos2", p));
				onbattle.put(p.getName(), desafiado.getName());
				onbattle.put(desafiado.getName(), p.getName());
				itens1v1Normal(p);
				itens1v1Normal(desafiado);
				p.updateInventory();
				desafiado.updateInventory();
				BarAPI.removeBar(p);
				BarAPI.removeBar(desafiado);
				for(Player on : Bukkit.getOnlinePlayers()) {
					p.hidePlayer(on);
					desafiado.hidePlayer(on);
					p.showPlayer(desafiado);
					desafiado.showPlayer(p);
				}
			} else if(type.equalsIgnoreCase("refil")) {
				if(!tipo.get(desafiado.getName()).equalsIgnoreCase("refil")) {
					tipo.remove(desafiado.getName());
					tipo.put(desafiado.getName(), "normal");
					return;
				}
				p.sendMessage("§a" + ChatColor.stripColor(p.getName()) + " aceitou sua solicitaçao de 1v1 com refil!");
				desafiado.sendMessage("§aVocê aceitou a solicitaçao de 1v1 com refil de " + ChatColor.stripColor(desafiado.getName()) + "!");
				desafiado.teleport(Main.loc("1v1pos1", desafiado));
				p.teleport(Main.loc("1v1pos2", p));
				onbattle.put(p.getName(), desafiado.getName());
				onbattle.put(desafiado.getName(), p.getName());
				itens1v1Refil(p);
				itens1v1Refil(desafiado);
				p.updateInventory();
				desafiado.updateInventory();
				BarAPI.removeBar(p);
				BarAPI.removeBar(desafiado);
				for(Player on : Bukkit.getOnlinePlayers()) {
					p.hidePlayer(on);
					desafiado.hidePlayer(on);
					p.showPlayer(desafiado);
					desafiado.showPlayer(p);
				}
			}
			return;
		}
		
		if(type.equalsIgnoreCase("normal")) {
			p.sendMessage("§6Você desafiou §7" + ChatColor.stripColor(desafiado.getDisplayName()) + " §6para um 1v1 normal!");
			p.sendMessage("§6Aguarde ele aceitar clicando em você com a Blaze-Rod!");
			desafiado.sendMessage("§6Você foi desafiado por §7" + ChatColor.stripColor(p.getDisplayName()) + " §6para um 1v1 normal!");
			desafiado.sendMessage("§6Clique nele com a Blaze-Rod para aceitar!");
			tipo.put(p.getName(), "normal");
		} else if(type.equalsIgnoreCase("refil")) {
			p.sendMessage("§6Você desafiou §7" + ChatColor.stripColor(desafiado.getDisplayName()) + " §6para um 1v1 com refil!");
			p.sendMessage("§6Aguarde ele aceitar clicando em você com o Iron!");
			desafiado.sendMessage("§6Você foi desafiado por §7" + ChatColor.stripColor(p.getDisplayName()) + " §6para um 1v1 com refil!");
			desafiado.sendMessage("§6Clique nele com o Iron para aceitar!");
			tipo.put(p.getName(), "refil");
		}
		
		cd.add(p.getName());
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				if(cd.contains(p.getName())) {
					cd.remove(p.getName());
				}
				if(desafiados.containsKey(p.getName())) {
					desafiados.remove(p.getName());
				}
				if(tipo.containsKey(p.getName())) {
					tipo.remove(p.getName());
				}
			}
		}, 10 * 20L);
		
		desafiados.put(p.getName(), desafiado.getName());
	}
	
	@SuppressWarnings("deprecation")
	public static void Fila1v1Rapido(Player p) {
		if(fast1v1.contains(p.getName())) {
			fast1v1.remove(p.getName());
			p.setItemInHand(API.item(Material.INK_SACK, 1, "§aEntrar na fila do 1v1 rápido", 8));
			p.sendMessage("§cVocê saiu da fila do 1v1 rápido!");
		} else {
			p.setItemInHand(API.item(Material.INK_SACK, 1, "§cSair da fila do 1v1 rápido", 10));
			p.sendMessage("§aVocê entrou na fila do 1v1 rápido!");
			fast1v1.add(p.getName());
			if(fast1v1.size() > 1) {
				int random = new Random().nextInt(fast1v1.size());
				while(fast1v1.get(random).equalsIgnoreCase(p.getName())) {
					random = new Random().nextInt(fast1v1.size());
				}
				if((Bukkit.getPlayerExact(fast1v1.get(random)) != null)) {
					Player desafiado = Bukkit.getPlayerExact(fast1v1.get(random));
					p.sendMessage("§aVocê entrou em 1v1 com " + ChatColor.stripColor(desafiado.getName()) + "!");
					desafiado.sendMessage("§aVocê entrou em 1v1 com " + ChatColor.stripColor(p.getName()) + "!");
					desafiado.teleport(Main.loc("1v1pos1", desafiado));
					p.teleport(Main.loc("1v1pos2", p));
					onbattle.put(p.getName(), desafiado.getName());
					onbattle.put(desafiado.getName(), p.getName());
					fast1v1.remove(p.getName());
					fast1v1.remove(desafiado.getName());
					KitManager.resetKit(p, false);
					KitManager.resetKit(desafiado, false);
					KitManager.kitPvP(p);
					KitManager.kitPvP(desafiado);
					p.updateInventory();
					desafiado.updateInventory();
					BarAPI.removeBar(p);
					BarAPI.removeBar(desafiado);
					for(Player on : Bukkit.getOnlinePlayers()) {
						p.hidePlayer(on);
						desafiado.hidePlayer(on);
						p.showPlayer(desafiado);
						desafiado.showPlayer(p);
					}
				}
			}
		}
	}
}
