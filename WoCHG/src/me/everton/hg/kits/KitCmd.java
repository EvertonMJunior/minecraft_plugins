package me.everton.hg.kits;

import me.everton.hg.JsonBuilder;
import me.everton.hg.JsonBuilder.ClickAction;
import me.everton.hg.kits.KitSelector.Inv;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game!");
			return true;
		}
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("kit")) {
			if(args.length >= 0 && args.length != 1) {
				KitSelector.openSelector(p, Inv.SEUS_KITS);
			} else if(args.length == 1) {
				for(KitType kit : KitType.values()) {
					if(kit.getName().equalsIgnoreCase(args[0])) {
						if(!p.hasPermission("kit." + kit.getName().toLowerCase())) {
							JsonBuilder json = new JsonBuilder("§7[§c!§7] Você nao possui este kit! Clique ");
							json.withText("§c§laqui ");
							json.withClickEvent(ClickAction.RUN_COMMAND, "/buy");
							json.withText("§7e compre-o!");
							json.sendJson(p);
							break;
						}
						
						KitManager.giveKit(p, kit);
					}
				}
			}
		}
		if(label.equalsIgnoreCase("kits")) {
			KitSelector.openSelector(p, Inv.SEUS_KITS);
		}
		return false;
	}

}
