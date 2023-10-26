package me.everton.hg.jogo.Timers;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.everton.hg.API;
import me.everton.hg.Main;
import me.everton.hg.Cmds.Admin;
import me.everton.hg.ScoreBoard.Board;

public class TimerPreJogo {
	public static Integer shed_id;

	public static void cancel() {
		if (shed_id != null) {
			Main.sh.cancelTask(shed_id);
			shed_id = null;
			Main.PRE_JOGO = false;
		}
	}

	public static void contagem() {
		Main.PRE_JOGO = true;
		shed_id = Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(),
				new Runnable() {

					@SuppressWarnings("deprecation")
					@Override
					public void run() {if (Main.PRE_JOGO) {
						if ((Main.TEMPO_PREJOGO % 30 == 0)
								&& (Main.TEMPO_PREJOGO != 0)) {
							Bukkit.broadcastMessage("§7[§c!§7] O torneio começa em §c§l"
									+ Main.secToMinSec(Main.TEMPO_PREJOGO)
									+ "§7!");
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.playSound(p.getLocation(), Sound.CLICK, 1F,
										1F);
							}
						}
						
						if(Main.TEMPO_PREJOGO == 0) {
							if(Bukkit.getOnlinePlayers().length - Admin.admins.size() >= Main.MINIMO_PLAYERS) {
								API.startPartida();
								Main.PRE_JOGO = false;
								for(Player p : Bukkit.getOnlinePlayers()) {
									p.playSound(p.getLocation(), Sound.ANVIL_LAND, 15.5F,
											15.5F);
								}
								cancel();
							} else {
								Bukkit.broadcastMessage("§7[§c!§7] Sao necessários §c§l" + Main.MINIMO_PLAYERS + " jogadores §7para que o torneio começe!");
								for(Player p : Bukkit.getOnlinePlayers()) {
									p.removePotionEffect(PotionEffectType.SLOW);
									p.removePotionEffect(PotionEffectType.JUMP);
								}
								Main.TEMPO_PREJOGO = 90;
							}
						}
						for(Player p : Bukkit.getOnlinePlayers()) {
							Board.refreshScore(p);
						}
						
						if(Main.TEMPO_PREJOGO <= 15 && Main.TEMPO_PREJOGO != 0) {
							Bukkit.broadcastMessage("§7[§c!§7] O torneio começa em §c§l"
									+ Main.secToMinSec(Main.TEMPO_PREJOGO)
									+ "§7!");
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.playSound(p.getLocation(), Sound.CLICK, 1F,
										1F);
							}
						}
						
						if(Main.TEMPO_PREJOGO == 10) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.setAllowFlight(false);
								p.setFlying(false);
								Random r = new Random();
								if(r.nextBoolean()) {
									p.teleport(Main.spawn.add(r.nextInt(15), 5, r.nextInt(15)));
								} else {
									p.teleport(Main.spawn.subtract(r.nextInt(15), 0, r.nextInt(15)).add(0, 5, 0));
								}
								
								p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 255));
								p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 200));
							}
						}
						Main.TEMPO_PREJOGO -= 1;
					}
					}
				}, 0, 20L);
	}
}
