package me.dark.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.MySQL.SQLStatus;
import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;
import me.dark.kit.KitManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Scoreboard implements Runnable {
	
	private Map<UUID, ScoreboardManager> scoreboards = new HashMap<UUID, ScoreboardManager>();	
	

	@SuppressWarnings("deprecation")
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (hasBoard(p)) {
				Kit k = KitManager.getKit(p);
				ScoreboardManager board = getScoreboardManager(p);
				if (Main.estado == GameState.PREGAME) {
						board.setDisplayName("        §b§lGoodCraft     ");
						board.setText(" ", "", 12);
						board.setText("§fRank §b➛ ", DarkUtils.getRank(p).replaceAll("Plus", "+"), 11);
						board.setText(" ", "", 10);
						board.setText("§fRanking §b➛ ", ("§a"+Main.rankPlayer.get(p)).replace("1001", "1000+"), 9);
						board.setText("§fGoodCoins §b➛ ", "§a"+SQLStatus.getCoins(p.getUniqueId()), 8);
						board.setText(" ", "", 7);;
						if (k == null) {
							board.setText("§fKit §b➛ ", "§7Nenhum", 6);
						} else {
							board.setText("§fKit §b➛ ", "§7"+k.toString(), 6);
						}
						board.setText(" ", "" , 5);
						board.setText("§fIniciando §b➛ ", "§7"+DarkUtils.tempoInt(Main.toStart).replace("-", ""), 4);
						board.setText("§fJogadores §b➛ ", "§7"+Main.players.size()+"/"+Bukkit.getMaxPlayers(), 3);
						board.setText(" ", "" , 2);
						board.setText("§7       hg.good", "§7craft.net   ", 1);
						
					} else if (Main.estado == GameState.INVENCIBILITY) {
						
						board.setDisplayName("        §b§lGoodCraft     ");
						board.setText(" ", "", 12);
						board.setText("§fRank §b➛ ", DarkUtils.getRank(p).replaceAll("Plus", "+"), 11);
						board.setText(" ", "", 10);
						board.setText("§fRanking §b➛ ", ("§a"+Main.rankPlayer.get(p)).replace("1001", "1000+"), 9);
						board.setText("§fGoodCoins §b➛ ", "§a"+SQLStatus.getCoins(p.getUniqueId()), 8);
						board.setText(" ", "", 7);
						if (k == null) {
							board.setText("§fKit §b➛ ", "§7Nenhum", 6);
						} else {
							board.setText("§fKit §b➛ ", "§7"+k.toString(), 6);
						}
						String tempo = DarkUtils.tempoInt(120-Main.gameTime);
						board.setText(" ", "" , 5);
						board.setText("§fInvencibili", "dade §b➛ §7"+tempo.replace("-", ""), 4);
						board.setText("§fJogadores §b➛ ", "§7"+Main.players.size()+"/"+Bukkit.getMaxPlayers(), 3);
						board.setText(" ", "" , 2);
						board.setText("§7       hg.good", "§7craft.net   ", 1);
					} else {
						board.setDisplayName("        §b§lGoodCraft     ");
						board.setText(" ", "", 12);
						board.setText("§fRank §b➛ ", DarkUtils.getRank(p).replaceAll("Plus", "+"), 11);
						board.setText(" ", "", 10);
						board.setText("§fRanking §b➛ ", ("§a"+Main.rankPlayer.get(p)).replace("1001", "1000+"), 9);
						board.setText("§fGoodCoins §b➛ ", "§a"+SQLStatus.getCoins(p.getUniqueId()), 8);
						board.setText(" ", "", 7);
						if (k == null) {
							board.setText("§fKit §b➛ ", "§7Nenhum", 6);
						} else {
							board.setText("§fKit §b➛ ", "§7"+k.toString(), 6);
						}
						board.setText(" ", "" , 5);
						board.setText("§fTempo ", "§b➛ §7"+DarkUtils.tempoInt(Main.gameTime).replace("-", ""), 4);
						board.setText("§fJogadores §b➛ ", "§7"+Main.players.size()+"/"+Bukkit.getMaxPlayers(), 3);
						board.setText(" ", "" , 2);
						board.setText("§7       hg.good", "§7craft.net   ", 1);
					}	
			  }
		}
		for (Player p : Main.players) {
			if (Main.estado == GameState.PREGAME) return;
			if (Main.estado == GameState.INVENCIBILITY) return;
			if (Main.estado == GameState.WIN) return;
			if (p.getLocation().getBlockY() > 150) {
				p.damage(5.0D);
			}else if (p.getLocation().getBlockX() > 500) {
				p.damage(5.0D);
			}else if (p.getLocation().getBlockZ() > 500) {
				p.damage(5.0D);
			}else if (p.getLocation().getBlockX() < -500) {
				p.damage(5.0D);
			}else if (p.getLocation().getBlockZ() < -500) {
				p.damage(5.0D);
			}
		}
	}
	
	public boolean hasBoard(Player p) {
		return scoreboards.containsKey(p.getUniqueId());
	}
	
	public ScoreboardManager getScoreboardManager(Player p) {
		return scoreboards.get(p.getUniqueId());
	}
	
	public void createBoard(Player p) {
		if (!scoreboards.containsKey(p.getUniqueId())) {
			scoreboards.put(p.getUniqueId(), new ScoreboardManager(p, "        §b§lGoodCraft     "));
		}
	}
	
	public void removeBoard(Player p) {
		if (scoreboards.containsKey(p.getUniqueId())) {
			scoreboards.get(p.getUniqueId()).reset();
			scoreboards.remove(p.getUniqueId());
		}
	}

}