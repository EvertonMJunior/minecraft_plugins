package me.everton.WocPvP.Comandos;

import me.everton.WocPvP.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tp implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("tp")) {
			Player p = (Player) sender;
			if (p.hasPermission("wocpvp.tp")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "Use /tp (jogador) (jogador)");
				} else if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					Player jogador1 = p.getServer().getPlayer(args[0]);
					p.teleport(jogador1);
					Bukkit.broadcast(
							"§7[" + p.getDisplayName() + "§7(" + p.getName()
									+ "§7)" + " §7foi teleportado até "
									+ jogador1.getDisplayName() + "§7("
									+ jogador1.getName() + "§7)]",
							"wocpvp.admin");
					Main.log(p.getName() + " se teleportou para "
							+ jogador1.getName());

				} else if (args.length == 2) {
					Player target = Bukkit.getPlayerExact(args[0]);
					Player target2 = Bukkit.getPlayerExact(args[1]);
					if ((target == null) || (!(target instanceof Player))
							|| (target2 == null)
							|| (!(target2 instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					Player jogador1 = p.getServer().getPlayer(args[0]);
					Player jogador2 = p.getServer().getPlayer(args[1]);
					jogador1.teleport(jogador2);
					Bukkit.broadcast(
							"§7[" + jogador1.getDisplayName() + "§7("
									+ jogador1.getName() + "§7)"
									+ " §7foi teleportado até "
									+ jogador2.getDisplayName() + "§7("
									+ jogador2.getName() + "§7)]",
							"wocpvp.admin");
					Main.log(p.getName() + " teleportou " + jogador1.getName()
							+ " para " + jogador2.getName());
				} else if (args.length == 4) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					Player jogador1 = p.getServer().getPlayer(args[0]);
					Bukkit.broadcast(
							"§7[" + jogador1.getDisplayName() + "§7("
									+ jogador1.getName() + "§7)"
									+ " §7foi teleportado até: X"
									+ Double.valueOf(args[1]) + ", Y"
									+ Double.valueOf(args[2]) + ", Z"
									+ Double.valueOf(args[3]), "wocpvp.admin");
					Location tp = new Location(p.getWorld(),
							Double.valueOf(args[1]), Double.valueOf(args[2]),
							Double.valueOf(args[3]));
					jogador1.teleport(tp);
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}
}