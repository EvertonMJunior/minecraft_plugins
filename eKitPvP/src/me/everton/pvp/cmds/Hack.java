package me.everton.pvp.cmds;

import java.util.HashMap;

import me.everton.pvp.API;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Hack implements CommandExecutor {
	public static HashMap<String, String> target = new HashMap<>();
	
	public static void openInv(Player p, Player t) {
		API.deleteHashMapKey(target, p.getName());
		target.put(p.getName(), t.getName());
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
		Inventory inv = Bukkit.createInventory(p, 9, "§c➤ §lHacks");
		inv.setItem(0, API.item(Material.BONE, 1, "§6§lAntiKnockback"));
		inv.setItem(1, API.item(Material.DIAMOND_SWORD, 1, "§6§lFF/KA"));
		inv.setItem(2, API.item(Material.MUSHROOM_SOUP, 1, "§6§lAutoSoup"));
		p.openInventory(inv);
	}
	
	public static String getRC(Player p) {
		return target.get(p.getName());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando In-Game!");
			return true;
		}
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("hack")) {
			if (!p.hasPermission("pvp.cmd.hack")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			if(args.length == 1) {
				if(Bukkit.getPlayerExact(args[0]) == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}
				Player t = Bukkit.getPlayerExact(args[0]);
				
				openInv(p, t);
			} else {
				p.sendMessage("§cUse /hack <nome do jogador>");
			}
		}
		return false;
	}

}
