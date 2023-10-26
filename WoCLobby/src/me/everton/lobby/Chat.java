package me.everton.lobby;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener,CommandExecutor{
	private static boolean chat = true;

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game!");
			return true;
		}
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("cc")) {
			if(!p.hasPermission("woc.cmd.cc")) {
				p.sendMessage("§7[§c!§7] Sem permissoes!");
				return true;
			}
			
			for(Player on : Bukkit.getOnlinePlayers()) {
				for(int i = 0; i < 96; i++) {
					on.sendMessage(" ");
				}
				on.sendMessage("§6§m--(-------------------------------)--");
				on.sendMessage("                    §b§lO chat foi limpo!");
				on.sendMessage("§6§m--(-------------------------------)--");
				on.sendMessage(" ");
			}
		}
		
		if(label.equalsIgnoreCase("pc")) {
			if(!p.hasPermission("woc.cmd.pc")) {
				p.sendMessage("§7[§c!§7] Sem permissoes!");
				return true;
			}
			
			if(chat) {
				for(Player on : Bukkit.getOnlinePlayers()) {
					on.sendMessage(" ");
					on.sendMessage("§6§m--(-------------------------------)--");
					on.sendMessage("               §b§lO chat foi desativado!");
					on.sendMessage("§6§m--(-------------------------------)--");
					on.sendMessage(" ");
				}
				chat = false;
			} else {
				for(Player on : Bukkit.getOnlinePlayers()) {
					on.sendMessage(" ");
					on.sendMessage("§6§m--(-------------------------------)--");
					on.sendMessage("                    §b§lO chat foi ativo!");
					on.sendMessage("§6§m--(-------------------------------)--");
					on.sendMessage(" ");
				}
				chat = true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		e.setCancelled(true);
		
		if(!chat) {
			if(!p.hasPermission("woc.chatwhenoff")) {
				p.sendMessage("§cO chat está desativado!");
				return;
			}
		}
		
		String chatColor = "";
		String msg = e.getMessage();
		if(p.hasPermission("woc.chatcolor")) {
			chatColor = "§f";
			msg = e.getMessage().replace("&", "§");
		}
		
		for(Player on : Bukkit.getOnlinePlayers()) {
			on.sendMessage(p.getDisplayName() + "§7: " + chatColor + msg);
		}
	}
}
