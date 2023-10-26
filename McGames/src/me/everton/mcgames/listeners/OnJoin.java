package me.everton.mcgames.listeners;

import me.tigerhix.lib.bossbar.BossbarLib;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class OnJoin implements Listener{
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.getInventory().setItem(0, new ItemStack(Material.COMPASS));
		p.getInventory().setItem(1, new ItemStack(Material.NETHER_STAR));
		p.getInventory().setItem(3, new ItemStack(Material.PUMPKIN));
		p.getInventory().setItem(4, new ItemStack(Material.POTION));
		p.getInventory().setItem(5, new ItemStack(Material.BONE));
		p.getInventory().setItem(7, new ItemStack(Material.WATCH));
		p.getInventory().setItem(8, new ItemStack(Material.WRITTEN_BOOK));
		
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective ob = sb.registerNewObjective("sidebar", "dummy");
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		ob.setDisplayName("§6Minecraft§fGames");
		ob.getScore("§aCréditos:").setScore(6917);
		ob.getScore("§aLobby:").setScore(7);
		p.setScoreboard(sb);
		
		BossbarLib.getHandler().getBossbar(p).setMessage("§6§lMINECRAFTGAMES.COM.BR");
		BossbarLib.getHandler().updateBossbar(p);
		
		p.sendMessage("§6[VIP§b+§6] §f_Everton_Boss_§7: §fEu _Everton_Boss_ sou VIP e também quero ativá-lo no fórum! Dia: 10/10/2015");
		e.setJoinMessage(null);
	}
}
