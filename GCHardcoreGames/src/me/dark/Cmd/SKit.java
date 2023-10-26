package me.dark.Cmd;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;

public class SKit implements CommandExecutor {

	public HashMap<String, ItemStack[]> skit = new HashMap<>();
	public HashMap<String, ItemStack[]> armor = new HashMap<>();

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("skit")) {
			if (!p.hasPermission(Main.perm_modplus)) {
				return true;
			}
			if (args.length > 0) {
				ItemStack[] armor;
				if (args[0].equalsIgnoreCase("create")) {
					if (args.length == 2) {
						ItemStack[] is = p.getInventory().getContents();
						armor = p.getInventory().getArmorContents();
						if (!skit.containsKey(args[1])) {
							skit.put(args[1], is);
							this.armor.put(args[1], armor);
							p.sendMessage("ß7Kit '" + args[1]
									+ "' criado com sucesso");
							return true;
						}
						p.sendMessage("ßcEste kit ja existe");
						return true;
					}
					p.sendMessage("ßcUse: /skit create <nome>");
					return true;
				} else if (args[0].equalsIgnoreCase("apply")) {
					if (args.length >= 3) {
						if (args[1].equalsIgnoreCase("all")) {
							if (args.length < 3) {
								p.sendMessage("ßcUse: /skit apply all <kit>");
								return true;
							}
							String kit = args[2];
							if (skit.containsKey(kit)) {
								for (Player online : Bukkit.getOnlinePlayers()) {
									if (online.getName() == p.getName()) {
										return true;
									}
									if(Main.players.contains(p)) {
									online.sendMessage("ß7Kit '" + kit
											+ "' foi aplicado com sucesso");
									giveKit(online, kit);
									}
								}
								p.sendMessage("ß7Kit '"
										+ kit
										+ "' foi aplicado a todos os players com sucesso");
								giveKit(p, kit);
								return true;
							}
							p.sendMessage("ßcEste kit n√o existe");
							return true;
						}
						else if (args[1].equalsIgnoreCase("area")) {
							if (args.length < 4) {
								p.sendMessage("ßcUse: /skit apply area <blocos> <kit>");
								return true;
							}
							Integer inte = Integer.valueOf(args[2]);
							for (Entity e : p.getNearbyEntities(inte, inte,
									inte)) {
								if (e.getType().equals(EntityType.PLAYER)) {
									String kit = args[3];
									if (skit.containsKey(kit)) {
										p.sendMessage("ß7Kit '"
												+ kit
												+ "' foi aplicado a todos os players em " + inte + " blocos com sucesso");
										Player pl = (Player) e;
										giveKit(pl, kit);

										giveKit(p, kit);
										return true;
									}
									p.sendMessage("ßcEste kit n√o existe");
									return true;
								}
							}
							return true;
						}
						Player pl = Bukkit.getPlayer(args[1]);
						if (pl != null) {
							if (skit.containsKey(args[2])) {
								pl.sendMessage("ß7Kit " + args[2]
										+ " foi aplicado com sucesso");
								if (pl != p)
									p.sendMessage("ß7Kit '" + args[2]
											+ "' foi aplicado com sucesso em '"
											+ pl.getName() + "'");
								giveKit(pl, args[2]);
								return true;
							}
							p.sendMessage(ChatColor.RED + "Este kit n√o existe");
							return true;
						}
					} else {
						p.sendMessage("ßcUse: /skit apply (jogador | all | area <blocos>) <kit>");
						return true;
					}
				} else if (args[0].equalsIgnoreCase("remove")) {
					if (args.length == 2) {
						if (skit.containsKey(args[2])) {
							skit.remove(args[2]);
							this.armor.remove(args[2]);
							p.sendMessage(ChatColor.GRAY + "Kit '" + args[1]
									+ "' removido com sucesso");
							return true;
						}
						p.sendMessage(ChatColor.RED + "Este kit n√o existe");
						return true;
					}
					p.sendMessage(ChatColor.RED + "Use: /skit remove <kit>");
					return true;
				}
			} else {
				p.sendMessage("ßcUse: /skit (apply | create | remove)");
				return true;
			}
		}
		return false;
	}

	public void giveKit(Player p, String kit) {
		p.getInventory().clear();
		p.getInventory()
				.setContents(
						(ItemStack[]) skit
								.get(kit));
		p.getInventory().setArmorContents(
				(ItemStack[]) this.armor
						.get(kit));
	}
}
