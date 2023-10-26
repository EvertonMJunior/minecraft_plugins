package me.everton.epvp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import me.everton.epvp.AntiCheat.FFTest;
import me.everton.epvp.AntiCheat.MacroTest;
import me.everton.epvp.Bans.Ban;
import me.everton.epvp.Bans.Kick;
import me.everton.epvp.Bans.Mute;
import me.everton.epvp.Comandos.Admin;
import me.everton.epvp.Comandos.AnunciarVip;
import me.everton.epvp.Comandos.AutoSoup;
import me.everton.epvp.Comandos.Broadcast;
import me.everton.epvp.Comandos.Chat;
import me.everton.epvp.Comandos.ChatStaff;
import me.everton.epvp.Comandos.ComprarBuycraft;
import me.everton.epvp.Comandos.Construir;
import me.everton.epvp.Comandos.Duvida;
import me.everton.epvp.Comandos.Fps;
import me.everton.epvp.Comandos.Hack;
import me.everton.epvp.Comandos.Infos;
import me.everton.epvp.Comandos.KitCmd;
import me.everton.epvp.Comandos.KitSelector;
import me.everton.epvp.Comandos.Loja;
import me.everton.epvp.Comandos.Mlg;
import me.everton.epvp.Comandos.Money;
import me.everton.epvp.Comandos.Sopa;
import me.everton.epvp.Comandos.Spec;
import me.everton.epvp.Comandos.Specs;
import me.everton.epvp.Comandos.StaffChat;
import me.everton.epvp.Comandos.Tag;
import me.everton.epvp.Comandos.Tell;
import me.everton.epvp.Comandos.Tp;
import me.everton.epvp.Comandos.Trolar;
import me.everton.epvp.Comandos.Uteis;
import me.everton.epvp.Comandos.Warp;
import me.everton.epvp.Comandos.Warps;
import me.everton.epvp.Placas;
import me.everton.epvp.Kits.Habilidades.Avatar;
import me.everton.epvp.Kits.Habilidades.C4;
import me.everton.epvp.Kits.Habilidades.Cactus;
import me.everton.epvp.Kits.Habilidades.Flash;
import me.everton.epvp.Kits.Habilidades.Grappler;
import me.everton.epvp.Kits.Habilidades.Kangaroo;
import me.everton.epvp.Kits.Habilidades.Legolas;
import me.everton.epvp.Kits.Habilidades.Ninja;
import me.everton.epvp.Kits.Habilidades.Poseidon;
import me.everton.epvp.Kits.Habilidades.Sniper;
import me.everton.epvp.Kits.Habilidades.Sombra;
import me.everton.epvp.Kits.Habilidades.Stomper;
import me.everton.epvp.Kits.Habilidades.Terrorista;
import me.everton.epvp.Kits.Habilidades.Vacuum;
import me.everton.epvp.Listeners.OnDeath;
import me.everton.epvp.Listeners.OnJoin;
import me.everton.epvp.Listeners.OnMove;
import me.everton.epvp.Listeners.OnQuit;
import me.everton.epvp.Listeners.Principais;
import me.everton.epvp.e1v1.Cmd1v1;
import me.everton.epvp.e1v1.Listener1v1;
import me.everton.epvp.e1v1.Main1v1;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
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
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Score;
import org.bukkit.util.Vector;

