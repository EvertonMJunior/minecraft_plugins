package me.everton.WocPvP.Listeners;

import me.everton.WocPvP.SettingsManager;
import me.everton.WocPvP.Comandos.Fake;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {
	@EventHandler
	public void Sair(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		for (Player on : Bukkit.getOnlinePlayers()) {
			on.sendMessage("§7[§c-§7] " + p.getDisplayName());
		}
		SettingsManager.getInstance().getMoney().set("a", "b");
		p.sendMessage(SettingsManager.getInstance().getMoney().getString("A"));
		SettingsManager.getInstance().reloadMoney();
		SettingsManager.getInstance().saveMoney();
		Fake.fakes.remove(p);
		Fake.fakesplay.remove(p);
		Fake.fakestag.remove(p);

	}
}
