package me.everton.hg;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import me.everton.hg.Cmds.Admin;
import me.everton.hg.Cmds.ChatStaff;
import me.everton.hg.Cmds.Gerais;
import me.everton.hg.Cmds.Tag;
import me.everton.hg.Cmds.TesteHacks;
import me.everton.hg.Cmds.Uteis;
import me.everton.hg.jogo.ListenersPrincipais;
import me.everton.hg.jogo.Timers.TimerPreJogo;
import me.everton.hg.kits.KitCmd;
import me.everton.hg.kits.KitSelector;
import me.everton.hg.kits.KitType;
import me.everton.hg.kits.habilidades.C4;
import me.everton.hg.kits.habilidades.Deshfire;
import me.everton.hg.kits.habilidades.Endermage;
import me.everton.hg.kits.habilidades.Forcefield;
import me.everton.hg.kits.habilidades.Kangaroo;
import me.everton.hg.kits.habilidades.Stomper;
import me.everton.hg.kits.habilidades.Titan;
import me.everton.hg.kits.habilidades.Worm;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
	public static Plugin plugin;

	public static Boolean EM_JOGO = false;
	public static Boolean INVENCIBILIDADE = false;
	public static Boolean TRANSICAO = false;
	public static Boolean PRE_JOGO = false;
	public static Boolean STATE_KITS = true;
	public static int TEMPO_JOGO = 0;
	public static int TEMPO_PREJOGO = 180;
	public static int faseScoreBoard = 0;
	public static int TEMPO_INVENCIBILIDADE = 120;
	public static int MINIMO_PLAYERS = 5;
	public static HashMap<String, Integer> kills = new HashMap<>();
	public static Location spawn = null;
	public static Scoreboard Scoreboard = null;

	public static BukkitScheduler sh = Bukkit.getServer().getScheduler();

	@SuppressWarnings("deprecation")
	public static Player[] onlines = Bukkit.getOnlinePlayers();
	public static ArrayList<String> participando = new ArrayList<>();

	public static HashMap<String, KitType> kit = new HashMap<>();

	public static Plugin getPlugin() {
		return plugin;
	}

	@SuppressWarnings("deprecation")
	public void onEnable() {
		plugin = this;

		registerCmds();
		registerEvents();

		getServer().getMessenger().registerOutgoingPluginChannel(this,
				"BungeeCord");
		Bukkit.getConsoleSender().sendMessage(
				"§7[§6§l!!!§7] §4WoCHG Habilitado");
		API.addRecipes();
		Scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

		Random r = new Random();

		int x = r.nextInt(70);
		int z = r.nextInt(70);
		int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z);
		Location random = new Location(Bukkit.getWorld("world"), x, Bukkit
				.getWorld("world").getHighestBlockYAt(
						new Location(Bukkit.getWorld("world"), x, y, z)), z);
		spawn = random;
		for (Player on : Bukkit.getOnlinePlayers()) {
			if (!participando.contains(on.getName())) {
				participando.add(on.getName());
			}
			on.setWalkSpeed(0.2F);
			on.teleport(spawn);
			kills.put(on.getName(), 0);
			kit.put(on.getName(), KitType.NONE);
			on.setScoreboard(Scoreboard);
		}

		Location loc = randomLocation(spawn.getChunk()).add(0.0D, 30.0D, 0.0D);
		Location loc2 = randomLocation(spawn.getChunk()).add(0.0D, 30.0D, 0.0D)
				.subtract(1000, 0, 1000);
		spawn.getWorld().loadChunk(loc.getChunk());
		spawn.getWorld().loadChunk(loc2.getChunk());

		TimerPreJogo.contagem();

		sh.scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					String header = "";
					header += "§b§m--(-------------------)--";
					header += "\n§rBem-Vindo à §6§lWoC§b§lNetwork§r!\n";

					String footer = "";
					footer += "\n§fVocê está no §6§lLobby§f!";
					footer += "\n§fIP: §6§ljogar.wocpvp.com";
					footer += "\n§fPing: §6§l"
							+ ((CraftPlayer) p).getHandle().ping;
					footer += "\n§b§m--(-------------------)--";
					API.setHeaderFooter(p, header, footer);
				}
			}
		}, 0L, 5L);
	}

	private void registerCmds() {
		getCommand("aplicacao").setExecutor(new Uteis());
		getCommand("autosoup").setExecutor(new TesteHacks());
		getCommand("antikb").setExecutor(new TesteHacks());
		getCommand("ac").setExecutor(new ChatStaff());
		getCommand("sc").setExecutor(new ChatStaff());
		getCommand("spawn").setExecutor(this);
		getCommand("start").setExecutor(new Gerais());
		getCommand("specs").setExecutor(new Gerais());
		getCommand("togglekits").setExecutor(new Gerais());
		getCommand("admin").setExecutor(new Admin());
		getCommand("adm").setExecutor(new Admin());
		getCommand("invis").setExecutor(new Admin());
		getCommand("info").setExecutor(new Gerais());
		getCommand("tp").setExecutor(new Gerais());
		getCommand("skit").setExecutor(new Gerais());
		getCommand("tpall").setExecutor(new Gerais());
		getCommand("help").setExecutor(new Gerais());
		getCommand("jogo").setExecutor(new Gerais());
		getCommand("tempo").setExecutor(new Gerais());
		getCommand("arena").setExecutor(new Gerais());
		getCommand("report").setExecutor(new Gerais());
		getCommand("cc").setExecutor(new Gerais());
		getCommand("ffeast").setExecutor(new Gerais());
		getCommand("tpfeast").setExecutor(new Gerais());
		getCommand("feast").setExecutor(new Gerais());
		getCommand("tag").setExecutor(new Tag());
		getCommand("kit").setExecutor(new KitCmd());
		getCommand("kits").setExecutor(new KitCmd());
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new Tag(), this);
		Bukkit.getPluginManager().registerEvents(new ChatStaff(), this);
		Bukkit.getPluginManager().registerEvents(new ListenersPrincipais(),
				this);
		Bukkit.getPluginManager().registerEvents(new Uteis(), this);
		Bukkit.getPluginManager().registerEvents(new KitSelector(), this);
		Bukkit.getPluginManager().registerEvents(new Admin(), this);

		Bukkit.getPluginManager().registerEvents(new Stomper(), this);
		Bukkit.getPluginManager().registerEvents(new Worm(), this);
		Bukkit.getPluginManager().registerEvents(new Titan(), this);
		Bukkit.getPluginManager().registerEvents(new Deshfire(), this);
		Bukkit.getPluginManager().registerEvents(new C4(), this);
		Bukkit.getPluginManager().registerEvents(new Kangaroo(), this);
		Bukkit.getPluginManager().registerEvents(new Endermage(), this);
		Bukkit.getPluginManager().registerEvents(new Forcefield(), this);
	}

	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				deleteDir(new File(dir, children[i]));
			}
		}
		dir.delete();
	}

	public static void abrirGuiSpectador(Player p, Player[] players, int rows,
			Boolean showadmin) {
		ItemStack i = new ItemStack(Material.SKULL_ITEM, 1,
				(short) SkullType.PLAYER.ordinal());
		SkullMeta m = (SkullMeta) i.getItemMeta();
		Inventory spectate = Bukkit.createInventory(p, rows * 9,
				"§4§lJogadores Onlines");
		int adminc = 0;
		for (int z = 0; z < players.length; z++) {
			if (Admin.admins.contains(players[z].getName())) {
				adminc++;
			}
			if (!(players.length > 53)) {
				m.setDisplayName(players[z].getDisplayName());
				m.setOwner(players[z].getName());
				i.setItemMeta(m);
				if (!showadmin) {
					if (!Admin.admins.contains(players[z].getName())) {
						spectate.setItem(z - adminc, i);
					}
				} else {
					spectate.setItem(z, i);
				}
				p.openInventory(spectate);
			} else if ((players.length > 53)) {
				p.sendMessage("§aSuportando por enquanto apenas 53 jogadores onlines!");
			}
		}
	}

	public static void sendServer(Player p, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch (Exception e) {
			e.printStackTrace();
		}
		p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
	}

	public static Location randomLocation(Chunk c) {
		Random random = new Random();
		Location startFrom = spawn;
		Location loc = startFrom.clone();
		loc.add((random.nextBoolean() ? 1 : -1) * random.nextInt(500), 60.0D,
				(random.nextBoolean() ? 1 : -1) * random.nextInt(500));
		int newY = ((World) Bukkit.getWorlds().get(0)).getHighestBlockYAt(loc);
		loc.setY(newY);
		return loc;
	}

	@SuppressWarnings("deprecation")
	public static void broadCast(String msg) {
		if (Bukkit.getOnlinePlayers().length >= 1) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				on.sendMessage(msg);
			}
		}
		Bukkit.getConsoleSender().sendMessage(msg);
	}

	public static String secToMin(int i) {
		if (i >= 86400) {
			int d = (int) TimeUnit.SECONDS.toDays(i);
			int h = (int) (TimeUnit.SECONDS.toHours(i) - (d * 24));
			int m = (int) (TimeUnit.SECONDS.toMinutes(i) - (TimeUnit.SECONDS
					.toHours(i) * 60));
			int s = (int) (TimeUnit.SECONDS.toSeconds(i) - (TimeUnit.SECONDS
					.toMinutes(i) * 60));
			return doisDigitos(d) + ":" + doisDigitos(h) + ":" + doisDigitos(m)
					+ ":" + doisDigitos(s);
		} else if (i >= 3600) {
			int h = (int) TimeUnit.SECONDS.toHours(i);
			int m = (int) (TimeUnit.SECONDS.toMinutes(i) - (TimeUnit.SECONDS
					.toHours(i) * 60));
			int s = (int) (TimeUnit.SECONDS.toSeconds(i) - (TimeUnit.SECONDS
					.toMinutes(i) * 60));
			return doisDigitos(h) + ":" + doisDigitos(m) + ":" + doisDigitos(s);
		} else {
			int m = (int) TimeUnit.SECONDS.toMinutes(i);
			int s = i - (m * 60);
			return doisDigitos(m) + ":" + doisDigitos(s);
		}
	}

	public static String secToMinSec(int i) {
		if (i >= 86400) {
			int d = (int) TimeUnit.SECONDS.toDays(i);
			int h = (int) (TimeUnit.SECONDS.toHours(i) - (d * 24));
			int m = (int) (TimeUnit.SECONDS.toMinutes(i) - (TimeUnit.SECONDS
					.toHours(i) * 60));
			int s = (int) (TimeUnit.SECONDS.toSeconds(i) - (TimeUnit.SECONDS
					.toMinutes(i) * 60));
			return doisDigitos(d) + " dia(s), " + doisDigitos(h) + " hora(s), "
					+ doisDigitos(m) + " minuto(s) e " + doisDigitos(s)
					+ " segundo(s)";
		} else if (i >= 3600) {
			int h = (int) TimeUnit.SECONDS.toHours(i);
			int m = (int) (TimeUnit.SECONDS.toMinutes(i) - (TimeUnit.SECONDS
					.toHours(i) * 60));
			int s = (int) (TimeUnit.SECONDS.toSeconds(i) - (TimeUnit.SECONDS
					.toMinutes(i) * 60));

			return doisDigitos(h) + " hora(s), " + doisDigitos(m)
					+ " minuto(s) e " + doisDigitos(s) + " segundo(s)";
		} else {
			int m = (int) TimeUnit.SECONDS.toMinutes(i);
			int s = i - (m * 60);
			if ((m == 0) && (s != 0)) {
				if (s > 1) {
					return doisDigitos(s) + " segundos";
				} else {
					return doisDigitos(s) + " segundo";
				}
			} else if ((m != 0) && (s == 0)) {
				if (m > 1) {
					return doisDigitos(m) + " minutos";
				} else {
					return doisDigitos(m) + " minuto";
				}
			} else {
				if ((s > 1) && (m > 1)) {
					return doisDigitos(m) + " minutos e " + s + " segundos";
				} else if ((s > 1) && (!(m > 1))) {
					return doisDigitos(m) + " minuto e " + s + " segundos";
				} else if ((m > 1) && (!(s > 1))) {
					return doisDigitos(m) + " minutos e " + s + " segundo";
				}
			}
			return doisDigitos(s) + " minutos e " + s + " segundos";
		}
	}

	public static String doisDigitos(int number) {

		if (number == 0) {
			return "00";
		}

		if (number / 10 == 0) {
			return "0" + number;
		}

		return String.valueOf(number);
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§7[§c!§7] WoCHG Desabilitado");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§7[§c!§7] Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("spawn")) {
			Player p = (Player) sender;
			if ((PRE_JOGO) && (!p.hasPermission("hg.admin"))) {
				p.teleport(spawn);
				p.sendMessage("§7[§c!§7] Teleportado ao spawn!");
			} else if (p.hasPermission("hg.admin")) {
				p.teleport(spawn);
				p.sendMessage("§7[§c!§7] Teleportado ao spawn!");
			} else {
				p.sendMessage("§7[§c!§7] Desculpe, mas este comando pode ser usado apenas antes do inicio do jogo!");
			}
		}
		return false;
	}

	@EventHandler
	public void aoChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String jogador = p.getDisplayName();
		if (e.getMessage().contains("%")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(
					"§7[§c!§7] Por favor, não use o símbolo %!");
		} else {
			char first = e.getMessage().charAt(0);
			String primeiro = first + "";
			String msg = primeiro.toUpperCase()
					+ e.getMessage().substring(1, e.getMessage().length())
							.toLowerCase();
			if (p.hasPermission("wochg.admin")) {

				e.setFormat(jogador + "§6§l >> §r§f"
						+ e.getMessage().replace("&", "§"));
			} else {
				e.setFormat(jogador + "§6§l >> §r§f" + msg);
			}
		}
	}

	public static void verInv(Player p, Player de) {
		PlayerInventory inv = de.getInventory();
		p.openInventory(inv);
	}

	public static Main getMain() {
		return (Main) Bukkit.getServer().getPluginManager().getPlugin("Main");
	}

}
