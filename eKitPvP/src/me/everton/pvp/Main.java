package me.everton.pvp;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Iterator;

import me.everton.pvp.cmds.Admin;
import me.everton.pvp.cmds.Build;
import me.everton.pvp.cmds.Gerais;
import me.everton.pvp.cmds.Hack;
import me.everton.pvp.cmds.Rank;
import me.everton.pvp.cmds.StaffChat;
import me.everton.pvp.cmds.Troll;
import me.everton.pvp.cmds.Warps;
import me.everton.pvp.cmds.scoreCmds;
import me.everton.pvp.db.MySQL;
import me.everton.pvp.e1v1.Desafiar;
import me.everton.pvp.e1v1.Main1v1;
import me.everton.pvp.e1v1.bot.Bot;
import me.everton.pvp.kits.KitSelector;
import me.everton.pvp.kits.habilidades.Anchor;
import me.everton.pvp.kits.habilidades.Archer;
import me.everton.pvp.kits.habilidades.Avatar;
import me.everton.pvp.kits.habilidades.Backpacker;
import me.everton.pvp.kits.habilidades.C4;
import me.everton.pvp.kits.habilidades.Deshfire;
import me.everton.pvp.kits.habilidades.Endermage;
import me.everton.pvp.kits.habilidades.Fenix;
import me.everton.pvp.kits.habilidades.Icicles;
import me.everton.pvp.kits.habilidades.Jellyfish;
import me.everton.pvp.kits.habilidades.Madman;
import me.everton.pvp.kits.habilidades.Pyro;
import me.everton.pvp.kits.habilidades.Fireman;
import me.everton.pvp.kits.habilidades.Fisherman;
import me.everton.pvp.kits.habilidades.Flash;
import me.everton.pvp.kits.habilidades.Forcefield;
import me.everton.pvp.kits.habilidades.Gladiator;
import me.everton.pvp.kits.habilidades.Grappler;
import me.everton.pvp.kits.habilidades.Hulk;
import me.everton.pvp.kits.habilidades.Kangaroo;
import me.everton.pvp.kits.habilidades.Monk;
import me.everton.pvp.kits.habilidades.Ninja;
import me.everton.pvp.kits.habilidades.Poseidon;
import me.everton.pvp.kits.habilidades.Aladdin;
import me.everton.pvp.kits.habilidades.Sumo;
import me.everton.pvp.kits.habilidades.Snail;
import me.everton.pvp.kits.habilidades.Sniper;
import me.everton.pvp.kits.habilidades.Sombra;
import me.everton.pvp.kits.habilidades.Spectre;
import me.everton.pvp.kits.habilidades.Stomper;
import me.everton.pvp.kits.habilidades.Switcher;
import me.everton.pvp.kits.habilidades.Terrorista;
import me.everton.pvp.kits.habilidades.Thor;
import me.everton.pvp.kits.habilidades.Timelord;
import me.everton.pvp.kits.habilidades.Titan;
import me.everton.pvp.kits.habilidades.Vacuum;
import me.everton.pvp.kits.habilidades.Viper;
import me.everton.pvp.kits.habilidades.Wither;
import me.everton.pvp.listeners.Chat;
import me.everton.pvp.listeners.Dano;
import me.everton.pvp.listeners.DeathListener;
import me.everton.pvp.listeners.JoinListener;
import me.everton.pvp.listeners.ListenersPrincipais;
import me.everton.pvp.listeners.Motd;
import me.everton.pvp.listeners.MoveListener;
import me.everton.pvp.listeners.QuitListener;
import me.everton.pvp.listeners.Signs;
import me.everton.pvp.listeners.Sopa;
import me.everton.pvp.tags.TagCmd;
import me.everton.pvp.villagers.Villager1;
import me.everton.pvp.villagers.Villager2;
import me.everton.pvp.villagers.Villager3;
import me.everton.pvp.villagers.Villager4;
import me.everton.pvp.villagers.VillagerCmd;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;

public class Main extends JavaPlugin {
	public static Plugin plugin;
	public static Scoreboard sb;
	public static BukkitScheduler sh;
	public static ProtocolManager protocolManager;
	public static MySQL MySQL = new MySQL(getPlugin(), "localhost", "3306",
			"epvp", "root", "vertrigo");
	public static Connection c = null;
	public static EffectLib lib;
	public static EffectManager em;

	public void onLoad() {
		protocolManager = ProtocolLibrary.getProtocolManager();
	}

	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(
				"§7>> §bO plugin §6ePvP §bfoi habilitado!");
		Bukkit.getConsoleSender().sendMessage(
				"§7>> §bCriado por §6EvertonPvP§b!");
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(" ");

