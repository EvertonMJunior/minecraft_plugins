package me.everton.WocPvP;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import me.everton.WocPvP.Comandos.Admin;
import me.everton.WocPvP.Comandos.AnunciarVip;
import me.everton.WocPvP.Comandos.AutoSoup;
import me.everton.WocPvP.Comandos.Broadcast;
import me.everton.WocPvP.Comandos.Chat;
import me.everton.WocPvP.Comandos.Construir;
import me.everton.WocPvP.Comandos.Duvida;
import me.everton.WocPvP.Comandos.Fake;
import me.everton.WocPvP.Comandos.FakeOff;
import me.everton.WocPvP.Comandos.Fakes;
import me.everton.WocPvP.Comandos.Fps;
import me.everton.WocPvP.Comandos.Hack;
import me.everton.WocPvP.Comandos.Infos;
import me.everton.WocPvP.Comandos.Kit;
import me.everton.WocPvP.Comandos.KitCmd;
import me.everton.WocPvP.Comandos.Loja;
import me.everton.WocPvP.Comandos.Mlg;
import me.everton.WocPvP.Comandos.Money;
import me.everton.WocPvP.Comandos.Sopa;
import me.everton.WocPvP.Comandos.Spec;
import me.everton.WocPvP.Comandos.Specs;
import me.everton.WocPvP.Comandos.StaffChat;
import me.everton.WocPvP.Comandos.Tag;
import me.everton.WocPvP.Comandos.Tell;
import me.everton.WocPvP.Comandos.Tp;
import me.everton.WocPvP.Comandos.Trolar;
import me.everton.WocPvP.Comandos.Uteis;
import me.everton.WocPvP.Comandos.Warp;
import me.everton.WocPvP.Eventos.EManager;
import me.everton.WocPvP.Eventos.EventoCmd;
import me.everton.WocPvP.Eventos.Placas;
import me.everton.WocPvP.HG.HG;
import me.everton.WocPvP.Kits.Habilidades.Endermage;
import me.everton.WocPvP.Kits.Habilidades.Gladiator;
import me.everton.WocPvP.Kits.Habilidades.Grappler;
import me.everton.WocPvP.Kits.Habilidades.Hulk;
import me.everton.WocPvP.Kits.Habilidades.Infernor;
import me.everton.WocPvP.Kits.Habilidades.Kangaroo;
import me.everton.WocPvP.Kits.Habilidades.Stomper;
import me.everton.WocPvP.Kits.Habilidades.Switcher;
import me.everton.WocPvP.Listeners.KitListener;
import me.everton.WocPvP.Listeners.OnDeath;
import me.everton.WocPvP.Listeners.OnJoin;
import me.everton.WocPvP.Listeners.OnMove;
import me.everton.WocPvP.Listeners.OnQuit;
import me.everton.WocPvP.Listeners.TagLogin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Score;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public class Main extends JavaPlugin implements Listener {
	public static Plugin plugin;

	public static BukkitScheduler sh = Bukkit.getScheduler();
	public static SettingsManager settings = SettingsManager.getInstance();
	public static Main m;

	public static ArrayList<Player> usandokit = new ArrayList<>();
	public static ArrayList<Player> grappler = new ArrayList<>();
	public static ArrayList<Player> kits = new ArrayList<>();
	public static ArrayList<Player> switcher = new ArrayList<>();
	public static ArrayList<Player> stomper = new ArrayList<>();
	public static ArrayList<Player> pvp = new ArrayList<>();
	public static ArrayList<Player> endermage = new ArrayList<>();
	public static ArrayList<Player> infernor = new ArrayList<>();
	public static ArrayList<Player> gladiator = new ArrayList<>();
	public static ArrayList<Player> kangaroo = new ArrayList<>();
	public static ArrayList<Player> hulk = new ArrayList<>();
	
	@SuppressWarnings("unused")
	private MySql mysql;

	public static Plugin getPlugin() {
		return plugin;
	}

	public void onLoad() {
		m = this;
		settings.setup(this);
	}

	public void onEnable() {
		plugin = this;
		autoMsg();

		Bukkit.getServer().getPluginManager()
				.registerEvents(new TagLogin(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Spec(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Admin(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new OnJoin(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Chat(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Fps(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Trolar(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Tag(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Fake(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new FakeOff(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Hack(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Specs(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new OnDeath(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new StaffChat(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Construir(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Infos(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new EventoCmd(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Placas(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new KitListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new Warp(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new HG(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new ServerScoreboard(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Loja(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new OnQuit(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new OnMove(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new CombatLog(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Mlg(), this);

		// KITS

		Bukkit.getServer().getPluginManager()
				.registerEvents(new Stomper(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Switcher(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Grappler(this), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Endermage(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Infernor(this), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new KitCmd(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Gladiator(this), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Kangaroo(this), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Hulk(this), this);
		getCommand("grappler").setExecutor(new Grappler(this));
		getCommand("kangaroo").setExecutor(new Kangaroo(this));
		getCommand("endermage").setExecutor(new Endermage());
		getCommand("switcher").setExecutor(new Switcher());
		getCommand("stomper").setExecutor(new Stomper());
		getCommand("infernor").setExecutor(new Infernor(this));
		getCommand("hulk").setExecutor(new Hulk(this));
		getCommand("pvp").setExecutor(new KitCmd());
		getCommand("gladiator").setExecutor(new Gladiator(this));

		// KITS

		getCommand("construir").setExecutor(new Construir());
		getCommand("mlg").setExecutor(new Mlg());
		getCommand("addmoney").setExecutor(new Money());
		getCommand("removemoney").setExecutor(new Money());
		getCommand("setmoney").setExecutor(new Money());
		getCommand("money").setExecutor(new Money());
		getCommand("wocpvp").setExecutor(this);
		getCommand("heal").setExecutor(new Uteis());
		getCommand("sopa").setExecutor(new Sopa());
		getCommand("fakes").setExecutor(new Fakes());
		getCommand("sc").setExecutor(new StaffChat());
		getCommand("fake").setExecutor(new Fake());
		getCommand("specs").setExecutor(new Specs());
		getCommand("tfake").setExecutor(new FakeOff());
		getCommand("tag").setExecutor(new Tag());
		getCommand("fps").setExecutor(new Fps());
		getCommand("autosoup").setExecutor(new AutoSoup());
		getCommand("hack").setExecutor(new Hack());
		getCommand("spec").setExecutor(new Spec());
		getCommand("adm").setExecutor(new Admin());
		getCommand("vis").setExecutor(new Admin());
		getCommand("tp").setExecutor(new Tp());
		getCommand("chat").setExecutor(new Chat());
		getCommand("troll").setExecutor(new Trolar());
		getCommand("loja").setExecutor(new Loja());
		getCommand("aplicacao").setExecutor(new Infos());
		getCommand("youtuber").setExecutor(new Infos());
		getCommand("limpar").setExecutor(new Uteis());
		getCommand("bc").setExecutor(new Broadcast());
		getCommand("broadcast").setExecutor(new Broadcast());
		getCommand("evento").setExecutor(new EventoCmd());
		getCommand("anunciovip").setExecutor(new AnunciarVip());
		getCommand("anunciounban").setExecutor(new AnunciarVip());
		getCommand("anunciodoacao").setExecutor(new AnunciarVip());
		getCommand("anunciokit").setExecutor(new AnunciarVip());
		getCommand("duvida").setExecutor(new Duvida());
		getCommand("rd").setExecutor(new Duvida());
		getCommand("invis").setExecutor(new Admin());
		getCommand("msg").setExecutor(new Tell());
		getCommand("r").setExecutor(new Tell());
		getCommand("hg").setExecutor(new HG());
		getCommand("warps").setExecutor(new Warp());
		getCommand("ajuda").setExecutor(new Uteis());
		getCommand("voar").setExecutor(new Uteis());
		getCommand("setrank").setExecutor(new Uteis());
		getCommand("reportar").setExecutor(new Uteis());
		
		Bukkit.getConsoleSender().sendMessage(
				"§7[§c§lWoCPvP§7] §3§lO plugin foi habilitado com sucesso!");

		if(settings.getConfig().getBoolean("mysql.usar")){
			mysql = new MySql();
			Bukkit.getConsoleSender().sendMessage("§7[§c§lWoCPvP§7] §3§lO plugin foi habilitado com sucesso!");
		}
		

		
		Player[] ons = Bukkit.getOnlinePlayers();
		for (int i = 0; i < ons.length; i++) {
			ons[i].setScoreboard(Bukkit.getScoreboardManager()
					.getMainScoreboard());
			ServerScoreboard.criarScoreboard(ons[i]);
		}
	}
	
	public static Location loc(String prefixx, Player p) {
		String prefix = prefixx.toLowerCase();
		try{
				World w = Bukkit.getServer().getWorld(
						settings.getConfig().getString(prefix + ".world"));
				Double x = settings.getConfig().getDouble(prefix + ".x");
				Double y = settings.getConfig().getDouble(prefix + ".y");
				Double z = settings.getConfig().getDouble(prefix + ".z");
				Float yaw = (float) settings.getConfig().getDouble(prefix + ".yaw");
				Float pitch = (float) settings.getConfig().getDouble(
						prefix + ".pitch");
				Location l = new Location(w, x, y, z);
				l.setYaw(yaw);
				l.setPitch(pitch);

				return l;
		}
		catch(IllegalArgumentException e){
			p.sendMessage("§7[§a!§7] §b"+prefix.toUpperCase() + " ainda nao foi definido!");
			if(p.hasPermission("wocpvp.admin")){
				p.sendMessage("§cUse /warps set " + prefix + " para definir esta warp!");
			}
		}
		return null;
	}
	
	public static void setLoc(Player p, String prefixx){
		String prefix = prefixx.toLowerCase();
		if(!p.isOp()){
			p.sendMessage("§4§7[§c!§7] Sem permissao!!");
		} else {
			settings.getConfig().set(prefix + ".world", p.getWorld().getName());
			settings.getConfig().set(prefix + ".x", p.getLocation().getX());
			settings.getConfig().set(prefix + ".y", p.getLocation().getY());
			settings.getConfig().set(prefix + ".z", p.getLocation().getZ());
			settings.getConfig().set(prefix + ".yaw", p.getLocation().getYaw());
			settings.getConfig().set(prefix + ".pitch", p.getLocation().getPitch());
			settings.saveConfig();
			p.sendMessage("§7[§a!§7] §b"+ prefix.toUpperCase() +" setado!");
		}
	}

	public static void giveSoup(Player p, int Sopas) {
		for (int i = 0; i < Sopas; i++) {
			p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}
	}

	@SuppressWarnings("rawtypes")
	public void salvarStats2() {
		for (Map.Entry e : ServerScoreboard.kills.entrySet()) {
			String p = (String) e.getKey();
			Score score = (Score) e.getValue();
			SettingsManager.score.set(p + ".kills",
					Integer.valueOf(score.getScore()));
		}
		for (Map.Entry e : ServerScoreboard.deaths.entrySet()) {
			String p = (String) e.getKey();
			Score score = (Score) e.getValue();
			SettingsManager.score.set(p + ".deaths",
					Integer.valueOf(score.getScore()));
		}
		
		settings.saveScore();

		for (World worlds : Bukkit.getWorlds()) {
			String world = worlds.getName();
			for (Iterator iterator = getServer().getWorld(world).getEntities()
					.iterator(); iterator.hasNext();) {
				Entity entity = (Entity) iterator.next();
				if ((entity.getType() != EntityType.PLAYER)
						&& (entity.getType() != EntityType.ITEM_FRAME)
						&& (entity.getType() != EntityType.MINECART)
						&& (entity.getType() != EntityType.PAINTING)) {
					entity.remove();
				}
			}
		}

		for (Player on : Bukkit.getOnlinePlayers()) {
			spawnItens(on);
			on.teleport(Main.loc("spawn", on));
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("wocpvp")) {
			if (sender.isOp()) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reload")) {

						Bukkit.getPluginManager().disablePlugin(this);
						onLoad();
						Bukkit.getPluginManager().enablePlugin(this);
						sender.sendMessage("§7[§6WoCPvP§7] §3Configuracao recarregada!");
					} else {
						sender.sendMessage("§7[§6WoCPvP§7] §3Use: /wocpvp reload");
					}
				} else {
					sender.sendMessage("§7[§6WoCPvP§7] §3Use: /wocpvp reload");
				}
			} else {
				sender.sendMessage("§4§7[§c!§7] Sem permissao!!");
			}
		}
		return false;
	}

	public static WorldGuardPlugin getWorldGuard() {
		Plugin plugin = Bukkit.getServer().getPluginManager()
				.getPlugin("WorldGuard");
		if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
			return null;
		}
		return (WorldGuardPlugin) plugin;
	}

	public static boolean areaPvP(Player p) {
		ApplicableRegionSet region = getWorldGuard().getRegionManager(
				p.getWorld()).getApplicableRegions(p.getLocation());
		if (region.allows(DefaultFlag.PVP)) {
			return true;
		}
		return false;
	}
	
	public static boolean areaConstruir(Player p) {
		ApplicableRegionSet region = getWorldGuard().getRegionManager(
				p.getWorld()).getApplicableRegions(p.getLocation());
		if (region.allows(DefaultFlag.BUILD)) {
			return true;
		}
		return false;
	}

	public static WorldGuardPlugin getWorldGuard1() {
		Plugin plugin = Bukkit.getServer().getPluginManager()
				.getPlugin("WorldGuard");
		if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
			return null;
		}
		return (WorldGuardPlugin) plugin;
	}

	public void onDisable() {
		settings.saveConfig();
		settings.saveItens();
		settings.saveMoney();
		settings.saveScore();
		salvarStats2();
		settings.reloadConfig();
		settings.reloadItens();
		settings.reloadMoney();
		settings.reloadScore();
		Bukkit.getConsoleSender().sendMessage(
				"§7[§c§lWoCPvP§7] §3§lO plugin foi desabilitado com sucesso!");
	}

	public static int getAmount(Player p, Material m) {
		int amount = 0;
		for (ItemStack item : p.getInventory().getContents()) {
			if ((item != null) && (item.getType() == m)
					&& (item.getAmount() > 0)) {
				amount += item.getAmount();
			}
		}
		return amount;
	}

	public static void log(String message) {
		if (getPlugin().getConfig().getBoolean("log")) {
			try {
				DateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				DateFormat data = new SimpleDateFormat("ddMMyyyy");
				Calendar cal = Calendar.getInstance();
				File dataFolder = new File("plugins/WoCPvP/logs");
				if (!dataFolder.exists()) {
					dataFolder.mkdir();
				}
				File saveTo = new File("plugins/WoCPvP/logs/log"
						+ data.format(cal.getTime()) + ".txt");
				if (!saveTo.exists()) {
					saveTo.createNewFile();
				}
				FileWriter fw = new FileWriter(saveTo, true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println("[" + dateFormat.format(cal.getTime()) + "] "
						+ message);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void logCmd(String cmd, Player p) {
		if (getPlugin().getConfig().getBoolean("log")) {
			try {
				DateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				DateFormat data = new SimpleDateFormat("ddMMyyyy");
				Calendar cal = Calendar.getInstance();
				File dataFolder = new File("plugins/WoCPvP/logs");
				if (!dataFolder.exists()) {
					dataFolder.mkdir();
				}
				File saveTo = new File("plugins/WoCPvP/logs/logCmd"
						+ data.format(cal.getTime()) + ".txt");
				if (!saveTo.exists()) {
					saveTo.createNewFile();
				}
				FileWriter fw = new FileWriter(saveTo, true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println("[" + dateFormat.format(cal.getTime()) + "] "
						+ p.getName() + " executou o comando: " + cmd);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void logChat(String msg, Player p) {
		if (getPlugin().getConfig().getBoolean("log")) {
			try {
				DateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				DateFormat data = new SimpleDateFormat("ddMMyyyy");
				Calendar cal = Calendar.getInstance();
				File dataFolder = new File("plugins/WoCPvP/logs");
				if (!dataFolder.exists()) {
					dataFolder.mkdir();
				}
				File saveTo = new File("plugins/WoCPvP/logs/logChat"
						+ data.format(cal.getTime()) + ".txt");
				if (!saveTo.exists()) {
					saveTo.createNewFile();
				}
				FileWriter fw = new FileWriter(saveTo, true);
				PrintWriter pw = new PrintWriter(fw);

				pw.println("[" + dateFormat.format(cal.getTime()) + "] "
						+ p.getName() + " digitou no chat: " + msg);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void autoMsg() {
		sh.scheduleSyncRepeatingTask(
				Main.plugin, new Runnable() {
					public void run() {
						Random r = new Random();
						for (Player on : Bukkit.getServer().getOnlinePlayers())
							on.sendMessage(getPlugin()
									.getConfig()
									.getStringList("msgauto")
									.get(r.nextInt(getPlugin().getConfig()
											.getStringList("msgauto").size()))
									.replace("&", "§"));

					}
				}, 1200L, 1200L);
	}

	public static Main getMain() {
		return (Main) Bukkit.getServer().getPluginManager().getPlugin("Main");
	}

	public static void spawnItens(final Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(
				Bukkit.getPluginManager().getPlugin("WoCPvP"), new Runnable() {
					@SuppressWarnings("deprecation")
					public void run() {
						p.getInventory().clear();
						p.getInventory().setArmorContents(null);
						ItemStack kits = new ItemStack(Material.ENDER_CHEST);
						ItemMeta metakits = kits.getItemMeta();
						metakits.setDisplayName("§6§lKits");
						kits.setItemMeta(metakits);

						ItemStack warps = new ItemStack(Material.PAPER);
						ItemMeta metawarps = warps.getItemMeta();
						metawarps.setDisplayName("§3§lWarps");
						warps.setItemMeta(metawarps);

						ItemStack loja = new ItemStack(Material.EMERALD);
						ItemMeta metaloja = loja.getItemMeta();
						metaloja.setDisplayName("§2§lLoja");
						loja.setItemMeta(metaloja);
						p.getInventory().setItem(1, kits);
						p.getInventory().setItem(4, warps);
						p.getInventory().setItem(7, loja);
						p.updateInventory();
					}
				}, 11L);

	}

	@EventHandler
	public void preprocessarCmd(PlayerCommandPreprocessEvent e) {
		final Player p = e.getPlayer();

		if ((EManager.pe.contains(p))
				&& (!e.getMessage().equalsIgnoreCase("/evento sair"))
				&& (!e.getMessage().equalsIgnoreCase("/ac"))
				&& (!e.getMessage().equalsIgnoreCase("/adm"))
				&& (!p.hasPermission("wocpvp.eventos"))) {
			p.sendMessage("§cSem comandos em eventos! Caso deseje sair, use /evento sair!");
			e.setCancelled(true);
		}
		
		if(e.getMessage().equalsIgnoreCase("/clear")){
			e.setCancelled(true);
			p.chat("/limpar");
		}

		if ((e.getMessage().contains("/?"))
				|| (e.getMessage().contains("/help"))
				|| (e.getMessage().contains("/ver")
						|| (e.getMessage().contains("/pl")) || (e.getMessage()
						.contains("/plugins")))) {
			e.setCancelled(true);
			p.sendMessage("§3§l****************************************");
			p.sendMessage("§4§l           WoCPvP");
			p.sendMessage("§7Servidor desenvolvido por");
			p.sendMessage("§c§l        EvertonPvP");
			p.sendMessage("§3§l****************************************");
		}

		if ((e.getMessage().equalsIgnoreCase("/kit urgal"))
				|| (e.getMessage().equalsIgnoreCase("/kit ghost"))
				|| (e.getMessage().equalsIgnoreCase("/kit skeleton"))) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Kit Desativado!");
		}
		if ((e.getMessage().equalsIgnoreCase("/admin"))) {
			e.setCancelled(true);
			p.chat("/adm");
		}
		if ((e.getMessage().toLowerCase().contains("/ac"))) {
			e.setCancelled(true);
			p.chat("/sc"
					+ e.getMessage().replace("/ac", "").replace("/aC", "")
							.replace("/Ac", "").replace("/AC", ""));
		}
		if ((e.getMessage().toLowerCase().contains("/report"))
				&& (!e.getMessage().toLowerCase().contains("/reportar"))) {
			e.setCancelled(true);
			p.sendMessage("§4§lERRO: §cPor favor, use o comando /reportar!");
		}
		if ((e.getMessage().toLowerCase().contains("/me"))) {
			e.setCancelled(true);
			p.sendMessage("§cComando desativado!");
		}

		if ((e.getMessage().toLowerCase().contains("/tell"))) {
			e.setCancelled(true);
			p.chat("/msg"
					+ e.getMessage().replace("/tell", "").replace("/telL", "")
							.replace("/teLL", "").replace("/tELL", "")
							.replace("/TELL", "").replace("/TeLl", "")
							.replace("/tElL", "").replace("/TEll", "")
							.replace("/teLL", ""));
		}
		if ((e.getMessage().equalsIgnoreCase("/cc"))) {
			e.setCancelled(true);
			p.chat("/chat clear");
		}
		if ((e.getMessage().equalsIgnoreCase("/lagg killmobs"))) {
			for (World worlds : Bukkit.getWorlds()) {
				String world = worlds.getName();
				for (@SuppressWarnings("rawtypes")
				Iterator iterator = getServer().getWorld(world).getEntities()
						.iterator(); iterator.hasNext();) {
					Entity entity = (Entity) iterator.next();
					if ((entity.getType() != EntityType.PLAYER)
							&& (entity.getType() != EntityType.ITEM_FRAME)
							&& (entity.getType() != EntityType.MINECART)
							&& (entity.getType() != EntityType.PAINTING)) {
						entity.remove();
					}
				}
			}

		}
		if ((e.getMessage().equalsIgnoreCase("/kitreset"))) {
			if (p.hasPermission("uk.kitreset")) {
				KitListener.hgkit.remove(p);
				HG.hg.remove(p);
				HG.hgin.remove(p);
				Fps.fps.remove(p);
				KitManager.resetKit(p, true);

				Bukkit.getScheduler().scheduleSyncDelayedTask(
						Bukkit.getPluginManager().getPlugin("WoCPvP"),
						new Runnable() {
							@SuppressWarnings("deprecation")
							public void run() {
								p.getInventory().clear();
								ItemStack kits = new ItemStack(
										Material.ENDER_CHEST);
								ItemMeta metakits = kits.getItemMeta();
								metakits.setDisplayName("§6§lKits");
								kits.setItemMeta(metakits);

								ItemStack warps = new ItemStack(Material.PAPER);
								ItemMeta metawarps = warps.getItemMeta();
								metawarps.setDisplayName("§3§lWarps");
								warps.setItemMeta(metawarps);

								ItemStack loja = new ItemStack(Material.EMERALD);
								ItemMeta metaloja = loja.getItemMeta();
								metaloja.setDisplayName("§2§lLoja");
								loja.setItemMeta(metaloja);

								p.getInventory().setItem(1, kits);
								p.getInventory().setItem(4, warps);
								p.getInventory().setItem(7, loja);

								p.updateInventory();
							}
						}, 6L);
			}
		}

		if ((e.getMessage().toLowerCase().contains("/invsee"))) {
			log(p.getName() + " abriu o inventário de "
					+ e.getMessage().replace("/invsee", "").replace(" ", ""));
		}

		if ((e.getMessage().toLowerCase().contains("/kit"))) {
			if (HG.hg.contains(p)) {
				e.setCancelled(true);
				p.sendMessage("§cSem Kits no warp HG!");
			}
		}

		if ((e.getMessage().toLowerCase().contains("/invsee"))) {
			log(p.getName() + " abriu o inventário de "
					+ e.getMessage().replace("/invsee", "").replace(" ", ""));
		}

		if ((e.getMessage().toLowerCase().contains("/fly"))) {
			e.setCancelled(true);
			p.chat("/voar" + e.getMessage().toLowerCase().replace("/fly", ""));
		}

		if ((e.getMessage().toLowerCase().contains("/spawn"))) {
			e.setCancelled(true);
			p.sendMessage("§aAguarde 5 segundos para ser teleportado ao spawn!");
			Bukkit.getScheduler().scheduleSyncDelayedTask(
					Bukkit.getPluginManager().getPlugin("WoCPvP"),
					new Runnable() {
						public void run() {
							p.teleport(Main.loc("spawn", p));
							UltraKits.Main.resetKit(p);
							spawnItens(p);

						}
					}, 100L);
		}

		if ((e.getMessage().equalsIgnoreCase("/kit"))) {
			if (HG.hg.contains(p)) {
				e.setCancelled(true);
				p.sendMessage("§cSem Kits no warp HG!");
			} else {
				e.setCancelled(true);
				Kit.InvKits(p, "1");
			}
		}

		if ((e.getMessage().equalsIgnoreCase("/kits"))) {
			if (HG.hg.contains(p)) {
				e.setCancelled(true);
				p.sendMessage("§cSem Kits no warp HG!");
			} else {
				e.setCancelled(true);
				Kit.InvKits(p, "1");
			}
		}

		if (e.getMessage().equalsIgnoreCase("/stop")) {
			if ((p.isOp()) || (p.hasPermission("*"))) {
				Bukkit.getConsoleSender().sendMessage(
						"§7[§c§lWoCPvP§7] §3§lServidor Desligado!");
				for (Player on : Bukkit.getOnlinePlayers()) {
					on.kickPlayer("§7[§c§lWoCPvP§7] §3§lServidor Reiniciando! \n §3§lVoltamos ja!");
				}
				Bukkit.shutdown();
			}
		}
		if (e.getMessage().equalsIgnoreCase("/reload")) {
			e.setCancelled(true);
			if ((p.isOp()) || (p.hasPermission("*"))) {
				for (Player on : Bukkit.getOnlinePlayers()) {
					if ((!on.isOp()) || (!on.hasPermission("*"))) {
						on.kickPlayer("§7[§c§lWoCPvP§7] §3§lServidor Reiniciando! \n §3§lVoltamos ja!");
					}
				}
				Bukkit.reload();
				Bukkit.getConsoleSender().sendMessage(
						"§7[§c§lWoCPvP§7] §3§lReload completo!");
				p.sendMessage("§7[§c§lWoCPvP§7] §3§lReload completo!");
			}
		}
		logCmd(e.getMessage(), p);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInteract1(PlayerInteractEvent e) {
		ItemStack tigela = new ItemStack(Material.BOWL, 1);
		ItemMeta tigelameta = tigela.getItemMeta();
		tigelameta.setDisplayName("Tigela");
		tigela.setItemMeta(tigelameta);
		Player p = e.getPlayer();
		Damageable hp = p;
		if (hp.getHealth() != 20.0D) {
			int sopa = 7;
			if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
					&& (p.getItemInHand().getTypeId() == 282)) {
				p.setHealth(hp.getHealth() + sopa > hp.getMaxHealth() ? hp
						.getMaxHealth() : hp.getHealth() + sopa);
				p.getItemInHand().setType(Material.BOWL);
				p.getItemInHand().setItemMeta(tigelameta);
				p.playSound(p.getLocation(), Sound.DRINK, 1F, 1F);
			}
		}
	}

	@EventHandler
	public void onItemDrop(ItemSpawnEvent e) {
		final Item drop = e.getEntity();
		if ((drop.getItemStack().getType() == Material.MUSHROOM_SOUP)
				|| (drop.getItemStack().getType() == Material.RED_MUSHROOM)
				|| (drop.getItemStack().getType() == Material.BROWN_MUSHROOM)) {
			Bukkit.getServer().getScheduler()
					.scheduleSyncDelayedTask(this, new Runnable() {
						public void run() {
							drop.remove();
						}
					}, 100L);
		} else {
			drop.remove();
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onProjHit(ProjectileHitEvent e) {
		if ((e.getEntity() instanceof Arrow)) {
			Arrow arrow = (Arrow) e.getEntity();
			arrow.remove();
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void aoQuebrar(PlayerItemBreakEvent e) {
		if ((e.getBrokenItem().getType() == Material.WOOD_SWORD)
				|| (e.getBrokenItem().getType() == Material.STONE_SWORD)
				|| (e.getBrokenItem().getType() == Material.GOLD_SWORD)
				|| (e.getBrokenItem().getType() == Material.IRON_SWORD)
				|| (e.getBrokenItem().getType() == Material.DIAMOND_SWORD)) {
			e.getBrokenItem().setAmount(1);
		}
	}

	@EventHandler
	public void aoDropar(PlayerPickupItemEvent e) {
		if ((e.getItem().getItemStack().getType() == Material.RED_MUSHROOM)
				|| (e.getItem().getItemStack().getType() == Material.BROWN_MUSHROOM)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlockBurn(BlockSpreadEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlockBurn(BlockIgniteEvent e) {
		if (!Construir.construir.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent ev) {
		if ((ev.getEntity() instanceof Player)) {
			Player p = (Player) ev.getEntity();

			ItemStack[] armor = p.getInventory().getArmorContents();
			if (armor.length > 0) {
				for (int i = 0; i < armor.length; i++) {
					armor[i].setDurability((short) -armor[i].getType()
							.getMaxDurability());
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent ev) {
		if ((ev.getEntity() instanceof Player)) {
			Player p = (Player) ev.getEntity();

			ItemStack[] armor = p.getInventory().getArmorContents();
			if (armor.length > 0) {
				for (int i = 0; i < armor.length; i++) {
					armor[i].setDurability((short) -armor[i].getType()
							.getMaxDurability());
				}
			}
		}
		if ((ev.getDamager() instanceof Player)) {
			Player p = (Player) ev.getDamager();
			ItemStack handitem = p.getItemInHand();
			if (handitem.getDurability() != handitem.getType()
					.getMaxDurability()) {
				handitem.setDurability((short) -32768);
			}
		}
	}

	@EventHandler
	public void completeTab(PlayerChatTabCompleteEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPermission("wocpvp.admin")) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if (!e.getChatMessage().equalsIgnoreCase(on.getName())) {
					e.getTabCompletions().clear();
				}
			}
		}
	}

	@EventHandler
	public void aoSpawnMob(CreatureSpawnEvent e) {
		e.setCancelled(true);
	}
}