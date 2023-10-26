package me.everton.EPVP;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Admin implements CommandExecutor,Listener {
	public ArrayList<Player> admin = new ArrayList<Player>();
	public ArrayList<Player> v = new ArrayList<Player>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("admin")){
			if(p.hasPermission("ehg.admin")){
				if(!(admin.contains(p))){
					for(Player pl : Bukkit.getServer().getOnlinePlayers()){
						pl.hidePlayer(p);
					}
					admin.add(p);
					p.setGameMode(GameMode.CREATIVE);
					p.setCanPickupItems(true);
					p.getInventory().clear();
					ItemStack blazerod = new ItemStack(Material.BLAZE_ROD);
					ItemMeta metabr = blazerod.getItemMeta();
					p.getInventory().setItem(1, blazerod);
					metabr.setDisplayName(ChatColor.AQUA + "Informações do player");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "Você entrou no modo admin!");
				} else if(admin.contains(p)){
					for(Player pl : Bukkit.getServer().getOnlinePlayers()){
						pl.showPlayer(p);
					}
					admin.remove(p);
					p.setHealth(20);
					p.setSaturation(20);
					p.setGameMode(GameMode.SURVIVAL);
					p.setCanPickupItems(true);
					p.getInventory().clear();
					p.sendMessage(ChatColor.LIGHT_PURPLE + "Você saiu do modo admin!");
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "Você não tem permissão!");
			}
		}
		if(cmd.getName().equalsIgnoreCase("v")){
			if(p.hasPermission("ehg.admin")){
				if(!v.contains(p)){
					for(Player pl : Bukkit.getServer().getOnlinePlayers()){
						pl.hidePlayer(p);
					}
					v.add(p);
					p.sendMessage(ChatColor.GOLD + "Agora você está invisível!");
				} else {
					for(Player pl : Bukkit.getServer().getOnlinePlayers()){
						pl.showPlayer(p);
					}
					v.remove(p);
					p.sendMessage(ChatColor.GOLD + "Agora você está visível!");
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "Você não tem permissão!");
			}
		}
		return false;
	}
	@EventHandler
	public void AdminLogin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(p.hasPermission("ehg.admin")){
			if(!(admin.contains(p))){
				for(Player pl : Bukkit.getServer().getOnlinePlayers()){
					pl.hidePlayer(p);
				}
				admin.add(p);
				p.setGameMode(GameMode.CREATIVE);
			}
		}
	}
	@EventHandler
	public void AdminLogin(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(p.hasPermission("ehg.admin")){
			if(admin.contains(p)){
				for(Player pl : Bukkit.getServer().getOnlinePlayers()){
					pl.showPlayer(p);
				}
				admin.remove(p);
				p.setGameMode(GameMode.SURVIVAL);
			}
		}
	}
	@EventHandler
	public void OpenInv(PlayerInteractEntityEvent e,InventoryClickEvent ie){
		Player rightclick = (Player) e.getRightClicked();
		if(e.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD){
		if(e.getRightClicked().getType() == EntityType.PLAYER){
			String playerclicado = rightclick.getName();
			e.getPlayer().sendMessage(ChatColor.GREEN + "Você abriu o inventário de " + playerclicado);
			Inventory invsee = Bukkit.createInventory(null, 36, "Inventário");
			ItemStack slot0 = rightclick.getInventory().getItem(0);
			invsee.setItem(0, slot0);
			ItemStack slot1 = rightclick.getInventory().getItem(1);
			invsee.setItem(1, slot1);
			ItemStack slot2 = rightclick.getInventory().getItem(2);
			invsee.setItem(2, slot2);
			ItemStack slot3 = rightclick.getInventory().getItem(3);
			invsee.setItem(3, slot3);
			ItemStack slot4 = rightclick.getInventory().getItem(4);
			invsee.setItem(4, slot4);
			ItemStack slot5 = rightclick.getInventory().getItem(5);
			invsee.setItem(5, slot5);
			ItemStack slot6 = rightclick.getInventory().getItem(6);
			invsee.setItem(6, slot6);
			ItemStack slot7 = rightclick.getInventory().getItem(7);
			invsee.setItem(7, slot7);
			ItemStack slot8 = rightclick.getInventory().getItem(8);
			invsee.setItem(8, slot8);
			ItemStack slot9 = rightclick.getInventory().getItem(9);
			invsee.setItem(9, slot9);
			ItemStack slot10 = rightclick.getInventory().getItem(10);
			invsee.setItem(10, slot10);
			ItemStack slot11 = rightclick.getInventory().getItem(11);
			invsee.setItem(11, slot11);
			ItemStack slot12 = rightclick.getInventory().getItem(12);
			invsee.setItem(12, slot12);
			ItemStack slot13 = rightclick.getInventory().getItem(13);
			invsee.setItem(13, slot13);
			ItemStack slot14 = rightclick.getInventory().getItem(14);
			invsee.setItem(14, slot14);
			ItemStack slot15 = rightclick.getInventory().getItem(15);
			invsee.setItem(15, slot15);
			ItemStack slot16 = rightclick.getInventory().getItem(16);
			invsee.setItem(16, slot16);
			ItemStack slot17 = rightclick.getInventory().getItem(17);
			invsee.setItem(17, slot17);
			ItemStack slot18 = rightclick.getInventory().getItem(18);
			invsee.setItem(18, slot18);
			ItemStack slot19 = rightclick.getInventory().getItem(19);
			invsee.setItem(19, slot19);
			ItemStack slot20 = rightclick.getInventory().getItem(20);
			invsee.setItem(20, slot20);
			ItemStack slot21 = rightclick.getInventory().getItem(21);
			invsee.setItem(21, slot21);
			ItemStack slot22 = rightclick.getInventory().getItem(22);
			invsee.setItem(22, slot22);
			ItemStack slot23 = rightclick.getInventory().getItem(23);
			invsee.setItem(23, slot23);
			ItemStack slot24 = rightclick.getInventory().getItem(24);
			invsee.setItem(24, slot24);
			ItemStack slot25 = rightclick.getInventory().getItem(25);
			invsee.setItem(25, slot25);
			ItemStack slot26 = rightclick.getInventory().getItem(26);
			invsee.setItem(26, slot26);
			ItemStack slot27 = rightclick.getInventory().getItem(27);
			invsee.setItem(27, slot27);
			ItemStack slot28 = rightclick.getInventory().getItem(28);
			invsee.setItem(28, slot28);
			ItemStack slot29 = rightclick.getInventory().getItem(29);
			invsee.setItem(29, slot29);
			ItemStack slot30 = rightclick.getInventory().getItem(30);
			invsee.setItem(30, slot30);
			ItemStack slot31 = rightclick.getInventory().getItem(31);
			invsee.setItem(31, slot31);
			ItemStack slot32 = rightclick.getInventory().getItem(32);
			invsee.setItem(32, slot32);
			ItemStack slot33 = rightclick.getInventory().getItem(33);
			invsee.setItem(33, slot33);
			ItemStack slot34 = rightclick.getInventory().getItem(34);
			invsee.setItem(34, slot34);
			ItemStack slot35 = rightclick.getInventory().getItem(35);
			invsee.setItem(35, slot35);
			
			e.getPlayer().openInventory(invsee);
			if(ie.getInventory().getName().equalsIgnoreCase("Inventário")){
				Bukkit.broadcastMessage("Interagiu com o inventário do player " + rightclick);
				if(ie.getSlot() == 0){
					if(ie.getAction() == Action.RIGHT_CLICK_AIR){
						rightclick.getInventory().setItem(0, arg1);
					}
				}
			}
		}
		}
		
	}
}
