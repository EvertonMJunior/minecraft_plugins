package me.everton.WocPvP.Listeners;

import me.everton.WocPvP.Comandos.Tag;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class TagLogin implements Listener {
	public static String dono = Tag.dono;
	public static String admin = Tag.admin;
	public static String mod = Tag.mod;
	public static String builder = Tag.builder;
	public static String trial = Tag.trial;
	public static String yt = Tag.yt;
	public static String tvip = Tag.tvip;
	public static String pro = Tag.pro;
	public static String vip = Tag.vip;
	public static String normal = Tag.normal;

	// Coloca a tag automaticamente ao logar
	@EventHandler
	public void TagAoLogar(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		if (p.getName().length() <= 14) {
			if (p.hasPermission("*")) {

				p.setDisplayName(dono + p.getName());
				p.setPlayerListName(dono + p.getName());

			} else if (p.hasPermission("tag.dono")) {

				p.setDisplayName(dono + p.getName());
				p.setPlayerListName(dono + p.getName());

			} else if (p.hasPermission("tag.admin")) {

				p.setDisplayName(admin + p.getName());

				p.setPlayerListName(admin + p.getName());

			} else if (p.hasPermission("tag.mod")) {

				p.setDisplayName(mod + p.getName());
				p.setPlayerListName(mod + p.getName());

			} else if (p.hasPermission("tag.builder")) {

				p.setDisplayName(builder + p.getName());
				p.setPlayerListName(builder + p.getName());

			} else if (p.hasPermission("tag.trial")) {

				p.setDisplayName(trial + p.getName());
				p.setPlayerListName(trial + p.getName());

			} else if (p.hasPermission("tag.youtuber")) {

				p.setDisplayName(yt + p.getName());
				p.setPlayerListName(yt + p.getName());

			} else if (p.hasPermission("tag.tvip")) {

				p.setDisplayName(tvip + p.getName());
				p.setPlayerListName(tvip + p.getName());

			} else if (p.hasPermission("tag.pro")) {

				p.setDisplayName(pro + p.getName());
				p.setPlayerListName(pro + p.getName());

			} else if (p.hasPermission("tag.vip")) {

				p.setDisplayName(vip + p.getName());
				p.setPlayerListName(vip + p.getName());

			} else {

				p.setDisplayName(normal + p.getName());
				p.setPlayerListName(normal + p.getName());

			}
		} else {
			if (p.hasPermission("*")) {

				p.setDisplayName(dono + p.getName());
				p.setPlayerListName(dono + p.getName().substring(0, 14));

			} else if (p.hasPermission("tag.dono")) {

				p.setDisplayName(dono + p.getName());
				p.setPlayerListName(dono + p.getName().substring(0, 14));

			} else if (p.hasPermission("tag.admin")) {

				p.setDisplayName(admin + p.getName());
				p.setPlayerListName(admin + p.getName().substring(0, 14));

			} else if (p.hasPermission("tag.mod")) {

				p.setDisplayName(mod + p.getName());
				p.setPlayerListName(mod + p.getName().substring(0, 14));

			} else if (p.hasPermission("tag.builder")) {

				p.setDisplayName(builder + p.getName());
				p.setPlayerListName(builder + p.getName().substring(0, 14));

			} else if (p.hasPermission("tag.trial")) {

				p.setDisplayName(trial + p.getName());
				p.setPlayerListName(trial + p.getName().substring(0, 14));

			} else if (p.hasPermission("tag.youtuber")) {

				p.setDisplayName(yt + p.getName());
				p.setPlayerListName(yt + p.getName().substring(0, 14));

			} else if (p.hasPermission("tag.tvip")) {

				p.setDisplayName(tvip + p.getName());
				p.setPlayerListName(tvip + p.getName().substring(0, 14));

			} else if (p.hasPermission("tag.pro")) {

				p.setDisplayName(pro + p.getName());
				p.setPlayerListName(pro + p.getName().substring(0, 14));

			} else if (p.hasPermission("tag.vip")) {

				p.setDisplayName(vip + p.getName());
				p.setPlayerListName(vip + p.getName().substring(0, 14));

			} else {

				p.setDisplayName(normal + p.getName());
				p.setPlayerListName(normal + p.getName().substring(0, 14));

			}
		}

	}

	@EventHandler
	public void onNameTag(AsyncPlayerReceiveNameTagEvent e) {
		e.setTag(e.getNamedPlayer().getDisplayName());
	}

}
