package me.everton.pvp.tags;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import me.everton.pvp.API;
import me.everton.pvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayServerScoreboardTeam;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class TagCmd implements CommandExecutor {
	public static HashMap<String, String> prefixes = new HashMap<>();

	public static void setTag(Player p, TagType tag) {
		if (tag == TagType.NORMAL) {
			setPrefix(p, "§7");
			p.setDisplayName("§7" + p.getName() + "§r");
		} else {
			p.setDisplayName(tag.getPrefix() + p.getName() + "§r");
			setPrefix(p, tag.getPrefix());
		}
		String listName = tag.getColor().replace("§l", "") + p.getName();
		if (listName.length() > 16) {
			p.setPlayerListName(listName.substring(0, 15));
		} else {
			p.setPlayerListName(listName);
		}
	}

	public static void setTag(Player p) {
		TagType tag = getHighestRank(p);
		if (tag == TagType.NORMAL) {
			setPrefix(p, "§7");
			p.setDisplayName("§7" + p.getName() + "§r");
		} else {
			p.setDisplayName(tag.getPrefix() + p.getName() + "§r");
			setPrefix(p, tag.getPrefix());
		}
		String listName = tag.getColor() + p.getName();
		if (listName.length() > 16) {
			p.setPlayerListName(listName.substring(0, 15));
		} else {
			p.setPlayerListName(listName);
		}
	}

	public static TagType tagFromString(String tag) {
		try {
			return TagType.valueOf(tag.toUpperCase());
		} catch (Exception e) {
			for (TagType t : TagType.values()) {
				if (t.getTag().equalsIgnoreCase(tag)) {
					return t;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static void setPrefix(Player p, String prefix) {
		PacketContainer pa = Main.getPM().createPacket(
				PacketType.Play.Server.SCOREBOARD_TEAM);
		WrapperPlayServerScoreboardTeam wr = new WrapperPlayServerScoreboardTeam(
				pa);
		String pr = prefix;
		if (pr.length() > 16) {
			pr = prefix.substring(0, 15);
		}
		wr.setTeamDisplayName(pr);
		wr.setTeamPrefix(pr);
		wr.setTeamName(UUID.randomUUID().toString().substring(0, 15));
		ArrayList<String> al = new ArrayList<>();
		al.add(p.getName());
		wr.setPlayers(al);
		prefixes.put(p.getName(), prefix);

		for (Player on : Bukkit.getOnlinePlayers()) {
			try {
				Main.getPM().sendServerPacket(on, pa);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void updatePrefixes(Player pToU) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!prefixes.containsKey(p.getName())) {
				return;
			}
			PacketContainer pa = Main.getPM().createPacket(
					PacketType.Play.Server.SCOREBOARD_TEAM);
			WrapperPlayServerScoreboardTeam wr = new WrapperPlayServerScoreboardTeam(
					pa);
			String pr = prefixes.get(p.getName());
			if (pr.length() > 16) {
				pr.substring(0, 15);
			}
			wr.setTeamDisplayName(pr);
			wr.setTeamPrefix(pr);
			wr.setTeamName(UUID.randomUUID().toString().substring(0, 15));
			ArrayList<String> al = new ArrayList<>();
			al.add(p.getName());
			wr.setPlayers(al);

			try {
				Main.getPM().sendServerPacket(pToU, pa);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean hasPermission(Player p, TagType tag) {
		if (tag == TagType.NORMAL) {
			return true;
		}
		if (p.hasPermission("tag." + tag.name().toLowerCase())) {
			return true;
		} else {
			if (tag.ordinal() == 0) {
				if (p.hasPermission("tag." + tag.name().toLowerCase())) {
					return true;
				}
			} else {
				for (TagType t : TagType.values()) {
					if (t.ordinal() < tag.ordinal()) {
						if (p.hasPermission("tag." + t.name().toLowerCase())) {
							return true;
						}
					}
				}
			}
			return false;
		}
	}

	public static TagType getHighestRank(Player p) {
		for (TagType tag : TagType.values()) {
			if (p.hasPermission("tag." + tag.name().toLowerCase())) {
				return tag;
			}
		}

		return TagType.NORMAL;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cComando in-game!");
			return true;
		}
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("tag")) {
			if (args.length != 1) {
				p.sendMessage(" ");
				p.sendMessage("§e§m----(-------(§6TAGS§e§m)-------)----");
				for (TagType tag : TagType.values()) {
					if (hasPermission(p, tag)) {
						p.sendMessage("- " + tag.getPrefix());
					}
				}
				p.sendMessage("§e§m----(-------(§6TAGS§e§m)-------)----");
				p.sendMessage(" ");
			} else {
				TagType tag = tagFromString(args[0]);
				if (tag == null) {
					p.sendMessage("§cA tag §4“" + args[0].toUpperCase()
							+ "”§c não existe!");
					return true;
				}
				if (hasPermission(p, tag)) {
					setTag(p, tag);
					API.sendActionBar(p,
							"§6§m---§r  §4Tag escolhida: " + tag.getPrefix()
									+ " §6§m---");
				} else {
					p.sendMessage("§7[§c!§7] Você não possui esta §c§lTag§7!");
				}
			}
		}
		return false;
	}

}
