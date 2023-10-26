package me.everton.od.cmds;

import me.everton.od.Board;
import me.everton.od.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Time implements CommandExecutor{
	public static enum Team{
		RED, BLUE;
	}
	
	public static void setTeam(Player p, Team time){
		int playersPerTeam = Bukkit.getOnlinePlayers().length / 2;
		if(!Main.PRE_JOGO) {
			p.sendMessage("§b§lObsidian §6§lDestroyer §6➤ §aO jogo já começou!");
			return;
		}
		
		if(time == Team.RED) {
			if(!(Main.red.getPlayers().size() <= playersPerTeam)) {
				p.sendMessage("§b§lObsidian §6§lDestroyer §6➤ §aTime cheio!");
				return;
			}
			if(Main.blue.getPlayers().contains(p)) {
				Main.blue.removePlayer(p);
			}
			Main.red.addPlayer(p);
			p.setDisplayName("§c" + p.getName() + "§r");
			p.sendMessage("§b§lObsidian §6§lDestroyer §6➤ §aTime §cVermelho §aselecionado!");
		} else if(time == Team.BLUE) {
			if(!(Main.blue.getPlayers().size() <= playersPerTeam)) {
				p.sendMessage("§b§lObsidian §6§lDestroyer §6➤ §aTime cheio!");
				return;
			}
			if(Main.red.getPlayers().contains(p)) {
				Main.red.removePlayer(p);
			}
			Main.blue.addPlayer(p);
			p.setDisplayName("§9" + p.getName() + "§r");
			p.sendMessage("§b§lObsidian §6§lDestroyer §6➤ §aTime §9Azul §aselecionado!");
		}
		
		Board.refresh(p);
	}
	
	public static String getTeam(Player p) {
		if(Main.red.getPlayers().contains(p)) {
			return "Vermelho";
		} else if(Main.blue.getPlayers().contains(p)) {
			return "Azul";
		} else {
			return "Sem time";
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game!");
			return true;
		}
		Player p = (Player) sender;
		if(label.equalsIgnoreCase("time")) {
			if(args.length == 1) {
				switch (args[0].toLowerCase()) {
				case "red":
					setTeam(p, Team.RED);
					break;
					
				case "blue":
					setTeam(p, Team.BLUE);
					break;

				default:
					p.sendMessage("§b§lObsidian §6§lDestroyer §6➤ §aUse §9Blue §aou §cRed!");
					break;
				}
			} else {
				p.sendMessage("§b§lObsidian §6§lDestroyer §6➤ §aUse §9Blue §aou §cRed!");
			}
		}
		return false;
	}
	
	
}
