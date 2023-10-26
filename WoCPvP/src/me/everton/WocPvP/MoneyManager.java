package me.everton.WocPvP;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

public class MoneyManager {
	static FileConfiguration money = Main.settings.getMoney();
	
	public static HashMap<String, Integer> din = new HashMap<>();

	public static void addMoney(String nome, int quantidade) {
		
		int dindin = quantidade + money.getInt(nome.toLowerCase() + ".money") + din.get(nome.toLowerCase());

		din.put(nome.toLowerCase(), dindin);

		if (money.getInt(nome.toLowerCase() + ".money") < 0) {
			money.set(nome.toLowerCase() + ".money", 0);
			Main.settings.saveMoney();
		} else {
			money.set(nome.toLowerCase() + ".money", checkMoney(nome.toLowerCase()) + quantidade);
			Main.settings.saveMoney();
		}

		if (Bukkit.getPlayerExact(nome.toLowerCase()) != null) {
			Player p = Bukkit.getPlayerExact(nome.toLowerCase());
			p.sendMessage("§a+ §7" + quantidade + " moedas!");

			Score m = (Score) ServerScoreboard.money.get(p.getName());
			m.setScore(money.getInt(nome.toLowerCase() + ".money"));
			ServerScoreboard.money.put(p.getName(), m);

		}
	}

	public static void setMoney(String nome, int quantidade) {
		money.set(nome.toLowerCase() + ".money", quantidade);
		Main.settings.saveMoney();

		if (money.getInt(nome.toLowerCase() + ".money") < 0) {
			money.set(nome.toLowerCase() + ".money", 0);
			Main.settings.saveMoney();
		}

		if (Bukkit.getPlayerExact(nome.toLowerCase()) != null) {
			Player p = Bukkit.getPlayerExact(nome.toLowerCase());
			p.sendMessage("§aAgora Você tem " + quantidade + " moedas!");

			Score m = (Score) ServerScoreboard.money.get(p.getName());
			m.setScore(money.getInt(nome.toLowerCase() + ".money"));
			ServerScoreboard.money.put(p.getName(), m);
		}
	}

	public static void removeMoney(String nome, int quantidade) {
		money.set(nome.toLowerCase() + ".money",
				money.getInt(nome.toLowerCase() + ".money") - quantidade);
		Main.settings.saveMoney();
		if (money.getInt(nome.toLowerCase() + ".money") < 0) {
			money.set(nome.toLowerCase() + ".money", 0);
			Main.settings.saveMoney();
		}

		if (Bukkit.getPlayerExact(nome.toLowerCase()) != null) {
			Player p = Bukkit.getPlayerExact(nome.toLowerCase());
			p.sendMessage("§c- §7" + quantidade + " moedas!");

			Score m = (Score) ServerScoreboard.money.get(p.getName());
			m.setScore(money.getInt(nome.toLowerCase() + ".money"));
			ServerScoreboard.money.put(p.getName(), m);
		}
	}

	public static int checkMoney(String nome) {
		return money.getInt(nome.toLowerCase() + ".money");
	}
}
