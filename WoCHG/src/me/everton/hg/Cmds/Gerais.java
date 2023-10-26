package me.everton.hg.Cmds;

import java.util.ArrayList;

import me.everton.hg.API;
import me.everton.hg.Main;
import me.everton.hg.SQL;
import me.everton.hg.jogo.ArenaFinal;
import me.everton.hg.jogo.Feast;
import me.everton.hg.jogo.Timers.TimerInvencibilidade;
import me.everton.hg.jogo.Timers.TimerJogo;
import me.everton.hg.jogo.Timers.TimerPreJogo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Gerais implements CommandExecutor {
	public static ArrayList<String> specson = new ArrayList<>(),
			reportCd = new ArrayList<>();

	public static boolean isInt(String s) {
		for (int a = 0; a < s.length(); a++) {
			if (a == 0 && s.charAt(a) == '-')
				continue;
			if (!Character.isDigit(s.charAt(a)))
				return false;
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game!");
			return true;
		}
		final Player p = (Player) sender;
		if (label.equalsIgnoreCase("specs")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Comando ingame!");
				return true;
			}
			if (!p.hasPermission("hg.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					if (!specson.contains(p.getName())) {
						specson.add(p.getName());
						p.sendMessage("§dAgora Você esta vendo espectadores!");
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
						for (String adms : Admin.admins) {
							Player adm = Bukkit.getPlayerExact(adms);
							if(adm == null) {
								return true;
							}
							p.showPlayer(adm);
						}
					} else {
						p.sendMessage("§cVocê ja esta vendo espectadores!");
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
					}
				} else if (args[0].equalsIgnoreCase("off")) {
					if (specson.contains(p.getName())) {
						specson.remove(p.getName());
						p.sendMessage("§cAgora Você nao esta vendo espectadores!");
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
						for (String adms : Admin.admins) {
							Player adm = Bukkit.getPlayerExact(adms);
							if(adm == null) {
								return true;
							}
							p.hidePlayer(adm);
						}
					} else {
						p.sendMessage("§cVocê ja nao esta vendo espectadores!");
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
					}
				}
			} else {
				p.sendMessage("§cUse /specs [on/off]");
			}
		} else if ((((label.equalsIgnoreCase("info"))) || (label
				.equalsIgnoreCase("help"))) || (label.equalsIgnoreCase("jogo"))) {
			if (Main.EM_JOGO) {
				sender.sendMessage("§4➤ §lInformaçoes do jogo");
				if(Main.INVENCIBILIDADE) {
					sender.sendMessage("    §b• §4Estágio do jogo: §c§lInvencibilidade");
				} else {
					sender.sendMessage("    §b• §4Estágio do jogo: §c§lEm jogo");
				}
				sender.sendMessage("    §b• §4Kills: §c§l" + Main.kills.get(p.getName()));
				sender.sendMessage("    §b• §4Kit: §c§l" + ChatColor.stripColor(Main.kit.get(p.getName()).getName()));
				sender.sendMessage("    §b• §4Tempo de jogo: §c§l" + Main.secToMinSec(Main.TEMPO_JOGO));
				if(Feast.feast != null) {
					if(Main.TEMPO_JOGO >= 900) {
						sender.sendMessage("    §b• §4Feast: §c§lJá spawnou em X: " + Feast.feast.getX() + " Z: " + Feast.feast.getZ());
					} else {
						sender.sendMessage("    §b• §4Feast: §c§lVai spawnar em " + (Main.secToMinSec(900 - Main.TEMPO_JOGO)) + " em X: " + Feast.feast.getX() + " Z: " + Feast.feast.getZ());
					}
				}
			} else {
				sender.sendMessage("§7[§c!§7] O jogo ainda nao iniciou!");
				if (sender instanceof Player) {
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				}
			}
		} else if (label.equalsIgnoreCase("tempo")) {
			if (!sender.hasPermission("hg.admin")) {
				sender.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				if (sender instanceof Player) {
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				}
				return true;
			}
			
			if(args.length != 1) {
				p.sendMessage("§cUse /tempo <tempo em segundos>");
				return true;
			}

			if (!isInt(args[0])) {
				sender.sendMessage("§7[§c!§7] Use números!");
				if (sender instanceof Player) {
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				}
				return true;
			}

			if (args[0].length() > 9) {
				sender.sendMessage("§7[§c!§7] Máximo de 9 digitos!");
				if (sender instanceof Player) {
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				}
				return true;
			}

			if (args.length == 1) {
				if (Main.PRE_JOGO) {
					Main.TEMPO_PREJOGO = Integer.valueOf(args[0]);
				} else if (Main.INVENCIBILIDADE) {
					Main.TEMPO_INVENCIBILIDADE = Integer.valueOf(args[0]);
				} else if (Main.EM_JOGO) {
					Main.TEMPO_JOGO = Integer.valueOf(args[0]);
				}

				sender.sendMessage("§7[§c!§7] Tempo alterado para "
						+ Main.secToMinSec(Integer.valueOf(args[0])));
				if (sender instanceof Player) {
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
				}
			} else {
				sender.sendMessage("§7[§c!§7] Use /tempo <tempo em segundos>");
				if (sender instanceof Player) {
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				}
			}
		} else if (label.equalsIgnoreCase("start")) {
			if (!sender.hasPermission("hg.admin")) {
				sender.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				if (sender instanceof Player) {
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				}
				return true;
			}
			if (Main.PRE_JOGO) {
				API.startPartida();
				TimerInvencibilidade.contagem();
				TimerJogo.contagem();
				TimerPreJogo.cancel();
			} else if (Main.INVENCIBILIDADE) {
				TimerInvencibilidade.cancel();
				Main.broadCast("§7[§c!§7] A invencibilidade acabou! Que vença o melhor!");
				for (Player on : Bukkit.getOnlinePlayers()) {
					on.playSound(on.getLocation(), Sound.ANVIL_LAND, 2F, 2F);
				}
				Main.INVENCIBILIDADE = false;
				Main.PRE_JOGO = false;
				Main.EM_JOGO = true;
			} else {
				sender.sendMessage("§7[§c!§7] O jogo já iniciou!");
				if (sender instanceof Player) {
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				}
			}
		} else if (label.equalsIgnoreCase("arena")) {
			if (!sender.hasPermission("hg.admin")) {
				sender.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				if (sender instanceof Player) {
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				}
				return true;
			}
			ArenaFinal.spawnar(p);
			ArenaFinal.teleportar(p);
			sender.sendMessage("§7[§c!§7] Arena Spawnada!");
			if (sender instanceof Player) {
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
			}
		} else if (label.equalsIgnoreCase("togglekits")) {
			if (!sender.hasPermission("hg.admin")) {
				sender.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				if (sender instanceof Player) {
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				}
				return true;
			}

			if (Main.STATE_KITS) {
				Main.STATE_KITS = false;
				Main.broadCast("§7[§c!§7] Os kits foram desativados!");
			} else {
				Main.broadCast("§7[§c!§7] Os kits foram ativos!");
				Main.STATE_KITS = true;
			}
		} else if (label.equalsIgnoreCase("report")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Comando in-game!");
				return true;
			}
			if (args.length >= 2) {
				if (reportCd.contains(p.getName())) {
					p.sendMessage("§cAguarde para enviar outro report!");
					return true;
				}

				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String motivo = sb.toString().trim();

				SQL.putReport(args[0], motivo, p.getName());
				reportCd.add(p.getName());
				p.sendMessage("§aReport enviado! Aguarde 15 segundos para enviar o próximo!");

				Main.sh.scheduleSyncDelayedTask(Main.getPlugin(),
						new Runnable() {

							@Override
							public void run() {
								reportCd.remove(p.getName());
							}
						}, 15 * 20L);
			} else {
				p.sendMessage("§cUse: /report <jogador> <motivo>");
			}
		} else if(label.equalsIgnoreCase("tpall")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Comando in-game!");
				return true;
			}
			if(!p.hasPermission("hg.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				return true;
			}
			
			for(Player on : Bukkit.getOnlinePlayers()) {
				on.teleport(p.getLocation());
				on.sendMessage("§7[§c!§7] Todos foram teleportados para este local!");
			}
		} else if(label.equalsIgnoreCase("skit")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Comando in-game!");
				return true;
			}
			if(!p.hasPermission("hg.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				return true;
			}
			if(args.length < 1 || args.length > 1) {
				p.sendMessage("§cUse /skit <sim ou nao para desativar ou ativar habilidades>");
			} else {
				if(args[0].equalsIgnoreCase("sim")) {
					for(Player on : Bukkit.getOnlinePlayers()) {
						Main.STATE_KITS = false;
						on.sendMessage("§7[§c!§7] Todos os kits foram desativados!");
						on.getInventory().setContents(p.getInventory().getContents());
						on.getInventory().setArmorContents(p.getInventory().getArmorContents());
						for(PotionEffect pot : p.getActivePotionEffects()) {
							on.addPotionEffect(pot);
						}
					}
				} else if(args[0].equalsIgnoreCase("nao")) {
					for(Player on : Bukkit.getOnlinePlayers()) {
						on.getInventory().setContents(p.getInventory().getContents());
						on.getInventory().setArmorContents(p.getInventory().getArmorContents());
						for(PotionEffect pot : p.getActivePotionEffects()) {
							on.addPotionEffect(pot);
						}
					}
				} else {
					p.sendMessage("§cUse /skit <sim ou nao para desativar ou ativar habilidades>");
				}
			}
		} else if(label.equalsIgnoreCase("tp")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Comando in-game!");
				return true;
			}
			if(!p.hasPermission("hg.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				return true;
			}
			if(args.length == 1) {
				if(Bukkit.getPlayerExact(args[0]) == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}
				Player p1 = Bukkit.getPlayerExact(args[0]);
				
				p.teleport(p1.getLocation());
				for(Player on : Bukkit.getOnlinePlayers()) {
					if(on.hasPermission("hg.admin")) {
						on.sendMessage("§7[§c" + p.getName() + " §7se teleportou até §c" + p1.getName() + "§7!]");
					}
				}
			} else if(args.length == 2) {
				if(Bukkit.getPlayerExact(args[0]) == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}
				
				if(Bukkit.getPlayerExact(args[1]) == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}
				Player p1 = Bukkit.getPlayerExact(args[0]);
				Player p2 = Bukkit.getPlayerExact(args[1]);
				
				p1.teleport(p2.getLocation());
				for(Player on : Bukkit.getOnlinePlayers()) {
					if(on.hasPermission("hg.admin")) {
						if(p1.getName() != p.getName()) {
							on.sendMessage("§7[§c" + p1.getName() + " §7foi teleportado até §c" + p2.getName() + "§7 por §c" + p.getName() + "§7!]");
						} else {
							on.sendMessage("§7[§c" + p1.getName() + " §7se teleportou até §c" + p2.getName() + "§7!]");
						}
					}
				}
			} else if(args.length == 3) {
				try {
					p.teleport(new Location(p.getWorld(), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2])));
					for(Player on : Bukkit.getOnlinePlayers()) {
						if(on.hasPermission("hg.admin")) {
							on.sendMessage("§7[§c" + p.getName() + " §7se teleportou até §cX:" + args[0] + ", Y:" + args[1] + ", Z:" + args[2] + " §7!]");
						}
					}
				} catch(Exception e) {
					p.sendMessage("§cLocalizaçao inválida!");
				}
			} else if(args.length == 4) {
				if(Bukkit.getPlayerExact(args[0]) == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}
				Player p1 = Bukkit.getPlayerExact(args[0]);
				
				try {
					p1.teleport(new Location(p.getWorld(), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3])));
					for(Player on : Bukkit.getOnlinePlayers()) {
						if(on.hasPermission("hg.admin")) {
							if(p1.getName() != p.getName()) {
								on.sendMessage("§7[§c" + p1.getName() + " §7foi teleportado até §cX:" + args[1] + ", Y:" + args[2] + ", Z:" + args[3] + " §7 por §c" + p.getName() + "§7!]");
							} else {
								on.sendMessage("§7[§c" + p.getName() + " §7se teleportou até §cX:" + args[1] + ", Y:" + args[2] + ", Z:" + args[3] + " §7!]");
							}
						}
					}
				} catch(Exception e) {
					p.sendMessage("§cLocalizaçao inválida!");
				}
			}
		} else if(label.equalsIgnoreCase("cc")) {
			if(!p.hasPermission("hg.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				return true;
			}
			
			for(int i = 0; i < 30; i++) {
				for(Player on : Bukkit.getOnlinePlayers()) {
					on.sendMessage("  ");
				}
			}
			
			for(Player on : Bukkit.getOnlinePlayers()) {
				on.sendMessage("          §9O §lchat §9foi limpo!");
				on.sendMessage(" ");
				on.sendMessage(" ");
			}
		} else if(label.equalsIgnoreCase("ffeast")) {
			if(!p.hasPermission("hg.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				return true;
			}
			
			if(args.length != 1) {
				p.sendMessage("§cUse /ffeast sim");
			} else if(args.length == 1) {
				if(Feast.feast != null) {
					Feast.spawnChests();
					p.sendMessage("Baus Spawnados");
					return true;
				}
				
				Feast.spawnFeast();
				p.sendMessage("§7[§c!§7] O §c§lfeast foi spawnado!");
			}
		} else if(label.equalsIgnoreCase("tpfeast")) {
			if(!p.hasPermission("hg.admin")) {
				p.sendMessage("§7[§c!§7] Sem permissoes suficientes!");
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
				return true;
			}
			
			Feast.teleport(p);
		} else if(label.equalsIgnoreCase("feast")) {
			if(Feast.feast == null) {
				p.sendMessage("§7[§c!§7] O §c§lfeast §7ainda nao spawnou!");
				return true;
			}
			p.setCompassTarget(Feast.feast);
			API.checkWin();
			p.sendMessage("§7Bússola apontando para o §c§lFeast§7!");
		}
		return false;
	}
}
