package me.everton.epvp.Comandos;

import java.util.HashMap;

import me.everton.epvp.Main;
import me.everton.epvp.Bans.Mute;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements CommandExecutor, Listener {
	public static String chatstate = "on";

	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();

	public static HashMap<String, Integer> flood = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("chat")) {
			Player p = (Player) sender;
			if (p.hasPermission("pvp.chatadm")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "Use: /chat (on/off)");
				} else if (args.length == 1) {
					String argumento1 = args[0];
					if (argumento1.equalsIgnoreCase("off")) {
						if (chatstate == "on") {
							chatstate = "off";
							for (Player on : Bukkit.getOnlinePlayers()) {
								on.sendMessage("§7[§c!§7] O chat foi desativado!");
							}
						} else if (chatstate == "off") {
							p.sendMessage(ChatColor.RED
									+ "O chat já está desativado!");
						}
					} else if (argumento1.equalsIgnoreCase("on")) {
						if (chatstate == "off") {
							chatstate = "on";
							for (Player on : Bukkit.getOnlinePlayers()) {
								on.sendMessage("§7[§a!§7] O chat foi ativo!");
							}
						} else if (chatstate == "on") {
							p.sendMessage(ChatColor.RED
									+ "O chat já está ativo!");
						}
					}
				}

			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		} else if(label.equalsIgnoreCase("cc")){
			Player p = (Player) sender;
			if (p.hasPermission("pvp.chatadm")){
			for (Player on : Bukkit.getServer().getOnlinePlayers()) {
				for(int i = 0; i < 92; i++) {
					on.sendMessage(" ");
				}
			}
		}
		} else if(label.equalsIgnoreCase("lc")){
			Player p = (Player) sender;
			if (p.hasPermission("pvp.chatadm")){
				for(int i = 0; i < 92; i++) {
					p.sendMessage(" ");
				}

				p.sendMessage(ChatColor.AQUA
						+ ""
						+ ChatColor.BOLD
						+ "                   Seu chat foi limpo!");
		}
		}
		return false;
	}

	@EventHandler
	public void EventoChat(AsyncPlayerChatEvent e) throws Exception {
		String jogador = e.getPlayer().getDisplayName();
		Player p = e.getPlayer();
		if((Main.settings.getMutes().getString(p.getUniqueId() + ".motivo") != null) && (Main.settings.getMutes().getString(p.getUniqueId() + ".autor") != null) && (((Main.settings.getMutes().getLong(p.getUniqueId() + ".tempo") - System.currentTimeMillis()) / 1000 / 3600) != 0)){
			
		if((Main.settings.getMutes().getLong(p.getUniqueId() + ".tempoatual") == 0) && (Main.settings.getMutes().getInt(p.getUniqueId() + ".tempo") == 0)){
			p.sendMessage("§cVocê foi mutado para sempre por " + Mute.autorMute(p.getName()) + " ! Motivo: " + Mute.motivoMute(p.getName()));
			e.setCancelled(true);
		} else {
			long ts;
			ts = System.currentTimeMillis() / 1000L;
	          if (ts - Main.settings.getMutes().getLong(p.getUniqueId() + ".tempoatual") > Main.settings.getMutes().getInt(p.getUniqueId() + ".tempo"))
	          {
	        	  Mute.desmutarUUID(p.getUniqueId());
	        	  Main.settings.saveMutes();
	          } else {
	        	  p.sendMessage("§cVocê foi mutado por " + Mute.autorMute(p.getName()) + " por " + (Main.settings.getMutes().getInt(p.getUniqueId() + ".tempo") / 60) + " minutos! Motivo: " + Mute.motivoMute(p.getName()));
	        	  e.setCancelled(true);
	          }
			
		}
		
		}
		
		

		if (!p.hasPermission("pvp.admin")) {

			if (task.containsKey(p.getName())) {
				p.sendMessage("§cAguarde §l" + cd.get(p.getName())
						+ " §r§csegundos para falar novamente!");
				if (!flood.containsKey(p.getName())) {
					flood.put(p.getName(), 1);
				}
				p.sendMessage("§cVocê tem mais " + (5 - flood.get(p.getName()))
						+ " chances antes de ser kickado!");
				if (flood.get(p.getName()) == 5) {
					p.kickPlayer("§cVocê foi kickado por floodar 5 vezes seguidas! \n §cEsperamos que isso nao se repita!");
				}
				flood.put(p.getName(), flood.get(p.getName()) + 1);

				e.setCancelled(true);
			} else {

				final Player pf = p;

				int coolDown = Main.sh.scheduleSyncRepeatingTask(Main.plugin,
						new Runnable() {
							int tempo = 5;

							public void run() {
								if (tempo > 0) {
									tempo--;
									cd.put(pf.getName(), tempo);
								}
								if (tempo == 0) {
									if(task.containsKey(pf.getName())) {
										Main.sh.cancelTask(task.get(pf.getName()));
										cd.remove(pf.getName());
										task.remove(pf.getName());
										flood.remove(pf.getName());
									}
								}
							}
						}, 0, 20);
				task.put(p.getName(), coolDown);
			}
		}

		if (e.getMessage().contains("%")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cPor favor, não use o símbolo %!");
		} else {
			if ((chatstate == "off")
					& (!e.getPlayer().hasPermission("pvp.chatadm"))) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						ChatColor.RED + "O chat está desativado!");
			} else {
				char first = e.getMessage().charAt(0);
				String primeiro = first + "";
				String msg = primeiro.toUpperCase()
						+ e.getMessage().substring(1, e.getMessage().length())
								.toLowerCase();
				if (p.hasPermission("pvp.admin")) {

					e.setFormat(jogador + "§6§l >> §r§f"
							+ e.getMessage().replace("&", "§"));
				} else if (p.hasPermission("pvp.chatcolor")) {
					e.setFormat(jogador
							+ "§6§l >> §r§7"
							+ msg.replace("&", "§").replace("§k", "&k")
									.replace("§l", "&l"));
				} else {
					e.setFormat(jogador + "§6§l >> §r§7" + msg);
				}
			}
		}
	}
}
