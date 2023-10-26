package me.everton.WocPvP.Comandos;

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

public class Infos implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("youtuber")) {
			Player p = (Player) sender;
			Inventory ytinv = Bukkit.createInventory(p, 9, "§0Youtuber");

			ItemStack yt = new ItemStack(Material.BEDROCK, 1);
			ItemMeta ytmeta = yt.getItemMeta();
			ytmeta.setDisplayName(ChatColor.AQUA
					+ "Quer ser Youtuber no WoCPvP?");
			ArrayList<String> loreyt = new ArrayList<String>();
			loreyt.add("§b ");
			loreyt.add("§cRequisitos: ");
			loreyt.add("§6- Ao menos 350 inscritos");
			loreyt.add("§6- Ao menos 3500 views");
			loreyt.add("§6- Pelo menos 200 views por vídeo");
			loreyt.add("§6- Pelo menos 20 likes por vídeo");
			loreyt.add("§6- Ao menos 1 vídeo no server");
			ytmeta.setLore(loreyt);
			yt.setItemMeta(ytmeta);

			ytinv.setItem(4, yt);
			p.openInventory(ytinv);
		}
		if (label.equalsIgnoreCase("aplicacao")) {
			Player p = (Player) sender;
			Inventory aplicacaoinv = Bukkit.createInventory(p, 9,
					"§0Aplicacao Staff");
			p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);

			ItemStack trial = new ItemStack(Material.MAGMA_CREAM, 1);
			ItemMeta trialmeta = trial.getItemMeta();
			trialmeta.setDisplayName(ChatColor.GREEN
					+ "Quer ser Moderador do WoCPvP?");
			ArrayList<String> loretrial = new ArrayList<String>();
			loretrial.add("§b");
			loretrial.add("§bEntre em: §6http://bit.ly/1zF0fQI");
			trialmeta.setLore(loretrial);
			trial.setItemMeta(trialmeta);

			ItemStack coder = new ItemStack(Material.REDSTONE_BLOCK, 1);
			ItemMeta codermeta = coder.getItemMeta();
			codermeta.setDisplayName(ChatColor.GREEN
					+ "Quer ser Coder do WoCPvP?");
			ArrayList<String> lorecoder = new ArrayList<String>();
			lorecoder.add("§b");
			lorecoder.add("§bEntre em: §6http://bit.ly/1E4Z6Y1");
			codermeta.setLore(lorecoder);
			coder.setItemMeta(codermeta);

			ItemStack builder = new ItemStack(Material.REDSTONE_BLOCK, 1);
			ItemMeta buildermeta = builder.getItemMeta();
			buildermeta.setDisplayName(ChatColor.GREEN
					+ "Quer ser Builder do WoCPvP?");
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
	public void aoClickInv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName().equalsIgnoreCase("§0Youtuber")) {
			e.setCancelled(true);
			if (e.getSlot() == 4) {
				e.setCancelled(true);
				p.closeInventory();
				p.sendMessage("§bQuer ser Youtuber no WoCPvP?");
				p.sendMessage("§cRequisitos: ");
				p.sendMessage("§6- Ao menos 350 inscritos");
				p.sendMessage("§6- Ao menos 3500 views");
				p.sendMessage("§6- Pelo menos 200 views por vídeo");
				p.sendMessage("§6- Pelo menos 20 likes por vídeo");
				p.sendMessage("§6- Ao menos 1 vídeo no server");
				p.sendMessage("§bCaso deseje, contate algum membro da STAFF!");
			}
		}
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
