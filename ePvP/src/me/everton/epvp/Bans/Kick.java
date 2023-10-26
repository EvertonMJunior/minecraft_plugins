package me.everton.epvp.Bans;

import me.everton.epvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kick implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		CommandSender p = sender;
		
		if(!p.hasPermission("pvp.admin")){
			p.sendMessage("§cSem permissao!");
			return true;	
		}
		
		if (label.equalsIgnoreCase("kick")) {
			if (args.length >= 2) {
				Player t = Bukkit.getPlayerExact(args[0]);
				
				if(t == null){
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}
				
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String allArgs = sb.toString().trim();
				
				for(Player on : Bukkit.getOnlinePlayers()){
					on.sendMessage("§7" + args[0] + " foi kickado por " + sender.getName() + "! Motivo: " + allArgs);
				}
				if(!(sender instanceof Player)){
					sender.sendMessage("§aO jogador foi kickado com sucesso!");
				}
				
				t.kickPlayer("§7[§3§l" + Main.settings.getConfig().getString("NomeServidor") + "§7] \n §cVocê foi kickado! \n Motivo: " + allArgs + " \n Por: " + p.getName());
			} else {
				p.sendMessage("§cUse: /kick <jogador> <motivo>");
			}
		}
		return false;
	}
}
