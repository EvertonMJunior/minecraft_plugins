package me.dark.Cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dark.Main;
import me.dark.kit.Kit;
import me.dark.kit.KitManager;

public class ToggleKits implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		if (p.hasPermission(Main.perm_modplus)) {
			if (args.length==0) {
				if (Main.toggleKits) {
					for (Kit k : Kit.kits) {
						k.getPlayers().clear();
					}
					Bukkit.broadcastMessage("§4Todos os kits foram desativados!");
					Main.toggleKits = false;
				} else {
					Bukkit.broadcastMessage("§4Todos os kits foram habilitados!");
					Main.toggleKits = true;
				}
			} else if (KitManager.getKitByString(args[0])!=null) {
				Kit k = KitManager.getKitByString(args[0]);
				if (Main.notToggled.contains(k)) {
					Main.notToggled.remove(k);
					Bukkit.broadcastMessage("§4O kit "+k.toString()+" foi habilitado!");
				} else {
					Main.notToggled.add(k);
					for (Player pa : Bukkit.getOnlinePlayers()) {
						if (!k.getPlayers().contains(pa)) {
							pa.sendMessage("§4O kit "+k.toString() + " foi desativado!");
						}
					}
					for (Player pa : k.getPlayers()) {
						pa.sendMessage("§4O kit "+k.toString()+" foi desativado, escolha outro!");
					}
					k.getPlayers().clear();
				}
			}
		}
		return false;
	}
	

}
