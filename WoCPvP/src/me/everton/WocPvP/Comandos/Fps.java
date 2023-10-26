package me.everton.WocPvP.Comandos;

import java.util.ArrayList;

import me.everton.WocPvP.Main;
import me.everton.WocPvP.HG.HG;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Fps implements CommandExecutor, Listener {
	public static ArrayList<Player> fps = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("fps")) {
			
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("set")) {
					Main.setLoc(p, "fps");
					return true;
				}
				return true;
			}
			
			if (HG.hg.contains(p)) {
				HG.hg.remove(p);
				HG.hgin.remove(p);
			}
			if (!(fps.contains(p))) {
				if (!Spec.specs.contains(p)) {
					p.getInventory().clear();
					fps.add(p);
					UltraKits.Main.resetKit(p);
					UltraKits.KitItems.kitPvP(p);
				}
				p.teleport(Main.loc("fps", p));
				p.sendMessage(ChatColor.GREEN + "Você entrou no FPS!");
			} else if (fps.contains(p)) {
				if (!Spec.specs.contains(p)) {
					fps.remove(p);
					p.setHealth(20D);
					p.setFoodLevel(20);
					p.setGameMode(GameMode.SURVIVAL);
					UltraKits.Main.resetKit(p);
					Main.spawnItens(p);
				}
				p.teleport(Main.loc("spawn", p));
				p.sendMessage(ChatColor.GREEN + "Você saiu do FPS!");
			}
		}
		return false;
	}
}
