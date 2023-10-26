package me.everton.epvp.Comandos;

import java.util.List;

import me.confuser.barapi.BarAPI;
import me.everton.epvp.API;
import me.everton.epvp.Main;
import me.everton.epvp.Title;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Warps implements Listener,CommandExecutor{
	public static FileConfiguration w = Main.settings.getConfig();
	public static List<String> wa;
	public static List<String> wa2;
	
	@SuppressWarnings("deprecation")
	public static void addWarp(Player p, String name, String categoria, boolean itensInv) {
		if(categoria.equalsIgnoreCase("arenas")) {
			if(!wa.contains(name)) {
				wa.add(name);
				w.set("Arenas", wa);
			}
		} else if(categoria.equalsIgnoreCase("gerais")) {
			wa2.add(name);
			w.set("WarpsGerais", wa2);
		}
		
		if(p.getItemInHand().getTypeId() == 0) {
			p.sendMessage("§7[§c!§7] Item da warp nao permitido!");
			return;
		}
		
		w.set("warps." + name + ".nome", name);
		w.set("warps." + name + ".world", p.getLocation().getWorld().getName());
		w.set("warps." + name + ".x", p.getLocation().getX());
		w.set("warps." + name + ".y", p.getLocation().getY());
		w.set("warps." + name + ".z", p.getLocation().getZ());
		w.set("warps." + name + ".yaw", p.getLocation().getYaw());
		w.set("warps." + name + ".pitch", p.getLocation().getPitch());
		w.set("warps." + name + ".item", p.getItemInHand().getTypeId());
		w.set("warps." + name + ".itemtype", Integer.valueOf(p.getItemInHand().getData().getData()));
		w.set("warps." + name + ".categoria", categoria);
		if(itensInv) {
			w.set("warps." + name + ".itens", p.getInventory().getContents());
			w.set("warps." + name + ".armor", p.getInventory().getArmorContents());
		}
		
		Main.settings.saveConfig();
	}
	
	public static void openWarpInv(Player p, int categoria) {
		if(categoria == 1) {
			
			Inventory inv = Bukkit.createInventory(p, 27, "§4§l4Warps - Principal");
			
			for(int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0", 7));
			}
			
			inv.setItem(11, API.item(Material.PAPER, 1, "§b§lArenas"));
			inv.setItem(15, API.item(Material.CAKE, 1, "§b§lGerais"));
			
			p.openInventory(inv);
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
		} else if(categoria == 2) {
			int size = 0;
			if(wa.size() > 14) {
				size = 45;
			} else if(wa.size() > 7) {
				size = 36;
			} else {
				size = 27;
			}
			
			Inventory inv = Bukkit.createInventory(p, size, "§4§lWarps - Arenas");
			
			for(int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, "§0", 7));
			}
			
			if(size == 45) {
				for(int i = 0; i < 7; i++) {
					if(i == wa.size()) {
						break;
					}
					inv.setItem(i + 10, API.item(w.getInt("warps." + wa.get(i) + ".item"), 1, w.getString("warps." + wa.get(i) + ".nome"), w.getInt("warps." + wa.get(i) + ".itemtype")));
				}
				for(int i = 7; i < 14; i++) {
					if(i == wa.size()) {
						break;
					}
					inv.setItem(i + 12, API.item(w.getInt("warps." + wa.get(i) + ".item"), 1, w.getString("warps." + wa.get(i) + ".nome"), w.getInt("warps." + wa.get(i) + ".itemtype")));
				}
				for(int i = 14; i < 21; i++) {
					if(i == wa.size()) {
						break;
					}
					inv.setItem(i + 14, API.item(w.getInt("warps." + wa.get(i) + ".item"), 1, w.getString("warps." + wa.get(i) + ".nome"), w.getInt("warps." + wa.get(i) + ".itemtype")));
				}
			} else if(size == 36) {
				for(int i = 7; i < 15; i++) {
					if(i == wa.size()) {
						break;
					}
					inv.setItem(i + 12, API.item(w.getInt("warps." + wa.get(i) + ".item"), 1, w.getString("warps." + wa.get(i) + ".nome"), w.getInt("warps." + wa.get(i) + ".itemtype")));
				}
				for(int i = 0; i < 7; i++) {
					if(i == wa.size()) {
						break;
					}
					inv.setItem(i + 10, API.item(w.getInt("warps." + wa.get(i) + ".item"), 1, w.getString("warps." + wa.get(i) + ".nome"), w.getInt("warps." + wa.get(i) + ".itemtype")));
				}
			} else if(size == 27) {
				for(int i = 0; i < 7; i++) {
					if(i == wa.size()) {
						break;
					}
					inv.setItem(i + 10, API.item(w.getInt("warps." + wa.get(i) + ".item"), 1, w.getString("warps." + wa.get(i) + ".nome"), w.getInt("warps." + wa.get(i) + ".itemtype")));
				}
			}
			
			p.openInventory(inv);
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
		} else if(categoria == 3) {
			int size = 0;
			if(wa2.size() >= 54) {
				size = 54;
			} else if(wa2.size() >= 45) {
				size = 45;
			} else if(wa2.size() >= 36) {
				size = 36;
			} else if(wa2.size() >= 27) {
				size = 27;
			} else {
				size = 27;
			}
			
			Inventory inv = Bukkit.createInventory(p, size, "§4§lWarps - Principal");
			
			
			
			p.openInventory(inv);
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.5F, 15.5F);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player p = (Player) sender;
		if(label.equalsIgnoreCase("setwarp")) {
			if(!p.isOp()) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("item")) {
					if(p.getItemInHand().getTypeId() == 0) {
						p.sendMessage("§7[§c!§7] Item nao permitido!");
						return true;
					}
					w.set("warps." + args[1] + ".item", p.getItemInHand().getTypeId());
					w.set("warps." + args[1] + ".itemtype", Integer.valueOf(p.getItemInHand().getData().getData()));
					Main.settings.saveConfig();
					p.sendMessage("§7[§a§7] Item setado para: " + p.getItemInHand().getTypeId() + ":" + p.getItemInHand().getData().getData());
					return true;
				}
				addWarp(p, args[0], args[1], false);
				p.sendMessage("§7[§a§7] Warp Setada!");
			} else if(args.length > 3) {
				addWarp(p, args[0], args[1], true);
				p.sendMessage("§7[§a§7] Warp Setada! (Comando: )");
			} else {
				p.sendMessage("§cUse: /setwarp <warp> <Arenas/Gerais> <true se quiser os itens do inventario>");
				p.sendMessage("§cUse para setar o item da warp (o item será o que está em sua mao na hora do comando): /setwarp item <warp>");
			}
		} 
		if(label.equalsIgnoreCase("delwarp")) {
			if(!p.isOp()) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			if(args.length == 2) {
				if(args[1].equalsIgnoreCase("arenas")) {
					if(!wa.contains(args[0])) {
						p.sendMessage("§7[§c!§7] Esta warp nao existe!");
						return true;
					}
					w.set("warps." + args[0] + ".nome", null);
					w.set("warps." + args[0] + ".world", null);
					w.set("warps." + args[0] + ".x", null);
					w.set("warps." + args[0] + ".y", null);
					w.set("warps." + args[0] + ".z", null);
					w.set("warps." + args[0] + ".yaw", null);
					w.set("warps." + args[0] + ".pitch", null);
					w.set("warps." + args[0] + ".item", null);
					w.set("warps." + args[0] + ".itemtype", null);
					w.set("warps." + args[0] + ".categoria", null);
					w.set("warps." + args[0] + ".itens", null);
					w.set("warps." + args[0] + ".armor", null);
					wa.remove(args[0]);
					w.set("Arenas", wa);
					Main.settings.saveConfig();
					
				} else if(args[1].equalsIgnoreCase("gerais")) {
					if(!wa2.contains(args[0])) {
						p.sendMessage("§7[§c!§7] Esta warp nao existe!");
						return true;
					}
					w.set("warps." + args[0] + ".nome", null);
					w.set("warps." + args[0] + ".world", null);
					w.set("warps." + args[0] + ".x", null);
					w.set("warps." + args[0] + ".y", null);
					w.set("warps." + args[0] + ".z", null);
					w.set("warps." + args[0] + ".yaw", null);
					w.set("warps." + args[0] + ".pitch", null);
					w.set("warps." + args[0] + ".item", null);
					w.set("warps." + args[0] + ".itemtype", null);
					w.set("warps." + args[0] + ".categoria", null);
					w.set("warps." + args[0] + ".itens", null);
					w.set("warps." + args[0] + ".armor", null);
					wa2.remove(args[0]);
					w.set("Gerais", wa);
					Main.settings.saveConfig();
				} else {
					p.sendMessage("§cUse: /delwarp <warp> <Arenas/Gerais>");
				}
			} else {
				p.sendMessage("§cUse: /delwarp <warp> <Arenas/Gerais>");
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onInvInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Warps - Arenas")) {
			e.setCancelled(true);
			if(wa.contains(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))) {
				String world = w.getString("warps." + e.getCurrentItem().getItemMeta().getDisplayName() + ".world");
				Double x = w.getDouble("warps." + e.getCurrentItem().getItemMeta().getDisplayName() + ".x");
				Double y = w.getDouble("warps." + e.getCurrentItem().getItemMeta().getDisplayName() + ".y");
				Double z = w.getDouble("warps." + e.getCurrentItem().getItemMeta().getDisplayName() + ".z");
				Float yaw = (float) w.getDouble("warps." + e.getCurrentItem().getItemMeta().getDisplayName() + ".yaw");
				Float pitch = (float) w.getDouble("warps." + e.getCurrentItem().getItemMeta().getDisplayName() + ".pitch");
				
				Location wloc = new Location(Bukkit.getWorld(world), x, y, z);
				wloc.setYaw(yaw);
				wloc.setPitch(pitch);
				
				p.setMaxHealth(2.0D);
				p.setHealth(2.0D);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000000, 255));
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000000, 200));
				
				Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					

					@Override
					public void run() {
						p.teleport(wloc);
						p.setMaxHealth(20.0D);
						p.setHealth(20.0D);
						
						if(Title.getProtocolVersion(p) < 47) {
							BarAPI.setMessage(p, "§aVocê foi teleportado à warp " + e.getCurrentItem().getItemMeta().getDisplayName() + "!", 3);
						}
						API.sendTitle(p, "§6Teleportado para Arena", "§a" + e.getCurrentItem().getItemMeta().getDisplayName(), 1, 3, 1);
						
						if(w.get("warps." + e.getCurrentItem().getItemMeta().getDisplayName() + ".itens") != null) {
							ItemStack[] inv = ((List<ItemStack>) w.get("warps." + e.getCurrentItem().getItemMeta().getDisplayName() + ".itens")).toArray(new ItemStack[0]);
							p.getInventory().setContents(inv);
						}
						p.removePotionEffect(PotionEffectType.SLOW);
						p.removePotionEffect(PotionEffectType.JUMP);
					}
				}, 5 * 20L);
			}
		}
	}
}
