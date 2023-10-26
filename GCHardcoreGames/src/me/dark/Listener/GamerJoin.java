package me.dark.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Chests.Chests;
import me.dark.Game.GameState;
import me.dark.MySQL.MySQL;
import me.dark.MySQL.SQLStatus;
import me.dark.Title.TitleAPI;

public class GamerJoin implements Listener{
	
	public static void preInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setItem(4, GeralGameL.chests);
		p.getInventory().setItem(2, HotBar.status);
		p.getInventory().setItem(0, Chests.chest);
		p.getInventory().setItem(6, HotBar.soup);
		p.getInventory().setItem(8, PreGameFight.p1v1);
		
		p.getInventory().setHeldItemSlot(4);
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		MySQL.addPlayerToTable(p.getUniqueId(), "good_players");
		MySQL.addPlayerToTable1(p.getUniqueId(), "good_coins");
		MySQL.addPlayerToTable(p.getUniqueId(), "good_chests");
		if (MySQL.ativo) {
			if (!Main.rankPlayer.containsKey(p)) {
				Main.rankPlayer.put(p, SQLStatus.getRank(p.getUniqueId()));
			}
		}
		if (e.getResult() == Result.KICK_WHITELIST) {
			e.setKickMessage("§3Estamos em manutenção, aguarde!");
		}else if (e.getResult() == Result.KICK_FULL) {
			if (!p.hasPermission(Main.perm_bronze)) {
				e.setKickMessage("§3Servidor lotado!\n \n§7Compre vip e entre com o servidor lotado!\n"+Main.site);
			}
		}else if (Main.estado==null) {
			e.disallow(Result.KICK_OTHER, "§cAguarde...");
		} else if (Main.estado != GameState.PREGAME) {
			if (Main.gameTime < 300) {
				if (!p.hasPermission(Main.perm_prata)) {
					e.disallow(Result.KICK_OTHER, "§3O jogo já inicou!\n \n§7Compre vip e entre até os 5 primeiros minutos\n§7"+Main.site);
				}
			} else {
				if (!p.hasPermission(Main.perm_mod)) {
					e.disallow(Result.KICK_OTHER, "§3O jogo já inicou!\n\n§7"+Main.site);
				}
			}
		} 
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		Main.sb.removeBoard(p);
		if (Main.estado == GameState.PREGAME) {
			Main.players.remove(p);
		}
		if (PreGameFight.in1v1.contains(e.getPlayer())) {
			PreGameFight.in1v1.remove(e.getPlayer());
		}
		LavaChallenge.inLava.remove(e.getPlayer());
	}
	
	@EventHandler
	public void kick(PlayerKickEvent e) {
		Player p = e.getPlayer();
		Main.sb.removeBoard(p);
		if (Main.estado == GameState.PREGAME) {
			Main.players.remove(p);
		}
		if (PreGameFight.in1v1.contains(e.getPlayer())) {
			PreGameFight.in1v1.remove(e.getPlayer());
		}
		LavaChallenge.inLava.remove(e.getPlayer());
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (PreGameFight.in1v1.contains(e.getPlayer())) {
			PreGameFight.in1v1.remove(e.getPlayer());
		}
		LavaChallenge.inLava.remove(e.getPlayer());
		e.setJoinMessage(null);
		p.setWalkSpeed(0.2f);
		
		p.setHealth(20.0D);
		p.setFoodLevel(20);
		TitleAPI.sFullTitulo(p, 20, 20, 20, "§bGoodCraft §3HG", "§fSobreviva e seja o melhor!");
		if (Main.estado == GameState.PREGAME) {
			p.teleport(Main.usingWorld.getSpawnLocation());
			preInv(p);	
			if (!Main.players.contains(p)) {
				Main.players.add(p);
			}
		} else {
			if (!GamerRelog.reloging.contains(p)) {
				if (Main.gameTime >= 300) {
					p.chat("/admin");
				} else {
					if (!Main.players.contains(p)) {
						Main.players.add(p);
					}
					p.getInventory().addItem(new ItemStack(Material.COMPASS));
				}	
			}
		}
		Main.sb.createBoard(p);
	}

}
