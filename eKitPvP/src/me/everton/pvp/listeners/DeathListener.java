package me.everton.pvp.listeners;

import me.everton.pvp.API;
import me.everton.pvp.LocationsManager;
import me.everton.pvp.Main;
import me.everton.pvp.ScoreboardManager;
import me.everton.pvp.SpawnProtection;
import me.everton.pvp.ScoreManager.Deaths;
import me.everton.pvp.ScoreManager.Kills;
import me.everton.pvp.ScoreManager.Money;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.habilidades.CopyOfFishingHook;
import me.everton.pvp.kits.habilidades.Grappler;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class DeathListener implements Listener{
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		try {
			if(e.getEntity().hasMetadata("NPC")) {
				return;
			}
		} catch(Exception ex) {
			
		}
		
		try {
			if(e.getEntity().getKiller().hasMetadata("NPC")) {
				return;
			}
		} catch(Exception ex) {
			
		}
		
		final Player p = e.getEntity();
		if (Grappler.hooks.containsKey(p.getName())) {
			((CopyOfFishingHook) Grappler.hooks.get(p.getName())).remove();
			Grappler.hooks.remove(p.getName());
		}
		p.setHealth(20.0D);
		p.teleport(LocationsManager.getSpawn());
		KitManager.resetKit(p);
		e.getDrops().clear();
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		for(PotionEffect pot : p.getActivePotionEffects()) {
			p.removePotionEffect(pot.getType());
		}
		p.setFoodLevel(20);
		p.setSaturation(20F);
		
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				p.setVelocity(new Vector());
				API.itensIniciais(p);
				p.updateInventory();
				p.setFireTicks(0);
				((CraftPlayer)p).getHandle().extinguish();
			}
		}, 1L);
		
		if(e.getEntity().getKiller() instanceof Player) {
			Player k = (Player) e.getEntity().getKiller();
			p.sendMessage("§cVocê foi morto por §4" + ChatColor.stripColor(k.getPlayerListName()) + "§c!");
			if(p.hasPermission("pvp.moneyboost1")) {
				Money.removeMoney(p.getName(), 25);
			} else {
				Money.removeMoney(p.getName(), 50);
			}
			k.sendMessage("§aVocê matou §2" + ChatColor.stripColor(p.getPlayerListName()) + "§a!");
			if(k.hasPermission("pvp.moneyboost1")) {
				Money.addMoney(k.getName(), 200);
			} else {
				Money.addMoney(k.getName(), 100);
			}
			
			Kills.addKill(k.getName(), 1);
			Deaths.addDeath(p.getName(), 1);
			
			ScoreboardManager.refreshSidebar(k);
		} else {
			p.sendMessage("§cVocê morreu!");
		}
		ScoreboardManager.refreshSidebar(p);
		e.setDeathMessage(null);
		SpawnProtection.addProtection(p, true);
	}
}
