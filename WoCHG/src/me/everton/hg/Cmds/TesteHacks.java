package me.everton.hg.Cmds;

import java.util.HashMap;

import me.everton.hg.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TesteHacks implements CommandExecutor {
	public static HashMap<String, ItemStack[]> inv = new HashMap<>();
	public static HashMap<String, Double> health = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("autosoup")) {
			final Player p = (Player) sender;
			if (p.hasPermission("wochg.admin")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "Use: /autosoup (player)");
				} else if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					final Player teste = Bukkit.getPlayerExact(args[0]);
					Main.verInv(p, teste);
					Bukkit.getScheduler().runTaskLater(Main.plugin,
							new Runnable() {

								public void run() {
									inv.put(teste.getName(), teste
											.getInventory().getContents());
									Damageable hp = teste;
									health.put(teste.getName(), hp.getHealth());
									hp.setHealth(5D);
									teste.getInventory().clear();
									teste.getInventory().setItem(
											2,
											new ItemStack(
													Material.MUSHROOM_SOUP));
									teste.getInventory().setItem(
											11,
											new ItemStack(
													Material.MUSHROOM_SOUP));
								}
							}, 20L);

					Bukkit.getScheduler().runTaskLater(Main.plugin,
							new Runnable() {
								public void run() {
									teste.getInventory().setContents(
											(ItemStack[]) inv.get(teste
													.getName()));
									teste.setHealth(health.get(teste.getName()));
								}
							}, 60L);
					inv.remove(teste.getName());
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}

		if (label.equalsIgnoreCase("antikb")) {
			Player p = (Player) sender;
			if (!p.hasPermission("wochg.admin")) {
				p.sendMessage("§4§7[§c!§7] Sem permissao!!");
				return true;
			}

			if (args.length == 1) {
				if (Bukkit.getPlayerExact(args[0]) == null) {
					p.sendMessage("§cJogador nao encontrado");
					return true;
				}
				Player target = Bukkit.getPlayerExact(args[0]);
				target.setVelocity(target.getVelocity().setY(0.2D));
				p.sendMessage("§aAntiKB de" + p.getName() + " testado!");
			} else {
				p.sendMessage("§cUse: /antikb (jogador)");
			}
		}
		return false;
	}
}
