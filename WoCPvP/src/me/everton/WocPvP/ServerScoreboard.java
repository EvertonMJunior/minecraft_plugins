package me.everton.WocPvP;

import java.util.HashMap;

import me.everton.WocPvP.Comandos.Admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ServerScoreboard implements Listener {
	public static HashMap<String, Scoreboard> scoreboards = new HashMap<>();
	public static HashMap<String, Score> deaths = new HashMap<>();
	public static HashMap<String, Score> kills = new HashMap<>();
	public static HashMap<String, Score> money = new HashMap<>();
	public static HashMap<String, Score> killst = new HashMap<>();

	public static void criarScoreboard(Player p) {
		if (!scoreboards.containsKey(p.getName())) {
			if (SettingsManager.score.getConfigurationSection(p.getName()) == null) {
				Scoreboard sb = Bukkit.getScoreboardManager()
						.getNewScoreboard();
				Objective obj = sb.registerNewObjective(p.getName(), "dummy");
				obj.setDisplaySlot(DisplaySlot.SIDEBAR);
				obj.setDisplayName("§c" + p.getName());
				Score d = obj.getScore(Bukkit.getOfflinePlayer("§bMorreu:"));
				Score k = obj.getScore(Bukkit.getOfflinePlayer("§bMatou:"));
				Score ks = obj.getScore(Bukkit
						.getOfflinePlayer("§bKillStreak:"));
				Score m = obj.getScore(Bukkit.getOfflinePlayer("§bMoedas:"));

				d.setScore(0);
				k.setScore(0);
				ks.setScore(0);
				m.setScore(MoneyManager.checkMoney(p.getName()));

				scoreboards.put(p.getName(), sb);
				deaths.put(p.getName(), d);
				kills.put(p.getName(), k);
				money.put(p.getName(), m);
				killst.put(p.getName(), ks);

				SettingsManager.score.set(p.getName() + ".deaths",
						Integer.valueOf(0));
				SettingsManager.score.set(p.getName() + ".kills",
						Integer.valueOf(0));
				Main.settings.saveScore();
				p.setScoreboard((Scoreboard) scoreboards.get(p.getName()));
			} else {
				Scoreboard sb = Bukkit.getScoreboardManager()
						.getNewScoreboard();
				Objective obj = sb.registerNewObjective(p.getName(), "dummy");
				obj.setDisplaySlot(DisplaySlot.SIDEBAR);
				obj.setDisplayName("§c" + p.getName());
				Score d = obj.getScore(Bukkit.getOfflinePlayer("§bMorreu:"));
				Score k = obj.getScore(Bukkit.getOfflinePlayer("§bMatou:"));
				Score ks = obj.getScore(Bukkit
						.getOfflinePlayer("§bKillStreak:"));
				Score m = obj.getScore(Bukkit.getOfflinePlayer("§bMoedas:"));

				d.setScore(SettingsManager.score.getInt(p.getName() + ".deaths"));
				k.setScore(SettingsManager.score.getInt(p.getName() + ".kills"));
				ks.setScore(0);
				m.setScore(MoneyManager.checkMoney(p.getName()));

				if ((k.getScore() > 0) && (d.getScore() > 0)) {
				} else if ((k.getScore() > 0) && (d.getScore() <= 0)) {
				} else if ((k.getScore() == 0) && (d.getScore() == 0)) {
				} else if ((k.getScore() == 0) && (d.getScore() > 0)) {
					k.setScore(0);
				}

				scoreboards.put(p.getName(), sb);
				deaths.put(p.getName(), d);
				kills.put(p.getName(), k);
				money.put(p.getName(), m);
				killst.put(p.getName(), ks);

				p.setScoreboard((Scoreboard) scoreboards.get(p.getName()));
			}
		} else {
			p.setScoreboard((Scoreboard) scoreboards.get(p.getName()));
		}

	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void main(PlayerDeathEvent e) {
		if ((e.getEntity().getKiller() instanceof Player)) {
			Player p = e.getEntity();
			Player killer = e.getEntity().getKiller();
			if (!p.getName().equalsIgnoreCase(killer.getName())) {
				Score d = (Score) deaths.get(p.getName());
				Score k = (Score) kills.get(killer.getName());
				Score ks = (Score) killst.get(killer.getName());
				Score ksp = (Score) killst.get(p.getName());
				d.setScore(d.getScore() + 1);
				k.setScore(k.getScore() + 1);
				ks.setScore(ks.getScore() + 1);

				Main.settings.score
						.set(killer.getName() + ".kills",
								Main.settings.score.getInt(killer.getName()
										+ ".kills") + 1);
				Main.settings.score
						.set(p.getName() + ".deaths",
								Main.settings.score.getInt(p.getName()
										+ ".deaths") + 1);
				Main.settings.saveScore();

				ksp.setScore(0);
				deaths.put(p.getName(), d);
				kills.put(killer.getName(), k);
				killst.put(killer.getName(), ks);
				killst.put(p.getName(), ksp);
				if (ks.getScore() == 5) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 5!");
					}
					MoneyManager.addMoney(killer.getName(), 50);
				} else if (ks.getScore() == 10) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 10!");
					}
					MoneyManager.addMoney(killer.getName(), 150);
				} else if (ks.getScore() == 15) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 15!");
					}
					MoneyManager.addMoney(killer.getName(), 200);
				} else if (ks.getScore() == 20) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 20!");
					}
					MoneyManager.addMoney(killer.getName(), 250);
				} else if (ks.getScore() == 25) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 25!");
					}
					MoneyManager.addMoney(killer.getName(), 300);
				} else if (ks.getScore() == 30) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 30!");
					}
					MoneyManager.addMoney(killer.getName(), 350);
				} else if (ks.getScore() == 35) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 35!");
					}
					MoneyManager.addMoney(killer.getName(), 400);
				} else if (ks.getScore() == 40) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 40!");
					}
					MoneyManager.addMoney(killer.getName(), 450);
				} else if (ks.getScore() == 45) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 45!");
					}
					MoneyManager.addMoney(killer.getName(), 500);
				} else if (ks.getScore() == 50) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 50!");
					}
					MoneyManager.addMoney(killer.getName(), 550);
				} else if (ks.getScore() == 55) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 55!");
					}
					MoneyManager.addMoney(killer.getName(), 600);
				} else if (ks.getScore() == 60) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 60!");
					}
					MoneyManager.addMoney(killer.getName(), 650);
				} else if (ks.getScore() == 65) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 65!");
					}
					MoneyManager.addMoney(killer.getName(), 700);
				} else if (ks.getScore() == 70) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 70!");
					}
					MoneyManager.addMoney(killer.getName(), 750);
				} else if (ks.getScore() == 75) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 75!");
					}
					MoneyManager.addMoney(killer.getName(), 800);
				} else if (ks.getScore() == 80) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 80!");
					}
					MoneyManager.addMoney(killer.getName(), 850);
				} else if (ks.getScore() == 85) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 85!");
					}
					MoneyManager.addMoney(killer.getName(), 900);
				} else if (ks.getScore() == 90) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 90!");
					}
					MoneyManager.addMoney(killer.getName(), 950);
				} else if (ks.getScore() == 100) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						on.sendMessage("§7[§c§l!§7] " + killer.getDisplayName()
								+ " §6esta numa KillStreak de 100!");
					}
					MoneyManager.addMoney(killer.getName(), 1000);
				}

				if ((!Admin.vis.contains(p)) && (!Admin.vis.contains(killer))) {
					p.sendMessage("§cVocê foi morto por §4"
							+ ChatColor.stripColor(killer.getDisplayName()));
					killer.sendMessage("§aVocê matou §2"
							+ ChatColor.stripColor(p.getDisplayName()));
				}

				if ((killer.hasPermission("tag.mod"))
						|| (killer.hasPermission("tag.admin"))
						|| (killer.hasPermission("tag.dono"))
						|| (killer.hasPermission("*"))) {
					MoneyManager.addMoney(killer.getName(), 300);
				} else if ((killer.hasPermission("tag.pro"))
						|| (killer.hasPermission("tag.youtuber"))
						|| (killer.hasPermission("tag.trial"))) {
					MoneyManager.addMoney(killer.getName(), 200);
				} else {
					MoneyManager.addMoney(killer.getName(), 100);
				}

				if ((p.hasPermission("tag.mod"))
						|| (p.hasPermission("tag.admin"))
						|| (p.hasPermission("tag.dono"))
						|| (p.hasPermission("*"))) {
					MoneyManager.removeMoney(p.getName(), 15);
				} else if ((p.hasPermission("tag.pro"))
						|| (p.hasPermission("tag.youtuber"))
						|| (p.hasPermission("tag.trial"))) {
					MoneyManager.removeMoney(p.getName(), 25);
				} else {
					MoneyManager.removeMoney(p.getName(), 50);
				}
			}
		}
	}

	public static void resetScoreboard(Player p) {
		if (kills.containsKey(p.getName())) {
			Score k = (Score) kills.get(p.getName());
			k.setScore(0);
			kills.put(p.getName(), k);
		}
		if (deaths.containsKey(p.getName())) {
			Score d = (Score) deaths.get(p.getName());
			d.setScore(0);
			deaths.put(p.getName(), d);
		}
	}
}
