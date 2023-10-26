package me.everton.WocPvP.Comandos;

import me.everton.WocPvP.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Trolar implements CommandExecutor, Listener {
	public static Inventory trollar = Bukkit.createInventory(null, 27,
			"Trolle Hackers!");
	public static Player jogador1;

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("troll")) {
			Player p = (Player) sender;
			if (p.hasPermission("wocpvp.admin")) {
				if (args.length == 0) {
					p.sendMessage("§cUse /trollar (nick)");

				} else if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					jogador1 = p.getServer().getPlayer(args[0]);
					trollar.clear();

					ItemStack opFake = new ItemStack(Material.DIAMOND, 1);
					ItemMeta opFakeMeta = opFake.getItemMeta();
					opFakeMeta.setDisplayName(ChatColor.AQUA + "OP Fake");
					opFake.setItemMeta(opFakeMeta);
					trollar.setItem(3, opFake);

					ItemStack jogarAlto = new ItemStack(Material.FIREWORK, 1);
					ItemMeta jogarAltoMeta = jogarAlto.getItemMeta();
					jogarAltoMeta.setDisplayName(ChatColor.RED
							+ "Jogar para o alto");
					jogarAlto.setItemMeta(jogarAltoMeta);
					trollar.setItem(11, jogarAlto);

					ItemStack queimarP = new ItemStack(Material.FIRE, 1);
					ItemMeta queimarPMeta = queimarP.getItemMeta();
					queimarPMeta.setDisplayName(ChatColor.GOLD + "Queimar");
					queimarP.setItemMeta(queimarPMeta);
					trollar.setItem(13, queimarP);

					ItemStack explodirP = new ItemStack(Material.TNT, 1);
					ItemMeta explodirPMeta = explodirP.getItemMeta();
					explodirPMeta.setDisplayName(ChatColor.DARK_RED
							+ "Explodir");
					explodirP.setItemMeta(explodirPMeta);
					trollar.setItem(15, explodirP);

					p.openInventory(trollar);
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}

	@EventHandler
	public void InvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName().equalsIgnoreCase("Trolle Hackers!")) {
			if (e.getSlot() == 11) {
				e.setCancelled(true);
				p.closeInventory();
				jogador1.setVelocity(p.getVelocity().setY(10.0D));
				Main.log(p.getName() + " jogou " + jogador1.getName()
						+ " para o alto com o /trollar!");
			} else if (e.getSlot() == 13) {
				e.setCancelled(true);
				p.closeInventory();
				jogador1.setFireTicks(24000);
			} else if (e.getSlot() == 15) {
				e.setCancelled(true);
				p.closeInventory();
				jogador1.getWorld().createExplosion(jogador1.getLocation(), 1f,
						true);
				Main.log(p.getName() + " explodiu " + jogador1.getName()
						+ " com o /trollar!");
			} else if (e.getSlot() == 3) {
				e.setCancelled(true);
				p.closeInventory();
				jogador1.sendMessage("§7[CONSOLE: Opped "
						+ jogador1.getName().toLowerCase() + "§7]");
				Main.log(p.getName() + " trollou " + jogador1.getName()
						+ " fingindo que ele era OP com o /trollar!");
			}

		}
	}

	@EventHandler
	public void aoExplodir(ExplosionPrimeEvent e) {
		e.setCancelled(true);
	}

}