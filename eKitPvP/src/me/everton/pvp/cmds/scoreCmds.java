package me.everton.pvp.cmds;
import me.everton.pvp.API;
import me.everton.pvp.Msgs;
import me.everton.pvp.ScoreManager;
import me.everton.pvp.ScoreboardManager;
import me.everton.pvp.ScoreManager.Deaths;
import me.everton.pvp.ScoreManager.Kills;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class scoreCmds implements CommandExecutor{

		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String label,
				String[] args) {
			if(!(sender instanceof Player)) {
				if(label.equalsIgnoreCase("resetkdr")) {
					if(args.length == 1) {
						if(!ScoreManager.isRegistered(args[0])) {
							sender.sendMessage("§c" + args[0] + " nunca entrou no " + Msgs.nomeServidor + "§e!");
							return true;
						}
						
						Kills.setKills(args[0], 0);
						Deaths.setDeaths(args[0], 0);
						sender.sendMessage("§aKDR de " + args[0] + " resetado!");
						API.broadcastMessage("§e" + args[0] + " acabou de comprar um §lReset KDR§e! GG ;D");
						
						Player t = Bukkit.getPlayerExact(args[0]);
						if(t != null) {
							ScoreboardManager.refreshSidebar(t);
							t.sendMessage(" ");
							t.sendMessage("§e§m----»--------------«----");
							t.sendMessage("§a       Seu §lKDR §afoi resetado!");
							t.sendMessage("§e§m----»--------------«----");
							t.sendMessage(" ");
							t.playSound(t.getLocation(), Sound.LEVEL_UP, 1F, 1F);
						}
					} else {
						sender.sendMessage("§cUse /resetkdr <jogador>");
					}
				}
			}
			return false;
		}
		
	}