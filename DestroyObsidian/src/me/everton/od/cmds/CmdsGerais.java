package me.everton.od.cmds;

import me.everton.od.API;
import me.everton.od.API.GameStage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdsGerais implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game!");
			return true;
		}
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("start")) {
			if(!p.hasPermission("od.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			
			if(API.getGameStage() == GameStage.JOGO) {
				p.sendMessage("§7[§c!§7] O jogo já iniciou!");
				return true;
			}
			
			if(API.getGameStage() == GameStage.INVENCIBILIDADE) {
				p.sendMessage("§7[§c!§7] O jogo já iniciou!");
				return true;
			}
			
			if(API.getGameStage() == GameStage.PRE_JOGO) {
				API.startPartida();
			}
		}
		return false;
	}
	
}
