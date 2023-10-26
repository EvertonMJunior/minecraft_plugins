package me.everton.WocPvP.Comandos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.everton.WocPvP.Main;
import me.everton.WocPvP.MySql;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Uteis implements CommandExecutor {
	public static ArrayList<Player> canFly = new ArrayList<>();
	public static HashMap<Player, String> reports = new HashMap<>();

	public static HashMap<Player, Integer> cd = new HashMap<>();
	public static HashMap<Player, Integer> task = new HashMap<>();
	
	private MySql mysql;
	
	public static void removePexGroups(String p) {
		@SuppressWarnings("deprecation")
		List<PermissionGroup> groups = PermissionsEx.getUser(p).getGroups();
		for (int i = 0; i < groups.size(); i++) {
			PermissionGroup gr = groups.get(i);
			PermissionsEx.getUser(p).removeGroup(gr);
		}
	}

	public static void setGroup(final String gn, final String p) {
		removePexGroups(p);
		PermissionsEx.getUser(p).addGroup(gn.toLowerCase());
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex reload");
		if ((gn.equalsIgnoreCase("trial")) || (gn.equalsIgnoreCase("builder"))
				|| (gn.equalsIgnoreCase("mod"))
				|| (gn.equalsIgnoreCase("admin"))
				|| (gn.equalsIgnoreCase("dono"))) {
			Main.settings.getConfig().set("staff." + gn.toLowerCase(),
					p.toLowerCase());
			Main.settings.saveConfig();
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc Agora §c§o§l"
					+ p + " §3pertence ao rank §c§o§l" + gn.toUpperCase()
					+ "§3!");
		} else {
			Main.settings.getConfig().set("staff." + gn.toLowerCase(), null);
			Main.settings.saveConfig();
		}
		Main.sh.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			@Override
			public void run() {
				if (Bukkit.getPlayerExact(p) != null) {
					Player pf = Bukkit.getPlayerExact(p);
					pf.kickPlayer("§7[§c§lWoCPvP§7] §3§lParabens! \n §3§lAgora pertences ao rank "
							+ gn.toUpperCase());

				}
			}
		}, 20L);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("heal")) {
			if (p.hasPermission("wocpvp.admin")) {
				if (args.length == 0) {
					p.setHealth(20D);
					p.setFoodLevel(20);
					p.sendMessage("§2Você foi curado!");
				} else if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					target.setHealth(20D);
					target.setFoodLevel(20);
					p.sendMessage("§2Você curou " + target.getDisplayName()
							+ "§2!");
					target.sendMessage("§2Você foi curado por "
							+ p.getDisplayName() + "§2!");
				}
			}
		}
		
		if(label.equalsIgnoreCase("limpar")){
			if(p.hasPermission("wocpvp.admin")){
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.sendMessage("§7[§a!§7] §bInventario limpo!");
			}
		}

		if (label.equalsIgnoreCase("setrank")) {
			if (p.isOp()) {
				if ((args.length < 2) || (args.length > 2)) {
					p.sendMessage("§cUse /setrank [player] [rank]");
				} else if (args.length == 2) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (args[1].equalsIgnoreCase("normal")) {
						setGroup("default", args[0]);
						if (target != null) {
							p.chat("/tag " + args[1]);
						}
						p.sendMessage("§a§lAgora " + args[0]
								+ " pertence ao rank " + args[1].toUpperCase());
					} else if (args[1].equalsIgnoreCase("vip")) {
						setGroup(args[1], args[0]);
						if (target != null) {
							p.chat("/tag " + args[1]);
						}
						p.sendMessage("§a§lAgora " + args[0]
								+ " pertence ao rank " + args[1].toUpperCase());
					} else if (args[1].equalsIgnoreCase("pro")) {
						setGroup(args[1], args[0]);
						if (target != null) {
							p.chat("/tag " + args[1]);
						}
						p.sendMessage("§a§lAgora " + args[0]
								+ " pertence ao rank " + args[1].toUpperCase());
					} else if (args[1].equalsIgnoreCase("trial")) {
						setGroup(args[1], args[0]);
						if (target != null) {
							p.chat("/tag " + args[1]);
						}
						p.sendMessage("§a§lAgora " + args[0]
								+ " pertence ao rank " + args[1].toUpperCase());
					} else if (args[1].equalsIgnoreCase("mod")) {
						setGroup(args[1], args[0]);
						if (target != null) {
							p.chat("/tag " + args[1]);
						}
						p.sendMessage("§a§lAgora " + args[0]
								+ " pertence ao rank " + args[1].toUpperCase());
					} else if (args[1].equalsIgnoreCase("builder")) {
						setGroup(args[1], args[0]);
						if (target != null) {
							p.chat("/tag " + args[1]);
						}
						p.sendMessage("§a§lAgora " + args[0]
								+ " pertence ao rank " + args[1].toUpperCase());
					} else if (args[1].equalsIgnoreCase("admin")) {
						setGroup(args[1], args[0]);
						if (target != null) {
							p.chat("/tag " + args[1]);
						}
						p.sendMessage("§a§lAgora " + args[0]
								+ " pertence ao rank " + args[1].toUpperCase());
					} else if (args[1].equalsIgnoreCase("dono")) {
						setGroup(args[1], args[0]);
						if (target != null) {
							p.chat("/tag " + args[1]);
						}
						p.sendMessage("§a§lAgora " + args[0]
								+ " pertence ao rank " + args[1].toUpperCase());
					} else if (args[1].equalsIgnoreCase("yt")) {
						setGroup(args[1], args[0]);
						if (target != null) {
							p.chat("/tag " + args[1]);
						}
						p.sendMessage("§a§lAgora " + args[0]
								+ " pertence ao rank " + args[1].toUpperCase());
					} else {
						p.sendMessage("§6Este grupo nao existe!");
						p.sendMessage("§6Use: §b§l§onormal; vip; pro; yt; trial; mod; builder; admin; dono");
					}
				}
			}
		}

		if (label.equalsIgnoreCase("reportar")) {
			if (args.length < 2) {
				p.sendMessage("§cUse §l/reportar [nome do jogador/bug] [motivo]");
			} else if (args.length >= 2) {
				String nick = null;
				Player target = Bukkit.getPlayerExact(args[0]);
				if ((target == null) || (!(target instanceof Player))) {
					if (!args[0].equalsIgnoreCase("bug")) {
						sender.sendMessage("§cEste jogador nao esta online!");
						return true;
					} else if (args[0].equalsIgnoreCase("bug")) {
						nick = "Um Bug";
					}
				} else {
					nick = target.getName();
				}
				if ((reports.containsKey(p))
						&& (reports.get(p).equalsIgnoreCase(nick))) {
					p.sendMessage("§cAguarde para enviar um report sobre §4§l"
							+ nick + " §cnovamente!");
					return true;
				}
				final Player pf = p;
				int coolDown = Main.sh.scheduleSyncRepeatingTask(Main.plugin,
						new Runnable() {
							int tempo = 180;

							public void run() {
								if (tempo > 0) {
									tempo--;
									cd.put(pf, tempo);
								}
								if (tempo == 0) {
									Main.sh.cancelTask(task.get(pf));
									cd.remove(pf);
									task.remove(pf);
									reports.remove(pf);
								}
							}
						}, 0, 20);
				task.put(p, coolDown);
				reports.put(p, nick);

				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String allArgs = sb.toString().trim();

				for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
					if (staff.hasPermission("wocpvp.admin")) {
						staff.sendMessage("                §f§l******§c§l REPORT DE "
								+ p.getName() + " §f§l******");
						staff.sendMessage(" ");
						staff.sendMessage("§4§l" + nick + " §cfoi reportado!");
						staff.sendMessage("§cMotivo: §4§l" + allArgs);
						staff.sendMessage(" ");
						staff.sendMessage("                §f§l******§c§l REPORT DE "
								+ p.getName() + " §f§l******");
						staff.playSound(staff.getLocation(), Sound.ANVIL_LAND,
								1F, 1F);
						
					}
				}
				mysql.reportar(nick, allArgs, p.getName());
			}
		}

		if (label.equalsIgnoreCase("voar")) {

			if (p.hasPermission("wocpvp.admin")) {
				if (args.length == 0) {
					if (p.getAllowFlight()) {
						p.setAllowFlight(false);
						p.sendMessage("§6Você nao pode voar mais!");
						canFly.remove(p);
					} else if (!p.getAllowFlight()) {
						p.setAllowFlight(true);
						p.sendMessage("§6Agora Você pode voar!");
						canFly.add(p);
					}

				} else if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					if (target.getAllowFlight()) {
						target.setAllowFlight(false);
						p.sendMessage("§6" + target.getName()
								+ " nao pode voar mais!");
						target.sendMessage("§6Você nao pode voar mais!");
						canFly.remove(target);
					} else if (!target.getAllowFlight()) {
						target.setAllowFlight(true);
						p.sendMessage("§6" + target.getName()
								+ " agora pode voar!");
						target.sendMessage("§6Agora Você pode voar!");
						canFly.add(target);
					}

				}
			} else {
				p.sendMessage("§4§7[§c!§7] Sem permissao!!");
			}
		}
		if (label.equalsIgnoreCase("ajuda")) {
			p.sendMessage("§f******************** §cAJUDA WOCPVP §f********************");
			p.sendMessage("§f- §b§l/kit -  Use para escolher um kit.");
			p.sendMessage("§f- §b§l/duvida [duvida] -  Use para duvidas! Apenas com staffers onlines!");
			p.sendMessage("§f- §b§l/fps -  Use para entrar na arena FPS.");
			p.sendMessage("§f- §b§l/youtuber -  Use para saber os requisitos YT.");
			p.sendMessage("§f- §b§l/aplicacao -  Use para ver o formulario para a staff.");
			p.sendMessage("§f- §b§l/loja -  Use para comprar VIPs, Unban ou Doar ao servidor.");
			p.sendMessage("§f- §b§l/tag -  Use trocar de tag.");
			p.sendMessage("§f- §b§l/tell [jogador] [mensagem] -  Use para mandar mensagens privadas a outros jogadores.");
			p.sendMessage("§f- §b§l/r -  Use para responder mensagens privadas.");
			p.sendMessage("§f- §b§l/warps -  Veja as warps e va ate elas.");
			p.sendMessage("§f- §b§l/hg -  Use para ir até o warp Early HG.");
			p.sendMessage("§f- §b§l/sopa -  Use para pegar sopas.");
			if ((p.hasPermission("tag.vip")) || (p.hasPermission("tag.vip+"))) {
				p.sendMessage(" ");
				p.sendMessage("§f- §b§l/spec -  Modo espectador.");
				p.sendMessage("§f- §b§l/evento spec -  Espectar eventos.");
			}
			if (p.hasPermission("wocpvp.admin")) {
				p.sendMessage(" ");
				p.sendMessage("§f-*- §cCOMANDOS STAFF §f-*-");
				p.sendMessage("§f- §b§l/admin -  Use para entrar no modo admin.");
				p.sendMessage("§f- §b§l/vis -  Use para ficar visivel/invisivel.");
				p.sendMessage("§f- §b§l/invis -  Use para ganhar pocao de invisibilidade.");
				p.sendMessage("§f- §b§l/tp [jogador] (jogador) -  Use para teleportar-se até outros jogadores.");
				p.sendMessage("§f- §b§l/bc [mensagem] -  Use para mandar uma mensagem ao servidor.");
				p.sendMessage("§f- §b§l/construir -  Use para ativar/desativar o modo construir.");
				p.sendMessage("§f- §b§l/rd [jogador] [resposta] -  Use para responder duvidas.");
				p.sendMessage("§f- §b§l/fake [nick] (resposta) -  Use para usar nicks fakes.");
				p.sendMessage("§f- §b§l/tfake -  Use para tirar o fake.");
				p.sendMessage("§f- §b§l/fakes -  Use para listar os fakes.");
				p.sendMessage("§f- §b§l/sc (mensagem) -  Use para entrar/sair do chat staff ou para mandar uma mensagem.");
				p.sendMessage("§f- §b§lstaffers para ele.");
				p.sendMessage("§f- §b§l/specs [on/off] -  Use para ligar/desligar espectadores.");
				p.sendMessage("§f- §b§l/trollar [jogador] -  Use para trollar HACKERS.");
				p.sendMessage("§f- §b§l/spec -  Modo espectador.");

			}
			p.sendMessage("§f******************** §cAJUDA WOCPVP §f********************");
		}
		return false;
	}	
}
