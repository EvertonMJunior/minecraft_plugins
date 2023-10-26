package me.everton.epvp.Comandos;

import java.util.ArrayList;

import me.confuser.barapi.BarAPI;
import me.everton.epvp.CombatLog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Hack implements CommandExecutor, Listener {
	public static ArrayList<String> hacks = new ArrayList<String>();

	public static World w = Bukkit.getServer().getWorld("world");
	public static Location cabine1 = new Location(w, 568.5, 52.2, 336.8);
	public static Location cabine2 = new Location(w, 560.3, 53.2, 344.8);
	public static Location cabine3 = new Location(w, 576.6, 52.4, 344.5);
	public static Location cabine4 = new Location(w, 568.5, 51.2, 353.3);

	public static Location hackspawn = new Location(w, 568, 53, 345);

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("hack")) {
			Player p = (Player) sender;
			if (p.hasPermission("pvp.admin")) {
				if (args.length == 0) {
					p.setGameMode(GameMode.CREATIVE);
					p.teleport(hackspawn);
					p.sendMessage(ChatColor.RED + "Trolle Hackers! xD");
				} else if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					Player jogadorTp = p.getServer().getPlayer(args[0]);
					jogadorTp.setGameMode(GameMode.CREATIVE);
					jogadorTp.teleport(hackspawn);
					jogadorTp.sendMessage(ChatColor.RED
							+ "Você foi puxado por " + p.getDisplayName()
							+ ChatColor.RED + "!");
					jogadorTp.sendMessage(ChatColor.RED + "Trolle Hackers! xD");
					p.sendMessage(ChatColor.GREEN + jogadorTp.getName()
							+ " foi puxado ate o /hack!");
				} else if (args.length == 2) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					Player jogadorHack = p.getServer().getPlayer(args[0]);
					int nTroll = Integer.valueOf(args[1]);
					if (nTroll == 1) {
						if(CombatLog.cl.contains(p.getName())) {
							CombatLog.cl.remove(p.getName());
						}
						jogadorHack
								.teleport(new Location(w, 568.5, 52.2, 336.8));
					} else if (nTroll == 2) {
						if(CombatLog.cl.contains(p.getName())) {
							CombatLog.cl.remove(p.getName());
						}
						jogadorHack
								.teleport(new Location(w, 560.3, 53.2, 344.8));
					} else if (nTroll == 3) {
						if(CombatLog.cl.contains(p.getName())) {
							CombatLog.cl.remove(p.getName());
						}
						jogadorHack
								.teleport(new Location(w, 576.6, 52.4, 344.5));
					} else if (nTroll == 4) {
						if(CombatLog.cl.contains(p.getName())) {
							CombatLog.cl.remove(p.getName());
						}
						jogadorHack
								.teleport(new Location(w, 568.5, 51.2, 353.3));
					} else {
						p.sendMessage("§cCabine nao existe!");
						return true;
					}

					for (Player onlines : Bukkit.getServer().getOnlinePlayers()) {
						onlines.sendMessage(ChatColor.RED
								+ ""
								+ ChatColor.BOLD
								+ "Opaa! "
								+ jogadorHack.getName()
								+ " foi pego usando hack! Foi ban conhecer ele!");
					}
					hacks.add(jogadorHack.getName());
					jogadorHack.setGameMode(GameMode.SURVIVAL);
					BarAPI.setMessage(jogadorHack, ChatColor.AQUA
							+ "Porque Você esta usando hack no WoCPvP?", 5);
					jogadorHack.sendMessage(ChatColor.AQUA + ""
							+ ChatColor.BOLD
							+ "Porque Você esta usando hack no WoCPvP?");
				}
			} else {
				sender.sendMessage(ChatColor.DARK_RED
						+ "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}

	@EventHandler
	public void aoTp(PlayerTeleportEvent e) {
		if (hacks.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(
					ChatColor.RED + "Que feio! Tentando fugir!");
		}
	}

	@EventHandler
	public void aoMorrer(PlayerDeathEvent e) {
		Player p = e.getEntity().getPlayer();
		if (hacks.contains(p.getName())) {
			hacks.remove(p.getName());
		}
	}
}