package me.everton.epvp.Comandos;

import java.util.ArrayList;

import me.everton.epvp.KitManager;
import me.everton.epvp.Main;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Fps implements CommandExecutor, Listener {
	public static ArrayList<String> fps = new ArrayList<String>();

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
		
			if (!(fps.contains(p.getName()))) {
				if (!Spec.specs.contains(p.getName())) {
					p.getInventory().clear();
					fps.add(p.getName());
				}
				p.teleport(Main.loc("fps", p));
				p.sendMessage("§7[§a!§7] Você entrou no FPS!");
				if(Main.usandokit.contains(p.getName())) {
					KitManager.resetKit(p, false);
				}
				KitManager.kitPvP(p);
			} else if (fps.contains(p.getName())) {
				if (!Spec.specs.contains(p.getName())) {
					fps.remove(p.getName());
					p.setHealth(20D);
					p.setFoodLevel(20);
					p.setGameMode(GameMode.SURVIVAL);
					Main.spawnItens(p);
				}
				if(Main.usandokit.contains(p.getName())) {
					KitManager.resetKit(p, false);
				}
				p.teleport(Main.loc("spawn", p));
				p.sendMessage("§7[§a!§7] Você saiu do FPS!");
			}
		}
		return false;
	}
	
	@EventHandler
	public void onTp(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		if(fps.contains(p.getName())) {
			fps.remove(p.getName());
		}
	}
}
