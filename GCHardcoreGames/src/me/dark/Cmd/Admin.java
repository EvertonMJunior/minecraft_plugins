package me.dark.Cmd;

import java.util.ArrayList;

import me.dark.Main;
import me.dark.Listener.PreGameFight;
import me.dark.Utils.Vanish;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Admin implements CommandExecutor, Listener{
	public static ArrayList<Player> admin = new ArrayList<Player>();
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission(Main.perm_mod)) return true;
			if (!admin.contains(p)) {
				
				for (Player all : Bukkit.getOnlinePlayers()) {
					p.showPlayer(all);
				}
				
				if (PreGameFight.in1v1.contains(p)) {
					PreGameFight.in1v1.remove(p);
					p.closeInventory();
					p.getInventory().clear();
					p.setHealth(20D);
					p.setFoodLevel(20);
				}
				
				p.sendMessage(Main.prefix + "Você entrou no modo §cAdmin§7!");
				admin.add(p);
				p.setGameMode(GameMode.CREATIVE);
				
				Vanish.hideSpec(p);
				Main.players.remove(p);
			} else {
				p.sendMessage(Main.prefix + "Você saiu do modo §cAdmin§7!");
				admin.remove(p);
				p.setGameMode(GameMode.SURVIVAL);
				
				Vanish.showAll(p);
				Main.players.add(p);
				
			}
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void join(PlayerJoinEvent e) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			e.getPlayer().showPlayer(all);
		}
		for (Player all : admin) {
			Vanish.hideSpec(all);
		}
	}
	@EventHandler
	public void left(PlayerQuitEvent e) {
		admin.remove(e.getPlayer());
	}
	@EventHandler
	public void entitydmg(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			if (Admin.admin.contains(e.getPlayer())) {
				e.getPlayer().openInventory(((Player) e.getRightClicked()).getInventory());
			}
		}
	}
	
}
