package me.dark.Listener;

import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.MySQL.SQLStatus;
import me.dark.Utils.DarkUtils;
import me.dark.kit.KitManager;

public class GamerDeath implements Listener{
	public static HashMap<Player, Integer> killstreak = new HashMap<Player, Integer>();
	@EventHandler
	public void death(PlayerDeathEvent e) {
		final Player p = e.getEntity();
		e.setDeathMessage(null);
		GamerDamage.combatLog.remove(p);
		if (Main.estado == GameState.PREGAME) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
				public void run() {
		    		if (p.isDead()){
		    			p.spigot().respawn();
		    		}
		            
					PreGameFight.in1v1.remove(p);
					LavaChallenge.inLava.remove(p);
					GamerJoin.preInv(p);
					p.teleport(p.getWorld().getSpawnLocation());
					if (p.getKiller() instanceof Player) {
						p.getKiller().playSound(p.getKiller().getLocation(), Sound.ANVIL_LAND, 1f, 1f);
						
				        ItemStack cogu1 = new ItemStack(Material.BROWN_MUSHROOM, 16);
				        p.getKiller().getInventory().addItem(cogu1);
				        
				        ItemStack cogu2 = new ItemStack(Material.RED_MUSHROOM, 16);
				        p.getKiller().getInventory().addItem(cogu2);
					}
				}
			},1/100);
			return;
		}
		if (p.hasPermission(Main.perm_prata) && Main.gameTime <= 300) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
				public void run() {
		    		if (p.isDead()){
		    			p.spigot().respawn();
		    		}
		    		int x = 300+new Random().nextInt(120);
					int z = 300+new Random().nextInt(120);
					if (new Random().nextBoolean()) {
						x = -x;
					}
					if (new Random().nextBoolean()) {
						z = -z;
					}
					p.setCanPickupItems(false);
				    Location loc = p.getWorld().getHighestBlockAt(x, z).getLocation().clone().add(0, 4, 0);
					p.teleport(loc);
					p.setCanPickupItems(true);
					p.updateInventory();
					p.setFallDistance(0);
					p.setFoodLevel(20);
					p.setHealth(20.0D);
				}
			},1/100);
		}else if (Main.players.contains(p)) {
			Main.players.remove(p);
			SQLStatus.addDeath(p.getUniqueId());
			SQLStatus.killstreak.remove(p);
			EntityDamageEvent caus = p.getLastDamageCause();
			EntityDamageEvent.DamageCause cause = caus.getCause();
			if (cause == EntityDamageEvent.DamageCause.FALL) {
				String msg = doubleMsg("nunca ouviu falar em escadas",
						"caiu de um lugar alto");
				broadCastDeath(p, msg);
			} else if (cause == EntityDamageEvent.DamageCause.CONTACT) {
				String msg = doubleMsg("morreu por um cacto",
						"morreu por um cacto");
				broadCastDeath(p, msg);
			} else if (cause == EntityDamageEvent.DamageCause.DROWNING) {
				String msg = doubleMsg("morreu afogado",
						"morreu afogado");
				broadCastDeath(p, msg);
			} else if (cause == EntityDamageEvent.DamageCause.LAVA) {
				String msg = doubleMsg("tentou nadar na lava",
						"estava brincando muito perto da lava");
				broadCastDeath(p, msg);
			} else if (cause == EntityDamageEvent.DamageCause.SUFFOCATION) {
				broadCastDeath(p, "morreu sufocado");
			} else if (cause == EntityDamageEvent.DamageCause.FIRE) {
				broadCastDeath(p, "tostou como uma carne assada");
			} else if (cause == EntityDamageEvent.DamageCause.POISON) {
				broadCastDeath(p, "morreu envenenado");
			} else if (cause == EntityDamageEvent.DamageCause.FIRE_TICK) {
				broadCastDeath(p, "queimado");
			} else if (cause == EntityDamageEvent.DamageCause.LIGHTNING) {
				broadCastDeath(p, "morreu por raios");
			} else if (cause == EntityDamageEvent.DamageCause.STARVATION) {
				broadCastDeath(p, "morreu de fome");
			} else if (cause == EntityDamageEvent.DamageCause.WITHER) {
				broadCastDeath(p, "morreu em envenenado");
			} else if (cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
				broadCastDeath(p, "morreu explodido");
			} else if (cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
				broadCastDeath(p, "morreu explodido");
			} else if (cause == EntityDamageEvent.DamageCause.CUSTOM) {
				broadCastDeath(p, "morreu pela borda do mundo");
			} else if (caus.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
				Entity entity = ((EntityDamageByEntityEvent) caus)
						.getDamager();
				if (entity.getType().equals(EntityType.PLAYER)) {
					Player killer = (Player) entity;
					SQLStatus.addKillstreak(killer);
					SQLStatus.addKill(killer.getUniqueId());
					SQLStatus.addCoins(killer.getUniqueId(), 100);
					String kill;
					if (!KitManager.hasAnyKit(killer)) {
						kill = killer.getName() + "§7(Nenhum)§3";
					} else {
						kill = killer.getName() + "§7("+KitManager.getKit(killer).toString()+")";	
						
					}
					String id = killer.getItemInHand().getType().name();
					broadCastDeath(p, "foi morto por " +kill + " usando "
							+ getItem(id));
				} else {
					String msg = doubleMsg("morreu por um monstro",
							"estava brincando com um monstro");
					broadCastDeath(p, msg);
				}
			} else {
				broadCastDeath(p, "morreu!");
			}
		}
	}
	
	public static void broadCastDeath(final Player p, String msg) {
		String message;
		if (!KitManager.hasAnyKit(p)) {
			message = "§3"+p.getName() + "§7(Nenhum)§3";
		} else {
			message = "§3"+p.getName() + "§7("+KitManager.getKit(p).toString()+")§3";	
			
		}
		Bukkit.broadcastMessage(message + " " + msg);
		KitManager.removeKit(p);
		
		if (p.hasPermission(Main.perm_mod)) {
			new BukkitRunnable() {
				public void run() {
					if (p.isOnline()) {
						p.chat("/admin");	
					}
				}
			}.runTask(Main.getMain());
		} else {
			if (p.isOnline()) {
				p.sendMessage(message + " " + msg + "\n§7Visite "+Main.site);	
				DarkUtils.sendToServer(p, "Lobby");	
			}
		}
	}
	
	public String doubleMsg(String msg, String msg2) {
		if (new Random().nextBoolean()) {
			return msg;
		} else {
			return msg2;
		}
	}
	
	public static String getItem(String name) {
		String prefix = "";
		String Name = "";
		if (name.equals("AIR")) {
			prefix = "seus";
			Name = "Punhos";
		} else if (name.equals("DIRT")) {
			prefix = "sua";
			Name = "Terra";
		} else if (name.equals("IRON_AXE")) {
			prefix = "seu";
			Name = "Machado de Ferro";
		} else if (name.equals("FLINT_AND_STEEL")) {
			prefix = "seu";
			Name = "Isqueiro";
		} else if (name.equals("BOW")) {
			prefix = "seu";
			Name = "Arco de flechas";
		} else if (name.equals("IRON_SWORD")) {
			prefix = "sua";
			Name = "Espada de Ferro";
		} else if (name.equals("WOOD_SWORD")) {
			prefix = "sua";
			Name = "Espada de Madeira";
		} else if (name.equals("WOOD_AXE")) {
			prefix = "seu";
			Name = "Machado de Madeira";
		} else if (name.equals("STONE_SWORD")) {
			prefix = "sua";
			Name = "Espada de Pedra";
		} else if (name.equals("STONE_AXE")) {
			prefix = "seu";
			Name = "Machado de Pedra";
		} else if (name.equals("DIAMOND_SWORD")) {
			prefix = "sua";
			Name = "Espada de Diamante";
		} else if (name.equals("DIAMOND_AXE")) {
			prefix = "seu";
			Name = "Machado de Diamante";
		} else if (name.equals("BOWL")) {
			prefix = "seu";
			Name = "Pote";
		} else if (name.equals("MUSHROOM_SOUP")) {
			prefix = "sua";
			Name = "Sopa";
		} else if (name.equals("GOLD_SWORD")) {
			prefix = "sua";
			Name = "Espada de Ouro";
		} else if (name.equals("GOLD_AXE")) {
			prefix = "seu";
			Name = "Machado de Ouro";
		} else if (name.equals("COMPASS")) {
			prefix = "sua";
			Name = "Bússola";
		} else {
			prefix = "sua";
			Name = toReadable(name);
		}
		return prefix + " " + Name;
	}
	
	public static String toReadable(String string) {
		String[] names = string.split("_");
		for (int i = 0; i < names.length; i++) {
			names[i] = (names[i].substring(0, 1) + names[i].substring(1)
					.toLowerCase());
		}
		return StringUtils.join(names, " ");
	}
	
	@EventHandler
	public void respawn(PlayerRespawnEvent e) {
		if (!Main.players.contains(e.getPlayer())) {
			e.setRespawnLocation(e.getPlayer().getLocation());	
			if (e.getPlayer().getAllowFlight()) {
				e.getPlayer().setFlying(true);
			}
		}
	}

}