import com.evilmidget38.UUIDFetcher;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public class Main extends JavaPlugin implements Listener {
	public static Plugin plugin;

	public static BukkitScheduler sh;
	public static SettingsManager settings = SettingsManager.getInstance();
	public static Main m;
	public static ArrayList<String> usandokit = new ArrayList<>();
	public static ArrayList<String> grappler = new ArrayList<>();
	public static ArrayList<String> legolas = new ArrayList<>();
	public static ArrayList<String> stomper = new ArrayList<>();
	public static ArrayList<String> vacuum = new ArrayList<>();
	public static ArrayList<String> quickdropper = new ArrayList<>();
	public static ArrayList<String> kangaroo = new ArrayList<>();
	public static ArrayList<String> cactus = new ArrayList<>();
	public static ArrayList<String> avatar = new ArrayList<>();
	public static ArrayList<String> poseidon = new ArrayList<>();
	public static ArrayList<String> sombra = new ArrayList<>();
	public static ArrayList<String> sniper = new ArrayList<>();
	public static ArrayList<String> endermage = new ArrayList<>();
	public static ArrayList<String> flash = new ArrayList<>();
	public static ArrayList<String> ninja = new ArrayList<>();
	public static ArrayList<String> terrorista = new ArrayList<>();
	public static ArrayList<String> c4 = new ArrayList<>();
	public static ArrayList<String> kits = new ArrayList<>();
	public static ArrayList<String> pvp = new ArrayList<>();
	
	public static ArrayList<String> listakits = new ArrayList<>();
	
	public static HashMap<String, Integer> money = new HashMap<>();
	
	public static ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
	public static ItemMeta smeta = sopa.getItemMeta();
    
    
    public static ItemStack cr = new ItemStack(Material.RED_MUSHROOM, 64);
    public static ItemMeta crm = cr.getItemMeta();
    
    
    public static ItemStack cb = new ItemStack(Material.BROWN_MUSHROOM, 64);
    public static ItemMeta cbm = cb.getItemMeta();
	
	public static String nomeservidor;
	public static String site;
	public static String loja;
	public static Boolean pirata;
	
	
	public static Economy economy = null;

	public static Plugin getPlugin() {
		return plugin;
	}

	public void onLoad() {
		m = this;
		settings.setup(this);
		if(Bukkit.getServer().getOnlineMode()){
				pirata = false;
		} else {
			pirata = true;
		}
		
		try {
			putMoney();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void onEnable() {
		plugin = this;
		sh = Bukkit.getScheduler();
		autoMsg();
		cbm.setDisplayName("§7Cogumelo Marrom");
	    cb.setItemMeta(cbm);
	    crm.setDisplayName("§cCogumelo Vermelho");
	    cr.setItemMeta(crm);
	    smeta.setDisplayName("§2Sopa de Cogumelos");
	    sopa.setItemMeta(smeta);

		Bukkit.getServer().getPluginManager().registerEvents(new Spec(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Admin(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new OnJoin(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Chat(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Fps(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new C4(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Trolar(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Tag(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Hack(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Specs(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Avatar(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Sombra(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ComprarBuycraft(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new OnDeath(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new StaffChat(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Construir(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Infos(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Legolas(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Flash(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Terrorista(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Placas(), this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new Warp(), this);
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
		Bukkit.getServer().getPluginManager()
			.registerEvents(new Kangaroo(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Stomper(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Principais(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Ban(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Listener1v1(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new MacroTest(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new KitSelector(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new KitManager(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Warps(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Ninja(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Poseidon(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Cactus(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Sniper(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new Vacuum(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new FFTest(), this);
		

		// KITS

		Bukkit.getServer().getPluginManager()
				.registerEvents(new Grappler(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new KitCmd(), this);
		getCommand("gm").setExecutor(new Uteis());

		// KITS

		getCommand("togglesc").setExecutor(new ChatStaff());
		getCommand("build").setExecutor(new Construir());
		getCommand("mute").setExecutor(new Mute());
		getCommand("kick").setExecutor(new Kick());
		getCommand("mlg").setExecutor(new Mlg());
		getCommand("invsee").setExecutor(new Uteis());
		getCommand("inv").setExecutor(new Uteis());
		getCommand("clear").setExecutor(new Uteis());
		getCommand("tpall").setExecutor(new Uteis());
		getCommand("kit").setExecutor(new KitCmd());
		getCommand("1v1").setExecutor(new Cmd1v1());
		getCommand("addmoney").setExecutor(new Money());
		getCommand("removemoney").setExecutor(new Money());
		getCommand("setmoney").setExecutor(new Money());
		getCommand("money").setExecutor(new Money());
		getCommand("unmute").setExecutor(new Mute());
		getCommand("epvp").setExecutor(this);
		getCommand("heal").setExecutor(new Uteis());
		getCommand("sopa").setExecutor(new Sopa());
		getCommand("sc").setExecutor(new ChatStaff());
		getCommand("specs").setExecutor(new Specs());
		getCommand("tag").setExecutor(new Tag());
		getCommand("fps").setExecutor(new Fps());
		getCommand("autosoup").setExecutor(new AutoSoup());
		getCommand("hack").setExecutor(new Hack());
		getCommand("delwarp").setExecutor(new Warps());
		getCommand("spec").setExecutor(new Spec());
		getCommand("adm").setExecutor(new Admin());
		getCommand("vis").setExecutor(new Admin());
		getCommand("tp").setExecutor(new Tp());
		getCommand("chat").setExecutor(new Chat());
		getCommand("troll").setExecutor(new Trolar());
		getCommand("loja").setExecutor(new Loja());
		getCommand("aplicacao").setExecutor(new Infos());
		getCommand("youtuber").setExecutor(new Infos());
		getCommand("bc").setExecutor(new Broadcast());
		getCommand("lc").setExecutor(new Chat());
		getCommand("broadcast").setExecutor(new Broadcast());
		getCommand("anunciovip").setExecutor(new AnunciarVip());
		getCommand("anunciounban").setExecutor(new AnunciarVip());
		getCommand("anunciodoacao").setExecutor(new AnunciarVip());
		getCommand("anunciokit").setExecutor(new AnunciarVip());
		getCommand("duvida").setExecutor(new Duvida());
		getCommand("rd").setExecutor(new Duvida());
		getCommand("invis").setExecutor(new Admin());
		getCommand("tell").setExecutor(new Tell());
		getCommand("r").setExecutor(new Tell());
		getCommand("warps").setExecutor(new Warp());
		getCommand("ajuda").setExecutor(new Uteis());
		getCommand("fly").setExecutor(new Uteis());
		getCommand("spawn").setExecutor(new Uteis());
		getCommand("warp").setExecutor(new Warp());
		getCommand("setrank").setExecutor(new Uteis());
		getCommand("report").setExecutor(new Uteis());
		getCommand("fftest").setExecutor(new FFTest());
		getCommand("macrotest").setExecutor(new MacroTest());
		getCommand("ac").setExecutor(new StaffChat());
		getCommand("admin").setExecutor(new Admin());
		getCommand("cc").setExecutor(new Chat());
		getCommand("resetkdr").setExecutor(new Uteis());
		getCommand("resetkit").setExecutor(new KitCmd());
		getCommand("dano").setExecutor(new Uteis());
		getCommand("head").setExecutor(new Uteis());
		getCommand("skit").setExecutor(new Uteis());
		getCommand("espada").setExecutor(new Uteis());
		getCommand("v").setExecutor(new Admin());
		getCommand("ban").setExecutor(new Ban());
		getCommand("setwarp").setExecutor(new Warps());
		getCommand("unban").setExecutor(new Ban());
		
		Warps.wa = Warps.w.getStringList("Arenas");
		Warps.wa2 = Warps.w.getStringList("WarpsGerais");
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		Bukkit.getConsoleSender().sendMessage(
				"§7[§c§lePvP§7] §3§lO plugin foi habilitado com sucesso!");

		if(settings.getConfig().getBoolean("mysql.usar")){
			
			Bukkit.getConsoleSender().sendMessage("§7[§c§lePvP§7] §3§lO MYSql foi ligado!");
		}
		
		Player[] ons = Bukkit.getOnlinePlayers();
		for (int i = 0; i < ons.length; i++) {
			ons[i].setScoreboard(Bukkit.getScoreboardManager()
					.getMainScoreboard());
			try {
				ServerScoreboard.criarScoreboard(ons[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		nomeservidor = settings.getConfig().getString("NomeServidor");
		site = settings.getConfig().getString("Site");
		loja = settings.getConfig().getString("Site_Loja");
		
		listakits.add("stomper");
		listakits.add("grappler");
		listakits.add("legolas");
		listakits.add("pvp");
		listakits.add("quickdropper");
		listakits.add("sniper");
		listakits.add("avatar");
		listakits.add("kangaroo");
		listakits.add("ninja");
		listakits.add("c4");
		listakits.add("flash");
		listakits.add("terrorista");
		listakits.add("sombra");
		listakits.add("cactus");
		listakits.add("poseidon");
		listakits.add("vacuum");
		
		for (World worlds : Bukkit.getWorlds()) {
			String world = worlds.getName();
			for (Iterator<?> iterator = getServer().getWorld(world).getEntities()
					.iterator(); iterator.hasNext();) {
				Entity entity = (Entity) iterator.next();
				if ((entity.getType() != EntityType.PLAYER)
						&& (entity.getType() != EntityType.ITEM_FRAME)
						&& (entity.getType() != EntityType.MINECART)
						&& (entity.getType() != EntityType.PAINTING)) {
					if(entity.getType() == EntityType.DROPPED_ITEM) {
						Item drop = (Item) entity;
						if(drop.getItemStack().getItemMeta().getDisplayName() == "C4") {
							return;
						}
					}					
					entity.remove();
				}
			}
		}

		for (Player on : Bukkit.getOnlinePlayers()) {
			spawnItens(on);
			on.teleport(Main.loc("spawn", on));
		}
		
		headerFooter();
	}
	
	public static void sendServer(Player p, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch(Exception e) {
			e.printStackTrace();
		}
		p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
	}
	
	private void headerFooter() {
		sh.scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(API.ping(p) < 0) {
						API.setHeaderFooter(p, "\n§6§lWoC§b§lNetwork\n", "§bVocê está no §4KitPvP!\n§bIP: §4jogar.wocpvp.com          §bSeu ping é de §40ms§b!");
					} else {
						API.setHeaderFooter(p, "\n§6§lWoC§b§lNetwork\n", "§bVocê está no §4KitPvP!\n§bIP: §4jogar.wocpvp.com          §bSeu ping é de §4" + API.ping(p) + "ms§b!");
					}
				}
			}
		}, 0, 5L);
	}
	
	public static void putMoney() throws Exception{
		for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
			if(pirata) {
			if(settings.getMoney().contains(p.getName().toLowerCase() + ".money")){
				money.put(p.getName().toLowerCase(), Integer.valueOf(settings.getMoney().getInt(p.getName().toLowerCase() + ".money")));
			} else {
				money.put(p.getName().toLowerCase(), 0);
			}
			} else {
				if(settings.getMoney().contains(UUIDFetcher.getUUIDOf(p.getName()) + ".money")){
					money.put(p.getName().toLowerCase(), Integer.valueOf(settings.getMoney().getInt(UUIDFetcher.getUUIDOf(p.getName()) + ".money")));
				} else {
					money.put(p.getName().toLowerCase(), 0);
				}
			}
		}
	}
	
	@EventHandler
	public void blockDamage(BlockDamageEvent e) {
		Player p = e.getPlayer();
		if(!Construir.construir.contains(p.getName())) {
			e.setCancelled(true);
		} else {
			if(CombatLog.cl.contains(p.getName())) {
			} else {
				e.setInstaBreak(true);
			}
		}
	}
	
	public static void saveMoney() throws Exception{
		for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if(pirata) {
				settings.getMoney().set(p.getName().toLowerCase() + ".money", money.get(p.getName().toLowerCase()));
			} else {
				settings.getMoney().set(UUIDFetcher.getUUIDOf(p.getName()) + ".money", money.get(p.getName().toLowerCase()));
			}
			settings.saveMoney();
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
			p.sendMessage("§7[§a!§7] Warp inexistente!");
			if(p.hasPermission("pvp.admin")){
				p.sendMessage("§cUse /warps set " + prefix + " para definir esta warp!");
			}
			System.out.println("Oe");
		}
		return null;
	}
	
	public static void setLoc(Player p, String prefixx){
		String prefix = prefixx.toLowerCase();
		if(!p.isOp()){
			p.sendMessage("§4§7[§c!§7] Sem permissao!");
		} else {
			settings.getConfig().set(prefix + ".world", p.getWorld().getName());
			settings.getConfig().set(prefix + ".x", p.getLocation().getX());
			settings.getConfig().set(prefix + ".y", p.getLocation().getY());
			settings.getConfig().set(prefix + ".z", p.getLocation().getZ());
			settings.getConfig().set(prefix + ".yaw", p.getLocation().getYaw());
			settings.getConfig().set(prefix + ".pitch", p.getLocation().getPitch());
			settings.saveConfig();
			p.sendMessage("§7[§a!§7] "+ prefix.toUpperCase() +" setado!");
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
	}
	
	public static void saveScore() {
		for(Map.Entry<?, ?> e : ScoreManager.kills.entrySet()) {
			String p = (String) e.getKey();
			int kills = (int) e.getValue();
			SQL.openConnection();
			try{
				if(SQL.tableContainsPlayer(p)) {				
					PreparedStatement sql = SQL.connection.prepareStatement("SELECT * FROM player_data WHERE player=?;");
					sql.setString(1, p);
					ResultSet result = sql.executeQuery();
					result.next();
					
					int k = result.getInt("kills");
					
					PreparedStatement sql2 = SQL.connection.prepareStatement("UPDATE player_data SET kills=? WHERE player=?;");
					sql2.setInt(1, k + kills);
					sql2.setString(2, p);
					sql2.executeUpdate();
					
					sql2.close();
					sql.close();
					result.close();
					
				} else {
					PreparedStatement newPlayer = SQL.connection.prepareStatement("INSERT INTO player_data (player, kills, deaths, money) values(?,?,0,0);");
					newPlayer.setString(1, p);
					newPlayer.setInt(2, kills);
					newPlayer.execute();
					newPlayer.close();
				}
			} catch(Exception e2) {
				e2.printStackTrace();
			} finally {
				SQL.closeConnection();
			}
		}
		
		for(Map.Entry<?, ?> e : ScoreManager.deaths.entrySet()) {
			String p = (String) e.getKey();
			int deaths = (int) e.getValue();
			SQL.openConnection();
			try{
				if(SQL.tableContainsPlayer(p)) {				
					PreparedStatement sql = SQL.connection.prepareStatement("SELECT * FROM player_data WHERE player=?;");
					sql.setString(1, p);
					ResultSet result = sql.executeQuery();
					result.next();
					
					int d = result.getInt("deaths");
					
					PreparedStatement sql2 = SQL.connection.prepareStatement("UPDATE player_data SET deaths=? WHERE player=?;");
					sql2.setInt(1, d + deaths);
					sql2.setString(2, p);
					sql2.executeUpdate();
					
					sql2.close();
					sql.close();
					result.close();
					
				} else {
					PreparedStatement newPlayer = SQL.connection.prepareStatement("INSERT INTO player_data (player, kills, deaths, money) values(?,0,?,0);");
					newPlayer.setString(1, p);
					newPlayer.setInt(2, deaths);
					newPlayer.execute();
					newPlayer.close();
				}
			} catch(Exception e2) {
				e2.printStackTrace();
			} finally {
				SQL.closeConnection();
			}
		}
		
		for(Map.Entry<?, ?> e : MoneyManager.money.entrySet()) {
			String p = (String) e.getKey();
			int money = (int) e.getValue();
			SQL.openConnection();
			try{
				if(SQL.tableContainsPlayer(p)) {				
					PreparedStatement sql = SQL.connection.prepareStatement("SELECT * FROM player_data WHERE player=?;");
					sql.setString(1, p);
					ResultSet result = sql.executeQuery();
					result.next();
					
					int m = result.getInt("money");
					
					PreparedStatement sql2 = SQL.connection.prepareStatement("UPDATE player_data SET money=? WHERE player=?;");
					sql2.setInt(1, m + money);
					sql2.setString(2, p);
					sql2.executeUpdate();
					
					sql2.close();
					sql.close();
					result.close();
					
				} else {
					PreparedStatement newPlayer = SQL.connection.prepareStatement("INSERT INTO player_data (player, kills, deaths, money) values(?,0,0,?);");
					newPlayer.setString(1, p);
					newPlayer.setInt(2, money);
					newPlayer.execute();
					newPlayer.close();
				}
			} catch(Exception e2) {
				e2.printStackTrace();
			} finally {
				SQL.closeConnection();
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("epvp")) {
			if (sender.isOp()) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reload")) {
						settings.reloadConfig();
						sender.sendMessage("§7[§6ePvP§7] §3Configuracao recarregada!");
					} else {
						sender.sendMessage("§7[§6ePvP§7] §3Use: /epvp reload");
					}
				} else {
					sender.sendMessage("§7[§6ePvP§7] §3Use: /epvp reload");
				}
			} else {
				sender.sendMessage("§4§7[§c!§7] Sem permissao!");
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

	
	@SuppressWarnings("deprecation")
	public static boolean areaPvP(Player p) {
		ApplicableRegionSet region = getWorldGuard().getRegionManager(
				p.getWorld()).getApplicableRegions(p.getLocation());
		if (region.allows(DefaultFlag.PVP)) {
			return true;
		}
		return false;
	}
	
	
	@SuppressWarnings("deprecation")
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
		settings.saveItens();
		settings.saveMoney();
		settings.saveScore();
		settings.saveBans();
		saveScore();
		
		try {
			if(SQL.connection != null && !SQL.connection.isClosed()) {
				SQL.connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Bukkit.getConsoleSender().sendMessage(
				"§7[§c§lePvP§7] §3§lO plugin foi desabilitado com sucesso!");
	}
	
	@EventHandler
	public void clearAnvils(EntityBlockFormEvent e) {
		Entity ent = e.getEntity();
		Block block = e.getBlock();
		if(ent.getType() == EntityType.FALLING_BLOCK) {
			if(block.getType() == Material.ANVIL) {
				block.getLocation().getBlock().setType(Material.AIR);
			}
		}
	}
	
	@EventHandler
	public void addFakes(PlayerChatTabCompleteEvent e) {
		
	}

	public static void autoMsg() {
		sh.scheduleSyncRepeatingTask(
				plugin, new Runnable() {
					@SuppressWarnings("deprecation")
					public void run() {
						Random r = new Random();
						for (Player on : Bukkit.getServer().getOnlinePlayers())
							on.sendMessage(getPlugin()
									.getConfig()
									.getStringList("msgauto")
									.get(r.nextInt(getPlugin().getConfig()
											.getStringList("msgauto").size()))
									.replace("&", "§").replace("%SERVIDOR%", settings.getConfig().getString("NomeServidor")));
						
						
						for (World worlds : Bukkit.getWorlds()) {
							String world = worlds.getName();
							for (@SuppressWarnings("rawtypes")
							Iterator iterator = Bukkit.getServer().getWorld(world).getEntities()
									.iterator(); iterator.hasNext();) {
								Entity entity = (Entity) iterator.next();
								
								if ((entity.getType() != EntityType.PLAYER)
										&& (entity.getType() != EntityType.ITEM_FRAME)
										&& (entity.getType() != EntityType.MINECART)
										&& (entity.getType() != EntityType.PAINTING)
										&& (entity.getType() != EntityType.FISHING_HOOK)) {
									if(entity.getType() == EntityType.DROPPED_ITEM) {
										Item drop = (Item) entity;
										if(drop.getItemStack().getItemMeta().getDisplayName() == "C4") {
											return;
										}
									}
									entity.remove();
								}
							}
						}

					}
				}, 1200L, 1200L);
	}

	public static Main getMain() {
		return (Main) Bukkit.getServer().getPluginManager().getPlugin("Main");
	}

	@SuppressWarnings("deprecation")
	public static void spawnItens(final Player p) {
		sh.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			@Override
			public void run() {
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				ItemStack kits = new ItemStack(Material.ENDER_CHEST);
				ItemMeta metakits = kits.getItemMeta();
				metakits.setDisplayName("§6§lKits §7(Clique Direito)");
				kits.setItemMeta(metakits);

				ItemStack warps = new ItemStack(Material.PAPER);
				ItemMeta metawarps = warps.getItemMeta();
				metawarps.setDisplayName("§3§lWarps §7(Clique Direito)");
				warps.setItemMeta(metawarps);

				ItemStack loja = new ItemStack(Material.EMERALD);
				ItemMeta metaloja = loja.getItemMeta();
				metaloja.setDisplayName("§2§lLoja §7(Clique Direito)");
				loja.setItemMeta(metaloja);
				p.getInventory().setItem(1, kits);
				p.getInventory().setItem(4, warps);
				p.getInventory().setItem(7, loja);
				p.updateInventory();
			}
		}, 2L);
	}

	@EventHandler
	public void preprocessarCmd(PlayerCommandPreprocessEvent e) {
		final Player p = e.getPlayer();
		
		if(e.getMessage().contains(":")) {
			if(e.getMessage().substring(0, 2).equalsIgnoreCase("//")) {
				e.setCancelled(true);
				p.sendMessage("§7[§c!§7] Use comandos sem : !");
			}
		}

		if ((e.getMessage().contains("/?"))
				|| (e.getMessage().contains("/help"))
				|| (e.getMessage().contains("/ver")
						|| (e.getMessage().contains("/pl")) || (e.getMessage()
						.contains("/plugins")))) {
			e.setCancelled(true);
			p.sendMessage("§3§l****************************************");
			p.sendMessage("§4§l           " + nomeservidor);
			p.sendMessage("§7Servidor desenvolvido por");
			p.sendMessage("§c§l        EvertonPvP");
			p.sendMessage("§3§l****************************************");
		}

		if ((e.getMessage().toLowerCase().contains("/me"))) {
			e.setCancelled(true);
			p.sendMessage("§cComando desativado!");
		}

		if (e.getMessage().equalsIgnoreCase("/stop")) {
			if ((p.isOp()) || (p.hasPermission("*"))) {
				e.setCancelled(true);
				Bukkit.getConsoleSender().sendMessage(
						"§7[§c§l"+nomeservidor+"§7] §3§lServidor Desligado!");
				for (Player on : Bukkit.getOnlinePlayers()) {
					on.sendMessage("§");
					sendServer(on, "hub");
				}
				sh.scheduleSyncDelayedTask(this, new Runnable() {
					
					@Override
					public void run() {
						Bukkit.shutdown();
					}
				}, 5 * 20L);
			}
		}
		if (e.getMessage().equalsIgnoreCase("/reload")) {
			e.setCancelled(true);
			if ((p.isOp()) || (p.hasPermission("*"))) {
				for (Player on : Bukkit.getOnlinePlayers()) {
					sendServer(on, "hub");
				}
				sh.scheduleSyncDelayedTask(this, new Runnable() {
					
					@Override
					public void run() {
						Bukkit.reload();
					}
				}, 5 * 20L);
				Bukkit.getConsoleSender().sendMessage(
						"§7[§c§l"+nomeservidor+"§7] §3§lReload completo!");
				p.sendMessage("§7[§c§l"+nomeservidor+"§7] §3§lReload completo!");
			}
		}
		if(Main1v1.on1v1.contains(p.getName())) {
			if(!e.getMessage().equalsIgnoreCase("/1v1")) {
				if(!p.hasPermission("pvp.admin")) {
					p.sendMessage("§cUse /1v1 para sair do 1v1!");
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInteract1(PlayerInteractEvent e) {
		ItemStack tigela = new ItemStack(Material.BOWL, 1);
		ItemMeta tigelameta = tigela.getItemMeta();
		tigelameta.setDisplayName("Tigela");
		tigela.setItemMeta(tigelameta);
		final Player p = e.getPlayer();
		Damageable hp = p;
		if (hp.getHealth() != 20.0D) {
			int sopa = 7;
			if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
					&& (p.getItemInHand().getType() == Material.MUSHROOM_SOUP)) {
				p.setHealth(hp.getHealth() + sopa > hp.getMaxHealth() ? hp
						.getMaxHealth() : hp.getHealth() + sopa);
				if(quickdropper.contains(p.getName())) {
					p.getItemInHand().setType(Material.BOWL);
					sh.scheduleSyncDelayedTask(this, new Runnable() {
						@Override
						public void run() {
							p.setItemInHand(new ItemStack(Material.AIR));
						}
					}, 1L);
				} else {
					p.getItemInHand().setType(Material.BOWL);
					p.getItemInHand().setItemMeta(tigelameta);
				}
				p.playSound(p.getLocation(), Sound.DRINK, 1F, 1F);
			}
		}
	}

	@EventHandler
	public void onItemDrop(ItemSpawnEvent e) {
		final Item drop = e.getEntity();
		if(drop.getItemStack().getItemMeta().getDisplayName() == "C4") {
			return;
		}
		drop.setPickupDelay(10);
		
			Bukkit.getServer().getScheduler()
					.scheduleSyncDelayedTask(this, new Runnable() {
						public void run() {
							drop.getWorld().playEffect(drop.getLocation(), Effect.SMOKE, 4);
							drop.remove();
						}
					}, 100L);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void aoDropar(PlayerDropItemEvent e){
		Item drop = e.getItemDrop();
		
		Player p = e.getPlayer();
		
		if(grappler.contains(p.getName())){
			if(drop.getItemStack().getType() == Material.LEASH){
				e.setCancelled(true);
			}
		}
		
		if(legolas.contains(p.getName())){
			if(drop.getItemStack().getType() == Material.BOW){
				e.setCancelled(true);
			}
			
			if(drop.getItemStack().getType() == Material.ARROW){
				e.setCancelled(true);
			}
		}
		
		if(avatar.contains(p.getName())){
			if(drop.getItemStack().getType() == Material.WOOL){
				e.setCancelled(true);
			}
			if(drop.getItemStack().getType() == Material.LAPIS_BLOCK){
				e.setCancelled(true);
			}
			if(drop.getItemStack().getType() == Material.GRASS){
				e.setCancelled(true);
			}
			if(drop.getItemStack().getType() == Material.REDSTONE_BLOCK){
				e.setCancelled(true);
			}
			if(drop.getItemStack().getType() == Material.BEACON){
				e.setCancelled(true);
			}
		}
		if(pvp.contains(p.getName())) {
			if(drop.getItemStack().getType() == Material.STONE_SWORD) {
				e.setCancelled(true);
			}
		} else {
			if(drop.getItemStack().getType() == Material.WOOD_SWORD) {
				e.setCancelled(true);
			}
		}
		
		if(c4.contains(p.getName())) {
			if(drop.getItemStack().getType() == Material.SLIME_BALL) {
				e.setCancelled(true);
			} else if(drop.getItemStack().getType() == Material.STONE_BUTTON) {
				e.setCancelled(true);
			}
		}
		
		if(sniper.contains(p.getName())) {
			if(drop.getItemStack().getType() == Material.BLAZE_ROD) {
				e.setCancelled(true);
			}
		}
		
		if(kangaroo.contains(p.getName())) {
			if(drop.getItemStack().getType() == Material.FIREWORK) {
				e.setCancelled(true);
			}
		}
		
		if(terrorista.contains(p.getName())) {
			if(drop.getItemStack().getType() == Material.MAGMA_CREAM) {
				e.setCancelled(true);
			}
		}
		
		if(sombra.contains(p.getName())) {
			if((drop.getItemStack().getType() == Material.WOOL) && (drop.getItemStack().getData().getData() == 15)) {
				e.setCancelled(true);
			}
		}
		
		if(flash.contains(p.getName())) {
			if(drop.getItemStack().getType() == Material.REDSTONE_TORCH_ON) {
				e.setCancelled(true);
			}
		}
		
		drop.setPickupDelay(10);
		
		if(drop.getItemStack().getType() == Material.BOWL){
			drop.setVelocity(p.getEyeLocation().getDirection().multiply(-0.5D));
			drop.setVelocity(new Vector(drop.getVelocity().getX(),
					1.5D, drop.getVelocity().getZ()));
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
		if (e.getItem().getItemStack().getType() != Material.MUSHROOM_SOUP) {
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
				handitem.setDurability((short) -handitem.getType().getMaxDurability());
			}
		}
	}
	
	public static int getAmount(Player p, Material m) {
		int n = 0;
		for(int i = 0; i < 36; i++) {
			if(p.getInventory().getItem(i).getType() == m) {
				n++;
			}
		}
		return n;
	}

	@EventHandler
	public void completeTab(PlayerChatTabCompleteEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPermission("pvp.admin")) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if (!e.getChatMessage().equalsIgnoreCase(on.getName())) {
					e.getTabCompletions().clear();
				}
			}
		}
	}

}