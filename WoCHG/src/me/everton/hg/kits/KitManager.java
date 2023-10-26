package me.everton.hg.kits;

import me.everton.hg.API;
import me.everton.hg.Main;
import me.everton.hg.ScoreBoard.Board;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class KitManager {
	public static void giveKit(Player p, KitType kit) {
		if(Main.INVENCIBILIDADE) {
			if(!p.hasPermission("hg.kitslater")) {
				p.sendMessage("§7[§c!§7] Você nao pode escolher seu kit agora!");
				return;
			}
		}
		if(Main.EM_JOGO) {
			if(!Main.INVENCIBILIDADE) {
				p.sendMessage("§7[§c!§7] Você nao pode escolher seu kit agora!");
				return;
			}
		}
		
		if(Main.INVENCIBILIDADE) {
			if(getKit(p) != KitType.NONE) {
				p.sendMessage("§7[§c!§7] Você já escolheu um kit!");
				return;
			}
		}
		
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F,
				15.5F);
		p.sendMessage(" ");
		p.sendMessage("§7[§6§l!!!§7] Kit §c§l" + kit.getName() + " §7escolhido com sucesso!");
		p.sendMessage("§7[§6§l!!!§7] Descriçao do Kit: §c§l" + kit.getDescription() + "§7!");
		p.sendMessage(" ");
		if(Main.kit.containsKey(p.getName())) {
			Main.kit.remove(p.getName());
		}
		Main.kit.put(p.getName(), kit);
		Board.refreshScore(p);
		p.closeInventory();
		
		if(Main.INVENCIBILIDADE) {
			for (KitType kits : KitType.values()) {
				if (getKit(p) == kits) {
					if (kits.getItemKit() == null) {
						return;
					}
					if (ChatColor
							.stripColor(
									kits.getItemKit().getItemMeta()
											.getDisplayName())
							.toLowerCase()
							.equalsIgnoreCase(kits.name().toLowerCase())) {
						if(API.isInvFull(p)) {
							p.getWorld().dropItemNaturally(p.getLocation(), p.getInventory().getItem(4));
							p.getInventory().setItem(4, kits.getItemKit());
							p.updateInventory();
							p.sendMessage("§7[§c!§7] Como seu inventário estava cheio, o item 5 de sua hotbar foi dropado e o item do kit colocado no lugar dele!");
						} else {
							p.getInventory().addItem(kits.getItemKit());
							p.updateInventory();
						}
						break;
					}
				}
			}
		}
	}
	
	public static KitType getKit(Player p) {
		return Main.kit.get(p.getName());
	}
	
	public static boolean isWithKitItemInHand(Player p) {
		try {
			if(ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName()).equalsIgnoreCase(getKit(p).getName())) {
				return true;
			} else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
	}
}
