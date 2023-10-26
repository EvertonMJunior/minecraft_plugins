package me.everton.pvp.e1v1;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.LocationsManager;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Main1v1 implements CommandExecutor{
	public static ArrayList<String> on1v1 = new ArrayList<>();
	public static ArrayList<String> fila1v1rapido = new ArrayList<>();
	
	public static HashMap<String, String> onBattle = new HashMap<>();
	
	public static void enterOrLeave(Player p) {
		if(on1v1.contains(p.getName())) {
			on1v1.remove(p.getName());
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 2F);
			p.teleport(LocationsManager.getSpawn());
			KitManager.resetKit(p);
			API.itensIniciais(p);
			API.deleteHashMapKey(Desafiar.pendentes, p.getName());
			p.sendMessage("§7§l1v1 §8> §aVocê saiu do 1v1!");
		} else {
			on1v1.add(p.getName());
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 2F);
			p.sendMessage("§7§l1v1 §8> §aVocê entrou no 1v1!");
			p.teleport(LocationsManager.getLocation("1v1"));
			KitManager.resetKit(p);
			giveItens(p);
		}
	}
	
	public static void giveItens(final Player p) {
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				
				p.getInventory().setItem(2, API.item(Material.BLAZE_ROD, 1, "§8> §c§lDesafio Normal"));
				p.getInventory().setItem(4, API.item(Material.SKULL_ITEM, 1, "§8> §c§l1v1 contra Bot"));
				p.getInventory().setItem(6, API.item(Material.INK_SACK, 1, "§8> §c§l1v1 Rápido", 8));
				p.updateInventory();
				p.setVelocity(new Vector());
			}
		}, 1L);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game!");
			return true;
		}
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("1v1")) {
			enterOrLeave(p);
		}
		return false;
	}
}
