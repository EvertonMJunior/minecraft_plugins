package me.dark.Cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.MySQL.SQLStatus;
import me.dark.Utils.DarkUtils;
import me.dark.kit.KitManager;

public class Jogo implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			String kitname = "Nenhum";
			if (KitManager.hasAnyKit(p)) {
				kitname = KitManager.getKit(p).toString();
			}
			if (Main.estado == GameState.STARTED){
				p.sendMessage(" ");
				p.sendMessage("§3   GoodCraft");
				p.sendMessage("§6HardcoreGames");
				p.sendMessage(" ");
				p.sendMessage("§3Seu Kit: §f"+kitname);
				p.sendMessage("§3Kills: §f"+SQLStatus.getKillstreak(p));
				p.sendMessage("§3Tempo: §f"+DarkUtils.tempoInt(Main.gameTime));
				p.sendMessage(" ");
				p.sendMessage("§6good-craft.net");
				p.sendMessage(" ");
			}else if (Main.estado == GameState.INVENCIBILITY){
				p.sendMessage(" ");
				p.sendMessage("§3   GoodCraft");
				p.sendMessage("§6HardcoreGames");
				p.sendMessage(" ");
				p.sendMessage("§3Seu Kit: §f"+kitname);
				p.sendMessage("§3Kills: §f"+SQLStatus.getKillstreak(p));
				p.sendMessage("§3Invencibilidade: §f"+DarkUtils.tempoInt(120-Main.gameTime));
				p.sendMessage(" ");
				p.sendMessage("§6good-craft.net");
				p.sendMessage(" ");
			}else if (Main.estado == GameState.WIN){
				p.sendMessage(" ");
				p.sendMessage("§3   GoodCraft");
				p.sendMessage("§6HardcoreGames");
				p.sendMessage(" ");
				p.sendMessage("§3Seu Kit: §f"+kitname);
				p.sendMessage("§3Kills: §f"+SQLStatus.getKillstreak(p));
				p.sendMessage("§3Tempo: §f"+DarkUtils.tempoInt(Main.gameTime));
				p.sendMessage(" ");
				p.sendMessage("§6good-craft.net");
				p.sendMessage(" ");
			}else if (Main.estado == GameState.PREGAME){
				p.sendMessage(" ");
				p.sendMessage("§3         GoodCraft");
				p.sendMessage("§6      HardcoreGames");
				p.sendMessage(" ");
				p.sendMessage("§3Seu Kit: §f"+kitname);
				p.sendMessage("§3Iniciando: §f"+DarkUtils.tempoInt(Main.toStart));
				p.sendMessage(" ");
				p.sendMessage("      §6good-craft.net");
				p.sendMessage(" ");
			}

		}
		
		return false;
	}

}
