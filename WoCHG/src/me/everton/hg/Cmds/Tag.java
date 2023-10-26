package me.everton.hg.Cmds;

import me.everton.hg.API;
import me.everton.hg.TagType;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

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
							+ TagType.DONO.toString()
							+ TagType.DONO.name());
					p.sendMessage("§f- "
							+ TagType.ADMIN.toString()
							+ TagType.ADMIN.name());
					p.sendMessage("§f- "
							+ TagType.CODER.toString()
							+ TagType.CODER.name());
					p.sendMessage("§f- "
							+ TagType.MODPLUS.toString()
							+ TagType.MODPLUS.name());
					p.sendMessage("§f- "
							+ TagType.MOD.toString()
							+ TagType.MOD.name());
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString()
							+ TagType.BUILDER.name());
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString()
							+ TagType.TRIAL.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBERPLUS.toString()
							+ TagType.YOUTUBERPLUS.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBER.toString()
							+ TagType.YOUTUBER.name());
					p.sendMessage("§f- "
							+ TagType.PRO.toString()
							+ TagType.PRO.name());
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
				} else if (p.hasPermission("tag.admin")) {
					p.sendMessage("§f- "
							+ TagType.ADMIN.toString()
							+ TagType.ADMIN.name());
					p.sendMessage("§f- "
							+ TagType.CODER.toString()
							+ TagType.CODER.name());
					p.sendMessage("§f- "
							+ TagType.MODPLUS.toString()
							+ TagType.MODPLUS.name());
					p.sendMessage("§f- "
							+ TagType.MOD.toString()
							+ TagType.MOD.name());
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString()
							+ TagType.BUILDER.name());
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString()
							+ TagType.TRIAL.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBERPLUS.toString()
							+ TagType.YOUTUBERPLUS.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBER.toString()
							+ TagType.YOUTUBER.name());
					p.sendMessage("§f- "
							+ TagType.PRO.toString()
							+ TagType.PRO.name());
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
				} else if (p.hasPermission("tag.coder")) {
					p.sendMessage("§f- "
							+ TagType.CODER.toString()
							+ TagType.CODER.name());
					p.sendMessage("§f- "
							+ TagType.MODPLUS.toString()
							+ TagType.MODPLUS.name());
					p.sendMessage("§f- "
							+ TagType.MOD.toString()
							+ TagType.MOD.name());
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString()
							+ TagType.BUILDER.name());
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString()
							+ TagType.TRIAL.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBERPLUS.toString()
							+ TagType.YOUTUBERPLUS.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBER.toString()
							+ TagType.YOUTUBER.name());
					p.sendMessage("§f- "
							+ TagType.PRO.toString()
							+ TagType.PRO.name());
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
				} else if (p.hasPermission("tag.mod+")) {
					p.sendMessage("§f- "
							+ TagType.MODPLUS.toString()
							+ TagType.MODPLUS.name());
					p.sendMessage("§f- "
							+ TagType.MOD.toString()
							+ TagType.MOD.name());
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString()
							+ TagType.BUILDER.name());
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString()
							+ TagType.TRIAL.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBERPLUS.toString()
							+ TagType.YOUTUBERPLUS.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBER.toString()
							+ TagType.YOUTUBER.name());
					p.sendMessage("§f- "
							+ TagType.PRO.toString()
							+ TagType.PRO.name());
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
				} else if (p.hasPermission("tag.mod")) {
					p.sendMessage("§f- "
							+ TagType.MOD.toString()
							+ TagType.MOD.name());
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString()
							+ TagType.BUILDER.name());
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString()
							+ TagType.TRIAL.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBERPLUS.toString()
							+ TagType.YOUTUBERPLUS.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBER.toString()
							+ TagType.YOUTUBER.name());
					p.sendMessage("§f- "
							+ TagType.PRO.toString()
							+ TagType.PRO.name());
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
				} else if (p.hasPermission("tag.builder")) {
					p.sendMessage("§f- "
							+ TagType.BUILDER.toString()
							+ TagType.BUILDER.name());
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString()
							+ TagType.TRIAL.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBERPLUS.toString()
							+ TagType.YOUTUBERPLUS.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBER.toString()
							+ TagType.YOUTUBER.name());
					p.sendMessage("§f- "
							+ TagType.PRO.toString()
							+ TagType.PRO.name());
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
				} else if (p.hasPermission("tag.trial")) {
					p.sendMessage("§f- "
							+ TagType.TRIAL.toString()
							+ TagType.TRIAL.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBERPLUS.toString()
							+ TagType.YOUTUBERPLUS.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBER.toString()
							+ TagType.YOUTUBER.name());
					p.sendMessage("§f- "
							+ TagType.PRO.toString()
							+ TagType.PRO.name());
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
				} else if (p.hasPermission("tag.yt+")) {
					p.sendMessage("§f- "
							+ TagType.YOUTUBERPLUS.toString()
							+ TagType.YOUTUBERPLUS.name());
					p.sendMessage("§f- "
							+ TagType.YOUTUBER.toString()
							+ TagType.YOUTUBER.name());
					p.sendMessage("§f- "
							+ TagType.PRO.toString()
							+ TagType.PRO.name());
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
				} else if (p.hasPermission("tag.youtuber")) {
					p.sendMessage("§f- "
							+ TagType.YOUTUBER.toString()
							+ TagType.YOUTUBER.name());
					p.sendMessage("§f- "
							+ TagType.PRO.toString()
							+ TagType.PRO.name());
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
				} else if (p.hasPermission("tag.pro")) {
					p.sendMessage("§f- "
							+ TagType.PRO.toString()
							+ TagType.PRO.name());
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
				} else if (p.hasPermission("tag.vip")) {
					p.sendMessage("§f- "
							+ TagType.VIP.toString()
							+ TagType.VIP.name());
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
				case "yt":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")
							&& !p.hasPermission("tag.mod")
							&& !p.hasPermission("tag.builder")
							&& !p.hasPermission("tag.trial")
							&& !p.hasPermission("tag.youtuber")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.YOUTUBER);
					break;
				case "youtuber":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")
							&& !p.hasPermission("tag.mod")
							&& !p.hasPermission("tag.builder")
							&& !p.hasPermission("tag.trial")
							&& !p.hasPermission("tag.youtuber")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.YOUTUBER);
					break;
				case "youtuberplus":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")
							&& !p.hasPermission("tag.mod")
							&& !p.hasPermission("tag.builder")
							&& !p.hasPermission("tag.trial")
							&& !p.hasPermission("tag.youtuberplus")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.YOUTUBERPLUS);
					break;
				case "yt+":
					if (!p.hasPermission("tag.dono")
							&& !p.hasPermission("tag.admin")
							&& !p.hasPermission("tag.coder")
							&& !p.hasPermission("tag.mod+")
							&& !p.hasPermission("tag.mod")
							&& !p.hasPermission("tag.builder")
							&& !p.hasPermission("tag.trial")
							&& !p.hasPermission("tag.youtuberplus")) {
						p.sendMessage("§7[§c!§7] Sem permissao!");
						return true;
					}
					useTag(p, TagType.YOUTUBERPLUS);
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
			API.sendActionBar(p, "§7[§c!§7] §4Agora usando a tag §7§lNORMAL§4!");
		} else {
			p.setDisplayName(tag.toString().replace("§l", "") + p.getName());
			String tablistName = tag.toString() + p.getName();
			if (tablistName.length() > 16) {
				p.setPlayerListName(tablistName.substring(0, 16));
			} else {
				p.setPlayerListName(tablistName);
			}
			API.sendActionBar(p, "§7[§c!§7] §4Agora usando a tag "
					+ tag.toString() + tag.name() + "§4!");
		}
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
	}

	public static void useTag(Player p, TagType tag, boolean msg) {
		if (tag == TagType.NORMAL) {
			p.setDisplayName("§7" + p.getName());
			p.setPlayerListName("§7" + p.getName());
			API.sendActionBar(p, "§7[§c!§7] §4Agora usando a tag §7§lNORMAL§4!");
		} else {
			p.setDisplayName(tag.toString().replace("§l", "") + p.getName());
			String tablistName = tag.toString() + p.getName();
			if (tablistName.length() > 16) {
				p.setPlayerListName(tablistName.substring(0, 16));
			} else {
				p.setPlayerListName(tablistName);
			}
			if (msg) {
				API.sendActionBar(p, "§7[§c!§7] §4Agora usando a tag "
						+ tag.toString() + tag.name() + "§4!");
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
		} else if (p.hasPermission("tag.yt+")) {
			useTag(p, TagType.PRO, false);
		} else if (p.hasPermission("tag.youtuber")) {
			useTag(p, TagType.PRO, false);
		} else if (p.hasPermission("tag.pro")) {
			useTag(p, TagType.PRO, false);
		} else if (p.hasPermission("tag.vip")) {
			useTag(p, TagType.VIP, false);
		} else {
			useTag(p, TagType.NORMAL, false);
		}
	}
}
