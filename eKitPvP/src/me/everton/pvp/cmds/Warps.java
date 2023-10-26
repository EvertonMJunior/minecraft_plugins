package me.everton.pvp.cmds;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.LocationsManager;
import me.everton.pvp.Main;
import me.everton.pvp.SpawnProtection;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Warps implements Listener,CommandExecutor{
	public static HashMap<String, Locations> warp = new HashMap<>();
	
	public static enum Locations{
		SPAWN("Spawn", LocationsManager.getSpawn()),
		FPS("FPS", LocationsManager.getLocation("fps")),
		ONEVSONE("1v1", LocationsManager.getLocation("1v1")),
		LAVACHALLENGE("LavaChallenge", LocationsManager.getLocation("lava-challenge")),
		SIMULADORDEHG("Simulador de HG", LocationsManager.getLocation("simulador-de-hg")),
		TEXTURAS("Texturas", LocationsManager.getLocation("texturas"));
		
		String nome;
		Location location;
		
		private Locations(String s, Location l) {
			nome = s;
			location = l;			
		}
		
		public String getName() {
			return nome;
		}
		
		public Location getLocation() {
			return location;
		}
	}
	
	public static void openSelector(Player p) {
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
		Inventory inv = Bukkit.createInventory(p, 27, "§4Warps");
		API.fillVillagerInv(inv);
		
		inv.setItem(10, API.item(Material.GLASS, 1, "§f» §f§lFPS"));
		inv.setItem(11, API.item(Material.BLAZE_ROD, 1, "§f» §6§l1v1"));
		inv.setItem(12, API.item(Material.MUSHROOM_SOUP, 1, "§f» §e§lSimulador de HG"));
		inv.setItem(13, API.item(Material.LAVA_BUCKET, 1, "§f» §6§lLavaChallenge"));
		inv.setItem(14, API.item(Material.BOOK, 1, "§f» §6§lTexturas"));
		
		p.openInventory(inv);
	}
	
	public static HashMap<String, String> warping = new HashMap<>();
	
	public static void tpCd(final Player p, final int cd, final Locations loc, final String beforeWarp) {
		if(loc == null) {
			p.sendMessage("§7[§c!§7] Erro nessa warp! Contate algum staffer avisando sobre o erro §c§l1§7 nas Warps!");
			return;
		}
		int jogadores = 0;
		for(Entity ent : p.getNearbyEntities(10.0D, 5.0D, 10.0D)) {
			if(ent instanceof Player) {
				Player en = (Player) ent;
				if(!SpawnProtection.hasProtection(en) && !Admin.admins.contains(en.getName())) {
					jogadores++;
				}
			}
		}
		if(jogadores == 0) {
			p.teleport(loc.getLocation());
			if(loc == Locations.SPAWN) {
				KitManager.resetKit(p);
				API.itensIniciais(p);
				SpawnProtection.addProtection(p, true);
			} else if(loc == Locations.FPS) {
				KitManager.resetKit(p);
				KitManager.giveKit(p, KitType.PVP);
				warp.put(p.getName(), loc);
				SpawnProtection.removeProtection(p, true);
			} else if(loc == Locations.ONEVSONE) {
				p.chat("/1v1");
			} else if(loc == Locations.LAVACHALLENGE) {
				KitManager.resetKit(p);
				KitManager.giveKit(p, KitType.PVP);
				warp.put(p.getName(), loc);
				SpawnProtection.removeProtection(p, true);
			} else if(loc == Locations.TEXTURAS) {
				KitManager.resetKit(p);
				KitManager.giveKit(p, KitType.PVP);
				warp.put(p.getName(), loc);
			}
			API.sendTitle(p, "§c§l" + loc.getName(), "§6§lSeja bem-vindo!", 1, 2, 1);
			return;
		}
		final Location l = p.getLocation().clone();
		l.setYaw(0);
		l.setPitch(0);
		if(warping.containsKey(p.getName()) && warping.get(p.getName()).equalsIgnoreCase(loc.getName())) {
			p.sendMessage("§7[§c!§7] Aguarde " + cd + " segundos!");
			return;
		}
		p.sendMessage("§cVocê será teleportado em " + cd + " segundos! Não se mexa!");
		warping.put(p.getName(), loc.getName());
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				warping.remove(p.getName());
				Location loca = p.getLocation().clone();
				loca.setYaw(0);
				loca.setPitch(0);
				if(!loca.equals(l)) {
					API.sendTitle(p, "§c§lTeleporte cancelado", "§6§lVocê se moveu", 1, 2, 1);
					return;
				}
				if(loc == Locations.SPAWN) {
					KitManager.resetKit(p);
					API.itensIniciais(p);
					SpawnProtection.addProtection(p, true);
				}
				API.sendTitle(p, "§c§l" + loc.getName(), "§6§lSeja bem-vindo!", 1, 2, 1);
				p.teleport(loc.getLocation());
			}
		}, cd * 20L);
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Comando in-game!");
			return true;
		}
		Player p = (Player) sender;
		if(label.equalsIgnoreCase("warp") || label.equalsIgnoreCase("warps")) {
			openSelector(p);
		}
		if(label.equalsIgnoreCase("setlocation")) {
			if(!p.hasPermission("pvp.cmd.setlocation")) {
				p.sendMessage("§7[§c!§7] Sem permissao!");
				return true;
			}
			
			if(args.length == 1) {
				LocationsManager.setLocation(p, args[0]);
				p.sendMessage("§a§l" + args[0] + "§a setado com sucesso");
			} else {
				p.sendMessage("§cUse /setlocation <nome da localizacao>");
			}
		}
		return false;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!e.getAction().name().contains("RIGHT")) {
			return;
		}
		
		if(p.getItemInHand().getType() != Material.PAPER) {
			return;
		}
		
		if(!p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§3§lWarps §7(Clique Direito)")) {
			return;
		}
		
		openSelector(p);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInvInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		if(inv == null) {
			return;
		}
		ItemStack i = e.getCurrentItem();
		if(inv.getTitle().equalsIgnoreCase("§4Warps")) {
			e.setCancelled(true);
			e.setCursor(null);
			
			if(i.getType() == Material.BLAZE_ROD) {
				tpCd(p, 5, Locations.ONEVSONE, "ao");
			}
			if(i.getType() == Material.MUSHROOM_SOUP) {
				tpCd(p, 5, Locations.SIMULADORDEHG, "ao");
			}
			if(i.getType() == Material.LAVA_BUCKET) {
				tpCd(p, 5, Locations.LAVACHALLENGE, "ao");
			}
			if(i.getType() == Material.BOOK) {
				tpCd(p, 5, Locations.TEXTURAS, "ao");
			}
			if(i.getType() != Material.STAINED_GLASS_PANE) {
				p.closeInventory();
			}
		}
	}
}