		registerCmds();
		registerEvents();
		setup();
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(
				"§7>> §bO plugin §6ePvP §bfoi desabilitado!");
		Bukkit.getConsoleSender().sendMessage(
				"§7>> §bCriado por §6EvertonPvP§b!");
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(" ");
		ScoreManager.storageData();
		OptionsManager.storageData();
		HandlerList.unregisterAll();
	}

	public void registerCmds() {
		registerCmd("admin", new Admin());
		registerCmd("cc", new Chat());
		registerCmd("pc", new Chat());
		registerCmd("sc", new StaffChat());
		registerCmd("s", new StaffChat());
		registerCmd("ac", new StaffChat());
		registerCmd("hack", new Hack());
		registerCmd("troll", new Troll());
		registerCmd("rank", new Rank());
		registerCmd("resetkdr", new scoreCmds());
		registerCmd("gm", new Gerais());
		registerCmd("build", new Build());
		registerCmd("clearlag", new Gerais());
		registerCmd("resetkit", new Gerais());
		registerCmd("ping", new Gerais());
		registerCmd("spawn", new Gerais());
		registerCmd("tp", new Gerais());
		registerCmd("tpall", new Gerais());
		registerCmd("doar", new Gerais());
		registerCmd("savestats", new Gerais());
		registerCmd("spawnprotection", new SpawnProtection());
		registerCmd("tag", new TagCmd());
		registerCmd("warp", new Warps());
		registerCmd("warps", new Warps());
		registerCmd("setlocation", new Warps());
		registerCmd("1v1", new Main1v1());
		registerCmd("spawnvillager", new VillagerCmd());
		registerCmd("abririnv", new VillagerCmd());
		registerCmd("criarinv", new VillagerCmd());
		registerCmd("bau", new Villager2());
	}

	public void registerEvents() {
		registerEvent(new KitSelector());
		registerEvent(new JoinListener());
		registerEvent(new QuitListener());
		registerEvent(new Admin());
		registerEvent(new Sopa());
		registerEvent(new Dano());
		registerEvent(new Chat());
		registerEvent(new ListenersPrincipais());
		registerEvent(new Troll());
		registerEvent(new DeathListener());
		registerEvent(new MoveListener());
		registerEvent(new SpawnProtection());
		registerEvent(new Build());
		registerEvent(new Signs());
		registerEvent(new OptionsSelector());
		registerEvent(new Warps());
		registerEvent(new Desafiar());
		registerEvent(new Motd());
		registerEvent(new Bot());
		registerEvent(new Villager1());
		registerEvent(new Villager2());
		registerEvent(new Villager3());
		registerEvent(new Villager4());

		// KITS LISTENER
		registerEvent(new Stomper());
		registerEvent(new C4());
		registerEvent(new Avatar());
		registerEvent(new Flash());
		registerEvent(new Grappler());
		registerEvent(new Kangaroo());
		registerEvent(new Archer());
		registerEvent(new Ninja());
		registerEvent(new Poseidon());
		registerEvent(new Sniper());
		registerEvent(new Sombra());
		registerEvent(new Terrorista());
		registerEvent(new Vacuum());
		registerEvent(new Forcefield());
		registerEvent(new Deshfire());
		registerEvent(new Spectre());
		registerEvent(new Backpacker());
		registerEvent(new Snail());
		registerEvent(new Viper());
		registerEvent(new Hulk());
		registerEvent(new Thor());
		registerEvent(new Titan());
		registerEvent(new Aladdin());
		registerEvent(new Fenix());
		registerEvent(new Monk());
		registerEvent(new Endermage());
		registerEvent(new Timelord());
		registerEvent(new Anchor());
		registerEvent(new Fireman());
		registerEvent(new Fisherman());
		registerEvent(new Pyro());
		registerEvent(new Gladiator());
		registerEvent(new Sumo());
		registerEvent(new Wither());
		registerEvent(new Icicles());
		registerEvent(new Jellyfish());
		registerEvent(new Madman());
		registerEvent(new Switcher());
	}

	public void registerCmd(String cmd, CommandExecutor c) {
		getCommand(cmd).setExecutor(c);
	}

	public void registerEvent(Listener l) {
		Bukkit.getPluginManager().registerEvents(l, this);
	}

	private void addPacketListeners() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
				new PacketAdapter(Main.getPlugin(),
						PacketType.Play.Server.SPAWN_ENTITY_LIVING) {
					@Override
					public void onPacketSending(PacketEvent e) {

					}
				});
	}

	private void startTasks() {
		sh.scheduleSyncRepeatingTask(this, new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					String header = "";
					header += "§b§m----»-------------------«----";
					header += "\n§rBem-Vindo ao §6§lTiger§f§lPvP§r\n";

					String footer = "";
					footer += "\n§fIP: §6§l" + Msgs.ip + "§r";
					Calendar ca = Calendar.getInstance();
					String horario = API.doisDigitos(ca.get(Calendar.HOUR_OF_DAY)) + ":" + API.doisDigitos(ca.get(Calendar.MINUTE)) + ":" + API.doisDigitos(ca.get(Calendar.SECOND));
					footer += "\n§fPing §6§l" + API.getPing(p) + "ms§7 ┃ §fHorário: §6§l" + horario + "§r";
					footer += "\n§b§m----»-------------------«----";
					API.setHeaderFooter(p, header, footer);
				}
			}
		}, 0L, 5L);

		sh.scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for (World worlds : Bukkit.getWorlds()) {
					String world = worlds.getName();
					for (Iterator<?> iterator = Bukkit.getServer()
							.getWorld(world).getEntities().iterator(); iterator
							.hasNext();) {
						Entity entity = (Entity) iterator.next();
						if (entity.getType() != EntityType.PLAYER
								&& entity.getType() != EntityType.ITEM_FRAME
								&& entity.getType() != EntityType.MINECART
								&& entity.getType() != EntityType.PAINTING
								&& entity.getType() != EntityType.FISHING_HOOK
								&& entity.getType() != EntityType.VILLAGER) {
							if (entity.getType() == EntityType.DROPPED_ITEM) {
								Item drop = (Item) entity;
								try {
									if (!drop.getItemStack().getItemMeta()
											.getDisplayName()
											.equalsIgnoreCase("noclear")) {
										entity.remove();
									}
								} catch (Exception ex) {
									entity.remove();
								}
							}
							entity.remove();
						}
					}
				}
			}
		}, 0L, 30 * 20L);

		sh.scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				API.broadcastMessage("§7[§c!§7] Guardando dados de jogadores em 5 segundos. Um lag mínimo é esperado.");
				sh.scheduleSyncDelayedTask(getPlugin(), new Runnable() {
					public void run() {
						ScoreManager.storageData();
						OptionsManager.storageData();
						API.broadcastMessage("§7[§c!§7] Dados guardados com sucesso!");
					}
				}, 5 * 20L);
			}
		}, 0L, 6000L);

		sh.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				Bukkit.getPluginManager().callEvent(new SecondsEvent());
			}
		}, 0L, 20L);
	}

	@SuppressWarnings("deprecation")
	public void setup() {
		plugin = this;
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		sh = Bukkit.getScheduler();
		lib = EffectLib.instance();
		em = new EffectManager(this);
		addPacketListeners();
		startTasks();

		c = MySQL.openConnection();
		if(c == null) {
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage(
					"§7>> §bErro na conexao com o MySQL!");
			Bukkit.getConsoleSender().sendMessage(
					"§7>> §bDesligando o Servidor!");
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.shutdown();
		} else {
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage(
					"§7>> §bConexao com o MySQL estabelecida!");
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage(" ");
		}

		for (World worlds : Bukkit.getWorlds()) {
			String world = worlds.getName();
			for (Iterator<?> iterator = Bukkit.getServer().getWorld(world)
					.getEntities().iterator(); iterator.hasNext();) {
				Entity entity = (Entity) iterator.next();
				if ((entity.getType() != EntityType.PLAYER)
						&& (entity.getType() != EntityType.ITEM_FRAME)
						&& (entity.getType() != EntityType.MINECART)){
					entity.remove();
				}
			}
		}

		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				try {
					for(Iterator<NPC> iterator = CitizensAPI.getNPCRegistry().iterator(); iterator.hasNext();) {
						NPC npc = iterator.next();
						for(Player on : Bukkit.getOnlinePlayers()) {
							on.hidePlayer((Player) npc.getEntity());
						}
						npc.despawn();
						npc.destroy();
					}
				} catch(Exception e) {
					
				}
			}
		}, 5L);

		for (Player on : Bukkit.getOnlinePlayers()) {
			ScoreManager.registerData(on);
			OptionsManager.registerData(on);
			ScoreboardManager.refreshSidebar(on);
			TagCmd.setTag(on);
			API.itensIniciais(on);
			on.updateInventory();
			on.teleport(LocationsManager.getSpawn());
			on.sendMessage("§6§m-------(------(------)------)-------");
			on.sendMessage("§b§l   Todos foram teleportados ao Spawn");
			on.sendMessage("§b§l              Devido à um Reload!");
			on.sendMessage("§6§m-------(------(------)------)-------");
			SpawnProtection.addProtection(on, true);
		}
		ShapelessRecipe recipe = new ShapelessRecipe(API.getSoup());
		recipe.addIngredient(Material.BOWL);
		recipe.addIngredient(Material.INK_SACK, 3);
		Bukkit.addRecipe(recipe);
		
		try {
			Villager1.spawnVillager(LocationsManager.getLocation("villager1"));
		} catch(Exception e) {
			
		}
		try {
			Villager2.spawnVillager(LocationsManager.getLocation("villager2"));
		} catch(Exception e) {
			
		}
		try {
			Villager3.spawnVillager(LocationsManager.getLocation("villager3"));
		} catch(Exception e) {
			
		}
		try {
			Villager4.spawnVillager(LocationsManager.getLocation("villager4"));
		} catch(Exception e) {
			
		}
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static ProtocolManager getPM() {
		return protocolManager;
	}

	public static EffectManager getEM() {
		return em;
	}
}
