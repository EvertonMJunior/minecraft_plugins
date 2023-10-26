package me.everton.epvp.Comandos;

import me.everton.epvp.MoneyManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Money implements CommandExecutor {

	public static boolean isInt(String s) {
		for (int a = 0; a < s.length(); a++) {
			if (a == 0 && s.charAt(a) == '-')
				continue;
			if (!Character.isDigit(s.charAt(a)))
				return false;
		}
		return true;
	}

	public static boolean isString(String s) {
		for (int a = 0; a < s.length(); a++) {
			if (a == 0 && s.charAt(a) == '-')
				continue;
			if (!Character.isAlphabetic(s.charAt(a)))
				return false;
		}
		return true;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if ((label.equalsIgnoreCase("addmoney"))
				|| (label.equalsIgnoreCase("removemoney"))
				|| (label.equalsIgnoreCase("setmoney"))) {
			if (!p.hasPermission("pvp.money")) {
				p.sendMessage("§4§7[§c!§7] Sem permissao!!");
				return true;
			}
			if (args.length == 2) {
				if (!isInt(args[1])) {
					p.sendMessage("§cUse apenas números!");
					return true;
				}
				if (args[1].length() > 10) {
					p.sendMessage("§cUse até 10 números!");
					return true;
				}
				if (Integer.valueOf(args[1]) < 0) {
					p.sendMessage("§cUse apenas numeros positivos!");
					return true;
				}
			}
		}
		if (label.equalsIgnoreCase("addmoney")) {
			if ((args.length < 2) || (args.length > 2)) {
				p.sendMessage("§cUse /addmoney (nome do jogador) (quantidade)");
			} else if (args.length == 2) {
				try {
					MoneyManager.addMoney(args[0], Integer.valueOf(args[1]));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					p.sendMessage("§a" + args[1]
							+ " moedas foram adicionados a conta de " + args[0]
							+ "! Agora " + args[0] + " tem "
							+ MoneyManager.checkMoney(args[0]) + " moedas.");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (label.equalsIgnoreCase("removemoney")) {
			if ((args.length < 2) || (args.length > 2)) {
				p.sendMessage("§cUse /removemoney (nome do jogador) (quantidade)");
			} else if (args.length == 2) {
				try {
					MoneyManager.removeMoney(args[0], Integer.valueOf(args[1]));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					p.sendMessage("§a" + args[1]
							+ " moedas foram removidas da conta de " + args[0]
							+ "! Agora " + args[0] + " tem "
							+ MoneyManager.checkMoney(args[0]) + " moedas.");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (label.equalsIgnoreCase("money")) {
			try {
				p.sendMessage("§aVocê tem " + MoneyManager.checkMoney(p.getName())
						+ " moedas!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (label.equalsIgnoreCase("setmoney")) {
			if ((args.length < 2) || (args.length > 2)) {
				p.sendMessage("§cUse /setmoney (nome do jogador) (quantidade)");
			} else if (args.length == 2) {
				try {
					MoneyManager.setMoney(args[0], Integer.valueOf(args[1]));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				p.sendMessage("§aAgora " + args[0] + " tem " + args[1]
						+ " moedas!");
			}
		}
		return false;
	}
}
