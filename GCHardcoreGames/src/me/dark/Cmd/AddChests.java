package me.dark.Cmd;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.dark.Main;
import me.dark.Chests.ChestsTypes;
import me.dark.MySQL.SQLStatus;
import me.dark.Utils.DarkUtils;
import net.goodcraft.UUIDFetcher;

public class AddChests implements CommandExecutor{
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission(Main.perm_admin)) return true;
		if (args.length == 0) {
			sender.sendMessage("§cUse /addchest <player> <tipo>");
		} else if (args.length == 1) {
			sender.sendMessage("§cUse /addchest <player> <tipo>");
		} else {
			UUID id = UUIDFetcher.getUUID(args[0]);
			DarkUtils.sendMessage(id.toString());
			if (id == null) {
				sender.sendMessage(Main.not_found);
			} else {
				if (SQLStatus.exists(id)) {
					if (ChestsTypes.G1.getType().equalsIgnoreCase(args[1])) {
						SQLStatus.addChestG1(id);
						sender.sendMessage("§bChest G1 adicionado!");
					} else if (ChestsTypes.G2.getType().equalsIgnoreCase(args[1])) {
						SQLStatus.addChestG2(id);
						sender.sendMessage("§bChest G2 adicionado!");
					}else if (ChestsTypes.G3.getType().equalsIgnoreCase(args[1])) {
						SQLStatus.addChestG3(id);
						sender.sendMessage("§bChest G3 adicionado!");
					}
				} else {
					sender.sendMessage("§bEsse jogador nunca entrou em nossos servidores!");
				}
			}
		}
		
		return false;
	}

}
