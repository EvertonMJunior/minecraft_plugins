package me.everton.pvp.cmds;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Random;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.OptionsManager;
import me.everton.pvp.ScoreManager;
import me.everton.pvp.ScoreboardManager;
import me.everton.pvp.ScoreManager.Money;
import me.everton.pvp.cmds.Warps.Locations;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.tags.TagType;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayServerPlayerInfo;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class Gerais implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if(label.equalsIgnoreCase("savestats") && sender instanceof ConsoleCommandSender) {
			API.broadcastMessage("§7[§c!§7] Guardando dados de jogadores em 5 segundos. Um lag mínimo é esperado.");
			Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				public void run() {
					ScoreManager.storageData();
					API.broadcastMessage("§7[§c!§7] Dados guardados com sucesso!");
				}
			}, 5 * 20L);
		}
		
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("gm")) {
			if (!p.hasPermission("pvp.cmd.gm")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("1")) {
					p.setGameMode(GameMode.CREATIVE);
					p.sendMessage("§7[§a!§7] Gamemode atualizado para §aCriativo");
				} else if (args[0].equalsIgnoreCase("2")) {
					p.setGameMode(GameMode.ADVENTURE);
					p.sendMessage("§7[§a!§7] Gamemode atualizado para §aAventura");
				} else if (args[0].equalsIgnoreCase("0")) {
					p.setGameMode(GameMode.SURVIVAL);
					p.sendMessage("§7[§a!§7] Gamemode atualizado para §aSurvival");
				}
			} else {
				if (p.getGameMode() == GameMode.CREATIVE) {
					p.setGameMode(GameMode.SURVIVAL);
					p.sendMessage("§7[§a!§7] Gamemode atualizado para §aSurvival");
				} else {
					p.setGameMode(GameMode.CREATIVE);
					p.sendMessage("§7[§a!§7] Gamemode atualizado para §aCriativo");
				}
			}
		}

		if (label.equalsIgnoreCase("clearlag")) {
			if (!p.hasPermission("pvp.cmd.clearlag")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			for (World worlds : Bukkit.getWorlds()) {
				String world = worlds.getName();
				for (Iterator<?> iterator = Bukkit.getServer().getWorld(world)
						.getEntities().iterator(); iterator.hasNext();) {
					Entity entity = (Entity) iterator.next();
					if (entity.getType() != EntityType.PLAYER
							&& entity.getType() != EntityType.ITEM_FRAME
							&& entity.getType() != EntityType.MINECART
							&& entity.getType() != EntityType.PAINTING
							&& entity.getType() != EntityType.FISHING_HOOK
							&& entity.getType() != EntityType.VILLAGER) {
						if (entity.getType() == EntityType.DROPPED_ITEM) {
							Item drop = (Item) entity;
							try {
								if (!drop.getItemStack().getItemMeta()
										.getDisplayName()
										.equalsIgnoreCase("noclear")) {
									entity.remove();
								}
							} catch (Exception ex) {
								entity.remove();
							}
						} else {
							entity.remove();
						}
					}
				}
			}

			p.sendMessage("§7[§c!§7] Entidades Removidas!");
		}

		if (label.equalsIgnoreCase("resetkit")) {
			if (!p.hasPermission("pvp.cmd.resetkit")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}

			KitManager.resetKit(p);
			p.sendMessage("§aSeu kit foi resetado!");
			p.playSound(p.getLocation(), Sound.FIRE_IGNITE, 15.5F, 15.5F);
			API.itensIniciais(p);
			p.updateInventory();
			ScoreboardManager.refreshSidebar(p);
		}

		if (label.equalsIgnoreCase("ping")) {
			if (args.length == 0) {
				p.sendMessage("§7Seu ping é de §c§l" + API.getPing(p) + "ms§7!");
			} else if (args.length == 1) {
				if (!p.hasPermission("pvp.cmd.pingothers")) {
					p.sendMessage("§7[§c!§7] Sem permissao!");
					return true;
				}
				Player t = Bukkit.getPlayerExact(args[0]);
				if (t == null) {
					p.sendMessage("§cJogador não encontrado!");
					return true;
				}

				p.sendMessage("§7O ping de §c§l" + t.getName() + " §7é de §c§l"
						+ API.getPing(t) + "ms§7!");
			}
		}

		if (label.equalsIgnoreCase("woclotadao")) {
			for (int i = 0; i < 100; i++) {
				PacketContainer packet = Main.getPM().createPacket(
						PacketType.Play.Server.PLAYER_INFO);
				WrapperPlayServerPlayerInfo wr = new WrapperPlayServerPlayerInfo(
						packet);
				wr.setPlayerName("SteveWoW" + i);
				for (Player on : Bukkit.getOnlinePlayers()) {
					on.sendMessage("§7[§a+§7] "
							+ TagType.values()[new Random().nextInt(TagType
									.values().length)].getPrefix() + "SteveWoW"
							+ i);
					try {
						Main.getPM().sendServerPacket(on, packet);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (label.equalsIgnoreCase("spawn")) {
			Warps.tpCd(p, 5, Locations.SPAWN, "ao");
		}

		if (label.equalsIgnoreCase("tp")) {
			if (!p.hasPermission("pvp.cmd.tp")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			
			if (args.length == 1) {
				Player t = Bukkit.getPlayerExact(args[0]);
				if (t == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}

				p.teleport(t.getLocation());
				API.broadcastMessage("§7[" + p.getName()
						+ " se teleportou para " + t.getName() + "]",
						"pvp.cmd.tp");
			} else if (args.length == 2) {
				Player t = Bukkit.getPlayerExact(args[0]);
				if (t == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}

				Player t1 = Bukkit.getPlayerExact(args[1]);
				if (t1 == null) {
					p.sendMessage("§cJogador nao encontrado!");
					return true;
				}

				t.teleport(t1.getLocation());
				API.broadcastMessage("§7[" + t.getName()
						+ " foi teleportado até " + t1.getName() + "]",
						"pvp.cmd.tp");
			} else if (args.length == 3) {
				try {
					double x = Double.valueOf(args[0]);
					double y = Double.valueOf(args[1]);
					double z = Double.valueOf(args[2]);
					p.teleport(new Location(p.getWorld(), x, y, z));
					API.broadcastMessage("§7[" + p.getName()
							+ " se teleportou para X:" + x + ", Y: " + y +"§7, Z: " + z + "] ", "pvp.cmd.tp");
				} catch (Exception e) {
					p.sendMessage("§cLocalização inválida!");
				}
			} else if (args.length == 4) {
				try {
					double x = Double.valueOf(args[1]);
					double y = Double.valueOf(args[2]);
					double z = Double.valueOf(args[3]);
					Player t = Bukkit.getPlayerExact(args[0]);
					if (t == null) {
						p.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					t.teleport(new Location(t.getWorld(), x, y, z));
					API.broadcastMessage("§7[" + t.getName()
							+ " foi teleportado para X:" + x + ", Y: " + y +", Z: " + z + "] ", "pvp.cmd.tp");
				} catch (Exception e) {
					p.sendMessage("§cLocalização inválida!");
				}
			} else {
				p.sendMessage("§cUse /tp <jogador>");
				p.sendMessage("§cUse /tp <jogador> <jogador>");
				p.sendMessage("§cUse /tp  <x> <y> <z>");
				p.sendMessage("§cUse /tp <jogador> <x> <y> <z>");
			}
		}
		
		if(label.equalsIgnoreCase("tpall")) {
			if (!p.hasPermission("pvp.cmd.tpall")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			
			for(Player on : Bukkit.getOnlinePlayers()) {
				on.teleport(p.getLocation());
				on.sendMessage("§7[§a!§7] Todos foram puxados!");
			}
		}
		
		if(label.equalsIgnoreCase("doar")) {
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("tigercoins")) {
					String nome = args[1];
					int quantidade = 0;
					try {
						 quantidade = Integer.valueOf(args[2]);
					} catch(NumberFormatException e) {
						p.sendMessage("§cUse /doar TigerCoins <jogador> <quantidade>");
						return true;
					}
					
					if(ScoreManager.isRegistered(nome)) {
						if(Money.getMoney(p.getName()) >= quantidade) {
							p.sendMessage("§aVocê doou §5" + quantidade + " §d§lTigerCoins §apara " + nome + "!");
							ScoreManager.registerData(nome);
							Money.removeMoney(p.getName(), quantidade);
							Money.addMoney(nome, quantidade);
							ScoreboardManager.refreshSidebar(p);
							Player t = Bukkit.getPlayerExact(nome);
							if(t != null) {
								t.sendMessage("§aVocê recebeu uma doaçao de §5" + quantidade + " §d§lTigerCoins §ade " + p.getName());
								ScoreboardManager.refreshSidebar(t);
							}
						} else {
							p.sendMessage("§cVocê nao possui §5" + quantidade + " §d§lTigerCoins §c!");
						}
					} else {
						p.sendMessage("§cEste jogador nunca entrou no servidor!");
					}
				} else {
					p.sendMessage("§cUse /doar TigerCoins <jogador> <quantidade>");
				}
			} else {
				p.sendMessage("§cUse /doar TigerCoins <jogador> <quantidade>");
			}
		}
		return false;
	}
}
