package me.everton.WocPvP.Listeners;

import java.util.ArrayList;

import me.everton.WocPvP.KitManager;
import me.everton.WocPvP.Main;
import me.everton.WocPvP.Comandos.Fps;
import me.everton.WocPvP.HG.HG;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class OnDeath implements Listener {
	public static ArrayList<Player> nomove = new ArrayList<>();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void aoMorrer(PlayerDeathEvent e) {
		final Player morreu = e.getEntity().getPlayer();
		e.getDrops().clear();
		e.setDroppedExp(0);
		e.setKeepLevel(false);
		e.setNewExp(0);
		e.setNewLevel(0);
		e.setNewTotalExp(0);
		for (PotionEffect p : morreu.getActivePotionEffects()) {
			morreu.removePotionEffect(p.getType());
		}
		morreu.setHealth(20D);
		morreu.setFoodLevel(20);
		morreu.setExp(0);
		morreu.setFireTicks(0);
		KitManager.resetKit(morreu, false);
		e.setDeathMessage(null);

		if (Fps.fps.contains(morreu)) {
			morreu.teleport(Main.loc("fps", morreu));
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin,
					new Runnable() {
						public void run() {
							UltraKits.KitItems.kitPvP(morreu);
						}
					}, 6L);
		} else if (HG.hg.contains(morreu)) {
			HG.hgin.remove(morreu);
			KitListener.hgkit.remove(morreu);
			morreu.teleport(Main.loc("hg", morreu));
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin,
					new Runnable() {
						public void run() {
							HG.itensHG(morreu);
						}
					}, 6L);
		} else if ((!Fps.fps.contains(morreu)) && (!HG.hgin.contains(morreu))) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin,
					new Runnable() {
						public void run() {
							Main.spawnItens(morreu);
						}
					}, 6L);
			morreu.teleport(Main.loc("spawn", morreu));
		}

		Bukkit.getServer().getScheduler()
				.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
					public void run() {
						morreu.setVelocity(new Vector());
					}
				}, 1L);

		morreu.setGameMode(GameMode.SURVIVAL);
	}
}
