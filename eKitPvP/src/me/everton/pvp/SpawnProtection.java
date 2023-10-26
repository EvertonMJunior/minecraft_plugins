package me.everton.pvp;

import java.util.ArrayList;

import me.everton.pvp.cmds.Admin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpawnProtection implements CommandExecutor, Listener {
	public static ArrayList<String> protegido = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando In-Game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("spawnprotection")) {
			if (!p.hasPermission("pvp.cmd.spawnprotection")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			if (args.length == 2) {
				Player t = Bukkit.getPlayerExact(args[1]);
				if (t == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}
				if (args[0].equalsIgnoreCase("add")) {
					if (!hasProtection(t)) {
						addProtection(t, true);
						p.sendMessage("§aProteção de Spawn adicionada para "
								+ t.getName() + " com sucesso!");
					} else {
						p.sendMessage("§cEste jogador já possui proteção de spawn!");
					}
				} else if (args[0].equalsIgnoreCase("remove")) {
					if (hasProtection(t)) {
						removeProtection(t, true);
						p.sendMessage("§aProteção de Spawn removida de "
								+ t.getName() + " com sucesso!");
					} else {
						p.sendMessage("§cEste jogador não possui proteção de spawn!");
					}
				} else if (args[0].equalsIgnoreCase("check")) {
					if (hasProtection(t)) {
						p.sendMessage("§a" + t.getName()
								+ " possui proteção de spawn.");
					} else {
						p.sendMessage("§a" + t.getName()
								+ " não possui proteção de spawn.");
					}
				} else {
					p.sendMessage("§cUse /spawnprotection <add/remove/check> <player>");
				}
			} else {
				p.sendMessage("§cUse /spawnprotection <add/remove/check> <player>");
			}
		}
		return false;
	}

	public static void addProtection(Player p, boolean msg) {
		if(!protegido.contains(p.getName())) {
			protegido.add(p.getName());
			if(msg) {
				p.sendMessage("§7[§c!§7] Você ganhou proteção de §c§lSpawn§7!");
				p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 15.5F);
			}
		}
	}

	public static void removeProtection(final Player p, boolean msg) {
		if (protegido.contains(p.getName())) {
			protegido.remove(p.getName());
			if(msg) {
				p.sendMessage("§7[§c!§7] Você perdeu sua proteção de §c§lSpawn§7!");
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 15.5F);
			}
		}
	}

	public static boolean hasProtection(Player p) {
		if (protegido.contains(p.getName())) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getLocation().getBlock().getType() == Material.CARPET
				&& p.getLocation().getBlock().getData() == 14
				|| p.getLocation().clone().subtract(0, 1, 0).getBlock()
						.getType() == Material.CARPET
				&& p.getLocation().clone().subtract(0, 1, 0).getBlock()
						.getData() == 14) {
			if (hasProtection(p)) {
				if (Admin.admins.contains(p.getName())) {
					return;
				}
				p.setVelocity(p.getEyeLocation().getDirection().multiply(3.0D)
						.setY(0.7D));
				p.setNoDamageTicks(2 * 20);
				removeProtection(p, true);
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasProtection(p)) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void noDamageOthes(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasProtection(p)) {
				p.sendMessage("§cVocê possui proteção de spawn!");
				e.setCancelled(true);
			}
		}
	}
}
