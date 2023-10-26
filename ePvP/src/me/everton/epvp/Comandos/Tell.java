package me.everton.epvp.Comandos;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tell implements CommandExecutor {
	public static HashMap<String, String> tells = new HashMap<>();
	public static ArrayList<String> tellon = new ArrayList<>();
	public static HashMap<String, String> p = new HashMap<>();
	public static HashMap<String, String> last = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("tell")) {

			if (args.length < 2) {
				if (args[0].equalsIgnoreCase("off")) {
					if (!tellon.contains(p.getName())) {
						tellon.add(p.getName());
						p.sendMessage("§6Seu tell foi desativado!");
					} else {
						p.sendMessage("§6Seu tell ja esta desativado!");
					}
				} else if (args[0].equalsIgnoreCase("on")) {
					if (tellon.contains(p.getName())) {
						tellon.remove(p.getName());
						p.sendMessage("§6Seu tell foi ativo!");
					} else {
						p.sendMessage("§6Seu tell ja esta ativo!");
					}
				} else {
					p.sendMessage("§cUse: /tell (player) (mensagem)");
				}
			} else if (args.length >= 2) {
				Player target = Bukkit.getServer().getPlayerExact(args[0]);
				if ((target == null) || (!(target instanceof Player))) {
					sender.sendMessage("§cJogador nao encontrado!");
					return true;
				}
				if (target.equals(p.getName())) {
					p.sendMessage("§cVocê nao pode mandar uma mensagem para si mesmo!");
					return true;
				}
				if (tellon.contains(target.getName())) {
					p.sendMessage("§cEste player esta com o tell desativado!");
					return true;
				}
				if (tellon.contains(p.getName())) {
					p.sendMessage("§cVocê esta com o tell desativado!");
					return true;
				}

				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String msg = sb.toString().trim();
				p.sendMessage("§7[eu -> " + target.getName() + "] §f" + msg);
				target.sendMessage("§7[" + p.getName() + " ->  eu] §f" + msg);
				last.put(target.getName(), p.getName());
				last.put(p.getName(), target.getName());

			} else {
				p.sendMessage("§cUse: /tell (player) (mensagem)");
			}
		}

		if (label.equalsIgnoreCase("r")) {

			if (args.length < 1) {
				p.sendMessage("§cUse: /r (mensagem)");
			} else if (args.length >= 1) {
				if (!last.containsKey(p.getName())) {
					p.sendMessage("§cVocê nao tem niguem para responder!");
					return true;
				}

				if (tellon.contains(last.get(p.getName()))) {
					p.sendMessage("§cEste player esta com o tell desativado!");
					return true;
				}
				if (tellon.contains(p.getName())) {
					p.sendMessage("§cVocê esta com o tell desativado!");
					return true;
				}

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String msg = sb.toString().trim();
				p.sendMessage("§7[eu -> " + last.get(p.getName()) + "] §f"
						+ msg);
				Player lastp = Bukkit.getPlayerExact(last.get(p.getName()));
				lastp.sendMessage(
						"§7[" + p.getName() + " ->  eu] §f" + msg);

				last.put(last.get(p.getName()), p.getName());

			} else {
				p.sendMessage("§cUse: /r (mensagem)");
			}
		}

		return false;
	}
}
