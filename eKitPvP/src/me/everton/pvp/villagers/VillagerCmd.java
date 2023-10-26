package me.everton.pvp.villagers;

import java.util.HashMap;

import me.confuser.barapi.BarAPI;
import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.Msgs;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class VillagerCmd implements CommandExecutor {
	private static HashMap<String, Inventory> invs = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game");
			return true;
		}
		final Player p = (Player) sender;

		if (label.equalsIgnoreCase("spawnvillager")) {
			if (!p.hasPermission("pvp.cmd.spawnvillager")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			if (args.length == 1) {
				try {
					switch (Integer.valueOf(args[0])) {
					case 1:
						Villager1.spawnVillager(p);
						break;
					case 2:
						Villager2.spawnVillager(p);
						break;
					case 3:
						Villager3.spawnVillager(p);
						break;
					case 4:
						Villager4.spawnVillager(p);
						break;

					default:
						p.sendMessage("§eUse:");
						p.sendMessage("- §6/spawnvillager 1 §f- §3Spawna o Villager §8“§e§lGanhe Prêmios§8”");
						p.sendMessage("- §6/spawnvillager 2 §f- §3Spawna o Villager §8“§e§lInformaçoes§8”");
						p.sendMessage("- §6/spawnvillager 3 §f- §3Spawna o Villager §8“§e§lCompre Kits§8”");
						p.sendMessage("- §6/spawnvillager 4 §f- §3Spawna o Villager §8“§e§lCompre Itens§8”");
						break;
					}
				} catch (NumberFormatException e) {
					p.sendMessage("§eUse:");
					p.sendMessage("- §6/spawnvillager 1 §f- §3Spawna o Villager §8“§e§lGanhe Prêmios§8”");
					p.sendMessage("- §6/spawnvillager 2 §f- §3Spawna o Villager §8“§e§lInformaçoes§8”");
					p.sendMessage("- §6/spawnvillager 3 §f- §3Spawna o Villager §8“§e§lCompre Kits§8”");
					p.sendMessage("- §6/spawnvillager 4 §f- §3Spawna o Villager §8“§e§lCompre Itens§8”");
				}
			} else {
				p.sendMessage("§eUse:");
				p.sendMessage("- §6/spawnvillager 1 §f- §3Spawna o Villager §8“§e§lGanhe Prêmios§8”");
				p.sendMessage("- §6/spawnvillager 2 §f- §3Spawna o Villager §8“§e§lInformações§8”");
				p.sendMessage("- §6/spawnvillager 3 §f- §3Spawna o Villager §8“§e§lCompre Kits§8”");
				p.sendMessage("- §6/spawnvillager 4 §f- §3Spawna o Villager §8“§e§lCompre Itens§8”");
				BarAPI.useSpigotHack();
				new BukkitRunnable() {
					int time = 120;

					@Override
					public void run() {
						BarAPI.removeBar(p);
						BarAPI.setMessage(p, Msgs.nomeServidor
								+ " §4➜ §f§lComeça em " + time + " segundos",
								time);
						time--;
					}
				}.runTaskTimer(Main.getPlugin(), 0L, 20L);
			}
		}

		if (label.equalsIgnoreCase("criarinv")) {
			if (!p.hasPermission("pvp.cmd.criarinv")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			if (args.length == 2) {
				String nome = args[1];

				if (invs.containsKey(nome)) {
					p.sendMessage("§cEste inventário já existe! Use /abririnv "
							+ nome);
					return true;
				}

				p.sendMessage("§aInventário " + nome + " criado!");
				Inventory inv = Bukkit.createInventory(p,
						Integer.valueOf(args[0]), nome.replace("&", "§"));
				API.fillVillagerInv(inv);
				p.openInventory(inv);
				invs.put(nome, inv);
			} else {
				p.sendMessage("§cUse /criarinv <slots> <nome>");
			}
		}

		if (label.equalsIgnoreCase("abririnv")) {
			if (!p.hasPermission("pvp.cmd.abririnv")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			if (args.length >= 1) {
				String nome = args[0];
				
				if (!invs.containsKey(nome)) {
					p.sendMessage("§cEste inventário nao existe!");
					return true;
				}

				Inventory inv = invs.get(nome);
				p.openInventory(inv);
			} else {
				p.sendMessage("§cUse /abririnv <nome>");
			}
		}
		return false;
	}
}
