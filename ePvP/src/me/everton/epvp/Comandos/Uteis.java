package me.everton.epvp.Comandos;

import java.util.HashMap;
import java.util.List;

import me.everton.epvp.KitManager;
import me.everton.epvp.Main;
import me.everton.epvp.ScoreManager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Uteis implements CommandExecutor,TabCompleter {
	public static HashMap<String, String> reports = new HashMap<>();

	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();
	
	public static HashMap<String, ItemStack[]> inv = new HashMap<>();
	public static HashMap<String, ItemStack[]> armor = new HashMap<>();
	
	
	public static boolean isInt(String s) {
		for (int a = 0; a < s.length(); a++) {
			if (a == 0 && s.charAt(a) == '-')
				continue;
			if (!Character.isDigit(s.charAt(a)))
				return false;
		}
		return true;
	}
	
	
	public static boolean dano = true;
	
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
		final Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("espada")){
			Inventory espadas = Bukkit.createInventory(p, 9, "§4§lEspadas");
			for(int i = 0; i < 9; i++){
				espadas.setItem(i, new ItemStack(Material.WOOD_SWORD));
			}
			
			p.openInventory(espadas);
		}
		
		if(label.equalsIgnoreCase("spawn")){
			p.sendMessage("§aAguarde 5 segundos para ser teleportado ao spawn!");
			Bukkit.getScheduler().scheduleSyncDelayedTask(
					Main.plugin,
					new Runnable() {
						public void run() {
							p.teleport(Main.loc("spawn", p));
							Main.spawnItens(p);
							KitManager.resetKit(p, false);
						}
					}, 100L);
		} else if(label.equalsIgnoreCase("head")) {
			p.setFireTicks(0);
			if(p.hasPermission("pvp.admin")){
				if(args.length == 1) {
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
					SkullMeta meta = (SkullMeta)skull.getItemMeta();
					meta.setOwner(args[0]);
					meta.setDisplayName("§6Cabeça de " + args[0]);
					skull.setItemMeta(meta);
					p.getInventory().addItem(skull);
					p.sendMessage("§7[§a!§7] Recebesses a cabeça de " + args[0]);
				} else {
					p.sendMessage("§cUse: /head <jogador>");
				}
			} else {
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
			}
		} else if (label.equalsIgnoreCase("heal")) {
			if (p.hasPermission("pvp.admin")) {
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
		
		if(label.equalsIgnoreCase("clear")){
			if(p.hasPermission("pvp.admin")){
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
			} else {
				p.sendMessage("§7[§c!§7] Sem permissao!");
			}
		}

		if (label.equalsIgnoreCase("report")) {
			if (args.length < 2) {
				p.sendMessage("§cUse §l/report [nome do jogador/bug] [motivo]");
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
				if ((reports.containsKey(p.getName()))
						&& (reports.get(p.getName()).equalsIgnoreCase(nick))) {
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
									cd.put(pf.getName(), tempo);
								}
								if (tempo == 0) {
									if(task.containsKey(pf.getName())) {
									Main.sh.cancelTask(task.get(pf.getName()));
									}
									cd.remove(pf.getName());
									task.remove(pf.getName());
									reports.remove(pf.getName());
								}
							}
						}, 0, 20);
				task.put(p.getName(), coolDown);
				reports.put(p.getName(), nick);

				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String allArgs = sb.toString().trim();

				for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
					if (staff.hasPermission("pvp.admin")) {
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
			}
		}

		if (label.equalsIgnoreCase("fly")) {

			if (p.hasPermission("pvp.admin")) {
				if (args.length == 0) {
					if (p.getAllowFlight()) {
						p.setAllowFlight(false);
						p.sendMessage("§7[§c!§7] Você nao pode voar mais!");
					} else if (!p.getAllowFlight()) {
						p.setAllowFlight(true);
						p.sendMessage("§7[§a!§7] Agora Você pode voar!");
					}

				} else if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					if (target.getAllowFlight()) {
						target.setAllowFlight(false);
						p.sendMessage("§7[§c!§7] " + target.getName()
								+ " nao pode voar mais!");
						target.sendMessage("§7[§c!§7] Você nao pode voar mais!");
					} else if (!target.getAllowFlight()) {
						target.setAllowFlight(true);
						p.sendMessage("§7[§a!§7] " + target.getName()
								+ " agora pode voar!");
						target.sendMessage("§7[§a!§7] Agora Você pode voar!");
					}

				}
			} else {
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
			}
		}
		
		if(label.equalsIgnoreCase("dano")){
			
			if(!p.hasPermission("pvp.admin")){
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
				return true;
			}
			
			if(args.length == 1){
				 if(args[0].equalsIgnoreCase("off")){
					 if(!dano){
						 p.sendMessage("§cO dano ja esta desativado!");
					 } else {
						 dano = false;
						 for(Player on : Bukkit.getOnlinePlayers()){
							 on.sendMessage("§7[§c!§7] O dano global foi desativado!");
						 }
					 }
				 } else if(args[0].equalsIgnoreCase("on")){
					 if(dano){
						 p.sendMessage("§cO dano ja esta ativo!");
					 } else {
						 dano = true;
						 for(Player on : Bukkit.getOnlinePlayers()){
							 on.sendMessage("§7[§c!§7] O dano global foi ativo!");
						 }
					 }
				 } else {
					 p.sendMessage("§cUse: /dano <on/off>");
				 }
			} else {
				p.sendMessage("§cUse: /dano <on/off>");
			}
		}
		
		if (label.equalsIgnoreCase("ajuda")) {
			p.sendMessage("§7[§c!§7] Recurso indisponível no momento!");
		}
		
		if(label.equalsIgnoreCase("gm")){
			if(!p.hasPermission("pvp.admin")){
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
				return true;
			}
			
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("0")){
					p.setGameMode(GameMode.SURVIVAL);
					p.sendMessage("§7[§c!§7] Gamemode atualizado para Survival!");
				} else if(args[0].equalsIgnoreCase("1")){
					p.setGameMode(GameMode.CREATIVE);
					p.sendMessage("§7[§c!§7] Gamemode atualizado para Criativo!");
				}
			} else {
				if(p.getGameMode() == GameMode.SURVIVAL){
					p.setGameMode(GameMode.CREATIVE);
					p.sendMessage("§7[§c!§7] Gamemode atualizado para Criativo!");
				} else if(p.getGameMode() == GameMode.CREATIVE){
					p.setGameMode(GameMode.SURVIVAL);
					p.sendMessage("§7[§c!§7] Gamemode atualizado para Survival!");
				}
			}
		}
		
		if(label.equalsIgnoreCase("skit")){
			if(!p.hasPermission("pvp.admin")){
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
				return true;
			}
			
			if(args.length == 2){
				if(args[1].equalsIgnoreCase("create")){
					p.sendMessage("§7[§c!§7] " + args[0] + " criado!");
					inv.put(args[0], p.getInventory().getContents());
					armor.put(args[0], p.getInventory().getArmorContents());
				} else {
					p.sendMessage("§cUse: /skit <nome do skit> <create/apply> <raio de blocos/all>");
				}
			} else if(args.length == 3){
				 if(args[1].equalsIgnoreCase("apply")){
						if(inv.containsKey(args[0])){
							if(args[2].equalsIgnoreCase("all")){
								for(Player on : Bukkit.getOnlinePlayers()){
									on.getInventory().setContents(inv.get(args[0]));
									on.getInventory().setArmorContents(armor.get(args[0]));
								}
								p.sendMessage("§7[§c!§7] SKIT " + args[0] + " aplicado para todos!");
							} else if(isInt(args[2])){
								for(Entity near : p.getNearbyEntities(Double.valueOf(args[2]), Double.valueOf(args[2]), Double.valueOf(args[2]))){
									if(near instanceof Player){
										Player n = (Player) near;
										n.getInventory().setContents(inv.get(args[0]));
										n.getInventory().setArmorContents(armor.get(args[0]));
									}
								}
								p.sendMessage("§7[§c!§7] SKIT " + args[0] + " aplicado para todos num raio de " + args[2] + " blocos!");
							}
						} else {
							p.sendMessage("§7[§c!§7] O SKIT " + args[0] + " nao existe!");
						}
					} else {
						p.sendMessage("§cUse: /skit <nome do skit> <create/apply> <raio de blocos/all>");
					}
			} else {
				p.sendMessage("§cUse: /skit <nome do skit> <create/apply> <raio de blocos/all>");
			}
		}
		
		
		if(label.equalsIgnoreCase("tpall")){
			if(!p.hasPermission("pvp.admin")){
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
				return true;
			}
			
			for(Player on : Bukkit.getOnlinePlayers()){
				on.teleport(p);
				on.sendMessage("§7[§c!§7] Todos foram teleportados para este local!");
			}
			
			
		}
		
		if((label.equalsIgnoreCase("invsee")) || (label.equalsIgnoreCase("inv"))){
			if(!p.hasPermission("pvp.admin")){
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
				return true;
			}
			
			if(args.length == 1){
				if(Bukkit.getPlayerExact(args[0]) == null){
					p.sendMessage("§7[§c!§7] O jogador esta offline!");
					return true;
				}				
				p.openInventory(Bukkit.getPlayerExact(args[0]).getInventory());
				p.sendMessage("§7[§c!§7] Vendo inventário de " + Bukkit.getPlayerExact(args[0]).getName() + "!");
			}
			
		}
		
		if(label.equalsIgnoreCase("resetkdr")) {
			if(!p.isOp()){
				p.sendMessage("§4§7[§c!§7] Sem permissao!");
				return true;
			}
			
			if(args.length == 1) {
				ScoreManager.resetKdr(args[0]);
				p.sendMessage("§7[§a!§7] O KDR de " + args[0] + " foi resetado!");
			}
		}
		return false;
	}

	@Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
            if (cmd.getName().equalsIgnoreCase("kit")) {
                    if (args.length == 1) {
                            return Main.listakits;
                    }
            }
           
            return null;
    }
}
