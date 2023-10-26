package me.everton.epvp.Comandos;

import java.util.HashMap;

import me.everton.epvp.API;
import me.everton.epvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Trolar implements CommandExecutor, Listener {
	public static Inventory trollar = Bukkit.createInventory(null, 9,
			"Trolle Hackers!");
	public static Player jogador1;
	
	public static HashMap<String, Item> drops = new HashMap<String, Item>();
	public static HashMap<String, Location> anvil = new HashMap<>();
	public static HashMap<String, Material> anvil2 = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("troll")) {
			Player p = (Player) sender;
			if (p.hasPermission("pvp.admin")) {
				if (args.length == 0) {
					p.sendMessage("§cUse /troll (nick)");

				} else if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if ((target == null) || (!(target instanceof Player))) {
						sender.sendMessage("§cJogador nao encontrado!");
						return true;
					}
					jogador1 = p.getServer().getPlayer(args[0]);
					trollar.clear();

					ItemStack opFake = API.item(Material.DIAMOND, 1, "§c§lDar OP §7(Fake)");
					trollar.setItem(0, opFake);

					ItemStack jogarAlto = API.item(Material.FIREWORK, 1, "§c§lJogar para Cima");
					trollar.setItem(1, jogarAlto);

					ItemStack queimarP = API.item(Material.FIRE, 1, "§c§lQueimar");
					trollar.setItem(2, queimarP);

					ItemStack explodirP = API.item(Material.TNT, 1, "§c§lExplodir");
					trollar.setItem(3, explodirP);
					
					ItemStack drop = API.item(Material.ANVIL, 1, "§c§lBigorna");
					trollar.setItem(4, drop);
					
					ItemStack crash = API.item(Material.MAGMA_CREAM, 1, "§c§lCrashar");
					trollar.setItem(5, crash);
					
					ItemStack cage = API.item(Material.BEDROCK, 1, "§c§lCage");
					trollar.setItem(6, cage);

					p.openInventory(trollar);
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
			}
		}
		return false;
	}

	@EventHandler
	public void InvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName().equalsIgnoreCase("Trolle Hackers!")) {
			if (e.getSlot() == 1) {
				e.setCancelled(true);
				p.closeInventory();
				jogador1.setVelocity(p.getVelocity().setY(10.0D));
			} else if (e.getSlot() == 2) {
				e.setCancelled(true);
				p.closeInventory();
				jogador1.setFireTicks(24000);
			} else if (e.getSlot() == 3) {
				e.setCancelled(true);
				p.closeInventory();
				Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					
					@Override
					public void run() {
						jogador1.getWorld().createExplosion(jogador1.getLocation(), 5F);
					}
				}, 5 * 20L);
			} else if (e.getSlot() == 4) {
				e.setCancelled(true);
				p.closeInventory();
				jogador1.getLocation().getWorld().spawnFallingBlock(jogador1.getLocation().add(0, 50, 0), Material.ANVIL, (byte) 1);
				jogador1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 255), true);
				jogador1.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 255), true);
				Location l = jogador1.getLocation().getBlock().getLocation();
				l.setYaw(-90);
				l.setPitch(-90);
				jogador1.teleport(l);
				anvil2.put(jogador1.getName(), l.getBlock().getType());
				anvil.put(jogador1.getName(), l.getBlock().getLocation());
				
				Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					
					@Override
					public void run() {
						jogador1.damage(20.0D);
						anvil.get(jogador1.getName()).getBlock().setType(anvil2.get(jogador1.getName()));
						anvil.remove(jogador1.getName());
						anvil2.remove(jogador1.getName());
					}
				}, 61L);
			} else if (e.getSlot() == 0) {
				e.setCancelled(true);
				p.closeInventory();
				jogador1.sendMessage("§7[CONSOLE: Opped "
						+ jogador1.getName().toLowerCase() + "§7]");
			} else if (e.getSlot() == 5) {
				e.setCancelled(true);
				p.closeInventory();
				if(p.isOp()) {
					if(jogador1.getGameMode() == GameMode.CREATIVE) {
						jogador1.setGameMode(GameMode.SURVIVAL);
					}
					jogador1.setHealthScale(134512132.34456199113D);
				} else {
					p.sendMessage("§cApenas para OPs!");
				}
			} else if (e.getSlot() == 6) {
				e.setCancelled(true);
				p.closeInventory();
				Location b = jogador1.getLocation().getBlock().getLocation().add(0, 50, 0);
				b.getBlock().setType(Material.BEDROCK);
				b.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
				b.subtract(1, 1, 0).getBlock().setType(Material.BEDROCK);
				b.add(0, 1, 1).getBlock().setType(Material.BEDROCK);
				b.subtract(0, 1, 1).getBlock().setType(Material.BEDROCK);
				
				jogador1.teleport(b.add(0, 1, 0));
			}

		}
	}

	@EventHandler
	public void aoExplodir(EntityExplodeEvent e) {
		e.blockList().clear();
	}

}