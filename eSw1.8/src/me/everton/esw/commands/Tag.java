package me.everton.esw.commands;

import me.everton.esw.API;
import me.everton.esw.PrefixManager;
import me.everton.esw.TagType;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import ca.wacos.nametagedit.NametagAPI;

public class Tag implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cComando In-Game!");
			return true;
		}
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("tag")) {
			if (args.length == 0) {
				p.sendMessage("§b-------- §l+ §b--------");

				if (p.hasPermission("tag.dono") || p.hasPermission("*")) {
					p.sendMessage("§f- "
							+ TagType.DONO.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.ADMIN.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.CODER.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.MODPLUS.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.MOD.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.PRO.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.VIP.toString().replace("&", "§"));
				} else if (p.hasPermission("tag.admin")) {
					p.sendMessage("§f- "
							+ TagType.ADMIN.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.CODER.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.MODPLUS.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.MOD.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.PRO.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.VIP.toString().replace("&", "§"));
				} else if (p.hasPermission("tag.coder")) {
					p.sendMessage("§f- "
							+ TagType.CODER.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.MODPLUS.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.MOD.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.PRO.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.VIP.toString().replace("&", "§"));
				} else if (p.hasPermission("tag.mod+")) {
					p.sendMessage("§f- "
							+ TagType.MODPLUS.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.MOD.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.PRO.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.VIP.toString().replace("&", "§"));
				} else if (p.hasPermission("tag.mod")) {
					p.sendMessage("§f- "
							+ TagType.MOD.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.PRO.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.VIP.toString().replace("&", "§"));
				} else if (p.hasPermission("tag.builder")) {
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.PRO.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.VIP.toString().replace("&", "§"));
				} else if (p.hasPermission("tag.trial")) {
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.PRO.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.VIP.toString().replace("&", "§"));
				} else if (p.hasPermission("tag.pro")) {
					p.sendMessage("§f- "
							+ TagType.PRO.toString().replace("&", "§"));
					p.sendMessage("§f- "
							+ TagType.VIP.toString().replace("&", "§"));
				} else if (p.hasPermission("tag.vip")) {
					p.sendMessage("§f- "
							+ TagType.VIP.toString().replace("&", "§"));
				}

				p.sendMessage("§f- §7NORMAL");
				p.sendMessage("§b-------- §l+ §b--------");
			} else if (args.length == 1) {
				switch (args[0].toLowerCase()) {
				case "dono":
					if (!p.hasPermission("tag.dono")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.DONO);
					break;
				case "admin":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.ADMIN);
					break;
				case "coder":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.CODER);
					break;
				case "mod+":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.MODPLUS);
					break;
				case "modplus":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.MODPLUS);
					break;
				case "mod":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")
							&& !p.hasPermission("tag.mod")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.MOD);
					break;
				case "builder":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")
							&& !p.hasPermission("tag.mod")
							&& !p.hasPermission("tag.builder")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.BUILDER);
					break;
				case "trial":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")
							&& !p.hasPermission("tag.mod")
							&& !p.hasPermission("tag.builder")
							&& !p.hasPermission("tag.trial")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.TRIAL);
					break;
				case "pro":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")
							&& !p.hasPermission("tag.mod")
							&& !p.hasPermission("tag.builder")
							&& !p.hasPermission("tag.trial")
							&& !p.hasPermission("tag.pro")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.PRO);
					break;
				case "vip":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")
							&& !p.hasPermission("tag.mod")
							&& !p.hasPermission("tag.builder")
							&& !p.hasPermission("tag.trial")
							&& !p.hasPermission("tag.pro")
							&& !p.hasPermission("tag.vip")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.VIP);
					break;
				case "normal":
					useTag(p, TagType.NORMAL);
					break;

				default:
					p.sendMessage("§7[§c!§7] Tag inexistente!");
					break;
				}
			}
		}
		return false;
	}

	public static void useTag(Player p, TagType tag) {
		if (tag == TagType.NORMAL) {
			p.setDisplayName("§7" + p.getName());
			p.setPlayerListName("§7" + p.getName());
			NametagAPI.setPrefix(p.getName(), "§7");
			API.sendActionBar(p, "§4Agora usando a tag §7§lNORMAL§4!");
		} else {
			p.setDisplayName(tag.toString() + " " + tag.toString().replace("&l", "").replace(tag.name(), "") + p.getName());
			p.setPlayerListName(tag.toString() + " " + tag.toString().replace("&l", "").replace(tag.name(), "") + p.getName());
			NametagAPI.setPrefix(p.getName(), tag.toString() + " " + tag.toString().replace("&l", "").replace(tag.name(), ""));
			API.sendActionBar(p, "§4Agora usando a tag "
					+ tag.toString().replace("&", "§") + "§4!");
		}
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
	}

	public static void useTag(Player p, TagType tag, boolean msg) {
		if (tag == TagType.NORMAL) {
			p.setDisplayName("§7" + p.getName());
			p.setPlayerListName("§7" + p.getName());
			NametagAPI.setPrefix(p.getName(), "§7");
			if(msg) {
				API.sendActionBar(p, "§4Agora usando a tag §7§lNORMAL§4!");
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
			}
		} else {
			p.setDisplayName(tag.toString().replace("&", "§") + " §f"
					+ p.getName());
			p.setPlayerListName(tag.toString().replace("&", "§") + " §f"
					+ p.getName());
			NametagAPI.setPrefix(p.getName(), tag.toString() + " " + tag.toString().replace("&l", "").replace(tag.name(), ""));
			if (msg) {
				API.sendActionBar(p, "§4Agora usando a tag "
						+ tag.toString().replace("&", "§") + "§4!");
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
			}
		}
	}

	public static void setTag(Player p) {
		if (p.hasPermission("tag.dono") || p.hasPermission("*")) {
			useTag(p, TagType.DONO, false);
		} else if (p.hasPermission("tag.admin")) {
			useTag(p, TagType.ADMIN, false);
		} else if (p.hasPermission("tag.coder")) {
			useTag(p, TagType.CODER, false);
		} else if (p.hasPermission("tag.mod+")) {
			useTag(p, TagType.MODPLUS, false);
		} else if (p.hasPermission("tag.mod")) {
			useTag(p, TagType.MOD, false);
		} else if (p.hasPermission("tag.builder")) {
			useTag(p, TagType.BUILDER, false);
		} else if (p.hasPermission("tag.trial")) {
			useTag(p, TagType.TRIAL, false);
		} else if (p.hasPermission("tag.pro")) {
			useTag(p, TagType.PRO, false);
		} else if (p.hasPermission("tag.vip")) {
			useTag(p, TagType.VIP, false);
		} else {
			useTag(p, TagType.NORMAL, false);
		}
	}
}
