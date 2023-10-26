package me.everton.WocPvP.Comandos;

import java.util.ArrayList;

import me.everton.WocPvP.Main;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

public class Mlg implements Listener, CommandExecutor {
	public static ArrayList<Player> mlg = new ArrayList<>();
	public static ArrayList<Player> acertou = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("mlg")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("set")) {
					Main.setLoc(p, "mlg");
					return true;
				}
				return true;
			}

			if (mlg.contains(p)) {
				mlg.remove(p);
				p.teleport(Main.loc("spawn", p));
			} else {
				mlg.add(p);
				p.teleport(Main.loc("mlg", p));
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.getInventory().setItem(0,
						new ItemStack(Material.WATER_BUCKET));
				p.updateInventory();
			}
		}
		return false;
	}

	@EventHandler
	public void aoDano(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if ((mlg.contains(p)) && (Main.areaConstruir(p))) {
				if ((e.getCause() == DamageCause.FALL)
						&& (!acertou.contains(p))) {
					e.setCancelled(true);
					p.sendMessage("§cVocê errou!");
					p.teleport(Main.loc("mlg", p));
					p.getInventory().clear();
					p.getInventory().setItem(0,
							new ItemStack(Material.WATER_BUCKET));
				}				
			} else if(mlg.contains(p)){
				e.setCancelled(true);
				p.sendMessage("§cVocê errou!");
				p.teleport(Main.loc("mlg", p));
				p.getInventory().clear();
				p.getInventory().setItem(0,
						new ItemStack(Material.WATER_BUCKET));
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void aoMlg(final PlayerBucketEmptyEvent e) {
		final Player p = e.getPlayer();
		if ((mlg.contains(p)) && (Main.areaConstruir(p))) {
			if((e.getBlockClicked().getX() == p.getLocation().getBlockX()) && (e.getBlockClicked().getZ() == p.getLocation().getBlockZ()))
			p.sendMessage("§aVocê acertou!");
			e.getBlockClicked().getRelative(BlockFace.UP)
					.setType(e.getBlockClicked().getType());
			Main.sh.scheduleSyncDelayedTask(Main.plugin, new Runnable() {

				@Override
				public void run() {
					e.getBlockClicked().getRelative(BlockFace.UP)
							.setType(Material.AIR);
				}
			}, 1L);
			p.teleport(Main.loc("mlg", p));
			p.getInventory().clear();
			p.getInventory().setItem(0, new ItemStack(Material.WATER_BUCKET));
			p.updateInventory();
			acertou.add(p);
		}
	}
}
