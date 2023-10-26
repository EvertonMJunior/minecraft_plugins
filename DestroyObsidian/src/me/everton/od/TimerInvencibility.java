package me.everton.od;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TimerInvencibility {
	public static Integer shed_Id;
	public static int TEMPO = 10;
	
	public static void cancel() {
		if(shed_Id != null) {
			Main.sh.cancelTask(shed_Id);
			shed_Id = null;
		}
	}
	
	public static void contagem() {
		Main.INVENCIBILIDADE = true;
		Main.EM_JOGO = true;
		Main.PRE_JOGO = false;
		shed_Id = Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(TEMPO == 0) {
					Bukkit.broadcastMessage("§7[§c!§7] A invencibilidade §c§lacabou§7!");
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 15.5F, 15.5F);
						Main.INVENCIBILIDADE = false;
						BarAPI.setMessage(p, "§b§lObsidian §6§lDestroyer§a ➤ "
								+ API.getGameStage().name() + "!");
					}
					cancel();
				} else {
					for(Player p : Bukkit.getOnlinePlayers()) {
						BarAPI.setMessage(p, "§b§lObsidian §6§lDestroyer§a ➤ Invencibilidade: <tempo>!".replace("<tempo>", API.secToMin(TEMPO)));
					}
					if(TEMPO % 30 == 0) {
						Bukkit.broadcastMessage("§7[§c!§7] A invencibilidade acaba em §c§l<tempo>§7!".replace("<tempo>", API.secToMinSec(TEMPO)));
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
						}
					}
					
					if(TEMPO <= 15) {
						Bukkit.broadcastMessage("§7[§c!§7] A invencibilidade acaba em §c§l<tempo>§7!".replace("<tempo>", API.secToMinSec(TEMPO)));
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
						}
					}
				}
				TEMPO--;
			}
		}, 0L, 20L);
	}
}
