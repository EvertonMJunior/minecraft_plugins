package me.everton.WocPvP.Comandos;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

public class Tag implements CommandExecutor, Listener {
	public static HashMap<String, Player> tagusada = new HashMap<String, Player>();
	public static String dono = "§4§o";
	public static String admin = "§c";
	public static String mod = "§5";
	public static String builder = "§3";
	public static String trial = "§d";
	public static String yt = "§b";
	public static String tvip = "§9§o";
	public static String pro = "§6";
	public static String vip = "§a";
	public static String normal = "§7";
	public static String cortag;

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player p = (Player) sender;
		if (!(sender instanceof Player)) {
			p.sendMessage("Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("teg")) {
			if (args.length == 0) {
				p.sendMessage(ChatColor.WHITE + "********************");
				if (p.hasPermission("*")) {
					p.sendMessage(ChatColor.WHITE + "- " + dono + "Dono");
					p.sendMessage(ChatColor.WHITE + "- " + admin + "Admin");
					p.sendMessage(ChatColor.WHITE + "- " + mod + "Mod");
					p.sendMessage(ChatColor.WHITE + "- " + builder + "Builder");
					p.sendMessage(ChatColor.WHITE + "- " + trial + "Trial");
					p.sendMessage(ChatColor.WHITE + "- " + yt + "Youtuber");
					p.sendMessage(ChatColor.WHITE + "- " + tvip + "TVip");
					p.sendMessage(ChatColor.WHITE + "- " + pro + "Pro");
					p.sendMessage(ChatColor.WHITE + "- " + vip + "Vip");

				} else if (sender.hasPermission("tag.dono")) {
					p.sendMessage(ChatColor.WHITE + "- " + dono + "Dono");
					p.sendMessage(ChatColor.WHITE + "- " + admin + "Admin");
					p.sendMessage(ChatColor.WHITE + "- " + mod + "Mod");
					p.sendMessage(ChatColor.WHITE + "- " + builder + "Builder");
					p.sendMessage(ChatColor.WHITE + "- " + trial + "Trial");
					p.sendMessage(ChatColor.WHITE + "- " + yt + "Youtuber");
					p.sendMessage(ChatColor.WHITE + "- " + pro + "Pro");
					p.sendMessage(ChatColor.WHITE + "- " + vip + "Vip");

				} else if (sender.hasPermission("tag.admin")) {
					p.sendMessage(ChatColor.WHITE + "- " + admin + "Admin");
					p.sendMessage(ChatColor.WHITE + "- " + mod + "Mod");
					p.sendMessage(ChatColor.WHITE + "- " + builder + "Builder");
					p.sendMessage(ChatColor.WHITE + "- " + trial + "Trial");
					p.sendMessage(ChatColor.WHITE + "- " + yt + "Youtuber");
					p.sendMessage(ChatColor.WHITE + "- " + pro + "Pro");
					p.sendMessage(ChatColor.WHITE + "- " + vip + "Vip");

				} else if (sender.hasPermission("tag.mod")) {
					p.sendMessage(ChatColor.WHITE + "- " + mod + "Mod");
					p.sendMessage(ChatColor.WHITE + "- " + builder + "Builder");
					p.sendMessage(ChatColor.WHITE + "- " + trial + "Trial");
					p.sendMessage(ChatColor.WHITE + "- " + yt + "Youtuber");
					p.sendMessage(ChatColor.WHITE + "- " + pro + "Pro");
					p.sendMessage(ChatColor.WHITE + "- " + vip + "Vip");

				} else if (sender.hasPermission("tag.builder")) {
					p.sendMessage(ChatColor.WHITE + "- " + builder + "Builder");
					p.sendMessage(ChatColor.WHITE + "- " + trial + "Trial");
					p.sendMessage(ChatColor.WHITE + "- " + yt + "Youtuber");
					p.sendMessage(ChatColor.WHITE + "- " + pro + "Pro");
					p.sendMessage(ChatColor.WHITE + "- " + vip + "Vip");

				} else if (sender.hasPermission("tag.trial")) {
					p.sendMessage(ChatColor.WHITE + "- " + trial + "Trial");
					p.sendMessage(ChatColor.WHITE + "- " + yt + "Youtuber");
					p.sendMessage(ChatColor.WHITE + "- " + pro + "Pro");
					p.sendMessage(ChatColor.WHITE + "- " + vip + "Vip");

				} else if (sender.hasPermission("tag.youtuber")) {
					p.sendMessage(ChatColor.WHITE + "- " + yt + "Youtuber");
					p.sendMessage(ChatColor.WHITE + "- " + pro + "Pro");
					p.sendMessage(ChatColor.WHITE + "- " + vip + "Vip");

				} else if (sender.hasPermission("tag.pro")) {
					p.sendMessage(ChatColor.WHITE + "- " + pro + "Pro");
					p.sendMessage(ChatColor.WHITE + "- " + vip + "Vip");

				} else if (sender.hasPermission("tag.vip")) {
					p.sendMessage(ChatColor.WHITE + "- " + vip + "Vip");
				}
				p.sendMessage(ChatColor.WHITE + "- " + normal + "Normal");
				p.sendMessage(ChatColor.WHITE + "********************");
				return true;
			}
			String tag = args[0];
			if (args.length == 1) {
				if (tag.equalsIgnoreCase("dono")) {
					if (sender.hasPermission("tag.dono")) {
						setarTag("dono", p, true);
					} else {
						p.sendMessage(ChatColor.DARK_RED
								+ "§7[§c!§7] Sem permissao!");
					}
				} else if (tag.equalsIgnoreCase("admin")) {
					if ((sender.hasPermission("tag.admin") || sender
							.hasPermission("tag.dono"))) {
						setarTag("admin", p, true);
					} else {
						p.sendMessage(ChatColor.DARK_RED
								+ "§7[§c!§7] Sem permissao!");
					}
				} else if (tag.equalsIgnoreCase("mod")) {
					if ((sender.hasPermission("tag.mod"))
							|| sender.hasPermission("tag.admin")
							|| sender.hasPermission("tag.dono")) {
						setarTag("mod", p, true);
					} else {
						p.sendMessage(ChatColor.DARK_RED
								+ "§7[§c!§7] Sem permissao!");
					}
				} else if (tag.equalsIgnoreCase("builder")) {
					if ((sender.hasPermission("tag.builder")
							|| sender.hasPermission("tag.mod")
							|| sender.hasPermission("tag.admin") || sender
								.hasPermission("tag.dono"))) {
						setarTag("builder", p, true);
					} else {
						p.sendMessage(ChatColor.DARK_RED
								+ "§7[§c!§7] Sem permissao!");
					}
				} else if (tag.equalsIgnoreCase("trial")) {
					if ((sender.hasPermission("tag.trial")
							|| sender.hasPermission("tag.builder")
							|| sender.hasPermission("tag.mod")
							|| sender.hasPermission("tag.admin") || sender
								.hasPermission("tag.dono"))) {
						setarTag("trial", p, true);
					} else {
						p.sendMessage(ChatColor.DARK_RED
								+ "§7[§c!§7] Sem permissao!");
					}
				} else if ((tag.equalsIgnoreCase("youtuber") || (tag
						.equalsIgnoreCase("yt")))) {
					if ((sender.hasPermission("tag.youtuber")
							|| sender.hasPermission("tag.builder")
							|| sender.hasPermission("tag.trial")
							|| sender.hasPermission("tag.mod")
							|| sender.hasPermission("tag.admin") || sender
								.hasPermission("tag.dono"))) {
						setarTag("youtuber", p, true);
					} else {
						p.sendMessage(ChatColor.DARK_RED
								+ "§7[§c!§7] Sem permissao!");
					}
				} else if ((tag.equalsIgnoreCase("tvip"))) {
					if (sender.hasPermission("tag.tvip") || (sender.hasPermission("tag.pro"))
							|| sender.hasPermission("tag.builder")
							|| sender.hasPermission("tag.youtuber")
							|| sender.hasPermission("tag.trial")
							|| sender.hasPermission("tag.mod")
							|| sender.hasPermission("tag.admin")
							|| sender.hasPermission("tag.dono")) {
						setarTag("tvip", p, true);
					} else {
						p.sendMessage(ChatColor.DARK_RED
								+ "§7[§c!§7] Sem permissao!");
					}
				} else if ((tag.equalsIgnoreCase("pro"))) {
					if ((sender.hasPermission("tag.pro"))
							|| sender.hasPermission("tag.builder")
							|| sender.hasPermission("tag.youtuber")
							|| sender.hasPermission("tag.trial")
							|| sender.hasPermission("tag.mod")
							|| sender.hasPermission("tag.admin")
							|| sender.hasPermission("tag.dono")) {
						setarTag("pro", p, true);
					} else {
						p.sendMessage(ChatColor.DARK_RED
								+ "§7[§c!§7] Sem permissao!");
					}
				} else if (tag.equalsIgnoreCase("vip")) {
					if ((sender.hasPermission("tag.vip")
							|| sender.hasPermission("tag.builder")
							|| sender.hasPermission("tag.pro")
							|| sender.hasPermission("tag.youtuber")
							|| sender.hasPermission("tag.trial")
							|| sender.hasPermission("tag.mod")
							|| sender.hasPermission("tag.admin") || sender
								.hasPermission("tag.dono"))) {
						setarTag("vip", p, true);
					} else {
						p.sendMessage(ChatColor.DARK_RED
								+ "§7[§c!§7] Sem permissao!");
					}
				} else if (tag.equalsIgnoreCase("normal")) {
					setarTag("normal", p, true);
				} else {
					p.sendMessage("§6A tag §c" + tag.toUpperCase()
							+ " §6não existe!");
				}
			}
		}
		return false;
	}

