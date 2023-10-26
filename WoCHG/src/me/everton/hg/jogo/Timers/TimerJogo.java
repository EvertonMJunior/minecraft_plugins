package me.everton.hg.jogo.Timers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.everton.hg.API;
import me.everton.hg.Main;
import me.everton.hg.ScoreBoard.Board;
import me.everton.hg.jogo.ArenaFinal;
import me.everton.hg.jogo.Feast;

public class TimerJogo {
	public static Integer shed_id;

	public static void cancel() {
		if (shed_id != null) {
			Main.sh.cancelTask(shed_id);
			shed_id = null;
		}
	}

	public static void contagem() {
		Main.EM_JOGO = true;
		shed_id = Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(),
				new Runnable() {

					@SuppressWarnings("deprecation")
					@Override
					public void run() {
						for(Player p : Bukkit.getOnlinePlayers()) {
							Board.refreshScore(p);
						}
						if ((Main.TEMPO_JOGO % 30 == 0)
								&& (Main.TEMPO_JOGO >= 2700)
								&& (Main.TEMPO_JOGO < 3000)) {
							Bukkit.broadcastMessage("§7[§c!§7] A §c§larena final §7spawnará em §c§l"
									+ Main.secToMinSec(3000 - Main.TEMPO_JOGO)
									+ "§7!");
						} else if ((Main.TEMPO_JOGO % 30 == 0)
								&& (Main.TEMPO_JOGO >= 4200)
								&& (Main.TEMPO_JOGO < 4500)) {
							Bukkit.broadcastMessage("§7[§c!§7] A §c§lpartida §7reiniciará em §c§l"
									+ Main.secToMinSec(4500 - Main.TEMPO_JOGO)
									+ "§7!");
						}

						if (Main.TEMPO_JOGO == 600) {
							Feast.contagemFeast();
						}

						switch (Main.TEMPO_JOGO) {
						case 3000:
							Bukkit.broadcastMessage("§7[§c!§7] A partida atingiu §c§l50 minutos§7! Todos foram teleportados para a arena final! Que vença o melhor!");
							ArenaFinal.spawnar();
							for (Player p : Bukkit.getOnlinePlayers()) {
								ArenaFinal.teleportar(p);
							}
							break;
						case 4500:
							Bukkit.broadcastMessage("§7[§c!§7] A partida atingiu §c§l1 hora e 15 minutos§7! Servidor reiniciando!");
							API.shutdownServer("A partida atingiu 1 hora e 15 minutos!");
							break;
						}
						Main.TEMPO_JOGO++;
					}
				}, 0, 20L);
	}
}
