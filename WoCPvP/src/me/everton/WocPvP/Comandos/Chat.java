package me.everton.WocPvP.Comandos;

import java.util.HashMap;

import me.everton.WocPvP.Main;

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

	public static HashMap<Player, Integer> cd = new HashMap<>();
	public static HashMap<Player, Integer> task = new HashMap<>();

	public static HashMap<Player, Integer> flood = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("chat")) {
			Player p = (Player) sender;
			if (p.hasPermission("wocpvp.chatadm")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "Use: /chat (on/off/clear)");
				} else if (args.length == 1) {
					String argumento1 = args[0];
					if (argumento1.equalsIgnoreCase("off")) {
						if (chatstate == "on") {
							chatstate = "off";
							for (Player on : Bukkit.getOnlinePlayers()) {
								on.sendMessage("§cO chat foi desativado!");
							}
							Main.log(p.getName() + " desativou o chat.");
						} else if (chatstate == "off") {
							p.sendMessage(ChatColor.RED
									+ "O chat já está desativado!");
						}
					} else if (argumento1.equalsIgnoreCase("on")) {
						if (chatstate == "off") {
							chatstate = "on";
							for (Player on : Bukkit.getOnlinePlayers()) {
								on.sendMessage("§aO chat foi ativado!");
							}
							Main.log(p.getName() + " ativou o chat.");
						} else if (chatstate == "on") {
							p.sendMessage(ChatColor.RED
									+ "O chat já está ativado!");
						}
					} else if (argumento1.equalsIgnoreCase("clear")) {
						Main.log(p.getName() + " limpou o chat.");
						for (Player on : Bukkit.getServer().getOnlinePlayers()) {
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");
							on.sendMessage(" ");

							on.sendMessage(ChatColor.GREEN
									+ ""
									+ ChatColor.BOLD
									+ "                   O chat foi limpo por "
									+ p.getDisplayName());
						}
					}
				}

			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}

	@EventHandler
	public void EventoChat(AsyncPlayerChatEvent e) {
		String jogador = e.getPlayer().getDisplayName();
		Player p = e.getPlayer();
		Main.logChat(e.getMessage(), e.getPlayer());

		if (!p.hasPermission("wocpvp.admin")) {

			if (task.containsKey(p)) {
				p.sendMessage("§cAguarde §l" + cd.get(p)
						+ " §r§csegundos para falar novamente!");
				if (!flood.containsKey(p)) {
					flood.put(p, 1);
				}
				p.sendMessage("§cVocê tem mais " + (5 - flood.get(p))
						+ " chances antes de ser kickado!");
				if (flood.get(p) == 5) {
					p.kickPlayer("§cVocê foi kickado por floodar 5 vezes seguidas! \n §cEsperamos que isso nao se repita!");
				}
				flood.put(p, flood.get(p) + 1);

				e.setCancelled(true);
			} else {

				final Player pf = p;

				int coolDown = Main.sh.scheduleSyncRepeatingTask(Main.plugin,
						new Runnable() {
							int tempo = 5;

							public void run() {
								if (tempo > 0) {
									tempo--;
									cd.put(pf, tempo);
								}
								if (tempo == 0) {
									Main.sh.cancelTask(task.get(pf));
									cd.remove(pf);
									task.remove(pf);
									flood.remove(pf);
								}
							}
						}, 0, 20);
				task.put(p, coolDown);
			}
		}

		if (e.getMessage().contains("%")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cPor favor, não use o símbolo %!");
		} else {
			if ((chatstate == "off")
					& (!e.getPlayer().hasPermission("wocpvp.chatadm"))) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						ChatColor.RED + "O chat está desativado!");
			} else {
				char first = e.getMessage().charAt(0);
				String primeiro = first + "";
				String msg = primeiro.toUpperCase()
						+ e.getMessage().substring(1, e.getMessage().length())
								.toLowerCase();
				if ((p.hasPermission("wocpvp.admin"))
						&& (!Fake.fakes.containsKey(e.getPlayer()))) {

					e.setFormat(jogador + "§6§l >> §r§f"
							+ e.getMessage().replace("&", "§"));
				} else if (p.hasPermission("wocpvp.chatcolor")) {
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
