package me.dark.Game.Timer;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.dark.Main;
import me.dark.Game.Feast;
import me.dark.Game.GameState;
import me.dark.Game.Minifeast;
import me.dark.Listener.LavaChallenge;
import me.dark.Listener.PreGameFight;
import me.dark.MySQL.SQLStatus;
import me.dark.Title.TitleAPI;
import me.dark.Utils.DarkUtils;
import me.dark.Utils.Schematic;
import me.dark.kit.Kit;
import me.dark.kit.KitManager;

public class GameTimer {
	
	public static BukkitTask preGameTask;
	public static BukkitTask gameTask;
	
	public static void preGame() {
		preGameTask = new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				if (Main.estado != GameState.PREGAME) {
					preGameTask = null;
					cancel();
				}else if (Main.players.size() < 4) {
					Main.toStart = 300;
					return;
				}else if (Main.players.size() >= Bukkit.getMaxPlayers()-5) {
					if (Main.toStart > 60) {
						Main.toStart = 45;
						Bukkit.broadcastMessage("§3Servidor lotado, tempo para iniciar diminuido!");
					}
				}else if (Main.toStart == 300) {
					broadCastStartingMSG("minutos", 5);
				}else if (Main.toStart == 240) {
					broadCastStartingMSG("minutos", 4);
				}else if (Main.toStart == 180) {
					broadCastStartingMSG("minutos", 3);
				}else if (Main.toStart == 120) {
					broadCastStartingMSG("minutos", 2);
				}else if (Main.toStart == 60) {
					broadCastStartingMSG("minuto", 1);
				}else if (Main.toStart == 30) {
					broadCastStartingMSG("segundos", 30);
				}else if (Main.toStart == 15) {
					broadCastStartingMSG("segundos", 15);
					for (Player p : Bukkit.getOnlinePlayers()) {
						TitleAPI.sFullTitulo(p, 20, 20, 20, "§bGoodCraft §3HG", "§fIniciando em 15 segundos!");
					}
		            playSound();
				}else if (Main.toStart == 10) {
					broadCastStartingMSG("segundos", 10);
					for (Player p : Bukkit.getOnlinePlayers()) {
						TitleAPI.sFullTitulo(p, 20, 20, 20, "§bGoodCraft §3HG", "§fIniciando em 10 segundos!");
					}
		            playSound();
				}else if (Main.toStart == 5) {
					broadCastStartingMSG("segundos", 5);
					for (Player p : Bukkit.getOnlinePlayers()) {
						TitleAPI.sFullTitulo(p, 20,20,20, "§bGoodCraft §3HG", "§cIniciando em 5 segundos!");
					}
		            playSound();
				}else if (Main.toStart == 4) {
					broadCastStartingMSG("segundos", 4);
					for (Player p : Bukkit.getOnlinePlayers()) {
						TitleAPI.sFullTitulo(p, 20,20,20, "§bGoodCraft §3HG", "§cIniciando em 4 segundos!");
					}
		            playSound();
				}else if (Main.toStart == 3) {
					broadCastStartingMSG("segundos", 3);
					for (Player p : Bukkit.getOnlinePlayers()) {
						TitleAPI.sFullTitulo(p, 20,20,20, "§bGoodCraft §3HG", "§cIniciando em 3 segundos!");
					}
		            playSound();
				}else if (Main.toStart == 2) {
					broadCastStartingMSG("segundos", 2);
					for (Player p : Bukkit.getOnlinePlayers()) {
						TitleAPI.sFullTitulo(p, 20,20,20, "§bGoodCraft §3HG", "§cIniciando em 2 segundos!");
					}
		            playSound();
				}else if (Main.toStart == 1) {
					broadCastStartingMSG("segundo", 1);
					for (Player p : Bukkit.getOnlinePlayers()) {
						TitleAPI.sFullTitulo(p, 20,20,20, "§bGoodCraft §3HG", "§cIniciando em 1 segundo!");
					}
		            playSound();
				}else if (Main.toStart == 0) {
					Main.toStart = 61;
					if (Main.players.size() < 4) {
						for (Player p : Main.players) {
							p.sendMessage("§3É preciso de §34 jogadores§b para a partida iniciar!");
						}
					} else {
						start();
					}
					
				}
				Main.usingWorld.setTime(0);
				Main.toStart--;
				
			}
		}.runTaskTimer(Main.getMain(), 20, 20);
		
	}
	
	@SuppressWarnings("deprecation")
	public static void playSound() {
		for (Player p : Bukkit.getOnlinePlayers()) {
	        p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.0F);	
		}
	}
	
	public static void start() {
		Main.estado = GameState.INVENCIBILITY;
		for (Player p : PreGameFight.in1v1) {
			p.closeInventory();
			p.getInventory().clear();
			p.setHealth(20D);
			p.setFoodLevel(20);
		}
		for (Player p : LavaChallenge.inLava) {
			p.closeInventory();
			p.getInventory().clear();
			p.setHealth(20D);
			p.setFoodLevel(20);
		}
		for (Entity en : Main.usingWorld.getEntities()) {
			if (en instanceof Villager) {
				en.remove();
			}
		}
		PreGameFight.in1v1.clear();
		LavaChallenge.inLava.clear();
		for (Kit k : Kit.kits) {
			k.onStartGame();
		}
		DarkUtils.clear();
		preGameTask.cancel();
		preGameTask = null;
		Bukkit.broadcastMessage("§3O jogo iniciou, que vença o melhor!");
		Main.usingWorld.setTime(0);
		Main.usingWorld.setThundering(false);
		Main.usingWorld.setStorm(false);
		if (preGameTask != null) {
			preGameTask.cancel();
		}
		for (Player p : Main.players) {
			p.closeInventory();
            p.getInventory().clear();
            p.playSound(p.getLocation(), Sound.ANVIL_LAND, 10.0F, 1.0F);
            p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 10.0F, 1.0F);
            p.playSound(p.getLocation(), Sound.PISTON_RETRACT, 10.0F, 1.0F);
            p.setGameMode(GameMode.SURVIVAL);
            p.setAllowFlight(false);
			p.setWalkSpeed(0.2f);
			p.setFireTicks(0);
            Kit k = KitManager.getKit(p);
            if (k != null) {
            	if (k.getItems() != null) {
            		for (ItemStack items : k.getItems()) {
            			if (items != null) {
            				p.getInventory().addItem(items);
            			}
            		}
            	}
            }
            p.getInventory().addItem(new ItemStack(Material.COMPASS));
		}
		game();
	}
	
	@SuppressWarnings("deprecation")
	public static void check(){
		if (Main.players.size() == 1) {
			Main.estado = GameState.WIN;
			GameTimer.gameTask.cancel();
			final Player p = Main.players.get(0);
			SQLStatus.addWin(p.getUniqueId());
			SQLStatus.addCoins(p.getUniqueId(), 1000);
			p.getWorld().setTime(19000L);
			if (!p.isOnline()) {
				for (Player other : Bukkit.getOnlinePlayers()) {
					other.kickPlayer("§3"+p.getName()+" ganhou!\n§7Visite "+Main.site);
				}
				return;
			}
			p.getInventory().clear();
			p.getInventory().setItem(4, DarkUtils.create_item(Material.WATER_BUCKET, "§3MLG", 1, 0, null));
			new BukkitRunnable() {
				public void run() {
					if (p.isOnline()) {
						p.setVelocity(p.getVelocity().multiply(0).setY(4));	
					} else {
						for (Player other : Bukkit.getOnlinePlayers()) {
							other.kickPlayer("§3"+p.getName()+" ganhou!\n§7Visite "+Main.site);
						}
						Bukkit.shutdown();
					}
					
				}
			}.runTaskLater(Main.getMain(), 1);
			Location loc = p.getLocation();
			loc.setY(160);
			Schematic.spawn(loc, "Bolo");
			p.teleport(loc.add(0,3,0));
			p.sendMessage(" ");
			p.sendMessage(Main.linha);
			p.sendMessage("§3Você ganhou!");
			p.sendMessage("§bTempo: §f"+DarkUtils.tempoInt(Main.gameTime));
			String kit = "Nenhum";
			if (KitManager.getKit(p) != null){
				kit = KitManager.getKit(p).toString();
				
			}
			p.sendMessage("§bKit: §f"+kit);
			p.sendMessage("§bKills: §f"+SQLStatus.getKillstreak(p));
			p.sendMessage(Main.linha);
			p.sendMessage(" ");
			
			new BukkitRunnable() {
				public void run() {
					if (p.isOnline()) {
						DarkUtils.FireWork(p.getLocation(), Color.AQUA);	
					} else {
						for (Player other : Bukkit.getOnlinePlayers()) {
							other.sendMessage("§3"+p.getName()+" ganhou!\n§7Visite "+Main.site);
							DarkUtils.sendToServer(p, "Lobby");
						}
						Bukkit.shutdown();
					}
				}
			}.runTaskTimer(Main.getMain(), 20, 20);
			
			new BukkitRunnable() {
				public void run() {
					if (p.isOnline()) {
						p.sendMessage("§3Você ganhou!\n \n§7Visite "+Main.site);
						DarkUtils.sendToServer(p, "Lobby");
					}
					for (Player other : Bukkit.getOnlinePlayers()) {
						if (other != p) {
							other.sendMessage("§3"+p.getName()+" ganhou!\n§7Visite "+Main.site);
							DarkUtils.sendToServer(p, "Lobby");	
						}
					}
					Bukkit.shutdown();
				}
			}.runTaskLater(Main.getMain(), 20*20);
			
		}
	}
	
	public static void game() {
		gameTask = new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				if (Main.gameTime == 0) {
					broadCastStartingMSGInv("minutos", 2);
				} else if (Main.gameTime == 60) {
					broadCastStartingMSGInv("minuto", 1);
				} else if (Main.gameTime == 90) {
					broadCastStartingMSGInv("segundos", 30);
				} else if (Main.gameTime == 105) {
					broadCastStartingMSGInv("segundos", 15);
					playSound();
				} else if (Main.gameTime == 110) {
					broadCastStartingMSGInv("segundos", 10);
					playSound();
				} else if (Main.gameTime == 115) {
					broadCastStartingMSGInv("segundos", 5);
					playSound();
				}else if (Main.gameTime == 116) {
					broadCastStartingMSGInv("segundos", 4);
					playSound();
				}else if (Main.gameTime == 117) {
					broadCastStartingMSGInv("segundos", 3);
					playSound();
				}else if (Main.gameTime == 118) {
					broadCastStartingMSGInv("segundos", 2);
					playSound();
				}else if (Main.gameTime == 119) {
					broadCastStartingMSGInv("segundos", 1);
					playSound();
				}else if (Main.gameTime == 120) {
					for (Player p : Main.players) {
			            p.playSound(p.getLocation(), Sound.ANVIL_LAND, 10.0F, 1.0F);
			            p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 10.0F, 1.0F);
			            p.playSound(p.getLocation(), Sound.PISTON_RETRACT, 10.0F, 1.0F);
					}
					Main.estado = GameState.STARTED;
					Bukkit.broadcastMessage("§4A invencibilidade acabou!");
				}else if (Main.gameTime == 900) {
					int z = 33+new Random().nextInt(100);
					int x = 33+new Random().nextInt(100);
					if (new Random().nextBoolean()) {
						z = -z;
					}
					if (new Random().nextBoolean()) {
						x = -x;
					}
					int y = Main.usingWorld.getHighestBlockYAt(z,x)+1;
					Feast.spawnFeast(new Location(Main.usingWorld, x, y, z));
					Feast.feastLoc = new Location(Main.usingWorld, x, y, z);
					Bukkit.broadcastMessage("§cFeast spawnando em §45 minutos§c em ["+Feast.feastLoc.getBlockX()+ ", "+Feast.feastLoc.getBlockY()+", "+Feast.feastLoc.getBlockZ()+"]");
				}else if (Main.gameTime == 960) {
					Bukkit.broadcastMessage("§cFeast spawnando em §44 minutos§c em ["+Feast.feastLoc.getBlockX()+ ", "+Feast.feastLoc.getBlockY()+", "+Feast.feastLoc.getBlockZ()+"]");
				}else if (Main.gameTime == 1020) {
					Bukkit.broadcastMessage("§cFeast spawnando em §43 minutos§c em ["+Feast.feastLoc.getBlockX()+ ", "+Feast.feastLoc.getBlockY()+", "+Feast.feastLoc.getBlockZ()+"]");
				}else if (Main.gameTime == 1080) {
					Bukkit.broadcastMessage("§cFeast spawnando em §42 minutos§c em ["+Feast.feastLoc.getBlockX()+ ", "+Feast.feastLoc.getBlockY()+", "+Feast.feastLoc.getBlockZ()+"]");
				}else if (Main.gameTime == 1140) {
					Bukkit.broadcastMessage("§cFeast spawnando em §41 minuto§c em ["+Feast.feastLoc.getBlockX()+ ", "+Feast.feastLoc.getBlockY()+", "+Feast.feastLoc.getBlockZ()+"]");
				}else if (Main.gameTime == 1200) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(Feast.feastLoc, Sound.AMBIENCE_THUNDER, 1f, 1f);
					}
					Bukkit.broadcastMessage("§4Feast acabou de spawnar em ["+Feast.feastLoc.getBlockX()+ ", "+Feast.feastLoc.getBlockY()+", "+Feast.feastLoc.getBlockZ() + "]");
					Feast.spawnChests(Feast.feastLoc.add(0,1,0));
				}else if (Main.gameTime == 300) {
					int z = 30+new Random().nextInt(400);
					int x = 30+new Random().nextInt(400);
					if (new Random().nextBoolean()) {
						z = -z;
					}
					if (new Random().nextBoolean()) {
						x = -x;
					}
					int y = Main.usingWorld.getHighestBlockYAt(x, z);
					Minifeast.spawnMinifeast(new Location(Main.usingWorld, x, y, z));
					
					int toBroadX = x+new Random().nextInt(100);
					int toBroadX2 = x-new Random().nextInt(100);
					int toBroadZ = z+new Random().nextInt(100);
					int toBroadZ2 = z-new Random().nextInt(100);
					
					Bukkit.broadcastMessage("§bUm mini feast spawnou entre §7(X: "
							+ toBroadX + " e "
							+ toBroadX2 + ") e (Z: "
							+ toBroadZ + " e "
							+ toBroadZ2 + ")");

				
				}else if (Main.gameTime == 500) {
					int z = 30+new Random().nextInt(400);
					int x = 30+new Random().nextInt(400);
					if (new Random().nextBoolean()) {
						z = -z;
					}
					if (new Random().nextBoolean()) {
						x = -x;
					}
					int y = Main.usingWorld.getHighestBlockYAt(x, z);
					Minifeast.spawnMinifeast(new Location(Main.usingWorld, x, y, z));
					
					int toBroadX = x+new Random().nextInt(100);
					int toBroadX2 = x-new Random().nextInt(100);
					int toBroadZ = z+new Random().nextInt(100);
					int toBroadZ2 = z-new Random().nextInt(100);
					
					Bukkit.broadcastMessage("§bUm mini feast spawnou entre §7(X: "
							+ toBroadX + " e "
							+ toBroadX2 + ") e (Z: "
							+ toBroadZ + " e "
							+ toBroadZ2 + ")");

				
				}else if (Main.gameTime == 700) {
					int z = 30+new Random().nextInt(400);
					int x = 30+new Random().nextInt(400);
					if (new Random().nextBoolean()) {
						z = -z;
					}
					if (new Random().nextBoolean()) {
						x = -x;
					}
					int y = Main.usingWorld.getHighestBlockYAt(x, z);
					Minifeast.spawnMinifeast(new Location(Main.usingWorld, x, y, z));
					
					int toBroadX = x+new Random().nextInt(100);
					int toBroadX2 = x-new Random().nextInt(100);
					int toBroadZ = z+new Random().nextInt(100);
					int toBroadZ2 = z-new Random().nextInt(100);
					
					Bukkit.broadcastMessage("§bUm mini feast spawnou entre §7(X: "
							+ toBroadX + " e "
							+ toBroadX2 + ") e (Z: "
							+ toBroadZ + " e "
							+ toBroadZ2 + ")");

				
				}
				
				check();
				Main.gameTime++;
				if (Main.gameTime >= 900 && Main.gameTime <= 1200) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						TitleAPI.sendActionBar(p, "§3Feast: §b"+DarkUtils.tempoInt(1200-Main.gameTime));
					}
				}
				if (Main.estado == GameState.INVENCIBILITY) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						TitleAPI.sendActionBar(p, "§3Invencibilidade: §b"+DarkUtils.tempoInt(120-Main.gameTime));
					}
				}
			}
		}.runTaskTimer(Main.getMain(), 20, 20);
	}
	
	public static void broadCastStartingMSG(String time, int tempo) {
		Bukkit.broadcastMessage("§bA partida vai iniciar em §3" + tempo + " " + time + "§b!");
	}
	public static void broadCastStartingMSGInv(String time, int tempo) {
		Bukkit.broadcastMessage("§cA invencibilidade acaba em §4" + tempo + " " + time + "§c!");
	}

}
