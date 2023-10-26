package me.everton.WocPvP.Comandos;

import me.confuser.barapi.BarAPI;
import me.everton.WocPvP.KitManager;
import me.everton.WocPvP.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class KitCmd implements CommandExecutor, Listener {
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("usarkit")) {
			if (args.length < 1) {
				p.chat("/kit");
			} else if (args.length == 1) {
				if (Main.usandokit.contains(p)) {
					p.sendMessage("§cVocê ja esta usando um kit!");
					return true;
				}
				if (args[0].equalsIgnoreCase("stomper")) {
					KitManager.kitStomper(p);
				} else if (args[0].equalsIgnoreCase("switcher")) {
					KitManager.kitSwitcher(p);
				} else if (args[0].equalsIgnoreCase("pvp")) {
					KitManager.kitPvP(p);
				} else if (args[0].equalsIgnoreCase("grappler")) {
					KitManager.kitGrappler(p);
				} else if (args[0].equalsIgnoreCase("endermage")) {
					KitManager.kitEndermage(p);
				} else if (args[0].equalsIgnoreCase("infernor")) {
					KitManager.kitInfernor(p);
				} else if (args[0].equalsIgnoreCase("gladiator")) {
					KitManager.kitGladiator(p);
				} else if (args[0].equalsIgnoreCase("kangaroo")) {
					KitManager.kitKangaroo(p);
				} else {
					p.sendMessage("§aEste kit nao existe!");
				}
			}
		}
		if (label.equalsIgnoreCase("pvp")) {
			KitManager.kitPvP(p);
		}
		return false;
	}

	@EventHandler
	public void aoPerderFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void aoWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void kitBar(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player)
				&& (e.getDamager() instanceof Player)) {
			Player p = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			BarAPI.setMessage(damager,
					"§f" + ChatColor.stripColor(p.getDisplayName()) + " - §b"
							+ KitManager.kitUsando(p), 2);
		}
	}
}
