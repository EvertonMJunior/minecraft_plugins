package me.everton.hg.Cmds;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Uteis implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game");
		}
		if (label.equalsIgnoreCase("aplicacao")) {
			Player p = (Player) sender;
			Inventory aplicacaoinv = Bukkit.createInventory(p, 9,
					"§0Aplicacao Staff");
			p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);

			ItemStack trial = new ItemStack(Material.MAGMA_CREAM, 1);
			ItemMeta trialmeta = trial.getItemMeta();
			trialmeta.setDisplayName(ChatColor.GREEN
					+ "Quer ser Moderador do WoCHG?");
			ArrayList<String> loretrial = new ArrayList<String>();
			loretrial.add("§b");
			loretrial.add("§bEntre em: §6http://bit.ly/1zF0fQI");
			trialmeta.setLore(loretrial);
			trial.setItemMeta(trialmeta);

			ItemStack coder = new ItemStack(Material.REDSTONE_BLOCK, 1);
			ItemMeta codermeta = coder.getItemMeta();
			codermeta.setDisplayName(ChatColor.GREEN
					+ "Quer ser Coder do WoCHG?");
			ArrayList<String> lorecoder = new ArrayList<String>();
			lorecoder.add("§b");
			lorecoder.add("§bEntre em: §6http://bit.ly/1E4Z6Y1");
			codermeta.setLore(lorecoder);
			coder.setItemMeta(codermeta);

			ItemStack builder = new ItemStack(Material.REDSTONE_BLOCK, 1);
			ItemMeta buildermeta = builder.getItemMeta();
			buildermeta.setDisplayName(ChatColor.GREEN
					+ "Quer ser Builder do WoCHG?");
			ArrayList<String> lorebuilder = new ArrayList<String>();
			lorebuilder.add("§b");
			lorebuilder.add("§bEntre em: §6http://bit.ly/1JoUNXc");
			buildermeta.setLore(lorebuilder);
			builder.setItemMeta(buildermeta);

			aplicacaoinv.setItem(4, trial);
			aplicacaoinv.setItem(0, coder);
			aplicacaoinv.setItem(8, builder);
			p.openInventory(aplicacaoinv);
		}
		return false;
	}

	@EventHandler
	public void aoInvInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName().equalsIgnoreCase("§0Aplicacao Staff")) {
			e.setCancelled(true);
			if (e.getSlot() == 4) {
				e.setCancelled(true);
				p.closeInventory();
				p.sendMessage("§bQuer ser Moderador do WoCPvP?");
				p.sendMessage("§bEntre em: §6http://bit.ly/1zF0fQI");
			} else if (e.getSlot() == 0) {
				e.setCancelled(true);
				p.closeInventory();
				p.sendMessage("§bQuer ser Coder do WoCPvP?");
				p.sendMessage("§bEntre em: §6http://bit.ly/1E4Z6Y1");
			} else if (e.getSlot() == 8) {
				e.setCancelled(true);
				p.closeInventory();
				p.sendMessage("§bQuer ser Builder do WoCPvP?");
				p.sendMessage("§bEntre em: §6http://bit.ly/1JoUNXc");
			}
		}
	}

}
