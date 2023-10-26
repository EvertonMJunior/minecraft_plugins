package me.everton.hg.jogo.Timers;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.everton.hg.Main;
import me.everton.hg.ScoreBoard.Board;

public class TimerInvencibilidade {
	public static Integer shed_id;

	public static void cancel() {
		if (shed_id != null) {
			Main.sh.cancelTask(shed_id);
			shed_id = null;
		}
	}

	public static void contagem() {
		Main.INVENCIBILIDADE = true;
		shed_id = Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(),
				new Runnable() {

					@SuppressWarnings("deprecation")
					@Override
					public void run() {
						if (Main.INVENCIBILIDADE) {
							if ((Main.TEMPO_INVENCIBILIDADE % 30 == 0)
									&& (Main.TEMPO_INVENCIBILIDADE != 0)) {
								Bukkit.broadcastMessage("§7[§c!§7] A invencibilidade acaba em §c§l"
										+ Main.secToMinSec(Main.TEMPO_INVENCIBILIDADE)
										+ "§7!");
								for(Player p : Bukkit.getOnlinePlayers()) {
									p.playSound(p.getLocation(), Sound.CLICK, 1F,
											1F);
								}
							}
							
							if(Main.TEMPO_INVENCIBILIDADE == 0) {
								Bukkit.broadcastMessage("§7[§c!§7] A invencibilidade acabou! Que vença o melhor!");
								Main.INVENCIBILIDADE = false;
								for(Player p : Bukkit.getOnlinePlayers()) {
									p.playSound(p.getLocation(), Sound.ANVIL_LAND, 15.5F,
											15.5F);
								}
								cancel();
							}
							for(Player p : Bukkit.getOnlinePlayers()) {
								Board.refreshScore(p);
							}
							
							if(Main.TEMPO_INVENCIBILIDADE <= 15 && Main.TEMPO_INVENCIBILIDADE != 0) {
								Bukkit.broadcastMessage("§7[§c!§7] A invencibilidade acaba em §c§l"
										+ Main.secToMinSec(Main.TEMPO_INVENCIBILIDADE)
										+ "§7!");
								for(Player p : Bukkit.getOnlinePlayers()) {
									p.playSound(p.getLocation(), Sound.CLICK, 1F,
											1F);
								}
							}
							Main.TEMPO_INVENCIBILIDADE -= 1;
						}
					}
				}, 0, 20L);
	}
}