package me.everton.epvp.Comandos;

import me.confuser.barapi.BarAPI;
import me.everton.epvp.KitManager;
import me.everton.epvp.Main;

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
import org.bukkit.potion.PotionEffectType;

public class KitCmd implements CommandExecutor, Listener {
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("resetkit")){
			if (p.hasPermission("pvp.resetkit")) {
				KitManager.resetKit(p, true);
				Main.spawnItens(p);
			} else {
				p.sendMessage("§7[§c!§7] Sem permissao!");
			}
		}
		
		if (label.equalsIgnoreCase("kit")) {
			if ((args.length < 1) || (args.length > 1)) {
				KitSelector.abrirSelector(p, "possui");
			} else if (args.length == 1) {
				if (Main.usandokit.contains(p)) {
					p.sendMessage("§cVocê ja esta usando um kit!");
					return true;
				}
				if (args[0].equalsIgnoreCase("stomper")) {
					KitManager.kitStomper(p);
				} else if (args[0].equalsIgnoreCase("switcher")) {
					//KitManager.kitSwitcher(p);
				} else if (args[0].equalsIgnoreCase("avatar")) {
					KitManager.kitAvatar(p);
				} else if (args[0].equalsIgnoreCase("pvp")) {
					KitManager.kitPvP(p);
				} else if (args[0].equalsIgnoreCase("grappler")) {
					KitManager.kitGrappler(p);
				} else if (args[0].equalsIgnoreCase("endermage")) {
					KitManager.kitEndermage(p);
				} else if (args[0].equalsIgnoreCase("infernor")) {
					//KitManager.kitInfernor(p);
				} else if (args[0].equalsIgnoreCase("gladiator")) {
					//KitManager.kitGladiator(p);
				} else if (args[0].equalsIgnoreCase("kangaroo")) {
					KitManager.kitKangaroo(p);
				} else if (args[0].equalsIgnoreCase("legolas")) {
					KitManager.kitLegolas(p);
				} else if (args[0].equalsIgnoreCase("sniper")) {
					KitManager.kitSniper(p);
				} else if (args[0].equalsIgnoreCase("ninja")) {
					KitManager.kitNinja(p);
				} else if (args[0].equalsIgnoreCase("c4")) {
					KitManager.kitC4(p);
				} else if (args[0].equalsIgnoreCase("flash")) {
					KitManager.kitFlash(p);
				} else if (args[0].equalsIgnoreCase("terrorista")) {
					KitManager.kitTerrorista(p);
				} else if (args[0].equalsIgnoreCase("sombra")) {
					KitManager.kitSombra(p);
				} else if (args[0].equalsIgnoreCase("cactus")) {
					KitManager.kitCactus(p);
				} else if (args[0].equalsIgnoreCase("poseidon")) {
					KitManager.kitPoseidon(p);
				} else if (args[0].equalsIgnoreCase("vacuum")) {
					KitManager.kitVacuum(p);
				} else if(args[0].equalsIgnoreCase("list")){
					String kits = "Nenhum";
					String todos = "Tens todos os kits ;D";
					for(int i = 0; i < Main.listakits.size(); i++) {
						if(p.hasPermission("kit." + Main.listakits.get(i))) {
							if(kits.equalsIgnoreCase("Nenhum")) {
								kits = String.valueOf(Main.listakits.get(i).charAt(0)).toUpperCase() + Main.listakits.get(i).substring(1, Main.listakits.get(i).length()).toLowerCase();
							} else {
								kits = kits + ", " + String.valueOf(Main.listakits.get(i).charAt(0)).toUpperCase() + Main.listakits.get(i).substring(1, Main.listakits.get(i).length()).toLowerCase();
							}
						} else {
							if(todos.equalsIgnoreCase("Tens todos os kits ;D")) {
								todos = String.valueOf(Main.listakits.get(i).charAt(0)).toUpperCase() + Main.listakits.get(i).substring(1, Main.listakits.get(i).length()).toLowerCase();
							} else {
								todos = todos + ", " + String.valueOf(Main.listakits.get(i).charAt(0)).toUpperCase() + Main.listakits.get(i).substring(1, Main.listakits.get(i).length()).toLowerCase();
							}
						}
					}
					p.sendMessage("§bSeus kits: §f" +  kits);
					p.sendMessage("§bOutros kits: §f" + todos);
				} else {
					p.sendMessage("§7[§c!§7] Este kit nao existe!");
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
			if((!p.hasPotionEffect(PotionEffectType.INVISIBILITY)) && (!Admin.vis.contains(p.getName()))) {
				BarAPI.setMessage(damager,
						"§f" + ChatColor.stripColor(p.getDisplayName()) + " - §b"
								+ KitManager.kitUsando(p), 1);	
			}
		}
	}
}
