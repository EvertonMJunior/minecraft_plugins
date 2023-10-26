package me.dark.Cmd;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dark.Main;
import me.dark.MySQL.MySQL;
import me.dark.MySQL.SQLStatus;
import me.dark.Utils.DarkUtils;
import net.goodcraft.UUIDFetcher;
public class Status implements CommandExecutor {
	@SuppressWarnings({ "unused" })
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		final Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("status")) {
			if (MySQL.ativo) {
				if (args.length == 1) {
					UUID id = UUIDFetcher.getUUID(args[0]);
					DarkUtils.sendMessage(id.toString());
					if (id == null) {
						p.sendMessage(Main.not_found);
					}else if (id.equals(p.getUniqueId())) {
						p.chat("/status");
					}else if (id != null) {
						if (SQLStatus.exists(id)) {
							p.sendMessage(Main.linha);
							String nome = UUIDFetcher.getName(id);
							p.sendMessage(Main.seta2+" §bStatus do(a) "+nome);
							p.sendMessage("§3Kills: §b"+SQLStatus.getKills(id));
							p.sendMessage("§3Mortes: §b"+ SQLStatus.getDeaths(id));
							p.sendMessage("§3KDR: §b"+ getKDR(SQLStatus.getKills(id),SQLStatus.getDeaths(id)));
							p.sendMessage("§3Wins: §b"+SQLStatus.getWins(id));
							p.sendMessage(Main.linha);
					} else {
						p.sendMessage("§bEsse jogador nunca entrou em nossos servidores!");
					}
				  }
				} else {
									p.sendMessage(Main.linha);
									p.sendMessage(Main.seta2+" §bSeus status");
									p.sendMessage("§3Kills: §b"+ SQLStatus.getKills(p.getUniqueId()));
									p.sendMessage("§3Mortes: §b"+ SQLStatus.getDeaths(p.getUniqueId()));
									p.sendMessage("§3KDR: §b"+ getKDR(SQLStatus.getKills(p.getUniqueId()), SQLStatus.getDeaths(p.getUniqueId())));
									p.sendMessage("§3Wins: §b"+ SQLStatus.getWins(p.getUniqueId()));
									p.sendMessage(Main.linha);
				}
			} else {
				p.sendMessage("§3MySQL desativado!");
				return true;
			}
		}
		return false;
	}

	public static double getKDR(int kills, int deaths) {
		if (kills == 0 && deaths == 0)
			return 0.0;
		else if (kills > 0 && deaths == 0) {
			return 0.0;
		} else if (deaths > 0 && kills == 0) {
			return 0.0;
		} else {
			return round(kills / (double) deaths, 2);
		}
	}

	public static double round(double value, int decimals) {
		double p = Math.pow(10, decimals);
		return Math.round(value * p) / p;
	}

}
