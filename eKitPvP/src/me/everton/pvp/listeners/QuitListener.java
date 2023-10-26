package me.everton.pvp.listeners;

import me.everton.pvp.API;
import me.everton.pvp.ScoreboardManager;
import me.everton.pvp.SpawnProtection;
import me.everton.pvp.cmds.Admin;
import me.everton.pvp.cmds.StaffChat;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.habilidades.CopyOfFishingHook;
import me.everton.pvp.kits.habilidades.Grappler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener{
	private void deleteFromData(Player p) {
		API.deleteArrayList(Admin.admins, p.getName());
		API.deleteArrayList(Admin.tr, p.getName());
		API.deleteHashMapKey(Admin.adminInv, p.getName());
		API.deleteHashMapKey(Admin.armor, p.getName());
		API.deleteHashMapKey(Admin.inv, p.getName());
		API.deleteHashMapKey(Admin.inventory, p.getName());
		API.deleteHashMapKey(Admin.rc, p.getName());
		
		API.deleteArrayList(StaffChat.onChat, p.getName());
		
		API.deleteHashMapKey(KitManager.kit, p.getName());
		
		API.deleteArrayList(SpawnProtection.protegido, p.getName());
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (Grappler.hooks.containsKey(e.getPlayer().getName())) {
			((CopyOfFishingHook) Grappler.hooks.get(e.getPlayer().getName())).remove();
			Grappler.hooks.remove(e.getPlayer().getName());
		}
		deleteFromData(p);
		for(Player on : Bukkit.getOnlinePlayers()) {
			on.sendMessage("§7[§c-§7] " + p.getDisplayName());
		}
		e.setQuitMessage(null);
		for(Player on : Bukkit.getOnlinePlayers()) {
			ScoreboardManager.refreshSidebar(on);
		}
	}
}