	public static void setarTag(String tag, Player de, Boolean bo) {
		if (tag == "dono") {
			cortag = dono;

		} else if (tag == "admin") {
			cortag = admin;

		} else if (tag == "mod") {
			cortag = mod;

		} else if (tag == "builder") {
			cortag = builder;

		} else if (tag == "trial") {
			cortag = trial;

		} else if ((tag == "youtuber") || (tag == "yt")) {
			cortag = yt;

		} else if (tag == "tvip") {
			cortag = tvip;

		} else if (tag == "pro") {
			cortag = pro;

		} else if (tag == "vip") {
			cortag = vip;

		} else if (tag == "normal") {
			cortag = normal;
		}
		if (de.getName().length() <= 14) {
			de.setDisplayName(cortag + de.getName());
			de.setPlayerListName(cortag + de.getName());

		} else {
			de.setDisplayName(cortag + de.getName());
			de.setPlayerListName(cortag + de.getName().substring(0, 14));
		}
		TagAPI.refreshPlayer(de);
		if (bo) {
			de.sendMessage(ChatColor.GRAY + "Agora você está usando a tag "
					+ cortag + tag.toUpperCase());
		}
	}

	@EventHandler
	public void onNameTag(AsyncPlayerReceiveNameTagEvent e) {
		e.setTag(e.getNamedPlayer().getDisplayName());
	}
}