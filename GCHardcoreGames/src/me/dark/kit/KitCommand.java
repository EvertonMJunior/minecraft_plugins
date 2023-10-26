package me.dark.kit;

import java.util.Arrays;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.JSON.JSONChatClickEventType;
import me.dark.JSON.JSONChatColor;
import me.dark.JSON.JSONChatExtra;
import me.dark.JSON.JSONChatFormat;
import me.dark.JSON.JSONChatHoverEventType;
import me.dark.JSON.JSONChatMessage;
import me.dark.Title.TitleAPI;

public class KitCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!Main.toggleKits) {
				p.sendMessage("§4Todos os kits foram desativados!");
				return true;
			}
			if (args.length == 0){
				String kits = "§3Seus kits: §f";
				
		        JSONChatMessage message = new JSONChatMessage(kits, JSONChatColor.WHITE, null);
		        boolean haveKit = false;
				for (Kit k : Kit.kits) {
					if (p.hasPermission("kit."+k.toString())) {
						String nome = "";
						if (Main.notToggled.contains(k)) {
							nome = "§4"+k.toString() + "§f, ";
						}else if (Kit.kits.get(Kit.kits.size()-1) != k) {
							nome = k.toString() + "§f, ";
						} else {
							nome = k.toString();
						}
				        JSONChatExtra extra = new JSONChatExtra(nome, JSONChatColor.WHITE, Arrays.asList(JSONChatFormat.NENHUM));
						haveKit = true;
				        extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "§aClique para escolher");
				        extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/kit "+k.toString());
				        message.addExtra(extra);
					}
				}
				String outros = "§cOutros kits: §f";
		        JSONChatMessage sad = new JSONChatMessage(outros, JSONChatColor.WHITE, null);
		        boolean allKits = true;
				for (Kit k : Kit.kits) {
					if (!p.hasPermission("kit."+k.toString())) {
						String nome = "";
						if (Main.notToggled.contains(k)) {
							nome = "§4"+k.toString() + "§f, ";
						}else if (Kit.kits.get(Kit.kits.size()-1) != k) {
							nome = k.toString() + "§f, ";
						} else {
							nome = k.toString();
						}
				        JSONChatExtra extra = new JSONChatExtra(nome, JSONChatColor.WHITE, Arrays.asList(JSONChatFormat.NENHUM));
				        extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "§cVocê nÃo possui esse kit!\n§4Compre-o em "+Main.site);
				        extra.setClickEvent(JSONChatClickEventType.OPEN_URL, Main.site);
				        sad.addExtra(extra);
				        allKits = false;
					}
				}
				if (haveKit) {
			        message.sendToPlayer(p);
				} else {
					p.sendMessage(kits + "Você nÃo tem nenhum kit!");
				}
				
				if (allKits) {
					p.sendMessage(outros + "Você já tem todos os kits!");
				} else {
					sad.sendToPlayer(p);
				}
				p.sendMessage("§3Compre outros kits em "+Main.site);
			} else {
				Kit k = KitManager.getKitByString(args[0]);
				if (k != null) {
					if (p.hasPermission("kit."+k.toString())) {
						if (Main.estado == GameState.PREGAME) {
							if (Main.notToggled.contains(k)) {
								p.sendMessage("§4Esse kit está desativado!");
								return true;
							}
							KitManager.removeKit(p);
							k.addPlayer(p);
							p.sendMessage(" ");
							p.sendMessage(Main.linha);
							if (TitleAPI.is1_8(p)) {
								TitleAPI.s5Titulo(p, 20, 20, 20, "§bVocê escolheu o kit", "§3§l"+k.toString());
							} else {
								p.sendMessage("§bVocê escolheu o kit §3§l"+k.toString()+"§b!");	
							}
							String s = "";
							for (String ss : k.getDesc()) {
								s = s+" "+ss;
							}
							p.sendMessage("§b§l"+k.toString()+" "+Main.seta+" "+s);
							p.sendMessage(Main.linha);
							p.sendMessage(" ");
							p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
							p.closeInventory();
						} else {
							if (Main.gameTime <= 300) {
								if (p.hasPermission(Main.perm_prata)) {
									if (!KitManager.hasAnyKit(p)) {
										if (Main.notToggled.contains(k)) {
											p.sendMessage("§4Esse kit está desativado!");
											return true;
										}
										KitManager.removeKit(p);
										k.addPlayer(p);
										p.sendMessage(" ");
										p.sendMessage(Main.linha);
										p.sendMessage("§bVocê escolheu o kit §3§l"+k.toString()+"§b!");
										String s = "";
										for (String ss : k.getDesc()) {
											s = s+" "+ss;
										}
										p.sendMessage("§b§l"+k.toString()+" "+Main.seta+" "+s);
										p.sendMessage(Main.linha);
										p.sendMessage(" ");
										p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
										p.closeInventory();
							            if (k != null) {
							            	if (k.getItems() != null) {
							            		for (ItemStack items : k.getItems()) {
							            			if (items != null) {
							            				p.getInventory().addItem(items);
							            			}
							            		}
							            	}
							            }
									} else {
										p.sendMessage(Main.prefix+ "Você já está com um kit!");
									}
								} else {
									p.sendMessage(Main.prefix+"Para escolher um kit após o jogo ter iniciado, você precisa ser §aVIP§7!");
									p.sendMessage(Main.prefix+"Adiquira seu vip em " + Main.site);
								}
							} else {
								p.sendMessage(Main.prefix+"O jogo já iniciou!");
							}
						}
					}else {
						p.sendMessage(Main.prefix+"Você não tem esse kit, compre-o em §3"+Main.site+"§f!");
					}
				} else {
					p.sendMessage(Main.prefix + "O kit '"+args[0] + "' nÃo foi encontrado!");
					p.sendMessage(Main.prefix+"Para abrir o kit selector utilize /kit");
				}
			}
		}
		return false;
	}

}
