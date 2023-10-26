package me.everton.epvp.Listeners;

import java.util.ArrayList;

import me.everton.epvp.KitManager;
import me.everton.epvp.Main;
import me.everton.epvp.Comandos.Admin;
import me.everton.epvp.Comandos.Fps;
import me.everton.epvp.Kits.Habilidades.C4;
import me.everton.epvp.Kits.Habilidades.CopyOfFishingHook;
import me.everton.epvp.Kits.Habilidades.Grappler;
import me.everton.epvp.e1v1.Main1v1;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class OnDeath implements Listener {
	public static ArrayList<String> nomove = new ArrayList<>();

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void aoMorrer(PlayerDeathEvent e) {
		final Player morreu = e.getEntity().getPlayer();
		final Player matou = e.getEntity().getKiller();
		e.getDrops().clear();
		e.setDroppedExp(0);
		e.setKeepLevel(false);
		e.setNewExp(0);
		e.setNewLevel(0);
		e.setNewTotalExp(0);
		if (Grappler.hooks.containsKey(morreu.getName()))
	    {
			  ((CopyOfFishingHook)Grappler.hooks.get(morreu.getName())).remove();
			  Grappler.hooks.remove(morreu.getName());
	    }
		for (PotionEffect p : morreu.getActivePotionEffects()) {
			morreu.removePotionEffect(p.getType());
		}
		morreu.setHealth(20D);
		morreu.setFoodLevel(20);
		morreu.setExp(0);
		morreu.setFireTicks(0);
		morreu.setWalkSpeed(0.2F);
		morreu.setFlySpeed(0.1F);
		KitManager.resetKit(morreu, false);
		e.setDeathMessage(null);

		if (Fps.fps.contains(morreu.getName())) {
			morreu.teleport(Main.loc("fps", morreu));
			KitManager.kitPvP(morreu);
		} else if((Main1v1.onbattle.containsKey(morreu.getName())) && (Main1v1.onbattle.containsKey(matou.getName()))) {
			morreu.teleport(Main.loc("1v1", morreu));
			matou.teleport(Main.loc("1v1", matou));
			morreu.sendMessage("§6Você perdeu o 1v1 para " + ChatColor.stripColor(matou.getName()) + ".");
			Damageable hp = matou;
			String cora = hp.getHealth() + "";
			morreu.sendMessage("§6Ele ficou com " + cora.substring(0, 4) + " coraçoes!");
			matou.sendMessage("§6Você ganhou o 1v1 contra " + ChatColor.stripColor(morreu.getName()) + "!");
			Main1v1.itens1v1(morreu);
			Main1v1.itens1v1(matou);
			Main1v1.onbattle.remove(morreu.getName());
			Main1v1.onbattle.remove(matou.getName());
			
			
			for(Player on : Bukkit.getOnlinePlayers()) {
				if(!Admin.vis.contains(on.getName())) {
					morreu.showPlayer(on);
					matou.showPlayer(on);
				}
			}
		} else if ((!Fps.fps.contains(morreu.getName()))) {
			Main.spawnItens(morreu);
			morreu.teleport(Main.loc("spawn", morreu));
		}
		
		if(C4.bombas.containsKey(morreu.getName())) {
			C4.bombas.get(morreu.getName()).remove();
			C4.bombas.remove(morreu.getName());
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
