package me.everton.epvp.Listeners;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.epvp.Main;
import me.everton.epvp.ServerScoreboard;
import me.everton.epvp.Comandos.Admin;
import me.everton.epvp.Comandos.AutoSoup;
import me.everton.epvp.Comandos.Chat;
import me.everton.epvp.Comandos.ChatStaff;
import me.everton.epvp.Comandos.Construir;
import me.everton.epvp.Comandos.Fps;
import me.everton.epvp.Comandos.Mlg;
import me.everton.epvp.Comandos.Sopa;
import me.everton.epvp.Comandos.Spec;
import me.everton.epvp.Comandos.Specs;
import me.everton.epvp.Comandos.StaffChat;
import me.everton.epvp.Comandos.Tell;
import me.everton.epvp.Comandos.Uteis;
import me.everton.epvp.Comandos.Warp;
import me.everton.epvp.Kits.Habilidades.C4;
import me.everton.epvp.Kits.Habilidades.CopyOfFishingHook;
import me.everton.epvp.Kits.Habilidades.Grappler;
import me.everton.epvp.e1v1.Main1v1;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {
	
	public static void deleteHashMapKey(HashMap<?, ?> h, String v){
		if(h.containsKey(v)){
			h.remove(v);
		}
	}
	
	public static void deleteArrayList(ArrayList<?> a, String v){
		if(a.contains(v)){
			a.remove(v);
		}
	}
	
	
	public static void deletePlayer(Player p){
		deleteHashMapKey(Chat.cd, p.getName());
		deleteHashMapKey(Chat.flood, p.getName());
		deleteHashMapKey(Chat.task, p.getName());
		
		deleteArrayList(Main.grappler, p.getName());
		deleteArrayList(Main.kits, p.getName());
		deleteArrayList(Main.pvp, p.getName());
		deleteArrayList(Main.avatar, p.getName());
		deleteArrayList(Main.legolas, p.getName());
		deleteArrayList(Main.quickdropper, p.getName());
		deleteArrayList(Main.sniper, p.getName());
		deleteArrayList(Main.kangaroo, p.getName());
		deleteArrayList(Main.usandokit, p.getName());
		
		deleteHashMapKey(Grappler.hooks, p.getName());
		deleteArrayList(Main1v1.on1v1, p.getName());
		
		deleteArrayList(Admin.admins, p.getName());
		deleteArrayList(Admin.vis, p.getName());
		
		deleteHashMapKey(AutoSoup.health, p.getName());
		deleteHashMapKey(AutoSoup.inv, p.getName());
		
		deleteArrayList(ChatStaff.sc, p.getName());
		
		deleteArrayList(Construir.construir, p.getName());
		
		deleteArrayList(Fps.fps, p.getName());
		
		deleteArrayList(Mlg.mlg, p.getName());
		deleteArrayList(Mlg.acertou, p.getName());
		
		deleteHashMapKey(Sopa.cd, p.getName());
		deleteHashMapKey(Sopa.task, p.getName());
		
		deleteArrayList(Spec.specs, p.getName());
		deleteArrayList(Specs.vendospecs, p.getName());
		
		deleteArrayList(StaffChat.sc, p.getName());
		
		deleteArrayList(Tell.tellon, p.getName());
		deleteHashMapKey(Tell.tells, p.getName());
		deleteHashMapKey(Tell.last, p.getName());
		deleteHashMapKey(Tell.p, p.getName());
		
		deleteHashMapKey(Uteis.cd, p.getName());
		deleteHashMapKey(Uteis.reports, p.getName());
		deleteHashMapKey(Uteis.task, p.getName());
		
		deleteHashMapKey(Warp.task, p.getName());
		deleteHashMapKey(Warp.loc, p.getName());
		
		deleteHashMapKey(ServerScoreboard.scoreboards, p.getName());
		
		deleteHashMapKey(C4.bombas, p.getName());
	}
	
	@EventHandler
	public void Sair(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		for (Player on : Bukkit.getOnlinePlayers()) {
			on.sendMessage("§7[§c-§7] " + p.getDisplayName());
		}
		
		
		if (Grappler.hooks.containsKey(p.getName()))
	    {
			  ((CopyOfFishingHook)Grappler.hooks.get(p.getName())).remove();
			  Grappler.hooks.remove(p.getName());
	    }
		
		deletePlayer(p);
	}
}
